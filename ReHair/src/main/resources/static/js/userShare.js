$(document).ready(function() {
    'use strict';
//username"),
//                     (String) datault.get(i).get("time"),
//                     (String) datault.get(i).get("content"),
//                     image,
//                     (int)datault.get(i).get("count"),
//                     (int)datault.get(i).get("seqid")

    var session = getUser();
    console.log("session:", session);
    if(session === "") {
        window.location.href = '/login';
    }



    let url = window.location.pathname;
    console.log("url:", url);
    let user = url.substring(7);
    console.log("user:", user);
    $.ajax({
        method: "GET",
        url: "/userGetArticle",
        async: false,
        data: {username: user, start: 0, bias: 10},
        success: function (data) {
            console.log(data);
            let s = document.getElementById("shares");
            let str = '<div class="blog-post" id="article">';
            if (session !== user) {
                for (let i=0; i<data.length; i++) {
                    str = str + '<li>' + '<div id="' + data[i].seqid + data[i].userName + '">' + '<p> 用户名：' +
                        '<a href="/share/' +
                        data[i].userName + '">' + data[i].userName + '</a>';
                    $.ajax({
                        method: "POST",
                        url: "/isFriend",
                        async: false,
                        data: {username: data[i].userName},
                        success: function (flag) {
                            console.log(flag);
                            if (flag === 1) {
                                str = str + '<button type="button" name="' + data[i].userName + '" onclick="unfollow(this)">unfollow </button></p>';
                            }
                            else if(flag === 2) {
                                str = str + '<button type="button" name="' + data[i].userName + '" onclick="follow(this)">follow </button></p>';
                            }
                            else str = str + '</p>';
                        }
                    });

                    str = str +
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

function deleteArticle(location) {
    'use strict';

    console.log(location.name);
    console.log(location.id);

    $.ajax({
        method: 'POST',
        url: "/deleteArticle",
        async:  false,
        data: {seqid: location.id},
        success: function(data) {
            if(!data.flag)
                alert(data.errorMsg);
            else {
                window.location.href = '/share/' + location.name;
            }
        }
    })
}