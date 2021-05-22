function getUser() {
    $.ajax({
        method: "GET",
        url: "/getUser",
        success: function (data) {
            return data;
        }
    })
}