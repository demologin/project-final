const userUrl = "/api/task";

// https://stackoverflow.com/a/5064235/548473
const ctx = {
    ajaxUrl: userUrl,
    pageName: "index"
}

window.onload = function() {
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
    const form = $('#detailsForm');
    fillTaskModal(id, form[0]);
}
