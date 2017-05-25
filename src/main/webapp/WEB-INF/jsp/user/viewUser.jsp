<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/header1.jsp"%>
<script language="javascript">

	function goback(){
		var url="${ctx}/user/userList.do";
		window.location.href=url;
	}


</script>
        <!--Body content-->
        <div id="content" class="clearfix">
            <div class="contentwrapper">
                <!--Content wrapper-->
                <div class="heading">
                    <h3>员工信息查看</h3>
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
         			 	<form:form id="loginForm" name="loginForm" method="post" action="userUpdate.do" modelAttribute="sysUser" cssClass="form-horizontal" role="form">
                           <form:errors cssStyle="color:red"></form:errors><br/>
								<form:input id="userId" type="hidden" path="userId" cssClass="form-control"  size="25"  placeholder="userId"/><form:errors path="userId" cssStyle="color:red"></form:errors> 
                            <div class="form-group">
                                <label class="col-md-2 control-label" for="loginName">用户名：</label>
                                <div class="col-md-6">
									 <form:input id="loginName" type="text" path="loginName" cssClass="form-control"  size="25" readonly="true" placeholder="用户名"/><form:errors path="loginName" cssStyle="color:red"></form:errors>  
                                </div>
                            </div>
                   			<!-- End .form-group  -->
                   			<div class="form-group">
                                <label class="col-md-2 control-label" for="loginName">工号：</label>
                                <div class="col-md-6">
									 <form:input id="loginName" type="text" path="sid" cssClass="form-control"  size="25" readonly="true"  placeholder="工号"/><form:errors path="sid" cssStyle="color:red"></form:errors>  
                                </div>
                            </div>
                            <!-- End .form-group  -->
                   			<div class="form-group">
                                <label class="col-md-2 control-label" for="loginName">姓：</label>
                                <div class="col-md-6">
									 <form:input id="loginName" type="text" path="firstName" cssClass="form-control"  size="25" readonly="true"  placeholder="姓"/><form:errors path="firstName" cssStyle="color:red"></form:errors>  
                                </div>
                            </div>
                            <!-- End .form-group  -->
                   			<div class="form-group">
                                <label class="col-md-2 control-label" for="loginName">名：</label>
                                <div class="col-md-6">
									 <form:input id="loginName" type="text" path="lastName" cssClass="form-control"  size="25" readonly="true"  placeholder="工号"/><form:errors path="lastName" cssStyle="color:red"></form:errors>  
                                </div>
                            </div>
                   			<!-- End .form-group  -->
                            <div class="form-group">
                                <label class="col-md-2 control-label" for="cellPhone" >手机号:</label>
                                <div class="col-md-6">
                                    <form:input cssClass="form-control" id="phone" type="text" path="cellPhone" readonly="true" placeholder="手机号" /><form:errors path="cellPhone" cssStyle="color:red"></form:errors>  
                                </div>
                            </div>
                             <!-- End .form-group  -->
                            <div class="form-group">
                                <label class="col-md-2 control-label" for="username">Email:</label>
                                <div class="col-md-6">
                                    <form:input cssclass="form-control" id="email" type="text" path="personalEmail" readonly="true" placeholder="email"  /><form:errors path="personalEmail" cssStyle="color:red"></form:errors> 
                                </div>
                            </div>
                             <!-- End .form-group  -->
                            <div class="form-group">
                                <label class="col-md-2 control-label" for="username">性别:</label>
                                <div class="col-md-6">
                                    <div class="radio-inline">
                                    	<form:input cssclass="form-control" id="email" type="text" path="gender" readonly="true" placeholder="email"  />
                                    </div>
                                </div>
                            </div>
                             <!-- End .form-group  -->
                            <div class="form-group">
                                <label class="col-md-2 control-label" for="text">入职日期：</label>
                                <div class="col-md-6">
                                    <form:input cssclass="form-control" id="name" type="text" path="entryDate" readonly="true" placeholder="入职日期"  /><form:errors path="entryDate" cssStyle="color:red"></form:errors> 
                                </div>
                            </div>
                            <!-- End .form-group  -->
                            <div class="form-group">
                                <label class="col-md-2 control-label" for="username">学历:</label>
                                <div class="col-md-6">
                                    <form:input cssclass="form-control" id="name" type="text" path="eduDegree" readonly="true" placeholder="学历"  /><form:errors path="eduDegree" cssStyle="color:red"></form:errors> 
                                </div>
                            </div>
                            <!-- End .form-group  -->
                            <div class="form-group">
                                <label class="col-md-2 control-label" for="username">毕业院校:</label>
                                <div class="col-md-6">
                                    <form:input cssclass="form-control" id="name" type="text" path="eduSchool" readonly="true" placeholder="毕业院校"  /><form:errors path="eduSchool" cssStyle="color:red"></form:errors> 
                                </div>
                            </div>
                            <!-- End .form-group  -->
                            <div class="form-group">
                                <label class="col-md-2 control-label" for="username">专业:</label>
                                <div class="col-md-6">
                                    <form:input cssclass="form-control" id="name" type="text" path="eduMajor" readonly="true" placeholder="专业"  /><form:errors path="eduMajor" cssStyle="color:red"></form:errors> 
                                </div>
                            </div>
                            <!-- End .form-group  -->
                            <div class="form-group">
                                <label class="col-md-2 control-label" for="username">地址:</label>
                                <div class="col-md-6">
                                    <form:input cssclass="form-control" id="name" type="text" path="homeAddr" readonly="true" placeholder="地址"  /><form:errors path="homeAddr" cssStyle="color:red"></form:errors>
                                </div>
                            </div>
                            <!-- End .form-group  -->
                            <div class="form-group">
                                <label class="col-md-2 control-label" for="username">生日:</label>
                                <div class="col-md-6">
                                    <form:input cssclass="form-control" id="name" type="text" path="birthDate" readonly="true" placeholder="生日" /><form:errors path="birthDate" cssStyle="color:red"></form:errors>
                                </div>
                            </div>
                            <!-- End .form-group  -->
                            <div class="form-group">
                                <label class="col-md-2 control-label" for="username">身份证号:</label>
                                <div class="col-md-6">
                                    <form:input cssclass="form-control" id="name" type="text" path="idNumber" readonly="true" placeholder="身份证号"  /><form:errors path="idNumber" cssStyle="color:red"></form:errors>
                                </div>
                            </div>
                            <!-- End .form-group  -->
                            <div class="form-group">
                                <label class="col-md-2 control-label" for="username">工资卡银行:</label>
                                <div class="col-md-6">
                                    <form:input cssclass="form-control" id="name" type="text" path="bankName" readonly="true" placeholder="工资卡银行"  /><form:errors path="bankName" cssStyle="color:red"></form:errors>
                                </div>
                            </div>
                            <!-- End .form-group  -->
                            <div class="form-group">
                                <label class="col-md-2 control-label" for="username">银行卡帐号:</label>
                                <div class="col-md-6">
                                    <form:input cssclass="form-control" id="name" type="text" path="bankAccount" readonly="true" placeholder="银行卡帐号" /><form:errors path="bankAccount" cssStyle="color:red"></form:errors>
                                </div>
                            </div>
                            <!-- End .form-group  -->
                            <div class="form-group">
                                <label class="col-md-2 control-label" for="text">离职日期：</label>
                                <div class="col-md-6">
                                    <form:input cssclass="form-control" id="name" type="text" path="quitDate" readonly="true"  placeholder="离职日期" /><form:errors path="quitDate" cssStyle="color:red"></form:errors>
                                </div>
                            </div>
                            <!-- End .form-group  -->
                            <div class="form-group">
                                <div class="col-md-offset-2">
                                    <button class="btn btn-info" type="button" onclick="goback();" >返回</button>
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