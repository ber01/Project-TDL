$('#found_id_btn').click(function () {

    var email = $('#found_id_email').val();
    
    if (email.length === 0){
        $('#found_not_email').text('필수 정보입니다.').css('color', 'red');
    } else {
        $.ajax({
            url: "/found/send/email",
            type: "POST",
            data: email,
            contentType: "application/json",
            dataType: "text",
            success: function () {
                alert("아이디 전송 성공!");
                location.href = "/login";
            },
            error: function (args) {
                $('#found_id_email').val('');
                $('#found_not_email').text(args.responseText).css('color', 'red');
            }
        });
    }
});

$('#found_pwd_btn').click(function () {

    var id = $('#found_pwd_id').val();
    var email = $('#found_pwd_email').val();

    var jsonData = JSON.stringify({
        id: id,
        email: email
    });

    $.ajax({
        url: "/found/send/id-email",
        type: "POST",
        data: jsonData,
        contentType: "application/json",
        dataType: "json",
        success: function () {
            alert("인증번호 전송 성공!");
            location.href = "/found/number"
        },
        error: function (args) {
            $('#found_pwd_id').val('');
            $('#found_pwd_email').val('');
            $('#found_not_pwd').text(args.responseText).css('color', 'red');
        }
    })
});

$('#found_cn_btn').click(function () {

    var number = $('#found_cn').val();

    if (number.length === 0){
        $('#found_not_cn').text('필수 정보입니다.').css('color', 'red');
    } else{
        $.ajax({
            url: "/found/send/number",
            type: "POST",
            data: number,
            contentType: "application/json",
            dataType: "text",
            success: function () {
                location.href = "/found/reset"
            },
            error: function (args) {
                $('#found_cn').val('');
                $('#found_not_cn').text(args.responseText).css('color', 'red');
            }
        })
    }
});

$('#found_reset_pwd').blur(function(){

    var pwd = $('#found_reset_pwd').val();
    var regExp = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,16}$/;

    if (pwd.length === 0) {
        $('#found_not_pwd').text('필수 정보입니다.').css('color', 'red');
    } else if (!regExp.test(pwd) || pwd.length < 8 || pwd.length > 16) {
        $('#found_not_pwd').text('8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.').css('color', 'red');
    } else {
        $('#found_not_pwd').text('사용가능한 비밀번호 입니다.').css('color', 'green');
    }
});

$('#found_reset_btn').click(function () {

    var pwd = $('#found_reset_pwd').val();
    var regExp = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,16}$/;

    var jsonData = JSON.stringify({
        pwd: pwd
    });

    if (regExp.test(pwd)){
        $.ajax({
            url: "/found/send/reset",
            type: "POST",
            data: jsonData,
            contentType: "application/json",
            dataType: "json",
            success: function () {
                alert("비밀번호가 변경되었습니다.");
                location.href = "/login";
            },
            error: function (args) {
                $('#found_not_pwd').text(args.responseText).css('color', 'red');
            }
        })
    } else {
        $('#found_reset_pwd').click().blur();
    }
});