'use strict';

/* Controllers */

var ontimeControllers = angular.module('ontimeControllers', []);

ontimeControllers.controller('quaterFromCtrl', ['$scope','TaskManager',  
  function($scope, TaskManager) {
      
      var self = this;
      
      $scope.tasks = [];
      
      TaskManager.loadAllTasks().then(function(data){
          $scope.tasks = data;
          //$scope.$apply();
      });
      
      // http://stackoverflow.com/questions/14561676/angularjs-and-contenteditable-two-way-binding-doesnt-work-as-expected
      $scope.addNewNote =  function(dropId){
          TaskManager.addNewTask(dropId).then(function(newId){
              TaskManager.loadAllTasks().then(function(data){
                 $scope.tasks = data;
                 //$scope.$apply();
                });
          });
      }
      
      $scope.updateTask = function(task){
    	  alert(task.data);
      }
      
      $scope.moveToBox = function(id, newseverity) {
    	TaskManager.updateSeverity(id, newseverity).then(function(data){
    		$scope.tasks = data;
    	});  
    };
      
  }]);
  
