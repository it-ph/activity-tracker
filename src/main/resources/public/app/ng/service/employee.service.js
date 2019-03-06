angular
	.module('employeeTracker')
	.factory('EmployeeDataOp', ['$http', function($http) {
	
		var EmployeeDataOp = {};
		
		
		EmployeeDataOp.getEmployeeList = function(){
			
			return $http({
				method: 'GET',
				 url: '/ActivityTracker/employees',
				 dataType: 'json',
				 headers: { 'Content-Type': 'application/json; charset=UTF-8'}
			})
			
		}
		
		EmployeeDataOp.getEmployee = function(id){
			
			return $http({
				method: 'GET',
				 url: '/ActivityTracker/employees/'+id,
				 dataType: 'json',
				 headers: { 'Content-Type': 'application/json; charset=UTF-8'}
			})
			
		}
		EmployeeDataOp.addEmployee = function(employee){
			return $http({
				method: 'POST',
				 url: '/ActivityTracker/employees/addEmployee',
				 dataType: 'json',
				 data : employee,
				 headers: { 'Content-Type': 'application/json; charset=UTF-8' }
			})
		}
		
		EmployeeDataOp.editEmployee = function(employee){
			return $http({
				method: 'PUT',
				 url: '/ActivityTracker/employees/editEmployee',
				 dataType: 'json',
				 data : employee,
				 headers: { 'Content-Type': 'application/json; charset=UTF-8' }
			})
		}
		return EmployeeDataOp;
	}]);