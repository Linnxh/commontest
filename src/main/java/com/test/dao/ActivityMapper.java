package com.test.dao;

import com.task.business.Activity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ActivityDAO继承基类
 */
@Repository
public interface ActivityMapper  {

    List<Activity> selectAll();

}