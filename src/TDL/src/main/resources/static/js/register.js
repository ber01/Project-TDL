var duplication = 0;

$('#login_duplication').click(function () {

    var var_id = $('#login_id').val();

    if (var_id.length === 0){
        alert("아이디를 입력하세요.");
    } else {

        $.ajax({
            url: "/register/duplication",
            type: "POST",
            data: var_id,
            contentType: "application/json",
            dataType: "text",
            success: function () {
                alert("생성 가능한 아이디 입니다.")
            },
            error: function () {
                alert("이미 존재하는 아이디 입니다.")
            }
        });

        duplication = 1;
    }
});

$('#login_btn').click(function () {

    var var_id = $('#login_id').val();
    var var_email = $('#login_email').val();
    var var_pwd = $('#login_pwd').val();

    if (var_id.length === 0){
        alert("아이디를 입력하세요.");
    } else if (var_email.length === 0){
        alert("이메일을 입력하세요.");
    } else if (var_pwd.length === 0){
        alert("비밀번호를 입력하세요.")
    } else if (duplication === 0){
        alert("아이디 중복 검사를 해주세요.")
    } else {

        var jsonData = JSON.stringify({
            id: var_id,
            email: var_email,
            pwd: var_pwd
        });

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
                alert("이동 실패!")
            }
        });
    }
});