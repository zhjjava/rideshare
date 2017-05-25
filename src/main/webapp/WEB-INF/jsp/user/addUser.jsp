<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/header1.jsp"%>
<script language="javascript">

	function goback(){
		var url="${ctx}/user/listUser.do";
		window.location.href=url;
	}
	

</script>
        <!--Body content-->
        <div id="content" class="clearfix">
            <div class="contentwrapper">
                <!--Content wrapper-->
                <div class="heading">
                    <h3>Add New User</h3>
                    <div class="resBtnSearch">
                        <a href="#"><span class="icon16 icomoon-icon-search-3"></span></a>
                    </div>
                    <div class="search">
                    </div>
                    <!-- End search -->
                </div>
                <!-- End .heading-->
                <!-- Build page from here: Usual with <div class="row-fluid"></div> -->
                <div class="row">
                    <div class="col-md-12">
                        <div class="page-header">
                        </div>
         			 	<form:form id="loginForm" name="loginForm" method="post" action="addUser.do" modelAttribute="sysUser" cssClass="form-horizontal" role="form">
                           <form:errors cssStyle="color:red"></form:errors><br/>
                            <div class="form-group">
                                <label class="col-md-2 control-label" for="loginName">Login Name：</label>
                                <div class="col-md-6">
									 <form:input id="loginName" type="text" path="loginName" cssClass="form-control"  size="25"  placeholder="Login Name"/><form:errors path="loginName" cssStyle="color:red"></form:errors>  
                                </div>
                            </div>
                   			<!-- End .form-group  -->
                            <div class="form-group">
                                <label class="col-md-2 control-label" for="loginName">Password：</label>
                                <div class="col-md-6">
									 <form:input id="password" type="text" path="password" cssClass="form-control"  size="25"  placeholder="Password"/><form:errors path="password" cssStyle="color:red"></form:errors>  
                                </div>
                            </div>
                   			<!-- End .form-group  -->
                   			<div class="form-group">
                                <label class="col-md-2 control-label" for="loginName">Employee ID：</label>
                                <div class="col-md-6">
									 <form:input id="loginName" type="text" path="sid" cssClass="form-control"  size="25"  placeholder="Employee ID"/><form:errors path="sid" cssStyle="color:red"></form:errors>  
                                </div>
                            </div>
                            <!-- End .form-group  -->
                   			<div class="form-group">
                                <label class="col-md-2 control-label" for="loginName">First Name：</label>
                                <div class="col-md-6">
									 <form:input id="loginName" type="text" path="firstName" cssClass="form-control"  size="25"  placeholder="First Name"/><form:errors path="firstName" cssStyle="color:red"></form:errors>  
                                </div>
                            </div>
                            <!-- End .form-group  -->
                   			<div class="form-group">
                                <label class="col-md-2 control-label" for="loginName">Last Name：</label>
                                <div class="col-md-6">
									 <form:input id="loginName" type="text" path="lastName" cssClass="form-control"  size="25"  placeholder="Last Name"/><form:errors path="lastName" cssStyle="color:red"></form:errors>  
                                </div>
                            </div>
                   			<!-- End .form-group  -->
                            <div class="form-group">
                                <label class="col-md-2 control-label" for="cellPhone" >Cell Phone Number:</label>
                                <div class="col-md-6">
                                    <form:input cssClass="form-control" id="phone" type="text" path="cellPhone" placeholder="Cell Phone Number" /><form:errors path="cellPhone" cssStyle="color:red"></form:errors>  
                                </div>
                            </div>
                             <!-- End .form-group  -->
                            <div class="form-group">
                                <label class="col-md-2 control-label" for="username">Email:</label>
                                <div class="col-md-6">
                                    <form:input cssclass="form-control" id="email" type="text" path="personalEmail" placeholder="email"  /><form:errors path="personalEmail" cssStyle="color:red"></form:errors> 
                                </div>
                            </div>
                             <!-- End .form-group  -->
                            <div class="form-group">
                                <label class="col-md-2 control-label" for="username">Gender:</label>
                                <div class="col-md-6">
                                    <div class="radio-inline">
                                        <label>
                                        	 <form:radiobutton path="gender" value="Male"/>Male  
                                        </label>
                                        <label>
                                        	<form:radiobutton path="gender" value="Female"/>Female 
                                        </label>
                                    </div>
                                </div>
                            </div>
                             <!-- End .form-group  -->
                            <div class="form-group">
                                <label class="col-md-2 control-label" for="text">Entry Date：</label>
                                <div class="col-md-6">
                                    <form:input cssclass="form-control" id="name" type="text" path="entryDate" placeholder="Entry Date"  /><form:errors path="entryDate" cssStyle="color:red"></form:errors> 
                                </div>
                            </div>
                           
                            <!-- End .form-group  -->
                            <div class="form-group">
                                <label class="col-md-2 control-label" for="username">ID Number:</label>
                                <div class="col-md-6">
                                    <form:input cssclass="form-control" id="name" type="text" path="idNumber" placeholder="ID Number"  /><form:errors path="idNumber" cssStyle="color:red"></form:errors>
                                </div>
                            </div>
                            <!-- End .form-group  -->
                            <div class="form-group">
                                <label class="col-md-2 control-label" for="text">Quite Date：</label>
                                <div class="col-md-6">
                                    <form:input cssclass="form-control" id="name" type="text" path="quitDate"  placeholder="Quite Date" /><form:errors path="quitDate" cssStyle="color:red"></form:errors>
                                </div>
                            </div>
                            
                            <div class="form-group">
                                <label class="col-md-2 control-label" for="text">Status：</label>
                                <div class="col-md-6">
                                    <form:select path="status" >  
								            <form:option value="1" label="Enabled"/>
								            <form:option value="0" label="Disabled"/>
								    </form:select>
                                </div>
                            </div>              
                            <!-- End .form-group  -->
                            <div class="form-group">
                                <div class="col-md-offset-2">
                                    <button type="submit" class="btn btn-info marginR10 marginL10">Save</button>
                                    <button class="btn btn-info" type="button" onclick="goback();" >Cancel</button>
                                </div>
                            </div>
                            <!-- End .form-group  -->
                        </form:form>
                    </div>
                    <!-- End .span12 -->
                </div>
                <!-- End .row -->
                <!-- Page end here -->
            </div>
            <!-- End contentwrapper -->
        </div>
        <!-- End #content -->
    </div>
<%@ include file="/common/footer1.jsp"%>