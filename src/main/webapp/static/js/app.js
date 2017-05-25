var myApp = angular.module('rsApp', ['ngRoute']);

myApp.config(function ($routeProvider) {
    
    $routeProvider
    
    .when('/', {
        templateUrl: '/rideshare/static/pages/rideshare.html',
        controller: 'rideShareController'
    })
    
    .when('/WeatherService', {
        templateUrl: '/rideshare/static/pages/weatherservice.html',
        controller: 'secondController'
    })
    
    .when('/MapService', {
        templateUrl: '/rideshare/static/pages/mapservice.html',
        controller: 'secondController'
    })
    
});

myApp.controller('rideShareController', ['$scope', '$log','$http', function($scope, $log, $http) {
	$scope.init = function () {
  	  $scope.queryRidePost(0);
    }
	$scope.pageNumber=1;
    $scope.pageSize=2;
    $scope.offerOrAsk=0;
    $scope.isShowingOffer=$scope.offerOrAsk==0?'active':'';
    $scope.isShowingAsk=$scope.offerOrAsk==1?'active':'';
    
    $scope.$watch('offerOrAsk', function(newValue, oldValue) {
        $scope.isShowingOffer=newValue==0?'active':'';
        $scope.isShowingAsk=newValue==1?'active':'';
    });
    $scope.totalPages=0;
    $scope.totalRecs=0;
    $scope.currentpage={};
    $scope.currentpage.data=[];
    
    $scope.prePage = function(){
    	$scope.pageNumber--;
    	$scope.queryRidePost($scope.offerOrAsk);
    };
    $scope.nextPage = function(){
    	$scope.pageNumber++;
    	$scope.queryRidePost($scope.offerOrAsk);
    }
    var config = {
    		headers : {
                'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;'
            }
        };
    $scope.queryRidePost= function(action){
    	if(action!=$scope.offerOrAsk){
    		$scope.pageNumber=1;
    	}
    	var action1=action|| 0;
    	$scope.offerOrAsk=action1;
    	//$log.log( $scope.offerOrAsk);
    	var data="pageNumber="+ $scope.pageNumber+"&pageSize="+$scope.pageSize+"&field1="+$scope.offerOrAsk;
	    $http.post("/rideshare/ridepost/listRidePost.do", data,config)
	       .then(function mySucces(response) {
	    	   $log.log(response);
	    	   var resp=response.data;
	           $scope.totalRecs = resp.pageData.totalRecs||0;
	           $scope.totalPages = resp.pageData.totalPages||0;
	           $scope.currentpage.data = resp.pageData.list||[];
	           
	           $scope.hasPrePage = $scope.pageNumber>1;
	           $scope.hasNextPage = $scope.pageNumber<$scope.totalPages;
	           
	        }, function myError(response) {
	           $scope.myWelcome = response.data.opReturnMsg;
	       });
    };
    
    $scope.init();
}]);

myApp.controller('secondController', ['$scope', '$log', '$routeParams', function($scope, $log, $routeParams) {
    
    $scope.num = $routeParams.num || 1;
    
}]);
