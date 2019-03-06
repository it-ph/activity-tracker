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
		  .addRemarks = function(task){
				return $http({
					method: 'POST',
					 url: '/ActivityTracker/employee-tasks/addRemarks',
					 dataType: 'json',
					 data: task,
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
			.getUserTaskHistory = function(params){
				return $http({
					method: 'POST',
					 url: '/ActivityTracker/employee-tasks/user-history',
					 dataType: 'json',
					 data: params,
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
		
		TaskDataOp
			.getTaskHistory = function(period){
				return $http({
					method: 'POST',
					url: '/ActivityTracker/employee-tasks/history',
					dataType: 'json',
					data: period,
					headers: { 'Content-Type': 'application/json; charset=UTF-8'}
				})
		}
		
		TaskDataOp
			.getEmployeeTask = function(empNumber){
				return $http({
					method: 'GET',
					url: '/ActivityTracker/employee-tasks/employee/'+empNumber,
					dataType: 'json',
					headers: { 'Content-Type': 'application/json; charset=UTF-8'}
				})
		}
		
		TaskDataOp
			 .getTaskPreferrences = function(){
				return $http({
					method: 'GET',
					url: '/ActivityTracker/tasks/preferrences'
				})
		}
		TaskDataOp
		 .addTaskPreferrences = function(pref){
			return $http({
				method: 'POST',
				url: '/ActivityTracker/tasks/preferrences',
				dataType: 'json',
				data: pref,
				headers: { 'Content-Type': 'application/json; charset=UTF-8'}
			})
		}	
		TaskDataOp
		 .deleteTaskPreferrences = function(pref){
			return $http({
				method: 'PUT',
				url: '/ActivityTracker/tasks/preferrences',
				dataType: 'json',
				data: pref,
				headers: { 'Content-Type': 'application/json; charset=UTF-8'}
			})
		}
		TaskDataOp
			.downloadReport = function(report){
				return $http({
					method: 'POST',
					url: '/ActivityTracker/employee-tasks/report/download',
					dataType: 'json',
					data: report,
					headers: { 'Content-Type': 'application/json; charset=UTF-8'}
				})
		}
		return TaskDataOp;
	}]);