// generate blocks and load blockfile
var sitarhero = Block('div', 'sitarhero');
sitarhero
    .add(Block('block', 'intro')
        .add('text', 'title')
        .add ('text', 'author')
    )
    .add(Block('block', 'label')
        .add(Block('block', 1)
            .add('text', 1)
        )
        .on('click', function (e) {
            $(document.body).animate({
                scrollTop: $('#sitarhero').offset().top + 'px'
            }, 600);
            e.stopPropagation();
        })
    )
    .add('div', 'content')
;

// display window
$(document).ready(function () {
    sitarhero.load(function () {
        var content = sitarhero.child('content');
        // load markdown from github readme
        $.ajax({
            url: 'https://raw.githubusercontent.com/anuvgupta/sitarhero/master/README.md',
            success: function (data) {
                // resize window handler
                $(window).resize(function () {
                    var screenshot = $("img[alt='Sitar Hero Screenshot']");
                    if (window.innerWidth < 800)
                        content.css({
                            width: '92.6%',
                            borderRadius: '5px',
                            margin: '40px auto 0 auto'
                        });
                    else content.css({
                        width: '75%',
                        borderRadius: '10px',
                        margin: '40px auto'
                    });
                    if (window.innerWidth < 550)
                        screenshot.css('width', '95%');
                    else screenshot.css('width', 'auto');
                });
                var renderer = new marked.Renderer();
                content.html(marked(data, { renderer: renderer })).css('opacity', '1');
                sitarhero.fill(document.body);
                document.body.style.opacity = '1';
                $(window).resize();
                var index = window.location.href.indexOf('#');
                if (index != -1) {
                    var id = window.location.href.substring(index + 1);
                    if (document.getElementById(id) != null) {
                        $(document.body).animate({
                            scrollTop: $('#' + id).offset().top + 'px'
                        }, /* 700 */ 0);
                    }
                }
            }
        });
    }, 'app', 'jQuery');
});
