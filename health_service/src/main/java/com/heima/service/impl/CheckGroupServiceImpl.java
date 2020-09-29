package com.heima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.StringUtil;
import com.heima.dao.CheckGroupDao;
import com.heima.health.HealthException;
import com.heima.health.entity.PageResult;
import com.heima.health.entity.QueryPageBean;
import com.heima.pojo.CheckGroup;
import com.heima.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 2 * @Author: liwanlei
 * 3 * @Date: 2020/9/21 15:34
 * 4
 */
@Service(interfaceClass = CheckGroupService.class)
public class CheckGroupServiceImpl implements CheckGroupService {
    /**
     * 添加检查组
     * @params
     * @return void
     */
    @Autowired
    private CheckGroupDao checkGroupDao;

    @Override
    @Transactional
    public void add(CheckGroup checkGroup, Integer[] checkitemIds) {
        //添加检查组
        checkGroupDao.add(checkGroup);
        //获取检查组id
        Integer checkGroupId = checkGroup.getId();
        //循环遍历被选中检查项的id
           if (null!=checkitemIds){
               for (Integer checkitemId:checkitemIds){
                   checkGroupDao.addCheckGroupCheckItem(checkGroupId,checkitemId);
               }
           }

    }


    /**
     * 分页查询
     * @params
     * @return com.heima.health.entity.PageResult<com.heima.pojo.CheckItem>
     */
    @Override
    public PageResult<CheckGroup> findPage(QueryPageBean queryPageBean) {
        //使用PageHelper分页插件，在sqlMapConfig.xml或者sqlSessionFactoryBean
        //初始页当前页码和当前页面
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        //模糊查询
        //判断是否有查询条件
        if (!StringUtil.isEmpty(queryPageBean.getQueryString())){
            //有查询条件，拼接%
            queryPageBean.setQueryString("%"+queryPageBean.getQueryString()+"%");
        }
        //查询的语句会被分页
        Page<CheckGroup> page= checkGroupDao.findPage(queryPageBean.getQueryString());
        //封装到分页结果对象中
        PageResult<CheckGroup> pageResult = new PageResult<CheckGroup>(page.getTotal(), page.getResult());

        return pageResult;
    }
    /**
     * 通过id查询检查组列表信息
     * @params
     * @return com.heima.pojo.CheckGroup
     */
    @Override
    public CheckGroup findById(int id) {
        return checkGroupDao.findById(id);
    }

    @Override
    public List<Integer> findCheckItemIdsByCheckGroupId(int id) {
        return checkGroupDao.findCheckItemIdsByCheckGroupId(id);
    }
    /**
     * 修改检查组信息
     * @params
     * @return void
     */
    @Override
    @Transactional
    public void update(CheckGroup checkGroup, Integer[] checkitemIds) {
        //先更新检查组信息
        checkGroupDao.update(checkGroup);
        //获取检查组id
        Integer checkGroupId=checkGroup.getId();
        //删除旧的关系
        checkGroupDao.deleteGroupCheckItem(checkGroupId);
        if (null!=checkitemIds){
            for (Integer checkitemId:checkitemIds){
                checkGroupDao.addCheckGroupCheckItem(checkGroupId,checkitemId);
            }
        }
    }



    /**
     * 查询所有检查组
     * @params
     * @return
     */
    @Override
    public List<CheckGroup> findAll() {
        return checkGroupDao.findAll();
    }
    /**
     * 通过id删除
     * @params
     * @return void
     */
    @Override
    @Transactional
    public void deleteById(int id) throws HealthException {
        //判断是否被套餐使用
     int count= checkGroupDao.findSetmealCountByCheckGroupId(id);
     if (count>0){
         //使用了就抛出异常
         throw new HealthException("访检查组已经被套餐使用了，不能删除");
     }
        //未被使用
        //先删除检查组和检查项关系
        checkGroupDao.deleteGroupCheckItem(id);
        //在删除检查组
        checkGroupDao.deleteById(id);




    }

}
