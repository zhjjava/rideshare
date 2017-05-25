/*
 * @(#)ExcelUtil.java	2015-07-08上午11:24:01
 * Copyright 2015  lc All rights reserved.
 */
package edu.mum.rideshare.util;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import edu.mum.core.exception.AppException;
import edu.mum.core.util.BeanUtilsEx;
/**


/**
 * 
 * 类<strong>ExcelUtil.java</strong>{此类功能描述}
 * 
 * @author: mz
 * @version: 1.0 Date: 2015-07-08 上午11:24:01
 */
public class ExcelUtil {

	/**
	 * 
	 * @param sheetName
	 * @param rowList,数据list
	 * @param propertieNames, list中对象的属性名，按设定的顺序显示
	 * 本方法将用反射的方式取到对应的值
	 * @param columnHeaderNames,excel的列名
	 * @return
	 * @throws AppException
	 */
    public static Workbook buildWorkBook(Workbook workbook, String sheetName, List<Object> rowDataList,List<String> propertyNames,List<String> columnHeaderNames) throws AppException{
    	if(CollectionUtils.isEmpty(rowDataList)||CollectionUtils.isEmpty(propertyNames) ||CollectionUtils.isEmpty(columnHeaderNames)){
    		throw new AppException("Expected valid paramenters: rowlist,propertyNames,columnHeaderNames");
    	}

		try {
			// 创建第一个sheet（页），并命名
			Sheet sheet = null;
			if(workbook.getNumberOfSheets()>0){
				sheet = workbook.getSheetAt(0);
			}
			if(sheet==null){
				sheet = workbook.createSheet((StringUtils.isNoneBlank(sheetName))?sheetName:"Sheet1");
			}
			// 手动设置列宽。第一个参数表示要为第几列设；，第二个参数表示列的宽度，n为列高的像素数。
			int size = columnHeaderNames.size();
			for(int i=0;i<size;i++){
			    sheet.setColumnWidth(i, (int) (35.7 * 150));
			}

			// 创建两种单元格格式
			CellStyle cs = workbook.createCellStyle();
			CellStyle cs2 = workbook.createCellStyle();
			// 创建两种字体
			Font f = workbook.createFont();
			Font f2 = workbook.createFont();

			// 创建第一种字体样式（用于列名）
			f.setFontHeightInPoints((short) 10);
			f.setColor(IndexedColors.BLACK.getIndex());
			f.setBoldweight(Font.BOLDWEIGHT_BOLD);

			// 创建第二种字体样式（用于值）
			f2.setFontHeightInPoints((short) 10);
			f2.setColor(IndexedColors.BLACK.getIndex());

			// 设置第一种单元格的样式（用于列名）
			cs.setFont(f);
			cs.setBorderLeft(CellStyle.BORDER_THIN);
			cs.setBorderRight(CellStyle.BORDER_THIN);
			cs.setBorderTop(CellStyle.BORDER_THIN);
			cs.setBorderBottom(CellStyle.BORDER_THIN);
			cs.setAlignment(CellStyle.ALIGN_CENTER);

			// 设置第二种单元格的样式（用于值）
			cs2.setFont(f2);
			cs2.setBorderLeft(CellStyle.BORDER_THIN);
			cs2.setBorderRight(CellStyle.BORDER_THIN);
			cs2.setBorderTop(CellStyle.BORDER_THIN);
			cs2.setBorderBottom(CellStyle.BORDER_THIN);
			cs2.setAlignment(CellStyle.ALIGN_CENTER);

			// 创建第一行, header
			Row row = sheet.createRow(0);
			//设置列名
//			int size2 = columnHeaderNames.size();
			for(int i=0;i<size;i++){
			    Cell cell = row.createCell(i);
			    cell.setCellValue(columnHeaderNames.get(i));
			    cell.setCellStyle(cs);
			}
			//设置数据块部分，设置每行每列的值
			Row dataRow = null;
			Cell cell = null;
			int listSize = rowDataList.size();
			
			for (int i = 0; i < listSize; i++) {
			    //创建一行, Row 行,Cell 方格 , Row 和 Cell 都是从0开始计数的
			    dataRow = sheet.createRow((i+1));
			    // 在row行上创建一个cell
			    int j=0;
			    for(String propertyName :propertyNames){
			        cell = dataRow.createCell(j);
			        cell.setCellValue(BeanUtilsEx.getProperty(rowDataList.get(i), propertyName));
			        cell.setCellStyle(cs2);
			        j++;
			    }
			}
		} catch (Exception e) {
			throw new AppException("生成Excel文件错误.",e);
		}
        return workbook;
    }
    
	/**
	 * 
	 * @param sheetName
	 * @param rowList,数据list
	 * @param propertieNames, list中对象的属性名，按设定的顺序显示
	 * 本方法将用反射的方式取到对应的值
	 * @param columnHeaderNames,excel的列名
	 * @return
	 * @throws AppException
	 */
    @SuppressWarnings("resource")
	public static Workbook buildWorkBook(String sheetName, List<Object> rowDataList,String [] propertyNames,String columnHeaderNames[]) throws AppException{
    	if(CollectionUtils.isEmpty(rowDataList)||ArrayUtils.isEmpty(propertyNames) ||ArrayUtils.isEmpty(columnHeaderNames)){
    		throw new AppException("Expected valid paramenters: rowlist,propertyNames,columnHeaderNames");
    	}
		Workbook wb = new HSSFWorkbook();
		wb = ExcelUtil.buildWorkBook(wb, sheetName, rowDataList, Arrays.asList(propertyNames), Arrays.asList(columnHeaderNames));
		return wb;
    }
    
    @SuppressWarnings("resource")
	public static Workbook buildWorkBook(Workbook workbook, String sheetName, List<Object> rowDataList,String [] propertyNames,String columnHeaderNames[]) throws AppException{
    	if(CollectionUtils.isEmpty(rowDataList)||ArrayUtils.isEmpty(propertyNames) ||ArrayUtils.isEmpty(columnHeaderNames)){
    		throw new AppException("Expected valid paramenters: rowlist,propertyNames,columnHeaderNames");
    	}
		workbook = ExcelUtil.buildWorkBook(workbook, sheetName, rowDataList, Arrays.asList(propertyNames), Arrays.asList(columnHeaderNames));
		return workbook;
    }
    
	/**
	 * 
	 * @param sheetName
	 * @param rowList,数据list
	 * @param propertieNames, list中对象的属性名，按设定的顺序显示
	 * 本方法将用反射的方式取到对应的值
	 * @param otherCellDataMap,传入在特定单元格中药写入的值，如表头中某个单元格要显示年月日, 格式是{0,0: 2015年01月2日,....}, key 是"列号,行号", 都从0开始
	 * @param dataAreaStartCellNo, 数据区域开始的行号和列号
	 * @return
	 * @throws AppException
	 */
    public static Workbook buildWorkBook(Workbook workbook, String sheetName, List<Object> rowDataList,String[] propertyNames,Map otherCellDataMap, String dataAreaStartCellNo) throws AppException{
    	if(CollectionUtils.isEmpty(rowDataList)||ArrayUtils.isEmpty(propertyNames) || StringUtils.isBlank(dataAreaStartCellNo)){
    		throw new AppException("Expected valid paramenters: rowlist,propertieNames");
    	}

		try {
			Sheet sheet = workbook.getSheetAt(0);
//			// 手动设置列宽。第一个参数表示要为第几列设；，第二个参数表示列的宽度，n为列高的像素数。
//			int size = columnHeaderNames.size();
//			for(int i=0;i<size;i++){
//			    sheet.setColumnWidth(i, (int) (35.7 * 150));
//			}
			//设置特定单元格的值
			if(otherCellDataMap!=null){
				Set<Entry> entries = otherCellDataMap.entrySet();
				String key = null;
				String cellValue = null;
				int rowNo = 0;
				int colNo = 0;
				Row currentRow = null;
				Cell currentCell =null;
				for(Entry ent : entries){
					key = ent.getKey().toString();
					cellValue = ent.getValue().toString();
					colNo = Integer.parseInt(key.substring(0, key.indexOf(",")));
					rowNo = Integer.parseInt(key.substring(key.indexOf(",")+1));
					currentRow = sheet.getRow(rowNo);
					currentCell = currentRow.getCell(colNo);
					currentCell.setCellValue(cellValue);
				}
			}
			int dataAreaStartRowNo = Integer.parseInt(dataAreaStartCellNo.substring(dataAreaStartCellNo.indexOf(",")+1));
			int dataAreaStartColNo = Integer.parseInt(dataAreaStartCellNo.substring(0,dataAreaStartCellNo.indexOf(",")));
			
			//设置数据块部分，设置每行每列的值
			Row dataRow = null;
			Cell cell = null;
			int rowNo =dataAreaStartRowNo;			
			for (Object obj : rowDataList) {
				dataRow = sheet.getRow(rowNo);
			    int colNo=dataAreaStartColNo;
			    for(String propertyName :propertyNames){
			        cell = dataRow.getCell(colNo);
			        cell.setCellValue(BeanUtilsEx.getProperty(obj, propertyName));
			        //cell.setCellStyle(cs2);
			        colNo++;
			    }
			    rowNo++;
			}
		} catch (Exception e) {
			throw new AppException("生成Excel文件错误.",e);
		}
        return workbook;
    }
}
