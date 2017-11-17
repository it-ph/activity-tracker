angular
	.module('employeeTracker')
	.factory("AccountDataOp",['$http',function($http){
		
		var AccountDataOp = {};
		
		AccountDataOp
			.getAccountList = function(account){
				return $http({
						method: 'GET',
						 url: '/ActivityTracker/accounts',
						 dataType: 'json',
						 data : account,
						 headers: { 'Content-Type': 'application/json; charset=UTF-8' }
			})
		}
		AccountDataOp
			.getRoleList = function(account){
				return $http({
						method: 'GET',
						 url: '/ActivityTracker/accounts/roles',
						 dataType: 'json',
						 data : account,
						 headers: { 'Content-Type': 'application/json; charset=UTF-8' }
			})
		}
		AccountDataOp
			.addAccount = function(account){
			return $http({
					method: 'POST',
					 url: '/ActivityTracker/accounts',
					 dataType: 'json',
					 data : account,
					 headers: { 'Content-Type': 'application/json; charset=UTF-8' }
				})
		}
		
		AccountDataOp
			.updateAccount = function(account){
				return $http({
						method: 'PUT',
						 url: '/ActivityTracker/accounts',
						 dataType: 'json',
						 data : account,
						 headers: { 'Content-Type': 'application/json; charset=UTF-8' }
				})
		}
		
		AccountDataOp
			.resetAccount = function(account){
				return $http({
						method: 'POST',
						 url: '/ActivityTracker/accounts/resetpassword',
						 dataType: 'json',
						 data : account,
						 headers: { 'Content-Type': 'application/json; charset=UTF-8' }
				})
		}
	
		AccountDataOp
			.disableAccount = function(account){
				return $http({
						method: 'POST',
						 url: '/ActivityTracker/accounts/disable',
						 dataType: 'json',
						 data : account,
						 headers: { 'Content-Type': 'application/json; charset=UTF-8' }
				})
		}
		
		AccountDataOp
			.enableAccount = function(account){
				return $http({
						method: 'POST',
						 url: '/ActivityTracker/accounts/enable',
						 dataType: 'json',
						 data : account,
						 headers: { 'Content-Type': 'application/json; charset=UTF-8' }
				})
		}
	    return AccountDataOp;
	  }]);