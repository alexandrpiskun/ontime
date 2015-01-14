'use strict';

/* Controllers */

var ontimeControllers = angular.module('ontimeControllers', []);

ontimeControllers.controller('quaterFromCtrl', ['$scope','TaskManager',  
  function($scope, TaskManager) {
      
      var self = this;
      
      $scope.tasks = [];
      $scope.chart = null;
      
      // init
      
      TaskManager.getById('4s24rNjjPr').then(function(data){
          $scope.chart = data;
      });
      
   
      
      // functions
      // http://stackoverflow.com/questions/14561676/angularjs-and-contenteditable-two-way-binding-doesnt-work-as-expected
      /* $scope.addNewNote =  function(dropId){
          TaskManager.addNewTask(dropId).then(function(newId){
              TaskManager.loadAllTasks().then(function(data){
                 $scope.tasks = data;
                 //$scope.$apply();
                });
          });
      }
      
      $scope.updateTask = function(task){
    	  TaskManager.updateTask(task).then(function(data){
      		$scope.tasks = data;
      	   });
      }
      
      $scope.moveToBox = function(id, newseverity) {
    	TaskManager.updateSeverity(id, newseverity).then(function(data){
    		$scope.tasks = data;
    	}); 
    	 
      };*/
      
  }]);
  
