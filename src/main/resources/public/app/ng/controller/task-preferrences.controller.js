angular
	.module('employeeTracker')
	.controller('TaskPreferrenceController',['$scope', '$state','Access','TaskDataOp',
		function($scope, $state,Access,TaskDataOp){
		
		
		$scope.items ={
				
				itemList: [],
				selectedItem: undefined,
				newItem: undefined,
				taskSelection: [],
				error: undefined,
				loadPreferrences: function(onloadFinished){
					
					TaskDataOp
					 .getTaskPreferrences()
					 .then(function(r){
						 
						 $scope.items.itemList = r.data;
						 
						 
						 if(onloadFinished){
							 onloadFinished();
						 }
					 })
					 .catch(function(e){
						 console.log(e)
					 })
				},
				loadSelection: function(callback){
					
					TaskDataOp
					.getTaskList()
					.then(function(r){
						$scope.items.taskSelection = r.data; 
						
						callback();
					})
					.catch(function(error){
						console.log(error);
					});
					
				},
				showAddModal: function(){
					
					this.loadSelection(function(){
						$('#add-pref').modal('show');
					});
					
				},
				
				addItem: function(){
					TaskDataOp
					 .addTaskPreferrences(this.selectedItem)
					 .then(function(r){
						 console.log(r)
						 $scope.items.selectedItem = undefined;
						 $scope.items.loadPreferrences(function(){
							 
							 $('#add-pref').modal('hide');
						 });
					 })
					 .catch(function(e){
						 console.log(e)
						 $scope.items.error ='Duplicate entry';
					 })
				},
				
				deleteItem: function(selectedItem){
					TaskDataOp
					 .deleteTaskPreferrences(selectedItem)
					 .then(function(r){
						 console.log(r)
						 $scope.items.loadPreferrences(function(){});
					 })
					 .catch(function(e){
						 console.log(e)
					 })
				}
				
		}
		
		$scope.loading = true;
		
		$scope.items.loadPreferrences(function(){
			$scope.loading = false;
		});
		
		$scope.items.loadSelection(function(){});
		
	}]);