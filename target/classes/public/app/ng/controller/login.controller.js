angular
	.module('employeeTracker')
	.controller('LoginController',['$rootScope', '$scope', '$state','$cookies','$http','Access','LoginDataOp',
		function($rootScope, $scope, $state,$cookies,$http,Access,LoginDataOp){
		
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
							
							data = response.data;

							$cookies.put('tracker-token', token);
							Access.user(data.principal.user);
							$http.defaults.headers.common.Authorization = 'Bearer ' + token;
							$state.go('home');
					
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