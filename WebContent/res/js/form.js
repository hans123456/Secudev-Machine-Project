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

var genders = [ 'Male', 'Female' ];

var salutations = {
  Male : [ 'Mr', 'Sir', 'Senior', 'Count' ],
  Female : [ 'Miss', 'Ms', 'Mrs', 'Madame', 'Majesty', 'Seniora' ],
}
