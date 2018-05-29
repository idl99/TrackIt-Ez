package teamideals.com.trackitez.entities;

import java.util.Date;

public class Unit implements DatastoreEntity {

    // Required Parameters
    private Item item;
    private String unitId;
    private UnitStatus status;

    // Optional Parameters
    private int nfcTagSerial;
    private Date expiryDate;

    public Unit(Item item, String unitId, UnitStatus status) {
        this.item = item;
        this.unitId = unitId;
        this.status = status;
    }

    public Unit(Item item, String unitId, UnitStatus status, Date expiryDate) {
        this(item,unitId,status);
        this.expiryDate = expiryDate;
    }

    public Unit(Item item, String unitId, UnitStatus status, int nfcTagSerial, Date expiryDate) {
        this(item,unitId,status,expiryDate);
        this.nfcTagSerial = nfcTagSerial;
    }

    public Item getItem() {
        return item;
    }

    public String getUnitId() {
        return unitId;
    }

    public UnitStatus getStatus() {
        return status;
    }

    public int getNfcTagSerial() {
        return nfcTagSerial;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

}
