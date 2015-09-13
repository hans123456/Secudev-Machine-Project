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
            // var ageDate = new Date(ageDifMs); // miliseconds from
            // epoch
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