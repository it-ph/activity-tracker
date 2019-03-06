angular
	.module('employeeTracker')
	.controller('UserTaskHistoryController',
			  ['$rootScope','$cookies','$scope', '$state','TaskDataOp','AccountDataOp','Access','FileSaver','Blob',
		function($rootScope,  $cookies, $scope,    $state,TaskDataOp,   AccountDataOp,   Access,  FileSaver,Blob){
		
		$scope.numPerPageCap = [10,25,50,100];
		$scope.pageCap= $scope.numPerPageCap[0];
		$scope.searchResults =[];
		$scope.empList = [];
		$scope.selectedUser = {};
		
		$scope.dateOptions = {
				showWeeks: false,
				startingDay: 0,
				minView: 'day'
		};
		
		$scope.history ={
				date: undefined,
				userId: 2,
				supervisorId: Access.getUser().id,
		}
		$scope.items = {
				
			currentPage: 1,
			numPerPageCap:  [10,25,50,100],
			numPerPage: 10,
			totalItems: 0,
			maxSize: 6,
			searchResults: [],
			filteredList: [],
			empList: [],
			selectedUser: undefined,
			
			history: {
				date: undefined,
				userId: undefined,
				supervisorId: Access.getUser().id,
			},
			
			canDownload: false,
			
			searchUserHistory: function(){
				
			
				if(this.selectedUser && this.history.date){
					
					this.history.userId = this.selectedUser.user.id;
					
					
					TaskDataOp
					 .getUserTaskHistory(this.history)
					 .then(function(r){
						
						 
						 $scope.items.searchResults = r.data;
						 $scope.items.totalItems = r.data.length;
						 $scope.items.refresh();
					 })
					 .catch(function(e){
						 console.log(e)
					 })
					
				}
			},
			refresh: function(){
				
				 begin = ((this.currentPage - 1) * this.numPerPage);
				 end = begin + this.numPerPage;
				 this.filteredList = this.searchResults.slice(begin, end);
			},
			
			pageChanged: function(){
				
				
				 begin = ((this.currentPage - 1) * this.numPerPage);
				 end = begin + this.numPerPage;
				 this.filteredList = this.searchResults.slice(begin, end);
			}
			
		};
		
		$scope.searchUserHistory = function(){
			
			
			if($scope.selectedUser.user && $scope.history.date){
				
				$scope.history.userId = $scope.selectedUser.user.id;
				
				console.log($scope.history);
				
				TaskDataOp
				 .getUserTaskHistory($scope.history)
				 .then(function(r){
					
					 
					 $scope.items.searchResults = r.data;
					 
					 
				 })
				 .catch(function(e){
					 console.log(e)
				 })
			}
			

		}
		
		$scope.searchUserHistory();
		
		
		$scope.loadUserTaskHistory = function(){
			
		}
		
		
		AccountDataOp
		  .getSubbordinates(Access.getUser().id)
		  .then(function(r){
			  $scope.empList = r.data;
		  })
		  .catch(function(e){
			  
		  })
		
	}]);