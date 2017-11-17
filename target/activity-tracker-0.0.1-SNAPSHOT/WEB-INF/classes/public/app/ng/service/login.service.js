angular
	.module('employeeTracker')
	.factory('LoginDataOp', ['$http', function($http) {
	
		var LoginDataOp = {};
		
		LoginDataOp.login = function(user){
			
			return $http({
				method: 'POST',
				 url: '/ActivityTracker/authenticate',
				 dataType: 'json',
				 data: user,
				 headers: { 'Content-Type': 'application/json; charset=UTF-8' }
			})
			
		}
		
		LoginDataOp.getClaimsFromToken = function(token){
			return $http({
				url: '/ActivityTracker/user-claims',
			    headers: { 'Authorization': 'Bearer '+token }
			})
				
		 }
		
		
		return LoginDataOp;
	}]);