package com.aibabel.speech.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aibabel.speech.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class MyAdapter<T, V> extends BaseAdapter {


    protected Context mContext;
    protected List<String> mDatas;
    protected LayoutInflater mInflater;
//    private int layoutId; //把布局单独提取出来
    private Map<String,Integer> mapLayoutId;
    private Map<String,ViewHolder> mapLayout;


    public MyAdapter(Context context, List<String> datas, Map<String,Integer> mapLayoutId) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        this.mDatas = datas;
        mapLayout=new HashMap<>();
        this.mapLayoutId = mapLayoutId;

    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public String getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //初始化ViewHolder
        ViewHolder holder=null;
        //第一次将所有子布局加载并存起来

            for (String key:mapLayoutId.keySet()) {
                holder = ViewHolder.get(mContext, convertView, parent, mapLayoutId.get(key), position);//layoutId就是单个item的布局
                mapLayout.put(key,holder);
            }



        //判断暴露出去那个子布局
        String json=mDatas.get(position);
        try {
            if (json.indexOf("{") != -1) {
                JSONObject jsonObject = JSON.parseObject(json);
                String code = jsonObject.getString("code");
                if (code.equals("001")) {
                    holder= mapLayout.get("viewspot");
                }else if (code.equals("001_1")) {
                    holder= mapLayout.get("viewspot");

                } else if (code.equals("002")) {
                    holder= mapLayout.get("txt");

                } else if (code.equals("003")) {
                    holder= mapLayout.get("txt");

                } else if (code.equals("003_0")) {
                    holder= mapLayout.get("poi");

                }else if (code.equals("003_1")) {
                    holder= mapLayout.get("poi");

                }else if (code.equals("004")) {
                    holder= mapLayout.get("txt");

                } else if (code.equals("005")) {
                    //百度地图
                    holder= mapLayout.get("map");

                } else if (code.equals("006")) {

                    holder= mapLayout.get("txt");
                } else if (code.equals("007")) {

                    holder= mapLayout.get("train");
                } else if (code.equals("008")) {
                    holder= mapLayout.get("rate");

                } else if (code.equals("009")) {
                    holder= mapLayout.get("train");
                } else if (code.equals("010")) {
                    holder= mapLayout.get("txt");

                } else if (code.equals("011")) {
                    //相声戏曲
                    holder= mapLayout.get("music");
                } else if (code.equals("012")) {

                    holder= mapLayout.get("txt");

                } else if (code.equals("013")) {
                    holder= mapLayout.get("train");

                } else if (code.equals("014")) {
                    holder= mapLayout.get("count");

                } else if (code.equals("015")) {
                    //

                } else if (code.equals("016")) {
                    //音乐
                    holder= mapLayout.get("music");

                } else if (code.equals("222")) {
                    holder= mapLayout.get("txt");

                } else if (code.equals("005_3")) {
                    holder= mapLayout.get("map");

                } else if (code.equals("005_0")) {
                    holder= mapLayout.get("map");

                }  else if (code.equals("005_2")) {
                    holder= mapLayout.get("map");

                }  else if (code.equals("666")) {
                    holder= mapLayout.get("select");

                }

                else if (code.equals("111")) {
                    holder= mapLayout.get("txt");

                } else {
                    holder= mapLayout.get("txt");
                }

            } else {
                holder= mapLayout.get("my");
            }
        } catch (Exception e) {
            holder= mapLayout.get("txt");
        }


        convert(holder, getItem(position));
        return holder.getConvertView();
    }

    //将convert方法公布出去
    public abstract void convert(ViewHolder holder, String t);


    //刷新数据
    public void fresh(List<String> datas) {
        mDatas = datas;
        notifyDataSetChanged();
    }
    //刷新数据
    public void freshOne(String datas) {
        mDatas.clear();
        mDatas.add(datas);
        notifyDataSetChanged();
    }

    //加载数据
    public void load(List<String> datas) {
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }
    public void loadOne(String datas) {
        mDatas.add(datas);
        notifyDataSetChanged();
    }
}
