angular
	.module('employeeTracker')
	.controller('TaskController',['$rootScope','$cookies','$scope', '$state','TaskDataOp','Access',
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
						
						if(data.principal.user.role.role != 'ADMIN'){
							$state.go('home');
						}
					}
					
					
				})
				.catch(function(error){
					$cookies.remove('tracker-token');
					Access.user(undefined);
					$state.go('landing');
				});
			
		
		}
		
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
		
		$scope.search = function(){
			
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