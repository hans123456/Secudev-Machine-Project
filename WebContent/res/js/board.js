$(document).ready(function() {
  $('a.colorbox').colorbox({
    photo : true,
    maxWidth : '95%',
    maxHeight : '95%',
  });
});

var result = function(res) {
  if (res == "deleted") {
    alert("Post was deleted body someone.");
    location.reload();
  } else if (res == "success") {
    alert("Post Successfully Deleted.");
    location.reload();
  } else {
    alert("You Did Something Bad.");
  }
};

function deletePost(id) {
  var ans = confirm("Are you sure?");
  if (ans == true) {
    $.post('/user/deletepost', {
      'id' : id
    }).done(result)
  }
}