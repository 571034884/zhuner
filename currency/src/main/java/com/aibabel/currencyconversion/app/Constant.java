package com.aibabel.currencyconversion.app;

/**
 * 作者：SunSH on 2018/5/17 21:41
 * 功能：
 * 版本：1.0
 */
public class Constant {

    public static String IP_PORT = "http://abroad.api.joner.aibabel.cn:7001";
    public static String IP_PORT_TEST = "http://39.107.238.111:7001";

    //汇率网址
    public final static String URL = "/v1/currency?c=";
    public final static String URL_COUPON = "/v1/GetCurrencyCouponData";
    //币种网址
//    public final static String URL_CURRENCY = "/v1/currency/list";
    public final static String URL_CURRENCY = "/v1/currencylistByLanguage";
    //保存到sharedpreference中的上一次获取的完整json
    public final static String CURRENCY_JSON_KEY = "JSON";
    public static String CURRENCY_JSON_VALUE = "";
    //保存到sharedpreference中的上一次获取的完整List
    public final static String CURRENCY_LIST_KEY = "LIST";
    public static String CURRENCY_LIST_VALUE = "";
    //保存到sharedpreference中的3中币种key，value
    public final static String CURRENCY_NAME_KEY_1 = "currency1";
    public final static String CURRENCY_NAME_KEY_2 = "currency2";
    public final static String CURRENCY_NAME_KEY_3 = "currency3";
    public static String CURRENCY_NAME_VALUE_1 = "人民币";
    public static String CURRENCY_NAME_VALUE_2 = "美元";
    public static String CURRENCY_NAME_VALUE_3 = "日元";
    public static String CURRENCY_NAME_MOREN_1 = "{\"key\":\"CNY\",\"zh_ch\":\"人民币\",\"zh_tw\":\"人民幣\",\"en\":\"Chinese yuan\",\"jp\":\"人民元\",\"ko\":\"중국 위안\"}";
    public static String CURRENCY_NAME_MOREN_2 = " {\"key\": \"USD\",\"zh_ch\": \"美元\",\"zh_tw\": \"美元\",\"en\": \"US dollar\",\"jp\": \"ＵＳドル\",\"ko\": \"미국 달러\"}";
    public static String CURRENCY_NAME_MOREN_3 = "{\"key\":\"JPY\",\"zh_ch\":\"日元\",\"zh_tw\":\"日圓\",\"en\":\"Japanese yen\",\"jp\":\"日本円\",\"ko\":\"일본 엔\"}";
    //保存到sharedpreference中的3中币种对应的缩写key，value
    public final static String CURRENCY_ABBREVIATION_KEY_1 = "abbreviations1";
    public final static String CURRENCY_ABBREVIATION_KEY_2 = "abbreviations2";
    public final static String CURRENCY_ABBREVIATION_KEY_3 = "abbreviations3";
    public static String CURRENCY_ABBREVIATION_VALUE_1 = "CNY";
    public static String CURRENCY_ABBREVIATION_VALUE_2 = "USD";
    public static String CURRENCY_ABBREVIATION_VALUE_3 = "JPY";
    //保存到sharedpreference中的3中币种对应的转化为另两种的汇率
    public static double CURRENCY_VALUE_1_TO_2;
    public static double CURRENCY_VALUE_1_TO_3;
    public static double CURRENCY_VALUE_2_TO_1;
    public static double CURRENCY_VALUE_2_TO_3;
    public static double CURRENCY_VALUE_3_TO_1;
    public static double CURRENCY_VALUE_3_TO_2;
    //活的汇率的时间
    public static String CURRENCY_TIME;
    //国际化的本地语言
    public static String NATIVE_LANGUAGE = "";

    public static String SYSTEM_LANGUAGE = "Chj";

    public static String DB_VERSION_CODE_KEY = "sp_version_code";
}
