$(document).ready(function() {
    'use strict';
    let base64 = document.getElementById("headPhoto").innerHTML;
    base64 = 'data:image/png;base64,' + base64;
    let img = document.createElement("img");
    img.src = base64;

    let head = document.getElementById('head'); //获得dom对象
    //$(head).html('<img src="data:image/png;base64' + base64 + '" alt="">');
    head.appendChild(img); //为dom添加子元素img
    document.getElementById("headPhoto").innerHTML = "";
});