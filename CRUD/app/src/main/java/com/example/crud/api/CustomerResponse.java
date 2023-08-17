package com.example.crud.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CustomerResponse {

    @SerializedName("items")
    private List<Customer> items;

    public List<Customer> getItems() {
        return items;
    }

    public void setItems(List<Customer> items) {
        this.items = items;
    }

    public int getSize() {
        int size = 0;
        if (items != null)
            size = items.size();
        return size;
    }

}
