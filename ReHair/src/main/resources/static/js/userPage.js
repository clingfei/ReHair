$(document).ready(function() {
    'use strict';

    var session = getUser();
    console.log(session);
    if(session === "") {
        window.location.href = '/login';
    }

    let url = window.location.pathname;
    console.log(url);
    let user = url.substring(6);
    document.getElementById("myShare").setAttribute("href", "/share/" + user);

    let base64 = document.getElementById("headPhoto").innerHTML;
    base64 = 'data:image/png;base64,' + base64;
    let img = document.createElement("img");
    img.src = base64;
    img.style.height= "50px";
    img.style.width = "50px";
    let head = document.getElementById('head');
    head.appendChild(img);
    document.getElementById("headPhoto").innerHTML = "";


});