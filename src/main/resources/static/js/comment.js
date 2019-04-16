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
            location.reload()
        },
        error: function () {
            alert("등록 실패!");
        }
    });
});

$('.comment_delete').click(function () {
    var data = $(this).data('test');
    $.ajax({
        url: '/comment/' + data,
        type: 'DELETE',
        contentType: 'application/json',
        success: function () {
            location.href = '/tdl/list'
        },
        error: function () {
            alert("삭제 실패!")
        }
    })
});

$('.comment_modify').click(function () {

    $(this).parent().parent().find('.li_in_left').attr('contenteditable', true);
    $(this).parent().parent().find('.li_in_left').trigger('focus');

    $('.comment_modify').click(function () {

        var data = $(this).data('test');
        var des = $(this).parent().parent().find('.li_in_left').text();
        $.ajax({
            url: '/comment/' + data,
            type: 'PUT',
            data: des,
            contentType: 'application/json',
            dataType: 'text',
            success: function () {
                location.href = "/tdl/list";
            },
            error: function () {
                alert('수정 실패!')
            }
        });

        $(this).parent().parent().find('.li_in_left').attr('contenteditable', false);
    });
});