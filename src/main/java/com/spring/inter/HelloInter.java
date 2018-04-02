package com.spring.inter;

import com.test.bean.Sku;
import com.wordnik.swagger.annotations.Api;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.wordnik.swagger.annotations.ApiOperation;

//@Path("/activityservice")
//@Consumes("application/json")
//// 当前方法接收的参数类型
//@Produces("application/json; charset=utf-8")
//// 当前类的所有方法都返回json格式的数据
//@Api("activityservice(活动相关接口)")
////@Path("/v1/users")
//

public interface HelloInter {

    @POST
   public Sku getSkuInfo();
    @POST
//    @Path("/activityoperation")
//    @ApiOperation(value = "活动操作", httpMethod = "POST", notes = "liupeng")
   public Sku getSkuInfo2();
}
