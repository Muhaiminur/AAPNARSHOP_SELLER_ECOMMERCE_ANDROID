package com.aapnarshop.seller.VIEW.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public abstract class GenericAdapter<T> extends ArrayAdapter<T>{

    private List<T> list;
    private Context context;

    public GenericAdapter(Context context, List<T> list) {
        super(context, 0, list);
        this.list = list;
        this.context= context;
    }
    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView,parent);
    }
    public static class ViewHolder {

        public TextView title;
    }

    @Nullable
    @Override
    public T getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position,convertView,parent);
    }

    public abstract View getCustomView(int position, View view, ViewGroup parent);

}
