var id_duplication = false;
var email_duplication = false;

$('#login_id').blur(function () {

    var id = $('#login_id').val();
    var regExp = /[a-z0-9]{5,10}/g;

    if (id.length === 0){
        $('#id_error').text('필수 정보입니다.').css('color', 'red');
    } else if (!regExp.test(id) || id.length < 5 || id.length > 10){
        $('#id_error').text('5~10자의 영문 소문자, 숫자만 사용 가능합니다.').css('color', 'red');
    } else{
        $.ajax({
            url: "/register/duplication/id",
            type: "POST",
            data: $('#login_id').val(),
            contentType: "application/json",
            dataType: "text",
            success: function () {
                $('#id_error').text('사용가능한 아이디 입니다.').css('color', 'green');
                id_duplication = true;
            },
            error: function () {
                $('#id_error').text('이미 사용중인 아이디 입니다.').css('color', 'red');
                id_duplication = false;
            }
        });
    }
});

$('#login_email').blur(function () {

    var email = $('#login_email').val();
    var regExp = /^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-Za-z0-9\-]+/;
    
    if (email.length === 0){
        $('#email_error').text('필수 정보입니다.').css('color', 'red');
    } else if (!regExp.test(email)) {
        $('#email_error').text('이메일 주소를 다시 확인해주세요.').css('color', 'red');
    } else{
        $.ajax({
            url: "/register/duplication/email",
            type: "POST",
            data: $('#login_email').val(),
            contentType: "application/json",
            dataType: "text",
            success: function () {
                $('#email_error').text('사용가능한 이메일 입니다.').css('color', 'green');
                email_duplication = true;
            },
            error: function () {
                $('#email_error').text('이미 사용중인 이메일 입니다.').css('color', 'red');
                email_duplication = false;
            }
        });
    }
});

$('#login_pwd').blur(function () {

    var pwd = $('#login_pwd').val();
    var regExp = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,16}$/;

    if (pwd.length === 0){
        $('#pwd_error').text('필수 정보입니다.').css('color', 'red');
    } else if (!regExp.test(pwd) || pwd.length < 8 || pwd.length > 16){
        $('#pwd_error').text('8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.').css('color', 'red');
    } else{
        $('#pwd_error').text('사용가능한 비밀번호 입니다.').css('color', 'green');
    }
});

$('#login_btn').click(function () {

    var var_id = $('#login_id').val();
    var var_email = $('#login_email').val();
    var var_pwd = $('#login_pwd').val();

    var jsonData = JSON.stringify({
        id: var_id,
        email: var_email,
        pwd: var_pwd
    });

    if (id_duplication && email_duplication) {
        $.ajax({
            url: "/register",
            type: "POST",
            data: jsonData,
            contentType: "application/json",
            dataType: "json",
            success: function () {
                location.href = '/login';
            },
            error: function () {
            }
        });
    } else {
        $('#login_id').click().blur();
        $('#login_email').click().blur();
        $('#login_pwd').click().blur();
    }
});