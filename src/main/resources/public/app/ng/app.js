
	
	angular
		.module('employeeTracker',['ui.router','ngCookies','ui.bootstrap','ui.bootstrap.datetimepicker','AxelSoft','ngStomp','ngFileSaver']);

	angular
		.module('employeeTracker')
		.config(['$stateProvider', '$urlRouterProvider', '$locationProvider',function($stateProvider, $urlRouterProvider, $locationProvider) {
		    $urlRouterProvider.otherwise('/');
		    
		 
		    $stateProvider		  
		    .state("home", {
		        url: '/',
		        templateUrl: '/ActivityTracker/app/partials/home.html', 
		        controller: 'DashboardController',
		        resolve: {
	                  isLoggedIn : ['$q', 'Access', function ($q, Access) {
	                      var deferred = $q.defer();
	                      if (Access.isAuthenticated()) { deferred.resolve();}
	                      else { deferred.reject();  }

	                      return deferred.promise;
	                  }]
	            }
		       
		    })
		    .state("login", {
		        url: '/login',
		        templateUrl: '/ActivityTracker/app/partials/login.html', 
		        controller: 'LoginController',
		        resolve: {
	                  isLoggedIn : ['$q', 'Access', function ($q, Access) {
	                      var deferred = $q.defer();
	                      if (!Access.isAuthenticated()) { deferred.resolve();}
	                      else { deferred.reject();  }

	                      return deferred.promise;
	                  }]
	            }
		    })
		    .state('user-history', {
		        url: '/user-history',
		        templateUrl: '/ActivityTracker/app/partials/user-history.html', 
		        controller: 'UserTaskHistoryController',
		        resolve: {
	                  isSupervisor : ['$q', 'Access', function ($q, Access) {
	                      var deferred = $q.defer();
	                      if (Access.isAuthenticated() && Access.getUser().role.role =='SUPERVISOR') {
	                    	  deferred.resolve();
	                    	 
	                      }
	                      else { 
	                    	  
	                    	  deferred.reject(); 
	                     }
	                      return deferred.promise;
	                  }]
	            }
		        
		    })
		    .state("tasks", {
		        url: '/tasks',
		        templateUrl: '/ActivityTracker/app/partials/task.html', 
		        controller: 'TaskController',
		        resolve: {
	                  isAdmin : ['$q', 'Access', function ($q, Access) {
	                      var deferred = $q.defer();
	                      if (Access.isAuthenticated() && Access.getUser().role.role =='ADMIN') {deferred.resolve(); }
	                      else {  deferred.reject(); }

	                      return deferred.promise;
	                  }]
	            }
		      
		    })
		    .state("employees", {
		        url: '/employees',
		        templateUrl: '/ActivityTracker/app/partials/employee.html', 
		        controller: 'EmployeeController',
		        resolve: {
	                  isAdmin : ['$q', 'Access', function ($q, Access) {
	                      var deferred = $q.defer();
	                      if (Access.isAuthenticated() && Access.getUser().role.role =='ADMIN') {
	                    	  deferred.resolve();
	                    	 
	                      }
	                      else { 
	                    	  
	                    	  deferred.reject(); 
	                     }
	                      return deferred.promise;
	                  }]
	            }
		        
		    })

		    .state("groups", {
		        url: '/groups',
		        templateUrl: '/ActivityTracker/app/partials/group2.html', 
		        controller: 'GroupController',
		        resolve: {
	                  isNotUser : ['$q', 'Access', function ($q, Access) {
	                      var deferred = $q.defer();
	                      if (Access.isAuthenticated() && Access.getUser().role.role !='USER') {deferred.resolve(); }
	                      else { deferred.reject();  }

	                      return deferred.promise;
	                  }]
	            }
		      
		    })
		     .state("accounts", {
		        url: '/accounts',
		        templateUrl: '/ActivityTracker/app/partials/account.html', 
		        controller: 'AccountController',
		        resolve: {
	                  isLoggedIn : ['$q', 'Access', function ($q, Access) {
	                      var deferred = $q.defer();
	                      if (Access.isAuthenticated() && Access.getUser().role.role =='ADMIN') { deferred.resolve();}
	                      else {deferred.reject(); }
	                      
	                      return deferred.promise;
	                  }]
	            }
		      
		    })
		    .state("profile", {
		        url: '/profile',
		        templateUrl: '/ActivityTracker/app/partials/profile.html', 
		        controller: 'ProfileController',
		        resolve: {
	                  isNotUser : ['$q', 'Access', function ($q, Access) {
	                      var deferred = $q.defer();
	                      if (Access.isAuthenticated()) {deferred.resolve(); }
	                      else { deferred.reject();  }

	                      return deferred.promise;
	                  }]
	            }
		      
		    
		    })
		    .state("task-preferrences", {
		        url: '/task-preferrences',
		        templateUrl: '/ActivityTracker/app/partials/task-preferrences.html', 
		        controller: 'TaskPreferrenceController',
		        resolve: {
	                  isUser : ['$q', 'Access', function ($q, Access) {
	                      var deferred = $q.defer();
	                      if (Access.isAuthenticated() && Access.getUser().role.role =='USER') {
	                    	  deferred.resolve();
	                    	 
	                      }
	                      else { 
	                    	  
	                    	  deferred.reject(); 
	                     }
	                      return deferred.promise;
	                  }]
	            }
		      
		    })
		   
		}]);
	
	angular
	.module('employeeTracker')
	.run(['$state','$cookies','$http','LoginDataOp','Access',function ($state,$cookies,$http,LoginDataOp,Access) {
		// Perform logical user logging
		// Check by making a call to server.
		token = $cookies.get('tracker-token');
		

		
		Access
		.getClaimsFromToken(token)
		.then(function(response){
			//console.log('Getting claims from token');
			
			data = response.data;
			
			
			if(data == ''){
				
				$state.go('login');
				
			}else{
				
				$http.defaults.headers.common.Authorization = 'Bearer ' + token;
				
				$cookies.put('tracker-token', token);
				
				//console.log(data);
				Access.user(data.principal.user);
				$state.go('home');
			}
			
		})
		.catch(function(error){
				console.log(error);
				$state.go('login');
		});
		
		
	}]);
	
	angular
	.module('employeeTracker')
	.filter('durationToTime',[function(){
		
		 return function(input,notEmpty) {
			 //utc
			 //return notEmpty ? moment.utc(input).format('HH:mm:ss') : 'N/A';
			 return  notEmpty ? moment.utc(moment.duration(input).asMilliseconds()).format('HH:mm:ss') : 'N/A';
		}
		 
	}]);
	
	angular
	.module('employeeTracker')
	.filter('dateToMoment',[function(){
		
		 return function(input,notEmpty) {
			 //utc			 
			 //return notEmpty ? moment.utc(input).format('MMM DD YYYY h:mm:ss a') : '--';
			 
			 //non-utc
			 return notEmpty ? moment(input).format('MMM DD YYYY h:mm:ss a') : '--';
		}
		 
	}]);
	
	angular
	.module('employeeTracker')
	.filter('dateToUtc',['$filter',function($filter){
		
		 return function(input,notEmpty) {
			 //utc			 
			 //return notEmpty ? moment.utc(input).format('MMM DD YYYY h:mm:ss a') : '--';
			 
			 //non-utc
			 return notEmpty ? $filter('date')(new Date(moment.utc(input).format('MMM DD YYYY h:mm:ss a')),'MMM. dd, yyyy h:mm:ss a') : '--';
		}
		 
	}]);
	


	

	
	