function pressX(id) {
    (x === parseInt(id.trim().substr(8))) ? document.getElementById(id).checked = true : x = parseInt(id.trim().substr(8));
    for (let i = -5; i <= 1; i++) if (id !== 'mainForm:bcb' + i) document.getElementById('mainForm:bcb' + i).checked = false;
    console.log('X = ' + x);
    document.getElementById('mainForm:x').value = x;
}