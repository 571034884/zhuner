package com.aibabel.travel.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.aibabel.travel.app.BaseApplication;
import com.aibabel.travel.bean.CityBean;
import com.aibabel.travel.bean.CityItemBean;
import com.aibabel.travel.bean.CountryData;
import com.aibabel.travel.bean.DataBean;
import com.aibabel.travel.bean.SpotBean;
import com.aibabel.travel.bean.SpotChildrenBean;
import com.aibabel.travel.utils.Constant;

import java.util.ArrayList;
import java.util.List;

public class DataDAO {
    private static final String TABLE_TRAVEL_NAME = "gowithtommyoutdata";
    private static final String TABLE_COUNTRY_NAME = "mingcheng";

//    private static final String path_pre = Environment.getExternalStorageDirectory()+"/travel/";

    static List<CountryData.DataBean.ResultsBean> countrys = new ArrayList<>();

    private static String[][] array = {{"法国", "5", "欧洲", "1"},
            {"意大利", "6", "欧洲", "1"},
            {"德国", "10", "欧洲", "0"},
            {"英国", "7", "欧洲", "0"},
            {"西班牙", "16", "欧洲", "0"},
            {"瑞士", "29", "欧洲", "0"},
            {"荷兰", "27", "欧洲", "0"},
            {"奥地利", "25", "欧洲", "0"},
            {"捷克", "33", "欧洲", "0"},
            {"梵蒂冈", "35", "欧洲", "0"},
            {"比利时", "22", "欧洲", "0"},
            {"俄罗斯", "31", "欧洲", "1"},
            {"土耳其", "17", "亚洲", "0"},
            {"希腊", "30", "欧洲", "0"},
            {"瑞典", "49", "欧洲", "0"},
            {"匈牙利", "26", "欧洲", "0"},
            {"丹麦", "23", "欧洲", "0"},
            {"葡萄牙", "37", "欧洲", "0"},
            {"芬兰", "81", "欧洲", "0"},
            {"挪威", "96", "欧洲", "0"},
            {"波兰", "42", "欧洲", "0"},
            {"爱尔兰", "48", "欧洲", "0"},
            {"克罗地亚", "95", "欧洲", "0"},
            {"中国大陆", "40", "亚洲", "0"},
            {"日本", "8", "亚洲", "1"},
            {"泰国", "20", "亚洲", "1"},
            {"新加坡", "11", "亚洲", "0"},
            {"韩国", "9", "亚洲", "1"},
            {"马来西亚", "21", "亚洲", "0"},
            {"柬埔寨", "19", "亚洲", "0"},
            {"越南", "43", "亚洲", "0"},
            {"印度尼西亚", "82", "亚洲", "0"},
            {"菲律宾", "94", "亚洲", "0"},
            {"阿联酋", "28", "亚洲", "0"},
            {"尼泊尔", "38", "亚洲", "0"},
            {"印度", "18", "亚洲", "0"},
            {"老挝", "93", "亚洲", "0"},
            {"以色列", "36", "亚洲", "0"},
            {"中国香港", "88", "亚洲", "0"},
            {"中国澳门", "98", "亚洲", "0"},
            {"中国台湾", "89", "亚洲", "0"},
            {"美国", "12", "北美洲", "1"},
            {"澳大利亚", "13", "大洋洲", "0"},
            {"巴西", "15", "南美洲", "0"},
            {"摩洛哥", "24", "非洲", "0"},
            {"埃及", "47", "非洲", "0"},
            {"加拿大", "50", "北美洲", "0"},
            {"新西兰", "51", "大洋洲", "0"},
            {"斯里兰卡", "85", "亚洲", "0"},
            {"墨西哥", "97", "北美洲", "0"},};

    /**
     * 获取所有国家
     *
     * @return
     */
    public static List<CountryData.DataBean.ResultsBean> queryCountrys() {
        SQLiteDatabase db = BaseApplication.sqLiteDatabase;
        List<CountryData.DataBean.ResultsBean> list = new ArrayList<>();
        if (db.isOpen()) {

            //SELECT * FROM mingcheng LEFT JOIN gowithtommyoutdata ON gowithtommyoutdata.name=mingcheng.cnName WHERE mingcheng.enName=''
            String sql = "select * from " + TABLE_TRAVEL_NAME + " where type = 1";
            Log.d("DAO",sql);
            Cursor cursor = db.rawQuery(sql, null);
            while (cursor.moveToNext()) {
                CountryData.DataBean.ResultsBean bean = new CountryData.DataBean.ResultsBean();
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String cover_path = cursor.getString(cursor.getColumnIndex("cover_path"));
                String countryName = cursor.getString(cursor.getColumnIndex("countryName"));

                bean.setId(id);
                bean.setName(countryName);
                bean.setCover(cover_path);
                list.add(bean);

            }

        }
        countrys = list;
        return list;
    }

    /**
     * 根据大洲查国家
     *
     * @param state
     * @return
     */
    public static List<CountryData.DataBean.ResultsBean> queryCountrysByState(String state) {
        List<CountryData.DataBean.ResultsBean> list = new ArrayList<>();
        if (null != countrys && countrys.size() > 0) {
            return list;
        }

        for (int i = 0; i < array.length; i++) { //遍历二维数组，遍历出来的每一个元素是一个一维数组
//            for(int j = 0; j < array[i].length; j++){ //遍历对应位置上的一维数组
            for (CountryData.DataBean.ResultsBean country : countrys) {
                if (TextUtils.equals(country.getName(), array[i][0]) && TextUtils.equals(array[i][2], state)) {
                    list.add(country);
                }
//                }


            }
        }
        return list;
    }

    /**
     * 获取国家
     *
     * @param id
     * @return
     */
    public static DataBean queryCountry(int id) {
        SQLiteDatabase db = BaseApplication.sqLiteDatabase;
        DataBean bean  = new DataBean();
        if (db.isOpen()) {

            //SELECT * FROM mingcheng LEFT JOIN gowithtommyoutdata ON gowithtommyoutdata.name=mingcheng.cnName WHERE mingcheng.enName=''
            Cursor cursor = db.rawQuery("select * from " + TABLE_TRAVEL_NAME + " where type = 1 and id = " + id, null);
            while (cursor.moveToNext()) {

//                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
//                int pid = cursor.getInt(cursor.getColumnIndex("pid"));
                int tpye = cursor.getInt(cursor.getColumnIndex("type"));
                String pname = cursor.getString(cursor.getColumnIndex("pname"));
                String cover_path = cursor.getString(cursor.getColumnIndex("cover_path"));
                String map_image_path = cursor.getString(cursor.getColumnIndex("cover_path"));
                String navi_image_path = cursor.getString(cursor.getColumnIndex("navi_image_path"));
                String cityName = cursor.getString(cursor.getColumnIndex("cityName"));
                String countryName = cursor.getString(cursor.getColumnIndex("countryName"));
                Double latitude = cursor.getDouble(cursor.getColumnIndex("latitude"));
                Double longitude = cursor.getDouble(cursor.getColumnIndex("longitude"));
                String audiospath = cursor.getString(cursor.getColumnIndex("audiospath"));
                String imagespath = cursor.getString(cursor.getColumnIndex("imagespath"));
                int subCount = cursor.getInt(cursor.getColumnIndex("subCount"));
                String navi_image = cursor.getString(cursor.getColumnIndex("navi_image"));
                String map_image = cursor.getString(cursor.getColumnIndex("map_image"));
                String audiossize = cursor.getString(cursor.getColumnIndex("audiossize"));
                String imagessize = cursor.getString(cursor.getColumnIndex("imagessize"));


                bean.setId(id);
                bean.setName(name);
                bean.setCover(cover_path);
                bean.setAudiospath(audiospath);

            }

        }

        return bean;
    }

    /**
     * 获取城市列表
     *
     * @param country
     * @return
     */
    public static List<CityBean.DataBean.ResultsBean> queryCitys(String country) {
        String path_pre = Environment.getExternalStorageDirectory()+"/offline/jqdl/" + Constant.PREFIX+"/";
        SQLiteDatabase db = BaseApplication.sqLiteDatabase;
        List<CityBean.DataBean.ResultsBean> list = new ArrayList<>();
        try{
        if (db.isOpen()) {

            String sql = "select * from " + TABLE_TRAVEL_NAME + " where type = 2 and countryName = "+"\'"+country+"\'" ;
            Log.d("DAO",sql);

                Cursor cursor = db.rawQuery(sql, null);
                while (cursor.moveToNext()) {
                    CityBean.DataBean.ResultsBean bean = new CityBean.DataBean.ResultsBean();
                    int id = cursor.getInt(cursor.getColumnIndex("id"));
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    int tpye = cursor.getInt(cursor.getColumnIndex("type"));
                    String pname = cursor.getString(cursor.getColumnIndex("pname"));
                    String cover_path = cursor.getString(cursor.getColumnIndex("cover_path"));
                    int subCount = cursor.getInt(cursor.getColumnIndex("subCount"));

                    bean.setId(id);
                    bean.setName(name);
                    bean.setSubCount(subCount);
                    bean.setCover(path_pre+cover_path);
                    list.add(bean);
                }
        }
                }catch(Exception e){
                e.printStackTrace();
                }




        return list;
    }



    /**
     * 获取城市
     *
     * @param id
     * @return
     */
    public static DataBean queryCity(int id) {
        String path_pre = Environment.getExternalStorageDirectory()+"/offline/jqdl/" + Constant.PREFIX+"/";
        SQLiteDatabase db = BaseApplication.sqLiteDatabase;
        DataBean bean  = new DataBean();
        if (db.isOpen()) {

            //SELECT * FROM mingcheng LEFT JOIN gowithtommyoutdata ON gowithtommyoutdata.name=mingcheng.cnName WHERE mingcheng.enName=''
            Cursor cursor = db.rawQuery("select * from " + TABLE_TRAVEL_NAME + " where type = 2 and id = " + id, null);
            while (cursor.moveToNext()) {

//                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
//                int pid = cursor.getInt(cursor.getColumnIndex("pid"));
                int tpye = cursor.getInt(cursor.getColumnIndex("type"));
                String pname = cursor.getString(cursor.getColumnIndex("pname"));
                String cover_path = cursor.getString(cursor.getColumnIndex("cover_path"));
                String map_image_path = cursor.getString(cursor.getColumnIndex("cover_path"));
                String navi_image_path = cursor.getString(cursor.getColumnIndex("navi_image_path"));
                String cityName = cursor.getString(cursor.getColumnIndex("cityName"));
                String countryName = cursor.getString(cursor.getColumnIndex("countryName"));
                Double latitude = cursor.getDouble(cursor.getColumnIndex("latitude"));
                Double longitude = cursor.getDouble(cursor.getColumnIndex("longitude"));
                String audiospath = cursor.getString(cursor.getColumnIndex("audiospath"));
                String imagespath = cursor.getString(cursor.getColumnIndex("imagespath"));
//                int subCount = cursor.getInt(cursor.getColumnIndex("subCount"));
//                String navi_image = cursor.getString(cursor.getColumnIndex("navi_image"));
//                String map_image = cursor.getString(cursor.getColumnIndex("map_image"));
//                String audiossize = cursor.getString(cursor.getColumnIndex("audiossize"));
//                String imagessize = cursor.getString(cursor.getColumnIndex("imagessize"));


                bean.setId(id);
                bean.setName(name);
                bean.setCover(path_pre+cover_path);
                bean.setAudiospath(audiospath);
                bean.setAudiospath(audiospath);

            }

        }

        return bean;
    }

    /**
     * 获取景点
     *
     * @param id
     * @return
     */
    public static DataBean querySpot(int id) {
        SQLiteDatabase db = BaseApplication.sqLiteDatabase;
      DataBean bean = new DataBean();
        if (db.isOpen()) {

            //SELECT * FROM mingcheng LEFT JOIN gowithtommyoutdata ON gowithtommyoutdata.name=mingcheng.cnName WHERE mingcheng.enName=''
            Cursor cursor = db.rawQuery("select * from " + TABLE_TRAVEL_NAME + "where type = 3 and id = " + id, null);
            while (cursor.moveToNext()) {

//                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
//                int pid = cursor.getInt(cursor.getColumnIndex("pid"));
                int tpye = cursor.getInt(cursor.getColumnIndex("type"));
                String pname = cursor.getString(cursor.getColumnIndex("pname"));
                String cover_path = cursor.getString(cursor.getColumnIndex("cover_path"));
                String map_image_path = cursor.getString(cursor.getColumnIndex("cover_path"));
                String navi_image_path = cursor.getString(cursor.getColumnIndex("navi_image_path"));
                String cityName = cursor.getString(cursor.getColumnIndex("cityName"));
                String countryName = cursor.getString(cursor.getColumnIndex("countryName"));
                Double latitude = cursor.getDouble(cursor.getColumnIndex("latitude"));
                Double longitude = cursor.getDouble(cursor.getColumnIndex("longitude"));
                String audiospath = cursor.getString(cursor.getColumnIndex("audiospath"));
                String imagespath = cursor.getString(cursor.getColumnIndex("imagespath"));
                int subCount = cursor.getInt(cursor.getColumnIndex("subCount"));
                String navi_image = cursor.getString(cursor.getColumnIndex("navi_image"));
                String map_image = cursor.getString(cursor.getColumnIndex("map_image"));
                String audiossize = cursor.getString(cursor.getColumnIndex("audiossize"));
                String imagessize = cursor.getString(cursor.getColumnIndex("imagessize"));

                bean.setId(id);
                bean.setName(name);
                bean.setCover(cover_path);
                bean.setSubCount(subCount);
                bean.setAudiospath(audiospath);
            }

        }

        return bean;
    }

    /**
     * 获取景点列表
     *
     * @param cityName
     * @return
     */
    public static List<SpotBean.DataBean.ResultsBean> querySpots(String cityName) {
        String path_pre = Environment.getExternalStorageDirectory()+"/offline/jqdl/" + Constant.PREFIX+"/";
        SQLiteDatabase db = BaseApplication.sqLiteDatabase;
        List<SpotBean.DataBean.ResultsBean> list = new ArrayList<>();
        if (db.isOpen()) {

           try{
               String sql = "select * from " + TABLE_TRAVEL_NAME + " where type = 3 and cityName = " + "\'"+cityName+ "\'";
               Log.d("DAO",sql);
               //SELECT * FROM mingcheng LEFT JOIN gowithtommyoutdata ON gowithtommyoutdata.name=mingcheng.cnName WHERE mingcheng.enName=''
               Cursor cursor = db.rawQuery(sql, null);
               while (cursor.moveToNext()) {
                   SpotBean.DataBean.ResultsBean bean = new SpotBean.DataBean.ResultsBean();
                   int id = cursor.getInt(cursor.getColumnIndex("id"));
                   String name = cursor.getString(cursor.getColumnIndex("name"));
                   String cover_path = cursor.getString(cursor.getColumnIndex("cover_path"));
                   String audiospath = cursor.getString(cursor.getColumnIndex("audiospath"));
                   int subCount = cursor.getInt(cursor.getColumnIndex("subCount"));

                   bean.setId(id);
                   bean.setName(name);
                   bean.setCover(path_pre+cover_path);
                   bean.setSubCount(subCount);
                   List<SpotBean.DataBean.ResultsBean.AudiosBean> audios = new ArrayList<>();
                   SpotBean.DataBean.ResultsBean.AudiosBean audiosBean = new SpotBean.DataBean.ResultsBean.AudiosBean();
                   audiosBean.setUrl(path_pre+audiospath);
                   audios.add(audiosBean);
                   bean.setAudios(audios);
                   list.add(bean);
               }

           }catch(Exception e){
               e.printStackTrace();
               }


        }

        return list;
    }

    /**
     * 获取子景点列表
     *
     * @param pname
     * @param pn pageNumber
     * @return
     */
    public static List<SpotChildrenBean.DataBean.ResultsBean> querySpotsChildren(String pname,int pn,String id) {
        String path_pre = Environment.getExternalStorageDirectory()+"/offline/jqdl/" + Constant.PREFIX+"/";
        SQLiteDatabase db = BaseApplication.sqLiteDatabase;
        List<SpotChildrenBean.DataBean.ResultsBean> list = new ArrayList<>();
        try{



        if (db.isOpen()) {
//            String sql = "select * from " + TABLE_TRAVEL_NAME + " where type = 4 and pname = " +  "\'"+pname+ "\'";
            String sql = "select * from " + TABLE_TRAVEL_NAME + " where type = 4 and pname = " +  "\'"+pname+ "\'"+" and pid = " + "\' "+id+ "\'";
            Log.d("DAO",sql);
            Cursor cursor = db.rawQuery(sql, null);
            while (cursor.moveToNext()) {
                SpotChildrenBean.DataBean.ResultsBean bean = new SpotChildrenBean.DataBean.ResultsBean();
                int mid = cursor.getInt(cursor.getColumnIndex("id"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String cover_path = cursor.getString(cursor.getColumnIndex("cover_path"));
                String audiospath = cursor.getString(cursor.getColumnIndex("audiospath"));
                int subCount = cursor.getInt(cursor.getColumnIndex("subCount"));
                bean.setId(mid);
                bean.setName(name);
                bean.setCover(path_pre+cover_path);
                bean.setSubCount(String.valueOf(subCount));
                List<SpotChildrenBean.DataBean.ResultsBean.AudiosBean> audios = new ArrayList<>();
                SpotChildrenBean.DataBean.ResultsBean.AudiosBean audiosBean = new SpotChildrenBean.DataBean.ResultsBean.AudiosBean();
                audiosBean.setUrl(path_pre+audiospath);
                audios.add(audiosBean);
                bean.setAudios(audios);
                list.add(bean);
            }

        }
        }catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }


}
