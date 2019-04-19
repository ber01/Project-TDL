function Content(args) {

    this.idx = args['idx'];
    this.content = args['content'];
    this.createdDate = args['createdDate'];
    this.modifiedDate = args['modifiedDate'];

    var list_in_li = document.createElement('li');
    list_in_li.className = 'list_in_li';

    var li_in_left = document.createElement('div');
    li_in_left.className = 'li_in_left';
    var li_in_right = document.createElement('div');
    li_in_right.className = 'li_in_right';

    li_in_left.appendChild(document.createTextNode(this.content));
    list_in_li.appendChild(li_in_left);
    list_in_li.appendChild(li_in_right);

    var comment_modify = document.createElement('span');
    comment_modify.className = 'li_span comment_modify';
    comment_modify.setAttribute('data-test', this.idx);

    var comment_delete = document.createElement('span');
    comment_delete.className = 'li_span comment_delete';
    comment_delete.setAttribute('data-test', this.idx);
    comment_delete.onclick = function () {
        var data = $(this).data('test');
        $.ajax({
            url: '/comment/' + data,
            type: 'DELETE',
            contentType: 'application/json',
            success: function () {
                list_in_li.remove();
            },
            error: function () {
                alert("삭제 실패!")
            }
        })
    };

    var comment_time = document.createElement('span');
    comment_time.className = 'comment_time';

    comment_modify.appendChild(document.createTextNode('수정'));
    comment_delete.appendChild(document.createTextNode('삭제'));
    comment_time.appendChild(document.createTextNode(this.createdDate.toString().substring(0, 10) + ' ' + this.createdDate.toString().substring(11, 16)));

    li_in_right.appendChild(comment_modify);
    li_in_right.appendChild(comment_delete);
    li_in_right.appendChild(comment_time);

    return list_in_li;
}

$('.insertReply').click(function () {

    var jsonData = JSON.stringify({
        content: $(this).parent().parent().parent().find('.todoDescriptionReply').val(),
        tdlIdx: $(this).parent().parent().parent().find('.comment').val()
    });

    var ul = $(this).parent().parent().parent().find('.list_ul');
    var text = $(this).parent().parent().parent().find('.todoDescriptionReply');

    $.ajax({
        url: "/comment",
        type: "POST",
        data: jsonData,
        contentType: "application/json",
        dataType: "json",
        success: function (args) {

            ul.append(new Content(args));

            text.val('');
            text.focus();
        },
        error: function () {
            alert("등록 실패!");
        }
    });
});

$('.comment_delete').click(function () {
    var data = $(this).data('test');
    var li = $(this).parent().parent();

    $.ajax({
        url: '/comment/' + data,
        type: 'DELETE',
        contentType: 'application/json',
        success: function () {
            li.remove();
        },
        error: function () {
            alert("삭제 실패!")
        }
    })
});

$('.comment_modify').click(function () {

    var is_modify = $(this).parent().parent().find('.li_in_left').attr('contenteditable');

    if (is_modify === "true"){
        var data = $(this).data('test');
        var des = $(this).parent().parent().find('.li_in_left').text();
        $.ajax({
            url: '/comment/' + data,
            type: 'PUT',
            data: des,
            contentType: 'application/json',
            dataType: 'text',
            success: function () {
                location.reload();
            },
            error: function () {
                alert('수정 실패!')
            }
        });
        $(this).parent().parent().find('.li_in_left').attr('contenteditable', false);
    } else{
        $(this).parent().parent().find('.li_in_left').attr('contenteditable', true);
        $(this).parent().parent().find('.li_in_left').trigger('focus');
    }
});