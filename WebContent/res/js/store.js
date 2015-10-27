$(document).ready(function() {
  $('a.colorbox').colorbox({
    photo : true,
    maxWidth : '95%',
    maxHeight : '95%',
  });
});

var app = angular.module('store', []);

app.controller('myformController', [ '$scope', function($scope) {
  console.debug("enter");
} ]);