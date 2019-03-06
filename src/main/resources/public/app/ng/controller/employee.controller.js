angular
	.module('employeeTracker')
	.controller('EmployeeController',['$rootScope', '$cookies','$scope', '$state','$filter','EmployeeDataOp','Access',
		function($rootScope, $cookies,$scope, $state,$filter,EmployeeDataOp,Access){
		$scope.newItem ={};
		$scope.selectedItem ={
				data: undefined,
				index: undefined
		};
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
			$scope.selectedItem.data = item;
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
        				//$state.reload();
    				    loadData();
    				}, 3000);
    				
    				//loadData();
				})
				.catch(function(error){
					console.log(error);
					$scope.error = error.data.message;
				});
		}

		$scope.editEmployee = function(){
			EmployeeDataOp
				.editEmployee($scope.selectedItem.data)
				.then(function(response){
					$scope.success_message = 'Employee updated succesfully!';
					
					$('#edit-employee').modal('hide');
					
					$('#successModal').modal('show');
					
    				setTimeout(function(){
    				    $('#successModal').modal('hide');
    				}, 3000);
    				
    				//loadData();
    				
    				$scope.refreshData($scope.selectedItem.data.id,$scope.selectedItem.index);
				
				})
				.catch(function(error){
					console.log(error);
					$scope.error = error.data.message;
				});
		}
		
		$scope.refreshData = function(id,index){
			
			EmployeeDataOp
			 .getEmployee(id)
			 .then(function(r){
				 console.log(r)
				 $scope.itemList[index] = r.data;
			 })
			 .catch(function(e){
				 console.log(e)
			 })
		}
		
		function refresh(){
			$scope.totalItems = $scope.itemList.length;
			var begin = (($scope.currentPage - 1) * $scope.numPerPage);
			var end = begin + $scope.numPerPage;
			$scope.filteredList = $scope.itemList.slice(begin, end);
		
		}
		
		$scope.itemList = {
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
				searchEmployee: function(){
					this.data = $filter('filter')(this.orig_data,this.search_keyword);
					this.refresh();
					this.totalItems = this.data.length;
					//console.log(this.search_keyword);
					//console.log(this.data);
				}
		}
		
		function loadData(){
			EmployeeDataOp
				.getEmployeeList()
				.then(function(response){
					
					$scope.itemList.data = response.data;
					$scope.itemList.orig_data = response.data;
					$scope.itemList.totalItems = response.data.length;
					$scope.itemList.refresh();
	
					 
				
				})
				.catch(function(error){
					console.log(error);
				});
		}
		
		
	}]); 