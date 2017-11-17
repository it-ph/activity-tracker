//angular
//	.module("employeeTracker")
//	.controller("ChatCtrl", function($scope, WebSocketService) {
//		  $scope.messages = [];
//		  $scope.message = "";
//		  $scope.max = 140;
//		
//		  $scope.addMessage = function() {
//			  WebSocketService.send($scope.message);
//		    $scope.message = "";
//		  };
//		
//		  WebSocketService.receive().then(null, null, function(message) {
//		    $scope.messages.push(message);
//		  });
//	});