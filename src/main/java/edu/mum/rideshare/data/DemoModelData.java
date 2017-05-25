package edu.mum.rideshare.data;

import java.util.List;
import java.util.Map;

/**
 *  
 * 类<strong>DemoModelData.java</strong>{}
 * @author: mz 
 * @version: 1.0	Date: July 15, 2015 8:46:21 AM
 */
public class DemoModelData {
	
    private String field1;
    private String field2;
    private String field3;
    private Integer field4;
    private Integer field5;
    private Float field6;
    private String field7;
    private String field8;
    private String field9;
    private String field10;
    
    //other HTML elements
    private List radioBoxEntries;
    private List checkBoxEntries1;
    private Map checkBoxEntries2;
    private List selectBoxEntries;
   
    
    /**two purposes for the following 3 properties,
    * 1.pass the initial/default values to front end
    * 2.collect the values checked by users in front end and pass them to the controller.
    **/
    private Integer radioBoxValue;
    private String selectedValue;
    private int[] checkedValues;
//    private String[] checkedValues;//or maybe the checked values are strings, just define it as the actual business scenario
    private String[] checkedValues2;
    public String getField1() {
		return field1;
	}
	public void setField1(String field1) {
		this.field1 = field1;
	}
	public String getField2() {
		return field2;
	}
	public void setField2(String field2) {
		this.field2 = field2;
	}
	public String getField3() {
		return field3;
	}
	public void setField3(String field3) {
		this.field3 = field3;
	}
	public Integer getField4() {
		return field4;
	}
	public void setField4(Integer field4) {
		this.field4 = field4;
	}
	public Integer getField5() {
		return field5;
	}
	public void setField5(Integer field5) {
		this.field5 = field5;
	}
	public Float getField6() {
		return field6;
	}
	public void setField6(Float field6) {
		this.field6 = field6;
	}
	public String getField7() {
		return field7;
	}
	public void setField7(String field7) {
		this.field7 = field7;
	}
	public String getField8() {
		return field8;
	}
	public void setField8(String field8) {
		this.field8 = field8;
	}
	public String getField9() {
		return field9;
	}
	public void setField9(String field9) {
		this.field9 = field9;
	}
	public String getField10() {
		return field10;
	}
	public void setField10(String field10) {
		this.field10 = field10;
	}
	public List getRadioBoxEntries() {
		return radioBoxEntries;
	}
	public void setRadioBoxEntries(List radioBoxEntries) {
		this.radioBoxEntries = radioBoxEntries;
	}
	public List getCheckBoxEntries1() {
		return checkBoxEntries1;
	}
	public void setCheckBoxEntries1(List checkBoxEntries1) {
		this.checkBoxEntries1 = checkBoxEntries1;
	}

	public Map getCheckBoxEntries2() {
		return checkBoxEntries2;
	}
	public void setCheckBoxEntries2(Map checkBoxEntries2) {
		this.checkBoxEntries2 = checkBoxEntries2;
	}
	public List getSelectBoxEntries() {
		return selectBoxEntries;
	}
	public void setSelectBoxEntries(List selectBoxEntries) {
		this.selectBoxEntries = selectBoxEntries;
	}
	public Integer getRadioBoxValue() {
		return radioBoxValue;
	}
	public void setRadioBoxValue(Integer radioBoxValue) {
		this.radioBoxValue = radioBoxValue;
	}
	public int[] getCheckedValues() {
		return checkedValues;
	}
	public void setCheckedValues(int[] checkedValues) {
		this.checkedValues = checkedValues;
	}
	public String getSelectedValue() {
		return selectedValue;
	}
	public void setSelectedValue(String selectedValue) {
		this.selectedValue = selectedValue;
	}
	public String[] getCheckedValues2() {
		return checkedValues2;
	}
	public void setCheckedValues2(String[] checkedValues2) {
		this.checkedValues2 = checkedValues2;
	}
 
    
    
	
}
