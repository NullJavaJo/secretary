//아이디 유효성 검사
let isCertification = false;
function idCheck() {
    let userId = $('#userId').val();
    $.ajax({
        url: '/restUser/idCheck',
        type: 'get',
        data: {userId: userId},
        contentType: "application/json",
        success: function (data) {
            console.log("1=중복/0=중복아님" + data);
            if (data === 1) {
                $("#idCheck").text("이미 존재하는 아이디입니다.");
                $("#idCheck").css("color", "#ae0f11");
                $("#submit").attr("disabled", true);
            } else if(data === 0 && userId.length>=5){
                $("#idCheck").text("사용 가능한 아이디입니다.");
                $("#idCheck").css("color", "#0029a4");
                $("#submit").attr("disabled", true);
            } else if(userId === ""){
                $("#idCheck").text("아이디를 작성해주세요.");
                $("#idCheck").css("color", "#ae0f11");
            }

        }, error: function () {
            console.log("실패");
        }
    });
}


function emailCheck(){
    let email = $("#email").val();
    let userId = $("#userId").val();
    let mailCheck = {email:email,userId:userId};
    if (email === "") {
        Swal.fire('메일 주소가 입력되지 않았습니다!', '', 'warning')
    }else {
    $.ajax({
        url: '/restUser/emailCheck',
        type: 'get',
        data: mailCheck,
        contentType: "application/json",
        success: function (data) {
            console.log("1=중복/0=중복아님" + data);
            if (data === 1) {
                Swal.fire('이미 가입된 이메일입니다!', '', 'info')
                $("#submit").attr("disabled", true);
            } else {
                $.ajax({
                    type: 'post',
                    url: '/restUser/sendMail',
                    data: {email: email},
                    dataType: 'json',
                    success: function (key) {
                        Swal.fire('이메일이 전송되었습니다!', '', 'success')
                        $('#check').attr("value", key);
                    }
                });
            }

        }, error: function () {
            console.log("실패");
        }
    });
    }
}


//회원가입 본인인증
/*function sendMail() {// 메일 입력 유효성 검사
    let mail = $("#email").val(); //사용자의 이메일 입력값.
    if (mail == "") {
        alert("메일 주소가 입력되지 않았습니다.");
    }
    else {
        //mail = mail+"@"+$(".domain").val(); //셀렉트 박스에 @뒤 값들을 더함.
        $.ajax({
            type: 'post',
            url: '/restUser/CheckMail',
            data: {mail: mail},
            dataType: 'json',
            success: function (key) {
                alert("인증번호가 전송되었습니다.")
                $('#check').attr("value", key);
            }
        });

        // isCertification = true; //추후 인증 여부를 알기위한 값
    }
}*/


function checkCertificationNo() {
    let checkKey = $('#check').val();
    let certificationNo = $('#certificationNo').val();
    if(checkKey === "" && certificationNo=== ""){
        Swal.fire('인증번호를 입력해주세요!', '', 'success')
    } else if ($("#certificationNo").val() === checkKey) { //인증 키 값의 비교를 위해 텍스트인풋과 벨류를 비교
        // console.log("key" + checkKey);
        isCertification = true;//인증성공여부 check
        $("#certificationNo").css("color", "#0029a4");
        $("#certificationNo").text("인증완료");
        Swal.fire('인증되었습니다!', '', 'success')
    } else {
        isCertification = false; //인증실패
        $("#certificationNo").text("불일치");
        $("#certificationNo").css("color", "#ae0f11");
        Swal.fire('인증번호가 일치하지 않습니다!', '', 'warning')
    }
}


function submitCheck(){
    if (isCertification == true){
        return true;
    }else{
        Swal.fire('메일 인증이 완료되지 않았습니다!', '', 'warning')
        return false;
    }
}


function pwdCheck(){

    if($("#password01").val() == $("#password02").val()){
        $("#pwmatch").text("비밀번호가 일치합니다.")
        $("#pwmatch").removeClass("glyphicon-remove");
        $("#pwmatch").addClass("glyphicon-ok");
        $("#pwmatch").css("color","#0029a4");
    }else{
        $("#pwmatch").text("비밀번호가 일치하지 않습니다.")
        $("#pwmatch").removeClass("glyphicon-ok");
        $("#pwmatch").addClass("glyphicon-remove");
        $("#pwmatch").css("color","#ae0f11");
    }
}


function back(){
    history.go(-1);
}