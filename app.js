var app = {
    block: Block('div', 'app'),
    username: 'anuvgupta',
    name: 'anuv gupta',
    reponame: 'sitarhero',
    reponame2: 'sitar hero',
    reponame3: 'Sitar Hero',
    dashedreponame: 'sitar-hero'
};
app.repourl = 'https://github.com/' + app.username + '/' + app.reponame;

// display window
$(document).ready(function () {
    document.title = app.reponame3;
    app.block.load(function () {
        var content = app.block.child('content');
        // load markdown from github readme
        $.ajax({
            url: 'https://raw.githubusercontent.com/' + app.username + '/' + app.reponame + '/master/README.md',
            success: function (data) {
                setTimeout(function () {
                    $("h1")[0].style.textAlign = 'left';
                    setTimeout(function () {
                        $("h1")[0].style.textAlign = 'left';
                    }, 100);
                }, 50);
                var renderer = new marked.Renderer();
                content.child('explore').html(marked(data, { renderer: renderer })).css('opacity', '1');
                app.block.fill(document.body);
                // document.body.innerHTML = app.block.node().innerHTML;
                app.block.child('tabs/explore').on('click', { noscroll: true });
                document.body.style.opacity = '1';
                Block.queries();
                var index = window.location.href.indexOf('#');
                if (index != -1) {
                    var id = window.location.hash;
                    if (id == '#source' || id == '#source-code')
                        app.block.child('tabs/github').on('click', { noscroll: true });
                    if (document.getElementById(id.substring(1)) != null) {
                        $(document.body).animate({
                            scrollTop: $(id).offset().top + 'px'
                        }, /* 700 */ 0);
                    }
                  }
            }
        });

        // load buttons script
        $.getScript({
            async: false,
            url: 'https://buttons.github.io/buttons.js'
        });
    }, 'app', 'jQuery');
});
