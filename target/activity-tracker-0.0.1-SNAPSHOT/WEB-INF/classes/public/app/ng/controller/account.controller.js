angular
	.module('employeeTracker')
	.controller('AccountController',['$rootScope','$cookies','$scope', '$state','AccountDataOp','EmployeeDataOp','Access',
		function($rootScope,$cookies,$scope, $state,AccountDataOp,EmployeeDataOp,Access){
		
		$scope.itemList =[];
		$scope.filteredList =[];
		$scope.item ={};
		$scope.newItem = {};
		$scope.selectedItem ={};
		$scope.employeeList =[];
		$scope.selectedEmployee ={};
		$scope.roleList =[];
		$scope.totalItems = null;
		$scope.currentPage = 1;
		$scope.maxSize = 5;
		$scope.numPerPage = 10;
		$scope.search_keyword ='';
		$scope.employee_search ='';
		$scope.error ='';
		$scope.success_msg ='';
		
		
		$scope.numPerPageCap = [10,25,50,100];
		$scope.pageCap = $scope.numPerPageCap[0];
		$scope.numPerPage = $scope.numPerPageCap[0];
		
		
		$scope.newItem ={
				user:{
					role:{}
				},
				employee:{}
		};
		token = $cookies.get('tracker-token');
		
		
		$scope.changeNumPerPage = function(){
			$scope.numPerPage = $scope.pageCap;
			refresh();
		}
		
		
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
		
		loadData();
		
		$scope.loadAccount = function(){
			EmployeeDataOp
				.getEmployeeList()
				.then(function(response){
					$scope.employeeList = response.data;
					//console.log($scope.employeeList);
				})
				.catch(function (error){
					console.log(error);
				});
			
			AccountDataOp
				.getRoleList()
				.then(function(response){
					$scope.roleList = response.data;
					
					$scope.newItem.user.role = ($scope.roleList.length >0) ? $scope.roleList[0]: {};
					//console.log($scope.newItem.user.role);
				})
				.catch(function(error){
					console.log(error);
				});
		}
		
		
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
		
		
		
		$scope.addAccount= function(){
			AccountDataOp
				.addAccount($scope.newItem)
				.then(function(response){
					
					$scope.success_message = 'User account added succesfully!';
					
					$('#add-account').modal('hide');
					
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
		
		$scope.updateAccount = function(){
			AccountDataOp
				.updateAccount($scope.selectedItem)
				.then(function(response){
					
					$scope.success_message = 'User account updated succesfully!';
					
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
		
		$scope.disableAccount = function(item){
			AccountDataOp
				.disableAccount(item)
				.then(function(response){
					
					$scope.success_message = 'Account disabled succesfully!';
					
					$('#successModal').modal('show');
					
    				setTimeout(function(){
    				    $('#successModal').modal('hide');
    				}, 3000);
    				 
    				$scope.error ='';
    				loadData();
				})
				.catch(function(error){
					$scope.error = error.data.message;
				});
			
		}
		
		$scope.enableAccount = function(item){
			AccountDataOp
				.enableAccount(item)
				.then(function(response){
					
					$scope.success_message = 'Account enabled succesfully!';
					
					$('#successModal').modal('show');
					
    				setTimeout(function(){
    				    $('#successModal').modal('hide');
    				}, 3000);
    				 
    				$scope.error ='';
    				loadData();
				})
				.catch(function(error){
					$scope.error = error.data.message;
				});
			
		}
		
		$scope.resetAccount = function(item){
			AccountDataOp
				.resetAccount(item)
				.then(function(response){
					
					$scope.success_message = 'Password reset succesfully!';
					
					$('#successModal').modal('show');
					
    				setTimeout(function(){
    				    $('#successModal').modal('hide');
    				}, 3000);
    				 
    				$scope.error ='';
    				loadData();
				})
				.catch(function(error){
					$scope.error = error.data.message;
				});
			
		}
		
		function loadData(){
			
			AccountDataOp
				.getAccountList()
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