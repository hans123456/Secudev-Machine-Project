function setValue() {
  return {
    restrict : 'A',
    require : 'ngModel',
    link : function($scope, $element, $attrs, ngModel) {
      $scope[$attrs.ngModel] = $attrs.setValue;
    },
  }
}

app.directive('setValue', setValue);