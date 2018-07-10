package com.teamideals.trackitez.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.teamideals.trackitez.R;
import com.teamideals.trackitez.viewmodels.UnitGroupList;

import java.util.List;

public class UnitGroupAdapter extends ArrayAdapter<UnitGroupList.UnitGroup> {

    private List<UnitGroupList.UnitGroup> unitGroupList;

    public UnitGroupAdapter(Context context, int resource,
                            int textViewResourceId, List<UnitGroupList.UnitGroup> unitGroupList) {
        super(context, resource, textViewResourceId, unitGroupList);
        this.unitGroupList = unitGroupList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = super.getView(position, convertView, parent);

        UnitGroupList.UnitGroup unitGroup = unitGroupList.get(position);
        String itemName = unitGroup.getItemName();
        long daysToExpiry = unitGroup.getNearestExpiry();
        long quantity = unitGroup.getQuantity();

        ImageView imageView = view.findViewById(R.id.circleExpiryIndicator);
        setCeiLevel(imageView, daysToExpiry);

        TextView tvDaysToExpiry = view.findViewById(R.id.noOfDays);
        String expiryTag = expiryTag(daysToExpiry);
        tvDaysToExpiry.setText(expiryTag);

        TextView tvItemName = view.findViewById(R.id.itemName);
        tvItemName.setText(itemName);

        TextView tvQuantity = view.findViewById(R.id.Quantity);
        tvQuantity.setText(
                String.format((getContext().getResources().getString(R.string.no_of_units)),
                        quantity
                ));

        ImageButton ivTrashCan = view.findViewById(R.id.icon_delete_unit);
        // TODO: update cloud db
        ivTrashCan.setOnClickListener(v -> {
            Log.d("LOGS", "Trash can onClick");
            unitGroupList.remove(getItem(position));
            notifyDataSetChanged();
        });

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
