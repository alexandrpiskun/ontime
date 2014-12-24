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
        template:
                "<div class='task' style='display: flex; float: left' dd-draggable='true' itemid='{{t.id}}' > \n\
                 <div style=' flex:1; background: black; color: white; float: left; width:1em'>...</div>\n\
                 <div style=' flex:4; padding-left:1em;' contenteditable='true' ng-model='t.text'/>\n\
                 </div></div>"
    };
};

ontimeDirectives.directive("taskItem", TaskItem);


var contenteditable = function() {
  return {
    restrict: "A",
    require: "ngModel",
    link: function(scope, element, attrs, ngModel) {

      function read() {
        ngModel.$setViewValue(element.html());
      }

      ngModel.$render = function() {
        element.html(ngModel.$viewValue || "");
      };

      element.bind("blur keyup change", function() {
        scope.$apply(read);
      });
    }
  };
};

ontimeDirectives.directive("contenteditable", contenteditable);


