let requestData;
const taskUrlDash = "/api/task";
// https://stackoverflow.com/a/5064235/548473

const ctx = {
    ajaxUrl: taskUrlDash,
    pageName: "index"
}
window.onload = function () {
    setEventListeners();

}
function setEventListeners() {
    document.querySelectorAll('.task-body')
        .forEach(task =>
            task.addEventListener('click', openModal));

}
function openModal(evt) {
    const id = evt.target
        .closest('.task-body')
        .getAttribute('value');
    modalFill(id);

}

function modalFill(id) {
    form = $('#detailsForm');
    $.get(ctx.ajaxUrl + '/' + id, function (data) {
        requestData = data;
        $.each(data, function (key, valueKey) {
            let line = form[0][key];
            if (line) {
                line.value = valueKey;
            }
            if (line != null && line.type === 'checkbox') {
                line.checked = valueKey;
            }

        });
        $('#editRow').modal('show');
    });
}

function sendDashboardForm() {
    let data = form.serialize();
    $.ajax({
        type: "POST",
        url: ctx.ajaxUrl + '/form',
        data: data
    }).done(function () {
        $("#editRow").modal('hide');
        updateTable()
        successNoty("Record saved");
    });
}
