package com.heima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.StringUtil;
import com.heima.dao.CheckItemDao;
import com.heima.health.HealthException;
import com.heima.health.constant.MessageConstant;
import com.heima.health.entity.PageResult;
import com.heima.health.entity.QueryPageBean;
import com.heima.pojo.CheckItem;
import com.heima.service.CheckItemService;
import com.sun.xml.internal.ws.handler.HandlerException;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

/**
 * Description: 发布 创建节点数据 /dubbo/接口包名/providers/dubbo://ip:port 接口类 方法
 * client.create().creatingParentIfNeeded().for("dubbo/接口包名/providers/","数据");
 *
 * ServerSocket(20880);
 * socket = serverSocket.accept();
 * socket.getInputSTream() =?> findAll
 * List findAll();
 * socket.getOutputStream().write(List) 响应给调用者
 * User: Eric
 */
@Service(interfaceClass = CheckItemService.class)
public class CheckItemServiceImpl implements CheckItemService {

    @Autowired
    private CheckItemDao checkItemDao;
    /**
     * 查询所有
     * @params
     * @return java.util.List<com.heima.pojo.CheckItem>
     */
    @Override
    public List<CheckItem> findAll() {
        return checkItemDao.findAll();
    }
    /**
     * 添加检查项
     * @params
     * @return void
     */
    @Override
    public void add(CheckItem checkItem) {
         checkItemDao.add(checkItem);

    }
    /**
     * 分页查询
     * @params
     * @return com.heima.health.entity.PageResult<com.heima.pojo.CheckItem>
     */
    @Override
    public PageResult<CheckItem> findPage(QueryPageBean queryPageBean) {
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
        Page<CheckItem> page= checkItemDao.findByCondition(queryPageBean.getQueryString());
        //封装到分页结果对象中
        PageResult<CheckItem> pageResult = new PageResult<CheckItem>(page.getTotal(), page.getResult());

        return pageResult;
    }

    @Override
    public CheckItem findById(int id) {
        return checkItemDao.findById(id);
    }

    /**
     * 删除检查项
     * @params
     * @return void
     */
    @Override
    public void deleteById(int id) throws HealthException {
        //先检查该检查项是否有多表关联项
        //调用到查询检查项的id是否存在
      int count=  checkItemDao.findCountByCheckItemId(id);
      //被使用就不能删除
        if (count>0){
            //抛出自定义异常
            throw  new HealthException(MessageConstant.CHECKITEM_IN_USE);
        }
        //没有被用就删除
        checkItemDao.deleteById(id);

    }
    /**
     *修改编辑检查项
     * @params
     * @return void
     */
    @Override
    public void update(CheckItem checkItem) {
        checkItemDao.update(checkItem);
    }

    public static void main(String[] args) throws UnknownHostException {
        System.out.println(InetAddress.getLocalHost());
    }
}
