/*
 * Created/Modified: 2022. This code was written or modified by Duberly Guarnizo.
 * Free distribution is allowed under the terms of the MIT licence.
 * ¿Comments? Write to duberlygfr@gmail.com.
 */
let oldPasswordHash = '';

function editAdmin(adminId) {
    const $name = $('#admin_name');
    const $email = $('#admin_email');
    const $active = $('#admin_active');
    const $password = $('#admin_password');
    const $passwordConfirm = $('#admin_password2');

    $.ajax({
        url: "/api/admin/id/" + adminId, // We are using the API version of controller so Thymeleaf won't complain about template
        type: "GET",
        contentType: "application/json",
        success: function (result) {
            $name.val(result['name']);
            $email.val(result['email']);
            $active.prop('checked', result['active']);
            $password.val('');
            $passwordConfirm.val('');
            oldPasswordHash = result['passwordHash'];
        }
    });
    const $updateButton = $('#btn-update-entity');
    const $createButton = $('#btn-create-entity');
    const $deleteButton = $('#btn-delete-entity');
    const $title = $('#modal-1-title');
    $title.text('Editar');
    $createButton.hide();
    $updateButton.show();
    $deleteButton.show();
    $updateButton.on('click', function (event) {
        updateAdmin(adminId);
    });
    $deleteButton.on('click', function (event) {
        deleteAdmin(adminId);
    });
    MicroModal.show('modal-1');
}

function updateAdmin(adminId) {
    const $name = $('#admin_name').val();
    const $email = $('#admin_email').val();
    const $active = $('#admin_active').prop('checked');
    const $password = $('#admin_password');
    const $password2 = $('#admin_password2');
    let admin = {};
    if ($password.val() === '') {
        admin = {
            'adminId': adminId,
            'name': $name,
            'email': $email,
            'active': $active,
            'passwordHash': oldPasswordHash
        }
        console.log("passwords are empty. Old password will be used: " + oldPasswordHash);
    } else {
        if ($password.val() !== $password2.val()) {
            alert("Las contraseñas no coinciden");
            return;
        } else {//change of password
            admin = {
                'adminId': adminId,
                'name': $name,
                'email': $email,
                'active': $active,
                'passwordHash': $password.val()
            }
            console.log("passwords are not empty");
        }
    }
    $.ajax({
        url: "/api/admin/update/" + adminId, // We are using the API version of controller so Thymeleaf won't complain about template
        type: "PUT",
        data: JSON.stringify(admin),
        contentType: "application/json",
        success: function (result) {
            console.log("contenido modificado");
            MicroModal.close('modal-1');
            window.location.reload();
        }
    });
}

function deleteAdmin(adminId) {
    $.ajax({
        url: "/api/admin/delete/" + adminId, // We are using the API version of controller so Thymeleaf won't complain about template
        type: "DELETE",
        contentType: "application/json",
        success: function (result) {
            console.log("contenido eliminado");
            MicroModal.close('modal-1');
            window.location.reload();
        }
    });
}