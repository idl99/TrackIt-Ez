package teamideals.com.trackitez.Entities;

public class Item {

    private int itemId;
    private String itemName;

    public Item(){

    }

    public Item(int itemId, String itemName){
        this.itemId = itemId;
        this.itemName = itemName;
    }

    public int getItemId() {
        return itemId;
    }

    public String getItemName() {
        return itemName;
    }

    @Override
    public boolean equals(Object obj) {
        if(super.equals(obj)){
            return true;
        }

        Item objItem = (Item) obj;
        if(objItem.getItemId()==this.itemId){
            return true;
        }

        return false;

    }

}
