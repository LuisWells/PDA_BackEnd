/*
	Multiverse by HTML5 UP
	html5up.net | @ajlkn
	Free for personal and commercial use under the CCA 3.0 license (html5up.net/license)
*/

(function ($) {
    function jsonToVisit(json_data) {
        return {
            "browser": json_data.user_agent.name,
            "browserVersion": json_data.user_agent.version,
            "city": json_data.location.city,
            "cookie_exists": false,
            "country": json_data.location.country.name,
            "device": json_data.user_agent.device.type,
            "os": json_data.user_agent.os.name,
            "clicked_download_graph": false,
            "clicked_generate_output": false,
            "clicked_select_graph_type": false,
            "clicked_select_output": false,
            "copied_share_link": false,
            "from_share_link": false, //TODO: implement URL recognition
            "had_interactions": false,
            "typed_graph_content": false,
            "was_admin": false,
        }
    }

    const $window = $(window),
        $body = $('body'),
        $wrapper = $('#wrapper');

    // Breakpoints.
    breakpoints({
        xlarge: ['1281px', '1680px'],
        large: ['981px', '1280px'],
        medium: ['737px', '980px'],
        small: ['481px', '736px'],
        xsmall: [null, '480px']
    });

    // Hack: Enable IE workarounds.
    if (browser.name === 'ie')
        $body.addClass('ie');

    // Touch?
    if (browser.mobile)
        $body.addClass('touch');

    // Transitions supported?
    // Notice that API requests are unsupported in browsers that don't support transitions.
    if (browser.canUse('transition')) {

        // Play initial animations on page load.
        $window.on('load', function () {
            window.setTimeout(function () {
                $body.removeClass('is-preload');
            }, 100);
            console.log("load event!");
            const API_URL = "https://api.ipregistry.co/?key=rhc99920r7mix3z9";
            //get the user's IP address and data from the API only in main page
            if (window.location.pathname === "/") {
                const response = async () => {
                    const result = await fetch(API_URL);
                    const data = await result.json();
                    console.log(data);
                    return data;

                }
                //create a visit object from the data
                response().then(data => {
                    const visit = jsonToVisit(data);
                    console.log(visit);
                    //send the visit object to the server
                    $.ajax({
                        url: "/api/visit/create",
                        type: "POST",
                        data: JSON.stringify(visit),
                        contentType: "application/json",
                        dataType: "json",
                        success: function (result) {
                            console.log("Visita creada:")
                            console.log(result);
                        }
                    });
                });
            } else {
                console.log("Not main page!");
            }
        });

        // Prevent transitions/animations on resize.
        let resizeTimeout;

        $window.on('resize', function () {

            window.clearTimeout(resizeTimeout);

            $body.addClass('is-resizing');

            resizeTimeout = window.setTimeout(function () {
                $body.removeClass('is-resizing');
            }, 100);

        });

    }

    // Scroll back to top.
    $window.scrollTop(0);

    // Panels.
    let $panels = $('.panel');

    $panels.each(function () {

        let $this = $(this),
            $toggles = $('[href="#' + $this.attr('id') + '"]'),
            $closer = $('<div class="closer" />').appendTo($this);

        // Closer.
        $closer
            .on('click', function (event) {
                $this.trigger('---hide');
            });

        // Events.
        $this
            .on('click', function (event) {
                event.stopPropagation();
            })
            .on('---toggle', function () {

                if ($this.hasClass('active'))
                    $this.triggerHandler('---hide');
                else
                    $this.triggerHandler('---show');

            })
            .on('---show', function () {

                // Hide other content.
                if ($body.hasClass('content-active'))
                    $panels.trigger('---hide');

                // Activate content, toggles.
                $this.addClass('active');
                $toggles.addClass('active');

                // Activate body.
                $body.addClass('content-active');

            })
            .on('---hide', function () {

                // Deactivate content, toggles.
                $this.removeClass('active');
                $toggles.removeClass('active');

                // Deactivate body.
                $body.removeClass('content-active');

            });

        // Toggles.
        $toggles
            .removeAttr('href')
            .css('cursor', 'pointer')
            .on('click', function (event) {

                event.preventDefault();
                event.stopPropagation();

                $this.trigger('---toggle');

            });

    });

    // Global events.
    $body
        .on('click', function (event) {

            if ($body.hasClass('content-active')) {

                event.preventDefault();
                event.stopPropagation();

                $panels.trigger('---hide');

            }

        });

    $window
        .on('keyup', function (event) {

            if (event.keyCode === 27
                && $body.hasClass('content-active')) {

                event.preventDefault();
                event.stopPropagation();

                $panels.trigger('---hide');

            }

        });


    // Header.
    const $header = $('#header');

    // Links.
    $header.find('a').each(function () {

        let $this = $(this),
            href = $this.attr('href');

        // Internal link? Skip.
        if (!href
            || href.charAt(0) === '#')
            return;

        // Redirect on click.
        $this
            .removeAttr('href')
            .css('cursor', 'pointer')
            .on('click', function (event) {

                event.preventDefault();
                event.stopPropagation();

                window.location.href = href;

            });

    });

    // Footer.
    const $footer = $('#footer');

    // Copyright.
    // This basically just moves the copyright line to the end of the *last* sibling of its current parent
    // when the "medium" breakpoint activates, and moves it back when it deactivates.
    $footer.find('.copyright').each(function () {

        let $this = $(this),
            $parent = $this.parent(),
            $lastParent = $parent.parent().children().last();

        breakpoints.on('<=medium', function () {
            $this.appendTo($lastParent);
        });

        breakpoints.on('>medium', function () {
            $this.appendTo($parent);
        });

    });

    // Main.
    const $main = $('#main');

    // Thumbs.
    $main.children('.thumb').each(function () {

        let $this = $(this),
            $image = $this.find('.image'), $image_img = $image.children('img'),
            x;

        // No image? Bail.
        if ($image.length === 0)
            return;

        // Image.
        // This sets the background of the "image" <span> to the image pointed to by its child
        // <img> (which is then hidden). Gives us way more flexibility.

        // Set background.
        $image.css('background-image', 'url(' + $image_img.attr('src') + ')');

        // Set background position.
        if (x = $image_img.data('position'))
            $image.css('background-position', x);

        // Hide original img.
        $image_img.hide();

    });

    // Poptrox.
    $main.poptrox({
        baseZIndex: 20000,
        caption: function ($a) {

            let s = '';

            $a.nextAll().each(function () {
                s += this.outerHTML;
            });

            return s;

        },
        fadeSpeed: 300,
        onPopupClose: function () {
            $body.removeClass('modal-active');
        },
        onPopupOpen: function () {
            $body.addClass('modal-active');
        },
        overlayOpacity: 0,
        popupCloserText: '',
        popupHeight: 150,
        popupLoaderText: '',
        popupSpeed: 300,
        popupWidth: 250,
        popupTextColor: '#000000',
        selector: '.thumb > a.image',
        usePopupCaption: true,
        usePopupCloser: true,
        usePopupDefaultStyling: false,
        usePopupForceClose: true,
        usePopupLoader: true,
        usePopupNav: true,
        windowMargin: 50
    });

    // Hack: Set margins to 0 when 'xSmall' activates.
    breakpoints.on('<=xsmall', function () {
        $main[0]._poptrox.windowMargin = 0;
    });

    breakpoints.on('>xsmall', function () {
        $main[0]._poptrox.windowMargin = 50;
    });

})(jQuery);