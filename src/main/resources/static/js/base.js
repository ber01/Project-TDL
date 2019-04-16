$('#insert').click(function () {

    var jsonData = JSON.stringify({
        description: $('#todoDescription').val(),
        status: null,
        createdDate: null,
        completedDate: null,
        user: null
    });

    $.ajax({
        url: "/tdl",
        type: "POST",
        data: jsonData,
        contentType: "application/json",
        dataType: "json",
        success: function () {
            location.href = '/tdl/list';
        },
        error: function () {
            alert("저장 실패!")
        }
    });
});

$('.delete').click(function () {
    var data = $(this).val();
    $.ajax({
        url: '/tdl/' + data,
        type: 'DELETE',
        contentType: 'application/json',
        dataType: 'json',
        success: function () {
            location.reload()
        },
        error: function () {
            alert("삭제 실패!")
        }
    })
});

$('.complete').click(function () {
    var data = $(this).val();
    $.ajax({
        url: '/tdl/complete/' + data,
        type: 'PUT',
        contentType: 'application/json',
        success: function () {
            location.reload()
        },
        error: function () {
            alert('변화 실패!')
        }
    })
});

$('.update').click(function () {

    $(this).parent().parent().parent().find('.tdlDescription').attr('contenteditable', true);
    $(this).parent().parent().parent().find('.tdlDescription').trigger('focus');

    $('.update').click(function () {

        var data = $(this).val();
        var des = $(this).parent().parent().parent().find('.tdlDescription').text();
        $.ajax({
            url: '/tdl/' + data,
            type: 'PUT',
            data: des,
            contentType: 'application/json',
            dataType: 'text',
            success: function () {
                location.reload()
            },
            error: function () {
                alert('수정 실패!')
            }
        });

        $(this).parent().parent().parent().find('.tdlDescription').attr('contenteditable', false);
    });
});

$(document).ready(function () {

    $('.comment').click(function () {

        var c = $(this).parent().parent().parent().parent().find('.hiddenList');

        if(c.is(':visible')){
            c.slideUp();
        } else{
            c.slideDown();
            $(this).parent().parent().parent().parent().find('.hiddenList').find('.todoDescriptionReply').trigger('focus');
        }
    });
});