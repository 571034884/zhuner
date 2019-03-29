package com.aibabel.scenic.bean;

import com.aibabel.baselibrary.http.BaseBean;

import java.util.List;

/**
 * Created by fytworks on 2019/3/27.
 */

public class SearchWordBean extends BaseBean{


    /**
     * data : {"cityList":[{"idstring":"CurrentCityId13","name":"巴黎","cover":"https://music.gowithtommy.com/mjtt_backend_server%2Fprod%2Fdata%2F2dd9fbdd1b90f4d19e3e88e9a1361a8e4eeca53b.jpg","type":2,"cityname":"","countryname":"法国","latitude":"48.861936","longitude":"2.355102","audiosUrl":"","subcount":40,"desc":""}],"poiList":[{"idstring":"CurrentScenicId22","name":"巴黎歌剧院","cover":"https://music.gowithtommy.com/mjtt_backend_server%2Fprod%2Fdata%2F56af4d29e3b1458356f585a558f7a958f890dd19.jpg","type":3,"cityname":"巴黎","countryname":"法国","latitude":"48.871952","longitude":"2.331754","audiosUrl":"https://mjtt.gowithtommy.com/mjtt_backend_server/prod/data/2b94e9e09d43c586beab6d0116c0f5e85d047632.mp3","subcount":46,"desc":""},{"idstring":"CurrentScenicId3577","name":"巴黎酒店","cover":"https://music.gowithtommy.com/mjtt_backend_server%2Fprod%2Fdata%2F354b983072ba018bf5dd458395c64a7b19ae2bbc.jpg","type":3,"cityname":"拉斯维加斯","countryname":"美国","latitude":"36.112559","longitude":"-115.170692","audiosUrl":"https://mjtt.gowithtommy.com/mjtt_backend_server/prod/data/50dec2fa3f9141075d075b1e00f2ce0dc9df051f.mp3","subcount":0,"desc":""},{"idstring":"CurrentScenicId3738","name":"巴黎圣母院","cover":"https://music.gowithtommy.com/mjtt_backend_server%2Fprod%2Fdata%2Faa98a104530c0e923aaaa2475aa3c02ae881558e.jpg","type":3,"cityname":"巴黎","countryname":"法国","latitude":"48.852989","longitude":"2.349881","audiosUrl":"https://mjtt.gowithtommy.com/mjtt_backend_server/prod/data/862130812267c20eaa9f1bf621e705af16275436.mp3","subcount":119,"desc":""},{"idstring":"CurrentScenicId390","name":"（城）巴黎","cover":"https://music.gowithtommy.com/mjtt_backend_server%2Fprod%2Fdata%2Ff977252ee3ddd7e6409c97f3f21d309bd08230f0.jpg","type":3,"cityname":"巴黎","countryname":"法国","latitude":"48.861936","longitude":"2.355102","audiosUrl":"https://mjtt.gowithtommy.com/mjtt_backend_server/prod/data/4125d80311728d1dc99aa00afbdb7d4e210097fe.mp3","subcount":0,"desc":""},{"idstring":"CurrentScenicId4785","name":"巴黎花宫娜香水博物馆","cover":"https://music.gowithtommy.com/mjtt_backend_server%2Fprod%2Fdata%2F464b78fac09b1cb2911778574d7f15f5e0798ca1.jpg","type":3,"cityname":"巴黎","countryname":"法国","latitude":"48.871504","longitude":"2.330303","audiosUrl":"https://mjtt.oss-cn-beijing.aliyuncs.com/mjtt_backend_server/prod/data/825e2cda995a081f0c6ea58de5cd42324596cf1b.mp3","subcount":8,"desc":""},{"idstring":"CurrentScenicId873","name":"巴黎大皇宫","cover":"https://music.gowithtommy.com/mjtt_backend_server%2Fprod%2Fdata%2F04b66e25223a0342552e2ecccf6202830e71f257.jpg","type":3,"cityname":"巴黎","countryname":"法国","latitude":"48.866081","longitude":"2.312476","audiosUrl":"https://mjtt.gowithtommy.com/%E6%AC%A7%E6%B4%B2%2F%E6%B3%95%E5%9B%BD%2F%E5%B7%B4%E9%BB%8E%2F%E6%94%B9%E5%86%99-7%2C%E6%B3%95%E5%9B%BD+%E5%B7%B4%E9%BB%8E+%E5%B7%B4%E9%BB%8E%E5%A4%A7%E7%9A%87%E5%AE%AB.mp3","subcount":5,"desc":""},{"idstring":"CurrentScenicId875","name":"巴黎古监狱","cover":"https://music.gowithtommy.com/mjtt_backend_server%2Fprod%2Fdata%2Fbf0fbacac7461b012becabab42b926fb1aee6501.jpg","type":3,"cityname":"巴黎","countryname":"法国","latitude":"48.856230","longitude":"2.345570","audiosUrl":"https://mjtt.gowithtommy.com/%E6%AC%A7%E6%B4%B2%2F%E6%B3%95%E5%9B%BD%2F%E5%B7%B4%E9%BB%8E%2F%E6%94%B9%E5%86%99-9%2C%E6%B3%95%E5%9B%BD+%E5%B7%B4%E9%BB%8E+%E5%B7%B4%E9%BB%8E%E5%8F%A4%E7%9B%91%E7%8B%B1.mp3","subcount":43,"desc":""}],"subPoiList":[{"idstring":"CurrentSubscenicId10064","name":"巴黎大皇宫主厅","cover":"https://music.gowithtommy.com/mjtt_backend_server%2Fprod%2Fdata%2F1f46bc085039cda29531c2e894dd45357d6c091b.jpg","type":4,"cityname":"巴黎","countryname":"法国","latitude":"0.000000","longitude":"0.000000","audiosUrl":"https://mjtt.gowithtommy.com/mjtt_backend_server/prod/data/82528027d5acea229c1733dca0f789b04636417a.mp3","subcount":0,"desc":""},{"idstring":"CurrentSubscenicId10414","name":"巴黎大皇宫雕塑","cover":"https://music.gowithtommy.com/mjtt_backend_server%2Fprod%2Fdata%2F6543828dc314613d7ebbe23c50d2327e252bc3b7.jpg","type":4,"cityname":"巴黎","countryname":"法国","latitude":"0.000000","longitude":"0.000000","audiosUrl":"https://mjtt.gowithtommy.com/mjtt_backend_server/prod/data/784e83f872b590a030e216da7535f0e040bffffb.mp3","subcount":0,"desc":""},{"idstring":"CurrentSubscenicId10489","name":"巴黎珠宝箱","cover":"https://music.gowithtommy.com/mjtt_backend_server%2Fprod%2Fdata%2F88a69ab80845978a366360b4a83bf473c1718462.jpg","type":4,"cityname":"巴黎","countryname":"法国","latitude":"48.867787","longitude":"2.330454","audiosUrl":"https://mjtt.gowithtommy.com/mjtt_backend_server/prod/data/728beca53b0275fd062a7436197e66d879887bbe.mp3","subcount":0,"desc":""},{"idstring":"CurrentSubscenicId10703","name":"战争画廊\u2014\u2014亨利四世进入巴黎","cover":"https://music.gowithtommy.com/mjtt_backend_server%2Fprod%2Fdata%2F2957da02182650b2214b355f7093c9ad95ce57d6.jpg","type":4,"cityname":"凡尔赛","countryname":"法国","latitude":"0.000000","longitude":"0.000000","audiosUrl":"https://mjtt.gowithtommy.com/mjtt_backend_server/prod/data/0e2acf76bac1384fa419270610aa1163530b840c.mp3","subcount":0,"desc":""},{"idstring":"CurrentSubscenicId12974","name":"《巴黎之花香槟酒瓶》","cover":"https://music.gowithtommy.com/mjtt_backend_server%2Fprod%2Fdata%2Fa9c794c4f3d8e5059dabbda2a679aad665e80607.jpg","type":4,"cityname":"箱根","countryname":"日本","latitude":"0.000000","longitude":"0.000000","audiosUrl":"https://mjtt.gowithtommy.com/mjtt_backend_server/prod/data/8f0dea9725d18c52c5ff31716473085b365354b2.mp3","subcount":0,"desc":""},{"idstring":"CurrentSubscenicId13486","name":"1754年巴黎的四轮轿式马车","cover":"https://music.gowithtommy.com/mjtt_backend_server%2Fprod%2Fdata%2Feb2bdceeb98c65f9dc676d9c7f6020ddcf250ffa.jpg","type":4,"cityname":"莫斯科","countryname":"俄罗斯","latitude":"0.000000","longitude":"0.000000","audiosUrl":"https://mjtt.gowithtommy.com/mjtt_backend_server/prod/data/014bd0e764e5087fd799f7729d306eafd36bfbca.mp3","subcount":0,"desc":""},{"idstring":"CurrentSubscenicId17162","name":"《从勒皮克街高处看巴黎的风景》","cover":"https://music.gowithtommy.com/mjtt_backend_server%2Fprod%2Fdata%2Fa5499ac765fe0f418406e99d0630c6799f728959.jpg","type":4,"cityname":"阿姆斯特丹","countryname":"荷兰","latitude":"0.000000","longitude":"0.000000","audiosUrl":"https://mjtt.gowithtommy.com/mjtt_backend_server/prod/data/54d648dbd05ef2708dd381359b28cab3575416e7.mp3","subcount":0,"desc":""},{"idstring":"CurrentSubscenicId17236","name":"巴黎社会","cover":"https://music.gowithtommy.com/mjtt_backend_server%2Fprod%2Fdata%2F239657d02971b7e3dadc3924da93751645660ef4.jpg","type":4,"cityname":"纽约","countryname":"美国","latitude":"0.000000","longitude":"0.000000","audiosUrl":"https://mjtt.gowithtommy.com/mjtt_backend_server/prod/data/d358a08e165a33b52ca0008907f8beba1c239fb8.mp3","subcount":0,"desc":""},{"idstring":"CurrentSubscenicId18696","name":"《巴黎的太阳，卢浮宫》","cover":"https://music.gowithtommy.com/mjtt_backend_server%2Fprod%2Fdata%2F67b80697c442af01c4473bd8fb55a5136b1ca91d.jpg","type":4,"cityname":"莫斯科","countryname":"俄罗斯","latitude":"0.000000","longitude":"0.000000","audiosUrl":"https://mjtt.gowithtommy.com/mjtt_backend_server/prod/data/51703165889c83a1717654e37dd1aaf4c40effb7.mp3","subcount":0,"desc":""},{"idstring":"CurrentSubscenicId18700","name":"《波本码头，冬季里的巴黎》","cover":"https://music.gowithtommy.com/mjtt_backend_server%2Fprod%2Fdata%2F05265ad69514072da34aec1a303f8f15c8b05d0d.jpg","type":4,"cityname":"莫斯科","countryname":"俄罗斯","latitude":"0.000000","longitude":"0.000000","audiosUrl":"https://mjtt.gowithtommy.com/mjtt_backend_server/prod/data/29bf1778794b57b183b64cbe9aba6a6508d7f3e7.mp3","subcount":0,"desc":""},{"idstring":"CurrentSubscenicId18832","name":"巴黎建筑艺术博物馆","cover":"https://music.gowithtommy.com/mjtt_backend_server%2Fprod%2Fdata%2F30caa0bacd2a2bf944acd2a41e92cca3353c99ae.jpg","type":4,"cityname":"巴黎","countryname":"法国","latitude":"48.862693","longitude":"2.288647","audiosUrl":"https://mjtt.gowithtommy.com/mjtt_backend_server/prod/data/492bbb96a278ab034c08cd216a9ae686cbbae722.mp3","subcount":0,"desc":""},{"idstring":"CurrentSubscenicId18834","name":"巴黎国立航海博物馆","cover":"https://music.gowithtommy.com/mjtt_backend_server%2Fprod%2Fdata%2F646373dab3289028bdecc2f44db4ac7130c48960.jpg","type":4,"cityname":"巴黎","countryname":"法国","latitude":"48.862321","longitude":"2.287483","audiosUrl":"https://mjtt.gowithtommy.com/mjtt_backend_server/prod/data/b51d44bfe7603a1b3d758c766221f161bd4e1a85.mp3","subcount":0,"desc":""},{"idstring":"CurrentSubscenicId20353","name":"巴黎岛高尔夫乡村俱乐部","cover":"https://music.gowithtommy.com/mjtt_backend_server%2Fprod%2Fdata%2F69549f2bed2d35e6d6a6cde48a9b7ef38a38d258.jpg","type":4,"cityname":"巴厘岛","countryname":"印度尼西亚","latitude":"0.000000","longitude":"0.000000","audiosUrl":"https://mjtt.gowithtommy.com/mjtt_backend_server/prod/data/e18bcd056d6d2f698c31f5e0af9361bcbd5f9de4.mp3","subcount":0,"desc":""},{"idstring":"CurrentSubscenicId21331","name":"巴黎之春购物街","cover":"https://music.gowithtommy.com/mjtt_backend_server%2Fprod%2Fdata%2Fc6a91e5bcb6b5984311b600fce40567f7b2d41ef.jpg","type":4,"cityname":"深圳","countryname":"中国大陆","latitude":"0.000000","longitude":"0.000000","audiosUrl":"https://mjtt.gowithtommy.com/mjtt_backend_server/prod/data/83882359c76b82324834a8dd540feb6ef3d43fdc.mp3","subcount":0,"desc":""},{"idstring":"CurrentSubscenicId22487","name":"巴黎高地上的风景","cover":"https://music.gowithtommy.com/mjtt_backend_server%2Fprod%2Fdata%2Fb614fa00e337dab6a0af8deeb9c3c5e27480e94b.jpg","type":4,"cityname":"巴黎","countryname":"法国","latitude":"0.000000","longitude":"0.000000","audiosUrl":"https://mjtt.gowithtommy.com/mjtt_backend_server/prod/data/fbf857c23914b50bc26ed07eeccf160e57811f80.mp3","subcount":0,"desc":""},{"idstring":"CurrentSubscenicId22614","name":"巴黎圣母院","cover":"https://music.gowithtommy.com/mjtt_backend_server%2Fprod%2Fdata%2Fdb6d582e856b1b6d0a1614ed25b6c836ef5dc9a6.jpg","type":4,"cityname":"巴黎","countryname":"法国","latitude":"0.000000","longitude":"0.000000","audiosUrl":"https://mjtt.gowithtommy.com/mjtt_backend_server/prod/data/21f40c3c0e9ad1d158d9cc16be67f4bd9f21fd9a.mp3","subcount":0,"desc":""},{"idstring":"CurrentSubscenicId22815","name":"巴黎协和广场","cover":"https://music.gowithtommy.com/mjtt_backend_server%2Fprod%2Fdata%2F991b8895b57b0f3bab334ea1e1e3eabb6319f40f.jpg","type":4,"cityname":"巴黎","countryname":"法国","latitude":"0.000000","longitude":"0.000000","audiosUrl":"https://mjtt.gowithtommy.com/mjtt_backend_server/prod/data/d0dbccf1b685d5398eeef787eee15b712968e786.mp3","subcount":0,"desc":""},{"idstring":"CurrentSubscenicId25989","name":"小说《巴黎圣母院》","cover":"https://music.gowithtommy.com/mjtt_backend_server%2Fprod%2Fdata%2Fea65b1d1cea99779e9529097baf0ea65274bde57.jpg","type":4,"cityname":"巴黎","countryname":"法国","latitude":"0.000000","longitude":"0.000000","audiosUrl":"https://mjtt.gowithtommy.com/mjtt_backend_server/prod/data/6abc12a74f7d352b4bb951db690209914b3e2c55.mp3","subcount":0,"desc":""},{"idstring":"CurrentSubscenicId25990","name":"小说《巴黎圣母院》内容简介","cover":"https://music.gowithtommy.com/mjtt_backend_server%2Fprod%2Fdata%2Fcffe68ea38a5815b461641701d87baa13dd121f0.jpg","type":4,"cityname":"巴黎","countryname":"法国","latitude":"0.000000","longitude":"0.000000","audiosUrl":"https://mjtt.gowithtommy.com/mjtt_backend_server/prod/data/449b5e81de86c1542e9553bf92725fa451698ff8.mp3","subcount":0,"desc":""},{"idstring":"CurrentSubscenicId26024","name":"巴黎中心点","cover":"https://music.gowithtommy.com/mjtt_backend_server%2Fprod%2Fdata%2F30498fbbb7f621138d80b4013e60c922286a9021.jpg","type":4,"cityname":"巴黎","countryname":"法国","latitude":"0.000000","longitude":"0.000000","audiosUrl":"https://mjtt.gowithtommy.com/mjtt_backend_server/prod/data/8fb7955316571c1b8f84c45ac7c806dfd20033ff.mp3","subcount":0,"desc":""},{"idstring":"CurrentSubscenicId26605","name":"战争画廊\u2014\u2014亨利四世进入巴黎","cover":"https://music.gowithtommy.com/mjtt_backend_server%2Fprod%2Fdata%2F2957da02182650b2214b355f7093c9ad95ce57d6.jpg","type":4,"cityname":"巴黎","countryname":"法国","latitude":"0.000000","longitude":"0.000000","audiosUrl":"https://mjtt.gowithtommy.com/mjtt_backend_server/prod/data/0e2acf76bac1384fa419270610aa1163530b840c.mp3","subcount":0,"desc":""},{"idstring":"CurrentSubscenicId26854","name":"巴黎街：雨天","cover":"https://music.gowithtommy.com/mjtt_backend_server%2Fprod%2Fdata%2F3b63f8acc87b5b653f05f0ca41fa6db4f44e1a68.jpg","type":4,"cityname":"芝加哥","countryname":"美国","latitude":"0.000000","longitude":"0.000000","audiosUrl":"https://mjtt.gowithtommy.com/mjtt_backend_server/prod/data/21421b41748d1cf0d139d36f07c78ef61ce86c59.mp3","subcount":0,"desc":""},{"idstring":"CurrentSubscenicId28317","name":"巴黎协和广场","cover":"https://music.gowithtommy.com/mjtt_backend_server%2Fprod%2Fdata%2Faf82291c7f75863ac8231748d089314dbc83792f.jpg","type":4,"cityname":"巴黎","countryname":"法国","latitude":"0.000000","longitude":"0.000000","audiosUrl":"https://mjtt.gowithtommy.com/mjtt_backend_server/prod/data/e04ed18b70ccfa25d8857154eaacbbf8b3fdd39a.mp3","subcount":0,"desc":""},{"idstring":"CurrentSubscenicId28319","name":"巴黎圣母院","cover":"https://music.gowithtommy.com/mjtt_backend_server%2Fprod%2Fdata%2Fabe599d30890cf71426001c5b844e10882ad236d.jpg","type":4,"cityname":"巴黎","countryname":"法国","latitude":"0.000000","longitude":"0.000000","audiosUrl":"https://mjtt.gowithtommy.com/mjtt_backend_server/prod/data/542afcb491ecea73bc69cb5587690fb72004e19c.mp3","subcount":0,"desc":""},{"idstring":"CurrentSubscenicId28625","name":"巴黎美术馆","cover":"https://music.gowithtommy.com/mjtt_backend_server%2Fprod%2Fdata%2F6c1515e3473f9512f2042f945ddc2af62f66f3f8.jpg","type":4,"cityname":"巴黎","countryname":"法国","latitude":"0.000000","longitude":"0.000000","audiosUrl":"https://mjtt.gowithtommy.com/mjtt_backend_server/prod/data/fd3f3e63d0433c2c1e563bb535fe881f856dc020.mp3","subcount":0,"desc":""},{"idstring":"CurrentSubscenicId28630","name":"巴黎歌剧院广场","cover":"https://music.gowithtommy.com/mjtt_backend_server%2Fprod%2Fdata%2F08f2b1cac96adca8a479f689b72f6307ada6cc6f.jpg","type":4,"cityname":"巴黎","countryname":"法国","latitude":"0.000000","longitude":"0.000000","audiosUrl":"https://mjtt.gowithtommy.com/mjtt_backend_server/prod/data/4711199c28c6bfcafdc91829c9338bb48b6305e6.mp3","subcount":0,"desc":""},{"idstring":"CurrentSubscenicId28819","name":"巴黎圣母院","cover":"https://music.gowithtommy.com/mjtt_backend_server%2Fprod%2Fdata%2F416cffe4abcf08eef11515bd28df9b6e12ceed0a.jpg","type":4,"cityname":"巴黎","countryname":"法国","latitude":"0.000000","longitude":"0.000000","audiosUrl":"https://mjtt.gowithtommy.com/mjtt_backend_server/prod/data/ba0d5fcd3c285502982ecd7b02e862e72874b35b.mp3","subcount":0,"desc":""},{"idstring":"CurrentSubscenicId29671","name":"巴黎沙滩","cover":"https://music.gowithtommy.com/mjtt_backend_server%2Fprod%2Fdata%2Fcb4fcd3dd3374e857e267101d51b55cdb27050a6.jpg","type":4,"cityname":"巴黎","countryname":"法国","latitude":"0.000000","longitude":"0.000000","audiosUrl":"https://mjtt.gowithtommy.com/mjtt_backend_server/prod/data/38fd470862a0c81479149507c3159b7e6aeb6208.mp3","subcount":0,"desc":""},{"idstring":"CurrentSubscenicId29680","name":"巴黎警察博物馆","cover":"https://music.gowithtommy.com/mjtt_backend_server%2Fprod%2Fdata%2Fa707fe5ef3715136d37e70a2e3287b17f1a7f1de.jpg","type":4,"cityname":"巴黎","countryname":"法国","latitude":"0.000000","longitude":"0.000000","audiosUrl":"https://mjtt.gowithtommy.com/mjtt_backend_server/prod/data/7e9c3bf3c9c1cc824f8e37f97220a9c7d0420f3a.mp3","subcount":0,"desc":""},{"idstring":"CurrentSubscenicId31327","name":"1871年5月的巴黎街头","cover":"https://music.gowithtommy.com/mjtt_backend_server%2Fprod%2Fdata%2F0854aa3ca0578b9cad6c108391498b4b4fc9c8ba.jpg","type":4,"cityname":"巴黎","countryname":"法国","latitude":"0.000000","longitude":"0.000000","audiosUrl":"https://mjtt.gowithtommy.com/mjtt_backend_server/prod/data/d4101a0fc4fd58f3b4609e43d86c4341b166b726.mp3","subcount":0,"desc":""},{"idstring":"CurrentSubscenicId32206","name":"1878年6月30日巴黎蒙托吉尔大街","cover":"https://music.gowithtommy.com/mjtt_backend_server%2Fprod%2Fdata%2F787406bb94bcb266dc39295ff98d7261abf8c7db.jpg","type":4,"cityname":"巴黎","countryname":"法国","latitude":"0.000000","longitude":"0.000000","audiosUrl":"https://mjtt.gowithtommy.com/mjtt_backend_server/prod/data/6491dce3d4453a461271e54e3b955a1994ae5b1f.mp3","subcount":0,"desc":""},{"idstring":"CurrentSubscenicId32558","name":"巴黎椅子","cover":"https://music.gowithtommy.com/mjtt_backend_server%2Fprod%2Fdata%2F0fb3b0b551e60048b3a8715bf9ddc94a80dcf543.jpg","type":4,"cityname":"巴黎","countryname":"法国","latitude":"0.000000","longitude":"0.000000","audiosUrl":"https://mjtt.gowithtommy.com/mjtt_backend_server/prod/data/e223fa3aca68850a3aacbb0451ac2c78d60197c6.mp3","subcount":0,"desc":""},{"idstring":"CurrentSubscenicId32845","name":"公寓大厦，巴黎6区，1 RUE海斯曼斯","cover":"https://music.gowithtommy.com/mjtt_backend_server%2Fprod%2Fdata%2F6ef92c3b1fd402eae01f6fe39011a16fa39becf6.jpg","type":4,"cityname":"巴黎","countryname":"法国","latitude":"0.000000","longitude":"0.000000","audiosUrl":"https://mjtt.gowithtommy.com/mjtt_backend_server/prod/data/294f0ae6913a7c4537037554978bb7f667ebb684.mp3","subcount":0,"desc":""},{"idstring":"CurrentSubscenicId32852","name":"1855年巴黎世博会工业馆横截面","cover":"https://music.gowithtommy.com/mjtt_backend_server%2Fprod%2Fdata%2Fb0f5af2169f9cb98c379b47a00ac81af465944fa.jpg","type":4,"cityname":"巴黎","countryname":"法国","latitude":"0.000000","longitude":"0.000000","audiosUrl":"https://mjtt.gowithtommy.com/mjtt_backend_server/prod/data/b3d731e80705be359f78481713354620d95e9789.mp3","subcount":0,"desc":""},{"idstring":"CurrentSubscenicId33181","name":"米勒、库尔贝和法国自然主义：巴黎奥赛博物馆珍藏","cover":"https://music.gowithtommy.com/mjtt_backend_server%2Fprod%2Fdata%2F4ab2428629da686a76cf3bd4a61b4b1b7f265830.jpg","type":4,"cityname":"上海","countryname":"中国大陆","latitude":"0.000000","longitude":"0.000000","audiosUrl":"https://mjtt.gowithtommy.com/mjtt_backend_server/prod/data/7fb30a2f81c2ef52109da5013ef61b37e6422526.mp3","subcount":0,"desc":""},{"idstring":"CurrentSubscenicId33825","name":"圣米歇尔码头和巴黎圣母院","cover":"https://music.gowithtommy.com/mjtt_backend_server%2Fprod%2Fdata%2Fc83b7a4256d4e349ec59c6eb417b96df03f50c02.jpg","type":4,"cityname":"巴黎","countryname":"法国","latitude":"0.000000","longitude":"0.000000","audiosUrl":"https://mjtt.gowithtommy.com/mjtt_backend_server/prod/data/a47c8ce159879b3690fb801bca02056fa0226f66.mp3","subcount":0,"desc":""},{"idstring":"CurrentSubscenicId5000","name":"巴黎古监狱西北角","cover":"https://music.gowithtommy.com/mjtt_backend_server%2Fprod%2Fdata%2F15c73d5f6ba12f2c4f385877891c4b6e0db2fa18.jpg","type":4,"cityname":"巴黎","countryname":"法国","latitude":"48.856416","longitude":"2.345421","audiosUrl":"https://mjtt.gowithtommy.com/mjtt_backend_server/prod/data/6a6033f53f20c551bec9516144c0493d72a96cff.mp3","subcount":0,"desc":""},{"idstring":"CurrentSubscenicId5002","name":"巴黎古监狱","cover":"https://music.gowithtommy.com/mjtt_backend_server%2Fprod%2Fdata%2F998be74674e782d92b161064648bfcbb367028b5.jpg","type":4,"cityname":"巴黎","countryname":"法国","latitude":"48.855992","longitude":"2.345473","audiosUrl":"https://mjtt.gowithtommy.com/mjtt_backend_server/prod/data/8bfcd8e768a36894601737514e8fbe9d72db5f7c.mp3","subcount":0,"desc":""},{"idstring":"CurrentSubscenicId6883","name":"巴黎广场","cover":"https://music.gowithtommy.com/mjtt_backend_server%2Fprod%2Fdata%2F63dce0d516defcaba1f56fba27e35ade4701ef52.jpg","type":4,"cityname":"柏林","countryname":"德国","latitude":"52.516392","longitude":"13.378815","audiosUrl":"https://mjtt.oss-cn-beijing.aliyuncs.com/mjtt_backend_server/prod/data/6c976c768eb51d12f96183c4ee007a93c7340130.mp3","subcount":0,"desc":""},{"idstring":"CurrentSubscenicId8342","name":"巴黎记忆","cover":"https://music.gowithtommy.com/mjtt_backend_server%2Fprod%2Fdata%2F8f41a89c4e5687133dbbe4d06eb6cc25547e2fa4.jpg","type":4,"cityname":"马德里","countryname":"西班牙","latitude":"0.000000","longitude":"0.000000","audiosUrl":"https://mjtt.gowithtommy.com/mjtt_backend_server/prod/data/e8659a71c158b00bd993b4fead143c6dc1f22c67.mp3","subcount":0,"desc":""},{"idstring":"CurrentSubscenicId9346","name":"圣洁纳维耶夫安慰巴黎人","cover":"https://music.gowithtommy.com/mjtt_backend_server%2Fprod%2Fdata%2F8daddc240c71d9ba0c3b8d0b290866098a1fd783.jpg","type":4,"cityname":"巴黎","countryname":"法国","latitude":"0.000000","longitude":"0.000000","audiosUrl":"https://mjtt.gowithtommy.com/mjtt_backend_server/prod/data/4843b32de3fde58cd1c931fcac9790fd104fa4ea.mp3","subcount":0,"desc":""},{"idstring":"CurrentSubscenicId9508","name":"巴黎议会的耶稣受难","cover":"https://music.gowithtommy.com/mjtt_backend_server%2Fprod%2Fdata%2F786d2793ac3b96a042392682bb42434ecc0d59bf.jpg","type":4,"cityname":"巴黎","countryname":"法国","latitude":"0.730000","longitude":"0.490000","audiosUrl":"https://mjtt.gowithtommy.com/mjtt_backend_server/prod/data/44bca1c06f178445cf4785b8741be33613cb04f3.mp3","subcount":0,"desc":""}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<CityListBean> cityList;
        private List<CityListBean> poiList;
        private List<CityListBean> subPoiList;

        public List<CityListBean> getCityList() {
            return cityList;
        }

        public void setCityList(List<CityListBean> cityList) {
            this.cityList = cityList;
        }

        public List<CityListBean> getPoiList() {
            return poiList;
        }

        public void setPoiList(List<CityListBean> poiList) {
            this.poiList = poiList;
        }

        public List<CityListBean> getSubPoiList() {
            return subPoiList;
        }

        public void setSubPoiList(List<CityListBean> subPoiList) {
            this.subPoiList = subPoiList;
        }

        public static class CityListBean {
            /**
             * idstring : CurrentCityId13
             * name : 巴黎
             * cover : https://music.gowithtommy.com/mjtt_backend_server%2Fprod%2Fdata%2F2dd9fbdd1b90f4d19e3e88e9a1361a8e4eeca53b.jpg
             * type : 2
             * cityname :
             * countryname : 法国
             * latitude : 48.861936
             * longitude : 2.355102
             * audiosUrl :
             * subcount : 40
             * desc :
             */

            private String idstring;
            private String name;
            private String cover;
            private int type;
            private String cityname;
            private String countryname;
            private String latitude;
            private String longitude;
            private String audiosUrl;
            private int subcount;
            private String desc;
            private String pidStr;

            public String getIdstring() {
                return idstring;
            }

            public void setIdstring(String idstring) {
                this.idstring = idstring;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getCover() {
                return cover;
            }

            public void setCover(String cover) {
                this.cover = cover;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getCityname() {
                return cityname;
            }

            public void setCityname(String cityname) {
                this.cityname = cityname;
            }

            public String getCountryname() {
                return countryname;
            }

            public void setCountryname(String countryname) {
                this.countryname = countryname;
            }

            public String getLatitude() {
                return latitude;
            }

            public void setLatitude(String latitude) {
                this.latitude = latitude;
            }

            public String getLongitude() {
                return longitude;
            }

            public void setLongitude(String longitude) {
                this.longitude = longitude;
            }

            public String getAudiosUrl() {
                return audiosUrl;
            }

            public void setAudiosUrl(String audiosUrl) {
                this.audiosUrl = audiosUrl;
            }

            public int getSubcount() {
                return subcount;
            }

            public void setSubcount(int subcount) {
                this.subcount = subcount;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getPidStr() {
                return pidStr;
            }

            public void setPidStr(String pidStr) {
                this.pidStr = pidStr;
            }
        }

    }
}
