angular
	.module('employeeTracker')
	.controller('EmployeeController',['$rootScope', '$cookies','$scope', '$state','$filter','EmployeeDataOp','Access',
		function($rootScope, $cookies,$scope, $state,$filter,EmployeeDataOp,Access){
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
		

		
		loadData();
		Access.showNav(true);
		
		
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
			//console.log(item);
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