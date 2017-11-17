angular
	.module('employeeTracker')
	.controller('NavController',['$rootScope','$cookies', '$scope', '$state','Access',
		function($rootScope,$cookies, $scope, $state,Access){

		$scope.user ={};
		$scope.error ='';
		
		$scope.logout = function(){
			Access.user(undefined);
			$cookies.remove('tracker-token');
			$state.go('login');
		}
		
		$scope.isAdmin = function(){
			if(Access.getUser() == undefined)
				return false
			else return Access.getUser().role.role == 'ADMIN';
		}
		
		$scope.isUser = function(){
			if(Access.getUser() == undefined)
				return false
			else return Access.getUser().role.role == 'USER';
		}
		$scope.isAuthenticated = function(){
			return Access.isAuthenticated();
		}
	}]);