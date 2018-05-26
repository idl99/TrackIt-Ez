package teamideals.com.trackitez.entities;

import java.time.LocalDate;

public class Unit implements DatastoreEntity {

    // Required Parameters
    private Item item;
    private int unitId;
    private UnitStatus status;

    // Optional Parameters
    private int nfcTagSerial;
    private LocalDate expiryDate;

    public Unit(Item item, int unitId, UnitStatus status) {
        this.item = item;
        this.unitId = unitId;
        this.status = status;
    }

    public Unit(Item item, int unitId, UnitStatus status, int nfcTagSerial, LocalDate expiryDate) {
        this.item = item;
        this.unitId = unitId;
        this.status = status;
        this.nfcTagSerial = nfcTagSerial;
        this.expiryDate = expiryDate;
    }

    public Item getItem() {
        return item;
    }

    public int getUnitId() {
        return unitId;
    }

    public UnitStatus getStatus() {
        return status;
    }

    public int getNfcTagSerial() {
        return nfcTagSerial;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

}
