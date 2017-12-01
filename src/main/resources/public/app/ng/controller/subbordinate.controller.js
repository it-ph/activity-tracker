angular
	.module('employeeTracker')
	.controller('SubbordinateController',['$rootScope','$cookies','$scope', '$state','$stomp','GroupDataOp','Access',
		function($rootScope,$cookies,$scope, $state,$stomp,GroupDataOp,Access){
		
		
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
		
		
		$scope.$on('taskUpdate',function(event,data){

			//console.log('Group update broadcast received subbordinate controller');
			loadTask();
		});
		
		$scope.$on('groupUpdateBroadcast',function(event,data){

			//console.log('Group update broadcast received subbordinate controller');
			loadTask();
		});
		
		$scope.$on('groupUpdate',function(event,data){
			//console.log('Group update broadcast received subordinate controller');
			loadTask();
		});
		
	
		 function loadTask(){
			
			GroupDataOp
				.getSubordinateTaskList()
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
		
		loadTask();
	}]); 