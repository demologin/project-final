const userUrl = "/api/task";

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
        successNoty(enabled ? "Task enabled" : "Task disabled");
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
                "data": "enabled",
                "render": function (data, type, row) {
                    if (type === "display") {
                        return "<input type='checkbox' " + (data ? "checked" : "") + " onclick='enable($(this)," + row.id + ");'/>";
                    }
                    return data;
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
            if (!data.enabled) {
                $(row).attr("data-user-enabled", false);
            }
        }
    });
});

document.querySelector(".checkbox-value").addEventListener('click', (evt) => {
    let checkbox = evt.target;
    let checked = checkbox.checked;
    checkbox.value = checked ? 'true' : 'false';
});
