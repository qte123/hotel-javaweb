$(function () {
    var username = $("#username");
    var password = $("#password");
    $("#submit").bind("click", function () {
        if (username.val() === "") {
            alert("用户名不能为空！")
            return false;
        }
        if (password.val() === "") {
            alert("密码不能为空！")
            return false;
        }
        $.ajax({
            url: "/api/admin/login",
            type: "POST",
            data: {
                username: username.val(),
                password: password.val()
            },
            success: function (res) {
                if (res === "true") {
                    $(location).attr("href", "/root/menu");
                }else {
                    alert("登录失败！用户名或密码错误！")
                }
            },
            error: function () {
                console.log("出现错误！");
            }
        })
        return true;
    })
})