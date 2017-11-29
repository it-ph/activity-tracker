angular
	.module('employeeTracker')
	.controller('ProfileController',['$scope', '$state','Access','AccountDataOp',
		function($scope, $state,Access,AccountDataOp){
		
		$scope.profile = Access.getUser();
		
		$scope.newPassword ='';
		$scope.confirmNewPassword ='';
		$scope.userName = $scope.profile.username;
		
		//console.log($scope.profile);
		
		
		$scope.updateProfile = function(){
			$scope.error ='';
	
			
			user = {
				id: $scope.profile.id,
				password: $scope.newPassword
			};
			
			if($scope.newPassword != $scope.confirmNewPassword){
				$scope.error ='password mismatched';
			}
			
			if($scope.newPassword.length < 8){
				$scope.error ='password must be at least 8 characters';
			}
			
			if($scope.error ==''){
				AccountDataOp
				.updateUser(user)
				.then(function(response){

					$scope.success_message = 'Password updated succesfully!';
					
					$('#update-profile').modal('hide');
					
					$('#successModal').modal('show');
					
    				setTimeout(function(){
    				    $('#successModal').modal('hide');
    				}, 3000);
    				 
    				$scope.error ='';
					
				})
				.catch(function(error){
					console.log(error);
				});
			}
			
		}
		
		
	}]);