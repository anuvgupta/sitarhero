$(document).ready(function () {
    var heading = $('#heading');
    var A = $('#heading #A');
    var B = $('#heading #B');
    var author = $('#heading #B');
    var screenshot = $('#screenshot');
    var sitarhero = $('#SitarHero');
    // resize window handler
    $(window).resize(function () {
        // resize content
        if (window.innerWidth < 800) {
            screenshot.css('margin-right', '2%')
                .css('margin-bottom', '-125px');
            sitarhero.css('width', '90%');
        } else {
            screenshot.css('margin-right', '5%')
                .css('margin-bottom', '-30%');
            sitarhero.css('width', '75%');
        }
        // resize heading
        var imgHeight = screenshot.height();
        heading.css('height', imgHeight + 'px');
        var fontsize = 100;
        if (imgHeight < 250) fontsize = 60;
        else if (imgHeight < 320) fontsize = 80;
        A.css('font-size', fontsize + 'px');
        B.css('font-size', fontsize * (3/5) + 'px');
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
