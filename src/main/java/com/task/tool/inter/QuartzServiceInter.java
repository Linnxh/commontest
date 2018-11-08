package com.task.tool.inter;

import com.common.JsonResult;
import com.task.tool.bean.QuartzServiceModel;
import com.test.bean.Sku;

import javax.ws.rs.POST;
import java.util.List;

public interface QuartzServiceInter {

    List<QuartzServiceModel> queryStartList();

    JsonResult insert(QuartzServiceModel record);

    int update(QuartzServiceModel record);

    int delete(long id);

//    int updateStatus(QuartzUpdateReq req);

    List<QuartzServiceModel> check(QuartzServiceModel record);


}
