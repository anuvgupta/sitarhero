$(document).ready(function () {
    var heading = $('#heading');
    var screenshot = $('#screenshot');
    var sitarhero = $('#SitarHero');
    // resize window handler
    $(window).resize(function () {
        // resize content
        if (window.innerWidth < 800) {
            screenshot.css('margin-right', '2%')
                .css('margin-bottom', '-55px');
            sitarhero.css('width', '90%');
        } else {
            screenshot.css('margin-right', '5%')
                .css('margin-bottom', '-105px');
            sitarhero.css('width', '70%');
        }
        // resize heading
        var imgHeight = screenshot.height();
        heading.css('height', imgHeight + 'px');
        if (imgHeight < 250)
            heading.css('font-size', '60px');
        else if (imgHeight < 320)
            heading.css('font-size', '80px');
        else heading.css('font-size', '100px');
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
