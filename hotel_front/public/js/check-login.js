var login_user = $("#login-user");
var login_number = $("#login-number");
var check = $("#check");
$(function () {
    check.bind("click", function () {
        if (login_user.val() === "") {
            alert("输入的姓名不能为空！")
            return false
        }
        if (login_number.val() === "") {
            alert("输入的身份证号不能为空！")
            return false
        }
        if (!ifChineseName(login_user.val())) {
            alert("输入的姓名格式不对！")
            return false
        }
        if (!ifIDNumber(login_number.val())) {
            alert("输入的身份证号格式不对！")
            return false
        } else {
            $.ajax({
                url: "/api/order/selectByDouble",
                type: "POST",
                data: {
                    method: "selectByDouble",
                    name: login_user.val(),
                    userIDNumber: login_number.val()
                },
                success: function (res) {
                    if (res === "true") {
                        $(location).attr("href", "/check?userIDNumber=" + login_number.val());
                    } else {
                        alert("没有查询到订单信息！")
                    }
                },
                error: function () {
                    console.log("出现错误！");
                }
            })
            return false
        }
    })
})