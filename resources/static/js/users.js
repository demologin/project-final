const userUrl = "/api/admin/users";

// https://stackoverflow.com/a/5064235/548473
const ctx = {
    ajaxUrl: userUrl,
    pageName: "user"
}

function enable(chkbox, id) {
    var enabled = chkbox.is(":checked");

    $.ajax({
        url: `${userUrl}/${id}`,
        type: "PATCH",
        data: `enabled=${enabled}`,
        headers: { 'Authorization': 'Bearer ' + token }
    }).done(function () {
        chkbox.closest("tr").attr("enabled", enabled);
        successNoty(enabled ? "User enabled" : "User disabled");
    }).fail(function () {
        chkbox.prop("checked", !enabled);
    });
}

$(function () {
    makeEditable({
        "columns": [
            {
                "data": "firstName"
            },
            {
                "data": "lastName"
            },
            {
                "data": "displayName"
            },
            {
                "data": "email",
                "render": function (data, type, row) {
                    if (type === "display") {
                        return `<a href='mailto:${data}'>${data}</a>`;
                    }
                    return data;
                }
            },
            {
                "data": "roles"
            },
            {
                "data": "startpoint",
                "render": function (date, type, row) {
                    if (type === "display" && date) {
                        return date.substring(0, 10);
                    }
                    return date;
                }
            },
            {
                "data": "endpoint",
                "render": function (data, type, row) {
                    let enabled = data === null;
                    if (type === "display") {
                        return `<input type='checkbox' ${enabled ? "checked" : ""} onclick='enable($(this), ${row.id});'/>`;
                    }
                    return enabled;
                }
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
        ],
        "createdRow": function (row, data, dataIndex) {
            $(row).addClass("data-enabled"); // Added class "data-enabled" to <tr> elements
            if (data.endpoint) {
                $(row).attr("enabled", false);
            }
        }
    });
});
