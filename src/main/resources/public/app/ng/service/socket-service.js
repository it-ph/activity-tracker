angular
	.module("employeeTracker")
	.service("SocketService",['$stomp', function($stomp) {
		
		var SocketService = {};
		
		
	
		
		SocketService.subscribe = function(callback){
			
			$stomp
				.subscribe("/queue/test", callback);
		}
		
		SocketService.send = function(message){
			$stomp.send('/app/user-status', message,{ priority: 9, custom: 42});
			
		};
		
	
			
		return SocketService;
	}]);