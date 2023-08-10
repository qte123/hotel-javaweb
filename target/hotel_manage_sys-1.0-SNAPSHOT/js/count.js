var fn = null
$(function () {
    fn = function () {
        $.ajax({
            url: "/hotel/income.do",
            type: "POST",
            data: {
                method: "countPrice",
            }, success: function (str) {
                $("table tr:not(:first)").remove();
                var s = str.split(",")
                $(s).each(function (i, a) {
                    console.log(i)
                    var roomType = a.substring(0, a.indexOf('*'))
                    var count = a.substring(a.indexOf('*') + 1, a.indexOf('#'))
                    var price = a.substring(a.indexOf('#') + 1, a.indexOf('&'))
                    var total = a.substring(a.indexOf('&') + 1)
                    $('table').append(' <tr>\n' +
                        '                    <td><span>' + roomType + '</span></td>\n' +
                        '                    <td><span>' + count + '</span></td>\n' +
                        '                    <td><span>' + price + '</span></td>\n' +
                        '                    <td><span>' + total + '</span></td>\n' +
                        '                    \n' +
                        '                </tr>')
                })
            }, error: function () {
                console.log("出现错误！")
            }
        })
    }
    fn()
    //每五十秒中调用一次
    setInterval(fn, 3000);
})