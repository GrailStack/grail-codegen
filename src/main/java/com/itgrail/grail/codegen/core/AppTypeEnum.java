package com.itgrail.grail.codegen.core;

import com.google.common.collect.Lists;
import com.itgrail.grail.codegen.service.dto.AppTypeDTO;

import java.util.List;

public enum AppTypeEnum {

    APP_TYPE_ZT("Grail DDD应用", "GrailFramework", "中台应用是基于Grail  Framework构建的中台应用，内置领域驱动(DDD),CQRS，应用内部流程编排，扩展点等常用功能。"),

    APP_TYPE_SINGLE("传统三层应用", "grailsingle", "传统应用基于Grail  Framework构建，用于创建工具型应用，查询服务，缓存服务，定时服务等，内置了Grail  Web，Grail  Mybatis，Grail  Swagger等功能。"),

    APP_TYPE_DDD("Grail DDD简化应用", "grailDdd", "简化版的ddd应用");


    private String appTypeName;

    private String appTypeValue;

    private String desc;

    AppTypeEnum(String appTypeName, String appTypeValue, String desc) {
        this.appTypeName = appTypeName;
        this.appTypeValue = appTypeValue;
        this.desc = desc;
    }

    public String getAppTypeName() {
        return appTypeName;
    }

    public void setAppTypeName(String appTypeName) {
        this.appTypeName = appTypeName;
    }

    public String getAppTypeValue() {
        return appTypeValue;
    }

    public void setAppTypeValue(String appTypeValue) {
        this.appTypeValue = appTypeValue;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static List<AppTypeDTO> getAppType(){
        List list = Lists.newArrayList();
        for (AppTypeEnum appTypeEnum : AppTypeEnum.values()) {
            AppTypeDTO appTypeDTO=new AppTypeDTO();
            appTypeDTO.setAppTypeName(appTypeEnum.appTypeName);
            appTypeDTO.setAppTypeValue(appTypeEnum.appTypeValue);
            appTypeDTO.setDesc(appTypeEnum.desc);
            list.add(appTypeDTO);
        }
        return list;

    }

}
