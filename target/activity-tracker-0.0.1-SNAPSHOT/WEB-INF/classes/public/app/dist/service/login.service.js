angular.module("employeeTracker").factory("LoginDataOp",["$http",function(t){var e={};return e.login=function(e){return t({method:"POST",url:"/ActivityTracker/authenticate",dataType:"json",data:e,headers:{"Content-Type":"application/json; charset=UTF-8"}})},e.getClaimsFromToken=function(e){return t({url:"/ActivityTracker/user-claims",headers:{Authorization:"Bearer "+e}})},e}]);