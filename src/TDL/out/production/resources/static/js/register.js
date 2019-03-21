$('#login_btn').click(function () {
    var jsonData = JSON.stringify({
        id: $('#login_id').val(),
        email: $('#login_email').val(),
        pwd: $('#login_pwd').val()
    });
    $.ajax({
        url: "/register",
        type: "POST",
        data: jsonData,
        contentType: "application/json",
        dataType: "json",
        success: function () {
            location.href = '/tdl/list';
        },
        error: function () {
            alert("이동 실패!")
        }
    });
});