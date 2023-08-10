var item = $("#item");
var roomNumber;
var status;
var roomClass;
$(function (){
    //发送ajax请求，并刷新
    function fn() {
        $.ajax({
            url: "/api/room/roomList",
            type: "POST",
            data: {
                method: "roomList"
            },
            dataType: 'json',
            success: function (roomList) {
                if (roomList !== null) {
                    item.empty();
                    $(roomList).each(function (i, room) {
                        newroom = $(room)
                        roomNumber = newroom.attr("roomNumber");
                        status = Number(newroom.attr("status"));
                        if (Number(status) === Number(ROOM_STATUS_0)) {
                            roomClass = "state-green";
                        } else if (Number(status) === Number(ROOM_STATUS_1)) {
                            roomClass = "state-red";
                        } else if (Number(status) === Number(ROOM_STATUS_2)) {
                            roomClass = "state-blue";
                        } else if (Number(status) === Number(ROOM_STATUS_3)) {
                            roomClass = "state-gray";
                        }
                        item.append("<li class=\"" + roomClass + "\"><i></i><span class=\"A001\">" + roomNumber + "</span></li>")
                    });
                }
            },
            error: function () {
                console.log("出现错误！");
            }
        })
    }

    fn()
//每五秒中调用一次
    setInterval(fn, 3000);
})

