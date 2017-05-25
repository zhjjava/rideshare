<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/header1.jsp"%>
<%
edu.mum.rideshare.util.displaytag.CheckboxTableDecorator decorator = new edu.mum.rideshare.util.displaytag.CheckboxTableDecorator();
decorator.setId("sysCodeId");
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
                    <h3>Sys Code list</h3>
                    <div class="resBtnSearch">
                        <a href="#"><span class="icon16 icomoon-icon-search-3"></span></a>
                    </div>
                    <div class="search">
                    </div>
                    <!-- End search -->
                </div>
                
    <div class="container">

        <div >
            <form id="form1" name="form1" method="post" action="preCallAjax.do" class="form-horizontal">                
                <div class="form-group">
                    <label class="col-md-12 control-label" for="data1">Field1:</label>
                    <div class="col-md-12">
                        
                         <input id="data1" type="text" name="data1" class="form-control"  size="25"  placeholder="Field1"/> 
                        <span class="icon16 icomoon-icon-user right gray marginR10"></span>
                    </div>
                </div><!-- End .form-group  -->
                <div class="form-group">
                    <label class="col-md-12 control-label" for="data2">Field2:</label>
                    <div class="col-md-12">
                        <input id="data2" type="text" name="data2" value="" class="form-control" placeholder="Field2">
                        <span class="icon16 icomoon-icon-user right gray marginR10"></span>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-12 control-label" for="data3">Field3:</label>
                    <div class="col-md-12">
                        <input id="data3_1" type="text" name="data3" value="" class="form-control" placeholder="Field3">
                        <span class="icon16 icomoon-icon-user right gray marginR10"></span>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-12 control-label">Field3:</label>
                    <div class="col-md-12">
                        <input id="data3_2" type="text" name="data3" value="" class="form-control" placeholder="Field3">
                        <span class="icon16 icomoon-icon-user right gray marginR10"></span>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-12 control-label">Field2:</label>
                    <div class="col-md-12">
                        <input id="data3_3" type="text" name="data3" value="" class="form-control" placeholder="Field3">
                        <span class="icon16 icomoon-icon-user right gray marginR10"></span>
                    </div>
                </div>                                                
                <!-- End .form-group  -->
                <div class="form-group">
                    <div class="col-md-12 clearfix form-actions">
                        <button type="button" class="btn btn-info right" id="queryForm" onclick="callAjax();"><span class="icon16 icomoon-icon-enter white"></span>Submit</button>
                         <button class="btn btn-info btn-xs" data-toggle="modal" data-target=".bs-example-modal-lg1" onclick="preEditData(1010);"><span class="icon16 icomoon-icon-eye-2 white"></span> Edit1</button>
                         <button class="btn btn-info btn-xs" type="button"  onclick="preEditData(2020);"><span class="icon16 icomoon-icon-eye-2 white"></span> Edit2</button>
                    </div>
                </div><!-- End .form-group  -->
           </form>
        </div>

    </div>
                   
               <!-- Page end here -->
            </div>
            <!-- End contentwrapper -->
        </div>
        <!-- End #content -->
    </div>     
   <!--start modal-->
   <form id="editForm" name="editForm" method="post" action="editSomethingAjax.do" class="form-horizontal">    
    <div id="modalDiv" class="modal fade bs-example-modal-lg1" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">Title</h4>
                </div>
                <div class="modal-body" id="dataMainPart">
                    <label for="">Field1</label>
                    <input type="hidden" name="pkOfAmout" value="10">
                    <input type="text"   name="newValueOfAmout" id="newValueOfAmout_1" class="form-control uniform-input text" value="2000">
                    <label for="">Field1</label>
                    <input type="hidden" name="pkOfAmout" value="20">
                    <input type="text"   name="newValueOfAmout" class="form-control uniform-input text" value="2000">
                    <label for="">Field3</label>
                    <input type="hidden" name="pkOfAmout" value="30">
                    <input type="text"   name="newValueOfAmout" class="form-control uniform-input text" value="2000">
                    <label for="">Field4</label>
                    <input type="hidden" name="pkOfAmout" value="40">
                    <input type="text"   name="newValueOfAmout" class="form-control uniform-input text" value="2000">
                    <label for="">Field5</label>
                    <input type="hidden" name="pkOfAmout" value="50">
                    <input type="text"   name="newValueOfAmout" class="form-control uniform-input text" value="2000">
                 
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-info">Save</button>
                </div>
            </div>
        </div>
    </div>
    </form>
    <!--end modal-->                   
<script language="javascript">
//focusElement(getEl("theValue"));
function callAjax() {	  
	  var jsonReqInfo = $('#form1').serialize(); 	                    
      alert($('#form1').serialize());  
      jQuery.ajax( {  
        type : 'POST',  
        //contentType : 'application/text',  
        url : 'callAjax.do',  
        data : jsonReqInfo,  
        dataType : 'json',  
        success : function(respData) {  
          alert(respData.data1);  
        },  
        error : function(respData) {  
          alert("error")  
        }  
      });
}

$("#modalDiv").on("hidden.bs.modal", function() {  
	// alert(88);
    $(this).removeData("bs.modal");  
});      
function preEditData(value){
	$('#newValueOfAmout_1').val(value);
	//alert($('#newValueOfAmout_1').val());
	$('#modalDiv').modal();
}
</script>
<%@ include file="/common/footer1.jsp"%>