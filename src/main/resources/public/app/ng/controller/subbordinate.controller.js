angular
	.module('employeeTracker')
	.controller('SubbordinateController',['$rootScope','$cookies','$scope','$filter', '$state','$stomp','GroupDataOp','Access','TaskDataOp',
		function($rootScope,$cookies,$scope,$filter, $state,$stomp,GroupDataOp,Access,TaskDataOp){
		
		
		$scope.success_message = '';		
		$scope.itemList =[];
		$scope.filteredList =[];
		$scope.item ={};
		
		$scope.totalItems = null;
		$scope.currentPage = 1;
		$scope.maxSize = 5;
		$scope.numPerPage = 10;
		$scope.search_keyword =undefined;
		
		$scope.numPerPageCap = [10,25,50,100];
		
		$scope.pageCap = $scope.numPerPageCap[0];
		$scope.numPerPage = $scope.numPerPageCap[0];
		$scope.userTaskHistory =[];
		
		$scope.error ='';
		$scope.success_msg ='';
		
		
		$scope.sortType = 'employeeNumber';
		$scope.sortReverse = false;
	
		
		$scope.toggleSort = function(sortType){
			$scope.sortType = sortType;
			$scope.sortReverse = !$scope.sortReverse;
		}
		
		
	
		Access.showNav(true);
		
		
		$scope.options ={
				data: [],
				columns:['Employee Number','Full Name','Task','Start','End','Duration']
		}
		
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

			//console.log('Task update broadcast received subbordinate controller');
			loadTask();
			//console.log(data);
			
			$scope.getIndexes(data.employee.employeeNumber); 
		});
		
		$scope.refreshData = function(empNumber){
		}
		$scope.$on('groupUpdateBroadcast',function(event,data){

			//console.log('Group update broadcast received subbordinate controller');
			//loadTask();
			loadTask();
		});
		
		$scope.$on('groupUpdate',function(event,data){
			//console.log('Group update broadcast received subordinate controller');
			loadTask();
		});
		
		$scope.searchMember = function(group){
			
			memberTasks = group.memberTasks;
			console.log(group);
			
			result = $filter('filter')(memberTasks,group.search);
			
			console.log(result);
//			
			group.data = result;
			group.totalItems = result.length;
			group.refresh();
		}
		
		$scope.sortReverse = true;
		
		$scope.sortGroup = function(group,col){
			
		
			var sorted =[];
			
			var memberTasks = group.filteredList;
			
			switch(col){
			
				case 1: sorted = $filter('orderBy')(memberTasks,'employee.employeeNumber',group.numSort);
						group.numSort = !group.numSort;
						break;
						
				case 2: sorted = $filter('orderBy')(memberTasks,'employee.firstName',group.nameSort);
						group.nameSort = !group.nameSort;
						break;

				case 3: sorted = $filter('orderBy')(memberTasks,'task.taskName',group.taskSort);
						group.taskSort = !group.taskSort;
						break;
				
				case 4: sorted = $filter('orderBy')(memberTasks,'startDate',group.startSort);
						group.startSort = !group.startSort;
						break; 
						
				case 5: sorted = $filter('orderBy')(memberTasks,'endDate',group.endSort);
						group.endSort = !group.endSort;
						break; 
						
				case 6: sorted = $filter('orderBy')(memberTasks,'duration',group.durationSort);
						group.durationSort = !group.durationSort;
						break; 
			}
			group.filteredList =sorted;
		 }
		
		 function loadTask(){
			
			GroupDataOp
				.getSubordinateTaskList()
				.then(function(response){
					
					$scope.itemList = response.data;
					
					
				    $scope.itemList.forEach(function(group){
				    	
				    	 group.numSort = false;
				    	 group.nameSort = false;
				    	 group.taskSort = false;
				    	 group.startSort = false;
				    	 group.endSort = false;
				    	 group.durationSort =false;
				    	 group.filteredList = group.memberTasks;
				    	 group.search = undefined;
			             group.currentPage = 1;
			             group.maxSize = 6;
			             group.numPerPage = 10;
			             group.totalItems = group.memberTasks.length;
			             group.data = group.memberTasks;
			             
			             group.pageChanged = function(){
			             
			             
			            	 var begin = ((group.currentPage - 1) * group.numPerPage);
			    			 var end = begin + group.numPerPage;
			    			 
			    			 group.filteredList = group.data.slice(begin, end);
			    			 
			             }
			             group.refresh = function(){
			            	 var b = ((group.currentPage - 1) * group.numPerPage);
							 var e= b + group.numPerPage;
							 group.filteredList = group.data.slice(b, e);
							 
			             }
				        
			             group.memberTasks.forEach(function(obj){
			            	 
//				             obj.startDate = new Date(moment.utc(obj.startDate).format("MMM DD YYYY h:mm:ss a"));
//				             obj.endDate = null == obj.endDate ? null : new Date(moment.utc(obj.endDate).format("MMM DD YYYY h:mm:ss a"));
//				             
				             obj.duration = $filter('durationToTime')((obj.endDate - obj.startDate),obj.endDate);
				             
				          });
			             
			             group.refresh();
				          
				         
				      });
				    
				    $(function(){
						$('th>i').click(function(){
							$(this).toggleClass('fa-caret-down');
							$(this).toggleClass('fa-caret-up');
							
						})
					})
				})
				.catch(function(error){
					console.log(error);
				});
				
			
		}
	
		$scope.getIndexes = function(empNumber){
			
			var indexes=[];
			
			for(i=0; i<$scope.itemList.length; i++){
				
				
				for(x=0; x<$scope.itemList[i].memberTasks.length; x++){
					
					//console.log($scope.itemList[i].memberTasks[x]);
					
					if($scope.itemList[i].memberTasks[x].employee.employeeNumber == empNumber){
						//employee = $scope.itemList[i].memberTasks[x].employee;
						
						indexes.push([i,x]);
						
						break;
					}
					
				}
			}
			
			
			TaskDataOp
				.getEmployeeTask(empNumber)
				.then(function(r){
					console.log(r)

					r.data.duration = $filter('durationToTime')((r.data.endDate - r.data.startDate),r.data.endDate);
					
					indexes.forEach(function(obj){
						$scope.itemList[obj[0]].filteredList[obj[1]] = angular.copy(r.data);
						
					});
					
				})
				.catch(function(e){
					console.log(e);
				})
		}
		

		function refresh(){
			$scope.totalItems = $scope.itemList.length;
			var begin = (($scope.currentPage - 1) * $scope.numPerPage);
			var end = begin + $scope.numPerPage;
			$scope.filteredList = $scope.itemList.slice(begin, end);
		
		}
		
		loadTask();
		
		
	}]); 