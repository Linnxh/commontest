package com.test.dao;

import com.task.tool.bean.QuartzServiceModel;
import com.test.bean.Sku;

import java.util.List;

public interface QuartzServiceMapper {

    int deleteByPrimaryKey(Long id);

    int insert(QuartzServiceModel record);

    QuartzServiceModel selectByPrimaryKey(Long id);

    List<QuartzServiceModel> queryStartList();

}
