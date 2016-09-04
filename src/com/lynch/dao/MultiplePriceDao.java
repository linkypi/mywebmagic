package com.lynch.dao;

import com.lynch.mapper.MultiplePriceMapper;
import com.lynch.model.MultiplePrice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;


/**
 * @author: JL
 * @version: v1.0
 * @date: 2016/9/4/0004 11:25
 */
@Component
public class MultiplePriceDao implements IAction<MultiplePrice>,Serializable {

    @Autowired
    private MultiplePriceMapper mapper;

    @Override
    public boolean insert(MultiplePrice model) throws Exception {
        int count = mapper.insertUser(model);
        return count>0;
    }

    @Override
    public boolean deleteById(int id) {
        return false;
    }

    @Override
    public boolean update(MultiplePrice person) {
        return false;
    }

    @Override
    public MultiplePrice queryById(int id) {
        return null;
    }

    @Override
    public List<MultiplePrice> queryAllPerson() {
        return null;
    }
}
