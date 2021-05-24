function createShare() {
    'use strict';

    var btn = document.getElementById("create");
    btn.innerHTML = '<input type="text" id="txt">' +
    '<button type="button" onClick="upfile.click()" className="transparent btn" style="">上传图片</button> ' +
    '<input type="file" id="upfile" accept="" onChange="uploadShare(this)" style="visibility: hidden; position: absolute;"> ';
}

function uploadShare(file) {
    'use strict';
    console.log(file.files);
    let time = current();
    let text = document.getElementById("txt").value;
    console.log(time);
    console.log(text);

    var imgFile;
    const reader = new FileReader();
    reader.onload = function (ev) {
        imgFile =ev.target.result;
        //document.querySelector("img").src= ev.target.result;
        console.log(imgFile);
        //获取上传文件的类型
        let imgType = imgFile.substring(imgFile.indexOf("/")+1, imgFile.indexOf(";"));
        console.log(imgType);
        //截取编码的正文部分
        imgFile = imgFile.substring(imgFile.indexOf(",")+1);
        $.ajax({
            method: "POST",
            url: "/crtShare",
            async: false,
            data: {"content": text, "time":time, "image": imgFile, "imgType":imgType},
            //data: {"content": text, "time":time, "image": imgFile},
            success: function(data) {
                console.log(data);
                if (!data.flag)
                    alert(data.errorMsg);
                //window.location.href = "/share";
            }
        })
    }
    //发起异步读取文件请求，读取结果为data:url的字符串形式，
    reader.readAsDataURL(file.files[0]);
}

function current() {
    var d = new Date(),
        str = '';
    str += d.getFullYear() + '-'; //获取当前年份
    str += d.getMonth() + 1 + '-'; //获取当前月份（0——11）
    str += d.getDate() + '-';
    str += d.getHours() + '-';
    str += d.getMinutes() + '-';
    str += d.getSeconds();
    console.log(str);
    return str;
}