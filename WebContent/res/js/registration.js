var app = angular.module('register', []);

app.controller('myformController', [ '$scope', function($scope) {
  var resultElem = $('#result');
  var result = function(res) {
    if (res == "success") {
      resultElem.css("color", "green");
      resultElem.html("Successfully Registered.");
      document.getElementById("myform").reset();
    } else {
      resultElem.css("color", "red");
      if (res == "fail") resultElem.html("Username Already Taken.");
      else resultElem.html("You did Something Bad.");
    }
  }
  $scope.register = function() {
    resultElem.html('');
    $.post("/register", $('#myform').serialize()).done(result);
  };
  $scope.genders = genders;
  $scope.salutations = salutations;
} ]);

app.directive("legalAge", legalAge);
