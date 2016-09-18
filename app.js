$(document).ready(function () {
    var sitarhero = $('#SitarHero');
    // resize window handler
    $(window).resize(function () {
        if (window.innerWidth < 800)
            sitarhero.css('width', '90%');
        else sitarhero.css('width', '70%');
    });
    // load markdown from github readme
    $.ajax({
        url: 'https://raw.githubusercontent.com/anuvgupta/sitarhero/master/README.md',
        success: function (data) {
            sitarhero.html(marked(data)).css('opacity', '1');
            $(window).resize();
            var index = window.location.href.indexOf('#');
            if (index != -1) {
                var id = window.location.href.substring(index + 1);
                if (document.getElementById(id) != null) {
                    $(document.body).animate({
                        scrollTop: $('#' + id).offset().top + 'px'
                    }, 700);
                }
            }
        }
    });
});
