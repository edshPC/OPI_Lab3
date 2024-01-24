var x = 0;

function pressX(id) {
    console.log('x before: ' + x);
    (x === parseInt(id.trim().substr(8))) ? document.getElementById(id).checked = true : x = parseInt(id.trim().substr(8));
    for (let i = -5; i <= 1; i++) if (id !== 'mainForm:bcb' + i) document.getElementById('mainForm:bcb' + i).checked = false;
    console.log('X = ' + x);
    document.getElementById('mainForm:x').value = x;
}

function checkboxValidate(value) {
    let checkboxes = document.getElementsByClassName('checkbox');
    for (let checkbox of checkboxes) {
        checkbox.checked = false;
    }
    value.checked = true;
}