$('#found_id_btn').click(function () {

    var email = $('#found_id_email').val();

    var jsonData = JSON.stringify({
        email: email
    });

    $.ajax({
        url: "/forgot-id",
        type: "POST",
        data: jsonData,
        contentType: "application/json",
        dataType: "json",
        success: function () {
            alert("아이디 전송 성공!");
            location.href = "/login";
        },
        error: function (args) {
            $('#found_id_email').val('');
            $('#found_not_email').text(args.responseText).css('color', 'red');
        }
    })
});

$('#found_pwd_btn').click(function () {

    var id = $('#found_pwd_id').val();
    var email = $('#found_pwd_email').val();

    var jsonData = JSON.stringify({
        id: id,
        email: email
    });

    $.ajax({
        url: "/forgot-password",
        type: "POST",
        data: jsonData,
        contentType: "application/json",
        dataType: "json",
        success: function () {
            alert("인증번호 전송 성공!");
            location.href = "/tdl/list"
        },
        error: function (args) {
            $('#found_pwd_id').val('');
            $('#found_pwd_email').val('');
            $('#found_not_pwd').text(args.responseText).css('color', 'red');
        }
    })
});

$('#found_reset_btn').click(function () {

    var pwd = $('#found_reset_pwd').val();
    var regExp = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,16}$/;

    var token = $(this).data('test');

    var jsonData = JSON.stringify({
        pwd: pwd,
        token: token
    });

    $.ajax({
        url: "/reset-password",
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
});