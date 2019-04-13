$('.insertReply').click(function () {

    var jsonData = JSON.stringify({
        content : $(this).parent().parent().parent().find('.todoDescriptionReply').val(),
        tdlIdx : $(this).parent().parent().parent().find('.comment').val()
    });

    $.ajax({
        url: "/comment",
        type: "POST",
        data: jsonData,
        contentType: "application/json",
        dataType: "json",
        success: function () {
            location.href = "/tdl/list";
        },
        error: function () {
            alert("등록 실패!");
        }
    });
});