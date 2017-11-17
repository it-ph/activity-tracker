webpackJsonp([1],[
/* 0 */
/***/ (function(module, exports) {


	
	angular
		.module('employeeTracker',['ui.router','ngCookies','ui.bootstrap','oc.lazyLoad','AxelSoft']);

	angular
		.module('employeeTracker')
		.config(['$ocLazyLoadProvider','$stateProvider', '$urlRouterProvider', '$locationProvider',function($ocLazyLoadProvider,$stateProvider, $urlRouterProvider, $locationProvider) {
		    $urlRouterProvider.otherwise('/');
		    
		    $ocLazyLoadProvider.config({
		        'debug': true, // For debugging 'true/false'
		        'events': true, // For Event 'true/false'
		        'modules': [{ // Set modules initially
		            name : 'landing', // State1 module
		            files: ['/ActivityTracker/app/ng/controller/landing.controller.js']
		        },{
		            name : 'home', // State2 module
		            files: ['/ActivityTracker/app/ng/controller/home.controller.js']
		        }]
		    });
		    
		    $stateProvider
		    .state("landing", {
		        url: '/landing',
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
		    $locationProvider.html5Mode(false);
		}]);

/***/ })
],[0]);