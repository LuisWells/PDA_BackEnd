let formInputs;
MicroModal.init();

function showCreationForm(graphId) {
    //TODO: add listeners to buttons to update visit details
    const $createGraph = $('#btn-create-graph'),
        $closeNewEntityBtn = $('#btn-close-new-graph-popup');

    const $form = document.getElementById("graph-creation-form-controls");
    const URL = "/api/graph/variables/" + graphId;
    const variableList = async () => {
        const response = await fetch(URL);
        return await response.json();
    }
    variableList().then(data => {
        console.log("Variables for ID: " + graphId);
        for (let dataKey in data) {
            const input = document.createElement("input");
            input.setAttribute("type", "text");
            input.setAttribute("id", data[dataKey]);
            input.setAttribute("placeholder", data[dataKey]);
            $form.append(input);
        }
        formInputs = document.querySelectorAll("#graph-creation-form-controls input");
        //create graph
        $createGraph.on('click', function (event) {
            let graphData = {};

            for (let input of formInputs) {
                graphData[input.id] = input.value;
            }
            console.log(graphData);
            console.log(graphData);
            const content_request = {"contentJson": JSON.stringify(graphData)};
            $.ajax({
                //create GRAPH
                url: "/api/content/create",
                type: "POST",
                data: JSON.stringify(content_request),
                contentType: "application/json",
                dataType: "json",
                success: function (result) {
                    //create LINK
                    console.log("Content created:");
                    console.log(result);
                    const contentId = result.contentId;
                    const link_path = graphId + "-" + contentId;
                    $form.replaceChildren(); //delete child nodes after successful creation
                    $.ajax({
                        url: "/api/link/create/",
                        type: "POST",
                        contentType: "application/json",
                        data: JSON.stringify({"path": link_path}),
                        dataType: "json",
                        success: function (result) {
                            console.log("Link created:");
                            console.log(result);
                            window.open("/artifact?/path=" + link_path, "_self");
                        }
                    });
                }
            });
        });
    });
    MicroModal.show('graph-creation-form');

    $closeNewEntityBtn.on('click', function (event) {
        MicroModal.close('graph-creation-form');
        console.log("Close button clicked");
        console.log($form);
        $form.replaceChildren();
    });
}



