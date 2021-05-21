function showFriend() {
    'use strict';

    $.ajax({
        method: "GET",
        url: "/showFriend",
        data: {"start": 0, "bias":10},
        success: function(data) {
            console.log(data);
            if(data.length != 0) {
                let str = '<div class="blog-post" id="article">';
                let s = document.getElementById("friend");
                for (let i = 0; i < data.length; i++) {
                    str = str + '<p>'  + data[i] + '</p>';
                }
                str = str + '</div>';
                s.innerHTML = str;
            }
        }
    })
}