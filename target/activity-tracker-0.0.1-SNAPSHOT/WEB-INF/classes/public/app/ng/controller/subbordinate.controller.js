angular
	.module('employeeTracker')
	.controller('SubbordinateController',['$rootScope','$cookies','$scope', '$state','GroupDataOp','Access',
		function($rootScope,$cookies,$scope, $state,GroupDataOp,Access){
		
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
					console.log(data);
				})
				.catch(function(error){
					$cookies.remove('tracker-token');
					Access.user(undefined);
					$state.go('landing');
				})
		}
		

		
		$scope.success_message = '';		
		$scope.itemList =[];
		$scope.filteredList =[];
		$scope.item ={};
		
		$scope.totalItems = null;
		$scope.currentPage = 1;
		$scope.maxSize = 5;
		$scope.numPerPage = 10;
		$scope.search_keyword ='';
		
		$scope.numPerPageCap = [10,25,50,100];
		
		$scope.pageCap = $scope.numPerPageCap[0];
		$scope.numPerPage = $scope.numPerPageCap[0];
		
		
		$scope.error ='';
		$scope.success_msg ='';
		
		
		loadTask();
		Access.showNav(true);
		
		$scope.search = function(){
			
		};
		
		$scope.changeNumPerPage = function(){
			$scope.numPerPage = $scope.pageCap;
			refresh();
		}
		
		$scope.setPage = function (pageNo) {
			$scope.currentPage = pageNo;
		};
	
		$scope.pageChanged = function() {
	
			 var begin = (($scope.currentPage - 1) * $scope.numPerPage);
			 var end = begin + $scope.numPerPage;
			 $scope.filteredList = $scope.itemList.slice(begin, end);
	
		};
		
	
		 function loadTask(){
			
			GroupDataOp
				.getSubordinateTaskList(token)
				.then(function(response){
					$scope.itemList = response.data;
					$scope.totalItems = $scope.itemList.length;
					var begin = (($scope.currentPage - 1) * $scope.numPerPage);
					var end = begin + $scope.numPerPage;
					$scope.filteredList = $scope.itemList.slice(begin, end);
					
				})
				.catch(function(error){
					console.log(error);
				});
				
		}
	
		

		function refresh(){
			$scope.totalItems = $scope.itemList.length;
			var begin = (($scope.currentPage - 1) * $scope.numPerPage);
			var end = begin + $scope.numPerPage;
			$scope.filteredList = $scope.itemList.slice(begin, end);
		
		}
		
	}]); 