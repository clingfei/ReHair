$(document).ready(function() {
    'use strict';
//username"),
//                     (String) result.get(i).get("time"),
//                     (String) result.get(i).get("content"),
//                     image,
//                     (int)result.get(i).get("count"),
//                     (int)result.get(i).get("seqid")

    var session = getUser();
    console.log(session);
    if(session === "") {
        window.location.href = '/login';
    }



    let url = window.location.pathname;
    console.log(url);
    let user = url.substring(7);
    console.log(user);
    $.ajax({
        method: "GET",
        url: "/userGetArticle",
        async: false,
        data: {username: user, start: 0, bias: 10},
        success: function (data) {
            console.log(data);
            let s = document.getElementById("shares");
            let str = '<div class="blog-post" id="article">';
            if (session != url) {
                for (let i=0; i<data.length; i++) {
                    str = str + '<li>' + '<div id="' + data[i].seqid + data[i].userName + '">' + '<p> 用户名：' +
                        '<a href="/share/' +
                        data[i].userName + '">' + data[i].userName + '</a></p>'  +
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
            else {
                for (let i=0; i<data.length; i++) {
                    str = str + '<li>' + '<div id="' + data[i].seqid + data[i].userName + '">' + '<p> 用户名：' +
                        '<a href="/share/' +
                        data[i].userName + '">' + data[i].userName + '</a></p>'  +
                        '<p> 内容:' + data[i].text + '</p>' +
                        '<p> 时间:' + data[i].time + '</p>' +
                        '<button type="button" class="btn btn-primary" id="' + data[i].seqid +
                        '" name="' + data[i].userName + '" onclick="deleteArticle(this)">删除' + '</button>';
                    for (let j=0; j<data[i].photos.length; j++) {
                        let base64 = 'data:image/png;base64,' + data[i].photos[j];
                        str = str + '<img src="' + base64 + '">';
                    }
                    str = str + '</div>' + '</li>';
                }
                str = str + '</div>';
                s.innerHTML = str;
            }

        }
    })

});

function deleteArticle(data) {
    'use strict';

    console.log(data.name);
    console.log(data.id);

}