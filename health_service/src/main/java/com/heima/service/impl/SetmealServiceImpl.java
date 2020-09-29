package com.heima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.StringUtil;
import com.heima.dao.SetmealDao;
import com.heima.health.HealthException;
import com.heima.health.constant.MessageConstant;
import com.heima.health.entity.PageResult;
import com.heima.health.entity.QueryPageBean;
import com.heima.pojo.CheckGroup;
import com.heima.pojo.CheckItem;
import com.heima.pojo.Setmeal;
import com.heima.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 2 * @Author: liwanlei
 * 3 * @Date: 2020/9/22 11:02
 * 4
 */
@Service(interfaceClass = SetmealService.class)
public class SetmealServiceImpl implements SetmealService {

    @Autowired
   private SetmealDao setmealDao;
    
    @Override
    /**
     *模糊查询
     * @author: lwl
     * @date: 2020/9/22
     * @param queryPageBean: 
     * @return: com.heima.health.entity.PageResult<com.heima.pojo.Setmeal>
     */
    public PageResult<Setmeal> findPage(QueryPageBean queryPageBean) {
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
        Page<Setmeal> page= setmealDao.findByCondition(queryPageBean.getQueryString());
        //封装到分页结果对象中
        PageResult<Setmeal> pageResult = new PageResult<Setmeal>(page.getTotal(), page.getResult());
        return pageResult;
    }

    @Override
    @Transactional
    /**
     * 添加套餐
     * @author: lwl
     * @date: 2020/9/22
     * @param setmeal: 
     * @param checkGroupIds: 
     * @return: void
     */
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
        //先添加套餐
        setmealDao.add(setmeal);
        //获取新增的套餐id
        Integer setmealId = setmeal.getId();
        //循环遍历获取id
        if (null!=checkgroupIds){
            for (Integer checkGroupId:checkgroupIds){
                //添加套装与检查组的关系
                setmealDao.addSetmealCheckGroup(setmealId,checkGroupId);
            }
        }

    }

    @Override
    /**
     *通过id查询检查组id集合
     * @author: lwl
     * @date: 2020/9/22
     * @param id:
     * @return: java.util.List<java.lang.Integer>
     */
    public List<Integer> findCheckGroupIdsBySetmealId(int id) {

        return setmealDao.findCheckGroupIdsBySetmealId(id);
    }



    @Override
    /**
     *查询选中套餐检查组的id
     * @author: lwl
     * @date: 2020/9/22
     * @param id:
     * @return: com.heima.pojo.Setmeal
     */
    public Setmeal findById(int id) {
        return setmealDao.findById(id);
    }


    /**
     *修改套餐
     * @author: lwl
     * @date: 2020/9/22
     * @param setmeal:
     * @param checkgroupIds:
     * @return: void
     */
    @Override
    @Transactional
    public void update(Setmeal setmeal, Integer[] checkgroupIds) {
        //先更新套餐
        setmealDao.update(setmeal);
        //删除旧关系
        setmealDao.deleteSetmealCheckGroup(setmeal.getId());
        //添加新关系
        if (null!=checkgroupIds){
            for (Integer checkgroupId:checkgroupIds){
                setmealDao.addSetmealCheckGroup(setmeal.getId(),checkgroupId);
            }
        }

    }


    /**
     * 删除套餐，检查判断套餐是否被删除
     * @author: lwl
     * @date: 2020/9/22
     * @param id:
     * @return: void
     */
    @Override
    @Transactional
    public void deleteById(int id)throws HealthException {
        //判断订单是否被用
    int count=setmealDao.findCountBySetmealId(id);
    if (count>0){
        throw  new HealthException(MessageConstant.DELETE_SETMEAL_FAIL);
    }
        //没有被使用删除
        // 递归删除联系表
        setmealDao.deleteSetmealCheckGroup(id);
        //再删除套餐
        setmealDao.deleteById(id);

    }

    @Override
    /**
     * 查询套餐数据库所有图片
     * @author: lwl
     * @date: 2020/9/22
     * @param :
     * @return: java.util.List<java.lang.String>
     */
    public List<String> findImags() {
        return setmealDao.findImages();
    }

    /**
     * 查询所有的套餐
     * @return
     */
    @Override
    public List<Setmeal> findAll() {
        return setmealDao.findAll();
    }

    /**
     * 移动端查询所有套餐
     * @author: lwl
     * @date: 2020/9/24
     * @param null:
     * @return:
     */
    @Override
    public Setmeal findDetailById(int id) {
        return setmealDao.findDetailById(id);
    }

    @Override
    public Setmeal findDetailById2(int id) {
        return setmealDao.findDetailById2(id);
    }

    @Override
    public Setmeal findDetailById3(int id) {
        Setmeal setmeal = setmealDao.findById(id);
        // 查询套餐下的检查组
        List<CheckGroup> checkGroups = setmealDao.findCheckGroupBySetmealId(id);
        setmeal.setCheckGroups(checkGroups);
        // 查询每个检查组下的检查项
        if(checkGroups!= null && checkGroups.size() > 0){
            for (CheckGroup checkGroup : checkGroups) {
                List<CheckItem> checkItems = setmealDao.findCheckItemsByCheckGroupId(checkGroup.getId());
                checkGroup.setCheckItems(checkItems);
            }
        }
        return setmeal;
    }
}
