$('#insert').click(function () {

    var jsonData = JSON.stringify({
        description: $('#todoDescription').val(),
        status: null,
        createdDate: null,
        completedDate: null
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
            location.href = '/tdl/list'
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
        dataType: 'json',
        success: function () {
            location.href = '/tdl/list'
        },
        error: function () {
            alert('변화 실패!')
        }
    })
});

var num = 1;

$('.update').click(function () {

    if (num === 1) {
        $(this).parent().parent().parent().find('.tdlDescription').attr('contenteditable', true);
        $(this).parent().parent().parent().find('.tdlDescription').trigger('focus');
        num = 2;
    }

    if (num === 2) {

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
                    location.href = "/tdl/list";
                },
                error: function () {
                    alert('수정 실패!')
                }
            });

            $(this).parent().parent().parent().find('.tdlDescription').attr('contenteditable', false);
            num = 1;
        });
    }
});