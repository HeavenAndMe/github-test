package com.funtl.my.shop.commons.dto;

import com.funtl.my.shop.commons.persisten.BaseEntity;

import java.io.Serializable;
import java.util.List;

/**
 * 分页数据传输对象
 * @author Zhan
 * @create 2019/8/22 - 8:06
 */
public class PageInfo<T extends BaseEntity> implements Serializable {
    private int  draw;
    private int  recordsTotal;
    private int  recordsFiltered;
    private List<T> data;
    private String error;

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public int getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(int recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public int getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(int recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
