$(document).ready(function() {
    $("#date").val(moment().format("YYYY-MM-DD"));
    console.log("today  "+moment().format("YYYY-MM-DD"));

    $('#summernote').summernote({
        maximumImageFileSize: 10520000,
        placeholder: 'content',
        minHeight: 370,
        maxHeight: null,
        focus: true,
        lang : 'ko-KR',
        callbacks: {
            onImageUpload: function (files, editor, welEditable) {


                // sendFile(files[0], this);
                for(var i = files.length - 1; i>=0; i--){
                    sendFile(files[i], editor,welEditable);
                }

            }
        }
    });


    function sendFile(file,editor,welEditable) {
        var data = new FormData();
        data.append('file', file);
        $.ajax({
            data: data,
            type: "POST",
            url: '/diary/fileUpload',
            cache: false,
            contentType: false,
            enctype: 'multipart/form-data',
            processData: false,
            success: function(data) {
                $("#summernote").summernote("insertImage",data.url);
                // $('.note-editable').append("<p><img src='"+data.url+"' width='90px' height='auto'/></p><br/>");
                $('#summernote').summernote('insertNode', data.url);
                // $('.note-editable').reload();
            },
            error:function (request,status,error, form_data){
                alert("Error");
            }
        });
    }

    var check = $("input[type='checkbox']");
    check.click(function(){
        $("p.toggle").toggle();

        // $('#checkbox').is("checked").attr("value", 1);

    });

    const emotion1 = location.href.split("?");
    const emotion2 = emotion1[1].split("=");
    const emotion = emotion2[1];
    $('#emotionNo').attr("value", `${emotion}`);


});



// function goWrite(frm){
//     var title = frm.title.value;
//     var content = frm.content.value;
//
//     if(title.trim() == ''){
//         alert("제목을 입력해주세요");
//         return false;
//     }
//     if(content.trim() == ''){
//         alert("내용을 입력해주세요");
//         return false;
//     }
//     debugger;
//     $("form").attr("method", "POST").attr("action", "/diary/addDiary").submit();
// }


$(function geoFindMe() {

    function success(position) {
        var latitude  = position.coords.latitude;
        var longitude = position.coords.longitude;
        const coordsObj = {
            latitude,
            longitude
        };
        var url = "https://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon="+ longitude + "&appid=7d9ec3e89f78fa3eef02069216cce88c&units=metric";


        //saveCoords(coordsObj);
        getWeather(latitude, longitude);

    }



    function error() {
        status.textContent = 'Unable to retrieve your location';
    }

    if(!navigator.geolocation) {
        status.textContent = 'Geolocation is not supported by your browser';
    } else {
        status.textContent = '';
        navigator.geolocation.getCurrentPosition(success, error);
    }

    function getWeather(lat, lon){
        fetch(
            `https://api.openweathermap.org/data/2.5/weather?lat=${lat}&lon=${lon}&appid=7d9ec3e89f78fa3eef02069216cce88c&units=metric`
        )
            .then(function(response){
                return response.json();
            })
            .then(function(json){
                console.log(json);
                const temparature = String(json.main.temp).substring(0,2);  //온도
                const place = json.name;   // 사용자 위치
                var icon = json.weather[0].icon;

                $('#temparature').append(`${temparature}`)
                $('#location').append(`${place}`)
                $('#location1').attr("value", `${place}`)

                $(".pic").removeClass("pic").addClass(icon);
                var imgURL = "/images/weather/" + icon + ".png";
                $('#img').attr("src", imgURL);
                $('#weather').attr("value", imgURL);
            });
    }
});
// $("#tag").onclick(function () {
//     $("#tag").prepend("<input type=\"text\" name=\"tag_text\">");
// });

$(document).on("click", "#addTag", function(){
    $('#addedTag').append("<li><input class='tagText' id='tagText' type=\"text\" name=\"tag_text\" style=\"float: left\"/> <span id='deleteTag'>삭제</span></li>")
})
$(document).on("click", "#deleteTag", function (){
    alert($(this).closest('li'));
    $(this).closest('li').remove();
})




function goWrite(frm) {

    var title = frm.diaryTitle.value;
    var content = frm.summernote.value;
    var tag = $("#frm input[type=text]")
    if (title.trim() == ''){
        Swal.fire('제목을 입력해주세요!', '', 'info')
        return false;
    }$.trim($('#tagText').val())
    for(var i =0; i < tag.length; i++){
        if(""==$.trim((tag[i]).val())||null==$.trim((tag[i]).val())){
            Swal.fire('태그를 입력해주세요!', '', 'info')
            return false;
        }

    }
    if(title.length > 20){
        Swal.fire('제목이 너무 길어요!', '', 'warning')
        return false;
    }
    // if (tag.trim() == ''){
    //     alert("태그를 입력해주세요");
    //     return false;
    // }
    if (content.trim() == ''){
        Swal.fire('내용을 입력해주세요!', '', 'warning')
        return false;
    }else{
        frm.submit();
    }


}

$(function($) {
    $("body").css("display", "none");
    $("body").fadeIn(1000);
    $("a.transition").click(function(event){
        event.preventDefault();
        linkLocation = this.href;
        $("body").fadeOut(1000, redirectPage);
    });
    function redirectPage() {
        window.location = linkLocation;
    }
});