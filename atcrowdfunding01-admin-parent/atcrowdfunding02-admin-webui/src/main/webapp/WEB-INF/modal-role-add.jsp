<%--
  Created by IntelliJ IDEA.
  User: 张成伟
  Date: 2022/9/27
  Time: 10:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <div id="addModal" class="modal fade" tabindex="-1" role="dialog">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">尚筹网系统弹窗</h4>
                </div>
                <div class="modal-body">
                    <p>新增角色</p>
                    <form class="form-signin" role="form">
                        <div class="form-group has-success has-feedback">
                            <input type="text" name="roleName" class="form-control" placeholder="请输入角色名称" autofocus>
                            <span class="glyphicon glyphicon-user form-control-feedback"></span>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button id="saveRoleBtn" type="button" class="btn btn-primary">保存</button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
</body>
</html>
