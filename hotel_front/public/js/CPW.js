$(function () {
    var username = $("#username")
    var oldPassword = $("#oldPassword")
    var newPassword = $("#newPassword")
    var again = $("#again")
    var btn = $("#btn")

    btn.bind("click", function () {

        if (newPassword.val() !== again.val()) {
            alert("两次输入的密码不正确！")
            return false;
        }
        if (!ifPassword(newPassword.val()) || !ifPassword(oldPassword.val())) {
            alert("密码格式不正确！长度限制6-12位，只能是字母、数字和下划线")
            return false;
        }
        if (!ifUsername(username.val())) {
            alert("用户名格式不正确！长度限制4到16位（字母，数字，下划线，减号）")
            return false;
        }
        console.log(username.val())
        console.log(oldPassword.val())
        console.log(newPassword.val())
        $.ajax({
            url: "/api/admin/modifyPwd",
            type: "POST",
            data: {
                method: "modifyPwd",
                username: username.val(),
                oldPassword: oldPassword.val(),
                newPassword: newPassword.val()
            },
            success: function (res) {
                if (res==="true"){
                    alert("密码修改成功！")
                    $(location).attr("href","/login")
                }else {
                    alert("密码修改失败！")
                }
            }, error: function () {
                console.log("出现错误！")
            }
        })
        return true
    })
})