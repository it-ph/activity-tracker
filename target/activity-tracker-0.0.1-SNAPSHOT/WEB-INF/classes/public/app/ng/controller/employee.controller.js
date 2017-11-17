angular
	.module('employeeTracker')
	.controller('EmployeeController',['$rootScope', '$cookies','$scope', '$state','EmployeeDataOp','Access',
		function($rootScope, $cookies,$scope, $state,EmployeeDataOp,Access){
		$scope.newItem ={};
		$scope.selectedItem ={};
		$scope.itemList =[];
		$scope.filteredList =[];
		$scope.item ={};
		$scope.keyword ='';
		$scope.totalItems = null;
		$scope.currentPage = 1;
		$scope.maxSize = 5;
		$scope.numPerPage = 25;
		$scope.search_keyword ='';
		
		$scope.numPerPageCap = [10,25,50,100];
		
		$scope.pageCap = $scope.numPerPageCap[0];
		$scope.numPerPage = $scope.numPerPageCap[0];
		
		
		$scope.error ='';
		$scope.success_msg ='';
		
		$scope.sortType = 'employeeNumber';
		$scope.sortReverse = false;
	
		
		$scope.toggleSort = function(sortType){
			$scope.sortType = sortType;
			$scope.sortReverse = !$scope.sortReverse;
		}
		
		token  = $cookies.get('tracker-token');
		
		if(token == undefined){
			
			$state.go('landing');
			
		}else{
			Access
				.getClaimsFromToken(token)
				.then(function(response){
					
					data = response.data;
					
					if(data.authenticated){
						Access.user(data.principal.user);
						
						if(data.principal.user.role.role !='ADMIN'){
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
		
		$scope.setSelectedItem  = function(item){
			$scope.selectedItem = item;
			console.log(item);
		}
		
		$scope.addEmployee = function(){
			EmployeeDataOp
				.addEmployee($scope.newItem)
				.then(function(response){
					$scope.success_message = 'Employee added succesfully!';
					
					$('#add-employee').modal('hide');
					
					$('#successModal').modal('show');
					
    				setTimeout(function(){
    				    $('#successModal').modal('hide');
    				}, 3000);
    				
    				loadData();
				})
				.catch(function(error){
					console.log(error);
					$scope.error = error.data.message;
				});
		}

		$scope.editEmployee = function(){
			EmployeeDataOp
				.editEmployee($scope.selectedItem)
				.then(function(response){
					$scope.success_message = 'Employee updated succesfully!';
					
					$('#edit-employee').modal('hide');
					
					$('#successModal').modal('show');
					
    				setTimeout(function(){
    				    $('#successModal').modal('hide');
    				}, 3000);
    				
    				loadData();
				})
				.catch(function(error){
					console.log(error);
					$scope.error = error.data.message;
				});
		}
		
		function refresh(){
			$scope.totalItems = $scope.itemList.length;
			var begin = (($scope.currentPage - 1) * $scope.numPerPage);
			var end = begin + $scope.numPerPage;
			$scope.filteredList = $scope.itemList.slice(begin, end);
		
		}
		
		function loadData(){
			
			EmployeeDataOp
				.getEmployeeList()
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
		
		$('#edit-employee').on('hidden.bs.modal',function(){
			//alert('hidden');
		});
		
	}]); 