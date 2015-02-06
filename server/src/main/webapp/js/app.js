'use strict';

/* App Module */

var ontimeApp = angular.module('ontimeApp', [
  'ngRoute',
  /*'ontimeAnimations',*/
  'ontimeControllers' ,
  'ontimeFilters'  ,
  'ontimeServices' ,
  'ontimeFactories', 
  'ontimeDirectives'
]);

ontimeApp.config(['$routeProvider',
  function($routeProvider) {
    $routeProvider.
      when('/:id', {
        templateUrl: 'partials/quater-form.html',
        controller: 'quaterFromCtrl'
      }).
      when('/', {
          templateUrl: 'partials/quater-form.html',
          controller: 'quaterFromCtrl'
        }).
      otherwise({
        redirectTo: '/'
      });
  }]).run(['$route', '$rootScope', '$location', function ($route, $rootScope, $location) {
	    var original = $location.path;
	    $location.path = function (path, reload) {
	        if (reload === false) {
	            var lastRoute = $route.current;
	            var un = $rootScope.$on('$locationChangeSuccess', function () {
	                $route.current = lastRoute;
	                un();
	            });
	        }
	        return original.apply($location, [path]);
	    };
	}]);
