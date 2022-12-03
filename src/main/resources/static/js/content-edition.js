/*
 * Created/Modified: 2022. This code was written or modified by Duberly Guarnizo.
 * Free distribution is allowed under the terms of the MIT licence.
 * ¿Comments? Write to duberlygfr@gmail.com.
 */

function editContent(contentId) {
    const $jsonField = $('#content_json');
    $.ajax({
        url: "/api/content/id/" + contentId, // We are using the API version of controller so Thymeleaf won't complain about template
        type: "GET",
        contentType: "application/json",
        success: function (result) {
            console.log("recuperado contenido");
            console.log(result['contentJson']);
            $jsonField.val(result['contentJson']);
        }
    });
    MicroModal.show('modal-1');
    const $updateButton = $('#btn-update-entity');
    const $deleteButton = $('#btn-delete-entity');
    const $title = $('#modal-1-title');
    $title.text('Editar');
    $updateButton.on('click', function (event) {
        updateContent(contentId);
    });
    $deleteButton.on('click', function (event) {
        deleteContent(contentId);
    });
}

function updateContent(contentId) {
    const contentData = $('#content_json').val();

    console.log("Modifying content...");
    const content = {
        'contentId': contentId,
        'contentJson': contentData
    }
    $.ajax({
        url: "/api/content/update/" + contentId, // We are using the API version of controller so Thymeleaf won't complain about template
        type: "PUT",
        data: JSON.stringify(content),
        contentType: "application/json",
        success: function (result) {
            console.log("contenido modificado");
            MicroModal.close('modal-1');
            window.location.reload();
        }
    });
}

function deleteContent(contentId) {
    $.ajax({
        url: "/api/content/delete/" + contentId, // We are using the API version of controller so Thymeleaf won't complain about template
        type: "DELETE",
        contentType: "application/json",
        success: function (result) {
            console.log("contenido eliminado: " + contentId);
            window.location.reload();
        }
    });
}