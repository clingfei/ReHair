window.onload = function() {
    'use strict';

    let s = document.getElementById("checkboxs");
    let faceType = ["oval", "oblong", "heart", "round", "square"];
    $.ajax({
        method: 'GET',
        url: "/getTemplatePhoto",
        async: true,
        success: function (data) {
            console.log(data);
            var str = '<div class="blog-post" id="article">';
            for (let i=0; i<data.length; i++) {
                str = str + '<p> <input type="checkbox" id="cbox' + i + '" value="' + (i+1) +
                    '" onclick="getHairType(this)">' +
                    '<label for="cbox' + i + '" id="label' + i +'" name="test" style=\"color:darkgrey\">' + faceType[i] + '</label>' + "<label></label>" +
                    '<img id="tem' + i + '" src="data:image/png;base64,' + data[i] + '" alt="" height="200" width="200"></p>';

                //let img = document.getElementById("tem" + toString(i+1));
                //document.querySelector("img").src
                //img.src = data[i];
            }
            str = str + '</div>';
            s.innerHTML = str;
        }
    })
};

function getHairType(data) {
    'use strict';
    console.log("-----" + data.value);
    console.log(typeof data.value);
    let id = "label" + (parseInt(data.value)-1).toString();
    console.log(id);
    let fType = document.getElementById(id);
    console.log(fType.innerHTML);
    console.log(fType.textContent);
    $.ajax({
        method: "GET",
        url: "/getHairType",
        async: false,
        data: {"faceType":data.value},
        success: function(data) {
            console.log(data);
            let s = document.getElementById("checkboxs");
            var str = '<div class="blog-post" id="article">'  + '<h2 id="head2" style=\"color:white;\">' + fType.textContent + '</h2>';
            for (let i=0; i<5; i++) { // 写死规定为5张小图片算了，还需要单独加一个接口，暂时还没有写好了。
                str = str + '<p> <input type="checkbox" name="test" id="cbox' + i + '" value="' + (i+1) +
                    '"><label for="cbox " id="faceType' + i  + '" style=\"color:white;\">发型' + (i+1) + '</label>' +
                    '<img id="tem' + i + '" src="data:image/png;base64,' + data[i] + '" alt="" height="200" width="200"></p>';

                //let img = document.getElementById("tem" + toString(i+1));
                //document.querySelector("img").src
                //img.src = data[i];
            }
            str = str + '</div>';
            s.innerHTML = str;
            let button = document.getElementById("modifybutton");
            let strr = '<button type="button" onClick="upfile.click()" className="transparent btn" style="">ModifyPicture</button> ' +
                '<input type="file" id="upfile" accept="" onChange="modifyPicture(this)" style="visibility: hidden; position: absolute;"> ';;
            button.innerHTML = strr;
        }
    });
}

function modifyPicture(file) {
    'use strict';
    let faceType = document.getElementById('head2').innerHTML;
    console.log("---"+faceType);
    var hairType;
    let obj = document.getElementsByName("test");
    var i = 0;
    let k;
    for(k in obj){
        i = i + 1;
        if(obj[k].checked)
           break;
    }
    hairType = i;
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
            url: "/modPic",
            async: false,
            data: {"faceType": faceType, "hairType": hairType, "image": imgFile, "imgType":imgType},
            //data: {"content": text, "time":time, "image": imgFile},
            success: function(data) {
                console.log(data);
                let s = document.getElementById("res");
                let str = '<p style ="color:white;">SCORE: ' + data.score + '</p>' +
                    '<img src="data:image/png;base64,' + data.image + '" alt="" height="200" width="200">';
                if (data.score > 5)
                    str = str + '<p style ="color:white;">Wow! You are so Beautiful!</p>';
                else str = str + '<p style ="color:white;">Wow! You are so ugly!</p>';
                s.innerHTML = str;
            }
        })
    }
    //发起异步读取文件请求，读取结果为data:url的字符串形式，
    reader.readAsDataURL(file.files[0]);
}

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

$(function() {
    // my_own_thing
    // 这里的重写有问题，需要返回到个人主页
    $('#my_own_thing').empty();
    var tmp = "<li>";
    tmp = tmp + "<a onclick=\"logout()\"> 退出登录 </a>"
    tmp = tmp + "</li>";

    $('#my_own_thing').append(tmp);

    tmp = "<li>";
    tmp = tmp + "<a href=\"/share\">动态圈</a>"
    tmp = tmp + "</li>";
    $('#my_own_thing').append(tmp);

    tmp = "<li>";
    tmp = tmp + "<a id=\"myShare\" href=\"\">我的动态</a>"
    tmp = tmp + "</li>";
    $('#my_own_thing').append(tmp);

    var session = getUser();
    tmp = tmp + '<li>';
    tmp = tmp + "<a id=\"personal_page\" href=\"/user/" + session + '"';
    tmp = tmp +  ">个人主页</a>"
    tmp = tmp + "</li>";
    $('#my_own_thing').append(tmp);

})
