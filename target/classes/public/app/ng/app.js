
	
	angular
		.module('employeeTracker',['ui.router','ngCookies','ui.bootstrap','AxelSoft','ngStomp']);

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
	                      console.log(Access.getUser().role.role);
	                      console.log(Access.isAuthenticated() && Access.getUser().role.role =='ADMIN');
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
	                  isAdmin : ['$q', 'Access', function ($q, Access) {
	                      var deferred = $q.defer();
	                      if (Access.isAuthenticated() && Access.getUser().role.role =='ADMIN') { deferred.resolve();}
	                      else {deferred.reject(); }
	                      
	                      return deferred.promise;
	                  }]
	            }
		      
		    })
		      .state("group-detail", {
		        url: '/group-detail',
		        templateUrl: '/ActivityTracker/app/partials/group-detail.html', 
		        controller: 'GroupDetailController',
		        params: {
		            group: null
		        }
		      
		    })
		   
		    $locationProvider.html5Mode(false);
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
			console.log('Getting claims from token');
			
			data = response.data;
			$http.defaults.headers.common.Authorization = 'Bearer ' + token;
		
			$cookies.put('tracker-token', token);
			Access.user(data.principal.user);
			$state.go('home');
		})
		.catch(function(error){
				console.log(error);
				$state.go('login');
		});
		
		
	}]);