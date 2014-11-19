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
              update:function(){ console.log('not implemented');},
              delete:function(){ console.log('not implemented');}
        }
        return Task;
}]);


ontimeFactories.factory('TaskManager', ["$q", "Task",  function($q, Task){
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
                var deferred = $q.defer();
                        deferred.resolve( this._tasks
                                /*[
                                new Task({text:"task1", severity:4}),
                                new Task({text:"task2", severity:3}),
                                new Task({text:"task3", severity:3}),
                                new Task({text:"task4", severity:2}),
                                new Task({text:"task5", severity:2}),
                                new Task({text:"task6", severity:2}),
                                new Task({text:"task7", severity:1}),
                                new Task({text:"task8", severity:1}),
                                new Task({text:"task9", severity:1}),
                                ]*/);
                return  deferred.promise;
            }
        };
}]);
