package com.task.business;


import com.test.dao.ActivityMapper;
import com.test.dao.SkuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class ActivityTaskService implements IJobDo {

    @Autowired
    ActivityMapper activityMapper;

    @Override
    public void run() throws Exception {
        //活动开始
        List<Activity> activities = activityMapper.selectAll();

        //这个查出的活动，然后根据具体的业务逻辑规则，选出那个是要开始的活动，停止的也是同理，此处不讨论
        System.out.println("ActivityTaskService，这个查出的活动，然后根据具体的业务逻辑规则，选出那个是要开始或者停止的活动=======>>>>>>"+new Date());
    }
}

