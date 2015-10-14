(function() {

  var app;
  try {
    app = angular.module('post');
  } catch (e) {
    app = angular.module('post', []);
  }

  app.controller("searchformcontroller", [
      '$scope',
      '$compile',
      function($scope, $compile) {

        var i = 0;

        $scope.columnOptions = [ {
          value : 1,
          name : 'username',
        }, {
          value : 2,
          name : 'date',
        }, ];

        $scope.comparisonOptions = [ [ {
          value : 1,
          name : '=',
        } ], [ {
          value : 1,
          name : '>=',
        }, {
          value : 2,
          name : '<=',
        }, {
          value : 3,
          name : '=',
        }, {
          value : 4,
          name : 'between',
        } ] ];

        $scope.addAdvance = function() {
          var selectConstraint = "<select name='a'>"
              + "<option value='1'>AND</option>"
              + "<option value='2'>OR</option>" + "</select>";
          var selectColumn = "<select name='b' ng-init='column" + i
              + "=columnOptions[0]'" + "ng-model='column" + i + "'"
              + "ng-options='option.name for option in columnOptions "
              + "track by option.value' required></select>";
          var selectComparison = "<select name='c' " + "ng-model='comparison"
              + i + "'" + "ng-init='comparison" + i
              + "=comparisonOptions[column" + i + ".value-1][0]'"
              + "ng-change='updateComparison(" + i + ")' "
              + "ng-options='option.name for option "
              + "in comparisonOptions[column" + i + ".value-1] "
              + "track by option.value' required></select>{{test}}"
          var inputNormal = "<span ng-if='column" + i + ".value==1'>"
              + "<input type='text' name='i'" + "id='i" + (i + 1) + "'"
              + " required /></span>";
          var dateNormal = "<span ng-if='column" + i + ".value==2'>"
              + "<input type='text' name='i'" + "id='i" + (i + 1) + "'"
              + "placeholder='YYYY-MM-DD' required />" + "<script>$('#i"
              + (i + 1) + "')" + ".datepicker({dateFormat : 'yy-mm-dd', "
              + "yearRange : '-1000:+0', changeMonth : true, "
              + "changeYear : true, showOtherMonths : true, }"
              + "); </script></span>"
          var dateBetween = "<span ng-if='column" + i
              + ".value==2 && comparison" + i + ".value==4'>"
              + " and <input type='text' name='i'" + "id='i" + (i + 2) + "'"
              + "placeholder='YYYY-MM-DD' required />" + "<script>$('#i"
              + (i + 2) + "')" + ".datepicker({dateFormat : 'yy-mm-dd', "
              + "yearRange : '-1000:+0', changeMonth : true, "
              + "changeYear : true, showOtherMonths : true, }"
              + "); </script></span>"
          var append = "<div id='advance" + i + "' class='row'>"
              + selectConstraint + " " + selectColumn + " " + selectComparison
              + " " + inputNormal + dateNormal + dateBetween
              + " <input type='button' ng-click='removeAdvance(" + i + ")'"
              + "value='-'/></div>";
          append = $compile(append)($scope);
          $('#advance').append(append);
          $('#i' + (i + 2)).datepicker({
            dateFormat : 'yy-mm-dd',
            yearRange : "-1000:+0",
            changeMonth : true,
            changeYear : true,
            showOtherMonths : true,
          });
          i += 3;
        };

        $scope.removeAdvance = function(id) {
          $("#advance" + id).remove();
        };

        $scope.updateColumn = function(id) {
        };

      } ]);

})();