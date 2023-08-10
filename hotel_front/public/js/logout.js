$(function () {
    var out = $("#out")
    out.bind("click", function () {
        $.ajax({
            url: "/api/admin/logout",
            type: "POST",
            success: function (res) {
                if (res === "true") {
                    alert("退出成功！")
                } else {
                    alert("退出失败！")
                }
            }, error: function () {
                console.log("出现错误！")
            }
        })
    })
})