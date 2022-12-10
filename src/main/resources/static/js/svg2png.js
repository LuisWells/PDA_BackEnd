/**
 * converts a svg string to base64 png using the domUrl
 * @param {string} svgText the svg text
 * @param {number} [margin=0] the width of the border - the image size will be height+margin by width+margin
 * @param {string} [fill] optionally background canvas fill
 * @return {Promise} a promise to the bas64 png image
 */
let svgToPng = function (svgText, margin, fill) {
    // convert a svg text to png using the browser
    return new Promise(function (resolve, reject) {
        try {
            // can use the domUrl function from the browser
            let domUrl = window.URL || window.webkitURL || window;
            if (!domUrl) {
                throw new Error("(browser doesnt support this)")
            }

            // figure out the height and width from svg text
            let match = svgText.match(/height=\"(\d+)/m);
            let height = match && match[1] ? parseInt(match[1], 10) : 200;
            match = svgText.match(/width=\"(\d+)/m);
            let width = match && match[1] ? parseInt(match[1], 10) : 200;
            margin = margin || 0;

            // it needs a namespace
            if (!svgText.match(/xmlns=\"/mi)) {
                svgText = svgText.replace('<svg ', '<svg xmlns="http://www.w3.org/2000/svg" ');
            }

            // create a canvas element to pass through
            let canvas = document.createElement("canvas");
            canvas.width = 800 + margin * 2; //modified by Duberly Guarnizo for this specific case
            canvas.height = 800 + margin * 2;
            let ctx = canvas.getContext("2d");


            // make a blob from the svg
            let svg = new Blob([svgText], {
                type: "image/svg+xml;charset=utf-8"
            });

            // create a dom object for that image
            let url = domUrl.createObjectURL(svg);

            // create a new image to hold it the converted type
            let img = new Image;

            // when the image is loaded we can get it as base64 url
            img.onload = function () {
                // draw it to the canvas
                ctx.drawImage(this, margin, margin);

                // if it needs some styling, we need a new canvas
                if (fill) {
                    let styled = document.createElement("canvas");
                    styled.width = canvas.width;
                    styled.height = canvas.height;
                    let styledCtx = styled.getContext("2d");
                    styledCtx.save();
                    styledCtx.fillStyle = fill;
                    styledCtx.fillRect(0, 0, canvas.width, canvas.height);
                    styledCtx.strokeRect(0, 0, canvas.width, canvas.height);
                    styledCtx.restore();
                    styledCtx.drawImage(canvas, 0, 0);
                    canvas = styled;
                }
                // we don't need the original anymore
                domUrl.revokeObjectURL(url);
                // now we can resolve the promise, passing the base64 url
                resolve(canvas.toDataURL());
            };

            // load the image
            img.src = url;

        } catch (err) {
            reject('failed to convert svg to png ' + err);
        }
    });
};
window.onload = function () {
    // get the svg text
    let svgText = document.getElementById("originalSVG").innerHTML;
    console.log(svgText);
    // convert it to png
    svgToPng(svgText, 5, 'white').then(function (base64) {
        // and display it

        const $link = document.getElementById("downloadLink");
        $link.href = base64;
        $link
    });
}