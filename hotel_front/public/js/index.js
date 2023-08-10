$(function () {
    var name = $("#name");
    var age = $("#age");
    var phoneNumber = $("#phone");
    var IDNumber = $("#number");
    var enterTime = $("#enter-time");
    var exitTime = $("#exit-time");
    var message = $("#message");
    name.bind("focus", function () {
        $(this).val("");
    }).bind("blur", function () {
        if ($(this).val() === "") $(this).val("请输入您的姓名");
    });
    message.bind("focus", function () {
        $(this).val("");
    }).bind("blur", function () {
        if ($(this).val() === "") $(this).val("请输入您的备注");
    });
    $("#submit").bind("click", function () {
        var sex = $('input[name="sex"]:checked');
        if (Number($("#roomType option:selected").val()) === Number(-1)) {
            alert("请选择房间！")
            return false;
        }
        if (name.val() !== "请输入您的姓名" && age.val() !== "" && phoneNumber.val() !== "" && IDNumber.val() !== "" && enterTime.val() !== "" && exitTime.val() !== "") {
            if (message.val() === "请输入您的备注") message.val("无")
            if (!ifChineseName(name.val())) {
                alert("请输入正确的中文姓名！")
                return false;
            }
            if (!ifAge(age.val())) {
                alert("请输入正确的年龄格式！")
                return false;
            }
            if (!ifPhoneNumber(phoneNumber.val())) {
                alert("请输入正确的电话号码！")
                return false;
            }
            if (!ifIDNumber(IDNumber.val())) {
                alert("请输入正确的身份证号！")
                return false;
            }
            if (!ifTimeTrue(enterTime.val(), exitTime.val())) {
                alert("请输入正确的预定时间！")
                return false;
            }
            if (!ifAgeTrue(age.val(), IDNumber.val())) {
                alert("请输入正确的年龄！")
                return false;
            }
            console.log(sex.val())
            if (!ifSexTrue(sex.val(), IDNumber.val())) {
                alert("请选择正确的性别！")
                return false;
            }
            return true;
        } else {
            alert("请输入完整信息！")
            return false;
        }
    });

    function fn1() {
        $.ajax({
            url: "/api/room/numberList",
            type: "POST",
            data: {
                method: "numberList"
            },
            dataType: 'json',
            success: function (list) {
                $(list).each(function (i, number) {
                    var n = $("#n" + i)
                    n.html(number)
                    if (Number(number) === Number(0)) {
                        $("#roomType").find('option[value=' + i + ']').attr("disabled", "disabled").css("background-color", "#EEEEEE");
                    } else {
                        $("#roomType").find('option[value=' + i + ']').removeAttr("disabled", "disabled").css("background-color", "inherit");
                    }
                })
            },
            error: function () {
                console.log("错误！")
            }
        })
        $.ajax({
            url: "/api/room/priceList",
            type: "POST",
            data: {
                method: "priceList"
            },
            dataType: 'json',
            success: function (list) {
                $(list).each(function (i, price) {
                    $("#p" + i).html(price + "/间")
                })
            },
            error: function () {
                console.log("错误！")
            }
        })
    }

    fn1()
//每五秒中调用一次
    setInterval(fn1, 3000);
});
