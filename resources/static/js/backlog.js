const userUrl = "/task";

// https://stackoverflow.com/a/5064235/548473
const ctx = {
    ajaxUrl: userUrl,
    pageName: "backlog"
}

function enable(chkbox, id) {
    var enabled = chkbox.is(":checked");
//  https://stackoverflow.com/a/22213543/548473
    $.ajax({
        url: userUrl + '/' + id,
        type: "PATCH",
        data: "enabled=" + enabled
    }).done(function () {
        chkbox.closest("tr").attr("data-user-enabled", enabled);
        successNoty(enabled ? "User enabled" : "User disabled");
    }).fail(function () {
        $(chkbox).prop("checked", !enabled);
    });
}

$(function () {
    makeEditable({
        "columns": [
            {
                "data": "id"
            },
            {
                "data": "statusCode"
            },
            {
                "data": "title"
            },
            {
                "data": "description",
            },
            {
                "data": "typeCode"
            },
            {
                "orderable": false,
                "defaultContent": "",
                "render": renderEditBtn
            },
            {
                "orderable": false,
                "defaultContent": "",
                "render": renderDeleteBtn
            }
        ],
        "order": [
            [
                0,
                "asc"
            ]
        ]
    });
});
