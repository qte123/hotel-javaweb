var fn = null
var fn1 = null
$(function () {
    var searchbutton = $("#searchbutton")
    var flag = true
    if (flag) {
        var n = -1
        var pageIndex = 1
        flag = false
    }

    fn = function (n1, pageIndex1) {
        $.ajax({
            url: "/api/order/query",
            type: "POST",
            data: {
                method: "query",
                roomType: n1,
                pageIndex: pageIndex1
            },
            dataType: "json",
            success: function (orderList) {
                console.log(orderList)
                $("table tr:not(:first)").remove();
                $(orderList).each(function (i, user) {
                    var userObj = $(user);
                    var number = userObj.attr("IDNumber")
                    var s = number.substr(0, 18)
                    var s1 = number.substr(number.indexOf('-') + 1)
                    console.log(number)
                    console.log(s1)
                    var content = ""
                    if (Number(s1) === Number(0)) {
                        s1 = "普通单间"
                        content = 'A'
                    } else if (Number(s1) === Number(1)) {
                        s1 = "豪华单间"
                        content = 'B'
                    } else if (Number(s1) === Number(2)) {
                        s1 = "普通双间"
                        content = 'C'
                    } else if (Number(s1) === Number(3)) {
                        s1 = "贵宾套房"
                        content = 'D'
                    } else {
                        s1 = "总统套房"
                        content = 'E'
                    }
                    $('table').append('  <tr>\n' +
                        '                <td><span>' + userObj.attr("name") + '</span></td>\n' +
                        '                <td><span>' + userObj.attr("age") + '</span></td>\n' +
                        '                <td><span>' + userObj.attr("sex") + '</span></td>\n' +
                        '                <td><span>' + s + '</span></td>\n' +
                        '                <td><span>' + userObj.attr("phoneNumber") + '</span></td>\n' +
                        '                <td><span>' + s1 + '</span></td>\n' +
                        '                <td><select name="queryName" >\n' +
                        '                    <option value="-1">--请选择--</option>\n' +
                        '                    <option value="' + content + '-001">001</c:if></option>\n' +
                        '                    <option value="' + content + '-002">002</c:if></option>\n' +
                        '                    <option value="' + content + '-003">003</c:if></option>\n' +
                        '                    <option value="' + content + '-004">004</c:if></option>\n' +
                        '                    <option value="' + content + '-005">005</c:if></option>\n' +
                        '                    <option value="' + content + '-006">006</c:if></option>\n' +
                        '                    <option value="' + content + '-007">007</c:if></option>\n' +
                        '                    <option value="' + content + '-008">008</c:if></option>\n' +
                        '                    <option value="' + content + '-009">009</c:if></option>\n' +
                        '                    <option value="' + content + '-010">010</c:if></option>\n' +
                        '                </select></td>\n' +
                        '                 <td><span class="confirm" ><a href="javascript:void(0)" id="agree' + i + '"><button>确认</button></a></span></td> ' +
                        '            </tr>')
                    list(user, i)
                })
            },
            error: function () {
                console.log("出现错误！");
            }
        })
    }

    fn(n, pageIndex)

    function list(user, i) {
        $("#agree" + i).bind("click", function () {
            fn1(user)
        })
    }

    searchbutton.bind("click", function () {
        n = $("#roomType").val()
        fn(n, pageIndex)
    })
    $('.swap ul').find('li').each(function () {
        $(this).click(function () {
            pageIndex = $(this).index() + 1;
            fn(n, pageIndex)
        })
    })

    $(".left-gt a").bind("click", function () {
        if (Number(pageIndex) === Number(1)) {
            alert("当前处于第一页,不能上一页")
            return false
        } else {
            pageIndex = Number(pageIndex) - Number(1)
            fn(n, pageIndex)
        }
    })
    $(".right-gt a").bind("click", function () {
        if (Number(pageIndex) === Number(6)) {
            alert("当前处于最后页,不能下一页")
            return false
        } else {
            pageIndex = Number(pageIndex) + Number(1)
            fn(n, pageIndex)
        }
    })
    fn1 = function (user) {
        var number = $(user).attr("IDNumber")
        var s = number.substr(0, 18)
        var room = $('select[name=queryName]').val()
        if (Number(room) === Number(-1)) {
            alert("请选择房间类型后，再提交！")
            return false
        }
        $.ajax({
            url: "/api/room/acceptRoom",
            type: "POST",
            data: {
                method: "acceptRoom",
                userIDNumber: s,
                roomNumber: $('select[name=queryName]').val()
            },
            success: function (res) {
                if (res === "true") {
                    fn(n, pageIndex)
                } else {
                    alert("提交失败！")
                    fn(n, pageIndex)
                }
            },
            error: function () {
                console.log("出现错误！");
            }
        })
    }

    //每五十秒中调用一次
    setInterval(fn(n, pageIndex), 50000);
})