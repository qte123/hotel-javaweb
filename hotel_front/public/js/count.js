var fn = null
$(function () {
    var save = $("#save")
    fn = function () {
        $.ajax({
            url: "/api/income/countPrice",
            type: "POST",
            data: {
                method: "countPrice",
            }, success: function (str) {
                $("table tr:not(:first)").remove();
                var s = str.split(",")
                $(s).each(function (i, a) {
                    var roomType = a.substring(0, a.indexOf('*'))
                    var count = a.substring(a.indexOf('*') + 1, a.indexOf('#'))
                    var price = a.substring(a.indexOf('#') + 1, a.indexOf('&'))
                    var total = a.substring(a.indexOf('&') + 1)
                    $('table').append(' <tr>\n' +
                        '                    <td><span>' + roomType + '</span></td>\n' +
                        '                    <td><span>' + count + '</span></td>\n' +
                        '                    <td><span>' + price + '</span></td>\n' +
                        '                    \n' +
                        '                </tr>')
                })
            }, error: function () {
                console.log("出现错误！")
            }
        })
    }
    fn()

    save.bind("click", function () {
        $.ajax({
            url: "/api/income/save",
            type: "POST",
            data: {
                method: "save",
            }, success: function (res) {
                if (res === "true") {
                    alert("保存成功！")
                } else {
                    alert("保存失败！")
                }
            }, error: function () {
                console.log("出现错误！")
            }
        })
        return false
    })

    //每五十秒中调用一次
    setInterval(fn, 3000);
})