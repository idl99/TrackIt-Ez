package com.teamideals.trackitez.entities;

import java.util.List;

public class Item implements DatastoreEntity {

    private String itemName;
    private List<ItemCategory> categories;

    public Item() {

    }

    public Item(String itemName, List<ItemCategory> categories) {
        this.itemName = itemName;
        this.categories = categories;
    }

    public String getItemName() {
        return itemName;
    }

    public List<ItemCategory> getCategories() {
        return categories;
    }

    @Override
    public boolean equals(Object obj) {

        if (super.equals(obj)) {
            return true;

        }

        Item objItem = (Item) obj;

        if (objItem.getItemName().equals(this.itemName)) {
            return true;
        }

        return false;

    }

    @Override
    public String toString() {
        return itemName;
    }


}
