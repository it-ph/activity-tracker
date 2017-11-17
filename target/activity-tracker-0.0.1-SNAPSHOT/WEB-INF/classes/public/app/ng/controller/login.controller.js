angular
	.module('employeeTracker')
	.controller('LoginController',['$rootScope', '$scope', '$state','$cookies','Access','LoginDataOp',
		function($rootScope, $scope, $state,$cookies,Access,LoginDataOp){
		
		$scope.user ={};
		$scope.error ='';
	
		$scope.login = function(){
			
			LoginDataOp
				.login($scope.user)
				.then(function(response){
					
					token = response.data.access_token;
					
					Access
						.getClaimsFromToken(token)
						.then(function(response){
							
							console.log("token response");
							console.log(response);
							 
							//if the user is authenticated store the user credentials
							data = response.data;
							
							if(data.authenticated){
								$cookies.put('tracker-token', token);
								Access.user(data.principal.user);
								$state.go('home');
							}
						})
						.catch(function(error){
								console.log(error);
								$scope.error = error.data.message;
						});
				
				
				})
				.catch(function(error){
					console.log(error);
					$scope.error = error.data.message;
				});
		}
		
	}]); 