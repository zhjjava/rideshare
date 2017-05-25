<!DOCTYPE html>
<html lang="en" ng-app="rsApp">
<%@ include file="/common/header1.jsp"%>

<c:if test="${sessionSysUser == null}">
	<c:redirect url="/${ctx}/login.do" />
</c:if>

    <!-- Fixed navbar -->
    <nav class="navbar navbar-default navbar-fixed-top">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="#">RS App</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
          <ul class="nav navbar-nav">
            <li class="active"><a href="#/">Ride Share</a></li>
            <li><a href="#/WeatherService">Weather Service</a></li>
            <li><a href="#/MapService">Map Service</a></li>
          </ul>

        </div>
      </div>
    </nav>

    <div class="container">     
           <div ng-view></div>
    </div> <!-- /container -->
    
<%@ include file="/common/footer1.jsp"%>
