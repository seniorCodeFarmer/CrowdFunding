function fillAuthTree() {
    var ajaxReturn = $.ajax({
        "url": "assign/get/all/auth.json",
        "type": "post",
        "dataType": "json",
        "async": false
    })

    if (ajaxReturn.status != 200) {
        layer.msg("请求处理出错！响应状态码是：" + ajaxReturn.status+"说明是：" + ajaxReturn.statusText);
        return;
    }

    var authList = ajaxReturn.responseJSON.data;

    var setting = {
        "data": {
            "simpleData": {
                // 开启简单json的功能
                "enable": true,
                "pIdKey": "categoryId"
            },
            "key": {
                "name": "title"
            },
        },
        "check": {
            "enable": true
        }
    };

    // 生成树形结构
    $.fn.zTree.init($("#authTreeDemo"),setting, authList);

    // 把节点展开
    var zTreeObj = $.fn.zTree.getZTreeObj("authTreeDemo");
    zTreeObj.expandAll(true);

    // 查询已分配的auth的id组成的数组
    ajaxReturn = $.ajax({
        "url": "assign/get/assignd/auth/id/by/role/id.json",
        "type": "post",
        "data": {
            "roleId": window.roleId
        },
        "dataType": "json",
        "async": false
    })

    if (ajaxReturn.status != 200) {
        layer.msg("请求处理出错！响应状态码是：" + ajaxReturn.status+"说明是：" + ajaxReturn.statusText);
        return;
    }

    var authIdList = ajaxReturn.responseJSON.data;

    // 根据authIdArray把树形结构中对应的多选框选中
    for(var i = 0;i <authIdList.length;i++) {
        var authId = authIdList[i];

        // 根据id查询树形结构中对应的节点
        var treeNode = zTreeObj.getNodeByParam("id",authId);

        // 将treeNode 设置为被勾选
        // checked设置为true表示节点勾选
        // checkTypeFlag 设置为false，表示不联动
        zTreeObj.checkNode(treeNode,true,false);
    }
}


// 声明专门的函数显示确认模态框
function showConfirmModal(roleArray) {
    $("#confirmModal").modal("show");

    $("#roleNameDiv").empty();

    window.roleIdArray = [];

    // 遍历roleArray
    for (var i = 0;i < roleArray.length;i++) {
        var role = roleArray[i];
        var roleName = role.roleName;
        $("#roleNameDiv").append(roleName+"<br>");

        var roleId = role.roleId;
        window.roleIdArray.push(roleId);
    }

}

// 执行分页，生成页面效果，任何时候调用这个函数都会重新加载页面
function generatePage() {

    // 1、获取分页数据
    var pageInfo = getPageInfoRemote();

    // 2、填充表格
    fillTableBody(pageInfo);
}

// 远程访问服务器端程序获取pageInfo数据
function getPageInfoRemote() {
    var ajaxResult = $.ajax({
        "url": "role/get/page/info.json",
        "type": "post",
        "async": false,
        "data": {
            "pageNum": window.pageNum,
            "pageSize": window.pageSize,
            "keyword": window.keyword
        },
        "dataType": "json"
    })
    console.log(ajaxResult);
    // 判断当前响应状态码
    var statusCode = ajaxResult.status;
    if (statusCode != 200) {
        layer.msg("服务器端程序调用失败！响应状态码="+statusCode+" 说明信息=" + ajaxResult.statusText);
        return null;
    }
    var resultEntity = ajaxResult.responseJSON;

    // 从resultEntity中获取result属性
    var resultCode = resultEntity.resultCode;

    // 判断resultCode是否成功
    if (resultCode == "FAILED") {
        layer.msg(resultEntity.message);
        return null;
    }

    // 确认result成功后获取pageInfo
    var pageInfo = resultEntity.data;
    return pageInfo;
}

// 填充表格
function fillTableBody(pageInfo) {

    // 清楚tbody中旧数据
    $("#rolePageBody").empty();

    // 没有搜索结果时不显示导航条
    $("#Pagination").empty();

    // 判断pageInfo是否有效
    if(pageInfo == null || pageInfo == undefined || pageInfo.list == null || pageInfo.list.length == 0) {
        $("#rolePageBody").append("<tr><td colspan='4'>抱歉！没有查询到数据</td></tr>")
        return;
    }

    // 填充tbody
    for (var i = 0;i < pageInfo.list.length;i++) {
        var role = pageInfo.list[i];
        var roleId = role.id;
        var roleName = role.name;
        var numberTd = "<td>"+ (i + 1) +"</td>";
        var checkbok = "<td><input id='"+roleId+"' class='itemBox' type='checkbox'></td>";
        var roleNameTd = "<td>" + roleName + "</td>";

        var checkBtn = "<button id='"+roleId+"' type='button' class='btn btn-success btn-xs checkBtn'><i class='glyphicon glyphicon-check'></i></button>";
        // 通过button标签的id属性，把roleId值传递到button按钮的单击响应函数中
        var pencilBtn = "<button id='"+roleId+"' type='button' class='btn btn-primary btn-xs pencilBtn'><i class='glyphicon glyphicon-pencil'></i></button>";
        //
        var removeBtn = "<button id='"+roleId+"' type='button' class='btn btn-danger btn-xs removeBtn'><i class='glyphicon glyphicon-remove'></i></button>";

        var buttonTd = "<td>" +checkBtn+" "+pencilBtn+" "+removeBtn+ "</td>";

        var tr = "<tr>" + numberTd + checkbok + roleNameTd + buttonTd + "</tr>";

        $("#rolePageBody").append(tr);
    }

    // 生成分页导航条
    generateNavigator(pageInfo);
}

// 生成分页页码导航条
function generateNavigator(pageInfo) {
    // 获取总记录数
    var totalRecord = pageInfo.total;

    // 声明相关属性
    var properties = {
        num_edge_entries: 3, //边缘页数
        num_display_entries: 5, //主体页数
        callback: paginationCallBack,
        items_per_page: pageInfo.pageSize,
        current_page: pageInfo.pageNum - 1,
        prev_text: "上一页",  //上一页按钮上显示的文本
        next_text: "下一页"   //下一页按钮上显示的文本
    }

    // 调用pagination()函数
    $("#Pagination").pagination(totalRecord,properties);
}

// 翻页式的回调函数
function paginationCallBack(pagaIndex, jQuery) {
    // 根据pageIndex计算得到pageNum
    window.pageNum = pageIndex + 1;
    // 调用分页函数
    generatePage();
    //由于每一个页码按钮都是超链接，所以在这个函数最后取消超链接的默认行为
    return false;
}

