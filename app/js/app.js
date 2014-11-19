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
      when('/', {
        templateUrl: 'partials/quater-form.html',
        controller: 'quaterFromCtrl'
      }).
      otherwise({
        redirectTo: '/'
      });
  }]);
