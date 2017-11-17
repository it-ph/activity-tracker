angular
	.module('employeeTracker')
	.controller('MainController',['$rootScope', '$scope', '$state','$stomp','Access',
		function($rootScope, $scope, $state,$stomp,Access){

		console.log('Main Controller');
		$scope.canShowNav = function(){
			visible = true;
			
			switch($state.current.name){
				case '':
				case 'landing':
				case 'login':
					  visible = false;
					  break;
				default: visible = true;
			}
			
			return visible;
		}
		
	
		$scope.isAuthenticated = function(){
			return Access.isAuthenticated();
		}
		
		
		var socket = null;
		var stompClient = null;

		
		 $scope.messages = [];
		 $scope.message = "";
		 $scope.max = 140;
		 
		$scope.connect = function(){
			$stomp
				.connect('/ActivityTracker/activity-socket',{})
				.then(function(frame){					
					$stomp.subscribe('/user-update/status',function(payload,headers,res){
						console.log(payload);	
						$scope.broadcast(payload);
					});					
				});
			
		}

		$scope.connect();
		
		$scope.sendMessage = function(obj){
			$stomp.send('/app/user-status', obj,{
	          priority: 9,
	          custom: 42 // Custom Headers 
	        });
		}
		
		$scope.broadcast = function(task){
			$scope.$broadcast('taskUpdate',task);
		}
	
		
		//send to parent controller when task event is triggered
		$scope.$on('eventEmitedName',function(event,data){
			console.log(data);
			//sendMessage(data);
			$scope.sendMessage(data);
		});
		
	

		
	
		
	
	}]); 