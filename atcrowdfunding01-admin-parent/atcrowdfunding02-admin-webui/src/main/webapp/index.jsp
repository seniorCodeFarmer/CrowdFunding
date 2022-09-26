<%--
  Created by IntelliJ IDEA.
  User: 张成伟
  Date: 2022/9/24
  Time: 22:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <base href="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/" />
    <script type="text/javascript" src="jquery/jquery-2.1.1.min.js"></script>
    <script type="text/javascript" src="layer/layer.js"></script>
    <script type="text/javascript">
        $(function (){

            $("#btn4").click(function (){
                //声明数组
                var student = {
                    "stuId": 5,
                    "stuName": "tom",
                    "address": {
                        "province": "安徽",
                        "city": "合肥",
                        "street": "莲花"
                    },
                    "subjectList": [
                        {
                            "subjectName": "JAVA",
                            "subjectScore": 100
                        },{
                            "subjectName": "C++",
                            "subjectScore": 100
                        }
                    ],
                    "map": {
                        "k1": "v1",
                        "k2": "v2"
                    }
                };

                var requestBody = JSON.stringify(student);
                $.ajax({
                    "url": "send/array/four.json",  //请求目标资源的地址
                    "type": "post",  //请求方式
                    "data": requestBody,
                    "contentType": "application/json;charset=UTF-8",  //请求体的内容类型
                    "dataType": "json", //如何对待服务器返回的数据
                    "success": function (response){  //服务器端成功处理请求后调用的回调函数
                        console.log(response);
                    },
                    "error": function (response){ //服务器端处理请求失败后调用的回调函数
                        console.log(response);
                    }
                });
            });


            $("#btn3").click(function (){
                //声明数组
                var array = [5,8,12];

                var requestBody = JSON.stringify(array);
                $.ajax({
                    "url": "send/array/three.html",  //请求目标资源的地址
                    "type": "post",  //请求方式
                    "data": requestBody,
                    "contentType": "application/json;charset=UTF-8",  //请求体的内容类型
                    "dataType": "text", //如何对待服务器返回的数据
                    "success": function (response){  //服务器端成功处理请求后调用的回调函数
                        alert(response);
                    },
                    "error": function (response){ //服务器端处理请求失败后调用的回调函数
                        alert(response);
                    }
                });
            });

            $("#btn2").click(function (){
                $.ajax({
                    "url": "send/array/two.html",  //请求目标资源的地址
                    "type": "post",  //请求方式
                    "data": {
                        "array[0]": 5,
                        "array[1]": 8,
                        "array[2]": 12
                    }, //请求参数
                    "dataType": "text", //如何对待服务器返回的数据
                    "success": function (response){  //服务器端成功处理请求后调用的回调函数
                        alert(response);
                    },
                    "error": function (response){ //服务器端处理请求失败后调用的回调函数
                        alert(response);
                    }
                });
            });

            $("#btn1").click(function (){
                $.ajax({
                    "url": "send/array/one.html",  //请求目标资源的地址
                    "type": "post",  //请求方式
                    "data": {
                        "array": [5,8,12]
                    }, //请求参数
                    "dataType": "text", //如何对待服务器返回的数据
                    "success": function (response){  //服务器端成功处理请求后调用的回调函数
                        alert(response);
                    },
                    "error": function (response){ //服务器端处理请求失败后调用的回调函数
                        alert(response);
                    }
                });
            });

            $("#btn5").click(function (){
                layer.msg("Layer弹框");
            });
        });
    </script>
</head>
<body>
    <a href="test/ssm.html">测试SSM整合的路径</a>
    <br/>
    <button id="btn1">Send[5,8,12] One</button></br>

    <button id="btn2">Send[5,8,12] Two</button></br>

    <button id="btn3">Send[5,8,12] Three</button><br>

    <button id="btn4">Send Object Four</button><br>

    <button id="btn5">点我弹框</button>

</body>
</html>
