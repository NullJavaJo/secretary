/* ****************
 *  일정 편집
 * ************** */
var editEvent = function (event, element, view) {

    $('#deleteEvent').data('id', event.id); //클릭한 이벤트 ID

    $('.popover.fade.top').remove();
    $(element).popover("hide");

    if (event.allDay === true) {
        editAllDay.prop('checked', true);
    } else {
        editAllDay.prop('checked', false);
    }

    if (event.end === null) {
        event.end = event.start;
    }

    if (event.allDay === true && event.end !== event.start) {
        editEnd.val(moment(event.end).subtract(1, 'days').format('YYYY-MM-DD HH:mm'))
    } else {
        editEnd.val(event.end.format('YYYY-MM-DD HH:mm'));
    }

    modalTitle.html('일정 수정');
    editId.val(event.id);
    editTitle.val(event.title);
    editStart.val(event.start.format('YYYY-MM-DD HH:mm'));
    editType.val(event.type);
    editDesc.val(event.description);
    editColor.val(event.backgroundColor).css('color', event.backgroundColor);

    addBtnContainer.hide();
    modifyBtnContainer.show();
    eventModal.modal('show');

    //업데이트 버튼 클릭시
    $('#updateEvent').unbind();
    $('#updateEvent').on('click', function () {

        if (editStart.val() > editEnd.val()) {
            alert('끝나는 날짜가 앞설 수 없습니다.');
            return false;
        }

        if (editTitle.val() === '') {
            alert('일정명은 필수입니다.')
            return false;
        }

        var statusAllDay;
        var startDate;
        var endDate;
        var displayDate;

        if (editAllDay.is(':checked')) {
            statusAllDay = '1';
            startDate = moment(editStart.val()).format('YYYY-MM-DD');
            endDate = moment(editEnd.val()).format('YYYY-MM-DD');
            displayDate = moment(editEnd.val()).add(1, 'days').format('YYYY-MM-DD');
        } else {
            statusAllDay = '0';
            startDate = editStart.val();
            endDate = editEnd.val();
            displayDate = endDate;
        }

        eventModal.modal('hide');

        // event.allDay = statusAllDay;
        // event.title = editTitle.val();
        // event.start = startDate;
        // event.end = displayDate;
        // event.type = editType.val();
        // event.backgroundColor = editColor.val();
        // event.description = editDesc.val();
        var eventData = {
            id: editId.val(),
            title: editTitle.val(),
            start: startDate,
            end: displayDate,
            description: editDesc.val(),
            type: editType.val(),
            username: '',
            backgroundColor: editColor.val(),
            textColor: '#ffffff',
            allDay: statusAllDay
        };
        $("#calendar").fullCalendar('updateEvent', eventData);
        console.log(eventData);
        //일정 업데이트
        $.ajax({
            type: "POST",
            contentType:"application/json",
            url: "/restCalendar/updateCalendar",
            data: JSON.stringify(eventData),
            success: function (response) {
                Swal.fire('수정되었습니다!', '', 'success')
                $('#calendar').fullCalendar('removeEvents');
                $('#calendar').fullCalendar('refetchEvents');
            }
        });

    });
};

// 삭제버튼
$('#deleteEvent').on('click', function (event) {
    
    //$('#deleteEvent').unbind();
    $("#calendar").fullCalendar('removeEvents', $(this).data('id'));
    eventModal.modal('hide');
    var eventData = {
        id: editId.val()
    };
    //삭제시
    $.ajax({
        type: "POST",
        contentType:"application/json",
        url: "/restCalendar/deleteCalendar",
        data: JSON.stringify(eventData),
        success: function (response) {
            Swal.fire('삭제되었습니다!', '', 'success')

        }
    });

});