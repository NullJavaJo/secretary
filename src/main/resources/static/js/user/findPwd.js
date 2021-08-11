$(function (){
    $("#checkEmail").on("click",function (){
        let email = {
            email:$("#pwdEmail").val(),
            userId:$("#pwdUserId").val()
        }
        if (email == "") {
            Swal.fire('메일 주소가 입력되지 않았습니다!', '', 'info')
        }else {
            $.ajax({
                url: '/restUser/emailCheck',
                type: 'get',
                data: email,
                contentType: "application/text",
                success: function (data) {
                    if(data===0){
                        $.ajax({
                            type: 'post',
                            url: '/restUser/sendMail',
                            data: email,
                            dataType: 'json',
                            success: function (key) {
                                Swal.fire('이메일이 전송되었습니다!', '', 'success')
                                $('#check').attr("value", key);
                            }
                        });
                    }else{
                        Swal.fire('등록되지 않은 이메일입니다!', '', 'warning')
                    }


                }, error: function () {
                    console.log("실패");
                }
            });
        }
    })
})


function checkCertificationNo() {

    let checkKey = $('#check').val();
    if (checkKey == ""){
        Swal.fire('인증번호를 입력해주세요!', '', 'info')
    }else if ($("#certificationNo").val() == checkKey) { //인증 키 값의 비교를 위해 텍스트인풋과 벨류를 비교
        console.log("key" + checkKey);
        $("#certificationNo").text("인증완료").css("color", "blue");
        Swal.fire('인증이 완료되었습니다!', '', 'success')
        isCertification = true; //인증성공여부 check
    } else {
        $("#certificationNo").text("불일치").css("color", "red");
        isCertification = false; //인증실패
        Swal.fire('인증이 실패했습니다!', '', 'warning')
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

function passwordChange(){
	if(submitCheck() == true){
		$("#password-modal").modal("show");
	 $("#sendPassword").on("click",function(e){

	                if($("#password01").val() == $("#password02").val()){
	                	let text = {
	                        	password : $("#password02").val(),
	                        	userId : $("#pwdUserId").val()
	                        };
	                    $.ajax({
	                        url : "/restUser/changePassword",
	                        type : "POST",
	                        data :JSON.stringify(text),
	                        contentType: "application/json",
	                        //async: false,
	                        success: function (response){
                                Swal.fire(response, '', 'success')
	                        }
	                    });
	                    
	                  location.href="login";
	                
	                }else{
                        Swal.fire('입력하신 비밀번호가 일치하지 않습니다!', '', 'warning')
	                  
	                }

	            })
					
		}
}



