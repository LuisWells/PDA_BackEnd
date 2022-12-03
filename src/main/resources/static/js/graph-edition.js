/*
 * Created/Modified: 2022. This code was written or modified by Duberly Guarnizo.
 * Free distribution is allowed under the terms of the MIT licence.
 * ¿Comments? Write to duberlygfr@gmail.com.
 */

function editGraph(graphId) {
    const $name = $('#graph_name');
    const $svg = $('#graph_svg');
    const $version = $('#graph_version');
    $.ajax({
        url: "/api/graph/id/" + graphId, // We are using the API version of controller so Thymeleaf won't complain about template
        type: "GET",
        contentType: "application/json",
        success: function (result) {
            $name.val(result['name']);
            $svg.val(result['svg']);
            $version.val(result['version']);
            const $updateButton = $('#btn-update-entity');
            const $createButton = $('#btn-create-entity');
            const $deleteButton = $('#btn-delete-entity');
            $createButton.hide();
            $updateButton.show();
            $deleteButton.show();
            $updateButton.on('click', function (event) {
                updateGraph(graphId);
            });
            $deleteButton.on('click', function (event) {
                deleteGraph(graphId);
            });
        }
    });
    MicroModal.show('modal-1');
}

function updateGraph(graphId) {

    const $name = $('#graph_name').val();
    const $svg = $('#graph_svg').val();
    const $version = $('#graph_version').val();
    console.log("Modifying content...");
    const graph = {
        'graphId': graphId,
        'name': $name,
        'svg': $svg,
        'version': $version
    }
    $.ajax({
        url: "/api/graph/update/" + graphId, // We are using the API version of controller so Thymeleaf won't complain about template
        type: "PUT",
        data: JSON.stringify(graph),
        contentType: "application/json",
        success: function (result) {
            console.log("contenido modificado");
            MicroModal.close('modal-1');
            window.location.reload();
        }
    });
}

function deleteGraph(graphId) {
    $.ajax({
        url: "/api/graph/delete/" + graphId, // We are using the API version of controller so Thymeleaf won't complain about template
        type: "DELETE",
        contentType: "application/json",
        success: function (result) {
            console.log("contenido eliminado: " + graphId);
            window.location.reload();
        }
    });
}