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
        success: function (res) {
            console.log(res);
            let s = document.getElementById("shares");

            let str = '<div class="blog-post" id="article" style=\"text-align:center;\">';

            for (let i=0; i<res.length; i++) {

                str = str + '<li class="row" style=\"border:solid darkgrey 0.5px;height=300px;\">';

                // 单纯的用户名部分
                str = str + "<div class = \"col-md-1\" style=\"height:100px;width:auto; margin:auto;\">";
                str = str + '<div id="' + res[i].seqid + res[i].userName + '">' +
                '<p> 用户名：' +
                '<a href="/share/' +
                    res[i].userName + '">' + res[i].userName + '</a>';

                $.ajax({
                    method: "POST",
                    url: "/isFriend",
                    async: false,
                    data: {username: res[i].userName},
                    success: function (flag) {
                        console.log(flag);
                        if (flag === 1) {
                            str = str + '<button type="button" style="background-color:aliceblue;color: black;" name="' + res[i].userName + '" onclick="unfollow(this)">unfollow </button></p>';
                        }
                        else if(flag === 2) {
                            str = str + '<button type="button" style="background-color:aliceblue;color: black;" name="' + res[i].userName + '" onclick="follow(this)">follow </button></p>';
                        }
                        else str = str + '</p>';
                    }
                });
                str = str + '</div>';
                str = str + "</div>";

                str = str + "<div class = \"col-md-1\" style=\"height:100px;width:auto; margin:auto;\">";
                str = str +
                    '<p> 内容:' + res[i].text + '</p>'
                    + '<p> 时间:' + res[i].time + '</p>' ;
                str = str + "</div>";


                for (let j=0; j<res[i].photos.length; j++) {
                    let base64 = 'data:image/png;base64,' + res[i].photos[j];
                    str = str + "<div class = \"col-md-1\" style=\"height:100px;width:auto; margin:auto;\">";
                    str = str + '<img src="' + base64 + '" style=\"height:100%;width:auto;\">';
                    str = str + "</div>";

                }
                str = str + '</li>';
            }
            str = str + '</div>';
            s.innerHTML = str;
        }
    })

});