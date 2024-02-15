var printer;

// var lastRR = 4;
window.onload = function () {
    printer = new Printer();
    printer.drawStart();
    printer.canvas.addEventListener('click', function (event) {
        printer.clickPoint(event)
    });
}

$(window).bind('load',function () {
    let checkboxes = document.getElementsByClassName('checkbox');
    for (let checkbox of checkboxes) {
        checkbox.checked = false;
    }
    document.getElementById("mainForm:r0").checked = true;
});

let checkboxes = document.getElementsByClassName('checkbox');
for (let checkbox of checkboxes) {
    checkbox.addEventListener('click', function checkboxValidate(event) {
        // console.log("Method called!");
        for (let checkbox of checkboxes) {
            checkbox.checked = false;
        }
        event.target.checked = true;
        // elem.checked = true;
    });
}

// function checkboxValidate(elem) {
//     console.log("Method called!");
//     // let checkboxes = document.getElementsByClassName('checkbox');
//     for (let checkbox of checkboxes) {
//         checkbox.checked = false;
//     }
//     // let idd = "r" + value;
//     elem.checked = true;
//     // value.checked = true;
// }

function clearBox() {
    let checkboxes = document.getElementsByClassName('checkbox');
    for (let checkbox of checkboxes) {
        checkbox.attr('checked', false);
    }
}