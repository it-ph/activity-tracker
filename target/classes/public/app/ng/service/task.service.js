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
					 headers: { 'Content-Type': 'application/json; charset=UTF-8'}
				})
			}
		
		TaskDataOp
			.addTask = function(task){
				return $http({
					method: 'POST',
					 url: '/ActivityTracker/tasks',
					 dataType: 'json',
					 data: task,
					 headers: { 'Content-Type': 'application/json; charset=UTF-8'}
				})	
		}
		
		TaskDataOp
			.updateTask = function(task){
				return $http({
					method: 'PUT',
					 url: '/ActivityTracker/tasks',
					 dataType: 'json',
					 data: task,
					 headers: { 'Content-Type': 'application/json; charset=UTF-8'}
				})	
		}
		
		TaskDataOp
			.getEmployeeTaskList = function(){
				return $http({
					method: 'GET',
					 url: '/ActivityTracker/employee-tasks',
					 dataType: 'json',
					 headers: {'Content-Type': 'application/json; charset=UTF-8' }
				})
		}
		
		TaskDataOp
			.getMyTaskList = function(){
				return $http({
					method: 'POST',
					 url: '/ActivityTracker/employee-tasks/myTasks',
					 dataType: 'json',
					 headers: { 'Content-Type': 'application/json; charset=UTF-8' }
				})
		}
		TaskDataOp
			.addEmployeeTask = function(employeeTask){
				return $http({
					method: 'POST',
					url: '/ActivityTracker/employee-tasks/addTask',
					dataType: 'json',
					data: employeeTask,
					headers: { 'Content-Type': 'application/json; charset=UTF-8'}
				})
		}
		
		TaskDataOp
			.endEmployeeTask = function(employeeTask){
				return $http({
					method: 'POST',
					url: '/ActivityTracker/employee-tasks/endTask',
					dataType: 'json',
					data: employeeTask,
					headers: { 'Content-Type': 'application/json; charset=UTF-8'}
				})
		}
		
		return TaskDataOp;
	}]);