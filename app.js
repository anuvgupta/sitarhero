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
        }
    });
});
