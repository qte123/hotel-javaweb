var fn = null
var fn1 = null
var fn2 = null
var fn3 = null
$(function () {
        var text = $('input[name=queryname]')
        var searchbutton = $("#searchbutton")
        var roomType = $("#roomType");
        var flag = true
        if (flag) {
            var n = -1
            var pageIndex = 1
            var queryname = ""
            flag = false
        }
        fn = function (queryname1, n1, pageIndex1) {
            $.ajax({
                url: "/api/user/query",
                type: "POST",
                data: {
                    method: "query",
                    queryname: queryname1,
                    roomType: n1,
                    pageIndex: pageIndex1
                },
                dataType: "json",
                success: function (userList) {
                    console.log(userList)
                    $("table tr:not(:first)").remove();

                    $(userList).each(function (i, user) {
                        var userObj = $(user);
                        console.log(user)
                        var number = userObj.attr("IDNumber")
                        var s = number.substr(0, 18)
                        var s1 = number.substring(number.indexOf('*') + 1, number.indexOf("#"))
                        var s2 = number.substr(number.indexOf("#") + 1)

                        if (s1 === '0') {
                            s1 = "普通单间"
                        } else if (s1 === '1') {
                            s1 = "豪华单间"
                        } else if (s1 === '2') {
                            s1 = "普通双间"
                        } else if (s1 === '3') {
                            s1 = "贵宾套房"
                        } else {
                            s1 = "总统套房"
                        }
                        $('table').append(' <tr>\n' +
                            '                    <td name="name"><span>' + userObj.attr("name") + '</span></td>\n' +
                            '                    <td name="age"><span>' + userObj.attr("age") + '</span></td>\n' +
                            '                    <td name="sex"><span>' + userObj.attr("sex") + '</span></td>\n' +
                            '                    <td name="IDNumber"><span>' + s + '</span></td>\n' +
                            '                    <td name="phoneNumber"><span>' + userObj.attr("phoneNumber") + '</span></td>\n' +
                            '                    <td name="roomType"><span>' + s1 + '</span></td>\n' +
                            '                    <td name="roomNumber"><span>' + s2 + '</span></td>' +
                            '                    <td>\n' +
                            '                    <span><a class="viewUser" href="javascript:;" userid=${user.id } username=${user.userName id="select' + i + '" } ><img src="../../images/read.png" alt="查看" title="查看" /></a></span>\n' +
                            '                    <span><a class="modifyUser" href="javascript:;" userid=${user.id } username=${user.userName id="modify' + i + '"} ><img src="../../images/xiugai.png" alt="修改" title="修改" /></a></span>\n' +
                            '                    <span><a class="deleteUser" href="javascript:;" userid=${user.id } username=${user.userName id="delete' + i + '"} ><img src="../../images/schu.png" alt="删除" title="删除" /></a></span>\n' +
                            '                    </td>\n' +
                            '                </tr>')
                        list(user, i)
                    })

                },
                error: function () {
                    console.log("出现错误！");
                }
            })
        }

        fn(queryname, n, pageIndex)

        function list(user, i) {
            $("#delete" + i).bind("click", function () {
                fn3(user, i)
            })
            $("#modify" + i).bind("click", function () {
                fn2(user, i)
            })
            $("#select" + i).bind("click", function () {
                fn1(user, i)
            })
        }


        searchbutton.bind("click", function () {
            queryname = text.val()
            n = roomType.val();
            fn(queryname, n, pageIndex)
        })
        $('.swap ul').find('li').each(function () {
            $(this).click(function () {
                pageIndex = $(this).index() + 1;
                fn(queryname, n, pageIndex)
            })
        })
        $(".left-gt a").bind("click", function () {
            if (Number(pageIndex) === Number(1)) {
                alert("当前处于第一页,不能上一页")
                return false
            } else {
                pageIndex = Number(pageIndex) - Number(1)
                fn(queryname, n, pageIndex)
            }
        })
        $(".right-gt a").bind("click", function () {
            if (Number(pageIndex) === Number(6)) {
                alert("当前处于最后页,不能下一页")
                return false
            } else {
                pageIndex = Number(pageIndex) + Number(1)
                fn(queryname, n, pageIndex)
            }
        })
        var flag1 = true
        //查看
        fn1 = function (user, i) {
            var s = $(user).attr("IDNumber")
            var IDNumber = s.substring(0, s.indexOf("*"));
            $(location).attr("href", "/root/message?userIDNumber=" + IDNumber)
        }

        //修改
        fn2 = function (user, i) {
            var u = $(user)
            var td = $("table").find('tr').eq(i + 1).find('td');
            if (flag1) {
                td.eq(0).html('<input type="text" name="name">')
                //td.eq(1).html('<input type="text" name="age">')
                /*td.eq(2).html('<label for="nan">男</label> <input type="radio" name="sex" value="男" checked="checked" id="nan">' +
                    '                <label for="nv">女</label> <input type="radio" name="sex" value="女" id="nv">')*/
                // td.eq(3).html('<input type="text" name="IDNumber"> ')
                td.eq(4).html('<input type="text" name="phoneNumber"> ')
                td.eq(5).html('<select name="roomType" >\n' +
                    '                        <option selected=selected value="-1">--请选择--</option>\n' +
                    '                        <option value="0" >普通单间</c:if></option>\n' +
                    '                        <option value="1">豪华单间</c:if></option>\n' +
                    '                        <option value="2">普通双间</c:if></option>\n' +
                    '                        <option value="3">贵宾套房</c:if></option>\n' +
                    '                        <option value="4">总统套房</c:if></option>\n' +
                    '          </select>')
                td.eq(6).html('<select name="roomNumber">\n' +
                    '                    <option selected=selected value="-1">--请选择--</option>\n' +
                    '                    <option value="001">001</c:if></option>\n' +
                    '                    <option value="002">002</c:if></option>\n' +
                    '                    <option value="003">003</c:if></option>\n' +
                    '                    <option value="004">004</c:if></option>\n' +
                    '                    <option value="005">005</c:if></option>\n' +
                    '                    <option value="006">006</c:if></option>\n' +
                    '                    <option value="007">007</c:if></option>\n' +
                    '                    <option value="008">008</c:if></option>\n' +
                    '                    <option value="009">009</c:if></option>\n' +
                    '                    <option value="010">010</c:if></option>\n' +
                    '                </select>')
                flag1 = false
            } else {
                var name = $('input[name="name"]')
                //var age = $('input[name="age"]')
                /*
                                var sex = $('input[name="sex"]:checked');
                */
                var phoneNumber = $('input[name="phoneNumber"]')
                var roomType = $('select[name="roomType"]')
                var roomNumber = $('select[name="roomNumber"]')
                if (phoneNumber.val() === "" && roomType.val() === "-1" && roomNumber.val() === "-1") {


                    var number = u.attr("IDNumber")
                    var s = number.substr(0, 18)
                    var s1 = number.substring(number.indexOf('*') + 1, number.indexOf("#"))
                    var s2 = number.substr(number.indexOf("#") + 1)

                    if (s1 === '0') {
                        s1 = "普通单间"
                    } else if (s1 === '1') {
                        s1 = "豪华单间"
                    } else if (s1 === '2') {
                        s1 = "普通双间"
                    } else if (s1 === '3') {
                        s1 = "贵宾套房"
                    } else {
                        s1 = "总统套房"
                    }
                    td.eq(0).html('<span>' + u.attr("name") + '</span>')
                    td.eq(1).html('<span>' + u.attr("age") + '</span>')
                    //td.eq(2).html('<span>' + sex.val() + '</span>')
                    //td.eq(3).html('<span>' + user.attr("iDNumber") + '</span>')
                    td.eq(4).html('<span>' + u.attr("phoneNumber") + '</span> ')
                    td.eq(5).html('<span>' + s1 + '</span>')
                    td.eq(6).html('<span>' + s2 + '</span>')
                    flag1 = true
                    return false
                }
                if (!ifChineseName(name.val())) {
                    alert("请输入正确的中文姓名！")
                    td.eq(0).html('<span>' + u.attr("name") + '</span>')
                    td.eq(1).html('<span>' + u.attr("age") + '</span>')
                    //td.eq(2).html('<span>' + sex.val() + '</span>')
                    //td.eq(3).html('<span>' + user.attr("iDNumber") + '</span>')
                    td.eq(4).html('<span>' + u.attr("phoneNumber") + '</span> ')
                    td.eq(5).html('<span>' + s1 + '</span>')
                    td.eq(6).html('<span>' + s2 + '</span>')
                    flag1 = true
                    return false;
                }
                if (!ifPhoneNumber(phoneNumber.val())) {
                    alert("请输入正确的电话号码！")
                    td.eq(0).html('<span>' + u.attr("name") + '</span>')
                    td.eq(1).html('<span>' + u.attr("age") + '</span>')
                    //td.eq(2).html('<span>' + sex.val() + '</span>')
                    //td.eq(3).html('<span>' + user.attr("iDNumber") + '</span>')
                    td.eq(4).html('<span>' + u.attr("phoneNumber") + '</span> ')
                    td.eq(5).html('<span>' + s1 + '</span>')
                    td.eq(6).html('<span>' + s2 + '</span>')
                    flag1 = true
                    return false;
                }

                var Num = andRoom(roomType.val(), roomNumber.val())
                td.eq(0).html('<span>' + name.val() + '</span>')
                td.eq(1).html('<span>' + u.attr("age") + '</span>')
                //td.eq(2).html('<span>' + sex.val() + '</span>')
                //td.eq(3).html('<span>' + user.attr("iDNumber") + '</span>')
                td.eq(4).html('<span>' + phoneNumber.val() + '</span> ')
                td.eq(5).html('<span>' + andRoom1(roomType.val()) + '</span>')
                td.eq(6).html('<span>' + Num + '</span>')
                flag1 = true
                $.ajax({
                    url: "/api/user/modify",
                    type: "POST",
                    data: {
                        method: "modify",
                        name: name.val(),
                        age: u.attr("age"),
                        sex: u.attr("sex"),
                        IDNumber: u.attr("IDNumber"),
                        phoneNumber: phoneNumber.val(),
                        roomType: roomType.val(),
                        roomNumber: Num
                    },
                    success: function (res) {
                        if (res === "true") {
                            alert("修改成功！")
                            fn(queryname, n, pageIndex)
                        } else {
                            alert("修改失败！")
                            fn(queryname, n, pageIndex)
                        }
                    },
                    error: function () {
                        console.log("出现错误！");
                    }
                })
                return true;
            }
        }
        //删除

        fn3 = function (user, i) {
            var td = $("table").find('tr').eq(i + 1)
            td.remove();
            var number = $(user).attr("IDNumber")
            var s = number.substr(0, 18)
            $.ajax({
                url: '/api/user/delete',
                type: "POST",
                data: {
                    method: "delete",
                    IDNumber: s
                },
                success: function (res) {
                    if (res === "true") {
                        alert("删除成功！")
                        fn(queryname, n, pageIndex)
                    } else {
                        alert("删除失败！")
                        fn(queryname, n, pageIndex)
                    }
                }, error: function () {
                    console.log("出现错误！");
                }
            })
        }

        //每五十秒中调用一次
        setInterval(fn(queryname, n, pageIndex), 50000);
    }
)