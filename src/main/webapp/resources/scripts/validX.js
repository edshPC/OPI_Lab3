function pressX(id){
    (x === parseInt(id.trim().substr(8))) ? document.getElementById(id).checked = true : x = parseInt(id.trim().substr(8));
    for(let i=-3 ;i<=5; i++) if (id !== 'form:bcb'+i) document.getElementById('form:bcb'+i).checked = false;
    console.log('X = ' + x);
    document.getElementById('form:x').value = x;
}