'use strict';

/* Services */

var ontimeFactories = angular.module('ontimeFactories', []);

ontimeFactories.factory('Task', [ "$q", function($q) {
	function Task(thatTask) {
		this.id = '';
		this.severity = 1;
		this.data = '';
		if (thatTask) {
			$.extend(this,thatTask);
		}
		return this;
	};
	return Task;
} ]);

ontimeFactories
		.factory(
				'TaskManager',
				[
						"$q",
						"Task",
						"$http",
						function($q, Task, $http) {
							return {
								_tasks : [],
								_mergeTask : function(id, data) {
									var instance = this._search(id);
									if (instance) {
										instance.set(data);
									} else {
										instance = new Task(data);
										this._taskPool[id] = instance;
									}
									return instance;
								},
								_search : function(id) {
									return this._taskPool[id];
								},

								_load : function(id) {

								},
								
								// core server
								createChart: function(chart){
									var self = this;
									var deferred = $q.defer();
									$http(
											{
												method : 'POST',
												url : '/api/v1/_save',
												headers : {
													'Content-Type' : 'application/x-www-form-urlencoded'
												},
												data : $.param(task),
											}).success(function(resp) {
										self._tasks.push(resp);
										deferred.resolve(resp);
									});
									return deferred.promise;

								},
								
								updateChart: function(chart){
									
								},
								
								getById: function(id){
									var self = this;
									var deferred = $q.defer();

									$http( {
												method : 'GET',
												url : '/api/v1/'+id,
											}).success(function(resp) {
										//self._tasks.push(resp);
										deferred.resolve(resp);
									});
									return deferred.promise;

								},
								////////////////////////////////////////////////////
								//helpers
								addNewTask : function(task_severity) {
									var self = this;
									var deferred = $q.defer();
									var task = new Task({
										data : "",
										severity : task_severity
									});

									$http(
											{
												method : 'POST',
												url : '/api/v1/_save',
												headers : {
													'Content-Type' : 'application/x-www-form-urlencoded'
												},
												data : $.param(task),
											}).success(function(resp) {
										self._tasks.push(resp);
										deferred.resolve(resp);
									});
									return deferred.promise;
								},

								updateSeverity : function(id, severity) {
									var deferred = $q.defer();
									var i = 0;
									var self = this;
									var taskslen = self._tasks.length;
									for (i = 0; i < taskslen; i++) {
										if (self._tasks[i].id == id) {
											self._tasks[i].severity = severity;
											$http(
													{
														method : 'POST',
														url : '/api/v1/' + id,
														headers : {
															'Content-Type' : 'application/x-www-form-urlencoded'
														},
														data : $
																.param(self._tasks[i]),
													}).success(function(resp) {
												self._tasks[i] = resp;
												deferred.resolve(self._tasks);
											});
											break;
										}
									}
									return deferred.promise;
								},

								updateTask : function(task) {
									var deferred = $q.defer();
									var self = this;
									$http(
											{
												method : 'POST',
												url : '/api/v1/' + task.id,
												headers : {
													'Content-Type' : 'application/x-www-form-urlencoded'
												},
												data : $.param(task),
											  }).success(function(resp) {
												deferred.resolve(self._tasks);
											});
									return deferred.promise;
								},
								
								loadAllTasks : function() {
									var self = this;
									var deferred = $q.defer();
									$http.get('/api/v1/all').success(
											function(data) {
												while (self._tasks.length) { self._tasks.pop(); };
												data.forEach(function(item) {
													self._tasks.push(item);
												});
												deferred.resolve(self._tasks);
											});
									return deferred.promise;
								}, 
								
								
							};
						} ]);
