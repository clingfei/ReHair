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
            // console.log(data);
            let s = document.getElementById("shares");
            // 最外层div
            let str = '<div class="blog-post" id="article" style=\"text-align:center;\">';

            if (session !== user) {  // 不是user的内容 不知道是什么
                for (let i=0; i<data.length; i++) {

                    str = str + '<li class=/"rows/">';

                    // 单纯的用户名部分
                    str = str + "<div class = \"col-md-3\" style=\"height:100px;width:auto; margin:auto;\">";
                    str = str + '<div id="' + data[i].seqid + data[i].userName + '">' +

                        '<p> 用户名：' +

                        '<a href="/share/' +
                        data[i].userName + '">' + data[i].userName + '</a>';

                    // 这里有两个按钮。
                    $.ajax({
                        method: "POST",
                        url: "/isFriend",
                        async: false,
                        data: {username: data[i].userName},
                        success: function (flag) {
                            console.log(flag);
                            if (flag === 1) {
                                str = str + '<button type="button" name="' + data[i].userName + '" onclick="unfollow(this)">unfollow </button>' + '</p>';
                            }
                            else if(flag === 2) {
                                str = str + '<button type="button" name="' + data[i].userName + '" onclick="follow(this)">follow </button>' + '</p>';
                            }
                            else str = str + '</p>';
                        }
                    });
                    str = str + '</div>';
                    str = str + "</div>";

                    /*
                    str = str + "<div class = \"col-md-3\" style=\"height:100px;width:auto; margin:auto;\">";
                    str = str + '<p> 时间:\n' + data[i].time + '</p>';
                    str = str + "</div>";
                     */
                    // 内容和时间栏目
                    str = str + "<div class = \"col-md-3\" style=\"height:100px;width:auto; margin:auto;\">";
                        str = str + '<p> 内容:' + data[i].text + '</p>'
                        + '<p> 时间:' + data[i].time + '</p>' ;
                    str = str + "</div>";


                    // 图片栏目

                    for (let j=0; j<data[i].photos.length; j++) {
                        let base64 = 'data:image/png;base64,' + data[i].photos[j];

                        str = str + "<div class = \"col-md-3\" style=\"height:100px;width:auto; margin:auto;\">";

                        str = str + '<img src="' + base64 + '">';

                        str = str + "</div>";
                    }

                    str = str + '</li>';
                }




                str = str + '</div>';
                s.innerHTML = str;
            }
            else {
                for (let i=0; i<data.length; i++) {

                    str = str + '<div  style=\"text-align: center\">';
                    str = str + '<div class="row" id="' + data[i].seqid + data[i].userName + '" style=\"border:solid darkgrey 0.5px;width:100%;background-color:rgba(0, 0, 0, 0.6);\">';

                    str = str + "<div class = \"col-md-3\" style=\"height:100px;width:auto; margin:auto;\">";
                    str = str + '<p> 用户名：' +
                        '<a href="/share/' +
                        data[i].userName + '">' + data[i].userName + '</a></p>';
                    str = str + "</div>";


                    str = str + "<div class = \"col-md-3\" style=\"height:100px;width:auto; margin:auto;\">";
                    str = str + '<p> 时间:\n' + data[i].time + '</p>';
                    str = str + "</div>";


                    str = str + "<div class = \"col-md-3\" style=\"height:100px;width:auto; margin:auto;\">";
                        str = str + '<button type="button" class="btn btn-primary" id="' + data[i].seqid +
                        '" name="' + data[i].userName + '" onclick="deleteArticle(this)">删除' + '</button>';
                    str = str + "</div>";


                    str = str + "<div class = \"col-md-3\" style=\"height:100px;width:100px; margin:auto;\">";
                    for (let j=0; j<data[i].photos.length; j++) {
                        let base64 = 'data:image/png;base64,' + data[i].photos[j];
                        str = str + '<img src="' + base64 + '" style=\"height:100%;width:100%;\">';
                    }
                    str = str + "</div>";


                    str = str + '</div>' + '</div>';
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



$(function() {
    // my_own_thing
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

    tmp = "<li>";
     // "${/user/username}}"

    /*
    tmp = tmp + "<a id=\"personal_page\" href=\"/user/" + name + '"';
                    tmp = tmp +  ">个人主页</a>"
                    tmp = tmp + "</li>";
    */
    var session = getUser();


        tmp = tmp + "<a id=\"personal_page\" href=\"/user/" + session + '"';
    tmp = tmp +  ">个人主页</a>"
    tmp = tmp + "</li>";
    $('#my_own_thing').append(tmp);

})
