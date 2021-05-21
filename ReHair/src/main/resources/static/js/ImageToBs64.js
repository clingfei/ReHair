function uploadFile(file) {
    'use strict';

    var imgFile;
    console.log(file.files);
    const reader = new FileReader();
    reader.onload = function (ev) {

        imgFile =ev.target.result;
        document.querySelector("img").src= ev.target.result;
        console.log(imgFile);

        $.ajax({
            method: "POST",
            url: "/setHead",
            async: false,
            data: {"image": imgFile},
            success: function(data) {
                console.log(data);
                if (!data.flag)
                    alert(data.errorMsg);
            }
        })
    }
    //发起异步读取文件请求，读取结果为data:url的字符串形式，
    reader.readAsDataURL(file.files[0]);

    console.log(reader.result);



}