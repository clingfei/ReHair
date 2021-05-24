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
                    '<label for="cbox' + i + '" id="label' + i +'">' + faceType[i] + '</label>' +
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
    console.log(data.value);
    let id = "label" + data.value;
    var fType = document.getElementById(id);
    $.ajax({
        method: "GET",
        url: "/getHairType",
        async: false,
        data: {"faceType":data.value},
        success: function(data) {
            console.log(data);
            let s = document.getElementById("checkboxs");
            var str = '<div class="blog-post" id="article">';
            for (let i=0; i<data.length; i++) {
                str = str + '<p> <input type="checkbox" name="test" id="cbox' + i + '" value="' + (i+1) +
                    '<label for="cbox " id="faceType' + i  + '">发型' + (i+1) + '</label>' +
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
    let faceType = document.getElementById('head2');
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
                let str = '<p>SCORE: ' + data.score + '</p>' +
                    '<img src="data:image/png;base64,' + data.image + '" alt="" height="200" width="200">';
                if (data.score > 5)
                    str = str + '<p>Wow! You are so Beautiful!</p>';
                else str = str + '<p>Wow! You are so ugly!</p>';
                s.innerHTML = str;
            }
        })
    }
    //发起异步读取文件请求，读取结果为data:url的字符串形式，
    reader.readAsDataURL(file.files[0]);
}