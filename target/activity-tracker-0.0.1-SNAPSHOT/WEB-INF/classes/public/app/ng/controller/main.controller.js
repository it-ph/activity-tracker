angular
	.module('employeeTracker')
	.controller('MainController',['$rootScope', '$scope', '$state','Access',
		function($rootScope, $scope, $state,Access){
		$scope.canShowNav = function(){
			visible = true;
			
			switch($state.current.name){
				case '':
				case 'landing':
				case 'login':
					  visible = false;
					  break;
				default: visible = true;
			}
			
			return visible;
		}
		
	
		$scope.isAuthenticated = function(){
			return Access.isAuthenticated();
		}
	}]); 