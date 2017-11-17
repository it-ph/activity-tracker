angular
	.module('employeeTracker')
	.controller('TaskController',['$rootScope','$cookies','$scope', '$state','$filter','TaskDataOp','Access',
		function($rootScope,$cookies,$scope, $state,$filter,TaskDataOp,Access){
		

	
		
		$scope.itemList =[];
		$scope.filteredList =[];
		$scope.item ={};
		$scope.newItem = {};
		$scope.selectedItem ={};
		
		$scope.totalItems = null;
		$scope.currentPage = 1;
		$scope.maxSize = 5;
		$scope.numPerPage = 10;
		$scope.search_keyword ='';
		
		$scope.error ='';
		$scope.success_msg ='';
		
		
		$scope.numPerPageCap = [10,25,50,100];
		$scope.pageCap = $scope.numPerPageCap[0];
		$scope.numPerPage = $scope.numPerPageCap[0];
		
		
		$scope.changeNumPerPage = function(){
			$scope.numPerPage = $scope.pageCap;
			refresh();
		}
		
		
		
		
		loadData();
		
		$scope.searchItem = function(){
			if($scope.search_keyword == ''){
				refresh();
			}else
			{
				$scope.searchList = $filter('filter')($scope.itemList,$scope.search_keyword);
				
				$scope.totalItems = $scope.searchList.length;
				
				var begin = (($scope.currentPage - 1) * $scope.numPerPage);
				var end = begin + $scope.numPerPage;
				$scope.filteredList = $scope.searchList.slice(begin, end);
				
			}
		};
		
		
		$scope.setPage = function (pageNo) {
			$scope.currentPage = pageNo;
		};
	
		$scope.pageChanged = function() {
	
			 var begin = (($scope.currentPage - 1) * $scope.numPerPage);
			 var end = begin + $scope.numPerPage;
			 $scope.filteredList = $scope.itemList.slice(begin, end);
	
		};
		
		$scope.setSelectedItem = function(item){
			$scope.selectedItem = item;
		}
		
		$scope.addTask = function(){
			TaskDataOp
				.addTask($scope.newItem)
				.then(function(response){
					
					$scope.success_message = 'Task added succesfully!';
					
					$('#add-task').modal('hide');
					
					$('#successModal').modal('show');
					
    				setTimeout(function(){
    				    $('#successModal').modal('hide');
    				}, 3000);
    				
    				$scope.error ='';
    				loadData();
				})
				.catch(function(error){
					console.log(error);
					$scope.error = error.data.message;
				})
		}
		
		$scope.editTask = function(){
			TaskDataOp
				.updateTask($scope.selectedItem)
				.then(function(response){
					
					$scope.success_message = 'Task updated succesfully!';
					
					$('#edit-task').modal('hide');
					
					$('#successModal').modal('show');
					
    				setTimeout(function(){
    				    $('#successModal').modal('hide');
    				}, 3000);
    				 
    				$scope.error ='';
    				loadData();
				})
				.catch(function(error){
					console.log(error);
					$scope.error = error.data.message;
				})
		}
		
		function loadData(){
			
			TaskDataOp
				.getTaskList()
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