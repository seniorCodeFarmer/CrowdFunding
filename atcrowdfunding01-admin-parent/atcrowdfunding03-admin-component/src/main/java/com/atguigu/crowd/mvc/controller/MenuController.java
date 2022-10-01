package com.atguigu.crowd.mvc.controller;

import com.atguigu.crowd.entity.Menu;
import com.atguigu.crowd.service.api.MenuService;
import com.atguigu.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhangchengwei
 * @create 2022-09-27 22:37
 */
@RestController
public class MenuController {

    @Autowired
    private MenuService menuService;

    @RequestMapping("/menu/get/whole/tree.json")
    public ResultEntity<Menu> getWholeTreeNew() {
        //1、查询全部的Menu对象
        List<Menu> menuList = menuService.getAll();

        // 2、声明一个变量用来存储找到的节点
        Menu root = null;

        // 3、创建Map对象用来存储id和Menu对象的对应关系
        Map<Integer,Menu> menuMap = new HashMap<>();

        // 4、遍历menuList填充menuMap
        for (Menu menu : menuList) {
            Integer id = menu.getId();
            menuMap.put(id,menu);
        }

        // 5、再次遍历menuList查找根节点、组装父子节点
        for (Menu menu : menuList) {
            Integer pid = menu.getPid();
            if (pid == null) {
                root = menu;
                continue;
            }

            Menu fatherMenu = menuMap.get(pid);
            fatherMenu.getChildren().add(menu);

        }
        return ResultEntity.successWithData(root);
    }

    @RequestMapping("menu/sava.json")
    public ResultEntity<String> savaMenu(Menu menu) {
        menuService.saveMenu(menu);

        return ResultEntity.successWithOutData();
    }

    @RequestMapping("menu/update.json")
    public ResultEntity<String> updateMenu(Menu menu) {
        menuService.update(menu);

        return ResultEntity.successWithOutData();
    }

    @RequestMapping("menu/remove.json")
    public ResultEntity<String> removeMenu(@RequestParam("id") Integer id) {
        menuService.removeMenu(id);

        return ResultEntity.successWithOutData();
    }


//    public ResultEntity<Menu> getWholeTreeOld() {
//        // 1、查询全部的Menu对象
//        List<Menu> menuList = menuService.getAll();
//
//        // 2、声明一个变量用来存储找到的节点
//        Menu root = null;
//
//        // 3、遍历menuList
//        for (Menu menu : menuList) {
//            // 4、获取当前menu对象的pid属性值
//            Integer pid = menu.getPid();
//
//            // 5、检查Pid是否为null
//            if (pid == null) {
//                root = menu;
//                continue;
//            }
//            // 6、如果pid不为null，则说明当前节点有父节点，找到父节点就可以进行组装，建立父子关系
//            for (Menu mybeFather : menuList) {
//                if (Objects.equals(mybeFather.getId(),menu.getPid())) {
//                    // 将子节点存入父节点的children集合
//                    mybeFather.getChildren().add(menu);
//                    break;
//                }
//            }
//        }
//        return ResultEntity.successWithData(root);
//    }
}
