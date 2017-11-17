angular.module("employeeTracker").factory("TaskDataOp",["$http",function(e){var t={};return t.getTaskList=function(){return e({method:"GET",url:"/ActivityTracker/tasks",dataType:"json",headers:{"Content-Type":"application/json; charset=UTF-8"}})},t.addTask=function(t){return e({method:"POST",url:"/ActivityTracker/tasks",dataType:"json",data:t,headers:{"Content-Type":"application/json; charset=UTF-8"}})},t.updateTask=function(t){return e({method:"PUT",url:"/ActivityTracker/tasks",dataType:"json",data:t,headers:{"Content-Type":"application/json; charset=UTF-8"}})},t.getEmployeeTaskList=function(t){return e({method:"GET",url:"/ActivityTracker/employee-tasks",dataType:"json",headers:{"Content-Type":"application/json; charset=UTF-8",Authorization:"Bearer "+t}})},t.getMyTaskList=function(t){return e({method:"POST",url:"/ActivityTracker/employee-tasks/myTasks",dataType:"json",headers:{"Content-Type":"application/json; charset=UTF-8",Authorization:"Bearer "+t}})},t.addEmployeeTask=function(t,a){return e({method:"POST",url:"/ActivityTracker/employee-tasks/addTask",dataType:"json",data:a,headers:{Authorization:"Bearer "+t}})},t.endEmployeeTask=function(t,a){return e({method:"POST",url:"/ActivityTracker/employee-tasks/endTask",dataType:"json",data:a,headers:{Authorization:"Bearer "+t}})},t}]);