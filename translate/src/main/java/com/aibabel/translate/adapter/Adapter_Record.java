package com.aibabel.translate.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aibabel.translate.R;
import com.aibabel.translate.bean.RecordBean;
import com.aibabel.translate.utils.LanguageUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 作者：wuqinghua_fyt on 2018/10/18 15:14
 * 功能：
 * 版本：1.0
 */
public class Adapter_Record extends RecyclerView.Adapter<Adapter_Record.MainViewHolder> {

    public static final int FIRST_STICKY_VIEW = 1;
    public static final int HAS_STICKY_VIEW = 2;
    public static final int NONE_STICKY_VIEW = 3;

    List<RecordBean> list;
    Context context;

    /**
     * 控制是否显示Checkbox
     */
    private boolean showCheckBox;

    /**
     * 是否全选
     */
    private boolean isCheckAll;

    public boolean isCheckAll() {
        return isCheckAll;
    }

    public void setCheckAll(boolean checkAll) {
        isCheckAll = checkAll;
        checkAll();
    }

    public boolean isShowCheckBox() {
        return showCheckBox;
    }

    public void setShowCheckBox(boolean showCheckBox) {
        this.showCheckBox = showCheckBox;
    }

    /**
     * 防止Checkbox错乱 做setTag  getTag操作
     */
    private SparseBooleanArray mCheckStates = new SparseBooleanArray();

    //接口实例
    private RecyclerViewOnItemClickListener onItemClickListener;

    public Adapter_Record(Context context, List<RecordBean> strings) {
        this.context = context;
        this.list = strings;
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MainViewHolder(LayoutInflater.from(context).inflate(R.layout.item_record, parent, false));
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    public class MainViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout llRoot;

        TextView header_view;
        private TextView tv_asr;
        private TextView tv_form;
        private TextView tv_to;
        private TextView tv_eng;
        private TextView tv_mt;
        private View view;
        private CheckBox cb_check;

        public MainViewHolder(View itemView) {
            super(itemView);
            llRoot = itemView.findViewById(R.id.llRoot);
            header_view = itemView.findViewById(R.id.tv_time);
            cb_check = itemView.findViewById(R.id.cb_check);
            tv_asr = itemView.findViewById(R.id.tv_asr);
            tv_form = itemView.findViewById(R.id.tv_form);
            tv_to = itemView.findViewById(R.id.tv_to);
            tv_eng = itemView.findViewById(R.id.tv_eng);
            tv_mt = itemView.findViewById(R.id.tv_mt);
            view = itemView.findViewById(R.id.view);
        }
    }

    @Override
    public void onBindViewHolder(final MainViewHolder holder, final int position) {

        final RecordBean bean = list.get(position);
        holder.header_view.setText(initDate(bean.getTime()));
        holder.itemView.setContentDescription(initDate(bean.getTime()));

        holder.cb_check.setTag(position);
        //判断当前checkbox的状态
        if (showCheckBox) {
            holder.cb_check.setVisibility(View.VISIBLE);
            //防止显示错乱
            holder.cb_check.setChecked(mCheckStates.get(position, false));
        } else {
            holder.cb_check.setVisibility(View.GONE);
            //取消掉Checkbox后不再保存当前选择的状态
            holder.cb_check.setChecked(false);
            mCheckStates.clear();
        }
        //点击监听
        holder.llRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (showCheckBox) {
                    holder.cb_check.setChecked(!holder.cb_check.isChecked());
                }
                onItemClickListener.onItemClickListener(view, position);
            }
        });
        //长按监听
        holder.llRoot.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (!showCheckBox) {
                    holder.cb_check.setChecked(!holder.cb_check.isChecked());
                }
                return onItemClickListener.onItemLongClickListener(view, position);
            }
        });
        holder.cb_check.setClickable(false);
        //对checkbox的监听 保存选择状态 防止checkbox显示错乱
        holder.cb_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                int pos = (int) compoundButton.getTag();
                if (b) {
                    mCheckStates.put(pos, true);
                } else {
                    mCheckStates.delete(pos);
                }

            }
        });
        //设置Tag
        holder.view.setTag(position);

        holder.tv_asr.setText(bean.getAsr());
        holder.tv_mt.setText(bean.getMt());
        String from = LanguageUtils.getNameByCode(bean.getFrom_code(), context);
        String to = LanguageUtils.getNameByCode(bean.getTo_code(), context);
        holder.tv_form.setText(from + "");
        holder.tv_to.setText(to + "");

        if (!(bean.getTo_code().contains("en")) && !(bean.getFrom_code().contains("en"))) {
            holder.tv_eng.setVisibility(View.VISIBLE);
            holder.tv_eng.setText(bean.getEng());
            if (TextUtils.isEmpty(bean.getEng())) {
                holder.tv_eng.setVisibility(View.GONE);
            }
        } else {
            holder.tv_eng.setVisibility(View.GONE);
        }


        if (position == list.size() - 1) {
            holder.view.setVisibility(View.GONE);
        } else {
            holder.view.setVisibility(View.VISIBLE);
            if (!TextUtils.equals(initDate(bean.getTime()), initDate(list.get(position + 1).getTime()))) {
                holder.view.setVisibility(View.GONE);
            } else {
                holder.view.setVisibility(View.VISIBLE);
            }
        }

        if (position == 0) {
            holder.header_view.setVisibility(View.VISIBLE);
            holder.itemView.setTag(FIRST_STICKY_VIEW);
        } else {
            if (!initDate(bean.getTime()).equals(initDate(list.get(position - 1).getTime()))) {
                holder.header_view.setVisibility(View.VISIBLE);
                holder.itemView.setTag(HAS_STICKY_VIEW);
            } else {
                holder.header_view.setVisibility(View.GONE);
                holder.itemView.setTag(NONE_STICKY_VIEW);
            }
        }
    }

    private String initDate(long time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(time);
        String dateStr = simpleDateFormat.format(date);
        return dateStr;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

//    //点击事件
//    @Override
//    public void onClick(View v) {
//        if (onItemClickListener != null) {
//            //注意这里使用getTag方法获取数据
//            onItemClickListener.onItemClickListener(v, (Integer) v.getTag());
//        }
//    }

    //    //长按事件
//    @Override
//    public boolean onLongClick(View v) {
//        //不管显示隐藏，清空状态
//        return onItemClickListener != null && onItemClickListener.onItemLongClickListener(v, (Integer) v.getTag());
//    }
//
//
    //设置点击事件
    public void setRecyclerViewOnItemClickListener(RecyclerViewOnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    //设置是否显示CheckBox
    public void setShowBox() {
        //取反
        showCheckBox = !showCheckBox;
    }

    private void checkAll() {
        mCheckStates.clear();
        for (int i = 0; i < list.size(); i++) {
            mCheckStates.put(i, isCheckAll);
        }
    }

    //接口回调设置点击事件
    public interface RecyclerViewOnItemClickListener {
        //点击事件
        void onItemClickListener(View view, int position);

        //长按事件
        boolean onItemLongClickListener(View view, int position);
    }


    /**
     * 当ListView数据发生变化时,调用此方法来更新ListView
     *
     * @param list
     */
    public void updateData(List<RecordBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

}
