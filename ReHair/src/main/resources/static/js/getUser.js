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