package com.itgrail.grail.codegen.core;

import com.google.common.collect.Lists;
import com.itgrail.grail.codegen.service.dto.DomainTypeDTO;

import java.util.List;

public enum DomainTypeEnum {

    DOMAIN_BASIC("基础域", "basic","bp", "基础服务域"),

    DOMAIN_CSC("商品域", "CSC","bp", "商品域"),

    DOMAIN_EVA("评价域", "eva", "bp","评价域"),

    DOMAIN_FMS("交易域", "fms","bp", "交易域"),

    DOMAIN_DISCOUNT("优惠域", "discount", "bp","优惠域"),

    DOMAIN_PAY("支付域", "pay", "bp","支付域"),

    DOMAIN_PAY_123("逆向交易域", "pay123","bp", "逆向交易域"),

    DOMAIN_TRN("订单域", "trn", "bp","订单域"),

    ;


    private String name;

    private String code;

    private String parentCode;

    private String domainDesc;

    DomainTypeEnum(String name, String code, String parentCode,String domainDesc) {
        this.name = name;
        this.code = code;
        this.domainDesc = domainDesc;
        this.parentCode=parentCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDomainDesc() {
        return domainDesc;
    }

    public void setDomainDesc(String domainDesc) {
        this.domainDesc = domainDesc;
    }

    public static List<DomainTypeDTO> queryAllDomain(){
        List list = Lists.newArrayList();
        for (DomainTypeEnum domainTypeEnum : DomainTypeEnum.values()) {
            DomainTypeDTO appTypeDTO=new DomainTypeDTO();
            appTypeDTO.setName(domainTypeEnum.name);
            appTypeDTO.setCode(domainTypeEnum.code);
            appTypeDTO.setDomainDesc(domainTypeEnum.domainDesc);
            appTypeDTO.setParentCode(domainTypeEnum.parentCode);
            list.add(appTypeDTO);
        }
        return list;

    }

}
