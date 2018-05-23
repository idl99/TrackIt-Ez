package teamideals.com.trackitez.entities;

public class Item {

    private String barcode;
    private String itemName;

    public Item(){

    }

    public Item(String barcode, String itemName){
        this.barcode = barcode;
        this.itemName = itemName;
    }

    public String getBarcode() {
        return barcode;
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
        if(objItem.getBarcode().equals(this.barcode)){
            return true;
        }

        return false;

    }

}
