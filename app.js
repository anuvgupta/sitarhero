$(document).ready(function () {
    // resize window handler
    $(window).resize(function () {
        if (window.innerWidth < 800)
            $('#main').css('width', '90%');
        else $('#main').css('width', '70%');
    });
    // load markdown from github readme
    $.ajax({
        url: 'https://raw.githubusercontent.com/anuvgupta/sitarhero/master/README.md',
        success: function (data) {
            $('#main').html(marked(data));
            $(window).resize();
        }
    });
});
