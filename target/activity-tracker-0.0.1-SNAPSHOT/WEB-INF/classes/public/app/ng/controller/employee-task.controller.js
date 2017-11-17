angular
	.module('employeeTracker')
	.controller('EmployeeTaskController',['$rootScope','$cookies','$scope', '$state','TaskDataOp','Access',
		function($rootScope,$cookies,$scope, $state,TaskDataOp,Access){
		
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
		
		
		loadData();
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
		
		
		function loadData(){
			TaskDataOp
				.getMyTaskList(token)
				.then(function(response){
					
					$scope.itemList = response.data;
					
					if($scope.itemList.length > 0){
						if($scope.itemList[0].endDate == null){
							$('#add-task-btn').prop('disabled',true);
						}else{
							$('#add-task-btn').prop('disabled',false);
						}
					}
					
					$scope.totalItems = $scope.itemList.length;
		
					var begin = (($scope.currentPage - 1) * $scope.numPerPage);
					var end = begin + $scope.numPerPage;
					$scope.filteredList = $scope.itemList.slice(begin, end);
				})
				.catch(function(error){
					console.log(error);
				});
				
		}
	
		
		//end task
		$scope.endTask = function(item){
			TaskDataOp
				.endEmployeeTask(token,item)
				.then(function(response){
					
					$scope.success_message = 'Task ended!';
					
					$('#add-task').modal('hide');
					
					$('#successModal').modal('show');
					
    				setTimeout(function(){
    				    $('#successModal').modal('hide');
    				}, 3000);
    				
    				loadData();
				})
				.catch(function(error){
					console.log(error);
				});
		}
		
		
		//task related variables
		$scope.taskList =[];
		$scope.selectedTask ={};
	
	
		$scope.loadTask = function(){
			
			TaskDataOp
				.getTaskList()
				.then(function(response){
					//console.log(response);
					$scope.taskList = response.data;
					$scope.selectedTask = $scope.taskList.length > 0 ?  $scope.taskList[0]: {};				
					
				})
				.catch(function(error){
					console.log(error);
				});
				
		}
		$scope.loadTask();
		
		
		$scope.addTask = function(){
		
			TaskDataOp
				.addEmployeeTask(token,$scope.selectedTask)
				.then(function(response){
					
					$scope.success_message = 'New task started!';
					
					$('#add-task').modal('hide');
					
					$('#successModal').modal('show');
					
    				setTimeout(function(){
    				    $('#successModal').modal('hide');
    				}, 3000);
    				
    				loadData();
					console.log(response);
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