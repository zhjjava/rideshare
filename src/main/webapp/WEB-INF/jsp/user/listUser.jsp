<%@ include file="/common/header1.jsp"%>
<%
edu.mum.rideshare.util.displaytag.CheckboxTableDecorator decorator = new edu.mum.rideshare.util.displaytag.CheckboxTableDecorator();
decorator.setId("userId");
decorator.setFieldName("checkboxValues");
pageContext.setAttribute("checkboxDecorator", decorator);
%>

 <!-- End #header -->
    <div id="wrapper">
        <!--Body content-->
        <div id="content" class="clearfix">
            <div class="contentwrapper">
                <!--Content wrapper-->
                <div class="heading">
                    <h3>User List</h3>
                    <div class="resBtnSearch">
                        <a href="#"><span class="icon16 icomoon-icon-search-3"></span></a>
                    </div>
                    <div class="search">
                    </div>
                    <!-- End search -->
                </div>
                
               <div class="row">
                    <div class="col-md-12">
                        <div class="panel panel-default gradient">
                            <div class="panel-heading">
                                <h4>
                                        <span>User List</span>
                                    </h4>
                            </div>
                            <div class="panel-body noPad clearfix">
                                <div tabindex="5001" style="overflow: hidden;" class="responsive">
                                    <div id="DataTables_Table_1_wrapper" class="dataTables_wrapper form-inline" role="grid">
<form:form name="dataListForm" method="post" action="listUser.do"  modelAttribute="queryFormData" onsubmit="trimFormFields(this);">
        <form:errors cssStyle="color:red"></form:errors><br/>
       <div class="row">
           <div class="col-md-6">
               <button class="btn btn-info btn-xs" type="button" onClick="javascript:addRow()">
               <span class="icon16 icomoon-icon-pen-3 white"></span>Add a New User</button>
           </div>
           <div class="col-md-6">
               <div id="DataTables_Table_1_filter" class="dataTables_filter">
                   <label><span></span>
                   	<div class="col-md-4">
              				<form:input id="userName" type="text" path="field1" cssClass="form-control"  size="25" />
         			    </div>
         			    <div class="col-md-4">
                  			 <button type="submit" class="btn btn-default" onclick="query(this.form);">Search</button>
         			    </div>
                   </label>
               </div>
           </div>
       </div>       
	<c:if test="${pageData!=null}">
    <display:table name="pageData" style="width:100%" id="row" pagesize="${pageSize}" decorator="checkboxDecorator" class="tableTools display table table-bordered dataTable" form="dataListForm" excludedParams="" varTotals="subTotalMap">
	  <display:column property="checkbox" media="html" title="<input type='checkbox' name='allbox' id='allbox'
	  onclick='checkAll(this.form)'/>" class="alignCenter" style="width:24px" />
	  <display:column title="NO" style="white-space: nowrap;width:4px" headerClass="col-tb-1">${row_rowNum+pageSize*(pageNumber-1)}</display:column>  
      <display:column title="Emplooyee SID" sortProperty="userId" sortable="true"><a href="viewUser.do?rid=${row.userId}">${row.sid}</a></display:column>
      <display:column title="NAME"  sortable="true" sortProperty="loginName">${row.firstName}${row.lastName}</display:column>
      <display:column title="Depart Name" property="department.departName" sortable="true" sortProperty="department.departName"/>
       <display:column title="Operation">									       
			 <button  onClick="javascript:updateRow(this.form,${row.userId})" class="btn btn-info btn-xs" data-toggle="modal" data-target=".bs-example-modal-lg1"><span class="icon16 icomoon-icon-eye-2 white"></span>Edit</button>
	</display:column> 
      　　 <display:setProperty name="export.pdf" value="true" />
　　 <display:setProperty name="export.csv" value="true" />
    </display:table>
    </c:if>
</form:form>
              </div>
                                </div>
                           </div>
                        </div>
                        <!-- End .panel -->
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
<script language="javascript">
//focusElement(getEl("theValue"));

function query(form) {
   //should reset pageNumber when submit a query request.
   form.pageNumber.value=1;
   form.submit();
}
function updateRow(form,rId) {
	   form.action="preEditUser.do?rid=" + rId;
	   form.submit();
}

function addRow() {
	   var url="${ctx}/user/preAddUser.do";
	   window.location.href=url;
}

$(function(){
	var msg="${msg}";
	if(msg!=null&&msg!=""){
	  alert(msg);
	}
	
});
</script>
<%@ include file="/common/footer1.jsp"%>