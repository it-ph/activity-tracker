angular.module("employeeTracker").factory("AccountDataOp",["$http",function(t){var a={};return a.getAccountList=function(a){return t({method:"GET",url:"/ActivityTracker/accounts",dataType:"json",data:a,headers:{"Content-Type":"application/json; charset=UTF-8"}})},a.getRoleList=function(a){return t({method:"GET",url:"/ActivityTracker/accounts/roles",dataType:"json",data:a,headers:{"Content-Type":"application/json; charset=UTF-8"}})},a.addAccount=function(a){return t({method:"POST",url:"/ActivityTracker/accounts",dataType:"json",data:a,headers:{"Content-Type":"application/json; charset=UTF-8"}})},a.updateAccount=function(a){return t({method:"PUT",url:"/ActivityTracker/accounts",dataType:"json",data:a,headers:{"Content-Type":"application/json; charset=UTF-8"}})},a.resetAccount=function(a){return t({method:"POST",url:"/ActivityTracker/accounts/resetpassword",dataType:"json",data:a,headers:{"Content-Type":"application/json; charset=UTF-8"}})},a.disableAccount=function(a){return t({method:"POST",url:"/ActivityTracker/accounts/disable",dataType:"json",data:a,headers:{"Content-Type":"application/json; charset=UTF-8"}})},a.enableAccount=function(a){return t({method:"POST",url:"/ActivityTracker/accounts/enable",dataType:"json",data:a,headers:{"Content-Type":"application/json; charset=UTF-8"}})},a}]);