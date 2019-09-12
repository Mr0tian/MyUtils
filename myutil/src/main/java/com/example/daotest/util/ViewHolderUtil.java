package com.example.daotest.util;

import android.util.SparseArray;
import android.view.View;

/**
 * @author tian on 2019/9/11
 * viewholder工具 listView 中使用
 */
public class ViewHolderUtil {
    @SuppressWarnings("unchecked")
    public static <T extends View> T get(View view, int id) {
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
        return (T) childView;
    }

    /*
     * 在getview里面的用法 示例
     *
     * @Override public View getView(int position, View convertView, ViewGroup
     * parent) {
     *
     * if (convertView == null) { convertView = LayoutInflater.from(context)
     * .inflate(R.layout.banana_phone, parent, false); }
     *
     * ImageView bananaView = ViewHolderUtil.get(convertView, R.id.banana);
     * TextView phoneView = ViewHolderUtil.get(convertView, R.id.phone);
     *
     * BananaPhone bananaPhone = getItem(position);
     * phoneView.setText(bananaPhone.getPhone());
     * bananaView.setImageResource(bananaPhone.getBanana());
     *
     * return convertView;
     * }
     */

}
