angular
	.module('employeeTracker')
	.controller('MainController',['$rootScope', '$scope', '$state','$stomp','Access',
		function($rootScope, $scope, $state,$stomp,Access){

		//console.log('Main Controller');
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
						//console.log('Sending broadcast for user status');	
						$scope.broadcast('taskUpdate',payload);
					});
					
					//$stomp.subscribe('/group-update/status',function(payload,headers,res){
					$stomp.subscribe('/user-update/group',function(payload,headers,res){
						//console.log('sending broadcast for group status main controller');	
						$scope.broadcast('groupUpdateBroadcast',payload);
					});
				});
			
		}

		$scope.connect();
		
		$scope.sendMessage = function(obj){
			//console.log("sending user status message");
			$stomp.send('/app/user-status', obj,{});
		}
		
		$scope.sendGroupUpdate = function(obj){
			$stomp.send('/app/group-status', obj,{});
		}
		
		$scope.broadcast = function(event,task){
			//console.log('broadcasting ');
			//console.log(event);
			$scope.$broadcast(event,task);
		}
	
		
		//send to parent controller when task event is triggered
		$scope.$on('eventEmitedName',function(event,data){
			//console.log(data);
			//sendMessage(data);
			$scope.sendMessage(data);
		});
		
		$scope.$on('updateTaskEmit',function(event,data){
			//console.log(data);
			//sendMessage(data);
			$scope.sendMessage(data);
		});
		
		$scope.$on('endTaskEmit',function(event,data){

			$stomp.send('/app/user-status', data,{});
			//console.log('endTask emit event received');
			//$scope.sendMessage(data);
		});

		$scope.$on('addTaskEmit',function(event,data){
			$stomp.send('/app/user-status', data,{});
			//console.log('addTask emit event received');
			//$scope.sendMessage(data);
		});

		$scope.$on('groupUpdateEmit',function(event,data){
			//console.log('groupUpdate emit event received');
			$scope.sendGroupUpdate(data);
		});
		
	
	}]); 