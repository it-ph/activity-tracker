angular
	.module('employeeTracker')
	.controller('GroupController',['$rootScope', '$scope','$cookies', '$state','GroupDataOp','Access',
		function($rootScope, $scope,$cookies, $state,GroupDataOp,Access){
		
		
		$scope.itemList =[];
		$scope.filteredList =[];
		$scope.item ={};
		$scope.newItem ={};
		$scope.adminList =[];
		$scope.memberList =[];
		
		
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
		loadData();
		
		$scope.newItem ={
				group:{},
				admin:{},
				member:{},
				groupName:'',
				groupEmployee:{
					group:{},
					employeeUser:{},
				}
		};
		$scope.newGroup ={
				groupName:''
		};
		$scope.selectedGroup ={};

		Access.showNav(true);
		
		$scope.search = function(){
			
		};
		
		$scope.setSelectedGroup = function(group){
			$scope.selectedGroup = group;
		}
		$scope.setSelectedMember = function(group,member){
			$scope.selectedMember.group = group;
			$scope.selectedMember.employeeUser = member;
		}
		
		$scope.setSelectedAdmin = function(group,admin){
			$scope.selectedAdmin.group = group;
			$scope.selectedAdmin.employeeUser = admin;
		}
		
		$scope.setPage = function (pageNo) {
			$scope.currentPage = pageNo;
		};
	
		$scope.pageChanged = function() {
	
			 var begin = (($scope.currentPage - 1) * $scope.numPerPage);
			 var end = begin + $scope.numPerPage;
			 $scope.filteredList = $scope.itemList.slice(begin, end);
	
		};
		
		$scope.showGroup = function(item){
			$state.go('group-detail',{group:item});
		}
		
		$scope.addGroup = function (){
			GroupDataOp
				.addGroup($scope.newGroup)
				.then(function(response){
					
					console.log(response);
					
					$scope.success_message = 'Group added succesfully!';
					
					$('#add-group').modal('hide');
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
		
		$scope.editGroup = function(){
			GroupDataOp
				.editGroup($scope.selectedGroup)
				.then(function(response){
					console.log(response);
					
					$scope.success_message = 'Group updated succesfully!';
					
					$('#edit-group').modal('hide');
					$('#successModal').modal('show');
					
					setTimeout(function(){
					    $('#successModal').modal('hide');
					}, 3000);
					
					loadData();
				})
				.catch(function(error){
					
				})
		}
		$scope.addMember = function(){
			
			GroupDataOp
				.addMember($scope.newItem.groupEmployee)
				.then(function(response){
					console.log(response);
					

					$scope.success_message = 'Member added succesfully!';
					
					$('#add-member').modal('hide');
					$('#successModal').modal('show');
					
					setTimeout(function(){
					    $('#successModal').modal('hide');
					}, 3000);
					
					$scope.newItem.groupEmployee ={};
					loadData();
				})
				.catch(function(error){
					console.log(error);
				});
		}
		
		$scope.removeMember = function(selectedGroup,selectedMember){
			groupEmployee ={
				group: selectedGroup,
				employeeUser: selectedMember
			};
			
			console.log(groupEmployee);
			
			GroupDataOp
			.removeMember(groupEmployee )
			.then(function(response){
				console.log(response);
				
				
				$scope.success_message = 'Member removed succesfully!';
				
				
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
		
		
		$scope.addAdmin = function(){
			
			console.log($scope.newItem.groupEmployee);
			
			GroupDataOp
			.addAdmin($scope.newItem.groupEmployee)
			.then(function(response){
				console.log(response);
				
				$scope.success_message = 'Admin added succesfully!';
				
				$('#add-admin').modal('hide');
				$('#successModal').modal('show');
				
				setTimeout(function(){
				    $('#successModal').modal('hide');
				}, 3000);
				
				$scope.newItem.groupEmployee ={};
				loadData();
			})
			.catch(function(error){
				console.log(error);
			});
		}
		
		
		$scope.removeAdmin = function(selectedGroup,selectedAdmin){
			groupEmployee ={
					group: selectedGroup,
					employeeUser: selectedAdmin
				};
				
			GroupDataOp
			.removeAdmin(groupEmployee)
			.then(function(response){
				console.log(response);
				
				$scope.success_message = 'Admin removed succesfully!';
				
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
		$scope.loadMemberSelection = function(group){
			GroupDataOp
				.getAvailableMembers(group)
				.then(function(response){
					console.log(response);
					
					$scope.memberList = response.data;
					$scope.newItem.groupEmployee.group = group;
				})
				.catch(function(error){
					console.log(error);
				})
			
		}
		
		$scope.loadAdminSelection = function(group){
			GroupDataOp
				.getAvailableAdmins(group)
				.then(function(response){
					console.log(response);
					
					$scope.adminList = response.data;
					$scope.newItem.groupEmployee.group = group;
				})
				.catch(function(error){
					console.log(error);
				})
		}
		
		
		function getMembers(group){
			
		}
		
		
		function refresh(){
			$scope.totalItems = $scope.itemList.length;
			var begin = (($scope.currentPage - 1) * $scope.numPerPage);
			var end = begin + $scope.numPerPage;
			$scope.filteredList = $scope.itemList.slice(begin, end);
		
		}
		
		function loadData(){
			
			GroupDataOp
				.getGroupList()
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
		
	}]); 