$(document).ready(function() {
    'use strict';

    let s = document.getElementById("photos");
    $.ajax({
        method: "GET",
        url: "/postReHair",
        async: false,
        success: function(data) {
            console.log(data);
            var str = '<div class="blog-post" id="article">';
            for (let i=0; i<data.length; i++) {
                str = str + '<li><p>脸型: ' + data[i].faceType + '</p>' +
                    '<p>发型: ' + data[i].hairType + '</p>' +
                    '<p>得分: ' + data[i].score + '</p>' +
                    '<img  src="data:image/png;base64,' + data[i].image +
                    '" alt="" height="200" width="200"></li>';
            }
            str = str + '</div>';
            s.innerHTML = str;
        }
    })
})