<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/header1.jsp"%>
    <!-- End #header -->
    <div id="wrapper">
        <!--Body content-->
        <div id="content" class="clearfix">
            <div class="contentwrapper">
                <!--Content wrapper-->
                <div class="heading">
                    <h3>SPRING MVC Tag lib and validation sample</h3>
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
                                        <span>SPRING MVC Tag lib and validation sample</span>
                                    </h4>
                            </div>
                            <div class="panel-body noPad clearfix">
                                <div tabindex="5001" style="overflow: hidden;" class="responsive">
                                    <div id="DataTables_Table_1_wrapper" class="dataTables_wrapper form-inline" role="grid">

<form:form name="demoForm" method="post" action="editData.do"  modelAttribute="demoModelData">
       								    <form:errors cssStyle="color:red"></form:errors><br/>
                                        <div class="row">
                                           <div class="col-md-2">Field1(Non-empty)</div>
                                            <div class="col-md-2">
                                               <form:input id="field1" type="text" path="field1" cssClass="form-control"  size="25" />
                                            </div>                                                                                 
                                        </div>
                                        <div class="row">
                                           <div class="col-md-2">Field2(Non-empty，min:3，max:6)</div>
                                            <div class="col-md-2">
                                               <form:input id="field2" type="text" path="field2" cssClass="form-control"  size="25" />
                                            </div>                                                                                 
                                        </div>
                                        <div class="row">
                                           <div class="col-md-2">Field3(Nullable，but if not null, then min:3, max:6)</div>
                                            <div class="col-md-2">
                                               <form:input id="field3" type="text" path="field3" cssClass="form-control"  size="25" />
                                            </div>                                                                                 
                                        </div>
                                        <div class="row">
                                           <div class="col-md-2">Field4(Non-empty, Integer)</div>
                                            <div class="col-md-2">
                                               <form:input id="field4" type="text" path="field4" cssClass="form-control"  size="25" />
                                            </div>                                                                                 
                                        </div>
                                        <div class="row">
                                           <div class="col-md-2">Field5(Non-empty, Integer，min:0，max:100)</div>
                                            <div class="col-md-2">
                                               <form:input id="field5" type="text" path="field5" cssClass="form-control"  size="25" />
                                            </div>                                                                                 
                                        </div>
                                        <div class="row">
                                           <div class="col-md-2">radioBoxValue(At least checked 1)</div>
                                            <div class="col-md-2">
                                            <form:radiobuttons path="radioBoxValue"  cssClass="form-control" items="${demoModelData.radioBoxEntries}" itemValue="value" itemLabel="key"/>
                                            </div>                                                                                 
                                        </div>
                                        <div class="row">
                                           <div class="col-md-2">selectedValue(Required)</div>
                                            <div class="col-md-2">
                                              <form:select path="selectedValue" cssClass="form-control">  
										            <form:option value="">Please Select</form:option>
										            <form:options items="${demoModelData.selectBoxEntries}" itemValue="value" itemLabel="key"/>
										        </form:select>                                            
                                            </div>                                                                                 
                                        </div>
                                        <div class="row">
                                           <div class="col-md-2">checkedValues(At least checked 1)</div>
                                            <div class="col-md-2">
                                            <form:checkboxes path="checkedValues"  cssClass="form-control" items="${demoModelData.checkBoxEntries1}" itemValue="value" itemLabel="key"/>
                                            </div>                                                                                 
                                        </div>
                                        <div class="row">
                                           <div class="col-md-2">checkedValues2(Min: 2, Max: 4)</div>
                                            <div class="col-md-2">
                                            <form:checkboxes path="checkedValues2"  cssClass="form-control" items="${demoModelData.checkBoxEntries2}"/>
                                            </div>                                                                                 
                                        </div>                                                                                                                                                                                                                                                
                                        <div class="row">                                                                               
                                            <div class="col-md-4">
                                                <button type="submit" class="btn btn-default" onclick="submitForm(this.form);">Submit</button>
                                            </div>
                                        </div>
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
function submitForm(form) {
   form.submit();
}

</script>
<%@ include file="/common/footer1.jsp"%>