$(document).ready(function() {
    'use strict';
    let base64 = document.getElementById("headPhoto").innerHTML;
    base64 = 'data:image/png;base64,' + base64;
    let img = document.createElement("img");
    img.src = base64;

    let head = document.getElementById('head');
    head.appendChild(img);
    document.getElementById("headPhoto").innerHTML = "";
});