function follow(username) {
    console.log(username.name);
    var url = window.location.href;
    $.ajax({
        method: "GET",
        url: "/follow",
        async: false,
        data: {friendName: username.name},
        success: function(data) {
            if (data.flag) {
                window.location.href = url;
            }
            else {
                alert(data.errorMsg);
            }
        }
    })
}

function unfollow(username) {
    console.log(username.name);
    $.ajax({
        method: "GET",
        url: "/unfollow",
        async: false,
        data: {"name": username.name},
        success: function (data) {
            if (data.flag) {
                window.location.href = url;
            } else {
                alert(data.errorMsg);
            }
        }
    });
}