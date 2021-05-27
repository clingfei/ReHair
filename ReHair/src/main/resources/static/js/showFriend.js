function showFriend() {
    'use strict';

    var session = getUser();
    console.log(session);
    if(session === "") {
        window.location.href = '/login';
    }

    $.ajax({
        method: "GET",
        url: "/showFriend",
        async: false,
        data: {"start": 0, "bias":10},
        success: function(data) {
            console.log(data);
            if(data.length != 0) {
                let str = '<div class="blog-post" id="article">';
                let s = document.getElementById("friend");
                for (let i = 0; i < data.length; i++) {
                    str = str +
                        '<li>' +
                        '<div  id="' + data[i] + '">' +  '<div style="color:white;">' + data[i]  + '</div>' + '<button type="button" name="' + data[i]
                        + '" onclick="unfollow(this)">unfollow</button>' +
                        '</div>' +
                        '</li>';
                }
                str = str + '</div>';
                s.innerHTML = str;
            }
        }
    })
}

function unfollow(data) {
    'use strict';

    console.log(data);
    console.log(data.name);
    $.ajax({
        method: "GET",
        url: "/unfollow",
        async: false,
        data: {"name": data.name},
        success: function(data) {
            console.log(data);
            if (data.flag) {
                let str = '<div class="blog-post" id="article">';
                let s = document.getElementById("friend");
                for (let i = 0; i < data.length; i++) {
                    str = str +
                        '<li>' +
                        '<div id="' + data[i] + '">' + data[i] + '<button type="button" name="' + data[i]
                        + '" onclick="unfollow(this)">unfollow</button>' +
                        '</div>' +
                        '</li>';
                }
                str = str + '</div>';
                s.innerHTML = str;
            }
            else {
                alert(data.errorMsg);
            }
        }
    })
}
