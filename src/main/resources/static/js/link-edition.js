/*
 * Created/Modified: 2022. This code was written or modified by Duberly Guarnizo.
 * Free distribution is allowed under the terms of the MIT licence.
 * ¿Comments? Write to duberlygfr@gmail.com.
 */

function deleteLink(linkId) {
    $.ajax({
        url: "/api/link/delete/" + linkId, // We are using the API version of controller so Thymeleaf won't complain about template
        type: "DELETE",
        contentType: "application/json",
        success: function (result) {
            console.log("link eliminado: " + linkId);
            window.location.reload();
        }
    });
}