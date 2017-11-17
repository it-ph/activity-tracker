
	
	angular
		.module('employeeTracker',['ui.router','ngCookies','ui.bootstrap','AxelSoft']);

	angular
		.module('employeeTracker')
		.config(['$stateProvider', '$urlRouterProvider', '$locationProvider',function($stateProvider, $urlRouterProvider, $locationProvider) {
		    $urlRouterProvider.otherwise('/');
		    
		 
		    $stateProvider
		    .state("landing", {
		        url: '/',
		        templateUrl: '/ActivityTracker/app/partials/landing.html',     
		        controller: 'LandingPageController',
		     
		    })
		    .state("home", {
		        url: '/home',
		        templateUrl: '/ActivityTracker/app/partials/home.html', 
		        controller: 'DashboardController',
		       
		    })
		    .state("login", {
		        url: '/login',
		        templateUrl: '/ActivityTracker/app/partials/login.html', 
		        controller: 'LoginController',
		      
		        
		    })
		    .state("tasks", {
		        url: '/tasks',
		        templateUrl: '/ActivityTracker/app/partials/task.html', 
		        controller: 'TaskController',
		       
		      
		    })
		    .state("employees", {
		        url: '/employees',
		        templateUrl: '/ActivityTracker/app/partials/employee.html', 
		        controller: 'EmployeeController',
		       
		        
		    })
		  
		    .state("groups", {
		        url: '/groups',
		        templateUrl: '/ActivityTracker/app/partials/group.html', 
		        controller: 'GroupController',
		      
		      
		    })
		     .state("accounts", {
		        url: '/accounts',
		        templateUrl: '/ActivityTracker/app/partials/account.html', 
		        controller: 'AccountController',
		       
		      
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