'use strict';

/* Services */

var ontimeFactories = angular.module('ontimeFactories', [ ]);


ontimeFactories.factory('Task', ["$q",  function($q){
        function Task(data){
            this.id = '';
            this.severity=1;
            this.text = '';
            if(data) {
                this.set(data);
            }
            if (this.id == ''){
                this.id = Math.floor(Math.random() * 10000);
            }
        };
        Task.prototype = {
        		set: function(data){
                angular.extend(this, data);  
              },
              	update : function(){ 
            	console.log('not implemented');
              },
              'delete' : function(){ 
            	console.log('not implemented');
              }
        };
        return Task;
}]);


ontimeFactories.factory('TaskManager', ["$q", "Task", "$http",  function($q, Task, $http){
        return {
            _taskPool:{}, 
            _tasks:[],
            _mergeTask:function(id,data){
                var instance  =  this._search(id);
                if (instance){
                    instance.set(data);
                }else{
                    instance = new Task(data);
                    this._taskPool[id] = instance;
                }
                return instance;
            },
            _search: function(id){
                return  this._taskPool[id];
            }, 
            
            _load: function(id){
                
            },
            
            addNewTask:function(task_severity){
                var self = this;
                var deferred = $q.defer();
                var  task = new Task({ text:"new todo", severity:task_severity });
               //  self._mergeTask (task.id, task);
                 this._tasks.push(task);
                deferred.resolve(task.id);
                return deferred.promise;
            },
            
            getTask:function(id){ 
                var deferred = $q.defer();
                deferred.resolve(function(){
                    var  task = new Task({ text:"new todo", severity:task_severity });
                    _taskPool.push(task.id, task);
                })
                return deferred.promise;
            },
            
            loadAllTasks:function(){
                var self = this;
                var deferred = $q.defer();
                $http.get('/api/v1/all').success(function(data){
                    data.forEach(function(item){
                       self._tasks.push({id:item.id, text:item.data, severity: item.severity}); 
                    });
                    deferred.resolve( this._tasks);
                });
                return  deferred.promise;
            }
        };
}]);
