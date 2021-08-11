

//Clock
$(document).ready(function(){
    $(".wrapper").load("../main/menu.html");
})

$(function() {
    setInterval(function() {
        var nTime = new Date();
        var hour = nTime.getHours();
        var minutes = nTime.getMinutes();
        var time = (hour >= 12 ? "PM  " : "AM  ")
            + (hour < 10 ? "0" + hour : (hour<=12 ? hour : ((hour-12).length>1) ? "0"+hour-12 : hour-12 )) + ":"

            + (minutes >= 10 ? minutes : "0" + minutes);

        $("#clock").text(time);
    }, 1000);

})

//Music
var index = 1;
$('#play-next').click(function() {
    index++;
    if(index > $('#myaudio source').length) index=2;
    console.log( index + '번째 소스 재생' );

    $('#myaudio source#main').attr('src',
        $('#myaudio source:nth-child('+index+')').attr('src'));
    $("#myaudio")[0].load();
    $("#myaudio")[0].play();
});

//Alarm
function getAlarmList(){
    $.ajax({
        url : "/restAlarm/getAlarmList",
        method : "GET",
        cache : false,
        contentType : "application/json",
        success : function(alarmList) {
            console.log(alarmList);
            if (document.getElementById('deleteAlarmList')) {
                document.querySelector('#alarmLi span').remove();
                document.querySelector('#alarmLi li').remove();
                $('#alarmLi li,#alarmLi span').remove();
            } else {
                $('#alarmLi li,#alarmLi span').remove();
                $('#alarmLi').css("display", "none");
                $('#alarmLi').fadeIn(1000);
                $.each(alarmList, function (i, item) {
                    if (item.diaryId == 0 && item.monologueId == 0) {
                        $('#alarmLi').append(
                            "<li id='getLimitReasons' value='" + item.userId + "'><img src='../../images/main/harrypotter.png'>" + item.userId + "님" + item.alarmText + "</li>"
                        )
                    } else if (item.monologueId == 0) {
                        $('#alarmLi').append(
                            "<li id='getDiary' value='" + item.diaryId + "'><img src='../../images/main/harrypotter.png'>" + item.userId + "님!" + item.alarmText + "</li>"
                        )
                    } else {
                        $('#alarmLi').append(
                            "<li id='getMonologue' value='" + item.monologueId + "'><img src='../../images/main/harrypotter.png'>" + item.userId + "님!" + item.alarmText + "</li>"
                        )
                    }
                })
                if (!$('#alarmLi').hasClass('deleteAlarmList')) {
                    $('#alarmLi').append("<span id='deleteAlarmList' class='deleteAlarmList' style='font-size: 10px'>&#128465; 목록지우기</span>")
                }
            }
        }
    })
};

$(document).on("click", "#getDiary", function (){
    $.ajax({
        url : "/restAlarm/seenDiaryAlarm",
        method : "POST",
        dataType : "json",
        data : {diaryId:$(this).attr('value')},
        success :

            self.location="/diary/getDiary?diaryNo="+$(this).attr("value")

    });
});

$(document).on("click", "#getMonologue", function (){
    $.ajax({
        url : "/restAlarm/seenMonologueAlarm",
        method : "POST",
        dataType : "json",
        data : {monologueId:$(this).attr('value')},
        success :


            self.location = "/monologue/getMonologue?monologueId=" + $(this).attr("value")

    });
});

$(document).on("click", "#deleteAlarmList", function (){
    const id = $('#getLimitReasons').attr('value');
    Swal.fire({
        title: '목록을 전부 지우시겠습니까?',
        text: "",
        icon: 'info',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Yes, delete it!'
    }).then((result) => {
        if (result.isConfirmed) {
            $.ajax({
                url : "/restAlarm/deleteAlarmList",
                method : "POST",
                dataType : "json",
                data : {userId:$('#getLimitReasons').attr('value')},
                success :
                    getAlarmList()
            });
            Swal.fire(
                '삭제되었습니다!',
                '',
                'success'
            )
        }else{
            Swal.fire(
                '지울 항목이 없습니다!',
                '',
                'info'
            )
        }
    })
});

function modal(id) {
    var zIndex = 9999;
    var modal = document.getElementById(id);

    // 모달 div 뒤에 희끄무레한 레이어
    var bg = document.createElement('div');
    bg.setStyle({
        position: 'fixed',
        zIndex: zIndex,
        left: '0px',
        top: '0px',
        width: '100%',
        height: '100%',
        overflow: 'auto',
        // 레이어 색갈은 여기서 바꾸면 됨
        backgroundColor: 'rgba(0,0,0,0.4)'
    });
    document.body.append(bg);

    // 닫기 버튼 처리, 시꺼먼 레이어와 모달 div 지우기
    modal.querySelector('.modal_close_btn').addEventListener('click', function() {
        bg.remove();
        $('.modal_close_btn').hidden;
        window.location.reload();
        modal.style.display = 'none';
    });

    modal.setStyle({
        position: 'fixed',
        display: 'block',
        boxShadow: '0 4px 8px 0 rgba(0, 0, 0, 0), 0 6px 20px 0 rgba(0, 0, 0, 0.19)',
        backgroundColor: 'white',

        // 시꺼먼 레이어 보다 한칸 위에 보이기
        zIndex: zIndex + 1,

        // div center 정렬
        top: '50%',
        left: '50%',
        transform: 'translate(-50%, -50%)',
        msTransform: 'translate(-50%, -50%)',
        webkitTransform: 'translate(-50%, -50%)'
    });
}

// Element 에 style 한번에 오브젝트로 설정하는 함수 추가
Element.prototype.setStyle = function(styles) {
    for (var k in styles) this.style[k] = styles[k];
    return this;
};

$(document).on("click", "#getLimitReasons", function(){
    // 모달창 띄우기
    $.ajax({
        url : "/restAlarm/getLimitReasons",
        type : "GET",
        dataType : "json",
        data : {userId:$(this).attr('value')},
        headers: { //excess 제어요청 헤더, n : v 형식으로 헤더 추가하면 url의 request header에서 해당 헤더로 값을 얻어올 수 있다.
            "Accept": "application/json",
            "Content-Type": "application/json"
        },
        success : function (limitReasonList){
            console.log(limitReasonList);
            $('.modal_close_btn').show();
            $('#limitDetail').remove();
            $("#my_modal").append(
                "<div id='limitDetail'><h1> 사용자 공유 제한 </h1>\n" +
                "<p>해당 계정은 최근 공유한 게시물에서 다음과 같은 신고가 다수 발생하여<br/>" +
                "운영진의 판단하에 공유 제한이 조치되었습니다.<br/>" +
                "다른 유저에게 불쾌감을 주거나 특정 인물을 언급하여 비하하는 등의 언행은<br/>" +
                "제재의 대상이 될 수 있습니다. <br/>" +
                "향후에도 재차 이와 같은일이 발생 시 보다 강도 높은 수준의 제재가 <br/>" +
                "이루어질 수 있으니 유의해주시기 바랍니다." +
                "<table id='modalTable' border='1'>" +
                "<th>게시물id</th>" +
                "<th>신고사유타입</th>" +
                "<th>상세신고사유</th>" +
                "<th>신고날짜</th>" +
                "</table></div>")
            $.each(limitReasonList, function (i,item){
                console.log(item);
                if(item.monologueId == 0) {
                    $("#modalTable").append(
                        "<tr>\n" +
                        "        <td><a href='/diary/getDiary?diaryNo=" + item.diaryId + "'>" + item.diaryId + "</a></td>\n" +
                        "        <td>" + item.reportReasonId + "</td>\n" +
                        "        <td>" + item.reportText + "</td>\n" +
                        "        <td>" + item.reportDate + "</td>\n" +
                        "    </tr>"
                    )
                }else{
                    $("#modalTable").append(
                        "<tr>\n" +
                        "        <td><a href='/monologue/getMonologue?monologueId=" + item.monologueId + "'>" + item.monologueId + "</a></td>\n" +
                        "        <td>" + item.reportReasonId + "</td>\n" +
                        "        <td>" + item.reportText + "</td>\n" +
                        "        <td>" + item.reportDate + "</td>\n" +
                        "    </tr>"
                    )
                }
            })
        }
    })
    modal('my_modal');
});

$(document).on("click", "#getMonologueReportReason", function(){
    // 모달창 띄우기
    $.ajax({
        url : "/restMonologue/getMonologueReportReason",
        type : "GET",
        dataType : "json",
        data : {monologueId: $(this).attr("class")},
        headers: { //excess 제어요청 헤더, n : v 형식으로 헤더 추가하면 url의 request header에서 해당 헤더로 값을 얻어올 수 있다.
            "Accept": "application/json",
            "Content-Type": "application/json"
        },
        success : function (reportList){
            console.log(reportList);
            $("#modalTable").remove();
            $("#my_modal").append("<table id='modalTable' border='1'>\n" +
                "    <th>신고자ID</th>\n" +
                "    <th>사유번호</th>\n" +
                "    <th>신고날짜</th>\n" +
                "    <th>신고사유</th>\n" +
                "</table>")
            $.each(reportList, function (i,item){
                console.log(item);
                $("#modalTable").append(
                    "<tr>\n" +
                    "        <td>" + item.reporterId + "</td>\n" +
                    "        <td>" + item.reportReasonId + "</td>\n" +
                    "        <td>" + item.reportDate + "</td>\n" +
                    "        <td>" + item.reportText + "</td>\n" +
                    "    </tr>"
                )
            })
        }
    })
    modal('my_modal');
});

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

    $('#answer').on('keydown', function(event) {
        console.log($("#question").val());
        const monologue = {
            questionId: $("#questionNo").val(),
            questionText: $("#question").text(),
            monologueText: $("#answer").val()
        };
        if (event.keyCode === 13){
            if (!event.shiftKey){
                console.log(monologue);
                event.preventDefault();
                $.ajax({
                    url : "/restMonologue/addMonologue",
                    type : "POST",
                    dataType : "json",
                    data : JSON.stringify(monologue),
                    contentType: "application/json",
                    success : function (response){
                        console.log(response);
                        function complete(){
                            $("#question").remove();
                            $("#answer").remove();
                            $(".intro").fadeIn("slow");
                        }
                        $("#question").fadeOut("slow");
                        $("#answer").fadeOut("slow",complete);
                    }
                })
            }else{

            }
        }

    });
})

function back(){
    history.go(-1);
}


