var app = angular.module('editinfo', []);

app.controller('myformController', [ '$scope', function($scope) {
  var resultElem = $('#result');
  var result = function(res) {
    if (res == "success") {
      resultElem.css("color", "green");
      resultElem.html("Successfully Edited.");
    } else if (res == "logout") {
      resultElem.css("color", "green");
      resultElem.html("Successfully Edited.");
      setTimeout(function() {
        window.location.href = "/";
      }, 1000);
    } else {
      resultElem.css("color", "red");
      resultElem.html("You did Something Bad.");
    }
  }
  $scope.editinfo = function() {
    resultElem.html('');
    $.post("editinfo", $('#myform').serialize()).done(result);
  };
} ]);

app.directive("legalAge", legalAge);
