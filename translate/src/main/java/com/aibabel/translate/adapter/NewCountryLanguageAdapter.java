package com.aibabel.translate.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aibabel.translate.R;
import com.aibabel.translate.bean.LanguageBean;
import com.aibabel.translate.offline.ChangeOffline;
import com.aibabel.translate.utils.SharePrefUtil;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Wuqinghua on 2018/4/24 0024.
 */

public class NewCountryLanguageAdapter extends BaseExpandableListAdapter {


    private List<LanguageBean> group;
    private Context context;
    private int idi;
    //由上层传入是否处于离线
    private boolean isNetwork = true;

    public NewCountryLanguageAdapter(List<LanguageBean> group, Context context, boolean isNet) {

        this.group = group;
        this.context = context;
        isNetwork = isNet;
    }

    @Override
    public int getGroupCount() {
        return group.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return group.get(i).getChild().size();
    }

    @Override
    public Object getGroup(int i) {
        return null;
    }

    @Override
    public Object getChild(int i, int i1) {
        return null;
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        ViewGroupHolder holder = null;
        if (view == null) {
            holder = new ViewGroupHolder();
            view = LayoutInflater.from(context).inflate(R.layout.item_group, null);
            holder.img_elv = (ImageView) view.findViewById(R.id.img_elv);
            holder.imgHorn = (ImageView) view.findViewById(R.id.img_horn);
            holder.language = (TextView) view.findViewById(R.id.language);
            holder.languageClassification = (TextView) view.findViewById(R.id.language_classification);
            holder.Choice = (ImageView) view.findViewById(R.id.Choice);
            view.setTag(holder);
        } else {
            holder = (ViewGroupHolder) view.getTag();
        }


        if (group.get(i).getSound() == 1) {
            holder.imgHorn.setVisibility(View.GONE);
        } else {
            holder.imgHorn.setVisibility(View.VISIBLE);
        }

        if (group.get(i).getChild().size() > 0) {
            holder.img_elv.setVisibility(View.VISIBLE);
        } else {
            holder.img_elv.setVisibility(View.GONE);
        }

        idi = SharePrefUtil.getInt(context, "idi", -1);

        if (group.get(i).getChild().size() > 0) {
            if (!b) {
                holder.img_elv.setImageResource(R.mipmap.right);
            } else {
                holder.img_elv.setImageResource(R.mipmap.buttom);
            }
        } else {
            holder.img_elv.setImageResource(0);
        }
        holder.Choice.setImageResource(0);
        if (group.get(i).getChild().size() == 0) {
            holder.Choice.setVisibility(View.VISIBLE);
            if (idi == group.get(i).getId()) {
                holder.Choice.setImageResource(R.mipmap.select);
            }
        } else if (!isNetwork && ChangeOffline.getInstance().offlineListMap.containsKey(group.get(i).getLang_code())) {
            holder.Choice.setVisibility(View.VISIBLE);
            if (idi == group.get(i).getId()) {
                holder.Choice.setImageResource(R.mipmap.select);
            }
        } else {
            holder.Choice.setImageResource(0);
            holder.Choice.setVisibility(View.GONE);
        }


        if (isNetwork) {
//            holder.language.setText(group.get(i).getName());
//            holder.languageClassification.setText(group.get(i).getName_local());
            //是否支持粤语到改语言的翻译
            if (!group.get(i).isNotSupport()) {
                holder.language.setText(group.get(i).getName());
                holder.language.setTextColor(context.getResources().getColor(R.color.fe5000));
                holder.languageClassification.setText(group.get(i).getName_local());
                holder.languageClassification.setTextColor(context.getResources().getColor(R.color.offline_list_999));
            } else {
                holder.language.setText(group.get(i).getName());
                holder.language.setTextColor(context.getResources().getColor(R.color.offline_list_name));
                holder.languageClassification.setText(group.get(i).getName_local());
                holder.languageClassification.setTextColor(context.getResources().getColor(R.color.offline_list_name_child));

            }


        } else {
            //不支持的离线的语言
            if (ChangeOffline.getInstance().offlineListMap.containsKey(group.get(i).getLang_code())) {
                holder.language.setText(group.get(i).getName());
                holder.language.setTextColor(context.getResources().getColor(R.color.fe5000));
                holder.languageClassification.setText(group.get(i).getName_local());
                holder.languageClassification.setTextColor(context.getResources().getColor(R.color.offline_list_999));
                holder.img_elv.setVisibility(View.GONE);
            } else {
                holder.language.setText(group.get(i).getName());
                holder.language.setTextColor(context.getResources().getColor(R.color.offline_list_name));
                holder.languageClassification.setText(group.get(i).getName_local());
                holder.languageClassification.setTextColor(context.getResources().getColor(R.color.offline_list_name_child));
                holder.img_elv.setVisibility(View.GONE);

            }

        }


        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        ViewChildHolder holder = null;
        if (view == null) {
            holder = new ViewChildHolder();
            view = LayoutInflater.from(context).inflate(R.layout.item_child, null);

            view.setTag(holder);
        } else {
            holder = (ViewChildHolder) view.getTag();
        }


        holder.Choice_child = (ImageView) view.findViewById(R.id.Choice_child);
        holder.language = (TextView) view.findViewById(R.id.language);
        holder.languageClassification = (TextView) view.findViewById(R.id.language_classification);
        holder.Choice_child.setImageResource(0);
        if (idi == group.get(i).getId()) {
//            int idi_var = SharePrefUtil.getInt(context, "idi_var", 0);
            String var_name = SharePrefUtil.getString(context, "var_name", "");
//            if (var_name==(group.get(i).getChild().get(i1).getVar_id())){
//                holder.Choice_child.setImageResource(R.mipmap.select);
//            }


            if (var_name.equals(group.get(i).getChild().get(i1).getVar())) {

                holder.Choice_child.setImageResource(R.mipmap.select);
            }
        }


        //是否支持粤语到改语言的翻译
        if (!group.get(i).getChild().get(i1).isNotSupport()) {
            holder.language.setText(group.get(i).getChild().get(i1).getVar());
            holder.language.setTextColor(context.getResources().getColor(R.color.fe5000));
            holder.languageClassification.setText(group.get(i).getChild().get(i1).getVar_local());
            holder.languageClassification.setTextColor(context.getResources().getColor(R.color.offline_list_999));
        } else {
            holder.language.setText(group.get(i).getChild().get(i1).getVar());
            holder.languageClassification.setText(group.get(i).getChild().get(i1).getVar_local());
            holder.language.setTextColor(context.getResources().getColor(R.color.offline_list_name));
            holder.languageClassification.setTextColor(context.getResources().getColor(R.color.offline_list_name_child));
        }


        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    class ViewGroupHolder {
        @BindView(R.id.img_elv)
        ImageView img_elv;
        @BindView(R.id.img_horn)
        ImageView imgHorn;
        @BindView(R.id.language)
        TextView language;
        @BindView(R.id.language_classification)
        TextView languageClassification;
        @BindView(R.id.Choice)
        ImageView Choice;
        @BindView(R.id.lv_lan_item_view)
        View lvLanItemView;
    }

    class ViewChildHolder {
        @BindView(R.id.language)
        TextView language;
        @BindView(R.id.language_classification)
        TextView languageClassification;
        @BindView(R.id.Choice_child)
        ImageView Choice_child;
        @BindView(R.id.lv_lan_item_view)
        View lvLanItemView;
    }


}
