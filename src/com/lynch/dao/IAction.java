package com.lynch.dao;

import java.util.List;

/**
 * @author: JL
 * @version: v1.0
 * @date: 2016/9/4/0004 11:23
 */
public interface IAction<T> {
    public boolean insert(T person) throws Exception;   //添加
    public boolean deleteById(int id);  //删除
    public boolean update(T person);    //修改
    public T queryById(int id);         //根据ID查询
    public List<T> queryAllPerson();    //查询全部
}
