angular
	.module('employeeTracker')
	.controller('DashboardController',['$rootScope','$cookies','$scope', '$state','$stomp','TaskDataOp','Access','SocketService',
		function($rootScope,$cookies,$scope, $state,$stomp,TaskDataOp,Access,SocketService){

		
		Access.showNav(true);
				
		//pagination variables
		$scope.itemList =[];
		$scope.filteredList =[];
		$scope.item ={};
		
		$scope.totalItems = null;
		$scope.currentPage = 1;
		$scope.maxSize = 5;
		$scope.numPerPage = 10;
		$scope.search_keyword ='';
		
		$scope.error ='';
		$scope.success_msg ='';
		
		$scope.stompClient = null;
	
		$scope.isAdmin = function(){
			if(Access.getUser() == undefined)
				return false
			else return Access.getUser().role.role == 'ADMIN';
		}
		
		$scope.isSupervisor = function(){
			if(Access.getUser() == undefined)
				return false
			else return Access.getUser().role.role == 'SUPERVISOR';
		}
		
		$scope.isUser = function(){
			if(Access.getUser() == undefined)
				return false
			else return Access.getUser().role.role == 'USER';
		}
		
		$scope.isAuthenticated = function(){
			return Access.isAuthenticated();
		}
		
		$scope.setPage = function (pageNo) {
			$scope.currentPage = pageNo;
		};
	
	
	  if(!Access.isAuthenticated()){
		  $state.go('login');
	  }
		$scope.pageChanged = function() {
	
			 var begin = (($scope.currentPage - 1) * $scope.numPerPage);
			 var end = begin + $scope.numPerPage;
			 $scope.filteredList = $scope.itemList.slice(begin, end);
	
		};
		
		
	
	
		
		
	}]); 