/*package edu.mum.rideshare.service;


 * Copyright 2015  lc All rights reserved.
 


import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import edu.mum.core.exception.AppException;
import edu.mum.rideshare.model.SysCodeCategory;
import edu.mum.rideshare.model.SysLocale;
import edu.mum.rideshare.util.AppContextUtil;

*//**
 * 
 *
 * @author: mz    
 *//*
public class SysCodeServiceTest {
	private SysCodeService sysCodeService = null;
	@Before
	public void init() {
		System.out.println("init.");
		this.sysCodeService = AppContextUtil.getInstance().getBean("sysCodeService",
				SysCodeService.class);
	}

	@After
	public void destroy() {

	}

	@Test
	public void testListData() throws AppException {
//		List<KeyValueBean> dataList=SysCodeUtil.getSysCodeList(1000,"en_US");
		List<SysCodeCategory> dataList = sysCodeService.listSysCategoryList();
		System.out.println("dataList:" + dataList.size());
		Assert.assertEquals(1, dataList.size());
	}

	
	//@Test
	public void testTransaction() throws AppException {

		SysLocale aSysLocale = new SysLocale();
		aSysLocale.setLocaleCode("zh_CN");
		aSysLocale.setLocaleName("zh_CN");
		aSysLocale.setSeq(1);
		aSysLocale.setStatus(1);
		sysCodeService.save(aSysLocale);
		
		
		//try set same pk and in another transaction
		aSysLocale = new SysLocale();
		aSysLocale.setLocaleCode("zh_CN");
		aSysLocale.setLocaleName("zh_CN");
		aSysLocale.setSeq(1);
		aSysLocale.setStatus(1);
		sysCodeService.save(aSysLocale);
			
		Assert.assertTrue(true);
	}
	
	@Test
	public void testTransaction2() throws AppException {

		sysCodeService.testOneTransactionInSysLocale("test_test");
			
		Assert.assertTrue(true);
	}
	
	
//	@Test
//	public void testInsertAndQueryAndDelete() throws AppException {
//		SysCode sysCode = new SysCode();
////		sysCode.setCategory(1);
////		sysCode.setSequence(2);
////		sysCode.setStatus(2);
////		sysCode.setTheName("1");
////		sysCode.setValue("1");
////		sysCode.setTheValue("1234");
//		sysCodeService.create(sysCode);
//	}
//	@Test
//	public void testGetNextSeqValue() throws Exception {
//		System.out.println(sysCodeService.getNextSeqValue("seq_sys_code"));
//	}
//	@Test
//	public void testGroupBy() throws Exception {
//			sysCodeService.testGroupBy(); 
//	}
//	@Test
//	public void testGroupBy() throws Exception {
//		SysCodeCategory s = new SysCodeCategory();
//		s.setCategoryId(10012);
//		s.setCategoryName("nnnn");
//		s.setEditable(new Integer(10));
//		s.setStatus(1);
//			sysCodeService.save(s); 
//	}	
//    @Test
//    public void testJAXBAnnotation() throws Exception{
//    	
//    	JAXBContext context = 
//    	      JAXBContext.newInstance(SysCode.class);
//    	   Marshaller m = context.createMarshaller();
//    	   m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);  
//    	   File file = new File("c:\\1.xml");
//    	   m.marshal(sysCodeService.listSysCode(0, null, null).get(0), new FileOutputStream(file));
//
//    }
}

*/