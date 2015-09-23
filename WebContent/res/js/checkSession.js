(function() {

  function readCookie(name, c, C, i) {
    c = document.cookie.split('; ');
    var cookies = {};
    for (i = c.length - 1; i >= 0; i--) {
      C = c[i].split('=');
      cookies[C[0]] = C[1];
    }
    return cookies[name];
  }

  (function() {
    var id = readCookie('JSESSIONID');
    setInterval(function() {
      if (id != readCookie('JSESSIONID')) location.reload();
    }, 500);
  })();

})();
