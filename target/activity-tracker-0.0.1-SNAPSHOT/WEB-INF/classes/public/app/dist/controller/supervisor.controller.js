angular.module("employeeTracker").controller("SupervisorController",["$rootScope","$scope","$state","SupervisorDataOp","Access",function(e,t,r,i,a){t.itemList=[],t.filteredList=[],t.item={},t.totalItems=null,t.currentPage=1,t.maxSize=5,t.numPerPage=10,t.search_keyword="",t.error="",t.success_msg="",i.getSupervisorList().then(function(e){t.itemList=e.data,t.totalItems=t.itemList.length;var r=(t.currentPage-1)*t.numPerPage,i=r+t.numPerPage;t.filteredList=t.itemList.slice(r,i)}).catch(function(e){console.log(e)}),a.showNav(!0),t.search=function(){},t.setPage=function(e){t.currentPage=e},t.pageChanged=function(){var e=(t.currentPage-1)*t.numPerPage,r=e+t.numPerPage;t.filteredList=t.itemList.slice(e,r)}}]);