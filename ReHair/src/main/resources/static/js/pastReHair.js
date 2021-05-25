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


function getUser() {
    var res = "";
    $.ajax({
        method: "GET",
        url: "/getUser",
        async: false,
        success: function (data) {
            res = data;
        }
    });
    return res;
}

$(function() {
    // my_own_thing
    // 这里的重写有问题，需要返回到个人主页
    $('#my_own_thing').empty();
    var tmp = "<li>";
    tmp = tmp + "<a onclick=\"logout()\"> 退出登录 </a>"
    tmp = tmp + "</li>";

    $('#my_own_thing').append(tmp);

    tmp = "<li>";
    tmp = tmp + "<a href=\"/share\">动态圈</a>"
    tmp = tmp + "</li>";
    $('#my_own_thing').append(tmp);

    tmp = "<li>";
    tmp = tmp + "<a id=\"myShare\" href=\"\">我的动态</a>"
    tmp = tmp + "</li>";
    $('#my_own_thing').append(tmp);

    var session = getUser();
    tmp = tmp + '<li>';
    tmp = tmp + "<a id=\"personal_page\" href=\"/user/" + session + '"';
    tmp = tmp +  ">个人主页</a>"
    tmp = tmp + "</li>";
    $('#my_own_thing').append(tmp);

})
