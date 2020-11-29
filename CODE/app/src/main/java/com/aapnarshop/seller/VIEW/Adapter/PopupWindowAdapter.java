package com.aapnarshop.seller.VIEW.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.aapnarshop.seller.Model.GET.Division;
import com.aapnarshop.seller.R;
import java.util.List;

public abstract class PopupWindowAdapter<T> extends BaseAdapter {
    private List<T> items;

    public PopupWindowAdapter(List<T> items) {
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public T getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      return getCustomView(position,convertView,parent);
    }
    public abstract View getCustomView(int position, View view, ViewGroup parent);

}
