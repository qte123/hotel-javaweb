$(function () {
    var name = $("#name");
    var sex = $("#sex");
    var age = $("#age");
    var phoneNumber = $("#phone");
    var IDNumber = $("#number");
    var roomType = $("#roomType");
    var enterTime = $("#enter-time");
    var exitTime = $("#exit-time");
    var price = $("#price");
    var cancel = $("#cancel");
    var url = $(location).attr("href");
    var index = url.indexOf('=');
    var IDNumbers = url.substr(index + 1);
    $.ajax({
        url: "/api/order/select",
        type: "POST",
        data: {
            method: "select",
            userIDNumber: IDNumbers
        },
        dataType: "json",
        success: function (order) {
            var orderObj = $(order);
            console.log("abc")
            roomTypeNumber = orderObj.attr("roomType");
            if (Number(roomTypeNumber) === Number(ROOM_TYPE_0)) {
                roomType1 = "普通单间"
            } else if (Number(roomTypeNumber) === Number(ROOM_TYPE_1)) {
                roomType1 = "豪华单间"
            } else if (Number(roomTypeNumber) === Number(ROOM_TYPE_2)) {
                roomType1 = "普通双间"
            } else if (Number(roomTypeNumber) === Number(ROOM_TYPE_3)) {
                roomType1 = "贵宾套房"
            } else {
                roomType1 = "总统套房"
            }
            enterTime1 = format(orderObj.attr("enterTime"))
            exitTime1 = format(orderObj.attr("exitTime"));
            price1 = orderObj.attr("price");
            roomType.html(roomType1)
            enterTime.html(enterTime1)
            exitTime.html(exitTime1)
            price.html(price1)
        },
        error: function () {
            console.log("出现错误！");
        }
    })
    $.ajax({
        url: "/api/user/select",
        type: "POST",
        data: {
            method: "select",
            userIDNumber: IDNumbers
        },
        dataType: "json",
        success: function (user) {
            console.log(user)
            var userObj = $(user)
            name1 = userObj.attr("name");
            sex1 = userObj.attr("sex");
            age1 = userObj.attr("age");
            phoneNumber1 = userObj.attr("phoneNumber");
            IDNumber1 = userObj.attr("IDNumber");
            name.html(name1)
            sex.html(sex1)
            age.html(age1)
            phoneNumber.html(phoneNumber1);
            IDNumber.html(IDNumber1)
        },
        error: function () {
            console.log("出现错误！");
        }
    })
    cancel.bind("click", function () {
        $.ajax({
            url: "/api/order/accept",
            type: "POST",
            data: {
                method: "accept",
                userIDNumber: IDNumbers
            },
            success: function (res) {
                if (res === "true") {
                    alert("订单已取消！");
                    $(location).attr("href", "/");
                } else {
                    alert("等待管理员取消！")
                }
            },
            error: function () {
                console.log("出现错误！");
            }
        })
        return false;
    })
})