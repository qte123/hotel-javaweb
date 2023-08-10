var fn = null
var fn1 = null
var fn2 = null
var fn3 = null
var fn4 = null
var fn5 = null
$(function () {
    var searchbutton = $("#searchbutton")
    var roomType = $("#roomType");
    var change = $("#change")
    var flag = true
    var oldPrice
    if (flag) {
        var n = -1
        var pageIndex = 1
        flag = false
    }
    fn = function (n1, pageIndex1) {
        $.ajax({
            url: "/api/room/query",
            type: "POST",
            data: {
                method: "query",
                roomType: n1,
                pageIndex: pageIndex1
            },
            dataType: "json",
            success: function (roomList) {
                console.log(roomList)
                $("table tr:not(:first)").remove();
                $(roomList).each(function (i, room) {
                        var roomObj = $(room);
                        var s = roomObj.attr("roomType")
                        if (Number(s) === Number(0)) {
                            s = "普通单间"
                        } else if (Number(s) === Number(1)) {
                            s = "豪华单间"
                        } else if (Number(s) === Number(2)) {
                            s = "普通双间"
                        } else if (Number(s) === Number(3)) {
                            s = "贵宾套房"
                        } else {
                            s = "总统套房"
                        }
                        oldPrice = roomObj.attr("price")
                        $('table').append('<tr>' +
                            '                <td><span>' + s + '</span></td>\n' +
                            '                <td><span>' + roomObj.attr("roomNumber") + '</span></td>\n' +
                            '                <td><span>' + oldPrice + '元</span></td>\n' +
                            '                <td>\n' +
                            '                    <span>\n' +
                            '                     <a class="div-agree" href="#" id="agress' + i + '"> <span class="agree"></span></a>\n' +
                            '                    <a class="div-disagree" href="#"  id="disagress' + i + '"> <span class="disagree"></span> </a>\n' +
                            '                    </span>\n' +
                            '                </td>' +
                            '                <td>\n' +
                            '                    <span>' +
                            '                        <a class="deleteUser" href="javascript:;" userid=${user.id } username=${user.userName}>\n' +
                            '                        <img src="../../images/schu.png" alt="删除" title="删除"/></a>' +
                            '                    </span>\n' +
                            '                    <span><a href="#" class="add" title="添加"></a></span>\n' +
                            '                </td>   ' +
                            '                </tr>                    ');
                        var status = roomObj.attr("status")
                        var td = $("table").find('tr').eq(i + 1).find('td');
                        if (Number(status) === Number("1")) {
                            td.eq(3).html("<span>已住</span>")
                        } else if (Number(status) === Number("0")) {
                            td.eq(3).html("<span>未住</span>")
                        } else if (Number(status) === Number("3")) {
                            td.eq(3).html("<span>已删除</span>")
                        } else {
                            td.eq(3).html(
                                '<span>待退房<a class="div-agree" href="#" id="agree' + i + '"> <span class="agree"></span></a>' +
                                '<a class="div-disagree" href="#"  id="disagree' + i + '"> <span class="disagree"></span></a></span>')
                        }


                        if (Number(status) === Number("0")) {
                            td.eq(4).html('' +
                                '<span>' +
                                '<a class="deleteUser" href="javascript:;" userid=${user.id } username=${user.userName } id="delete' + i + '" >\n' +
                                '<img src="../../images/schu.png" alt="删除" title="删除"/></a>' +
                                '</span>')
                        }
                        if (Number(status) === Number("3")) {
                            td.eq(4).html('<span><a href="#" class="add" title="添加" id="add' + i + '" ></a></span>')
                        }
                        if (Number(status) === Number("1") || Number(status) === Number("2")) {
                            td.eq(4).html('<span></span>')
                        }
                        list(room, i)
                    }
                )
            },
            error: function () {
                console.log("出现错误！");
            }

        })
    }
    fn(n, pageIndex)


    function list(room, i) {
        $("#delete" + i).bind("click", function () {
            fn4(room)
        })
        $("#add" + i).bind("click", function () {
            fn3(room)
        })
        $("#agree" + i).bind("click", function () {
            fn1(room)
        })
        $("#disagree" + i).bind("click", function () {
            fn2(room)
        })
    }

    searchbutton.bind("click", function () {
        n = roomType.val();
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

    var flag1 = true
    var sum = 0
    change.bind("click", function () {
        sum++
        var tr = $("table").find('tr')
        var td = tr.eq(1).find('td');
        console.log(flag1)
        console.log(sum)
        if (flag1) {
            if (Number(n) === Number(-1)) {
                alert("请先选择房型再修改价格！")
                td.eq(2).html('<span>' + oldPrice + '元</span>')
                return false
            }
            td.eq(2).html('<input type="text" name="price" placeholder="在此修改房价">')
            flag1 = false
        } else {
            var price = $("input[name='price']").val()
            if (price === "在此修改房价" || price === "") {
                flag1 = true
                td.eq(2).html('<span>' + oldPrice + '元</span>')
                return false
            }
            if (!ifAge(price)) {
                alert("房价格式错误！")
                flag1 = true
                td.eq(2).html('<span>' + oldPrice + '元</span>')
                return false
            }
            td.eq(2).html('<span>' + price + '元</span>')
            flag1 = true
            $.ajax({
                url: "/api/room/modifyPrice",
                type: "POST",
                data: {
                    method: "modifyPrice",
                    roomType: n,
                    price: price
                },
                success: function (res) {
                    if (res === "true") {
                        alert("成功！")
                        fn(n, pageIndex)
                    } else {
                        alert("失败！")
                        fn(n, pageIndex)
                    }
                }, error: function () {
                    console.log("出现错误！");
                }
            })
        }
    })

    //同意
    fn1 = function (room) {
        var roomNumber = $(room).attr("roomNumber")
        var price = $(room).attr("price")
        $.ajax({
            url: "/api/room/removeAccept",
            type: "POST",
            data: {
                method: "removeAccept",
                roomNumber: roomNumber,
                price: price
            },
            success: function (res) {
                if (res === "true") {
                    alert("成功！")
                    fn(n, pageIndex)
                } else {
                    alert("失败！")
                    fn(n, pageIndex)
                }
            }, error: function () {
                console.log("出现错误！");
            }
        })
    }

    //拒绝
    fn2 = function (room) {
        var roomNumber = $(room).attr("roomNumber")
        var price = $(room).attr("price")
        $.ajax({
            url: "/api/room/removeRefuse",
            type: "POST",
            data: {
                method: "removeRefuse",
                roomNumber: roomNumber,
                price: price
            },
            success: function (res) {
                if (res === "true") {
                    alert("成功！")
                    fn(n, pageIndex)
                } else {
                    alert("失败！")
                    fn(n, pageIndex)
                }
            }, error: function () {
                console.log("出现错误！");
            }
        })
    }

    //添加
    fn3 = function (room) {
        var roomNumber = $(room).attr("roomNumber")
        var price = $(room).attr("price")
        $.ajax({
            url: "/api/room/add",
            type: "POST",
            data: {
                method: "add",
                roomNumber: roomNumber,
                price: price
            },
            success: function (res) {
                if (res === "true") {
                    alert("成功！")
                    fn(n, pageIndex)
                } else {
                    alert("失败！")
                    fn(n, pageIndex)
                }
            }, error: function () {
                console.log("出现错误！");
            }
        })
    }

    //删除
    fn4 = function (room) {
        var roomNumber = $(room).attr("roomNumber")
        var price = $(room).attr("price")
        $.ajax({
            url: "/api/room/delete",
            type: "POST",
            data: {
                method: "delete",
                roomNumber: roomNumber,
                price: price
            },
            success: function (res) {
                if (res === "true") {
                    alert("成功！")
                    fn(n, pageIndex)
                } else {
                    alert("失败！")
                    fn(n, pageIndex)
                }
            }, error: function () {
                console.log("出现错误！");
            }
        })
    }

    // fn5 = function (select, roomNumber, price) {
    //     $.ajax({
    //         url: "/api/room/",
    //         type: "POST",
    //         data: {
    //             method: "select",
    //             roomNumber: roomNumber,
    //             price: price
    //         },
    //         success: function (res) {
    //             if (res === "true") {
    //                 alert("成功！")
    //                 fn(n, pageIndex)
    //             } else {
    //                 alert("失败！")
    //                 fn(n, pageIndex)
    //             }
    //         }, error: function () {
    //             console.log("出现错误！");
    //         }
    //     })
    // }

    //每五十秒中调用一次
    setInterval(fn(n, pageIndex), 50000);
})