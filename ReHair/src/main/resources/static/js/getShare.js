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
            let s = document.getElementById("shares");
            let str = '<div class="blog-post" id="article">';
            for (let i=0; i<data.length; i++) {
                console.log(data[i].username);
                console.log(data[i].content);
                str = str + '<li>' + '<div id="' + data[i].seqid + data[i].userName + '">' + '<p> 用户名：' +
                    data[i].userName + '</p>'  +
                    '<p> 内容:' + data[i].text + '</p>'
                     + '<p> 时间:' + data[i].time + '</p>' ;
                for (let j=0; j<data[i].photos.length; j++) {
                    let base64 = 'data:image/png;base64,' + data[i].photos[j];
                    str = str + '<img src="' + base64 + '">';
                }
                str = str + '</div>' + '</li>';
            }
            str = str + '</div>';
            s.innerHTML = str;
        }
    })

});