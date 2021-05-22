$(document).ready(function() {
    'use strict';
//username"),
//                     (String) result.get(i).get("time"),
//                     (String) result.get(i).get("content"),
//                     image,
//                     (int)result.get(i).get("count"),
//                     (int)result.get(i).get("seqid")
    $.ajax({
        method: "GET",
        url: "/getArticle",
        async: false,
        data: {start: 0, bias: 10},
        success: function (data) {
            console.log(data);
            let s = document.getElementById("share");
            let str = '<div class="blog-post" id="article">';
            for (let i=0; i<data.length; i++) {
                str = '<li>' + '<div id="' + data[i].seqid + data[i].username + '">' + data[i].username +
                    data[i].content + data[i].count + data[i].time +
                    '</div>' +  data.image + '</li>';
                for (let j = 0; j<data[i].photos.length; j++) {
                    let base64 = 'data:image/png;base64,' + data[i].photos[j];
                    let img = document.createElement("img");
                    img.src = base64;
                    let head = document.getElementById(data[i].seqid+data[i].username);
                    head.appendChild(img);
                }
            }
            str = str + '</div>'
            s.innerHTML = str;
        }
    })

});