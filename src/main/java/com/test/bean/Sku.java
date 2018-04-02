package com.test.bean;

import java.math.BigDecimal;
import java.util.Date;

public class Sku {
    private Long id;

    private Long spu_id;

    private String small_unit_code;

    private String big_unit_code;

    private Integer unit_transfer;

    private String sku_no;

    private Integer stock;

    private Integer sale_count;

    private Byte isdel;

    private Date updatetime;

    private BigDecimal big_retailprice;

    private BigDecimal retailprice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSpu_id() {
        return spu_id;
    }

    public void setSpu_id(Long spu_id) {
        this.spu_id = spu_id;
    }

    public String getSmall_unit_code() {
        return small_unit_code;
    }

    public void setSmall_unit_code(String small_unit_code) {
        this.small_unit_code = small_unit_code;
    }

    public String getBig_unit_code() {
        return big_unit_code;
    }

    public void setBig_unit_code(String big_unit_code) {
        this.big_unit_code = big_unit_code;
    }

    public Integer getUnit_transfer() {
        return unit_transfer;
    }

    public void setUnit_transfer(Integer unit_transfer) {
        this.unit_transfer = unit_transfer;
    }

    public String getSku_no() {
        return sku_no;
    }

    public void setSku_no(String sku_no) {
        this.sku_no = sku_no;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getSale_count() {
        return sale_count;
    }

    public void setSale_count(Integer sale_count) {
        this.sale_count = sale_count;
    }

    public Byte getIsdel() {
        return isdel;
    }

    public void setIsdel(Byte isdel) {
        this.isdel = isdel;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public BigDecimal getBig_retailprice() {
        return big_retailprice;
    }

    public void setBig_retailprice(BigDecimal big_retailprice) {
        this.big_retailprice = big_retailprice;
    }

    public BigDecimal getRetailprice() {
        return retailprice;
    }

    public void setRetailprice(BigDecimal retailprice) {
        this.retailprice = retailprice;
    }
}