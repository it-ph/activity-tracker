angular
	.module('employeeTracker')
	.controller('LandingPageController',['$rootScope','$cookies', '$scope', '$state','Access',
		function($rootScope,$cookies, $scope, $state,Access){
		
		$scope.user = Access.getUser();		
		
		$scope.isAuthenticated = function(){
			return Access.isAuthenticated();
		}
		
	
			
		$scope.logout = function(){
			Access.user(undefined);
			$cookies.remove('tracker-token');
			$state.go('landing');
		}
	}]); 