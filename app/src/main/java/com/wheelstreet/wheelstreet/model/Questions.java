package com.wheelstreet.wheelstreet.model;

import java.util.List;

public class Questions {

    private String status;

    private List<Data> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ClassPojo [status = " + status + ", data = " + data + "]";
    }
}
