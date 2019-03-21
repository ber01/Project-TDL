$('#login_btn').click(function () {
    var jsonData = JSON.stringify({
        id: $('#login_id').val(),
        pwd: $('#login_pwd').val()
    });
    $.ajax({
        url: "/login",
        type: "POST",
        data: jsonData,
        contentType: "application/json",
        dataType: "json",
        success: function () {
        },
        error: function () {
            alert("로그인 실패!")
        }
    });
});