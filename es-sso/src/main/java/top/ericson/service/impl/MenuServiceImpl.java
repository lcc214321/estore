package top.ericson.service.impl;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.extern.slf4j.Slf4j;
import top.ericson.mapper.MenuMapper;
import top.ericson.pojo.Menu;
import top.ericson.service.MenuService;
import top.ericson.vo.PageQuery;

@Slf4j
@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper nemuMapper;

    @Autowired
    HttpServletRequest request;

    /**
     * @author Ericson
     * @date 2020/04/30
     * @param menu
     * @see top.ericson.service.MenuService#createMenu(top.ericson.vo.info.Menu)
     * @description 新建
     */
    @Override
    public void create(Menu menu) {
        Integer userId = (Integer)request.getAttribute("userId");
        log.debug("nemuId:{}", userId);
        Date now = new Date();
        menu.setCreateTime(now)
            .setCreateUser(userId)
            .setUpdateTime(now)
            .setUpdateUser(userId);
        nemuMapper.insert(menu);
    }

    @Override
    public IPage<Menu> findPage(PageQuery pageQuery) {

        /*开启分页查询*/
        Page<Menu> page = new Page<>(pageQuery.getPageCurrent(), pageQuery.getPageSize());

        /*条件构造器*/
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();

        // 名称查询
        if (pageQuery.getName() != null) {
            queryWrapper.like("name", pageQuery.getName());
        }
        // 排序
        if (pageQuery.getOrderBy() != null && !"".equals(pageQuery.getOrderBy())) {
            queryWrapper.orderBy(true, pageQuery.getIsASC(), pageQuery.getOrderBy());
        }

        IPage<Menu> iPage = nemuMapper.selectPage(page, queryWrapper);
        return iPage;
    }

    /**
     * @author Ericson
     * @date 2020/04/29 15:00
     * @param id
     * @return
     * @see top.ericson.service.MenuService#deleteById(java.lang.Integer)
     * @description 
     */
    @Override
    public Integer deleteById(Integer id) {
        return nemuMapper.deleteById(id);
    }

    /**
     * @author Ericson
     * @date 2020/04/30
     * @param id
     * @param nemu
     * @return
     * @see top.ericson.service.MenuService#updateById(java.lang.Integer, top.ericson.vo.info.Menu)
     * @description 
     */
    @Override
    public Integer updateById(Menu nemu) {
        nemu.setMenuId(nemu.getMenuId());
        Integer userId = (Integer)request.getAttribute("userId");
        nemu.setUpdateTime(new Date())
            .setUpdateUser(userId);
        return nemuMapper.updateById(nemu);
    }

}