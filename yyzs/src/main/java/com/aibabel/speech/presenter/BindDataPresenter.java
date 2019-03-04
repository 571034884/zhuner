package com.aibabel.speech.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aibabel.speech.properites.Constants;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.bumptech.glide.Glide;
import com.aibabel.speech.MainActivity;
import com.aibabel.speech.R;
import com.aibabel.speech.activity.WebviewActivity;
import com.aibabel.speech.adapter.MyAdapter;
import com.aibabel.speech.adapter.MyAdapter1;
import com.aibabel.speech.adapter.ViewHolder;
import com.aibabel.speech.app.BaseApplication;
import com.aibabel.speech.baidu.BdMap;
import com.aibabel.speech.baidu.bean.PoiBean;
import com.aibabel.speech.properites.Constants;
import com.aibabel.speech.util.L;
import com.aibabel.speech.util.SDCardUtils;
import com.aibabel.speech.util.Utility;
import com.bumptech.glide.request.RequestOptions;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class BindDataPresenter {
    private MainActivity activity;
    private Context context;
    private WebView webView1;
    private ListView listView;
    List<Callback.Cancelable>  list=new ArrayList<>();



    public  BindDataPresenter(MainActivity a){
        activity=a;
        context=a;
        webView1=a.webView;
        listView=a.listView;
    }

    /**
     * activity结束时  取消presenter中的网络请求
     */
    public void cancelPost() {
        try {

            if (list.size()>0) {
                for (int i = 0; i < list.size(); i++) {
                    list.get(i).cancel();

                }
            }
        } catch (Exception e) {

        }
        if (list.size()>0) {
            list.clear();
        }

    }

    public void onStop() {

        if (activity.tts!=null) {
            activity.tts.stop();
            
        }
    }

    public void onDestory() {
        context=null;
        webView1=null;
        listView=null;
        activity=null;
        try {

            if (list.size()>0) {
                for (int i = 0; i < list.size(); i++) {
                    list.get(i).cancel();

                }
            }
        } catch (Exception e) {

        }
        list=null;

    }


    public void onPause() {

    }

    /**
     * 纯文本数据绑定在子布局上
     *
     * @param t
     * @param holder
     */
    public void bindTxtItem(String t, ViewHolder holder,TextToSpeech tts) {
        if (t.trim().equals("")) {
            ((TextView) holder.getView(R.id.item_result_txt_tv)).setText("准儿还在学习呢!");
            tts.speak("准儿还在学习呢!", TextToSpeech.QUEUE_ADD, null);
            return;
        }
        com.alibaba.fastjson.JSONObject bean = JSON.parseObject(t);
        com.alibaba.fastjson.JSONObject beanChild = bean.getJSONObject("data");
        if (bean.getString("code").equals("014")) {
            String answer = beanChild.getString("answer");
            ((TextView) holder.getView(R.id.item_result_txt_tv)).setText(answer);
            if (beanChild.containsKey("speech")) {
                tts.speak(beanChild.getString("speech"), TextToSpeech.QUEUE_ADD, null);
            }


        } else {
            String answer = beanChild.getString("answer");
            if (answer.equals("")) {
                answer="不好意思我刚才走神了!";
                ((TextView) holder.getView(R.id.item_result_txt_tv)).setText(answer);
                tts.speak(answer, TextToSpeech.QUEUE_ADD, null);
                return;
            }
            ((TextView) holder.getView(R.id.item_result_txt_tv)).setText(answer);
            tts.speak(answer, TextToSpeech.QUEUE_ADD, null);
        }

    }

    /**
     *计算结果
     * @param t
     * @param holder
     * @param tts
     */
    public void bindCountItem(String t, final ViewHolder holder,TextToSpeech tts) {
        com.alibaba.fastjson.JSONObject bean = JSON.parseObject(t);
        final com.alibaba.fastjson.JSONObject dataChild = bean.getJSONObject("data");
     if (bean.getString("code").equals("014")) {
            String[] answer = dataChild.getString("answer").split("=");
            ((TextView) holder.getView(R.id.item_result_count_equation)).setText(answer[0]+"=");
            ((TextView) holder.getView(R.id.item_result_count_result)).setText(answer[1]);
            if (dataChild.containsKey("speech")) {
                tts.speak(dataChild.getString("speech"), TextToSpeech.QUEUE_ADD, null);
            }
        }

    }

    /**
     * 汇率
     * @param t
     * @param holder
     * @param tts
     */
    public void bindExchangeRateItem(String t, final ViewHolder holder,TextToSpeech tts) {
        com.alibaba.fastjson.JSONObject bean = JSON.parseObject(t);
        final com.alibaba.fastjson.JSONObject dataChild = bean.getJSONObject("data");
        String[] answer = dataChild.getString("answer").split("\n");
        tts.speak(dataChild.getString("speech"), TextToSpeech.QUEUE_ADD, null);
        LinearLayout linearLayout=holder.getView(R.id.item_result_exchange_rate_ll);

        ((TextView)holder.getView(R.id.item_result_exchange_rate_date)).setText(answer[0]);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        for (int i = 1; i < answer.length; i++) {
            TextView textView = new TextView(context);

            if (i == answer.length - 1) {
                layoutParams.setMargins(50, 20, 50, 32);
            } else  {
                layoutParams.setMargins(50, 20, 50, 0);
            }
            textView.setPadding(0,0,0,0);
            textView.setTextSize(24);
            textView.setGravity(Gravity.CENTER_HORIZONTAL);
            textView.setTextColor(context.getResources().getColor(R.color.mainbg));
            textView.setText(answer[i]);
            textView.setLayoutParams(layoutParams);
            linearLayout.addView(textView);


        }

    }

    /**
     * 火车信息数据绑定在子布局上
     *
     * @param t
     * @param holder
     */
    public void bindTrainItem(String t, final ViewHolder holder,TextToSpeech tts,final WebView webView) {

        com.alibaba.fastjson.JSONObject bean = JSON.parseObject(t);
        final com.alibaba.fastjson.JSONObject dataChild = bean.getJSONObject("data");
        if (bean.getString("code").equals("007")) {
            String answer = dataChild.getString("answer");
            ((TextView) holder.getView(R.id.item_result_train_tv)).setText(answer);
            ((TextView) holder.getView(R.id.item_result_train_title)).setText("国家概况");
            tts.speak(answer, TextToSpeech.QUEUE_ADD, null);

             webView1=holder.getView(R.id.item_result_train_webview);

            webView1.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    view.getSettings().setJavaScriptEnabled(true);
                    super.onPageStarted(view, url, favicon);

                }

                @Override
                public void onPageFinished(WebView view, String url) {

                    ((TextView) holder.getView(R.id.item_result_train_loading)).setVisibility(View.GONE);
                    webView1.setVisibility(View.VISIBLE);
                    Log.e("-------", "onPageFinished: "+url );


                }

                @Override
                public void onReceivedError(WebView view, int errorCode,
                                            String description, String failingUrl) {
                    super.onReceivedError(view, errorCode, description, failingUrl);
                }

                @Override
                public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                    //super.onReceivedSslError(view, handler, error);
                    handler.proceed();
                }

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                    return super.shouldOverrideUrlLoading(view, request);
                }
            });
            webView1.loadUrl(dataChild.getString("body"));
            WebSettings webSettings = webView1.getSettings();
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            webSettings.setJavaScriptEnabled(true);
            webSettings.setDomStorageEnabled(true);

            webView1.getSettings().setJavaScriptEnabled(true);

            webView1.getSettings().setSavePassword(false);
            webView1.setVerticalScrollBarEnabled(false);
            webView1.setHorizontalScrollBarEnabled(false);
            // 设置可以支持缩放
            webView1.getSettings().setSupportZoom(true);
            // 扩大比例的缩放
            webView1.getSettings().setUseWideViewPort(true);
            // 自适应屏幕
            webView1.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
            webView1.getSettings().setLoadWithOverviewMode(true);
            webView1.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

//            webView1.setOnTouchListener(new View.OnTouchListener() {
//
//                @Override
//                public boolean onTouch(View v, MotionEvent event) {
//                    if (event.getAction() == MotionEvent.ACTION_UP)
//                       listView .requestDisallowInterceptTouchEvent(false);
//                    else
//                        listView.requestDisallowInterceptTouchEvent(true);
//                    return false;
//                }
//            });

            LinearLayout.LayoutParams params= (LinearLayout.LayoutParams) webView1.getLayoutParams();
            params.height=2950;
            webView1.setLayoutParams(params);








        } else {

            String answer = dataChild.getString("answer");
            ((TextView) holder.getView(R.id.item_result_train_tv)).setText(answer);

            if (bean.getString("code").equals("009")) {
                ((TextView) holder.getView(R.id.item_result_train_title)).setText("火车信息");
            } else {
                ((TextView) holder.getView(R.id.item_result_train_title)).setText("航班信息");
            }

            tts.speak(answer, TextToSpeech.QUEUE_ADD, null);
            if (dataChild.containsKey("info")) {
                com.alibaba.fastjson.JSONObject infoChild = dataChild.getJSONObject("info");
//            webView = ((WebView) holder.getView(R.id.item_result_train_webview));
                webView.setWebViewClient(new WebViewClient() {
                    @Override
                    public void onPageStarted(WebView view, String url, Bitmap favicon) {
                        super.onPageStarted(view, url, favicon);

                    }

                    @Override
                    public void onPageFinished(WebView view, String url) {

                        ((TextView) holder.getView(R.id.item_result_train_loading)).setVisibility(View.GONE);
                        webView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onReceivedError(WebView view, int errorCode,
                                                String description, String failingUrl) {
                        super.onReceivedError(view, errorCode, description, failingUrl);
                    }

                    @Override
                    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                        //super.onReceivedSslError(view, handler, error);
                        handler.proceed();
                    }
                });
                webView.loadUrl(infoChild.getString("url"));
                L.e("-----"+infoChild.getString("url"));
                webView.getSettings().setJavaScriptEnabled(true);
                webView.getSettings().setSavePassword(false);
                webView.setVerticalScrollBarEnabled(false);
                webView.setHorizontalScrollBarEnabled(false);
                // 设置可以支持缩放
                webView.getSettings().setSupportZoom(true);
                // 扩大比例的缩放
                webView.getSettings().setUseWideViewPort(true);
                // 自适应屏幕
                webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
                webView.getSettings().setLoadWithOverviewMode(true);
                webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);


            } else {
                ((LinearLayout) holder.getView(R.id.item_result_train_ll)).setVisibility(View.GONE);
            }

        }



    }

    /**
     * 选择出发位置
     * @param t
     * @param holder
     * @param tts
     * @param data
     * @param adapter
     */
    public void bindSelectOutsetItem(String t, ViewHolder holder, TextToSpeech tts, final List<String> data, final MyAdapter adapter, final String id){
        com.alibaba.fastjson.JSONObject bean = JSON.parseObject(t);
        com.alibaba.fastjson.JSONObject beanChild = bean.getJSONObject("data");
        JSONArray body=beanChild.getJSONArray("body");
        L.e(body.toString());
        String answer = beanChild.getString("answer");
        ((TextView) holder.getView(R.id.item_result_select_setout_tv)).setText(answer);

        tts.speak(answer, TextToSpeech.QUEUE_ADD, null);

        final TextView my= ((TextView) holder.getView(R.id.item_result_select_setout_my));
         my.setText(body.getString(0));
         my.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 data.clear();
                 data.add(my.getText().toString());
                 adapter.fresh(data);
                 shibie(my.getText().toString(),id,adapter);

             }
         });
        final TextView other= ((TextView) holder.getView(R.id.item_result_select_setout_other));
        other.setText(body.getString(1));
        other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.clear();
                data.add(other.getText().toString());
                adapter.fresh(data);
                shibie(other.getText().toString(),id,adapter);
            }
        });


    }


    /**
     * 规划路线
     * @param res
     * @param id
     * @param myAdapter
     */
    public void shibie(String res, String id, final MyAdapter myAdapter) {
        RequestParams rp = new RequestParams(Constants.getInstance().getIntentionUrl());
        rp.addBodyParameter("id", id);
        if (res.lastIndexOf("。")!=-1) {
            res=res.substring(0,res.length()-1);
        }
        rp.addBodyParameter("text", res);
        rp.addBodyParameter("location", BdMap.getInstance().getLocation(context));
        rp.addBodyParameter("city", BdMap.getInstance().getCity(context));
        rp.addBodyParameter("country", BdMap.getInstance().getCountry(context));
        rp.addBodyParameter("bot_session", BaseApplication.bot_session);
        rp.addBodyParameter("is_dialog_end", BaseApplication. is_dialog_end);


        L.e("--------" +  BaseApplication. bot_session);

        
       Callback.Cancelable post= x.http().post(rp, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                L.e("json--------------------------------" + result);
                BaseApplication.is_dialog_end=null;
                myAdapter.loadOne(result);
                com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(result);
                if (jsonObject.containsKey("bot_session")) {
                    BaseApplication. bot_session = jsonObject.getString("bot_session");
                }
                if (jsonObject.containsKey("is_dialog_end")) {
                    BaseApplication. is_dialog_end = jsonObject.getString("is_dialog_end");
                }
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                L.e("onError----------" + ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

       list.add(post);
    }


    /**
     * poi搜索
     */

    public void bindSearchListItem(String t, final ViewHolder holder) {
        com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(t);
        com.alibaba.fastjson.JSONObject data = jsonObject.getJSONObject("data");


        if (jsonObject.getString("code").equals("003_0")) {

            ((TextView) holder.getView(R.id.item_result_search_list_answer)).setText(data.getString("answer"));
            activity.tts.speak(data.getString("answer"), TextToSpeech.QUEUE_ADD, null);
            final ListView listView1 = ((ListView) holder.getView(R.id.item_result_search_list_listview));
            PoiBean bean=JSON.parseObject(t,PoiBean.class);
            List<PoiBean.DataBean.BodyBean>  poiList=bean.getData().getBody();
            if (poiList.size() > 0) {
                ((TextView) holder.getView(R.id.item_result_search_list_loading)).setVisibility(View.GONE);

                listView1.setAdapter(new MyAdapter1<PoiBean.DataBean.BodyBean>(context, poiList, R.layout.item_child_result_search_list) {


                    @Override
                    public void convert(ViewHolder holder, final PoiBean.DataBean.BodyBean poiDetailResult) {

                        try {
                            ((TextView) holder.getView(R.id.item_child_result_search_list_name)).setText(poiDetailResult.getName());
                            ((TextView) holder.getView(R.id.item_child_result_search_list_juli)).setText(poiDetailResult.getArea() + " • " + poiDetailResult.getDistance() + "米");
                            ((RatingBar) holder.getView(R.id.item_child_result_search_list_pingfen)).setRating(Float.valueOf(poiDetailResult.getRating()));
                            ((TextView) holder.getView(R.id.item_child_result_search_list_pinglun)).setText("￥" + poiDetailResult.getPrice() + "   " + poiDetailResult.getTag());
                            ImageView imageView = holder.getView(R.id.item_child_result_search_list_img);
                            bindImage(imageView, poiDetailResult.getUrl());

                            ((RelativeLayout) holder.getView(R.id.item_child_result_search_list_rl)).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(context, WebviewActivity.class);
                                    intent.putExtra("url", poiDetailResult.getUrl());
                                   activity. startActivity(intent);

                                }
                            });

                        } catch (Exception e) {

                        }

                    }
                });

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Utility.setListViewHeightBasedOnChildren(listView1);
                    }
                },400);

            } else {
                ((TextView) holder.getView(R.id.item_result_search_list_loading)).setVisibility(View.VISIBLE);
                ((TextView) holder.getView(R.id.item_result_search_list_loading)).setText("没有搜索结果!");
            }

        } else if (jsonObject.getString("code").equals("003_1")) {

            ((TextView) holder.getView(R.id.item_result_search_list_answer)).setText(data.getString("answer"));
            activity.tts.speak(data.getString("answer"), TextToSpeech.QUEUE_ADD, null);
            final ListView listView1 = ((ListView) holder.getView(R.id.item_result_search_list_listview));
            PoiBean bean=JSON.parseObject(t,PoiBean.class);
            List<PoiBean.DataBean.BodyBean>  poiList=bean.getData().getBody();
            if (poiList.size()>0) {
                ((TextView) holder.getView(R.id.item_result_search_list_loading)).setVisibility(View.GONE);

                listView1.setAdapter(new MyAdapter1<PoiBean.DataBean.BodyBean>(context, poiList, R.layout.item_child_result_search_list) {


                    @Override
                    public void convert(ViewHolder holder, final PoiBean.DataBean.BodyBean  poiDetailResult) {
                        ((TextView) holder.getView(R.id.item_child_result_search_list_name)).setText(poiDetailResult.getName());

                        ((TextView) holder.getView(R.id.item_child_result_search_list_juli)).setText(poiDetailResult.getArea()+ " • " + poiDetailResult.getDistance()+ "米");
                        ((RatingBar) holder.getView(R.id.item_child_result_search_list_pingfen)).setRating(Float.valueOf( poiDetailResult.getRating()));
                        ((TextView) holder.getView(R.id.item_child_result_search_list_pinglun)).setText("￥" + poiDetailResult.getPrice() + "   " + poiDetailResult.getTag());
                        ImageView imageView = holder.getView(R.id.item_child_result_search_list_img);
                        bindImage(imageView, poiDetailResult.getUrl());


                        ((RelativeLayout) holder.getView(R.id.item_child_result_search_list_rl)).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(context, WebviewActivity.class);
                                intent.putExtra("url", poiDetailResult.getUrl());
                                activity.startActivity(intent);

                            }

                        });

                    }

                });
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Utility.setListViewHeightBasedOnChildren(listView1);
                    }
                },400);


            } else {
                ((TextView) holder.getView(R.id.item_result_search_list_loading)).setVisibility(View.VISIBLE);
                ((TextView) holder.getView(R.id.item_result_search_list_loading)).setText("没有搜索结果!");
            }


        }

    }


    public void bindImage(final ImageView imageView, final String url) {

        RequestParams rp = new RequestParams(Constants.getInstance().getGetImgUrl());
        rp.addBodyParameter("url", url);

       Callback.Cancelable post= x.http().post(rp, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                L.e("图片：" + result);
                com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(result);
                if (jsonObject.containsKey("img")) {
                    RequestOptions options = new RequestOptions().placeholder(R.mipmap.meishi);
                    Glide.with(context).load(jsonObject.get("img")).apply(options).into(imageView);
//                    Glide.with(context).load(jsonObject.get("img")).placeholder(R.mipmap.meishi).into(imageView);
                } else if (jsonObject.getString("img").equals("")) {
                    Glide.with(context).load(R.mipmap.meishi).into(imageView);
                } else {
                    Glide.with(context).load(R.mipmap.meishi).into(imageView);
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                L.e("图片错误");
                Glide.with(context).load(R.mipmap.meishi).into(imageView);
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
       list.add(post);
    }



}
