const $createEntity = $('#btn-create-entity'),
    $openNewEntityBtn = $('#btn-open-new-entity-popup'),
    $closeNewEntityBtn = $('#btn-close-new-entity-popup'),
    $title = $('#modal-1-title');

MicroModal.init();
$createEntity.on('click', function (event) {
    switch ($createEntity.prop('name')) {
        case 'createAdmin':
            createAdmin();
            break;
        case 'createGraph':
            createGraph();
            break;
    }
});

function createAdmin() {
    const $username = $('#admin_name'), $password = $('#admin_password'), $password2 = $('#admin_password2'),
        $email = $('#admin_email'), $active = $('#admin_active');
    console.log("Creating admin...Value of checkbox: " + $active.prop('checked'));
    if ($password.val() === $password2.val()) {
        const admin = {
            'name': $username.val(),
            'passwordHash': $password.val(),
            'email': $email.val(),
            'active': $active.prop('checked')
        }
        $.ajax({
            url: "/api/admin/create", // We are using the API version of controller so Thymeleaf won't complain about template
            type: "POST",
            data: JSON.stringify(admin),
            contentType: "application/json",
            success: function (result) {
                console.log("usuario creado");
                MicroModal.close('modal-1');
                window.location.reload();
            }
        });
    } else {
        console.log("Passwords don't match!");
        alert("Las contraseñas no coinciden!");
    }
}

function createGraph() {
    const graph_name = $('#graph_name').val();
    const graph_svg = $('#graph_svg').val();
    const graph_version = $('#graph_version').val();
    $title.text('Crear Definición de Gráfico');

    console.log("Modifying content...");
    const graph = {
        'name': graph_name,
        'svg': graph_svg,
        'version': graph_version
    }
    $.ajax({
        url: "/api/graph/create", // We are using the API version of controller so Thymeleaf won't complain about template
        type: "POST",
        data: JSON.stringify(graph),
        contentType: "application/json",
        success: function (result) {
            console.log("gráfico creado!");
            MicroModal.close('modal-1');
            window.location.reload();
        }
    });
}

$openNewEntityBtn.on('click', function (event) {
    const $updateButton = $('#btn-update-entity');
    const $createButton = $('#btn-create-entity');
    const $deleteButton = $('#btn-delete-entity');
    $createButton.show();
    $updateButton.hide();
    $deleteButton.hide();
    $title.text('Nuevo');

    MicroModal.show('modal-1');
    console.log("Opening modal...");
});
$closeNewEntityBtn.on('click', function (event) {
    MicroModal.close('modal-1');
    console.log("Closing modal");
});