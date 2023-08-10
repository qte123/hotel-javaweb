$(function () {
    var username = $("#username");
    var password = $("#password");
    var surePassword = $("#surePassword");
    var email = $("#email");
    var sSignal = $("#sSignal")
    $("#submit").bind("click", function () {
        if (password.val() !== "") {
            if (surePassword.val() !== password.val()) {
                alert("两次输入的密码不正确！")
                return false;
            }
        }
        if (!ifPassword(password.val())) {
            alert("密码格式不正确！长度限制6-12位，只能是字母、数字和下划线")
            return false;
        }
        if (!ifUsername(username.val())) {
            alert("用户名格式不正确！长度限制4到16位（字母，数字，下划线，减号）")
            return false;
        }
        if (!ifEmail(email.val())) {
            alert("电子邮箱格式不正确！")
            return false;
        }
        if (!ifsSignal(sSignal.val())) {
            alert("六位数字邀请码格式不正确！")
            return false;
        }
        return true;
    })
});