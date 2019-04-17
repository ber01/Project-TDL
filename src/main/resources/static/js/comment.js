$('.insertReply').click(function () {

    var jsonData = JSON.stringify({
        content : $(this).parent().parent().parent().find('.todoDescriptionReply').val(),
        tdlIdx : $(this).parent().parent().parent().find('.comment').val()
    });

    var ul = $(this).parent().parent().parent().find('.list_ul');

    $.ajax({
        url: "/comment",
        type: "POST",
        data: jsonData,
        contentType: "application/json",
        dataType: "json",
        success: function (a, b, c) {
            var node = document.createElement('li');                 // Create a <li> node
            var text_node = document.createTextNode(a['content']);         // Create a text node
            node.appendChild(text_node);                              // Append the text to <li>
            ul.append(node);
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