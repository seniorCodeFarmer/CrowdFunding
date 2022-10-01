package com.atguigu.crowd.service.api;

import com.atguigu.crowd.entity.Menu;

import java.util.List;

/**
 * @author zhangchengwei
 * @create 2022-09-27 22:34
 */
public interface MenuService {
    List<Menu> getAll();

    void saveMenu(Menu menu);

    void update(Menu menu);

    void removeMenu(Integer id);
}
