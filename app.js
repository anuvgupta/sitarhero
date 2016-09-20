var sitarhero = Block('div', 'sitarhero');
sitarhero
    .add(Block('block', 'intro')
        .add('text', 'title')
        .add ('text', 'author')
    )
    .add(Block('block', 'tabs')
        .add(Block('tab', 'explore')
            .on('click', function (e) {
                if (e.detail.noscroll != true) {
                    $(document.body).animate({
                        scrollTop: $('#sitar-hero').offset().top + 'px'
                    }, 600);
                    e.stopPropagation();
                }
            })
        )
        .add('tab', 'github')
    )
    .add(Block('div', 'content')
        .add(Block('div', 'explore')
            .on('show', function () {
                sitarhero.child('content/explore').css('display', 'block');
            })
            .on('hide', function () {
                sitarhero.child('content/explore').css('display', 'none');
            })
        )
        .add(Block('div', 'github')
            .add('text', 'title')
            .add('text', 'textA')
            .add('text', 'textB')
            .add('text', 'textC')
            .add(Block('break').data(2))
            .add(Block('block', 'buttons')
                .add(Block('block', 1)
                    .add(Block('a', 'link')
                        .add('image', 1)
                    )
                    .add(Block('div', 'content')
                        .add('text', 1)
                    )
                )
            )
            .add(Block('break').data(2))
            .add('text', 'textD')
            .add('text', 'textE')
            .add(Block('break').data(2))
            .add(Block('block', 'author')
                .add(Block('block', 1)
                    .add(Block('a', 'link')
                        .add('image', 1)
                    )
                    .add(Block('div', 'content')
                        .add('text', 1)
                    )
                    .add(Block('div', 'follow')
                        .add('text', 1)
                    )
                )
            )
            .add(Block('break').data(2))
            .add(Block('div', 'footer')
                .add('text', 'textA')
                .add('text', 'textB')
                .add('text', 'textC')
            )
            .on('show', function () {
                sitarhero.child('content/github').css('display', 'table');
            })
            .on('hide', function () {
                sitarhero.child('content/github').css('display', 'none');
            })
        )
    )
    .add(Block('div', 'copyright')
        .add('text', 'textA')
        .add(Block('text', 'year').data((new Date()).getFullYear().toString()))
        .add('text', 'textB')
        .add(Block('break').data(2))
    )
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
                            margin: '40px auto 20px auto'
                        });
                    else content.css({
                        width: '75%',
                        borderRadius: '10px',
                        margin: '40px auto'
                    });
                    var tabs = sitarhero.child('tabs').children();
                    if (window.innerWidth < 550) {
                        screenshot.css('width', '95%');
                        sitarhero.child('intro/title').css({
                            'font-size': '100px',
                            'margin-bottom': '30px'
                        });
                        for (tab in tabs) tabs[tab].on('small');
                    } else {
                        screenshot.css('width', 'auto');
                        sitarhero.child('intro/title').css({
                            'font-size': '120px',
                            'margin-bottom': '20px'
                        });
                        for (tab in tabs) tabs[tab].on('big');
                    }
                });
                var renderer = new marked.Renderer();
                content.child('explore').html(marked(data, { renderer: renderer })).css('opacity', '1');
                sitarhero.fill(document.body);
                sitarhero.child('tabs/explore').on('click', { noscroll: true });
                document.body.style.opacity = '1';
                $("a[href='http://anuv.me/sitarhero']").parent().contents().filter(function() {
                    return this.nodeType == 3;
                }).last().remove();
                $("a[href='http://anuv.me/sitarhero']").remove();
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
        // load github buttons
        sitarhero.child('content/github/buttons/block/content')
            .html('<a class="github-button" href="https://github.com/anuvgupta/sitarhero" data-icon="octicon-star" data-style="mega" data-count-href="/anuvgupta/sitarhero/stargazers" data-count-api="/repos/anuvgupta/sitarhero#stargazers_count" data-count-aria-label="# stargazers on GitHub" aria-label="Star anuvgupta/sitarhero on GitHub">Star</a><br/>', true)
            .html('<a class="github-button" href="https://github.com/anuvgupta/sitarhero/fork" data-icon="octicon-repo-forked" data-style="mega" data-count-href="/anuvgupta/sitarhero/network" data-count-api="/repos/anuvgupta/sitarhero#forks_count" data-count-aria-label="# forks on GitHub" aria-label="Fork anuvgupta/sitarhero on GitHub">Fork</a><br/>', true)
            .html('<a class="github-button" href="https://github.com/anuvgupta/sitarhero" data-icon="octicon-eye" data-style="mega" data-count-href="/anuvgupta/sitarhero/watchers" data-count-api="/repos/anuvgupta/sitarhero#subscribers_count" data-count-aria-label="# watchers on GitHub" aria-label="Watch anuvgupta/sitarhero on GitHub">Watch</a><br/>', true)
        ;
        sitarhero.child('content/github/author/block/follow')
            .html('<a class="github-button" href="https://github.com/anuvgupta" data-style="mega" data-count-href="/anuvgupta/followers" data-count-api="/users/anuvgupta#followers" data-count-aria-label="# followers on GitHub" aria-label="Follow @anuvgupta on GitHub">Follow Me</a>', true)
        ;
        $.getScript('https://buttons.github.io/buttons.js');
    }, 'app', 'jQuery');
});
