$('#found_id_btn').click(function () {

    var email = $('#found_id_email').val();

    $.ajax({
        url: "/found/send/email",
        type: "POST",
        data: email,
        contentType: "application/json",
        dataType: "text",
        success: function () {
            alert("이메일로 아이디를 발송하였습니다.");
            location.href = "/login";
        },
        error: function (args) {
            $('#found_id_email').val('');
            $('#found_not_email').text(args.responseText).css('color', 'red');
        }
    });
});