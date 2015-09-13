var getParameterByName = function(name) {
  name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
  var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"), results = regex
      .exec(location.search);
  return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g,
      " "));
}

var app = angular.module('post', []);

app.controller('myformController', [ '$scope', function($scope) {

  var resultElem = $('#result');

  var previewResult = function(res) {
    $('#PreviewText').html(res);
  };

  var createResult = function(res) {
    if (res == "success") {
      resultElem.css("color", "green");
      resultElem.html("Successfully Posted.");
      document.getElementById("myform").reset();
      setTimeout(function() {
        console.debug(getParameterByName('page'));
        if (parseInt(getParameterByName('page')) >= 1) {
          window.location.href = "/board.jsp";
        } else location.reload();
      }, 1000);
    } else {
      resultElem.css("color", "red");
      resultElem.html("You did Something Bad.");
    }
  };

  var editResult = function(res) {
    if (res == "success") {
      resultElem.css("color", "green");
      resultElem.html("Successfully Edited.");
      document.getElementById("myform").reset();
      setTimeout(function() {
        window.location.href = "/board.jsp";
      }, 1000);
    } else {
      resultElem.css("color", "red");
      if (res == "deleted") {
        resultElem.html("Post was deleted body someone.");
        setTimeout(function() {
          window.location.href = "/board.jsp";
        }, 1000);
      } else {
        resultElem.html("You did Something Bad.");
      }
    }
  };

  $scope.preview = function() {
    $.post('/previewpost', $('#myform').serialize()).done(previewResult);
    $("#PreviewPost").show();
  }

  $scope.createpost = function() {
    $.post('/createpost', $('#myform').serialize()).done(createResult);
  }

  $scope.editpost = function() {
    $.post('/editpost', $('#myform').serialize()).done(editResult);
  }

} ]);

$(document).ready(function() {
  $('a.colorbox').colorbox({
    photo : true,
    maxWidth : '95%',
    maxHeight : '95%',
  });
});