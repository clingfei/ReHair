function login() {
    'use strict';

    var userName = document.getElementById("userName").value;
    var passWd = document.getElementById("passWd").value;

    if (typeof userName == "undefined" || userName == null || userName == "") {
        alert("用户名不能为空！");
        window.location.href = '/login';
        return;
    }
    if (typeof passWd == "undefined" || passWd == null || passWd == "") {
        alert("密码不能为空！");
        window.location.href = '/login';
        return;
    }

    $.ajax({
        method: "POST",
        url: "/login",
        async: false,
        data: {"username": userName, "password": passWd},
        success: function (data) {
            console.log(data);
            console.log(data.flag);
            console.log(data.errorMsg);
            if (data.flag) {
                // alert("success");
                window.location.href = '/';
                return;
            } else {
                alert(data.errorMsg);
                window.location.href = '/login';
                return;
            }
        }
    });

}