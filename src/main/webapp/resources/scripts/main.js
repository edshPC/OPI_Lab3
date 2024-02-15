var printer;

// var lastRR = 4;
window.onload = function () {
    printer = new Printer();
    printer.drawStart();
    printer.canvas.addEventListener('click', function (event) {
        printer.clickPoint(event)
    });
}

$(window).bind('load', function () {
    let checkboxes = document.getElementsByClassName('checkbox');
    for (let checkbox of checkboxes) {
        checkbox.checked = false;
    }
    document.getElementById("mainForm:r0").checked = true;
});

let checkboxes = document.getElementsByClassName('checkbox');
for (let checkbox of checkboxes) {
    checkbox.addEventListener('click', function checkboxValidate(event) {
        for (let checkbox of checkboxes) {
            checkbox.checked = false;
        }
        event.target.checked = true;
        // elem.checked = true;
    });
}

let rbuttons = document.getElementsByClassName('r-button');
for (let rbutton of rbuttons) {
    rbutton.addEventListener('click', function (event) {
        let buttons = document.querySelectorAll('input[class="r-button"]');
        for (let button of buttons) {
            button.style.backgroundColor = '#fffff4';
        }
        event.target.style.backgroundColor = '#c6aec7';
    })
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