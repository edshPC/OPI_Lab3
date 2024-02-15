function checkboxValidate(value) {
    let checkboxes = document.getElementsByClassName('checkbox');
    for (let checkbox of checkboxes) {
        checkbox.checked = false;
    }
    let idd = "r" + value;
    document.getElementById(idd).checked = true;
    // value.checked = true;
}