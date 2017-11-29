angular
	.module('employeeTracker')
	.controller('GroupDetailController',['$rootScope', '$scope', '$cookies','$state','$stateParams','Access',
		function($rootScope, $scope, $cookies,$state,$stateParams,Access){			
			token  = $cookies.get('tracker-token');				
				if(token == undefined){					
					$state.go('landing');
					
				}else{
					Access
						.getClaimsFromToken(token)
						.then(function(response){							
							data = response.data;						
							if(data.authenticated){
								Access.user(data.principal.user);								
								if(data.principal.user.role.role !='SUPERVISOR'){
									$state.go('home');
								}
							}
						})
						.catch(function(error){
							$cookies.remove('tracker-token');
							Access.user(undefined);
							$state.go('landing');
						});
								
				}
			//console.log($stateParams.group);
			if($stateParams.group == null){
				$state.go('home');
			}
			
			$scope.group = $stateParams.group;
			$scope.memberList =[];
			$scope.adminList =[];
			
			$scope.adminList = $scope.group.admin;
			$scope.memberList = $scope.group.members;
			
			//console.log($scope.adminList);
		
	}]); 