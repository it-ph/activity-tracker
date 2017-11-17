angular
	.module('employeeTracker')
	.factory('GroupDataOp', ['$http', function($http) {
	
		var GroupDataOp = {};
		
		
		GroupDataOp.getGroupList = function(){
			
			return $http({
				method: 'GET',
				 url: '/ActivityTracker/groups',
				 dataType: 'json',
				 headers: { 'Content-Type': 'application/json; charset=UTF-8' }
			})
			
		}
		
		GroupDataOp.addGroup = function(group){
			
			return $http({
				method: 'POST',
				 url: '/ActivityTracker/groups',
				 dataType: 'json',
				 data:group,
				 headers: { 'Content-Type': 'application/json; charset=UTF-8' }
			})
			
		}
		GroupDataOp.editGroup = function(group){
			
			return $http({
				method: 'PUT',
				 url: '/ActivityTracker/groups',
				 dataType: 'json',
				 data:group,
				 headers: { 'Content-Type': 'application/json; charset=UTF-8' }
			})
			
		}
		GroupDataOp.getSubordinateTaskList = function(token){
			
			return $http({
				method: 'GET',
				 url: '/ActivityTracker/group-task',
				 dataType: 'json',
				 headers: 
				 	{ 
					 'Content-Type': 'application/json; charset=UTF-8' ,
					 'Authorization': 'Bearer '+token
				 	 }
			})
			
		}
		
		GroupDataOp.getAvailableMembers = function(group,token){
			
			return $http({
				method: 'POST',
				 url: '/ActivityTracker/group-members',
				 dataType: 'json',
				 data: group,
				 headers: 
				 	{ 
					 'Content-Type': 'application/json; charset=UTF-8' ,
					 'Authorization': 'Bearer '+token
				 	 }
			})
			
		}
		
		GroupDataOp.getAvailableAdmins = function(group,token){
			return $http({
				method: 'POST',
				 url: '/ActivityTracker/group-admins',
				 dataType: 'json',
				 data: group,
				 headers: 
				 	{ 
					 'Content-Type': 'application/json; charset=UTF-8' ,
					 'Authorization': 'Bearer '+token
				 	 }
			})
			
		}
		GroupDataOp.addMember = function(groupMember,token){
			return $http({
				method: 'POST',
				 url: '/ActivityTracker/groups/addMember',
				 dataType: 'json',
				 data: groupMember,
				 headers: 
				 	{ 
					 'Content-Type': 'application/json; charset=UTF-8' ,
					 'Authorization': 'Bearer '+token
				 	 }
			})
			
		}
		GroupDataOp.removeMember = function(groupMember,token){
			return $http({
				method: 'POST',
				 url: '/ActivityTracker/groups/removeMember',
				 dataType: 'json',
				 data: groupMember,
				 headers: 
				 	{ 
					 'Content-Type': 'application/json; charset=UTF-8' ,
					 'Authorization': 'Bearer '+token
				 	 }
			})
			
		}
		GroupDataOp.addAdmin = function(groupAdmin,token){
			return $http({
				method: 'POST',
				 url: '/ActivityTracker/groups/addAdmin',
				 dataType: 'json',
				 data: groupAdmin,
				 headers: 
				 	{ 
					 'Content-Type': 'application/json; charset=UTF-8' ,
					 'Authorization': 'Bearer '+token
				 	 }
			})
			
		}
		
		GroupDataOp.removeAdmin = function(groupAdmin,token){
			return $http({
				method: 'POST',
				 url: '/ActivityTracker/groups/removeAdmin',
				 dataType: 'json',
				 data: groupAdmin,
				 headers: 
				 	{ 
					 'Content-Type': 'application/json; charset=UTF-8' ,
					 'Authorization': 'Bearer '+token
				 	 }
			})
			
		}
		return GroupDataOp;
	}]);