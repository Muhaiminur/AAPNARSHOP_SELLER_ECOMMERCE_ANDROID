package com.aapnarshop.seller.VIEW.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.aapnarshop.seller.Model.ReportDropdown;
import com.aapnarshop.seller.R;

import java.util.List;

public class ReportSpinnerAdapter extends ArrayAdapter<ReportDropdown>{

    private List<ReportDropdown> myarrayList;
    private Context context;


    public ReportSpinnerAdapter(Context context, int textViewResourceId, List<ReportDropdown> modelArrayList) {
        super(context, textViewResourceId, modelArrayList);
        this.myarrayList = modelArrayList;
        this.context = context;

    }
    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, parent);
    }

    @Nullable
    @Override
    public ReportDropdown getItem(int position) {
        return myarrayList.get(position);
    }

    @Override
    public int getCount() {
        return myarrayList.size();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, parent);
    }

    private View getCustomView(int position, ViewGroup parent) {
        ReportDropdown model = getItem(position);


        View spinnerRow = LayoutInflater.from(parent.getContext()).inflate(com.aapnarshop.seller.R.layout.custom_spinner_item, parent, false);

        TextView label = spinnerRow.findViewById(com.aapnarshop.seller.R.id.custom_spinner);
        label.setText(model.getTitle());


        return spinnerRow;
    }


}
