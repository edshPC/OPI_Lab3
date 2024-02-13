function checkboxValidate(value) {
    let checkboxes = document.getElementsByClassName('checkbox');
    for (let checkbox of checkboxes) {
        checkbox.checked = false;
    }
    value.checked = true;
}