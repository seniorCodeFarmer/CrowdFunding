<%--
  Created by IntelliJ IDEA.
  User: 张成伟
  Date: 2022/9/25
  Time: 20:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="UTF-8">
<%@include file="include-head.jsp" %>
<link rel="stylesheet" href="ztree/zTreeStyle.css" />
<script type="text/javascript" src="ztree/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="crowd/my-menu.js"></script>
<script type="text/javascript">
    $(function () {
        // 调用封装好的函数初始化树形结构
        generateTree();

        // 给添加子节点绑定单击响应函数
        $("#treeDemo").on("click",".addBtn",function (){
            // 将当前节点的id,作为新节点的pid保存到全局变量
            window.pid = this.id;

            $("#menuAddModal").modal('show');

            return false;
        });

        // 给添加子节点的模态框中的保存按钮绑定单击响应函数
        $("#menuSaveBtn").click(function () {
            // 收集表单项中用户输入的数据
            var name = $.trim($("#menuAddModal [name=name]").val());
            var url = $.trim($("#menuAddModal [name=url]").val());

            // 单选按钮要定位到“被选中”的那一个
            var icon =  $("#menuAddModal [name=icon]:checked").val();

            // 发送ajax请求
            $.ajax({
                "url": "menu/sava.json",
                "type": "post",
                "data": {
                    "pid": window.pid,
                    "name": name,
                    "url": url,
                    "icon": icon
                },
                "dataType": "json",
                "success": function (response) {
                    var result = response.resultCode;
                    if (result == "SUCCESS") {
                        layer.msg("操作成功！");
                        // 重新加载树形结构
                        generateTree();
                    }
                    if (result == "FAILED") {
                        layer.msg("操作失败！" + response.message)
                    }
                },
                "error": function (response) {
                    layer.msg(response.status + " " + response.statusText)
                }
            });
            $("#menuAddModal").modal('hide');

            // 清空表单
            // jQuery对象调用click() ，不传任何参数，相当于用户点击了一下
            $("#menuResetBtn").click();
        });

        // 给更新按钮绑定单击响应函数
        $("#treeDemo").on("click",".editBtn",function (){
            // 将当前节点的id,作为新节点的pid保存到全局变量
            window.id = this.id;

            $("#menuEditModal").modal('show');

            // 获取zTreeObj对象
            var zTreeObj = $.fn.zTree.getZTreeObj("treeDemo");

            // 根据id属性查询节点
            var key = "id";
            var value = window.id;
            var currentNode = zTreeObj.getNodeByParam(key,value);

            // 回显表单数据
            $("#menuEditModal [name=name]").val(currentNode.name);
            $("#menuEditModal [name=url]").val(currentNode.url);
            $("#menuEditModal [name=icon]").val([currentNode.icon]);

            return false;
        });

        // 给更新模态框中的更新按钮绑定单击响应函数
        $("#menuEditBtn").click(function () {

            // 收集表单数据
            var name = $("#menuEditModal [name=name]").val();
            var url = $("#menuEditModal [name=url]").val();
            var icon = $("#menuEditModal [name=icon]:checked").val();

            // 发送ajax请求
            $.ajax({
                "url": "menu/update.json",
                "type": "post",
                "data": {
                    "id": window.id,
                    "name": name,
                    "url": url,
                    "icon": icon
                },
                "dataType": "json",
                "success": function (response) {
                    var result = response.resultCode;
                    if (result == "SUCCESS") {
                        layer.msg("操作成功！");
                        // 重新加载树形结构
                        generateTree();
                    }
                    if (result == "FAILED") {
                        layer.msg("操作失败！" + response.message)
                    }
                },
                "error": function (response) {
                    layer.msg(response.status + " " + response.statusText)
                }
            });
            $("#menuEditModal").modal('hide');
        });

        // 给x按钮绑定单击响应函数
        $("#treeDemo").on("click",".removeBtn",function (){
            // 将当前节点的id,作为新节点的pid保存到全局变量
            window.id = this.id;

            $("#menuConfirmModal").modal('show');

            // 获取zTreeObj对象
            var zTreeObj = $.fn.zTree.getZTreeObj("treeDemo");

            // 根据id属性查询节点
            var key = "id";
            var value = window.id;
            var currentNode = zTreeObj.getNodeByParam(key,value);

            $("#removeNodeSpan").html("【<i class='"+ currentNode.icon +"'></i>" + currentNode.name + "】");

            return false;
        });

        // 给确认模态框中的ok按钮绑定单击响应函数
        $("#confirmBtn").click(function () {
            $.ajax({
                "url": "menu/remove.json",
                "type": "post",
                "data": {
                    "id": window.id
                },
                "dataType": "json",
                "success": function (response) {
                    var result = response.resultCode;
                    if (result == "SUCCESS") {
                        layer.msg("操作成功！");
                        // 重新加载树形结构
                        generateTree();
                    }
                    if (result == "FAILED") {
                        layer.msg("操作失败！" + response.message)
                    }
                },
                "error": function (response) {
                    layer.msg(response.status + " " + response.statusText)
                }
            });
            $("#menuConfirmModal").modal('hide');
        });
    })
</script>
<body>
<%@include file="include-nav.jsp" %>

<div class="container-fluid">
    <div class="row">
        <%@include file="include-sidebar.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <div class="panel panel-default">
                <div class="panel-heading"><i class="glyphicon glyphicon-th-list"></i> 权限菜单列表
                    <div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i
                            class="glyphicon glyphicon-question-sign"></i></div>
                </div>
                <div class="panel-body">
                    <ul id="treeDemo" class="ztree">

                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="modal-menu-add.jsp" %>
<%@include file="modal-menu-edit.jsp" %>
<%@include file="modal-menu-confirm.jsp" %>
</body>
</html>