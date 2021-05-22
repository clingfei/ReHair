function logout() {
    'use strict';

    $.ajax({
        method: "GET",
        url: "/logout",
        async: false,
        success: function(data) {
            console.log(data);
            if (data)
                window.location.href = "/login";
        }
    })
}