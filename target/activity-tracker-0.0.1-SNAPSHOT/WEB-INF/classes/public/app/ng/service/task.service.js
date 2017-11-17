angular
	.module('employeeTracker')
	.factory('TaskDataOp', ['$http', function($http) {
		var TaskDataOp = {};
		
		TaskDataOp
			.getTaskList = function(){
				return $http({
					method: 'GET',
					 url: '/ActivityTracker/tasks',
					 dataType: 'json',
					 headers: { 'Content-Type': 'application/json; charset=UTF-8' }
				})
			}
		
		TaskDataOp
			.addTask = function(task){
				return $http({
					method: 'POST',
					 url: '/ActivityTracker/tasks',
					 dataType: 'json',
					 data: task,
					 headers: { 'Content-Type': 'application/json; charset=UTF-8' }
				})	
		}
		
		TaskDataOp
			.updateTask = function(task){
				return $http({
					method: 'PUT',
					 url: '/ActivityTracker/tasks',
					 dataType: 'json',
					 data: task,
					 headers: { 'Content-Type': 'application/json; charset=UTF-8' }
				})	
		}
		
		TaskDataOp
			.getEmployeeTaskList = function(token){
				return $http({
					method: 'GET',
					 url: '/ActivityTracker/employee-tasks',
					 dataType: 'json',
					 headers: { 
						 	'Content-Type': 'application/json; charset=UTF-8' ,
						 	'Authorization': 'Bearer '+token
						 }
				})
		}
		
		TaskDataOp
			.getMyTaskList = function(token){
				return $http({
					method: 'POST',
					 url: '/ActivityTracker/employee-tasks/myTasks',
					 dataType: 'json',
					 headers: { 
						 	'Content-Type': 'application/json; charset=UTF-8' ,
						 	'Authorization': 'Bearer '+token
						 }
				})
		}
		TaskDataOp
			.addEmployeeTask = function(token,employeeTask){
				return $http({
					method: 'POST',
					url: '/ActivityTracker/employee-tasks/addTask',
					dataType: 'json',
					data: employeeTask,
					headers: { 'Authorization': 'Bearer '+token }
				})
		}
		
		TaskDataOp
			.endEmployeeTask = function(token,employeeTask){
				return $http({
					method: 'POST',
					url: '/ActivityTracker/employee-tasks/endTask',
					dataType: 'json',
					data: employeeTask,
					headers: { 'Authorization': 'Bearer '+token }
				})
		}
		
		return TaskDataOp;
	}]);