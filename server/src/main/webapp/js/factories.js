'use strict';

/* Services */

var ontimeFactories = angular.module('ontimeFactories', []);

ontimeFactories.factory('Task', [ "$q", function($q) {
	function Task(thatTask) {
		this.id = '';
		this.severity = 1;
		this.data = '';
		if (thatTask) {
			this.set(thatTask);
		}
		/*if (this.id == '') {
			this.id = Math.floor(Math.random() * 10000);
		}*/
	}
	;
	Task.prototype = {
		set : function(thatTask) {
			$.extend(this, thatTask);
		},
		update : function() {
			console.log('not implemented');
		},
		'delete' : function() {
			console.log('not implemented');
		}
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
									var i = 0;
									taskslen = self._tasks.lenght;
									for (i = 0; i < tasklen; i++) {
										if (self._tasks[i].id == id) {
											self._tasks[i].severity = severity;
											$http// .post('/api/v1/'+id,
													// task)
											(
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
								},

								loadAllTasks : function() {
									var self = this;
									var deferred = $q.defer();
									$http.get('/api/v1/all').success(
											function(data) {
												data.forEach(function(item) {
													self._tasks.push(item);
												});
												deferred.resolve(self._tasks);
											});
									return deferred.promise;
								}
							};
						} ]);
