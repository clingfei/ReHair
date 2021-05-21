function register() {
    'use strict';
    var userName = document.getElementById("userName").value;
    var passWd = document.getElementById("passWd").value;
    var passWd_2 = document.getElementById("passWd_2").value;
    var email = document.getElementById("email").value;

    if (typeof userName == "undefined" || userName == null || userName == "") {
        alert("用户名不能为空！");
        window.location.href = '/register';
        return;
    }
    if (typeof email == "undefined" || email == null || email == "") {
        alert("邮箱不能为空！");
        window.location.href = '/register';
        return;
    }
    if (typeof passWd == "undefined" || passWd == null || passWd == "") {
        alert("密码不能为空！");
        window.location.href = '/register';
        return;
    }
    if (passWd.length < 6) {
        alert("密码长度至少6位！");
        window.location.href = '/register';
        return;
    }
    console.log(passWd);
    console.log(passWd_2);
    if (passWd !== passWd_2) {
        alert("两次密码输入不一致！");
        window.location.href = '/register';
        return;
    }

    $.ajax({
        method: "POST",
        url: "/register",
        async: false,
        data: {"username": userName, "password": passWd, "email": email},

        success: function (data) {
            console.log(data);
            console.log(data.flag);
            console.log(data.errorMsg);
            if (data.flag) {
                // alert("success");
                window.location.href = '/login';
                return;
            } else {
                alert(data.errorMsg);
                window.location.href = '/register';
                return;
            }
        }
    });
}