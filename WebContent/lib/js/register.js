var attr = [ 'role', 'firstname', 'lastname', 'gender', 'salutation',
    'birthdate', 'username', 'password', 'about_me' ];

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
      else if (res == "bad date") resultElem.html("Fix Your Computer's Time.");
      else resultElem.html("You did Something Bad.");
    }
  }
  $scope.register = function() {
    resultElem.html('');
    var data = {};
    for (var i = 0; i < attr.length; i++)
      data[attr[i]] = $('#' + attr[i]).val();
    $.post("register", data).done(result);
  };
} ]);

app.directive("legalAge", legalAge);

function legalAge() {
  return {
    restrict : 'A',
    require : 'ngModel',
    link : function($scope, $element, $attrs, ngModel) {
      $scope.$watch($attrs.ngModel, function(value) {
        $($element).datepicker({
          dateFormat : 'yy-mm-dd',
          yearRange : "-100:+0",
          changeMonth : true,
          changeYear : true,
          showOtherMonths : true,
          onSelect : function(date) {
            // var ageDifMs = Date.now() - new Date(date).getTime();
            // var ageDate = new Date(ageDifMs); // miliseconds from epoch
            // ageDate.setDate(ageDate.getDate() + 1);
            // ageDate.getUTCFullYear() - 1970;
            var yBday = new Date(date).getUTCFullYear();
            var yNow = new Date().getUTCFullYear();
            var age = yNow - yBday;
            var isValid = age >= parseInt($attrs.legalAge);
            ngModel.$setValidity("legalAge", isValid);
            ngModel.$setViewValue(date);
          }
        });
      });
    },
  }
}

var salutations = {
  Male : [ 'Mr', 'Sir', 'Senior', 'Count' ],
  Female : [ 'Miss', 'Ms', 'Mrs', 'Madame', 'Majesty', 'Seniora' ],
}

function fill_select_options(id, opts) {
  $(id).empty();
  for (var i = 0; i < opts.length; i++)
    $(id).append($('<option>', {
      value : opts[i],
      text : opts[i]
    }));
}

$(document).ready(function() {
  fill_select_options('#salutation', salutations.Male);
  $('#gender').change(function() {
    fill_select_options('#salutation', salutations[$('#gender').val()]);
  });
});