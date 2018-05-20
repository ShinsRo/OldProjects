package com.midas2018.root._demo.domain;


public class DemoUserVO {
    private String id;
    private String password;
    private String data;

    public DemoUserVO(String id, String password, String data) {
        this.id = id;
        this.password = password;
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "{" +
                "id:'" + id + '\'' +
                ", password:'" + password + '\'' +
                ", data:'" + data + '\'' +
                '}';
    }
}


