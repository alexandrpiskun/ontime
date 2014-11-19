'use strict';

/* Directives */
var ontimeDirectives = angular.module('ontimeDirectives', [ ]);

var Draggable = function () {
 
    return {
        restrict: "A",
        link: function(scope, element, attributes, ctlr) {
            element.attr("draggable", true);
 
            element.bind("dragstart", function(eventObject) {
                eventObject.originalEvent.dataTransfer.setData("text", attributes.itemid);
            });
        }
    };
}
ontimeDirectives.directive("ddDraggable", Draggable);

var DropTarget= function () {
 
    return {
        restrict: "A",
        link: function (scope, element, attributes, ctlr) {
 
            var dropid = parseInt(attributes.dropid);
            element.bind("dragover", function(eventObject){
                eventObject.preventDefault();
            });
 
            element.bind("drop", function(eventObject) {
                 
                // invoke controller/scope move method
                scope.moveToBox(parseInt(eventObject.originalEvent.dataTransfer.getData("text")), dropid);
 
                // cancel actual UI element from dropping, since the angular will recreate a the UI element
                eventObject.preventDefault();
            });
        }
    };
}

ontimeDirectives.directive("ddDropTarget", DropTarget);


var TaskItem= function () {
 
    return {
        restrict: "E",
        scope:{t:"="}, 
        link: function (scope, element, attributes, ctlr) {
            // use tags
            var tag  = "<div class='task'><span style='background-color: black; color: white; float: left'>...</span> {{text}} {{severity}}</div>";
            
            //var tag ="<h6>TAG</h6>";
            element.append(tag);
        }
    };
}

ontimeDirectives.directive("taskItem", TaskItem);




