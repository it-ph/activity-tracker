angular
	.module('employeeTracker')
	.factory('GroupDataOp', ['$http', function($http) {
	
		var GroupDataOp = {};
		
		
		GroupDataOp.getGroupList = function(){
			
			return $http({
				method: 'GET',
				 url: '/ActivityTracker/groups',
				 dataType: 'json',
				 headers:{'Content-Type': 'application/json; charset=UTF-8' }
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
		GroupDataOp.getSubordinateTaskList = function(){
			
			return $http({
				method: 'GET',
				 url: '/ActivityTracker/group-task',
				 dataType: 'json',
				 headers: { 'Content-Type': 'application/json; charset=UTF-8' }
			})
			
		}
		
		GroupDataOp.getAvailableMembers = function(group){
			
			return $http({
				method: 'POST',
				 url: '/ActivityTracker/group-members',
				 dataType: 'json',
				 data: group,
				 headers: { 'Content-Type': 'application/json; charset=UTF-8'}
			})
			
		}
		
		GroupDataOp.getAvailableAdmins = function(group){
			return $http({
				method: 'POST',
				 url: '/ActivityTracker/group-admins',
				 dataType: 'json',
				 data: group,
				 headers: {'Content-Type': 'application/json; charset=UTF-8'}
			})
			
		}
		GroupDataOp.addMember = function(groupMember){
			return $http({
				method: 'POST',
				 url: '/ActivityTracker/groups/addMember',
				 dataType: 'json',
				 data: groupMember,
				 headers:{'Content-Type': 'application/json; charset=UTF-8'}
			})
			
		}
		GroupDataOp.removeMember = function(groupMember){
			return $http({
				method: 'POST',
				 url: '/ActivityTracker/groups/removeMember',
				 dataType: 'json',
				 data: groupMember,
				 headers: { 'Content-Type': 'application/json; charset=UTF-8'}
			})
			
		}
		GroupDataOp.addAdmin = function(groupAdmin){
			return $http({
				method: 'POST',
				 url: '/ActivityTracker/groups/addAdmin',
				 dataType: 'json',
				 data: groupAdmin,
				 headers:{ 'Content-Type': 'application/json; charset=UTF-8'}
			})
			
		}
		
		GroupDataOp.removeAdmin = function(groupAdmin){
			return $http({
				method: 'POST',
				 url: '/ActivityTracker/groups/removeAdmin',
				 dataType: 'json',
				 data: groupAdmin,
				 headers: { 'Content-Type': 'application/json; charset=UTF-8'}
			})
			
		}
		return GroupDataOp;
	}]);