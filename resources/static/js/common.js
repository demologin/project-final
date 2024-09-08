let form;

function makeEditable(datatableOpts) {
    ctx.datatableApi = $("#datatable").DataTable(
        $.extend(true, datatableOpts, {
            "ajax": {
                "url": ctx.ajaxUrl,
                "dataSrc": "",
                "headers": {
                    'Authorization': 'Bearer ' + token
                }
            },
            "paging": false,
            "info": true,
            "language": {
                "search": "Search"
            }
        })
    );
    form = $('#detailsForm');

    $(document).ajaxError(function (event, jqXHR) {
        failNoty(jqXHR);
    });
}

function add() {
    $('#modalTitle').html('Add ' + ctx.pageName);
    form.find(":input").val("");
    $('#editRow').modal('show');
}

function updateRow(id) {
    form.find(":input").val("");
    $("#modalTitle").html('Edit ' + ctx.pageName);
    $.ajax({
        url: ctx.ajaxUrl + '/' + id,
        type: 'GET',
        headers: {
            'Authorization': 'Bearer ' + token
        },
        success: function (data) {
            $.each(data, function (key, value) {
                form.find("input[name='" + key + "']").val(value);
            });
            $('#editRow').modal('show');
        }
    });
}

function deleteRow(id) {
    if (confirm("Are you sure?")) {
        $.ajax({
            url: ctx.ajaxUrl + '/' + id,
            type: "DELETE",
            headers: {
                'Authorization': 'Bearer ' + token
            }
        }).done(function () {
            updateTable();
            successNoty("Record deleted");
        });
    }
}

function updateTable() {
    $.ajax({
        url: ctx.ajaxUrl,
        type: 'GET',
        headers: {
            'Authorization': 'Bearer ' + token
        },
        success: function (data) {
            ctx.datatableApi.clear().rows.add(data).draw();
        }
    });
}

function save() {
    $.ajax({
        type: "POST",
        url: ctx.ajaxUrl + '/form',
        data: form.serialize(),
        headers: {
            'Authorization': 'Bearer ' + token
        }
    }).done(function () {
        $("#editRow").modal('hide');
        updateTable();
        successNoty("Record saved");
    });
}

let failedNote;

function closeNoty() {
    if (failedNote) {
        failedNote.close();
        failedNote = undefined;
    }
}

function successNoty(key) {
    closeNoty();
    new Noty({
        text: "<span class='fa fa-lg fa-check'></span> &nbsp;" + key,
        type: 'success',
        layout: "bottomRight",
        timeout: 1000
    }).show();
}

function renderEditBtn(data, type, row) {
    if (type === "display") {
        return "<a onclick='updateRow(" + row.id + ");'><span class='fa fa-pencil'></span></a>";
    }
}

function renderDeleteBtn(data, type, row) {
    if (type === "display") {
        return "<a onclick='deleteRow(" + row.id + ");'><span class='fa fa-remove'></span></a>";
    }
}

function failNoty(jqXHR) {
    closeNoty();
    var errorInfo = jqXHR.responseJSON;
    failedNote = new Noty({
        text: "<span class='fa fa-lg fa-exclamation-circle'></span> &nbsp;" + errorInfo.title + "<br>" +
            (errorInfo.invalid_params ? JSON.stringify(errorInfo.invalid_params) : errorInfo.detail),
        type: "error",
        layout: "bottomRight"
    });
    failedNote.show();
}
