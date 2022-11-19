let formInputs;
MicroModal.init();

function showCreationForm(graphId) {
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
                //graphData=graphData.concat('"'+input.id + '": "' + input.value+'",');
            }
            //graphData=graphData.concat("}");
            console.log(graphData);
            console.log(graphData);
            const content_request = {"contentJson": JSON.stringify(graphData)};
            $.ajax({
                url: "/api/content/create",
                type: "POST",
                data: JSON.stringify(content_request),
                contentType: "application/json",
                dataType: "json",
                success: function (result) {
                    console.log("Content created:");
                }
            });
            //create link
        });
        MicroModal.show('graph-creation-form');
    });

    $closeNewEntityBtn.on('click', function (event) {
        MicroModal.close('graph-creation-form');
        $form.empty();
    });
}



