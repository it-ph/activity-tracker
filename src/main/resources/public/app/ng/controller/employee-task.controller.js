angular
	.module('employeeTracker')
	.controller('EmployeeTaskController',['$rootScope','$cookies','$scope', '$state','$stomp','TaskDataOp','Access',
		function($rootScope,$cookies,$scope, $state,$stomp,TaskDataOp,Access){
		
		
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
		
		
		$scope.emitEvent = function(task,obj){
			
			$scope.$emit(task,obj);
		}
		

		function loadData(){
			TaskDataOp
				.getMyTaskList()
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
				.endEmployeeTask(item)
				.then(function(response){
					
					$scope.success_message = 'Task ended!';
					
					$('#add-task').modal('hide');
					
					$('#successModal').modal('show');
					
    				setTimeout(function(){
    				    $('#successModal').modal('hide');
    				}, 3000);

    				//console.log('emitting end task event');
    				$scope.$emit('endTaskEmit',$scope.itemList[0]);
    				
    				loadData();
    				//refresh();
    				//console.log($scope.itemList[0]);
    				
//    				$stomp.send('/app/user-status',$scope.itemList[0] , {
//    		          priority: 9,
//    		          custom: 42 // Custom Headers 
//    		        });
    				
//    				$stomp.send('/app/user-status', $scope.itemList[0], {
//    		          priority: 9,
//    		          custom: 42 // Custom Headers 
//    		        });
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
				.addEmployeeTask($scope.selectedTask)
				.then(function(response){
					
					$scope.success_message = 'New task started!';
					
					$('#add-task').modal('hide');
					
					$('#successModal').modal('show');
					
    				setTimeout(function(){
    				    $('#successModal').modal('hide');
    				}, 3000);
    				
    				loadData();
					//console.log(response);
    			//	refresh();
    				console.log($scope.itemList[0]);
    				
    				
//    				$stomp.send('/app/user-status', $scope.itemList[0], {
//    		          priority: 9,
//    		          custom: 42 // Custom Headers 
//    		        });
    				
    				//$scope.emitEvent('addTask',$scope.itemList[0]);
    				console.log('emitting addTask event');
    				$scope.$emit('addTaskEmit',$scope.itemList[0]);
					
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