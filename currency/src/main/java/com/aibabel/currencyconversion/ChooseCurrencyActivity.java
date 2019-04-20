package com.aibabel.currencyconversion;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aibabel.currencyconversion.app.BaseActivity;
import com.aibabel.currencyconversion.app.Constant;
import com.aibabel.currencyconversion.bean.NewCurrencyBean;
import com.aibabel.currencyconversion.custom.MySectionIndexer;
import com.aibabel.currencyconversion.custom.PinnedHeaderListView;
import com.aibabel.currencyconversion.custom.PinyinComparator;
import com.aibabel.currencyconversion.custom.SideBar;
import com.aibabel.currencyconversion.custom.SortAdapter;
import com.aibabel.currencyconversion.utils.CommonUtils;
import com.aibabel.currencyconversion.utils.FastJsonUtil;
import com.aibabel.currencyconversion.utils.NetUtil;
import com.aibabel.currencyconversion.utils.SharePrefUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ChooseCurrencyActivity extends BaseActivity {

    @BindView(R.id.iv_guanbi)
    ImageView ivGuanbi;
    @BindView(R.id.rv_currency)
    PinnedHeaderListView sortListView;
    @BindView(R.id.sb_sort)
    SideBar sbSort;
    @BindView(R.id.tv_dialog)
    TextView tvDialog;


    private List<NewCurrencyBean> sourceDateList;

    private String[] sections;
    private static String ALL_CHARACTER = "";
    private int[] counts;
    private MySectionIndexer mIndexer;

    private SortAdapter adapter;

    @Override
    public int initLayout() {
        return R.layout.activity_choose_currency;
    }

    @Override
    public void init() {

        initData();
//        initEvent();
        switch (CommonUtils.getLocalLanguage()) {
            case "zh_CN":
            case "zh_TW":
            case "en":
                sbSort.setVisibility(View.VISIBLE);
                break;
            case "ja":
            case "ko":
                sbSort.setVisibility(View.GONE);
                break;
            default:
                sbSort.setVisibility(View.GONE);
                break;
        }
    }

    /**
     * 初始化MainBean的数据列表
     */
    public void initData() {
        if (NetUtil.isNetworkAvailable(ChooseCurrencyActivity.this)) {
            GetRequest<String> getRequest = OkGo.<String>get(Constant.IP_PORT + Constant.URL_CURRENCY).tag(this);
            getRequest.params("sysLanguage", Constant.SYSTEM_LANGUAGE);
            getRequest.params("sn", CommonUtils.getSN());
            getRequest.params("sl", CommonUtils.getLocalLanguage());
            getRequest.params("no", CommonUtils.getRandom());
            getRequest.execute(new StringCallback() {
                @Override
                public void onSuccess(Response<String> response) {
                    Constant.CURRENCY_LIST_VALUE = response.body();
                    SharePrefUtil.saveString(ChooseCurrencyActivity.this, Constant.CURRENCY_LIST_KEY, Constant.CURRENCY_LIST_VALUE);
                    setListDate(Constant.CURRENCY_LIST_VALUE);
                    initEvent();
                }

                @Override
                public void onError(Response<String> response) {
                    super.onError(response);
                    Log.e("choosecurrency", "onError");
                    Constant.CURRENCY_LIST_VALUE = SharePrefUtil.getString(ChooseCurrencyActivity.this, Constant.CURRENCY_LIST_KEY, "");
                    if (TextUtils.equals(Constant.CURRENCY_LIST_VALUE, "")) {
                        setListDate(Constant.CURRENCY_LIST_VALUE);
                        initEvent();
                    } else {
                        Toast.makeText(ChooseCurrencyActivity.this, getResources().getString(R.string.toast_zanwuxinxi), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Constant.CURRENCY_LIST_VALUE = SharePrefUtil.getString(ChooseCurrencyActivity.this, Constant.CURRENCY_LIST_KEY, "");
            if (!TextUtils.equals(Constant.CURRENCY_LIST_VALUE, "")) {
                setListDate(Constant.CURRENCY_LIST_VALUE);
                initEvent();
            } else {
                Toast.makeText(ChooseCurrencyActivity.this, getResources().getString(R.string.toast_zanwuxinxi), Toast.LENGTH_SHORT).show();
            }
        }

        sbSort.setTextView(tvDialog);
    }

    public void setListDate(String json) {
        sourceDateList = FastJsonUtil.changeJsonToList(json, NewCurrencyBean.class);
        Collections.sort(sourceDateList, new PinyinComparator());
        getIndexerAndCounts(sourceDateList);
        //設置頂部固定頭部
        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.list_group_item, sortListView, false);

        sortListView.setPinnedHeaderView(view);
    }

    public void getIndexerAndCounts(List<NewCurrencyBean> list) {
        ALL_CHARACTER = "";
        List<String> sectionlist = new ArrayList<>();
        for (NewCurrencyBean city : list) {    //计算全部城市
            String firstCharacter = city.getGroup();
            if (!sectionlist.contains(firstCharacter.toUpperCase())) {
                sectionlist.add(firstCharacter.toUpperCase());
            }
        }
        String [] a  = sectionlist.toArray(new String[sectionlist.size()]);
        sections = new String[a.length+1];
        sections[0] = getResources().getString(R.string.common_used);
        System.arraycopy(a,0,sections,1,a.length);
        //初始化每个字母有多少个item
        counts = new int[sections.length];
        for (String s : sectionlist) {
            ALL_CHARACTER = ALL_CHARACTER + s;
        }
        for (NewCurrencyBean city : list) {    //计算全部城市
            String firstCharacter = city.getGroup();
            int index = ALL_CHARACTER.indexOf(firstCharacter);
            counts[index + 1]++;
        }

        /**
         * @修改内容：2019年4月20日 11:48:12，修改汇率常用中只显示日元和人民币，不显示美元了
         *
         * @修改人：张文颖
         */

        NewCurrencyBean bean1 = new NewCurrencyBean(Constant.CURRENCY_NAME_MOREN_1);
        NewCurrencyBean bean2 = new NewCurrencyBean(Constant.CURRENCY_NAME_MOREN_2);
//        NewCurrencyBean bean3 = new NewCurrencyBean(Constant.CURRENCY_NAME_MOREN_3);
        bean1.setGroup(getResources().getString(R.string.common_used));
        bean2.setGroup(getResources().getString(R.string.common_used));
//        bean3.setGroup(getResources().getString(R.string.common_used));
        sourceDateList.add(0, bean1);
        sourceDateList.add(1, bean2);
//        sourceDateList.add(2, bean3);
        ALL_CHARACTER = "#" + ALL_CHARACTER;
//        counts[0] = 3;
        counts[0] = 2;

        mIndexer = new MySectionIndexer(sections, counts);
        adapter = new SortAdapter(this, list, mIndexer);
        sortListView.setAdapter(adapter);
        sortListView.setOnScrollListener(adapter);
    }

    public void initEvent() {
        //设置右侧触摸监听
        sbSort.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                if (CommonUtils.isAvailable(ChooseCurrencyActivity.this)){
                    //该字母首次出现的位置
                    int position = mIndexer.getPositionForSection(ALL_CHARACTER.indexOf(s));
                    /**####  start-hjs-addStatisticsEvent   ##**/
                    try {
                        HashMap<String, Serializable> add_hp = new HashMap<>();
                        add_hp.put("currency_choose5_num",position);
                        addStatisticsEvent("currency_choose5", add_hp);
                        if (position != -1&& sortListView!=null) {
                            //因为列表默认加上了常用的分组 所以位置需要再-1
                            sortListView.setSelection(position + 1 - 1);
                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    /**####  end-hjs-addStatisticsEvent  ##**/

                }
            }
        });
        //ListView的点击事件
        sortListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                /**####  start-hjs-addStatisticsEvent   ##**/
                try {
                    HashMap<String, Serializable> add_hp = new HashMap<>();
                    NewCurrencyBean newCurrencyBean= (NewCurrencyBean) adapter.getItem(position);
                    add_hp.put("currency_choose2_name",newCurrencyBean.getZh_ch());
                    addStatisticsEvent("currency_choose2", add_hp);

                }catch (Exception e){
                    e.printStackTrace();
                }
                /**####  end-hjs-addStatisticsEvent  ##**/
                Intent intent = new Intent();
                intent.putExtra("xuanzhongAbbreviation", sourceDateList.get(position).getKey());
                intent.putExtra("xuanzhongName", (Serializable) sourceDateList.get(position));
                setResult(666, intent);
                finish();
            }
        });
    }


    @OnClick(R.id.iv_guanbi)
    public void onViewClicked() {

        /**####  start-hjs-addStatisticsEvent   ##**/
        try {
            addStatisticsEvent("currency_choose1", null);
        }catch (Exception e){
            e.printStackTrace();
        }
        /**####  end-hjs-addStatisticsEvent  ##**/
        finish();
    }

}
