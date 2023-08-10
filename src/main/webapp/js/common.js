var ifChineseName = null;
var ifAge = null;
var ifPhoneNumber = null;
var ifIDNumber = null;
var ifTimeTrue = null;
var ifAgeTrue = null;
var ifSexTrue = null;
var ifUsername = null;
var ifPassword = null;
var ifEmail = null;
var format = null;
var ifSexa = null;
var ifsSignal = null;
var andRoom = null;
var andRoom1 = null

var A_PRICE  //普通单间
var B_PRICE  //豪华单间
var C_PRICE  //普通双间
var D_PRICE  //贵宾套房
var E_PRICE  //总统套房

//房间类型
ROOM_TYPE_0 = 0;//普通单间
ROOM_TYPE_1 = 1;//豪华单间
ROOM_TYPE_2 = 2;//普通双间
ROOM_TYPE_3 = 3;//贵宾套房
ROOM_TYPE_4 = 4;//总统套房

//房间状态
ROOM_STATUS_0 = 0;//空闲
ROOM_STATUS_1 = 1;//已预订
ROOM_STATUS_2 = 2;//退房待处理
ROOM_STATUS_3 = 3;//已删除

//订单状态
ORDER_TYPE_0 = 0;//预定订单
ORDER_TYPE_1 = 1;//废弃订单
$(function () {

    //中文名字
    ifChineseName = function (name) {
        var reg = /[\u4e00-\u9fa5]/
        return reg.test(name);
    }

    //数字判断
    ifAge = function (number) {
        var reg = /^[0-9]*$/
        return reg.test(number);
    }

    //电话号码判断
    ifPhoneNumber = function (phoneNumber) {
        var reg = /^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\d{8}$/
        return reg.test(phoneNumber);
    }

    //身份证号判断
    ifIDNumber = function (IDNumber) {
        var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/
        return reg.test(IDNumber);
    }

    //预定时间判断
    ifTimeTrue = function (enterTime, exitTime) {
        var flag = false;
        var startTime = new Date(enterTime).getTime();
        var endTime = new Date(exitTime).getTime();
        var now = Date.parse(new Date());
        console.log(startTime)
        console.log(endTime)
        console.log(now)
        if (endTime > startTime && startTime >= now) {
            flag = true;
        }
        return flag;
    }

    //判断年龄是否有效
    ifAgeTrue = function (age, IDNumber) {
        year = Number(IDNumber.substr(6, 4))
        var time = new Date();
        var year1 = time.getFullYear();
        console.log(year)
        console.log(year1)
        var number = year1 - year
        console.log(number)
        console.log(age)

        return Number(number) === Number(age);

    }

    ifSexa = function (sex) {
        return sex === "男" || sex === "女";
    }

    //性别是否正确
    ifSexTrue = function (sex, IDNumber) {
        var s = Number(IDNumber[IDNumber.length - 2]);
        if (s === Number(1) || s === Number(3) || s === Number(5) || s === Number(7) || s === Number(9)) {
            gender = "男"
        } else {
            gender = "女"
        }
        return gender === sex;
    }
    //判断用户名是否正确格式
    ifUsername = function (username) {
        var reg = /^[a-zA-Z0-9_-]{4,16}$/;
        return reg.test(username);
    }

    //判断密码是否是正确格式
    ifPassword = function (password) {
        var reg = /^[\w]{6,12}$/;
        return reg.test(password);
    }

    //判断邀请码是否正确
    ifsSignal = function (sSignal) {
        var reg = /^\d{6}$/;
        return reg.test(sSignal)
    }
    //判断电子邮箱是否正确格式
    ifEmail = function (email) {
        var reg = /^(\w+([-.][A-Za-z0-9]+)*){3,18}@\w+([-.][A-Za-z0-9]+)*\.\w+([-.][A-Za-z0-9]+)*$/;
        return reg.test(email);
    }

    //时间戳转换成日期
    function add0(m) {
        return m < 10 ? '0' + m : m
    }

    format = function (timeS) {
        //timeS是整数，否则要parseInt转换
        var time = new Date(timeS);
        var y = time.getFullYear();
        var m = time.getMonth() + 1;
        var d = time.getDate();
        var h = time.getHours();
        var mm = time.getMinutes();
        var s = time.getSeconds();
        return y + '-' + add0(m) + '-' + add0(d) + ' ' + add0(h) + ':' + add0(mm) + ':' + add0(s);
    }


    andRoom = function (roomType, roomNum) {
        var roomNumber
        if (roomType === "0") {
            roomNumber = "A-" + roomNum
        }
        if (roomType === "1") {
            roomNumber = "B-" + roomNum
        }
        if (roomType === "2") {
            roomNumber = "C-" + roomNum
        }
        if (roomType === "3") {
            roomNumber = "D-" + roomNum
        }
        if (roomType === "4") {
            roomNumber = "E-" + roomNum
        }
        return roomNumber
    }
    andRoom1 = function (roomType) {
        var roomName
        if (roomType === "0") {
            roomName = "普通单间"
        }
        if (roomType === "1") {
            roomName = "豪华单间"
        }
        if (roomType === "2") {
            roomName = "普通双间"
        }
        if (roomType === "3") {
            roomName = "贵宾套房"
        }
        if (roomType === "4") {
            roomName = "总统套房"
        }
        return roomName
    }
})