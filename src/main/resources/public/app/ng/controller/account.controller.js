angular
	.module('employeeTracker')
	.controller('AccountController',['$rootScope','$cookies','$scope', '$state','$filter','AccountDataOp','EmployeeDataOp','Access',
		function($rootScope,$cookies,$scope, $state,$filter,AccountDataOp,EmployeeDataOp,Access){
		
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
		
		
		$scope.newRole = {
				user:{
					role:{}
				},
		};
		
		$scope.changeNumPerPage = function(){
			$scope.numPerPage = $scope.pageCap;
			refresh();
		}
		
	
		loadData();
		
		$scope.loadAccount = function(){
			
			$scope.error = '';
			
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
		
		$scope.showModal = function(){
			$scope.error ='';
		}
		
		$scope.hideModal = function(){
			$scope.error = '';
		}
		
		
		$scope.addAccount= function(){
			$scope.error ='';
			
			if(angular.equals({},$scope.newItem.employee)){
				
				$scope.error ="Please select an employee";
			}else{
				
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
    				
				})
				.catch(function(error){
					console.log(error);
					$scope.error = error.data.message;
				})
			}
			
		}
		$scope.showChangeRoleModal = function(acc){
			$scope.newRole = acc;
			//$scope.newRole.user.role =($scope.roleList.length >0) ? $scope.roleList[0]: {};
			$scope.newRole.user.role  = $filter('filter')($scope.roleList,acc.user.role.role)[0];
			
			$('#edit-role').modal('show');
		}
		
		$scope.changeAccountRole = function(){
			AccountDataOp
			 .changeAccountRole($scope.newRole)
			 .then(function(r){
				 $scope.success_message = 'User role updated succesfully!';
				 $('#edit-role').modal('hide');
					
					$('#successModal').modal('show');
					
 				setTimeout(function(){
 				    $('#successModal').modal('hide');
 				}, 3000);
 				 
 				$scope.error ='';
			 })
			 .catch(function(e){
				 console.log(e);
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
    				
				})
				.catch(function(error){
					console.log(error);
					$scope.error = error.data.message;
				})
		}
		
		$scope.disableAccount = function(item,index){
			AccountDataOp
				.disableAccount(item)
				.then(function(response){
					
					$scope.success_message = 'Account disabled succesfully!';
					
					$('#successModal').modal('show');
					
    				setTimeout(function(){
    				    $('#successModal').modal('hide');

        				//$state.reload();
        				// $('.table').DataTable();

        				
    				}, 3000);
    				 
    				$scope.error ='';
    				
    				//loadData();
    				$scope.refreshData(item,index)
				})
				.catch(function(error){
					$scope.error = error.data.message;
				});
			
		}
		
		$scope.enableAccount = function(item,index){
			AccountDataOp
				.enableAccount(item)
				.then(function(response){
					
					$scope.success_message = 'Account enabled succesfully!';
					
					$('#successModal').modal('show');
					
    				setTimeout(function(){
    				    $('#successModal').modal('hide');

        				//$state.reload();
        				// $('.table').DataTable();

        				
    				}, 3000);
    				 
    				
    				$scope.error ='';
    				$scope.refreshData(item,index);
				})
				.catch(function(error){
					$scope.error = error.data.message;
				});
			
		}
		
		$scope.resetAccount = function(item,index){
			AccountDataOp
				.resetAccount(item)
				.then(function(response){
					
					$scope.success_message = 'Password reset succesfully!';
					
					$('#successModal').modal('show');
					
    				setTimeout(function(){
    				    $('#successModal').modal('hide');

//        				$state.reload();
//        				 $('.table').DataTable();
    				}, 3000);
    				 
    				$scope.error ='';
				})
				.catch(function(error){
					$scope.error = error.data.message;
				});
			
		}
		
		$scope.itemList ={
				currentPage: 1,
				maxSize: 6,
				numPerPage: 10,
				totalItems: 0,
				data:[],
				orig_data: [],
				filteredList: [],
				search_keyword: undefined,
				
				pageChanged: function(){
					 var begin = ((this.currentPage - 1) * this.numPerPage);
					 var end = begin + this.numPerPage;
					 this.filteredList = this.data.slice(begin, end);
				},
				refresh: function(){
					 begin = ((this.currentPage - 1) * this.numPerPage);
					 end = begin + this.numPerPage;
					 this.filteredList = this.data.slice(begin, end);
			
				},
				searchAccount: function(){
					this.data = $filter('filter')(this.orig_data,this.search_keyword);
					this.totalItems = this.data.length;
					this.refresh();
				}
		}
		
		function loadData(){
			
			AccountDataOp
				.getAccountList()
				.then(function(response){
					
//					$scope.itemList = response.data;
//					$scope.totalItems = $scope.itemList.length;
//		
//					var begin = (($scope.currentPage - 1) * $scope.numPerPage);
//					var end = begin + $scope.numPerPage;
//					$scope.filteredList = $scope.itemList.slice(begin, end);
//					
					$scope.itemList.data = response.data;
					$scope.itemList.orig_data = response.data;
					$scope.itemList.totalItems = response.data.length;
					$scope.itemList.refresh();
					
				})
				.catch(function(error){
					console.log(error);
				});
			

			AccountDataOp
				.getRoleList()
				.then(function(response){
					$scope.roleList = response.data;
					
					$scope.newRole.user.role = ($scope.roleList.length >0) ? $scope.roleList[0]: {};
					//console.log($scope.newItem.user.role);
				})
				.catch(function(error){
					console.log(error);
				});
		}
		
		$scope.refreshData = function(item,index){
			
			console.log(index);
			console.log(item);
			console.log($scope.itemList[index]);
			
			AccountDataOp
			 .getAccount(item.id)
			 .then(function(r){
				// $scope.itemList.filteredList[index] = r.data;
				// $scope.itemList.refresh();
				 
				 loadData();
			 })
			 .catch(function(e){
				 
			 })
			//$scope.itemList[id] 
		}
		
	
		
	}]); 