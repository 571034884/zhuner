package com.aibabel.ocr.adapter;

import android.util.SparseArray;
import android.view.View;

/**
 * 高效ViewHolder
 * @author Administrator
 *
 */
public class ViewHolder {


    // I added a generic return type to reduce the casting noise in client code
    @SuppressWarnings("unchecked")
    public static  <B extends View> B get(View view, int id) {
        SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
        if (viewHolder == null) {
            viewHolder = new SparseArray<View>();
            view.setTag(viewHolder);
        }
        View childView = viewHolder.get(id);
        if (childView == null) {
            childView = view.findViewById(id);
            viewHolder.put(id, childView);
        }
        return (B) childView;
    }
}