angular
	.module('employeeTracker')
	.factory("AccountDataOp",['$http',function($http){
		
		var AccountDataOp = {};
		
		AccountDataOp
			.getAccountList = function(){
				return $http({
						method: 'GET',
						 url: '/ActivityTracker/accounts',
						 headers: { 'Content-Type': 'application/json; charset=UTF-8'}
			})
		}
		AccountDataOp
		   .getAccount = function(id){
			return $http({
				method: 'GET',
				 url: '/ActivityTracker/accounts/'+id,
				 headers: { 'Content-Type': 'application/json; charset=UTF-8'}
			})
		}
		AccountDataOp
			.getRoleList = function(){
				return $http({
						method: 'GET',
						 url: '/ActivityTracker/accounts/roles',
						 dataType: 'json',
						 headers: { 'Content-Type': 'application/json; charset=UTF-8'}
			})
		}
		AccountDataOp
			.addAccount = function(account){
				return $http({
						method: 'POST',
						 url: '/ActivityTracker/accounts',
						 dataType: 'json',
						 data : account,
						 headers: {'Content-Type': 'application/json; charset=UTF-8'}
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
		.updateUser = function(account){
			return $http({
					method: 'POST',
					 url: '/ActivityTracker/accounts/updateUser',
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
						 headers: { 'Content-Type': 'application/json; charset=UTF-8'}
				})
		}
		
		AccountDataOp
			.enableAccount = function(account){
				return $http({
						method: 'POST',
						 url: '/ActivityTracker/accounts/enable',
						 dataType: 'json',
						 data : account,
						 headers: { 'Content-Type': 'application/json; charset=UTF-8'}
				})
		}
		
		AccountDataOp
		.changeAccountRole = function(account){
			return $http({
					method: 'POST',
					 url: '/ActivityTracker/accounts/changeRole',
					 dataType: 'json',
					 data : account,
					 headers: { 'Content-Type': 'application/json; charset=UTF-8'}
			})
		}
		
		AccountDataOp
			.getSubbordinates = function(id){
				return $http({
						method: 'GET',
						 url: '/ActivityTracker/accounts/supervisors/subbordinates/'+id,
			})
		}
	    return AccountDataOp;
	  }]);