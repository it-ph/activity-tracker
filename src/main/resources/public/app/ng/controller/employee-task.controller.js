angular
	.module('employeeTracker')
	.controller('EmployeeTaskController',['$rootScope','$cookies','$scope', '$state','$stomp','$filter','TaskDataOp','Access',
		function($rootScope,$cookies,$scope, $state,$stomp,$filter,TaskDataOp,Access){
		
		
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
		
		$scope.taskPreferrences =[];
		
		$scope.error ='';
		$scope.success_msg ='';
		$scope.preferrences =[];
		$scope.taskWithRemarks = undefined;
		$scope.loading = false;

		Access.showNav(true);
		
		$scope.items ={
				
				canAddTask: false,
				itemList: [],
				orig_list: [],
				filteredList: [],
				taskList: [],
				taskPreferrences: [],
				taskSelection: [],
				totalItems: undefined,
				currentPage: 1,
				maxSize: 5,
				numPerPage: 10,
				search_keyword: undefined,
				numPerPageCap: [10,25,50,100],
				prefHasLoaded: false,
				
				pageChanged: function(){
					
					 var begin = ((this.currentPage - 1) * this.numPerPage);
					 var end = begin + this.numPerPage;
					 this.filteredList = this.orig_list.slice(begin, end);
				},
				
				
				changeNumPerPage: function(){

					this.totalItems = this.orig_list.length;
					begin = ((this.currentPage - 1) * this.numPerPage);
					end = begin + this.numPerPage;
					
					this.filteredList = this.orig_list.slice(begin, end);
				},
				
				searchTask: function(){
				
					this.itemList = $filter('filter')(this.orig_list,this.search_keyword);
					
					
					console.log(this.itemList);
					
//					this.totalItems = this.itemList.length;
//					begin = ((this.currentPage - 1) * this.numPerPage);
//					end = begin + this.numPerPage;					
//					this.filteredList = this.orig_list.slice(begin, end);
				},
				
				refresh: function(){
					
					this.totalItems = this.itemList.length;
					var begin = ((this.currentPage - 1) * this.numPerPage);
					var end = begin + this.numPerPage;
					this.filteredList = this.itemList.slice(begin, end);
					
				},
				
				loadTaskList: function(onloadFinished){
					TaskDataOp
					 .getMyTaskList()
					 .then(function(r){
						 
//						r.data.forEach(function(obj){
//				            obj.startDate = new Date(moment.utc(obj.startDate).format("MMM DD YYYY h:mm:ss a"));
//				            obj.endDate = obj.endDate? new Date(moment.utc(obj.endDate).format("MMM DD YYYY h:mm:ss a")) : null;
//					    });
 
					 	$scope.items.itemList = r.data;
					 	$scope.items.orig_list = r.data;
					 	
					 	if($scope.items.orig_list.length > 0){
					 		$scope.items.canAddTask = ($scope.items.orig_list[0].endDate != null);
					 	}else{
					 		$scope.items.canAddTask = true;
					 	}
						
						begin = (($scope.items.currentPage - 1) * $scope.items.numPerPage);
						end = begin + $scope.items.numPerPage;
						$scope.items.filteredList = $scope.items.orig_list.slice(begin, end);
						
						$scope.items.totalItems = $scope.items.orig_list.length;
						
						 
							
						onloadFinished();
						
					 })
					 .catch(function(e){
						 console.log(e)
					 })
				},
				
				loadPreferrences: function(onloadFinished){
					
					
					TaskDataOp
					 .getTaskPreferrences()
					 .then(function(r){
						 
						 $scope.items.taskPreferrences = r.data;
						 $scope.items.taskSelection = (r.data.length >0) ? r.data : $scope.items.taskList;
						 
						 onloadFinished();
					 })
					 .catch(function(e){
						 console.log(e)
					 })
				},
				
				saveRemarks: function(){
					
					TaskDataOp
					  .addRemarks($scope.taskWithRemarks)
					  .then(function(r){
						  console.log(r)
						  $('#remarks').modal('hide');	
							
							$scope.success_msg = 'Remarks added succesfully!';
							
							$('#successModal').modal('show');

							$scope.items.loadTaskList(function(){});

		    				$scope.$emit('updateTaskEmit',$scope.taskWithRemarks);
		    				
							setTimeout(function(){
							    $('#successModal').modal('hide');
							}, 3000);
							
							
						  
					  })
					  .catch(function(e){
						  console.log(e)
					  })
					
				},
				
				newTask: function(){
					$('#add-task').modal('show');
				},
				addTask: function(){
					
					
					if($scope.selectedTask){
						
						TaskDataOp
						.addEmployeeTask($scope.selectedTask)
						.then(function(r){

							
		    				$scope.$emit('addTaskEmit', $scope.items.itemList[0]);
		    				
		    				$scope.success_msg ='Task added succesfully!';
							$scope.selectedTask = undefined;
							$scope.error = undefined;
							
		    				$scope.items.loadTaskList(function(){
								
								$('#add-task').modal('hide');								
								$('#successModal').modal('show');	
								
			    				setTimeout(function(){
			    				    $('#successModal').modal('hide');
			    				}, 3000);
			    				
							});
							
						})
						.catch(function(e){
							
						})
					}else{
						$scope.error ='Please select a task';
					}
				
						
				},

				endTask: function(item){
					
					TaskDataOp
					.endEmployeeTask(item)
					.then(function(r){
						
						$scope.error = undefined;
						$scope.success_msg='Task ended succesfully';

	    				
						$scope.items.loadTaskList(function(){});

	    				$scope.$emit('endTaskEmit',$scope.items.itemList[0]);

						$('#successModal').modal('show');
						setTimeout(function(){
	    				    $('#successModal').modal('hide');
	    				    $success_msg = undefined;
	    				}, 3000);
	    				
						
					})
					.catch(function(e){
						console.log(e);
						$scope.error ='Something went wrong';
					});
				}
				
				
			
		};
		
		$scope.items.loadTaskList(function(){});
		$scope.items.loadPreferrences(function(){
			$scope.loading = false;
		});
		
		
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
		
		
		$scope.canAddTask = false;
		
//		function loadData(){
//			TaskDataOp
//				.getMyTaskList()
//				.then(function(response){
//					
//				
//					$scope.itemList = response.data;
//					
//					
//					if($scope.itemList.length > 0){
//						if($scope.itemList[0].endDate == null){
//							$('#add-task-btn').prop('disabled',true);
//							$scope.canAddTask = false;
//						}else{
//							$scope.canAddTask = true;
//							$('#add-task-btn').prop('disabled',false);
//						}
//					}
//					
//				
//					
//					$scope.totalItems = $scope.itemList.length;
//		
//					var begin = (($scope.currentPage - 1) * $scope.numPerPage);
//					var end = begin + $scope.numPerPage;
//					$scope.filteredList = $scope.itemList.slice(begin, end);
//				})
//				.catch(function(error){
//					console.log(error);
//				});
//				
//		}
	
		
		//end task
		$scope.endTask = function(item){
			item.startDate =null;
			item.endDate = null;
			
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
				})
				.catch(function(error){
					console.log(error);
				});
		}
		
		
		//task related variables
		$scope.taskList =[];
		$scope.selectedTask =undefined;
	
	
		$scope.loadTask = function(){
			
			TaskDataOp
				.getTaskList()
				.then(function(response){
					
					$scope.taskList = ($scope.preferrences.length >0)? $scope.preferrences : response.data;

				})
				.catch(function(error){
					console.log(error);
				});
				
		}
		$scope.loadTask();
		
		$scope.addRemarks = function(task){
			
			$scope.taskWithRemarks = task;
			$('#remarks').modal('show');
		
		}
		
		$scope.saveRemarks = function(){
			
			console.log($scope.taskWithRemarks);
			TaskDataOp
			  .addRemarks($scope.taskWithRemarks)
			  .then(function(r){
				  console.log(r)
				  $('#remarks').modal('hide');	
					
					$scope.success_message = 'Remarks added succesfully!';
					
					$('#successModal').modal('show');
					
					setTimeout(function(){
					    $('#successModal').modal('hide');
					}, 3000);
					
					loadData();
				  
			  })
			  .catch(function(e){
				  console.log(e)
			  })
		}
		
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
    				//console.log($scope.itemList[0]);
    				
    				
//    				$stomp.send('/app/user-status', $scope.itemList[0], {
//    		          priority: 9,
//    		          custom: 42 // Custom Headers 
//    		        });
    				
    				//$scope.emitEvent('addTask',$scope.itemList[0]);
    				//$scope.$emit('addTaskEmit',$scope.itemList[0]);
					
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