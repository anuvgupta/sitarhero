// custom blocks break, text, and image

// define break block
Block('break', function () { //function to create break block
    var block = Block('span'); //start off with a span block
    block.add(Block('br')); //add a line break to the span
    return block; //return the newly modified block
}, function (block, data) { //function to load data into break block
    var value = data('val'); //get block data 'val' (amount of breaks)
    if (value !== null) { //if val is null, don't change block
        //else add that many extra line breaks
        for (var i = 1; i < value; i++) block.add(Block('br'));
    }
    //prevent blocks from being added to this block
    block.add = function () {
        return block; //return block to allow chaining
    };
});

// define text block
Block('text', function () { //function to create text block
    var block = Block('span'); //start off with a span block
    //until data is loaded, span is blank, so do nothing
    return block; //return the newly modified block
}, function (block, data) { //function to load data into text block
    var value = data('val'); //get data 'val' (text of span)
    // if val is not null, add text to text block
    if (value != null) block.node().appendChild(document.createTextNode(value.replace('&nbsp;', ' ')));
    var html = data('html');
    if (html != null) block.html(html);
});

// define image block
Block('image', function () { //function to create image block
    var block = Block('div'); //start off with div block
    //prevent blocks from being added to this block
    block.add = function () {
        return block; //return block to allow chaining
    };
    //until data is loaded, image is blank, so do nothing
    return block; //return the newly modified block
}, function (block, data) { //function to load data into image block
    var src = data('src'); //get data 'src' (image source)
    var height = data('height'); //get data 'height'
    var width = data('width'); //get data 'width'
    var alt = data('alt');
    if (src != null) { //if src is not null
        //load background image
        block.css('background-image', "url('" + src + "')")
             .css('background-repeat', 'no-repeat')
             .css('background-size', 'contain');
    }
    //if height is not null, set image div height
    if (height !== null) block.css('height', height);
    else block.css('height', 'auto');
    //if width is not null, set image div width
    if (width !== null) block.css('width', width);
    else block.css('width', 'auto');
    //if alt is not null, set image div title
    if (alt !== null) block.attribute('title', alt);
});

Block('tab', function () {
    var block = Block('block');
    block.css({
        width: '180px',
        display: 'inline-table',
        font: '18px Arial',
        backgroundColor: 'rgba(255, 255, 255, 1)',
        borderRadius: '8px 8px 0 0',
        border: '1px solid #DDD',
        borderBottom: 'none',
        cursor: 'pointer',
        color: '#4C4C4C',
        margin: '0',
        opacity: '0.6'
    })
    .add('text', 1)
    .on('click', function () {
        var siblings = block.siblings();
        for (sibling in siblings) {
            if (siblings.hasOwnProperty(sibling))
                siblings[sibling].css('opacity', '0.6');
        }
        block.css('opacity', '1');
    });
    return block;
}, function (block, data, css) {
    block.child('text').html(data('val'));
    block.on('click', function () {
        var children = block.parent(1).child('content').children();
        for (child in children) {
            if (children.hasOwnProperty(child)) {
                if (child == block.mark())
                    children[child].on('show');
                else children[child].on('hide')
            }
        }
    });
});
