'use strict';

/* Controllers */

var ontimeControllers = angular.module('ontimeControllers', []);

ontimeControllers.controller('quaterFromCtrl', [
		'$scope',
		'TaskManager',
		'$route',
		'$location',
		function($scope, TaskManager, $route, $location) {
			var self = this;
			$scope.chart = TaskManager.getChartTemplate(0);

			// init
			var params = $route.current.params;
			
			if (params != undefined && params["id"] != undefined) {
				TaskManager.getById(params.id).then(function(data) {
					$scope.chart = data;
				},function(data){
					$location.path("/", false);
				});
			}

			$scope.sync = function(){
				TaskManager.updateChart($scope.chart).then(function(data) {
					if ($scope.chart.id != data.id){
						$location.path("/"+data.id, false);
					}
					$scope.chart = data;
				});
			}
			
			$scope.addNewTask = function(dropId) {
				var task = TaskManager.getChartTemplate(dropId);
					task.id = Math.round(Math.random()*1000000);
				$scope.chart.items.push(task);
				$scope.sync();
			}
			
			$scope.moveToBox = function(id, newseverity) {
				for (var i=0; i < $scope.chart.items.length; i++){
					if ($scope.chart.items[i].id == id){
						$scope.chart.items[i].severity = newseverity;
						break;
					}
				}
				$scope.sync();
			};
			
			$scope.updateTask = function(task){
				TaskManager.updateChart($scope.chart);
			}
			

			// functions
			// http://stackoverflow.com/questions/14561676/angularjs-and-contenteditable-two-way-binding-doesnt-work-as-expected
			/*
			 * $scope.addNewNote = function(dropId){
			 * TaskManager.addNewTask(dropId).then(function(newId){
			 * TaskManager.loadAllTasks().then(function(data){ $scope.tasks =
			 * data; //$scope.$apply(); }); }); }
			 * 
			 * $scope.updateTask = function(task){
			 * TaskManager.updateTask(task).then(function(data){ $scope.tasks =
			 * data; }); }
			 * 
			 * $scope.moveToBox = function(id, newseverity) {
			 * TaskManager.updateSeverity(id, newseverity).then(function(data){
			 * $scope.tasks = data; });
			 *  };
			 */

		} ]);
