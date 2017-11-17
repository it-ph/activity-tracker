angular
	.module('employeeTracker')
	.controller('LandingPageController',['$rootScope','$cookies', '$scope', '$state','Access',
		function($rootScope,$cookies, $scope, $state,Access){
		
		$scope.user = Access.getUser();		
		
		$scope.isAuthenticated = function(){
			return Access.isAuthenticated();
		}
		
		token = $cookies.get('tracker-token');
		
		if(token === undefined){
			
			Access.user(undefined);
			$cookies.remove('tracker-token');
			
		}else{
			
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
					}
				})
				.catch(function(error){
						console.log(error);
						$scope.error = error.data.message;
				});
		}
			
		$scope.logout = function(){
			Access.user(undefined);
			$cookies.remove('tracker-token');
			$state.go('landing');
		}
		
	}]); 