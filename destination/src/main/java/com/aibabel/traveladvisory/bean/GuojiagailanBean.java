package com.aibabel.traveladvisory.bean;

import com.aibabel.traveladvisory.app.Constans;
import com.aibabel.traveladvisory.okgo.BaseBean;

import java.util.List;

/**
 * 作者：SunSH on 2018/6/15 15:15
 * 功能：
 * 版本：1.0
 */
public class GuojiagailanBean extends BaseBean {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * place_picture : https://ypimg1.youpu.cn/album/Japan/20140730/53d8c9cc0b95e.jpg@640w_1000h_1c_1e_1o
         * overview_info : {"name":"国家概览","content":{"placeId":"135","placeCnName":"日本","distance":"377960","population":"12708","populationDesc":"大和族 98.5% 朝鲜族 0.5% 汉族 0.4% 其他民族 0.6%","language":[{"cnName":"日文","enName":"Japanese","value":"100"},{"cnName":"","enName":"","value":"0"}],"travelTime":{"busy":{"month":"3月-5月","desc":"樱花由南至北逐渐绽放 最佳赏樱季节"},"common":{"month":"9月-11月","desc":"枫叶由北至南逐渐变红"},"slack":{"month":"12月-2月","desc":"部分地区冬季降雪 较为寒冷"}},"expense":{"beer":"490日元（约26元人民币）","coffee":"350日元（约18元人民币）","hamburger":"650日元（约34元人民币）","shirt":"5500日元（约288元人民币）"},"timeDiffer":"+1","exchangeRate":{"currency":"日元","rate":"16.97"},"socket":{"desc":"100V 50HZ  60HZ"},"bankCard":{"desc":"VISA 、MASTER、银联卡"},"phone":{"desc":"开通了国际漫游的国内手机均可以在日本使用。"},"wifi":"很多酒店的WiFi都要单独收费，但网络端口是普遍配置。带个网络热点出行，就可方便在酒店上网。","budget":{"currency":"日元","level1":" >20k","level2":"15k~20k","level3":"＜15k","level4":""},"capital":"东京","countryId":"135","img":{"socket":["https://ypimg1.youpu.cn/yp/201510/20/113f4015b57278cfac3b890bda8480ce.png","https://ypimg1.youpu.cn/yp/201510/20/14998e7fb352abbc1f575a8e516dc6c8.png"],"backard":["https://ypimg1.youpu.cn/yp/201510/20/2738aa2b05c297434cea462230c30433.png","https://ypimg1.youpu.cn/yp/201510/20/a58ae577b7639105cd6d392ec8f406b4.png","https://ypimg1.youpu.cn/yp/201510/20/3ad47c94d9d3d185a45447089deb1d58.png","https://ypimg1.youpu.cn/yp/201510/20/0333fa59dfd376d8458d026b441a562d.png","https://ypimg1.youpu.cn/yp/201510/20/7c6ecf3d3708203cf0554ec0aad32543.png"],"phone":["https://ypimg1.youpu.cn/yp/201510/20/59e4d9a2ae21db71604ad1caa694d0d9.png","https://ypimg1.youpu.cn/yp/201510/20/2b7fd529e88fae796ca67926a9d87c18.png","https://ypimg1.youpu.cn/yp/201510/20/a456fb407495124ab059fde4713ee50c.png"]},"placePic":"https://static.youpu.cn/mobile/ypphone2014/images/place/135.png","populationPic":"https://static.youpu.cn/mobile/ypphone2014/images/v2/yx/002.png","temperaturePic":"https://static.youpu.cn/mobile/ypphone2014/images/impress/135.png"}}
         * visa_info : {"name":"签证指南","content":"<section class=\"raidersBox itemMenuBox\"><div class=\"raidersItem\"><div class=\"raidersBd\"><div class=\"raidersList\"><section class=\"raidersBox itemMenuBox\"><div class=\"raidersItem\"><div class=\"raidersBd\"><div class=\"raidersList\"><div class=\"pr raidersMod\"><em class=\"pa arrowIcon\"><\/em><span class=\"\">签证政策<\/span><\/div><div class=\"openCont\"><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">中国游客赴日本旅行分为单次签证、三年多次签以及五年多次签。<\/span><\/p><\/div><\/div><\/div><\/div><\/section><section class=\"raidersBox itemMenuBox\"><div class=\"raidersItem\"><div class=\"raidersBd\"><div class=\"raidersList\"><div class=\"pr raidersMod\"><em class=\"pa arrowIcon\"><\/em><span class=\"\">政策解读<\/span><\/div><div class=\"openCont\"><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">单次签证有效期三个月，最长停留时间15天。三年和五年多次签证首次赴日最多停留15天，再次赴日可停留30天，一年内累计停留最多180天。<\/span><\/p><\/div><\/div><\/div><\/div><\/section><\/div><\/div><\/div><\/section><section class=\"raidersBox itemMenuBox\"><div class=\"raidersItem\"><div class=\"raidersBd\"><div class=\"raidersList\"><div class=\"pr raidersMod\"><em class=\"pa arrowIcon\"><\/em><span class=\"\">签证申请流程<\/span><\/div><div class=\"openCont\"><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">＊日本签证必须委托日方指定的代理机构办理，个人无法自行办理<\/span><\/p><p><br/><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">1. 签证申请者确定赴日目的并拟定行程计划。<\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">2. 登陆日本驻华使领馆官方网站，浏览有关签证申请信息和签证所需材料，下载签证申请表。（如您选择通过游谱办理，下单成功后，客服会告知您接收所需申请表和材料的相关流程。）<\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">3. 选择代理机构办理签证申请，准备签证材料，缴纳签证费和签证服务费。<\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">4. 等候签证申请审核或补充资料。<\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">5. 领取签证。拿到签证后，应及时仔细核对签证上的各项信息是否正确，尤其是签证有效期的起止时间及停留天数是否与所申请的相符，签证上的个人信息如姓名拼写是否正确，如发现任何错误，应及时与使领馆联系。<\/span><\/p><p style=\"text-indent: 2em;\"><a href=\"http://tehui.youpu.cn/product/detail?id=1017633\" target=\"_self\"><span style=\"font-size: 14px;\"><img src=\"http://ypimg.oss-cn-beijing.aliyuncs.com/yp/201603/30/adaae0a88df24cebc5fcd32607aa29b1.png\" title=\"adaae0a88df24cebc5fcd32607aa29b1.png\" alt=\"adaae0a88df24cebc5fcd32607aa29b1.png\" width=\"100\" height=\"29\" border=\"0\" vspace=\"0\" style=\"width: 100px; height: 29px;\"/><\/span><\/a><\/p><p style=\"color: rgb(0, 0, 0); font-family: HannotateSC-W5; line-height: 16px; white-space: normal;\"><br/><\/p><\/div><\/div><\/div><\/div><\/section><section class=\"raidersBox itemMenuBox\"><div class=\"raidersItem\"><div class=\"raidersBd\"><div class=\"raidersList\"><div class=\"pr raidersMod\"><em class=\"pa arrowIcon\"><\/em><span class=\"\">签证领区的划分<\/span><\/div><div class=\"openCont\"><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">*请选择正确的领区，以利于您申请签证<\/span><\/p><p><br/><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">日本驻北京大使馆：受理北京市、天津市、陕西省、山西省、甘肃省、河南省、河北省、 湖北省、湖南省、青海省、新疆维吾尔自治区、宁夏回族自治区、西藏自治区、内蒙古自治区。&nbsp;<\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">日本驻上海总领事馆：受理上海市、安徽省、浙江省、江苏省、江西省。<\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">日本驻香港领事馆：受理香港特别行政区、澳门特别行政区。<\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">日本驻广州总领事馆：受理广东省、海南省、福建省、广西壮族自治区。<\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">日本驻重庆总领事馆：受理重庆市、四川省、贵州省、云南省。<\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">日本驻沈阳总领事馆：受理辽宁省（除大连市）、吉林省、黑龙江省。<\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">日本驻大连办事处：受理大连市。<\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">日本驻青岛总领事馆：受理山东省。<\/span><\/p><\/div><\/div><\/div><\/div><\/section><section class=\"raidersBox\"><div class=\"raidersItem\"><div class=\"raidersHd level_1_group\"><h3>单次签证办理<\/h3><\/div><div class=\"raidersBd\"><div class=\"raidersList\"><div class=\"pr raidersMod\"><em class=\"pa arrowIcon\"><\/em>必要资料<\/div><div class=\"openCont\"><p style=\"text-indent: 2em;\"><strong><span style=\"font-size: 14px;\">个人信息表<\/span><\/strong><span style=\"font-size: 14px;\"> (原件)<\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">\u2022除签名外，其他全部信息需机打填写完整，点击\u201c印刷\u201d后打印，不可手写<\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">\u2022表格为PDF格式,可直接填写编辑,请使用Adobe Reader等软件打开<\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">\u2022A4纸单面打印，左上方需印有二维码标识<\/span><\/p><p><br/><\/p><p style=\"text-indent: 2em;\"><strong><span style=\"font-size: 14px;\">户口本<\/span><\/strong><span style=\"font-size: 14px;\"> (复印件)<\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">\u2022①申请人本人整本户口本信息 ②如是集体户口，提供集体户口首页和本人页 ③如丢失，请开户派出所出具的户籍证明<\/span><\/p><p><br/><\/p><p style=\"text-indent: 2em;\"><strong><span style=\"font-size: 14px;\">身份证<\/span><\/strong><span style=\"font-size: 14px;\"> (复印件)<\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">\u2022正反面信息，16周岁以下如没有，可不提供<\/span><\/p><p><br/><\/p><p style=\"text-indent: 2em;\"><strong><span style=\"font-size: 14px;\">因私护照<\/span><\/strong><span style=\"font-size: 14px;\"> (原件)<\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">\u2022有效期离出发日期应至少还有6个月<\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">\u2022至少有两页完整连续的空白页，不包含备注页<\/span><\/p><p><br/><\/p><p style=\"text-indent: 2em;\"><strong><span style=\"font-size: 14px;\">照片<\/span><\/strong><span style=\"font-size: 14px;\"> (原件)<\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">\u2022近6个月内拍摄彩色照片2张，请在照片反面写上名字<\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">\u2022规格：45mm*45mm 正方形<\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">\u2022白色背景<\/span><\/p><p><br/><\/p><p style=\"text-indent: 2em;\"><strong><span style=\"font-size: 14px;\">婚姻证明<\/span><\/strong><span style=\"font-size: 14px;\"> (复印件)<\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">\u2022①如已婚，需提供结婚证； ②如离异，需提供离婚证或离婚协议证明； ③未婚、丧偶此项无需提供<\/span><\/p><p><br/><\/p><p style=\"text-indent: 2em;\"><strong><span style=\"font-size: 14px;\">暂住证明 <\/span><\/strong><span style=\"font-size: 14px;\">(原件)<\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">\u2022暂住证原件，送签时暂/居住证还在有效期内<\/span><\/p><p><br/><\/p><p style=\"text-indent: 2em;\"><strong><span style=\"font-size: 14px;\">营业执照副本或组织机构代码证<\/span><\/strong><span style=\"font-size: 14px;\"> (复印件)<\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">中文在职证明 (原件)<\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">\u2022注意：使领馆可能会致电单位电话或本人手机，请提交资料后注意接听，如有未接或不属实情况，可能会造成拒签<\/span><\/p><p><br/><\/p><p style=\"text-indent: 2em;\"><strong><span style=\"font-size: 14px;\">工资卡或退休金或活期银行卡对账单<\/span><\/strong><span style=\"font-size: 14px;\"> (原件)<\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">\u2022本人名下近1年交易记录的工资卡或储蓄卡对帐单（盖银行公章），须体现年收入过十万<\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">\u2022最后一笔交易日期在送签日期前30天内<\/span><\/p><p><br/><\/p><p style=\"text-indent: 2em;\"><strong><span style=\"font-size: 14px;\">全程交通预订单<\/span><\/strong><span style=\"font-size: 14px;\"> (原件)<\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">\u2022全程机票预订单，签证被签发前勿先行支付机票费用，以免产生损失<\/span><\/p><p><br/><\/p><p style=\"text-indent: 2em;\"><strong><span style=\"font-size: 14px;\">全程酒店预订单<\/span><\/strong><span style=\"font-size: 14px;\"> (原件)<\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">\u2022入住日期覆盖境外停留的全部天数，清晰显示入住人姓名全称、酒店名称、酒店地址及酒店电话，中文或日文<\/span><\/p><p><br/><\/p><p style=\"text-indent: 2em;\"><strong><span style=\"font-size: 14px;\">承诺书<\/span><\/strong><span style=\"font-size: 14px;\"> (原件)<\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">\u2022所有信息填写完整，本人亲笔签名<\/span><\/p><p style=\"text-indent: 2em; text-align: right;\"><a href=\"http://tehui.youpu.cn/product/detail?id=1017633\" target=\"_self\"><span style=\"font-size: 14px;\"><img src=\"http://ypimg.oss-cn-beijing.aliyuncs.com/yp/201603/30/400baa4922d8ebdb0b295db310fc8ce1.png\" title=\"400baa4922d8ebdb0b295db310fc8ce1.png\" alt=\"400baa4922d8ebdb0b295db310fc8ce1.png\" width=\"100\" height=\"29\" border=\"0\" vspace=\"0\" style=\"width: 100px; height: 29px;\"/><\/span><\/a><\/p><\/div><\/div><\/div><div class=\"raidersBd\"><div class=\"raidersList\"><div class=\"pr raidersMod\"><em class=\"pa arrowIcon\"><\/em>可选资料<\/div><div class=\"openCont\"><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">*可选资料可视个人实际情况提供，提供越多越有利于出签<\/span><\/p><p><br/><\/p><p style=\"text-indent: 2em;\"><strong><span style=\"font-size: 14px;\">房产证明<\/span><\/strong><span style=\"font-size: 14px;\"> (复印件)<\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">\u2022如有可提供，房产证或购房发票任选其一<\/span><\/p><p><br/><\/p><p style=\"text-indent: 2em;\"><strong><span style=\"font-size: 14px;\">其他资产证明<\/span><\/strong><span style=\"font-size: 14px;\"> (原件)<\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">\u2022信用卡对账单、股票或基金的账户情况等，但此类文件不能代替银行对账单<\/span><\/p><p><br/><\/p><p style=\"text-indent: 2em;\"><strong><span style=\"font-size: 14px;\">汽车行驶证 <\/span><\/strong><span style=\"font-size: 14px;\">(复印件)<\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">\u2022机动车行驶证或机动车登记证任选其一<\/span><\/p><\/div><\/div><\/div><div class=\"raidersBd\"><div class=\"raidersList\"><div class=\"pr raidersMod\"><em class=\"pa arrowIcon\"><\/em>未成年<\/div><div class=\"openCont\"><p style=\"text-indent: 2em;\"><strong><span style=\"font-size: 14px;\">出生证或出生医学证明<\/span><\/strong><span style=\"font-size: 14px;\"> (复印件)<\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">\u202218周岁及以下申请人提供<\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">\u2022内容涵盖申请人名字及父母信息<\/span><\/p><p><br/><\/p><p style=\"text-indent: 2em;\"><strong><span style=\"font-size: 14px;\">身份证 <\/span><\/strong><span style=\"font-size: 14px;\">(复印件)<\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">\u2022正反面信息，16周岁以下如没有，可不提供<\/span><\/p><p><br/><\/p><p style=\"text-indent: 2em;\"><strong><span style=\"font-size: 14px;\">中文在校证明<\/span><\/strong><span style=\"font-size: 14px;\"> (原件)<\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">\u2022①使用学校抬头纸打印，盖学校公章，班主任或学校领导签字 ②内容需包括：学校名称，地址和电话，学生所学专业或所在班级，学校假期时间，班主任或学校领导名字及职务<\/span><\/p><\/div><\/div><\/div><\/div><\/section><section class=\"raidersBox itemMenuBox\"><div class=\"raidersItem\"><div class=\"raidersBd\"><div class=\"raidersList\"><div class=\"pr raidersMod\"><em class=\"pa arrowIcon\"><\/em><span class=\"\">三年多次签证<\/span><\/div><div class=\"openCont\"><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">*旅行者必须在冲绳或东北三县至少停留一晚。<\/span><\/p><p><br/><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">激活条件：使馆要求至少在冲绳或东北三县（宫城县、岩手县、福岛县）的酒店住宿（需要注意的是酒店必须三星级以上，民宿、公寓等不符合要求）一晚，出签后不得取消住宿，退房时需向酒店索取住宿证明，回国后用于激活签证。<\/span><\/p><p><br/><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">除必备资料外还需要提供<\/span><\/p><p><br/><\/p><p style=\"text-indent: 2em;\"><strong><span style=\"font-size: 14px;\">资产证明原件<\/span><\/strong><\/p><p><br/><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">\u2022税前年收入在20万以上，提供本人名下近1年银行出具的工资明细（项目栏为\u201c工资\u201d）<\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">\u2022或近12个月个人完税证明（注明年薪）（盖税务局红章）<\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">\u2022若提供的银行明细单中无法明确显示\u201c工资\u201d字样，则必须提供合格完税证明<\/span><\/p><p style=\"text-indent: 2em; text-align: right;\"><br/><\/p><p style=\"text-indent: 2em; text-align: right;\"><a href=\"http://tehui.youpu.cn/product/detail?id=1017748\" target=\"_self\"><img src=\"http://ypimg.oss-cn-beijing.aliyuncs.com/yp/201603/30/30a41e01b816cba93b44e33ee3ebb8cb.png\" title=\"30a41e01b816cba93b44e33ee3ebb8cb.png\" alt=\"30a41e01b816cba93b44e33ee3ebb8cb.png\" width=\"100\" height=\"29\" border=\"0\" vspace=\"0\" style=\"width: 100px; height: 29px;\"/><\/a><\/p><\/div><\/div><\/div><\/div><\/section><section class=\"raidersBox itemMenuBox\"><div class=\"raidersItem\"><div class=\"raidersBd\"><div class=\"raidersList\"><div class=\"pr raidersMod\"><em class=\"pa arrowIcon\"><\/em><span class=\"\">五年多次签证<\/span><\/div><div class=\"openCont\"><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">激活条件：需要按照申请时提交的行程完成旅行，退房时向酒店索取住宿证明（全程酒店），回国后将护照首页、签证页、出入境章页、全程酒店住宿证明提交给旅行社，用于激活签证。<\/span><\/p><p><br/><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">除必备资料外还需要提供<\/span><\/p><p><br/><\/p><p style=\"text-indent: 2em;\"><strong><span style=\"font-size: 14px;\">资产证明原件<\/span><\/strong><\/p><p><br/><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">\u2022税前年收入在50万以上，提供本人名下近1年银行出具的工资明细（项目栏为\u201c工资\u201d）<\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">\u2022或近12个月个人完税证明（注明年薪）（盖税务局红章）<\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">\u2022若提供的银行明细单中<\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\"><br/><\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\"><a href=\"http://m.youpu.cn\" target=\"_self\"><\/a><\/span><\/p><p style=\"text-indent: 2em; text-align: right;\"><a href=\"http://tehui.youpu.cn/product/detail?id=1017760\" target=\"_self\"><img src=\"http://ypimg.oss-cn-beijing.aliyuncs.com/yp/201603/30/d53440d315c426660be895535e9d80bb.png\" title=\"d53440d315c426660be895535e9d80bb.png\" alt=\"d53440d315c426660be895535e9d80bb.png\" width=\"100\" height=\"29\" border=\"0\" vspace=\"0\" style=\"width: 100px; height: 29px;\"/><\/a><\/p><\/div><\/div><\/div><\/div><\/section><section class=\"raidersBox itemMenuBox\"><div class=\"raidersItem\"><div class=\"raidersBd\"><div class=\"raidersList\"><div class=\"pr raidersMod\"><em class=\"pa arrowIcon\"><\/em><span class=\"\">入境卡填写<\/span><\/div><div class=\"openCont\"><img src=\"http://ypimg.oss-cn-beijing.aliyuncs.com/yp/201603/02/6cd11dfbd201f77583e617103cccbb4f.jpg\" title=\"6cd11dfbd201f77583e617103cccbb4f.jpg\" alt=\"20111209_07dab9ed27f8280e9555YK8lYbzIS2Gz.jpg\"/><\/div><\/div><\/div><\/div><\/section><p><br/><\/p>"}
         * impress_overview : {"customs":{"cn_type":"风俗","content":"<p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">1、若在某个地方入口处标有\u201c土足禁止\u201d的提示表示要脱鞋入内；进入宾馆的房间，到日本人家里做客都要换拖鞋，去厕所有时还要换第二遍拖鞋。因此，袜子是否有洞，需要提前注意一下。去的地方多建议不穿系带的鞋。<\/span><\/p><p><br/><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">2、日本对礼仪很讲究，平时注意礼貌，吃饭注意坐姿。<\/span><\/p><p><br/><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">3、入温泉池之前要清洗身体，浴巾毛巾不可放到温泉水里。除此之外，有一些公共温泉不允许带纹身的客人进入。<\/span><\/p><p><br/><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">4、日本自来水可以直接饮用，但要调整到凉水再喝。<\/span><\/p><p><br/><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">5、在日本餐馆吃饭，基本上没有提供热水的习惯，都是凉水。<\/span><\/p><p><br/><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">6、如果是榻榻米的客房，按照日本人的习惯，被褥入睡前铺开，早上起床后叠好收进壁柜。一般的旅馆工作人员会在您就晚餐或到大澡堂入浴时把被褥铺好。<\/span><\/p><p><br/><\/p>"},"healthy":{"cn_type":"健康","content":"<p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">1、手握寿司和刺身大部分都是生肉做成的，大神级寿司店一定按预约的时间过去，之前有过中国客人迟到并且不吃生食跟店方闹出矛盾的先例。<\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\"><br/><\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px; text-indent: 2em;\">2、寿司、刺身等海产品很新鲜，但不要吃太多，虽然当地可以很方便的购买到胃药。<\/span><br/><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\"><br/><\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">3、当地没有区域性传染病，可以放心前往。<\/span><\/p><p><br/><\/p>"},"security":{"cn_type":"安全","content":"<p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">1、日本是个多地震的国家，如果遇到地震，一定要听从周边人员的指令，及时疏散到安全地带。<\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\"><br/><\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">2、出门要带伞，日本是海洋性气候，晴雨不定，最好带长把伞。因为在日本，多数商店门前，甚至私家住宅门旁，都有一个伞筐，把儿长的伞可以立在筐内，而折叠伞是不能放入伞筐中的。<\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\"><br/><\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">3、酒后骑自行车在某些地方是违法的，如京都。处罚很严厉。<\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\"><br/><\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">4、不要随意触摸民房，日本老宅子很多都拥有悠久的历史，很可能一个不小心就损伤了国宝级的财产。<\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\"><br/><\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">5、紧急电话<\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">&nbsp; &nbsp; &nbsp;火警、急救：119<\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">&nbsp; &nbsp; &nbsp;报警电话：110<\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">&nbsp; &nbsp; &nbsp;中国驻日本大使馆<\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">&nbsp; &nbsp; &nbsp;联系电话：03-3403-3388<\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">&nbsp; &nbsp; &nbsp;&nbsp;<\/span><\/p><p><br/><\/p>"},"festival":{"cn_type":"节庆","content":"<p style=\"text-indent: 0em;\"><span style=\"color: rgb(227, 108, 9);\"><strong><span style=\"font-size: 14px;\"><\/span><\/strong><\/span><\/p><p style=\"text-indent: 2em;\"><br/><\/p><p style=\"text-indent: 2em;\"><span style=\"color: rgb(227, 108, 9);\"><strong><span style=\"font-size: 14px;\">元旦<\/span><\/strong><\/span><span style=\"font-size: 14px; white-space: pre;\"><\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">时间：1月1日<span class=\"Apple-tab-span\" style=\"font-size: 14px; white-space: pre;\"><\/span><\/span><\/p><p><br/><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">从元旦1月1日到1月3日这三天叫做\u201c正月\u201d，是全然不干活的。新年里，大家去参拜神社或到朋友家去拜年、喝酒，吃新年里独特的美味佳肴。孩子们玩日本式纸牌、放风筝、拍羽毛毽子。在家门口还会插上松枝与稻草制的花彩，意思是\u201c插上树木迎接神灵降临\u201d。届时，全家还会去神社祈福，互相拜年。<\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\"><br/><\/span><\/p><p>&nbsp;<\/p><p style=\"text-indent: 2em;\"><span style=\"color: rgb(227, 108, 9);\"><strong><span style=\"font-size: 14px;\">偶人节 (\u201c雏祭\u201d)<\/span><\/strong><\/span><span style=\"font-size: 14px; white-space: pre;\"><\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">时间：3月3日<span class=\"Apple-tab-span\" style=\"font-size: 14px; white-space: pre;\"><\/span><\/span><\/p><p><img src=\"https://ypimg1.youpu.cn/upload/youpu/20141211/1418287329929518.jpg\" title=\"1418287329929518.jpg\" alt=\"偶人节.jpg\"/><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">一年一度的为年轻女孩乞求幸福和健康成长的日子。在这个日子，家家户户摆放着穿着传统宫廷装束的玩具娃娃和桃花，还有供奉一种钻石形状的米饼和干米团。玩偶节来源于古代的关于仪式涤罪的信念。人们相信，人类的罪行和污秽可以通过在河边的净化仪式中冲洗干净。后来，人们在这些仪式上使用纸做的玩偶；在江户时期之后，这些玩偶被设计成现在的玩具的样子。<\/span><\/p><p><br/><\/p><p style=\"text-indent: 2em;\"><span style=\"color: rgb(227, 108, 9);\"><strong><span style=\"font-size: 14px;\">樱花节<\/span><\/strong><\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">时间：3月15日至4月15日<span class=\"Apple-tab-span\" style=\"font-size: 14px; white-space: pre;\"><\/span><\/span><\/p><p><img src=\"https://ypimg1.youpu.cn/upload/youpu/20141211/1418287386496299.jpg\" title=\"1418287386496299.jpg\" alt=\"樱花节.jpg\"/><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">日本政府把每年的3月15日至4月15日定为\u201c樱花节\u201d。在东京最大的公园上野公园中，每年此时都要举办\u201c樱花祭\u201d。从早到晚，不分昼夜，欣赏\u201c夜樱\u201d的美丽。花季到来时，粉红色的花朵堆云聚雾，绵延不断，景象十分壮观。<\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"color: rgb(227, 108, 9);\"><strong><span style=\"font-size: 14px;\"><br/><\/span><\/strong><\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"color: rgb(227, 108, 9);\"><strong><span style=\"font-size: 14px;\"><br/><\/span><\/strong><\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"color: rgb(227, 108, 9);\"><strong><span style=\"font-size: 14px;\">神田祭<span class=\"Apple-tab-span\" style=\"font-size: 14px; white-space: pre;\"><\/span><\/span><\/strong><\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">时间：距5月15日最近的周六和周日<\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\"><br/><\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"white-space: pre; font-size: 14px;\"><\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">神田神社的祭会从江户时代开始即为江户两大祭会之一。以每年的5月1日为主举行盛大的祭会。此祭会期间，是以太鼓嘉年华会及抬神轿，在街道上缓步前进的方式将气氛抬升到高潮。主要的大祭会则为隔年举行。<\/span><\/p><p><br/><\/p><p style=\"text-indent: 2em;\"><span style=\"color: rgb(227, 108, 9);\"><strong><span style=\"font-size: 14px;\">浅草三社祭<\/span><\/strong><\/span><span style=\"font-size: 14px; white-space: pre;\"><\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">时间：5月第三周的周五、周六以及周日<\/span><\/p><p><img src=\"https://ypimg1.youpu.cn/upload/youpu/20141211/1418287428882596.jpg\" title=\"1418287428882596.jpg\" alt=\"浅草三社祭.jpg\"/><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">保留了传统文化的街区举办的东京最热门的神轿节庆活动，在浅草神社举办。在神社可以观赏\u201c编木（Binzasara）舞\u201d。舞者身着华丽衣装，手持名为\u201cBinzasara\u201d的乐器翩翩起舞，祈求丰收和子孙后代的繁荣。\u201c编木\u201d演奏像手风琴，一开一合，非常有可观性。<\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\"><br/><\/span><\/p><p><\/p><p style=\"text-indent: 2em;\"><span style=\"color: rgb(227, 108, 9);\"><strong><span style=\"font-size: 14px;\">山王祭<span class=\"Apple-tab-span\" style=\"font-size: 14px; white-space: pre;\"><\/span><\/span><\/strong><\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">时间：6月10-16日<span class=\"Apple-tab-span\" style=\"font-size: 14px; white-space: pre;\"><\/span><\/span><\/p><p><br/><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">山王祭，是东京都千代田区的日枝神社的祭礼，正式名称是\u201c日枝神社大祭\u201d，是江户时代最高级别的\u201c天下祭\u201d之一。山因祭会是隔年举行的关系，当轮为主祭会年时，还会与其他地区，如大田乐、民族舞蹈大会等相关的活动一起举行，热闹非凡。<\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\"><br/><\/span><\/p><p><\/p><p style=\"text-indent: 2em;\"><span style=\"color: rgb(227, 108, 9);\"><strong><span style=\"font-size: 14px;\">七夕节<\/span><\/strong><\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">时间：7月7日<\/span><\/p><p><img src=\"https://ypimg1.youpu.cn/upload/youpu/20141211/1418287486531158.jpg\" title=\"1418287486531158.jpg\" alt=\"七夕节.jpg\"/><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">七夕节被认为是从中国传去的风俗同日本固有习惯相结合的节日。七夕节在日本民俗活动中具有很重要的地位，日本人将其称为\u201c夏天的风物诗\u201d。这一天不单是牛郎与织女的情人节，人们会将写有各种各样的愿望的彩纸贴在竹枝上，以求愿望得以实现。<\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\"><br/><\/span><\/p><p><\/p><p style=\"text-indent: 2em;\"><span style=\"color: rgb(227, 108, 9);\"><strong><span style=\"font-size: 14px;\">盂兰盆节<\/span><\/strong><\/span><span style=\"font-size: 14px; white-space: pre;\"><\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">时间：7月13-15日或者8月13-15日<span class=\"Apple-tab-span\" style=\"font-size: 14px; white-space: pre;\"><\/span><\/span><\/p><p><br/><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">一年一度的迎接和安慰祖先亡灵的日子。据说，祖先的亡灵在这个日子来到家里。按照传统，在阴历7月的第17日纪念。13日那天人们点火欢迎祖先的亡灵。到16日，人们点燃送别火，送祖先的亡灵回去。节日期间，公司商店都会放假，因为在异地工作的人都会赶回家与家人团聚，那时候的交通很是繁忙。<\/span><\/p><p><br/><\/p><p style=\"text-indent: 2em;\"><span style=\"color: rgb(227, 108, 9);\"><strong><span style=\"font-size: 14px;\">深川八幡祭<\/span><\/strong><\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">时间：大约在接近8月15日左右的周六、日举行<span class=\"Apple-tab-span\" style=\"font-size: 14px; white-space: pre;\"><\/span><\/span><\/p><p><br/><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">深川八幡祭是一个结合日本神舆巡游及泼水玩意的狂野庆典，由早上七时许开始，全长约八公里的巡游路两旁，便有各式各样的围观者肆意向巡游队伍泼水，一直到下午五时许才结束。作为旁观者，若不湿身，实在是无比的幸运呢﹗<\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\"><br/><\/span><\/p><p><\/p><p style=\"text-indent: 2em;\"><span style=\"color: rgb(227, 108, 9);\"><strong><span style=\"font-size: 14px;\">赏月<\/span><\/strong><\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">时间：阴历8月15日和9月13日<\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\"><br/><\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"white-space: pre; font-size: 14px;\"><\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">日本的赏月习俗来源于中国，传到日本后，当地开始出现边赏月边举行宴会的风俗习惯，被称为\u201c观月宴\u201d。日本人没有吃月饼的习俗，阴历8月15日和9月13日夜晚，日本人在赏月的时候吃的是一种用糯米做成的白色团子，叫江米团子，又称月见团子。<\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\"><br/><\/span><\/p><p><\/p><p style=\"text-indent: 2em;\"><span style=\"color: rgb(227, 108, 9);\"><strong><span style=\"font-size: 14px;\">东京国际电影节<\/span><\/strong><\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">时间：每年10月23-31日左右，为期一周<\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\"><br/><\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"white-space: pre; font-size: 14px;\"><\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">国际电影制作者联盟公认的国际电影节。参赛作品只限长篇电影。<\/span><\/p><p style=\"text-indent: 2em;\"><\/p><p style=\"text-indent: 2em;\"><span style=\"color: rgb(227, 108, 9);\"><strong><span style=\"font-size: 14px;\"><br/><\/span><\/strong><\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"color: rgb(227, 108, 9);\"><strong><span style=\"font-size: 14px;\"><br/><\/span><\/strong><\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"color: rgb(227, 108, 9);\"><strong><span style=\"font-size: 14px;\">七五三<span class=\"Apple-tab-span\" style=\"font-size: 14px; white-space: pre;\"><\/span><\/span><\/strong><\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">时间：11月15日<span class=\"Apple-tab-span\" style=\"font-size: 14px; white-space: pre;\"><\/span><\/span><\/p><p><img src=\"https://ypimg1.youpu.cn/upload/youpu/20141211/1418287562410830.jpg\" title=\"1418287562410830.jpg\" alt=\"七五三节.jpg\"/><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">在日本习俗里，3岁、5岁和7岁是孩子特别幸运的三个年纪，所以每逢11月15日，3岁和5岁的男孩、3岁和7岁的女孩都会穿上鲜艳的和服，跟父母到神社参拜，祈愿身体健康，顺利成长。但是现在，孩子们也穿别的服装。这天，孩子的父母亲会到商店里购买一种叫做千岁糖的糖果给孩子，据说此种糖果为孩子带来长寿。全家热热闹闹，吃红豆米饭和带头尾的鳊鱼。<\/span><\/p><p><br/><\/p>"}}
         */

        private String place_picture;
        private OverviewInfoBean overview_info;
        private VisaInfoBean visa_info;
        private ImpressOverviewBean impress_overview;

        public String getPlace_picture(boolean offline, int width, int height) {
            if (place_picture == null||place_picture.equals(""))
                return "";
            if (!offline) {
                int begin = place_picture.indexOf("/", 8);
                int end = place_picture.indexOf("@");
                begin = begin == -1 ? 0 : begin;
                end = end == -1 ? place_picture.length() : end;
                String string = place_picture.substring(begin, end);
                return Constans.PIC_HOST + string +
                        "?imageMogr2/thumbnail/!" + width + "x" + height + "r/gravity/Center/crop/" + width + "x" + height + "/quality/50";
            } else {
                int begin = place_picture.lastIndexOf("/");
                if (place_picture.contains("@")) {
                    return place_picture.substring(begin + 1, place_picture.indexOf("@"));
                } else {
                    return place_picture.substring(begin + 1, place_picture.length());
                }
            }
        }

        public void setPlace_picture(String place_picture) {
            this.place_picture = place_picture;
        }

        public OverviewInfoBean getOverview_info() {
            return overview_info;
        }

        public void setOverview_info(OverviewInfoBean overview_info) {
            this.overview_info = overview_info;
        }

        public VisaInfoBean getVisa_info() {
            return visa_info;
        }

        public void setVisa_info(VisaInfoBean visa_info) {
            this.visa_info = visa_info;
        }

        public ImpressOverviewBean getImpress_overview() {
            return impress_overview;
        }

        public void setImpress_overview(ImpressOverviewBean impress_overview) {
            this.impress_overview = impress_overview;
        }

        public static class OverviewInfoBean {
            /**
             * name : 国家概览
             * content : {"placeId":"135","placeCnName":"日本","distance":"377960","population":"12708","populationDesc":"大和族 98.5% 朝鲜族 0.5% 汉族 0.4% 其他民族 0.6%","language":[{"cnName":"日文","enName":"Japanese","value":"100"},{"cnName":"","enName":"","value":"0"}],"travelTime":{"busy":{"month":"3月-5月","desc":"樱花由南至北逐渐绽放 最佳赏樱季节"},"common":{"month":"9月-11月","desc":"枫叶由北至南逐渐变红"},"slack":{"month":"12月-2月","desc":"部分地区冬季降雪 较为寒冷"}},"expense":{"beer":"490日元（约26元人民币）","coffee":"350日元（约18元人民币）","hamburger":"650日元（约34元人民币）","shirt":"5500日元（约288元人民币）"},"timeDiffer":"+1","exchangeRate":{"currency":"日元","rate":"16.97"},"socket":{"desc":"100V 50HZ  60HZ"},"bankCard":{"desc":"VISA 、MASTER、银联卡"},"phone":{"desc":"开通了国际漫游的国内手机均可以在日本使用。"},"wifi":"很多酒店的WiFi都要单独收费，但网络端口是普遍配置。带个网络热点出行，就可方便在酒店上网。","budget":{"currency":"日元","level1":" >20k","level2":"15k~20k","level3":"＜15k","level4":""},"capital":"东京","countryId":"135","img":{"socket":["https://ypimg1.youpu.cn/yp/201510/20/113f4015b57278cfac3b890bda8480ce.png","https://ypimg1.youpu.cn/yp/201510/20/14998e7fb352abbc1f575a8e516dc6c8.png"],"backard":["https://ypimg1.youpu.cn/yp/201510/20/2738aa2b05c297434cea462230c30433.png","https://ypimg1.youpu.cn/yp/201510/20/a58ae577b7639105cd6d392ec8f406b4.png","https://ypimg1.youpu.cn/yp/201510/20/3ad47c94d9d3d185a45447089deb1d58.png","https://ypimg1.youpu.cn/yp/201510/20/0333fa59dfd376d8458d026b441a562d.png","https://ypimg1.youpu.cn/yp/201510/20/7c6ecf3d3708203cf0554ec0aad32543.png"],"phone":["https://ypimg1.youpu.cn/yp/201510/20/59e4d9a2ae21db71604ad1caa694d0d9.png","https://ypimg1.youpu.cn/yp/201510/20/2b7fd529e88fae796ca67926a9d87c18.png","https://ypimg1.youpu.cn/yp/201510/20/a456fb407495124ab059fde4713ee50c.png"]},"placePic":"https://static.youpu.cn/mobile/ypphone2014/images/place/135.png","populationPic":"https://static.youpu.cn/mobile/ypphone2014/images/v2/yx/002.png","temperaturePic":"https://static.youpu.cn/mobile/ypphone2014/images/impress/135.png"}
             */

            private String name;
            private ContentBean content;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public ContentBean getContent() {
                return content;
            }

            public void setContent(ContentBean content) {
                this.content = content;
            }

            public static class ContentBean {
                /**
                 * placeId : 135
                 * placeCnName : 日本
                 * distance : 377960
                 * population : 12708
                 * populationDesc : 大和族 98.5% 朝鲜族 0.5% 汉族 0.4% 其他民族 0.6%
                 * language : [{"cnName":"日文","enName":"Japanese","value":"100"},{"cnName":"","enName":"","value":"0"}]
                 * travelTime : {"busy":{"month":"3月-5月","desc":"樱花由南至北逐渐绽放 最佳赏樱季节"},"common":{"month":"9月-11月","desc":"枫叶由北至南逐渐变红"},"slack":{"month":"12月-2月","desc":"部分地区冬季降雪 较为寒冷"}}
                 * expense : {"beer":"490日元（约26元人民币）","coffee":"350日元（约18元人民币）","hamburger":"650日元（约34元人民币）","shirt":"5500日元（约288元人民币）"}
                 * timeDiffer : +1
                 * exchangeRate : {"currency":"日元","rate":"16.97"}
                 * socket : {"desc":"100V 50HZ  60HZ"}
                 * bankCard : {"desc":"VISA 、MASTER、银联卡"}
                 * phone : {"desc":"开通了国际漫游的国内手机均可以在日本使用。"}
                 * wifi : 很多酒店的WiFi都要单独收费，但网络端口是普遍配置。带个网络热点出行，就可方便在酒店上网。
                 * budget : {"currency":"日元","level1":" >20k","level2":"15k~20k","level3":"＜15k","level4":""}
                 * capital : 东京
                 * countryId : 135
                 * img : {"socket":["https://ypimg1.youpu.cn/yp/201510/20/113f4015b57278cfac3b890bda8480ce.png","https://ypimg1.youpu.cn/yp/201510/20/14998e7fb352abbc1f575a8e516dc6c8.png"],"backard":["https://ypimg1.youpu.cn/yp/201510/20/2738aa2b05c297434cea462230c30433.png","https://ypimg1.youpu.cn/yp/201510/20/a58ae577b7639105cd6d392ec8f406b4.png","https://ypimg1.youpu.cn/yp/201510/20/3ad47c94d9d3d185a45447089deb1d58.png","https://ypimg1.youpu.cn/yp/201510/20/0333fa59dfd376d8458d026b441a562d.png","https://ypimg1.youpu.cn/yp/201510/20/7c6ecf3d3708203cf0554ec0aad32543.png"],"phone":["https://ypimg1.youpu.cn/yp/201510/20/59e4d9a2ae21db71604ad1caa694d0d9.png","https://ypimg1.youpu.cn/yp/201510/20/2b7fd529e88fae796ca67926a9d87c18.png","https://ypimg1.youpu.cn/yp/201510/20/a456fb407495124ab059fde4713ee50c.png"]}
                 * placePic : https://static.youpu.cn/mobile/ypphone2014/images/place/135.png
                 * populationPic : https://static.youpu.cn/mobile/ypphone2014/images/v2/yx/002.png
                 * temperaturePic : https://static.youpu.cn/mobile/ypphone2014/images/impress/135.png
                 */

                private String placeId;
                private String placeCnName;
                private String distance;
                private String population;
                private String populationDesc;
                private TravelTimeBean travelTime;
                private ExpenseBean expense;
                private String timeDiffer;
                private ExchangeRateBean exchangeRate;
                private SocketBean socket;
                private BankCardBean bankCard;
                private PhoneBean phone;
                private String wifi;
                private BudgetBean budget;
                private String capital;
                private String countryId;
                private ImgBean img;
                private String placePic;
                private String populationPic;
                private String temperaturePic;
                private List<LanguageBean> language;

                public String getPlaceId() {
                    return placeId;
                }

                public void setPlaceId(String placeId) {
                    this.placeId = placeId;
                }

                public String getPlaceCnName() {
                    return placeCnName;
                }

                public void setPlaceCnName(String placeCnName) {
                    this.placeCnName = placeCnName;
                }

                public String getDistance() {
                    return distance;
                }

                public void setDistance(String distance) {
                    this.distance = distance;
                }

                public String getPopulation() {
                    return population;
                }

                public void setPopulation(String population) {
                    this.population = population;
                }

                public String getPopulationDesc() {
                    return populationDesc;
                }

                public void setPopulationDesc(String populationDesc) {
                    this.populationDesc = populationDesc;
                }

                public TravelTimeBean getTravelTime() {
                    return travelTime;
                }

                public void setTravelTime(TravelTimeBean travelTime) {
                    this.travelTime = travelTime;
                }

                public ExpenseBean getExpense() {
                    return expense;
                }

                public void setExpense(ExpenseBean expense) {
                    this.expense = expense;
                }

                public String getTimeDiffer() {
                    return timeDiffer;
                }

                public void setTimeDiffer(String timeDiffer) {
                    this.timeDiffer = timeDiffer;
                }

                public ExchangeRateBean getExchangeRate() {
                    return exchangeRate;
                }

                public void setExchangeRate(ExchangeRateBean exchangeRate) {
                    this.exchangeRate = exchangeRate;
                }

                public SocketBean getSocket() {
                    return socket;
                }

                public void setSocket(SocketBean socket) {
                    this.socket = socket;
                }

                public BankCardBean getBankCard() {
                    return bankCard;
                }

                public void setBankCard(BankCardBean bankCard) {
                    this.bankCard = bankCard;
                }

                public PhoneBean getPhone() {
                    return phone;
                }

                public void setPhone(PhoneBean phone) {
                    this.phone = phone;
                }

                public String getWifi() {
                    return wifi;
                }

                public void setWifi(String wifi) {
                    this.wifi = wifi;
                }

                public BudgetBean getBudget() {
                    return budget;
                }

                public void setBudget(BudgetBean budget) {
                    this.budget = budget;
                }

                public String getCapital() {
                    return capital;
                }

                public void setCapital(String capital) {
                    this.capital = capital;
                }

                public String getCountryId() {
                    return countryId;
                }

                public void setCountryId(String countryId) {
                    this.countryId = countryId;
                }

                public ImgBean getImg() {
                    return img;
                }

                public void setImg(ImgBean img) {
                    this.img = img;
                }

                public String getPlacePic() {
                    return placePic;
                }

                public void setPlacePic(String placePic) {
                    this.placePic = placePic;
                }

                public String getPopulationPic() {
                    return populationPic;
                }

                public void setPopulationPic(String populationPic) {
                    this.populationPic = populationPic;
                }

                public String getTemperaturePic() {
                    return temperaturePic;
                }

                public void setTemperaturePic(String temperaturePic) {
                    this.temperaturePic = temperaturePic;
                }

                public List<LanguageBean> getLanguage() {
                    return language;
                }

                public void setLanguage(List<LanguageBean> language) {
                    this.language = language;
                }

                public static class TravelTimeBean {
                    /**
                     * busy : {"month":"3月-5月","desc":"樱花由南至北逐渐绽放 最佳赏樱季节"}
                     * common : {"month":"9月-11月","desc":"枫叶由北至南逐渐变红"}
                     * slack : {"month":"12月-2月","desc":"部分地区冬季降雪 较为寒冷"}
                     */

                    private BusyBean busy;
                    private CommonBean common;
                    private SlackBean slack;

                    public BusyBean getBusy() {
                        return busy;
                    }

                    public void setBusy(BusyBean busy) {
                        this.busy = busy;
                    }

                    public CommonBean getCommon() {
                        return common;
                    }

                    public void setCommon(CommonBean common) {
                        this.common = common;
                    }

                    public SlackBean getSlack() {
                        return slack;
                    }

                    public void setSlack(SlackBean slack) {
                        this.slack = slack;
                    }

                    public static class BusyBean {
                        /**
                         * month : 3月-5月
                         * desc : 樱花由南至北逐渐绽放 最佳赏樱季节
                         */

                        private String month;
                        private String desc;

                        public String getMonth() {
                            return month;
                        }

                        public void setMonth(String month) {
                            this.month = month;
                        }

                        public String getDesc() {
                            return desc;
                        }

                        public void setDesc(String desc) {
                            this.desc = desc;
                        }
                    }

                    public static class CommonBean {
                        /**
                         * month : 9月-11月
                         * desc : 枫叶由北至南逐渐变红
                         */

                        private String month;
                        private String desc;

                        public String getMonth() {
                            return month;
                        }

                        public void setMonth(String month) {
                            this.month = month;
                        }

                        public String getDesc() {
                            return desc;
                        }

                        public void setDesc(String desc) {
                            this.desc = desc;
                        }
                    }

                    public static class SlackBean {
                        /**
                         * month : 12月-2月
                         * desc : 部分地区冬季降雪 较为寒冷
                         */

                        private String month;
                        private String desc;

                        public String getMonth() {
                            return month;
                        }

                        public void setMonth(String month) {
                            this.month = month;
                        }

                        public String getDesc() {
                            return desc;
                        }

                        public void setDesc(String desc) {
                            this.desc = desc;
                        }
                    }
                }

                public static class ExpenseBean {
                    /**
                     * beer : 490日元（约26元人民币）
                     * coffee : 350日元（约18元人民币）
                     * hamburger : 650日元（约34元人民币）
                     * shirt : 5500日元（约288元人民币）
                     */

                    private String beer;
                    private String coffee;
                    private String hamburger;
                    private String shirt;

                    public String getBeer() {
                        return beer;
                    }

                    public void setBeer(String beer) {
                        this.beer = beer;
                    }

                    public String getCoffee() {
                        return coffee;
                    }

                    public void setCoffee(String coffee) {
                        this.coffee = coffee;
                    }

                    public String getHamburger() {
                        return hamburger;
                    }

                    public void setHamburger(String hamburger) {
                        this.hamburger = hamburger;
                    }

                    public String getShirt() {
                        return shirt;
                    }

                    public void setShirt(String shirt) {
                        this.shirt = shirt;
                    }
                }

                public static class ExchangeRateBean {
                    /**
                     * currency : 日元
                     * rate : 16.97
                     */

                    private String currency;
                    private String rate;

                    public String getCurrency() {
                        return currency;
                    }

                    public void setCurrency(String currency) {
                        this.currency = currency;
                    }

                    public String getRate() {
                        return rate;
                    }

                    public void setRate(String rate) {
                        this.rate = rate;
                    }
                }

                public static class SocketBean {
                    /**
                     * desc : 100V 50HZ  60HZ
                     */

                    private String desc;

                    public String getDesc() {
                        return desc;
                    }

                    public void setDesc(String desc) {
                        this.desc = desc;
                    }
                }

                public static class BankCardBean {
                    /**
                     * desc : VISA 、MASTER、银联卡
                     */

                    private String desc;

                    public String getDesc() {
                        return desc;
                    }

                    public void setDesc(String desc) {
                        this.desc = desc;
                    }
                }

                public static class PhoneBean {
                    /**
                     * desc : 开通了国际漫游的国内手机均可以在日本使用。
                     */

                    private String desc;

                    public String getDesc() {
                        return desc;
                    }

                    public void setDesc(String desc) {
                        this.desc = desc;
                    }
                }

                public static class BudgetBean {
                    /**
                     * currency : 日元
                     * level1 :  >20k
                     * level2 : 15k~20k
                     * level3 : ＜15k
                     * level4 :
                     */

                    private String currency;
                    private String level1;
                    private String level2;
                    private String level3;
                    private String level4;

                    public String getCurrency() {
                        return currency;
                    }

                    public void setCurrency(String currency) {
                        this.currency = currency;
                    }

                    public String getLevel1() {
                        return level1;
                    }

                    public void setLevel1(String level1) {
                        this.level1 = level1;
                    }

                    public String getLevel2() {
                        return level2;
                    }

                    public void setLevel2(String level2) {
                        this.level2 = level2;
                    }

                    public String getLevel3() {
                        return level3;
                    }

                    public void setLevel3(String level3) {
                        this.level3 = level3;
                    }

                    public String getLevel4() {
                        return level4;
                    }

                    public void setLevel4(String level4) {
                        this.level4 = level4;
                    }
                }

                public static class ImgBean {
                    private List<String> socket;
                    private List<String> backard;
                    private List<String> phone;

                    public List<String> getSocket() {
                        return socket;
                    }

                    public void setSocket(List<String> socket) {
                        this.socket = socket;
                    }

                    public List<String> getBackard() {
                        return backard;
                    }

                    public void setBackard(List<String> backard) {
                        this.backard = backard;
                    }

                    public List<String> getPhone() {
                        return phone;
                    }

                    public void setPhone(List<String> phone) {
                        this.phone = phone;
                    }
                }

                public static class LanguageBean {
                    /**
                     * cnName : 日文
                     * enName : Japanese
                     * value : 100
                     */

                    private String cnName;
                    private String enName;
                    private String value;

                    public String getCnName() {
                        return cnName;
                    }

                    public void setCnName(String cnName) {
                        this.cnName = cnName;
                    }

                    public String getEnName() {
                        return enName;
                    }

                    public void setEnName(String enName) {
                        this.enName = enName;
                    }

                    public String getValue() {
                        return value;
                    }

                    public void setValue(String value) {
                        this.value = value;
                    }
                }
            }
        }

        public static class VisaInfoBean {
            /**
             * name : 签证指南
             * content : <section class="raidersBox itemMenuBox"><div class="raidersItem"><div class="raidersBd"><div class="raidersList"><section class="raidersBox itemMenuBox"><div class="raidersItem"><div class="raidersBd"><div class="raidersList"><div class="pr raidersMod"><em class="pa arrowIcon"></em><span class="">签证政策</span></div><div class="openCont"><p style="text-indent: 2em;"><span style="font-size: 14px;">中国游客赴日本旅行分为单次签证、三年多次签以及五年多次签。</span></p></div></div></div></div></section><section class="raidersBox itemMenuBox"><div class="raidersItem"><div class="raidersBd"><div class="raidersList"><div class="pr raidersMod"><em class="pa arrowIcon"></em><span class="">政策解读</span></div><div class="openCont"><p style="text-indent: 2em;"><span style="font-size: 14px;">单次签证有效期三个月，最长停留时间15天。三年和五年多次签证首次赴日最多停留15天，再次赴日可停留30天，一年内累计停留最多180天。</span></p></div></div></div></div></section></div></div></div></section><section class="raidersBox itemMenuBox"><div class="raidersItem"><div class="raidersBd"><div class="raidersList"><div class="pr raidersMod"><em class="pa arrowIcon"></em><span class="">签证申请流程</span></div><div class="openCont"><p style="text-indent: 2em;"><span style="font-size: 14px;">＊日本签证必须委托日方指定的代理机构办理，个人无法自行办理</span></p><p><br/></p><p style="text-indent: 2em;"><span style="font-size: 14px;">1. 签证申请者确定赴日目的并拟定行程计划。</span></p><p style="text-indent: 2em;"><span style="font-size: 14px;">2. 登陆日本驻华使领馆官方网站，浏览有关签证申请信息和签证所需材料，下载签证申请表。（如您选择通过游谱办理，下单成功后，客服会告知您接收所需申请表和材料的相关流程。）</span></p><p style="text-indent: 2em;"><span style="font-size: 14px;">3. 选择代理机构办理签证申请，准备签证材料，缴纳签证费和签证服务费。</span></p><p style="text-indent: 2em;"><span style="font-size: 14px;">4. 等候签证申请审核或补充资料。</span></p><p style="text-indent: 2em;"><span style="font-size: 14px;">5. 领取签证。拿到签证后，应及时仔细核对签证上的各项信息是否正确，尤其是签证有效期的起止时间及停留天数是否与所申请的相符，签证上的个人信息如姓名拼写是否正确，如发现任何错误，应及时与使领馆联系。</span></p><p style="text-indent: 2em;"><a href="http://tehui.youpu.cn/product/detail?id=1017633" target="_self"><span style="font-size: 14px;"><img src="http://ypimg.oss-cn-beijing.aliyuncs.com/yp/201603/30/adaae0a88df24cebc5fcd32607aa29b1.png" title="adaae0a88df24cebc5fcd32607aa29b1.png" alt="adaae0a88df24cebc5fcd32607aa29b1.png" width="100" height="29" border="0" vspace="0" style="width: 100px; height: 29px;"/></span></a></p><p style="color: rgb(0, 0, 0); font-family: HannotateSC-W5; line-height: 16px; white-space: normal;"><br/></p></div></div></div></div></section><section class="raidersBox itemMenuBox"><div class="raidersItem"><div class="raidersBd"><div class="raidersList"><div class="pr raidersMod"><em class="pa arrowIcon"></em><span class="">签证领区的划分</span></div><div class="openCont"><p style="text-indent: 2em;"><span style="font-size: 14px;">*请选择正确的领区，以利于您申请签证</span></p><p><br/></p><p style="text-indent: 2em;"><span style="font-size: 14px;">日本驻北京大使馆：受理北京市、天津市、陕西省、山西省、甘肃省、河南省、河北省、 湖北省、湖南省、青海省、新疆维吾尔自治区、宁夏回族自治区、西藏自治区、内蒙古自治区。&nbsp;</span></p><p style="text-indent: 2em;"><span style="font-size: 14px;">日本驻上海总领事馆：受理上海市、安徽省、浙江省、江苏省、江西省。</span></p><p style="text-indent: 2em;"><span style="font-size: 14px;">日本驻香港领事馆：受理香港特别行政区、澳门特别行政区。</span></p><p style="text-indent: 2em;"><span style="font-size: 14px;">日本驻广州总领事馆：受理广东省、海南省、福建省、广西壮族自治区。</span></p><p style="text-indent: 2em;"><span style="font-size: 14px;">日本驻重庆总领事馆：受理重庆市、四川省、贵州省、云南省。</span></p><p style="text-indent: 2em;"><span style="font-size: 14px;">日本驻沈阳总领事馆：受理辽宁省（除大连市）、吉林省、黑龙江省。</span></p><p style="text-indent: 2em;"><span style="font-size: 14px;">日本驻大连办事处：受理大连市。</span></p><p style="text-indent: 2em;"><span style="font-size: 14px;">日本驻青岛总领事馆：受理山东省。</span></p></div></div></div></div></section><section class="raidersBox"><div class="raidersItem"><div class="raidersHd level_1_group"><h3>单次签证办理</h3></div><div class="raidersBd"><div class="raidersList"><div class="pr raidersMod"><em class="pa arrowIcon"></em>必要资料</div><div class="openCont"><p style="text-indent: 2em;"><strong><span style="font-size: 14px;">个人信息表</span></strong><span style="font-size: 14px;"> (原件)</span></p><p style="text-indent: 2em;"><span style="font-size: 14px;">•除签名外，其他全部信息需机打填写完整，点击“印刷”后打印，不可手写</span></p><p style="text-indent: 2em;"><span style="font-size: 14px;">•表格为PDF格式,可直接填写编辑,请使用Adobe Reader等软件打开</span></p><p style="text-indent: 2em;"><span style="font-size: 14px;">•A4纸单面打印，左上方需印有二维码标识</span></p><p><br/></p><p style="text-indent: 2em;"><strong><span style="font-size: 14px;">户口本</span></strong><span style="font-size: 14px;"> (复印件)</span></p><p style="text-indent: 2em;"><span style="font-size: 14px;">•①申请人本人整本户口本信息 ②如是集体户口，提供集体户口首页和本人页 ③如丢失，请开户派出所出具的户籍证明</span></p><p><br/></p><p style="text-indent: 2em;"><strong><span style="font-size: 14px;">身份证</span></strong><span style="font-size: 14px;"> (复印件)</span></p><p style="text-indent: 2em;"><span style="font-size: 14px;">•正反面信息，16周岁以下如没有，可不提供</span></p><p><br/></p><p style="text-indent: 2em;"><strong><span style="font-size: 14px;">因私护照</span></strong><span style="font-size: 14px;"> (原件)</span></p><p style="text-indent: 2em;"><span style="font-size: 14px;">•有效期离出发日期应至少还有6个月</span></p><p style="text-indent: 2em;"><span style="font-size: 14px;">•至少有两页完整连续的空白页，不包含备注页</span></p><p><br/></p><p style="text-indent: 2em;"><strong><span style="font-size: 14px;">照片</span></strong><span style="font-size: 14px;"> (原件)</span></p><p style="text-indent: 2em;"><span style="font-size: 14px;">•近6个月内拍摄彩色照片2张，请在照片反面写上名字</span></p><p style="text-indent: 2em;"><span style="font-size: 14px;">•规格：45mm*45mm 正方形</span></p><p style="text-indent: 2em;"><span style="font-size: 14px;">•白色背景</span></p><p><br/></p><p style="text-indent: 2em;"><strong><span style="font-size: 14px;">婚姻证明</span></strong><span style="font-size: 14px;"> (复印件)</span></p><p style="text-indent: 2em;"><span style="font-size: 14px;">•①如已婚，需提供结婚证； ②如离异，需提供离婚证或离婚协议证明； ③未婚、丧偶此项无需提供</span></p><p><br/></p><p style="text-indent: 2em;"><strong><span style="font-size: 14px;">暂住证明 </span></strong><span style="font-size: 14px;">(原件)</span></p><p style="text-indent: 2em;"><span style="font-size: 14px;">•暂住证原件，送签时暂/居住证还在有效期内</span></p><p><br/></p><p style="text-indent: 2em;"><strong><span style="font-size: 14px;">营业执照副本或组织机构代码证</span></strong><span style="font-size: 14px;"> (复印件)</span></p><p style="text-indent: 2em;"><span style="font-size: 14px;">中文在职证明 (原件)</span></p><p style="text-indent: 2em;"><span style="font-size: 14px;">•注意：使领馆可能会致电单位电话或本人手机，请提交资料后注意接听，如有未接或不属实情况，可能会造成拒签</span></p><p><br/></p><p style="text-indent: 2em;"><strong><span style="font-size: 14px;">工资卡或退休金或活期银行卡对账单</span></strong><span style="font-size: 14px;"> (原件)</span></p><p style="text-indent: 2em;"><span style="font-size: 14px;">•本人名下近1年交易记录的工资卡或储蓄卡对帐单（盖银行公章），须体现年收入过十万</span></p><p style="text-indent: 2em;"><span style="font-size: 14px;">•最后一笔交易日期在送签日期前30天内</span></p><p><br/></p><p style="text-indent: 2em;"><strong><span style="font-size: 14px;">全程交通预订单</span></strong><span style="font-size: 14px;"> (原件)</span></p><p style="text-indent: 2em;"><span style="font-size: 14px;">•全程机票预订单，签证被签发前勿先行支付机票费用，以免产生损失</span></p><p><br/></p><p style="text-indent: 2em;"><strong><span style="font-size: 14px;">全程酒店预订单</span></strong><span style="font-size: 14px;"> (原件)</span></p><p style="text-indent: 2em;"><span style="font-size: 14px;">•入住日期覆盖境外停留的全部天数，清晰显示入住人姓名全称、酒店名称、酒店地址及酒店电话，中文或日文</span></p><p><br/></p><p style="text-indent: 2em;"><strong><span style="font-size: 14px;">承诺书</span></strong><span style="font-size: 14px;"> (原件)</span></p><p style="text-indent: 2em;"><span style="font-size: 14px;">•所有信息填写完整，本人亲笔签名</span></p><p style="text-indent: 2em; text-align: right;"><a href="http://tehui.youpu.cn/product/detail?id=1017633" target="_self"><span style="font-size: 14px;"><img src="http://ypimg.oss-cn-beijing.aliyuncs.com/yp/201603/30/400baa4922d8ebdb0b295db310fc8ce1.png" title="400baa4922d8ebdb0b295db310fc8ce1.png" alt="400baa4922d8ebdb0b295db310fc8ce1.png" width="100" height="29" border="0" vspace="0" style="width: 100px; height: 29px;"/></span></a></p></div></div></div><div class="raidersBd"><div class="raidersList"><div class="pr raidersMod"><em class="pa arrowIcon"></em>可选资料</div><div class="openCont"><p style="text-indent: 2em;"><span style="font-size: 14px;">*可选资料可视个人实际情况提供，提供越多越有利于出签</span></p><p><br/></p><p style="text-indent: 2em;"><strong><span style="font-size: 14px;">房产证明</span></strong><span style="font-size: 14px;"> (复印件)</span></p><p style="text-indent: 2em;"><span style="font-size: 14px;">•如有可提供，房产证或购房发票任选其一</span></p><p><br/></p><p style="text-indent: 2em;"><strong><span style="font-size: 14px;">其他资产证明</span></strong><span style="font-size: 14px;"> (原件)</span></p><p style="text-indent: 2em;"><span style="font-size: 14px;">•信用卡对账单、股票或基金的账户情况等，但此类文件不能代替银行对账单</span></p><p><br/></p><p style="text-indent: 2em;"><strong><span style="font-size: 14px;">汽车行驶证 </span></strong><span style="font-size: 14px;">(复印件)</span></p><p style="text-indent: 2em;"><span style="font-size: 14px;">•机动车行驶证或机动车登记证任选其一</span></p></div></div></div><div class="raidersBd"><div class="raidersList"><div class="pr raidersMod"><em class="pa arrowIcon"></em>未成年</div><div class="openCont"><p style="text-indent: 2em;"><strong><span style="font-size: 14px;">出生证或出生医学证明</span></strong><span style="font-size: 14px;"> (复印件)</span></p><p style="text-indent: 2em;"><span style="font-size: 14px;">•18周岁及以下申请人提供</span></p><p style="text-indent: 2em;"><span style="font-size: 14px;">•内容涵盖申请人名字及父母信息</span></p><p><br/></p><p style="text-indent: 2em;"><strong><span style="font-size: 14px;">身份证 </span></strong><span style="font-size: 14px;">(复印件)</span></p><p style="text-indent: 2em;"><span style="font-size: 14px;">•正反面信息，16周岁以下如没有，可不提供</span></p><p><br/></p><p style="text-indent: 2em;"><strong><span style="font-size: 14px;">中文在校证明</span></strong><span style="font-size: 14px;"> (原件)</span></p><p style="text-indent: 2em;"><span style="font-size: 14px;">•①使用学校抬头纸打印，盖学校公章，班主任或学校领导签字 ②内容需包括：学校名称，地址和电话，学生所学专业或所在班级，学校假期时间，班主任或学校领导名字及职务</span></p></div></div></div></div></section><section class="raidersBox itemMenuBox"><div class="raidersItem"><div class="raidersBd"><div class="raidersList"><div class="pr raidersMod"><em class="pa arrowIcon"></em><span class="">三年多次签证</span></div><div class="openCont"><p style="text-indent: 2em;"><span style="font-size: 14px;">*旅行者必须在冲绳或东北三县至少停留一晚。</span></p><p><br/></p><p style="text-indent: 2em;"><span style="font-size: 14px;">激活条件：使馆要求至少在冲绳或东北三县（宫城县、岩手县、福岛县）的酒店住宿（需要注意的是酒店必须三星级以上，民宿、公寓等不符合要求）一晚，出签后不得取消住宿，退房时需向酒店索取住宿证明，回国后用于激活签证。</span></p><p><br/></p><p style="text-indent: 2em;"><span style="font-size: 14px;">除必备资料外还需要提供</span></p><p><br/></p><p style="text-indent: 2em;"><strong><span style="font-size: 14px;">资产证明原件</span></strong></p><p><br/></p><p style="text-indent: 2em;"><span style="font-size: 14px;">•税前年收入在20万以上，提供本人名下近1年银行出具的工资明细（项目栏为“工资”）</span></p><p style="text-indent: 2em;"><span style="font-size: 14px;">•或近12个月个人完税证明（注明年薪）（盖税务局红章）</span></p><p style="text-indent: 2em;"><span style="font-size: 14px;">•若提供的银行明细单中无法明确显示“工资”字样，则必须提供合格完税证明</span></p><p style="text-indent: 2em; text-align: right;"><br/></p><p style="text-indent: 2em; text-align: right;"><a href="http://tehui.youpu.cn/product/detail?id=1017748" target="_self"><img src="http://ypimg.oss-cn-beijing.aliyuncs.com/yp/201603/30/30a41e01b816cba93b44e33ee3ebb8cb.png" title="30a41e01b816cba93b44e33ee3ebb8cb.png" alt="30a41e01b816cba93b44e33ee3ebb8cb.png" width="100" height="29" border="0" vspace="0" style="width: 100px; height: 29px;"/></a></p></div></div></div></div></section><section class="raidersBox itemMenuBox"><div class="raidersItem"><div class="raidersBd"><div class="raidersList"><div class="pr raidersMod"><em class="pa arrowIcon"></em><span class="">五年多次签证</span></div><div class="openCont"><p style="text-indent: 2em;"><span style="font-size: 14px;">激活条件：需要按照申请时提交的行程完成旅行，退房时向酒店索取住宿证明（全程酒店），回国后将护照首页、签证页、出入境章页、全程酒店住宿证明提交给旅行社，用于激活签证。</span></p><p><br/></p><p style="text-indent: 2em;"><span style="font-size: 14px;">除必备资料外还需要提供</span></p><p><br/></p><p style="text-indent: 2em;"><strong><span style="font-size: 14px;">资产证明原件</span></strong></p><p><br/></p><p style="text-indent: 2em;"><span style="font-size: 14px;">•税前年收入在50万以上，提供本人名下近1年银行出具的工资明细（项目栏为“工资”）</span></p><p style="text-indent: 2em;"><span style="font-size: 14px;">•或近12个月个人完税证明（注明年薪）（盖税务局红章）</span></p><p style="text-indent: 2em;"><span style="font-size: 14px;">•若提供的银行明细单中</span></p><p style="text-indent: 2em;"><span style="font-size: 14px;"><br/></span></p><p style="text-indent: 2em;"><span style="font-size: 14px;"><a href="http://m.youpu.cn" target="_self"></a></span></p><p style="text-indent: 2em; text-align: right;"><a href="http://tehui.youpu.cn/product/detail?id=1017760" target="_self"><img src="http://ypimg.oss-cn-beijing.aliyuncs.com/yp/201603/30/d53440d315c426660be895535e9d80bb.png" title="d53440d315c426660be895535e9d80bb.png" alt="d53440d315c426660be895535e9d80bb.png" width="100" height="29" border="0" vspace="0" style="width: 100px; height: 29px;"/></a></p></div></div></div></div></section><section class="raidersBox itemMenuBox"><div class="raidersItem"><div class="raidersBd"><div class="raidersList"><div class="pr raidersMod"><em class="pa arrowIcon"></em><span class="">入境卡填写</span></div><div class="openCont"><img src="http://ypimg.oss-cn-beijing.aliyuncs.com/yp/201603/02/6cd11dfbd201f77583e617103cccbb4f.jpg" title="6cd11dfbd201f77583e617103cccbb4f.jpg" alt="20111209_07dab9ed27f8280e9555YK8lYbzIS2Gz.jpg"/></div></div></div></div></section><p><br/></p>
             */

            private String name;
            private String content;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }
        }

        public static class ImpressOverviewBean {
            /**
             * customs : {"cn_type":"风俗","content":"<p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">1、若在某个地方入口处标有\u201c土足禁止\u201d的提示表示要脱鞋入内；进入宾馆的房间，到日本人家里做客都要换拖鞋，去厕所有时还要换第二遍拖鞋。因此，袜子是否有洞，需要提前注意一下。去的地方多建议不穿系带的鞋。<\/span><\/p><p><br/><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">2、日本对礼仪很讲究，平时注意礼貌，吃饭注意坐姿。<\/span><\/p><p><br/><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">3、入温泉池之前要清洗身体，浴巾毛巾不可放到温泉水里。除此之外，有一些公共温泉不允许带纹身的客人进入。<\/span><\/p><p><br/><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">4、日本自来水可以直接饮用，但要调整到凉水再喝。<\/span><\/p><p><br/><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">5、在日本餐馆吃饭，基本上没有提供热水的习惯，都是凉水。<\/span><\/p><p><br/><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">6、如果是榻榻米的客房，按照日本人的习惯，被褥入睡前铺开，早上起床后叠好收进壁柜。一般的旅馆工作人员会在您就晚餐或到大澡堂入浴时把被褥铺好。<\/span><\/p><p><br/><\/p>"}
             * healthy : {"cn_type":"健康","content":"<p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">1、手握寿司和刺身大部分都是生肉做成的，大神级寿司店一定按预约的时间过去，之前有过中国客人迟到并且不吃生食跟店方闹出矛盾的先例。<\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\"><br/><\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px; text-indent: 2em;\">2、寿司、刺身等海产品很新鲜，但不要吃太多，虽然当地可以很方便的购买到胃药。<\/span><br/><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\"><br/><\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">3、当地没有区域性传染病，可以放心前往。<\/span><\/p><p><br/><\/p>"}
             * security : {"cn_type":"安全","content":"<p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">1、日本是个多地震的国家，如果遇到地震，一定要听从周边人员的指令，及时疏散到安全地带。<\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\"><br/><\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">2、出门要带伞，日本是海洋性气候，晴雨不定，最好带长把伞。因为在日本，多数商店门前，甚至私家住宅门旁，都有一个伞筐，把儿长的伞可以立在筐内，而折叠伞是不能放入伞筐中的。<\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\"><br/><\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">3、酒后骑自行车在某些地方是违法的，如京都。处罚很严厉。<\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\"><br/><\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">4、不要随意触摸民房，日本老宅子很多都拥有悠久的历史，很可能一个不小心就损伤了国宝级的财产。<\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\"><br/><\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">5、紧急电话<\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">&nbsp; &nbsp; &nbsp;火警、急救：119<\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">&nbsp; &nbsp; &nbsp;报警电话：110<\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">&nbsp; &nbsp; &nbsp;中国驻日本大使馆<\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">&nbsp; &nbsp; &nbsp;联系电话：03-3403-3388<\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">&nbsp; &nbsp; &nbsp;&nbsp;<\/span><\/p><p><br/><\/p>"}
             * festival : {"cn_type":"节庆","content":"<p style=\"text-indent: 0em;\"><span style=\"color: rgb(227, 108, 9);\"><strong><span style=\"font-size: 14px;\"><\/span><\/strong><\/span><\/p><p style=\"text-indent: 2em;\"><br/><\/p><p style=\"text-indent: 2em;\"><span style=\"color: rgb(227, 108, 9);\"><strong><span style=\"font-size: 14px;\">元旦<\/span><\/strong><\/span><span style=\"font-size: 14px; white-space: pre;\"><\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">时间：1月1日<span class=\"Apple-tab-span\" style=\"font-size: 14px; white-space: pre;\"><\/span><\/span><\/p><p><br/><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">从元旦1月1日到1月3日这三天叫做\u201c正月\u201d，是全然不干活的。新年里，大家去参拜神社或到朋友家去拜年、喝酒，吃新年里独特的美味佳肴。孩子们玩日本式纸牌、放风筝、拍羽毛毽子。在家门口还会插上松枝与稻草制的花彩，意思是\u201c插上树木迎接神灵降临\u201d。届时，全家还会去神社祈福，互相拜年。<\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\"><br/><\/span><\/p><p>&nbsp;<\/p><p style=\"text-indent: 2em;\"><span style=\"color: rgb(227, 108, 9);\"><strong><span style=\"font-size: 14px;\">偶人节 (\u201c雏祭\u201d)<\/span><\/strong><\/span><span style=\"font-size: 14px; white-space: pre;\"><\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">时间：3月3日<span class=\"Apple-tab-span\" style=\"font-size: 14px; white-space: pre;\"><\/span><\/span><\/p><p><img src=\"https://ypimg1.youpu.cn/upload/youpu/20141211/1418287329929518.jpg\" title=\"1418287329929518.jpg\" alt=\"偶人节.jpg\"/><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">一年一度的为年轻女孩乞求幸福和健康成长的日子。在这个日子，家家户户摆放着穿着传统宫廷装束的玩具娃娃和桃花，还有供奉一种钻石形状的米饼和干米团。玩偶节来源于古代的关于仪式涤罪的信念。人们相信，人类的罪行和污秽可以通过在河边的净化仪式中冲洗干净。后来，人们在这些仪式上使用纸做的玩偶；在江户时期之后，这些玩偶被设计成现在的玩具的样子。<\/span><\/p><p><br/><\/p><p style=\"text-indent: 2em;\"><span style=\"color: rgb(227, 108, 9);\"><strong><span style=\"font-size: 14px;\">樱花节<\/span><\/strong><\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">时间：3月15日至4月15日<span class=\"Apple-tab-span\" style=\"font-size: 14px; white-space: pre;\"><\/span><\/span><\/p><p><img src=\"https://ypimg1.youpu.cn/upload/youpu/20141211/1418287386496299.jpg\" title=\"1418287386496299.jpg\" alt=\"樱花节.jpg\"/><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">日本政府把每年的3月15日至4月15日定为\u201c樱花节\u201d。在东京最大的公园上野公园中，每年此时都要举办\u201c樱花祭\u201d。从早到晚，不分昼夜，欣赏\u201c夜樱\u201d的美丽。花季到来时，粉红色的花朵堆云聚雾，绵延不断，景象十分壮观。<\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"color: rgb(227, 108, 9);\"><strong><span style=\"font-size: 14px;\"><br/><\/span><\/strong><\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"color: rgb(227, 108, 9);\"><strong><span style=\"font-size: 14px;\"><br/><\/span><\/strong><\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"color: rgb(227, 108, 9);\"><strong><span style=\"font-size: 14px;\">神田祭<span class=\"Apple-tab-span\" style=\"font-size: 14px; white-space: pre;\"><\/span><\/span><\/strong><\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">时间：距5月15日最近的周六和周日<\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\"><br/><\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"white-space: pre; font-size: 14px;\"><\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">神田神社的祭会从江户时代开始即为江户两大祭会之一。以每年的5月1日为主举行盛大的祭会。此祭会期间，是以太鼓嘉年华会及抬神轿，在街道上缓步前进的方式将气氛抬升到高潮。主要的大祭会则为隔年举行。<\/span><\/p><p><br/><\/p><p style=\"text-indent: 2em;\"><span style=\"color: rgb(227, 108, 9);\"><strong><span style=\"font-size: 14px;\">浅草三社祭<\/span><\/strong><\/span><span style=\"font-size: 14px; white-space: pre;\"><\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">时间：5月第三周的周五、周六以及周日<\/span><\/p><p><img src=\"https://ypimg1.youpu.cn/upload/youpu/20141211/1418287428882596.jpg\" title=\"1418287428882596.jpg\" alt=\"浅草三社祭.jpg\"/><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">保留了传统文化的街区举办的东京最热门的神轿节庆活动，在浅草神社举办。在神社可以观赏\u201c编木（Binzasara）舞\u201d。舞者身着华丽衣装，手持名为\u201cBinzasara\u201d的乐器翩翩起舞，祈求丰收和子孙后代的繁荣。\u201c编木\u201d演奏像手风琴，一开一合，非常有可观性。<\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\"><br/><\/span><\/p><p><\/p><p style=\"text-indent: 2em;\"><span style=\"color: rgb(227, 108, 9);\"><strong><span style=\"font-size: 14px;\">山王祭<span class=\"Apple-tab-span\" style=\"font-size: 14px; white-space: pre;\"><\/span><\/span><\/strong><\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">时间：6月10-16日<span class=\"Apple-tab-span\" style=\"font-size: 14px; white-space: pre;\"><\/span><\/span><\/p><p><br/><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">山王祭，是东京都千代田区的日枝神社的祭礼，正式名称是\u201c日枝神社大祭\u201d，是江户时代最高级别的\u201c天下祭\u201d之一。山因祭会是隔年举行的关系，当轮为主祭会年时，还会与其他地区，如大田乐、民族舞蹈大会等相关的活动一起举行，热闹非凡。<\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\"><br/><\/span><\/p><p><\/p><p style=\"text-indent: 2em;\"><span style=\"color: rgb(227, 108, 9);\"><strong><span style=\"font-size: 14px;\">七夕节<\/span><\/strong><\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">时间：7月7日<\/span><\/p><p><img src=\"https://ypimg1.youpu.cn/upload/youpu/20141211/1418287486531158.jpg\" title=\"1418287486531158.jpg\" alt=\"七夕节.jpg\"/><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">七夕节被认为是从中国传去的风俗同日本固有习惯相结合的节日。七夕节在日本民俗活动中具有很重要的地位，日本人将其称为\u201c夏天的风物诗\u201d。这一天不单是牛郎与织女的情人节，人们会将写有各种各样的愿望的彩纸贴在竹枝上，以求愿望得以实现。<\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\"><br/><\/span><\/p><p><\/p><p style=\"text-indent: 2em;\"><span style=\"color: rgb(227, 108, 9);\"><strong><span style=\"font-size: 14px;\">盂兰盆节<\/span><\/strong><\/span><span style=\"font-size: 14px; white-space: pre;\"><\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">时间：7月13-15日或者8月13-15日<span class=\"Apple-tab-span\" style=\"font-size: 14px; white-space: pre;\"><\/span><\/span><\/p><p><br/><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">一年一度的迎接和安慰祖先亡灵的日子。据说，祖先的亡灵在这个日子来到家里。按照传统，在阴历7月的第17日纪念。13日那天人们点火欢迎祖先的亡灵。到16日，人们点燃送别火，送祖先的亡灵回去。节日期间，公司商店都会放假，因为在异地工作的人都会赶回家与家人团聚，那时候的交通很是繁忙。<\/span><\/p><p><br/><\/p><p style=\"text-indent: 2em;\"><span style=\"color: rgb(227, 108, 9);\"><strong><span style=\"font-size: 14px;\">深川八幡祭<\/span><\/strong><\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">时间：大约在接近8月15日左右的周六、日举行<span class=\"Apple-tab-span\" style=\"font-size: 14px; white-space: pre;\"><\/span><\/span><\/p><p><br/><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">深川八幡祭是一个结合日本神舆巡游及泼水玩意的狂野庆典，由早上七时许开始，全长约八公里的巡游路两旁，便有各式各样的围观者肆意向巡游队伍泼水，一直到下午五时许才结束。作为旁观者，若不湿身，实在是无比的幸运呢﹗<\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\"><br/><\/span><\/p><p><\/p><p style=\"text-indent: 2em;\"><span style=\"color: rgb(227, 108, 9);\"><strong><span style=\"font-size: 14px;\">赏月<\/span><\/strong><\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">时间：阴历8月15日和9月13日<\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\"><br/><\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"white-space: pre; font-size: 14px;\"><\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">日本的赏月习俗来源于中国，传到日本后，当地开始出现边赏月边举行宴会的风俗习惯，被称为\u201c观月宴\u201d。日本人没有吃月饼的习俗，阴历8月15日和9月13日夜晚，日本人在赏月的时候吃的是一种用糯米做成的白色团子，叫江米团子，又称月见团子。<\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\"><br/><\/span><\/p><p><\/p><p style=\"text-indent: 2em;\"><span style=\"color: rgb(227, 108, 9);\"><strong><span style=\"font-size: 14px;\">东京国际电影节<\/span><\/strong><\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">时间：每年10月23-31日左右，为期一周<\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\"><br/><\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"white-space: pre; font-size: 14px;\"><\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">国际电影制作者联盟公认的国际电影节。参赛作品只限长篇电影。<\/span><\/p><p style=\"text-indent: 2em;\"><\/p><p style=\"text-indent: 2em;\"><span style=\"color: rgb(227, 108, 9);\"><strong><span style=\"font-size: 14px;\"><br/><\/span><\/strong><\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"color: rgb(227, 108, 9);\"><strong><span style=\"font-size: 14px;\"><br/><\/span><\/strong><\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"color: rgb(227, 108, 9);\"><strong><span style=\"font-size: 14px;\">七五三<span class=\"Apple-tab-span\" style=\"font-size: 14px; white-space: pre;\"><\/span><\/span><\/strong><\/span><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">时间：11月15日<span class=\"Apple-tab-span\" style=\"font-size: 14px; white-space: pre;\"><\/span><\/span><\/p><p><img src=\"https://ypimg1.youpu.cn/upload/youpu/20141211/1418287562410830.jpg\" title=\"1418287562410830.jpg\" alt=\"七五三节.jpg\"/><\/p><p style=\"text-indent: 2em;\"><span style=\"font-size: 14px;\">在日本习俗里，3岁、5岁和7岁是孩子特别幸运的三个年纪，所以每逢11月15日，3岁和5岁的男孩、3岁和7岁的女孩都会穿上鲜艳的和服，跟父母到神社参拜，祈愿身体健康，顺利成长。但是现在，孩子们也穿别的服装。这天，孩子的父母亲会到商店里购买一种叫做千岁糖的糖果给孩子，据说此种糖果为孩子带来长寿。全家热热闹闹，吃红豆米饭和带头尾的鳊鱼。<\/span><\/p><p><br/><\/p>"}
             */

            private CustomsBean customs;
            private HealthyBean healthy;
            private SecurityBean security;
            private FestivalBean festival;

            public CustomsBean getCustoms() {
                return customs;
            }

            public void setCustoms(CustomsBean customs) {
                this.customs = customs;
            }

            public HealthyBean getHealthy() {
                return healthy;
            }

            public void setHealthy(HealthyBean healthy) {
                this.healthy = healthy;
            }

            public SecurityBean getSecurity() {
                return security;
            }

            public void setSecurity(SecurityBean security) {
                this.security = security;
            }

            public FestivalBean getFestival() {
                return festival;
            }

            public void setFestival(FestivalBean festival) {
                this.festival = festival;
            }

            public static class CustomsBean {
                /**
                 * cn_type : 风俗
                 * content : <p style="text-indent: 2em;"><span style="font-size: 14px;">1、若在某个地方入口处标有“土足禁止”的提示表示要脱鞋入内；进入宾馆的房间，到日本人家里做客都要换拖鞋，去厕所有时还要换第二遍拖鞋。因此，袜子是否有洞，需要提前注意一下。去的地方多建议不穿系带的鞋。</span></p><p><br/></p><p style="text-indent: 2em;"><span style="font-size: 14px;">2、日本对礼仪很讲究，平时注意礼貌，吃饭注意坐姿。</span></p><p><br/></p><p style="text-indent: 2em;"><span style="font-size: 14px;">3、入温泉池之前要清洗身体，浴巾毛巾不可放到温泉水里。除此之外，有一些公共温泉不允许带纹身的客人进入。</span></p><p><br/></p><p style="text-indent: 2em;"><span style="font-size: 14px;">4、日本自来水可以直接饮用，但要调整到凉水再喝。</span></p><p><br/></p><p style="text-indent: 2em;"><span style="font-size: 14px;">5、在日本餐馆吃饭，基本上没有提供热水的习惯，都是凉水。</span></p><p><br/></p><p style="text-indent: 2em;"><span style="font-size: 14px;">6、如果是榻榻米的客房，按照日本人的习惯，被褥入睡前铺开，早上起床后叠好收进壁柜。一般的旅馆工作人员会在您就晚餐或到大澡堂入浴时把被褥铺好。</span></p><p><br/></p>
                 */

                private String cn_type;
                private String content;

                public String getCn_type() {
                    return cn_type;
                }

                public void setCn_type(String cn_type) {
                    this.cn_type = cn_type;
                }

                public String getContent() {
                    return content;
                }

                public void setContent(String content) {
                    this.content = content;
                }
            }

            public static class HealthyBean {
                /**
                 * cn_type : 健康
                 * content : <p style="text-indent: 2em;"><span style="font-size: 14px;">1、手握寿司和刺身大部分都是生肉做成的，大神级寿司店一定按预约的时间过去，之前有过中国客人迟到并且不吃生食跟店方闹出矛盾的先例。</span></p><p style="text-indent: 2em;"><span style="font-size: 14px;"><br/></span></p><p style="text-indent: 2em;"><span style="font-size: 14px; text-indent: 2em;">2、寿司、刺身等海产品很新鲜，但不要吃太多，虽然当地可以很方便的购买到胃药。</span><br/></p><p style="text-indent: 2em;"><span style="font-size: 14px;"><br/></span></p><p style="text-indent: 2em;"><span style="font-size: 14px;">3、当地没有区域性传染病，可以放心前往。</span></p><p><br/></p>
                 */

                private String cn_type;
                private String content;

                public String getCn_type() {
                    return cn_type;
                }

                public void setCn_type(String cn_type) {
                    this.cn_type = cn_type;
                }

                public String getContent() {
                    return content;
                }

                public void setContent(String content) {
                    this.content = content;
                }
            }

            public static class SecurityBean {
                /**
                 * cn_type : 安全
                 * content : <p style="text-indent: 2em;"><span style="font-size: 14px;">1、日本是个多地震的国家，如果遇到地震，一定要听从周边人员的指令，及时疏散到安全地带。</span></p><p style="text-indent: 2em;"><span style="font-size: 14px;"><br/></span></p><p style="text-indent: 2em;"><span style="font-size: 14px;">2、出门要带伞，日本是海洋性气候，晴雨不定，最好带长把伞。因为在日本，多数商店门前，甚至私家住宅门旁，都有一个伞筐，把儿长的伞可以立在筐内，而折叠伞是不能放入伞筐中的。</span></p><p style="text-indent: 2em;"><span style="font-size: 14px;"><br/></span></p><p style="text-indent: 2em;"><span style="font-size: 14px;">3、酒后骑自行车在某些地方是违法的，如京都。处罚很严厉。</span></p><p style="text-indent: 2em;"><span style="font-size: 14px;"><br/></span></p><p style="text-indent: 2em;"><span style="font-size: 14px;">4、不要随意触摸民房，日本老宅子很多都拥有悠久的历史，很可能一个不小心就损伤了国宝级的财产。</span></p><p style="text-indent: 2em;"><span style="font-size: 14px;"><br/></span></p><p style="text-indent: 2em;"><span style="font-size: 14px;">5、紧急电话</span></p><p style="text-indent: 2em;"><span style="font-size: 14px;">&nbsp; &nbsp; &nbsp;火警、急救：119</span></p><p style="text-indent: 2em;"><span style="font-size: 14px;">&nbsp; &nbsp; &nbsp;报警电话：110</span></p><p style="text-indent: 2em;"><span style="font-size: 14px;">&nbsp; &nbsp; &nbsp;中国驻日本大使馆</span></p><p style="text-indent: 2em;"><span style="font-size: 14px;">&nbsp; &nbsp; &nbsp;联系电话：03-3403-3388</span></p><p style="text-indent: 2em;"><span style="font-size: 14px;">&nbsp; &nbsp; &nbsp;&nbsp;</span></p><p><br/></p>
                 */

                private String cn_type;
                private String content;

                public String getCn_type() {
                    return cn_type;
                }

                public void setCn_type(String cn_type) {
                    this.cn_type = cn_type;
                }

                public String getContent() {
                    return content;
                }

                public void setContent(String content) {
                    this.content = content;
                }
            }

            public static class FestivalBean {
                /**
                 * cn_type : 节庆
                 * content : <p style="text-indent: 0em;"><span style="color: rgb(227, 108, 9);"><strong><span style="font-size: 14px;"></span></strong></span></p><p style="text-indent: 2em;"><br/></p><p style="text-indent: 2em;"><span style="color: rgb(227, 108, 9);"><strong><span style="font-size: 14px;">元旦</span></strong></span><span style="font-size: 14px; white-space: pre;"></span></p><p style="text-indent: 2em;"><span style="font-size: 14px;">时间：1月1日<span class="Apple-tab-span" style="font-size: 14px; white-space: pre;"></span></span></p><p><br/></p><p style="text-indent: 2em;"><span style="font-size: 14px;">从元旦1月1日到1月3日这三天叫做“正月”，是全然不干活的。新年里，大家去参拜神社或到朋友家去拜年、喝酒，吃新年里独特的美味佳肴。孩子们玩日本式纸牌、放风筝、拍羽毛毽子。在家门口还会插上松枝与稻草制的花彩，意思是“插上树木迎接神灵降临”。届时，全家还会去神社祈福，互相拜年。</span></p><p style="text-indent: 2em;"><span style="font-size: 14px;"><br/></span></p><p>&nbsp;</p><p style="text-indent: 2em;"><span style="color: rgb(227, 108, 9);"><strong><span style="font-size: 14px;">偶人节 (“雏祭”)</span></strong></span><span style="font-size: 14px; white-space: pre;"></span></p><p style="text-indent: 2em;"><span style="font-size: 14px;">时间：3月3日<span class="Apple-tab-span" style="font-size: 14px; white-space: pre;"></span></span></p><p><img src="https://ypimg1.youpu.cn/upload/youpu/20141211/1418287329929518.jpg" title="1418287329929518.jpg" alt="偶人节.jpg"/></p><p style="text-indent: 2em;"><span style="font-size: 14px;">一年一度的为年轻女孩乞求幸福和健康成长的日子。在这个日子，家家户户摆放着穿着传统宫廷装束的玩具娃娃和桃花，还有供奉一种钻石形状的米饼和干米团。玩偶节来源于古代的关于仪式涤罪的信念。人们相信，人类的罪行和污秽可以通过在河边的净化仪式中冲洗干净。后来，人们在这些仪式上使用纸做的玩偶；在江户时期之后，这些玩偶被设计成现在的玩具的样子。</span></p><p><br/></p><p style="text-indent: 2em;"><span style="color: rgb(227, 108, 9);"><strong><span style="font-size: 14px;">樱花节</span></strong></span></p><p style="text-indent: 2em;"><span style="font-size: 14px;">时间：3月15日至4月15日<span class="Apple-tab-span" style="font-size: 14px; white-space: pre;"></span></span></p><p><img src="https://ypimg1.youpu.cn/upload/youpu/20141211/1418287386496299.jpg" title="1418287386496299.jpg" alt="樱花节.jpg"/></p><p style="text-indent: 2em;"><span style="font-size: 14px;">日本政府把每年的3月15日至4月15日定为“樱花节”。在东京最大的公园上野公园中，每年此时都要举办“樱花祭”。从早到晚，不分昼夜，欣赏“夜樱”的美丽。花季到来时，粉红色的花朵堆云聚雾，绵延不断，景象十分壮观。</span></p><p style="text-indent: 2em;"><span style="color: rgb(227, 108, 9);"><strong><span style="font-size: 14px;"><br/></span></strong></span></p><p style="text-indent: 2em;"><span style="color: rgb(227, 108, 9);"><strong><span style="font-size: 14px;"><br/></span></strong></span></p><p style="text-indent: 2em;"><span style="color: rgb(227, 108, 9);"><strong><span style="font-size: 14px;">神田祭<span class="Apple-tab-span" style="font-size: 14px; white-space: pre;"></span></span></strong></span></p><p style="text-indent: 2em;"><span style="font-size: 14px;">时间：距5月15日最近的周六和周日</span></p><p style="text-indent: 2em;"><span style="font-size: 14px;"><br/></span></p><p style="text-indent: 2em;"><span style="white-space: pre; font-size: 14px;"></span></p><p style="text-indent: 2em;"><span style="font-size: 14px;">神田神社的祭会从江户时代开始即为江户两大祭会之一。以每年的5月1日为主举行盛大的祭会。此祭会期间，是以太鼓嘉年华会及抬神轿，在街道上缓步前进的方式将气氛抬升到高潮。主要的大祭会则为隔年举行。</span></p><p><br/></p><p style="text-indent: 2em;"><span style="color: rgb(227, 108, 9);"><strong><span style="font-size: 14px;">浅草三社祭</span></strong></span><span style="font-size: 14px; white-space: pre;"></span></p><p style="text-indent: 2em;"><span style="font-size: 14px;">时间：5月第三周的周五、周六以及周日</span></p><p><img src="https://ypimg1.youpu.cn/upload/youpu/20141211/1418287428882596.jpg" title="1418287428882596.jpg" alt="浅草三社祭.jpg"/></p><p style="text-indent: 2em;"><span style="font-size: 14px;">保留了传统文化的街区举办的东京最热门的神轿节庆活动，在浅草神社举办。在神社可以观赏“编木（Binzasara）舞”。舞者身着华丽衣装，手持名为“Binzasara”的乐器翩翩起舞，祈求丰收和子孙后代的繁荣。“编木”演奏像手风琴，一开一合，非常有可观性。</span></p><p style="text-indent: 2em;"><span style="font-size: 14px;"><br/></span></p><p></p><p style="text-indent: 2em;"><span style="color: rgb(227, 108, 9);"><strong><span style="font-size: 14px;">山王祭<span class="Apple-tab-span" style="font-size: 14px; white-space: pre;"></span></span></strong></span></p><p style="text-indent: 2em;"><span style="font-size: 14px;">时间：6月10-16日<span class="Apple-tab-span" style="font-size: 14px; white-space: pre;"></span></span></p><p><br/></p><p style="text-indent: 2em;"><span style="font-size: 14px;">山王祭，是东京都千代田区的日枝神社的祭礼，正式名称是“日枝神社大祭”，是江户时代最高级别的“天下祭”之一。山因祭会是隔年举行的关系，当轮为主祭会年时，还会与其他地区，如大田乐、民族舞蹈大会等相关的活动一起举行，热闹非凡。</span></p><p style="text-indent: 2em;"><span style="font-size: 14px;"><br/></span></p><p></p><p style="text-indent: 2em;"><span style="color: rgb(227, 108, 9);"><strong><span style="font-size: 14px;">七夕节</span></strong></span></p><p style="text-indent: 2em;"><span style="font-size: 14px;">时间：7月7日</span></p><p><img src="https://ypimg1.youpu.cn/upload/youpu/20141211/1418287486531158.jpg" title="1418287486531158.jpg" alt="七夕节.jpg"/></p><p style="text-indent: 2em;"><span style="font-size: 14px;">七夕节被认为是从中国传去的风俗同日本固有习惯相结合的节日。七夕节在日本民俗活动中具有很重要的地位，日本人将其称为“夏天的风物诗”。这一天不单是牛郎与织女的情人节，人们会将写有各种各样的愿望的彩纸贴在竹枝上，以求愿望得以实现。</span></p><p style="text-indent: 2em;"><span style="font-size: 14px;"><br/></span></p><p></p><p style="text-indent: 2em;"><span style="color: rgb(227, 108, 9);"><strong><span style="font-size: 14px;">盂兰盆节</span></strong></span><span style="font-size: 14px; white-space: pre;"></span></p><p style="text-indent: 2em;"><span style="font-size: 14px;">时间：7月13-15日或者8月13-15日<span class="Apple-tab-span" style="font-size: 14px; white-space: pre;"></span></span></p><p><br/></p><p style="text-indent: 2em;"><span style="font-size: 14px;">一年一度的迎接和安慰祖先亡灵的日子。据说，祖先的亡灵在这个日子来到家里。按照传统，在阴历7月的第17日纪念。13日那天人们点火欢迎祖先的亡灵。到16日，人们点燃送别火，送祖先的亡灵回去。节日期间，公司商店都会放假，因为在异地工作的人都会赶回家与家人团聚，那时候的交通很是繁忙。</span></p><p><br/></p><p style="text-indent: 2em;"><span style="color: rgb(227, 108, 9);"><strong><span style="font-size: 14px;">深川八幡祭</span></strong></span></p><p style="text-indent: 2em;"><span style="font-size: 14px;">时间：大约在接近8月15日左右的周六、日举行<span class="Apple-tab-span" style="font-size: 14px; white-space: pre;"></span></span></p><p><br/></p><p style="text-indent: 2em;"><span style="font-size: 14px;">深川八幡祭是一个结合日本神舆巡游及泼水玩意的狂野庆典，由早上七时许开始，全长约八公里的巡游路两旁，便有各式各样的围观者肆意向巡游队伍泼水，一直到下午五时许才结束。作为旁观者，若不湿身，实在是无比的幸运呢﹗</span></p><p style="text-indent: 2em;"><span style="font-size: 14px;"><br/></span></p><p></p><p style="text-indent: 2em;"><span style="color: rgb(227, 108, 9);"><strong><span style="font-size: 14px;">赏月</span></strong></span></p><p style="text-indent: 2em;"><span style="font-size: 14px;">时间：阴历8月15日和9月13日</span></p><p style="text-indent: 2em;"><span style="font-size: 14px;"><br/></span></p><p style="text-indent: 2em;"><span style="white-space: pre; font-size: 14px;"></span></p><p style="text-indent: 2em;"><span style="font-size: 14px;">日本的赏月习俗来源于中国，传到日本后，当地开始出现边赏月边举行宴会的风俗习惯，被称为“观月宴”。日本人没有吃月饼的习俗，阴历8月15日和9月13日夜晚，日本人在赏月的时候吃的是一种用糯米做成的白色团子，叫江米团子，又称月见团子。</span></p><p style="text-indent: 2em;"><span style="font-size: 14px;"><br/></span></p><p></p><p style="text-indent: 2em;"><span style="color: rgb(227, 108, 9);"><strong><span style="font-size: 14px;">东京国际电影节</span></strong></span></p><p style="text-indent: 2em;"><span style="font-size: 14px;">时间：每年10月23-31日左右，为期一周</span></p><p style="text-indent: 2em;"><span style="font-size: 14px;"><br/></span></p><p style="text-indent: 2em;"><span style="white-space: pre; font-size: 14px;"></span></p><p style="text-indent: 2em;"><span style="font-size: 14px;">国际电影制作者联盟公认的国际电影节。参赛作品只限长篇电影。</span></p><p style="text-indent: 2em;"></p><p style="text-indent: 2em;"><span style="color: rgb(227, 108, 9);"><strong><span style="font-size: 14px;"><br/></span></strong></span></p><p style="text-indent: 2em;"><span style="color: rgb(227, 108, 9);"><strong><span style="font-size: 14px;"><br/></span></strong></span></p><p style="text-indent: 2em;"><span style="color: rgb(227, 108, 9);"><strong><span style="font-size: 14px;">七五三<span class="Apple-tab-span" style="font-size: 14px; white-space: pre;"></span></span></strong></span></p><p style="text-indent: 2em;"><span style="font-size: 14px;">时间：11月15日<span class="Apple-tab-span" style="font-size: 14px; white-space: pre;"></span></span></p><p><img src="https://ypimg1.youpu.cn/upload/youpu/20141211/1418287562410830.jpg" title="1418287562410830.jpg" alt="七五三节.jpg"/></p><p style="text-indent: 2em;"><span style="font-size: 14px;">在日本习俗里，3岁、5岁和7岁是孩子特别幸运的三个年纪，所以每逢11月15日，3岁和5岁的男孩、3岁和7岁的女孩都会穿上鲜艳的和服，跟父母到神社参拜，祈愿身体健康，顺利成长。但是现在，孩子们也穿别的服装。这天，孩子的父母亲会到商店里购买一种叫做千岁糖的糖果给孩子，据说此种糖果为孩子带来长寿。全家热热闹闹，吃红豆米饭和带头尾的鳊鱼。</span></p><p><br/></p>
                 */

                private String cn_type;
                private String content;

                public String getCn_type() {
                    return cn_type;
                }

                public void setCn_type(String cn_type) {
                    this.cn_type = cn_type;
                }

                public String getContent() {
                    return content;
                }

                public void setContent(String content) {
                    this.content = content;
                }
            }
        }
    }
}
