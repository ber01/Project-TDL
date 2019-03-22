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
            $.ajax({
                url: "tdl/current",
                type: "POST",
                data: jsonData,
                contentType: "application/json",
                dataType: "json",
                success: function () {
                    location.href = '/tdl/list'
                },
                error: function () {
                    alert("에러")
                }
            })
        },
        error: function () {
            alert("로그인 실패!")
        }
    });
});