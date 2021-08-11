


$(function geoFindMe() {

    const status = document.querySelector('#status');
    const mapLink = document.querySelector('#map-link');
    const iconElement = document.querySelector(".weather-icon");

    mapLink.href = '';
    mapLink.textContent = '';

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

$(function(){


    $("#report").on("click",function(){
        $("#eventModal").modal("show");
    })

    $("#sendReport").on("click",function(e){

        if($("input:checkbox[name='reason']:checked").length > 1){
            Swal.fire('신고 사유는 한가지만 선택가능합니다!', '', 'info')
        }else{
            let text = {
                reportText : $("#textArea").val(),
                diaryId : new Number($("#diaryId").val()),
                reportReasonId : new Number($("input:checkbox[name='reason']:checked").val())
            };
            $.ajax({
                url : "/restDiary/reportDiary",
                type : "POST",
                data :JSON.stringify(text),
                contentType: "application/json",
                success: function (response){
                    Swal.fire(response, '', 'success')
                }
            });
        }

    })

    $(".translate").on("click",function(){

        let text = {text : $("#text").text(),
            source:$("#source option:selected").val(),
            target:$("#target option:selected").val()};
        $.ajax({
            url : "/restDiary/translateDiary",
            type : "POST",
            data :JSON.stringify(text),
            contentType: "application/json",
            success: function (response){
                if($("#text").attr('name') == $("#target option:selected").val()){
                    Swal.fire('이미 번역되었습니다!', '', 'info')
                }else{
                    $(".translatedText").remove();
                    if(JSON.parse(response).errorMessage != null){
                        $("#text").attr('name',"");
                        return;
                    }
                    $(".tran").append("<div class='translatedText'>"+JSON.parse(response).message.result.translatedText+"</div> ");
                    $("#text").attr('name',$("#target option:selected").val());
                }
            },error(err){
                $("#text").attr('name',"");
            }
        });
    })

    $("#like").on("click",function(){
        let diaryId = {diaryId:$("#diaryId").val()}
        $.ajax({
            url : "/restDiary/likeDiary",
            type : "POST",
            data :JSON.stringify(diaryId),
            contentType: "application/json",
            success: function (response){
                alert(response);
            }
        });

    })


})

function back(){
    history.go(-1);
}

function deleteDiary(diaryId){
    console.log("deleteDiary Start");
    $("#formTag").method("POST").action("diary/deleteDiary");
    $(this).submit;
}


document.addEventListener("DOMContentLoaded", function(){
    var role = [[${role}]];
    var user = [[${user}]];
    console.log("role : "+role);
    console.log("user : "+user);
    if(role == "ADMIN"){
        //alert("admin이다! admin이 나타났다~!!");
        $("#forAdmin").append(
        "<a href='#' id='back' onclick='back()'>	&#11013;</a>"
        )
    } else{
    // alert("user 쫌생이다! user 쫌생이가 나타났다~!!")
    }
    if(user === '0'){
        console.log(user);
        $("#like").remove();
        $("#report").remove();
        $("#delete").remove();
    }else if(user==='1'){
        console.log(user);
        $("#modifyDiary").remove();
        $("#delete").remove();
    }else if(user==='2'){
        $("#like").remove();
        $("#report").remove();
        $("#delete").remove();
        $("#modifyDiary").remove();
    }

});

jQuery(function($) {
    $("body").css("display", "none");
    $("body").fadeIn(2000);
    $("a.transition").click(function(event){
        event.preventDefault();
        linkLocation = this.href;
        $("body").fadeOut(1000, redirectPage);
    });
    function redirectPage() {
        window.location = linkLocation;
    }
});
