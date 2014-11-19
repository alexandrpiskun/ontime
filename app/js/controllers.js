'use strict';

/* Controllers */

var ontimeControllers = angular.module('ontimeControllers', []);

ontimeControllers.controller('quaterFromCtrl', ['$scope','TaskManager',  
  function($scope, TaskManager) {
      
      var self = this;
      
      $scope.tasks = [];
      
      TaskManager.loadAllTasks().then(function(data){
          $scope.tasks = data;
      });
      
      
      $scope.addNewNote =  function(dropId){
          TaskManager.addNewTask(dropId).then(function(newId){
              console.log("task was created");
          });
      }
      $scope.moveToBox = function(id, dropid) {
 
        for (var index = 0; index < $scope.tasks.length; index++) {
            var item = $scope.tasks[index];
            if (item.id == id) {
                item.severity =  dropid; //  should be changed 
                break;
            }
        }
        $scope.$apply();
    };
      
  }]);
  
