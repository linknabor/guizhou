package com.yumu.hexie.backend.web.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

/**
 * Created by Administrator on 2015/4/10.
 */
public class PageResult<T> implements Serializable{
    private static final long serialVersionUID = 2812272301142308680L;
    private long total;
    private List<T> rows = new ArrayList<T>();
    public PageResult(Page<T> data){
        if(data != null) {
            this.total = data.getTotalElements();
            this.rows = data.getContent();
        }
    }
    public PageResult(){}

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
