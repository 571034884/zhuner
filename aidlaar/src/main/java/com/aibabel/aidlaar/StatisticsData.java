package com.aibabel.aidlaar;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 作者：SunSH on 2018/12/27 19:33
 * 功能：
 * 版本：1.0 注释整体
 * 修改：2.0 新结构
 */
public class StatisticsData implements Parcelable {

    private List<PathBean> path;
    private List<EventBean> event;
    private List<NotifyBean> notify;

    public StatisticsData() {
    }

    protected StatisticsData(Parcel in) {
        path = in.createTypedArrayList(PathBean.CREATOR);
        event = in.createTypedArrayList(EventBean.CREATOR);
        notify = in.createTypedArrayList(NotifyBean.CREATOR);
    }

    public static final Creator<StatisticsData> CREATOR = new Creator<StatisticsData>() {
        @Override
        public StatisticsData createFromParcel(Parcel in) {
            return new StatisticsData(in);
        }

        @Override
        public StatisticsData[] newArray(int size) {
            return new StatisticsData[size];
        }
    };

    public List<PathBean> getPath() {
        return path;
    }

    public void setPath(List<PathBean> path) {
        this.path = path;
    }

    public List<EventBean> getEvent() {
        return event;
    }

    public void setEvent(List<EventBean> event) {
        this.event = event;
    }

    public List<NotifyBean> getNotify() {
        return notify;
    }

    public void setNotify(List<NotifyBean> notify) {
        this.notify = notify;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(path);
        dest.writeTypedList(event);
        dest.writeTypedList(notify);
    }

    public void readFromParcel(Parcel dest) {

    }

    public static class PathBean implements Parcelable {
        /**
         * an : 小秘书
         * av : 1.1.1
         * c : [{"pn":"A页面","it":1551144279,"ot":1551144279,"i":2,"p":"携带参数-json格式字符串"},{"pn":"B页面","it":1551144279,"ot":1551144279,"i":2,"p":"携带参数"}]
         */

        private String an;
        private String av;
        private List<CBean> c;

        public PathBean() {
        }

        protected PathBean(Parcel in) {
            an = in.readString();
            av = in.readString();
            c = in.createTypedArrayList(CBean.CREATOR);
        }

        public static final Creator<PathBean> CREATOR = new Creator<PathBean>() {
            @Override
            public PathBean createFromParcel(Parcel in) {
                return new PathBean(in);
            }

            @Override
            public PathBean[] newArray(int size) {
                return new PathBean[size];
            }
        };

        public String getAn() {
            return an;
        }

        public void setAn(String an) {
            this.an = an;
        }

        public String getAv() {
            return av;
        }

        public void setAv(String av) {
            this.av = av;
        }

        public List<CBean> getC() {
            return c;
        }

        public void setC(List<CBean> c) {
            this.c = c;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(an);
            dest.writeString(av);
            dest.writeTypedList(c);
        }

        public void readFromParcel(Parcel dest) {

        }

        public static class CBean implements Parcelable {
            /**
             * pn : A页面
             * it : 1551144279
             * ot : 1551144279
             * i : 2
             * p : 携带参数-json格式字符串
             */

            private String pn;
            private long it;
            private long ot;
            private int i;
            private String p;

            public CBean() {
            }

            protected CBean(Parcel in) {
                pn = in.readString();
                it = in.readLong();
                ot = in.readLong();
                i = in.readInt();
                p = in.readString();
            }

            public static final Creator<CBean> CREATOR = new Creator<CBean>() {
                @Override
                public CBean createFromParcel(Parcel in) {
                    return new CBean(in);
                }

                @Override
                public CBean[] newArray(int size) {
                    return new CBean[size];
                }
            };

            public String getPn() {
                return pn;
            }

            public void setPn(String pn) {
                this.pn = pn;
            }

            public long getIt() {
                return it;
            }

            public void setIt(long it) {
                this.it = it;
            }

            public long getOt() {
                return ot;
            }

            public void setOt(long ot) {
                this.ot = ot;
            }

            public int getI() {
                return i;
            }

            public void setI(int i) {
                this.i = i;
            }

            public String getP() {
                return p;
            }

            public void setP(String p) {
                this.p = p;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(pn);
                dest.writeLong(it);
                dest.writeLong(ot);
                dest.writeInt(i);
                dest.writeString(p);
            }

            public void readFromParcel(Parcel dest) {

            }
        }
    }

    public static class EventBean implements Parcelable {
        /**
         * eid : 1111
         * et : 1551144279
         * p : 携带参数-json格式字符串
         */

        private int eid;
        private long et;
        private String p;

        public EventBean() {
        }

        protected EventBean(Parcel in) {
            eid = in.readInt();
            et = in.readLong();
            p = in.readString();
        }

        public static final Creator<EventBean> CREATOR = new Creator<EventBean>() {
            @Override
            public EventBean createFromParcel(Parcel in) {
                return new EventBean(in);
            }

            @Override
            public EventBean[] newArray(int size) {
                return new EventBean[size];
            }
        };

        public int getEid() {
            return eid;
        }

        public void setEid(int eid) {
            this.eid = eid;
        }

        public long getEt() {
            return et;
        }

        public void setEt(long et) {
            this.et = et;
        }

        public String getP() {
            return p;
        }

        public void setP(String p) {
            this.p = p;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(eid);
            dest.writeLong(et);
            dest.writeString(p);
        }

        public void readFromParcel(Parcel dest) {

        }
    }

    public static class NotifyBean implements Parcelable {
        /**
         * nid : 11
         * ti : 1551144279
         * c : [{"t":1,"p":"携带参数-json格式字符串"},{"t":1,"p":"携带参数-json格式字符串"},{"t":1,"p":"携带参数-json格式字符串"}]
         */

        private int nid;
        private long ti;
        private List<CBeanX> c;

        public NotifyBean() {
        }

        protected NotifyBean(Parcel in) {
            nid = in.readInt();
            ti = in.readLong();
            c = in.createTypedArrayList(CBeanX.CREATOR);
        }

        public static final Creator<NotifyBean> CREATOR = new Creator<NotifyBean>() {
            @Override
            public NotifyBean createFromParcel(Parcel in) {
                return new NotifyBean(in);
            }

            @Override
            public NotifyBean[] newArray(int size) {
                return new NotifyBean[size];
            }
        };

        public int getNid() {
            return nid;
        }

        public void setNid(int nid) {
            this.nid = nid;
        }

        public long getTi() {
            return ti;
        }

        public void setTi(long ti) {
            this.ti = ti;
        }

        public List<CBeanX> getC() {
            return c;
        }

        public void setC(List<CBeanX> c) {
            this.c = c;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(nid);
            dest.writeLong(ti);
            dest.writeTypedList(c);
        }

        public void readFromParcel(Parcel dest) {

        }

        public static class CBeanX implements Parcelable {
            /**
             * t : 1
             * p : 携带参数-json格式字符串
             */

            private int t;
            private String p;

            public CBeanX() {
            }

            protected CBeanX(Parcel in) {
                t = in.readInt();
                p = in.readString();
            }

            public static final Creator<CBeanX> CREATOR = new Creator<CBeanX>() {
                @Override
                public CBeanX createFromParcel(Parcel in) {
                    return new CBeanX(in);
                }

                @Override
                public CBeanX[] newArray(int size) {
                    return new CBeanX[size];
                }
            };

            public int getT() {
                return t;
            }

            public void setT(int t) {
                this.t = t;
            }

            public String getP() {
                return p;
            }

            public void setP(String p) {
                this.p = p;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(t);
                dest.writeString(p);
            }

            public void readFromParcel(Parcel dest) {

            }
        }
    }

//    /**
//     * appName : 小秘书
//     * appVersion : 1.1.1
//     * path : [{"name":"activity","content":[{"entry":"2019年1月21日11:15:03","exit":"2019年1月21日11:50:28","interactionTimes":1,"keyWord":"携带参数"},{"entry":"2019年1月21日11:15:03","exit":"2019年1月21日11:50:28","interactionTimes":1,"keyWord":"携带参数"}]}]
//     * event : [{"name":"点击事件","content":[{"time":"2019年1月21日11:15:03","descirbe":"xxxxxxxxxxxx","keyWord":"所在城市"},{"time":"2019年1月21日11:15:03","descirbe":"xxxxxxxxxxxx","keyWord":"所在城市"}]},{"name":"跳转事件","content":[{"time":"2019年1月21日11:15:03","descirbe":"xxxxxxxxxxxx","keyWord":"所在城市"},{"time":"2019年1月21日11:15:03","descirbe":"xxxxxxxxxxxx","keyWord":"所在城市"}]}]
//     * notify : [{"type":"通知类型","content":[{"time":"2019年1月21日11:15:03","descirbe":"xxxxxxxxxxxx","scope":"作用范围","consulted":"Boolean是否被查阅"},{"time":"2019年1月21日11:15:03","descirbe":"xxxxxxxxxxxx","scope":"作用范围","consulted":"Boolean是否被查阅"}]}]
//     */
//
//    private String appName;
//    private String appVersion;
//    private List<PathBean> path;
//    private List<EventBean> event;
//    private List<NotifyBean> notify;
//
//    public StatisticsData() {
//    }
//
//    public void readFromParcel(Parcel dest) {
//    }
//
//    protected StatisticsData(Parcel in) {
//        appName = in.readString();
//        appVersion = in.readString();
//    }
//
//    public static final Creator<StatisticsData> CREATOR = new Creator<StatisticsData>() {
//        @Override
//        public StatisticsData createFromParcel(Parcel in) {
//            return new StatisticsData(in);
//        }
//
//        @Override
//        public StatisticsData[] newArray(int size) {
//            return new StatisticsData[size];
//        }
//    };
//
//    public String getAppName() {
//        return appName;
//    }
//
//    public void setAppName(String appName) {
//        this.appName = appName;
//    }
//
//    public String getAppVersion() {
//        return appVersion;
//    }
//
//    public void setAppVersion(String appVersion) {
//        this.appVersion = appVersion;
//    }
//
//    public List<PathBean> getPath() {
//        return path;
//    }
//
//    public void setPath(List<PathBean> path) {
//        this.path = path;
//    }
//
//    public List<EventBean> getEvent() {
//        return event;
//    }
//
//    public void setEvent(List<EventBean> event) {
//        this.event = event;
//    }
//
//    public List<NotifyBean> getNotify() {
//        return notify;
//    }
//
//    public void setNotify(List<NotifyBean> notify) {
//        this.notify = notify;
//    }
//
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel parcel, int i) {
//        parcel.writeString(appName);
//        parcel.writeString(appVersion);
//    }
//
//    public static class PathBean implements Parcelable {
//        /**
//         * name : activity
//         * content : [{"entry":"2019年1月21日11:15:03","exit":"2019年1月21日11:50:28","interactionTimes":1,"keyWord":"携带参数"},{"entry":"2019年1月21日11:15:03","exit":"2019年1月21日11:50:28","interactionTimes":1,"keyWord":"携带参数"}]
//         */
//
//        private String name;
//        private List<PathContentBean> content;
//
//        public PathBean() {
//        }
//
//        public void readFromParcel(Parcel dest) {
//
//        }
//
//        protected PathBean(Parcel in) {
//            name = in.readString();
//        }
//
//        public static final Creator<PathBean> CREATOR = new Creator<PathBean>() {
//            @Override
//            public PathBean createFromParcel(Parcel in) {
//                return new PathBean(in);
//            }
//
//            @Override
//            public PathBean[] newArray(int size) {
//                return new PathBean[size];
//            }
//        };
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//
//        public List<PathContentBean> getContent() {
//            return content;
//        }
//
//        public void setContent(List<PathContentBean> content) {
//            this.content = content;
//        }
//
//        @Override
//        public int describeContents() {
//            return 0;
//        }
//
//        @Override
//        public void writeToParcel(Parcel parcel, int i) {
//            parcel.writeString(name);
//        }
//
//        public static class PathContentBean implements Parcelable {
//            /**
//             * entry : 2019年1月21日11:15:03
//             * exit : 2019年1月21日11:50:28
//             * interactionTimes : 1
//             * keyWord : 携带参数
//             */
//
//            private long entry;
//            private long exit;
//            private int interactionTimes;
//            private String keyWord;
//
//            public PathContentBean() {
//            }
//
//            public long getEntry() {
//                return entry;
//            }
//
//            public void setEntry(long entry) {
//                this.entry = entry;
//            }
//
//            public long getExit() {
//                return exit;
//            }
//
//            public void setExit(long exit) {
//                this.exit = exit;
//            }
//
//            public int getInteractionTimes() {
//                return interactionTimes;
//            }
//
//            public void setInteractionTimes(int interactionTimes) {
//                this.interactionTimes = interactionTimes;
//            }
//
//            public String getKeyWord() {
//                return keyWord;
//            }
//
//            public void setKeyWord(String keyWord) {
//                this.keyWord = keyWord;
//            }
//
//            protected PathContentBean(Parcel in) {
//                entry = in.readLong();
//                exit = in.readLong();
//                interactionTimes = in.readInt();
//                keyWord = in.readString();
//            }
//
//            public static final Creator<PathContentBean> CREATOR = new Creator<PathContentBean>() {
//                @Override
//                public PathContentBean createFromParcel(Parcel in) {
//                    return new PathContentBean(in);
//                }
//
//                @Override
//                public PathContentBean[] newArray(int size) {
//                    return new PathContentBean[size];
//                }
//            };
//
//            public void readFromParcel(Parcel dest) {
//
//            }
//
//            @Override
//            public int describeContents() {
//                return 0;
//            }
//
//            @Override
//            public void writeToParcel(Parcel parcel, int i) {
//                parcel.writeLong(entry);
//                parcel.writeLong(exit);
//                parcel.writeInt(interactionTimes);
//                parcel.writeString(keyWord);
//            }
//        }
//    }
//
//    public static class EventBean implements Parcelable {
//        /**
//         * name : 点击事件
//         * content : [{"time":"2019年1月21日11:15:03","descirbe":"xxxxxxxxxxxx","keyWord":"所在城市"},{"time":"2019年1月21日11:15:03","descirbe":"xxxxxxxxxxxx","keyWord":"所在城市"}]
//         */
//
//        private String name;
//        private List<EventContentBean> content;
//
//        public EventBean() {
//        }
//
//        public void readFromParcel(Parcel dest) {
//
//        }
//
//        protected EventBean(Parcel in) {
//            name = in.readString();
//        }
//
//        public static final Creator<EventBean> CREATOR = new Creator<EventBean>() {
//            @Override
//            public EventBean createFromParcel(Parcel in) {
//                return new EventBean(in);
//            }
//
//            @Override
//            public EventBean[] newArray(int size) {
//                return new EventBean[size];
//            }
//        };
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//
//        public List<EventContentBean> getContent() {
//            return content;
//        }
//
//        public void setContent(List<EventContentBean> content) {
//            this.content = content;
//        }
//
//        @Override
//        public int describeContents() {
//            return 0;
//        }
//
//        @Override
//        public void writeToParcel(Parcel parcel, int i) {
//            parcel.writeString(name);
//        }
//
//        public static class EventContentBean implements Parcelable {
//            /**
//             * time : 2019年1月21日11:15:03
//             * descirbe : xxxxxxxxxxxx
//             * keyWord : 所在城市
//             */
//
//            private long time;
//            private String descirbe;
//            private String keyWord;
//
//            public long getTime() {
//                return time;
//            }
//
//            public void setTime(long time) {
//                this.time = time;
//            }
//
//            public String getDescirbe() {
//                return descirbe;
//            }
//
//            public void setDescirbe(String descirbe) {
//                this.descirbe = descirbe;
//            }
//
//            public String getKeyWord() {
//                return keyWord;
//            }
//
//            public void setKeyWord(String keyWord) {
//                this.keyWord = keyWord;
//            }
//
//            public EventContentBean() {
//            }
//
//            protected EventContentBean(Parcel in) {
//                time = in.readLong();
//                descirbe = in.readString();
//                keyWord = in.readString();
//            }
//
//            public static final Creator<EventContentBean> CREATOR = new Creator<EventContentBean>() {
//                @Override
//                public EventContentBean createFromParcel(Parcel in) {
//                    return new EventContentBean(in);
//                }
//
//                @Override
//                public EventContentBean[] newArray(int size) {
//                    return new EventContentBean[size];
//                }
//            };
//
//            public void readFromParcel(Parcel dest) {
//
//            }
//
//            @Override
//            public int describeContents() {
//                return 0;
//            }
//
//            @Override
//            public void writeToParcel(Parcel parcel, int i) {
//                parcel.writeLong(time);
//                parcel.writeString(descirbe);
//                parcel.writeString(keyWord);
//            }
//        }
//    }
//
//    public static class NotifyBean implements Parcelable {
//        /**
//         * type : 通知类型
//         * content : [{"time":"2019年1月21日11:15:03","descirbe":"xxxxxxxxxxxx","scope":"作用范围","consulted":"Boolean是否被查阅"},{"time":"2019年1月21日11:15:03","descirbe":"xxxxxxxxxxxx","scope":"作用范围","consulted":"Boolean是否被查阅"}]
//         */
//
//        private int type;
//        private List<NotifyContentBean> content;
//
//        public int getType() {
//            return type;
//        }
//
//        public void setType(int type) {
//            this.type = type;
//        }
//
//        public List<NotifyContentBean> getContent() {
//            return content;
//        }
//
//        public void setContent(List<NotifyContentBean> content) {
//            this.content = content;
//        }
//
//        public NotifyBean() {
//        }
//
//        public void readFromParcel(Parcel dest) {
//
//        }
//
//        protected NotifyBean(Parcel in) {
//            type = in.readInt();
//            content = in.createTypedArrayList(NotifyContentBean.CREATOR);
//        }
//
//        public static final Creator<NotifyBean> CREATOR = new Creator<NotifyBean>() {
//            @Override
//            public NotifyBean createFromParcel(Parcel in) {
//                return new NotifyBean(in);
//            }
//
//            @Override
//            public NotifyBean[] newArray(int size) {
//                return new NotifyBean[size];
//            }
//        };
//
//        @Override
//        public int describeContents() {
//            return 0;
//        }
//
//        @Override
//        public void writeToParcel(Parcel parcel, int i) {
//            parcel.writeInt(type);
//            parcel.writeTypedList(content);
//        }
//
//
//        public static class NotifyContentBean implements Parcelable {
//            /**
//             * time : 2019年1月21日11:15:03
//             * descirbe : xxxxxxxxxxxx
//             * scope : 作用范围
//             * consulted : Boolean是否被查阅
//             */
//
//            private long time;
//            private String descirbe;
//            private String scope;
//            private boolean consulted;
//
//            public long getTime() {
//                return time;
//            }
//
//            public void setTime(long time) {
//                this.time = time;
//            }
//
//            public String getDescirbe() {
//                return descirbe;
//            }
//
//            public void setDescirbe(String descirbe) {
//                this.descirbe = descirbe;
//            }
//
//            public String getScope() {
//                return scope;
//            }
//
//            public void setScope(String scope) {
//                this.scope = scope;
//            }
//
//            public boolean isConsulted() {
//                return consulted;
//            }
//
//            public void setConsulted(boolean consulted) {
//                this.consulted = consulted;
//            }
//
//            public NotifyContentBean() {
//            }
//
//            protected NotifyContentBean(Parcel in) {
//                time = in.readLong();
//                descirbe = in.readString();
//                scope = in.readString();
//                consulted = in.readByte() != 0;
//            }
//
//            public static final Creator<NotifyContentBean> CREATOR = new Creator<NotifyContentBean>() {
//                @Override
//                public NotifyContentBean createFromParcel(Parcel in) {
//                    return new NotifyContentBean(in);
//                }
//
//                @Override
//                public NotifyContentBean[] newArray(int size) {
//                    return new NotifyContentBean[size];
//                }
//            };
//
//            public void readFromParcel(Parcel dest) {
//
//            }
//
//            @Override
//            public int describeContents() {
//                return 0;
//            }
//
//            @Override
//            public void writeToParcel(Parcel parcel, int i) {
//                parcel.writeLong(time);
//                parcel.writeString(descirbe);
//                parcel.writeString(scope);
//                parcel.writeByte((byte) (consulted ? 1 : 0));
//            }
//        }
//    }
}
