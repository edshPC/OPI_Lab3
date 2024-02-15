var printer;
// var lastRR = 4;
window.onload = function () {
    printer = new Printer();
    printer.drawStart();
    printer.canvas.addEventListener('click', function (event) {
        printer.clickPoint(event)
    });
}


function checkboxValidate(value) {
    console.log("Method called!");
    let checkboxes = document.getElementsByClassName('checkbox');
    for (let checkbox of checkboxes) {
        checkbox.checked = false;
    }
    let idd = "r" + value;
    document.getElementById(idd).checked = true;
    // value.checked = true;
}

function clearBox() {
    let checkboxes = document.getElementsByClassName('checkbox');
    for (let checkbox of checkboxes) {
        checkbox.attr('checked', false);
    }
}