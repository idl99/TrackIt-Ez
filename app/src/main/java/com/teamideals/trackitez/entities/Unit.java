package com.teamideals.trackitez.entities;

import android.support.annotation.NonNull;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Unit implements DatastoreEntity, Comparable<Unit> {

    // Required Parameters
    private Item item;
    private String unitId;
    private UnitStatus status;

    // Optional Parameters
    private int nfcTagSerial;
    private Date expiryDate;

    public Unit() {

    }

    public Unit(Item item, String unitId, UnitStatus status) {
        this.item = item;
        this.unitId = unitId;
        this.status = status;
    }

    public Unit(Item item, String unitId, UnitStatus status, Date expiryDate) {
        this(item, unitId, status);
        this.expiryDate = expiryDate;
    }

    public Unit(Item item, String unitId, UnitStatus status, int nfcTagSerial, Date expiryDate) {
        this(item, unitId, status, expiryDate);
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

    public long getExpiryInDays() {
        return TimeUnit.DAYS.convert(
                (this.expiryDate.getTime() - System.currentTimeMillis()),
                TimeUnit.MILLISECONDS);
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj) ||
                item.getItemName().equals
                        (((Unit) obj).getItem().getItemName());
    }

    @Override
    public int compareTo(@NonNull Unit o) {
        return (int) (this.getExpiryInDays() - o.getExpiryInDays());
    }
}
