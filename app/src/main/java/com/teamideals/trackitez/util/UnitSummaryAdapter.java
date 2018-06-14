package com.teamideals.trackitez.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.teamideals.trackitez.R;
import com.teamideals.trackitez.viewmodels.UnitSummary;

import java.util.List;

public class UnitSummaryAdapter extends ArrayAdapter<UnitSummary.ItemUnit> {

    private List<UnitSummary.ItemUnit> itemUnitList;

    public UnitSummaryAdapter(Context context, int resource,
                              int textViewResourceId, List<UnitSummary.ItemUnit> itemUnitList){
        super(context, resource, textViewResourceId, itemUnitList);
        this.itemUnitList = itemUnitList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = super.getView(position, convertView, parent);

        UnitSummary.ItemUnit itemUnit = itemUnitList.get(position);
        String itemName = itemUnit.getItemName();
        long quantity = itemUnit.getQuantity();
        long daysToExpiry = itemUnit.getExpiryinDays();

        TextView tvItemName = view.findViewById(R.id.itemName);
        tvItemName.setText(itemName);

        TextView tvQuantity = view.findViewById(R.id.Quantity);
        tvQuantity.setText(
                String.format((getContext().getResources().getString(R.string.no_of_units)),
                        quantity
                ));

        TextView tvDaysToExpiry = view.findViewById(R.id.noOfDays);
        String expiryTag = expiryTag(daysToExpiry);
        tvDaysToExpiry.setText(expiryTag);

        ImageView imageView = view.findViewById(R.id.circleExpiryIndicator);
        setCeiLevel(imageView, daysToExpiry);

        return view;
    }

    private String expiryTag(long expiryInDays) {
        if (expiryInDays > 90) {
            return (expiryInDays / 30) + "m";
        } else if (expiryInDays > 14) {
            return (expiryInDays / 7) + "w";
        } else
            return expiryInDays + "d";
    }

    private void setCeiLevel(ImageView imageView, long daysToExpiry) {

        if (daysToExpiry <= 3) {
            imageView.setImageDrawable(getContext().getResources()
                    .getDrawable(R.drawable.danger_expiry_indicator));
        } else if (daysToExpiry <= 7) {
            imageView.setImageDrawable(getContext().getResources()
                    .getDrawable(R.drawable.warning_expiry_indicator));
        } else if (daysToExpiry >= 14) {
            imageView.setImageDrawable(getContext().getResources()
                    .getDrawable(R.drawable.expiry_indicator));
        }

    }


}
