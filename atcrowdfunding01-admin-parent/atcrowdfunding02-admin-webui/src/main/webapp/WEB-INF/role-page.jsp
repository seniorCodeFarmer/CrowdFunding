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
<link rel="stylesheet" href="css/pagination.css">
<script type="text/javascript" src="jquery/jquery.pagination.js"></script>
<script type="text/javascript" src="crowd/my-role.js"></script>
<script type="text/javascript">
    $(function () {
        // 1、为分页操作初始化数据
        window.pageNum = 1;
        window.pageSize = 5;
        window.keyword = "";

        // 2、调用执行分页的函数，显示分页效果
        generatePage();

        // 3、给查询按钮绑定单击响应函数
        $("#searchBtn").click(function () {
            // 获取关键词数据赋值给对应的全局变量
            window.keyword = $("#keywordInput").val();

            // 调用分页函数刷新页面
            generatePage();
        })

        // 4、点击新增按钮打开模态框
        $("#showAddModalBtn").click(function () {
            $("#addModal").modal('show');
        })

        // 给角色保存按钮绑定单击函数
        $("#saveRoleBtn").click(function () {

            // 获取用户在文本框中输入的角色名称
            var roleName = $.trim($("#addModal [name=roleName]").val());

            // 发送ajax请求
            $.ajax({
                "url": "role/save.json",
                "type": "post",
                "data": {
                    "name": roleName
                },
                "dataType": "json",
                "success": function (response) {
                    var resultCode = response.resultCode;
                    if (resultCode == "SUCCESS") {
                        layer.msg("操作成功");
                        // 将页码调整到最后一页
                        window.pageNum = 9999;
                        // 重新加载分页数据
                        generatePage();
                    }
                    if (resultCode == "FAILED") {
                        layer.msg("操作失败" + response.message)
                    }
                },
                "error": function (response) {
                    layer.msg(response.status + " " + response.statusText);
                }
            });
            // 关闭模态框
            $("#addModal").modal('hide');

            // 清理模态框
            $("#addModal [name=roleName]").val("");
        })

        // 6、给铅笔按钮绑定单击响应函数
        // 传统的事件绑定方式只能在第一页有效
        // $(".pencilBtn").click(function () {
        //     $("#editModal").modal('show');
        // })
        // 使用jQuery对象的on()函数可以解决此问题
        // 找到动态生成的元素所附着的静态元素
        // 事件类型、选择器、事件响应函数
        $("#rolePageBody").on("click", ".pencilBtn", function () {
            $("#editModal").modal('show');

            var roleName = $(this).parent().prev().text();

            // 获取当前角色id
            // 为了让执行更新的按钮能获取此值，声明为全局变量
            window.roleId = this.id;

            // 使用roleName回显模态框文本
            $("#editModal [name=roleName]").val(roleName);
        });

        // 给更新按钮绑定单击响应函数
        $("#editRoleBtn").click(function () {

            var roleName = $("#editModal [name=roleName]").val();

            $.ajax({
                "url": "role/update.json",
                "type": "post",
                "data": {
                    "id": window.roleId,
                    "name": roleName,
                },
                "dataType": "json",
                "success": function (response) {
                    var resultCode = response.resultCode;
                    if (resultCode == "SUCCESS") {
                        layer.msg("操作成功");
                        // 重新加载分页数据
                        generatePage();
                    }
                    if (resultCode == "FAILED") {
                        layer.msg("操作失败" + response.message)
                    }
                },
                "error": function (response) {
                    layer.msg(response.status + " " + response.statusText);
                }
            })
            // 关闭模态框
            $("#editModal").modal('hide');
        })


        // 点击确认模态框中的确认删除执行删除
        $("#removeRoleBtn").click(function () {

            var requestBody = JSON.stringify(window.roleIdArray);

            $.ajax({
                "url": "role/remove/by/role/id/array.json",
                "type": "post",
                "data": requestBody,
                "contentType": "application/json;charset=UTF-8",
                "dataType": "json",
                "success": function (response) {
                    var resultCode = response.resultCode;
                    if (resultCode == "SUCCESS") {
                        layer.msg("操作成功");
                        // 重新加载分页数据
                        generatePage();
                    }
                    if (resultCode == "FAILED") {
                        layer.msg("操作失败" + response.message)
                    }
                },
                "error": function (response) {
                    layer.msg(response.status + " " + response.statusText);
                }
            });
            // 关闭模态框
            $("#confirmModal").modal('hide');
        });

        // 9、给单条删除按钮绑定单击函数
        $("#rolePageBody").on("click", ".removeBtn", function () {
            //从当前按钮出发获取rolename
            var roleName = $(this).parent().prev().text();
            // 创建role对象
            var roleArray = [{
                roleId: this.id,
                roleName: roleName
            }]
            showConfirmModal(roleArray);
        })

        // 10、给总的checkBox绑定单击响应函数
        $("#summaryBox").click(function () {
            // 获取当前多选框自身的状态
            var currentStatus = this.checked;

            // 用当前多选框的状态设置其他多选框状态
            $(".itemBox").prop("checked",currentStatus);
        })

        // 11、全选全不选的反向操作
        $("#rolePageBody").on("click", ".itemBox", function () {
            // 获取当前已经选中的itemBox的数量
            var checkedBoxCount = $(".itemBox:checked").length;

            // 获取全部itemBox的数量
            var totalBoxCount = $(".itemBox").length;

            // 使用二者的比较结果设置全选
            $("#summaryBox").prop("checked",checkedBoxCount == totalBoxCount);
        });

        // 12、给批量删除函数的按钮绑定响应函数
        $("#batchRemoveBtn").click(function () {

            var roleAarry = [];

            // 遍历所选中的复选框
            $(".itemBox:checked").each(function () {
                // 使用this引用当前遍历到的复选框
                var roleId = this.id;

                var roleName = $(this).parent().next().text();

                roleAarry.push({
                    "roleId": roleId,
                    "roleName": roleName
                })
            })

            // 检查roleAarry的长度是否为0
            if (roleAarry.length == 0) {
                layer.msg("请至少选择一个删除");
                return;
            }

            showConfirmModal(roleAarry);

        })
    })
</script>
<body>
<%@include file="include-nav.jsp" %>

<div class="container-fluid">
    <div class="row">
        <%@include file="include-sidebar.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
                </div>
                <div class="panel-body">
                    <form class="form-inline" role="form" style="float:left;">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">查询条件</div>
                                <input id="keywordInput" name="keyword" class="form-control has-success" type="text"
                                       placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button id="searchBtn" type="button" class="btn btn-warning"><i
                                class="glyphicon glyphicon-search"></i> 查询
                        </button>
                    </form>
                    <button type="button" id="batchRemoveBtn" class="btn btn-danger"
                            style="float:right;margin-left:10px;"><i
                            class=" glyphicon glyphicon-remove"></i> 删除
                    </button>
                    <button id="showAddModalBtn" type="button" class="btn btn-primary" style="float:right;"><i
                            class="glyphicon glyphicon-plus"></i> 新增
                    </button>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr>
                                <th width="30">#</th>
                                <th width="30"><input id="summaryBox" type="checkbox"></th>
                                <th>名称</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody id="rolePageBody">
                            </tbody>
                            <tfoot>
                            <tr>
                                <td colspan="6" align="center">
                                    <div id="Pagination" class="pagination"><!-- 这里显示分页 --></div>
                                </td>
                            </tr>

                            </tfoot>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="modal-role-add.jsp" %>
<%@include file="modal-role-edit.jsp" %>
<%@include file="modal-role-confirm.jsp" %>
</body>
</html>