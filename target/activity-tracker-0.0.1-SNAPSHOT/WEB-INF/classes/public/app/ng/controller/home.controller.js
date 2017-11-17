angular
	.module('employeeTracker')
	.controller('DashboardController',['$rootScope','$cookies','$scope', '$state','TaskDataOp','Access',
		function($rootScope,$cookies,$scope, $state,TaskDataOp,Access){
		
		Access.showNav(true);
		
		token = $cookies.get('tracker-token');
		
		if(token == undefined){
			
			$state.go('landing');
			
		}else{
			Access
				.getClaimsFromToken(token)
				.then(function(response){
					
					data = response.data;
					
					if(data.authenticated){
						Access.user(data.principal.user);
					}
				})
				.catch(function(error){
					$cookies.remove('tracker-token');
					Acess.user(undefined);
					$state.go('landing');
				})
			
		}
		
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
	
	
		$scope.pageChanged = function() {
	
			 var begin = (($scope.currentPage - 1) * $scope.numPerPage);
			 var end = begin + $scope.numPerPage;
			 $scope.filteredList = $scope.itemList.slice(begin, end);
	
		};
		
		
		
		
		
		
	}]); 