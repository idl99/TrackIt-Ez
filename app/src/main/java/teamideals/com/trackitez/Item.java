package teamideals.com.trackitez;

public class Item {

    private String itemName;
    private String itemExpiry;

    public Item(String itemName, String itemExpiry){
        this.itemName = itemName;
        this.itemExpiry = itemExpiry;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemExpiry() {
        return itemExpiry;
    }

    @Override
    public String toString() {
        return itemName;
    }
}
