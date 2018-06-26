package com.teamideals.trackitez.listeners;

import com.teamideals.trackitez.entities.NfcTag;

public interface OnTagScanListener {

    void newTagScanned(NfcTag nfcTag);

    void inUseTagScanned(NfcTag nfcTag);

    void notInUseTagScanned(NfcTag nfcTag);

}
