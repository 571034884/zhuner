package com.aibabel.aidlaar;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 作者：SunSH on 2018/12/27 19:33
 * 功能：
 * 版本：1.0
 */
public class StatisticsData implements Parcelable {

    /**
     * appName : 小秘书
     * appVersion : 1.1.1
     * path : [{"name":"activity","content":[{"entry":"2019年1月21日11:15:03","exit":"2019年1月21日11:50:28","interactionTimes":1,"keyWord":"携带参数"},{"entry":"2019年1月21日11:15:03","exit":"2019年1月21日11:50:28","interactionTimes":1,"keyWord":"携带参数"}]}]
     * event : [{"name":"点击事件","content":[{"time":"2019年1月21日11:15:03","descirbe":"xxxxxxxxxxxx","keyWord":"所在城市"},{"time":"2019年1月21日11:15:03","descirbe":"xxxxxxxxxxxx","keyWord":"所在城市"}]},{"name":"跳转事件","content":[{"time":"2019年1月21日11:15:03","descirbe":"xxxxxxxxxxxx","keyWord":"所在城市"},{"time":"2019年1月21日11:15:03","descirbe":"xxxxxxxxxxxx","keyWord":"所在城市"}]}]
     * notify : [{"type":"通知类型","content":[{"time":"2019年1月21日11:15:03","descirbe":"xxxxxxxxxxxx","scope":"作用范围","consulted":"Boolean是否被查阅"},{"time":"2019年1月21日11:15:03","descirbe":"xxxxxxxxxxxx","scope":"作用范围","consulted":"Boolean是否被查阅"}]}]
     */

    private String appName;
    private String appVersion;
    private List<PathBean> path;
    private List<EventBean> event;
    private List<NotifyBean> notify;

    public StatisticsData() {
    }

    public void readFromParcel(Parcel dest) {
    }

    protected StatisticsData(Parcel in) {
        appName = in.readString();
        appVersion = in.readString();
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

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

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
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(appName);
        parcel.writeString(appVersion);
    }

    public static class PathBean implements Parcelable {
        /**
         * name : activity
         * content : [{"entry":"2019年1月21日11:15:03","exit":"2019年1月21日11:50:28","interactionTimes":1,"keyWord":"携带参数"},{"entry":"2019年1月21日11:15:03","exit":"2019年1月21日11:50:28","interactionTimes":1,"keyWord":"携带参数"}]
         */

        private String name;
        private List<PathContentBean> content;

        public PathBean() {
        }

        public void readFromParcel(Parcel dest) {

        }

        protected PathBean(Parcel in) {
            name = in.readString();
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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<PathContentBean> getContent() {
            return content;
        }

        public void setContent(List<PathContentBean> content) {
            this.content = content;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(name);
        }

        public static class PathContentBean implements Parcelable {
            /**
             * entry : 2019年1月21日11:15:03
             * exit : 2019年1月21日11:50:28
             * interactionTimes : 1
             * keyWord : 携带参数
             */

            private long entry;
            private long exit;
            private int interactionTimes;
            private String keyWord;

            public PathContentBean() {
            }

            public long getEntry() {
                return entry;
            }

            public void setEntry(long entry) {
                this.entry = entry;
            }

            public long getExit() {
                return exit;
            }

            public void setExit(long exit) {
                this.exit = exit;
            }

            public int getInteractionTimes() {
                return interactionTimes;
            }

            public void setInteractionTimes(int interactionTimes) {
                this.interactionTimes = interactionTimes;
            }

            public String getKeyWord() {
                return keyWord;
            }

            public void setKeyWord(String keyWord) {
                this.keyWord = keyWord;
            }

            protected PathContentBean(Parcel in) {
                entry = in.readLong();
                exit = in.readLong();
                interactionTimes = in.readInt();
                keyWord = in.readString();
            }

            public static final Creator<PathContentBean> CREATOR = new Creator<PathContentBean>() {
                @Override
                public PathContentBean createFromParcel(Parcel in) {
                    return new PathContentBean(in);
                }

                @Override
                public PathContentBean[] newArray(int size) {
                    return new PathContentBean[size];
                }
            };

            public void readFromParcel(Parcel dest) {

            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel parcel, int i) {
                parcel.writeLong(entry);
                parcel.writeLong(exit);
                parcel.writeInt(interactionTimes);
                parcel.writeString(keyWord);
            }
        }
    }

    public static class EventBean implements Parcelable {
        /**
         * name : 点击事件
         * content : [{"time":"2019年1月21日11:15:03","descirbe":"xxxxxxxxxxxx","keyWord":"所在城市"},{"time":"2019年1月21日11:15:03","descirbe":"xxxxxxxxxxxx","keyWord":"所在城市"}]
         */

        private String name;
        private List<EventContentBean> content;

        public EventBean() {
        }

        public void readFromParcel(Parcel dest) {

        }

        protected EventBean(Parcel in) {
            name = in.readString();
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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<EventContentBean> getContent() {
            return content;
        }

        public void setContent(List<EventContentBean> content) {
            this.content = content;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(name);
        }

        public static class EventContentBean implements Parcelable {
            /**
             * time : 2019年1月21日11:15:03
             * descirbe : xxxxxxxxxxxx
             * keyWord : 所在城市
             */

            private long time;
            private String descirbe;
            private String keyWord;

            public long getTime() {
                return time;
            }

            public void setTime(long time) {
                this.time = time;
            }

            public String getDescirbe() {
                return descirbe;
            }

            public void setDescirbe(String descirbe) {
                this.descirbe = descirbe;
            }

            public String getKeyWord() {
                return keyWord;
            }

            public void setKeyWord(String keyWord) {
                this.keyWord = keyWord;
            }

            public EventContentBean() {
            }

            protected EventContentBean(Parcel in) {
                time = in.readLong();
                descirbe = in.readString();
                keyWord = in.readString();
            }

            public static final Creator<EventContentBean> CREATOR = new Creator<EventContentBean>() {
                @Override
                public EventContentBean createFromParcel(Parcel in) {
                    return new EventContentBean(in);
                }

                @Override
                public EventContentBean[] newArray(int size) {
                    return new EventContentBean[size];
                }
            };

            public void readFromParcel(Parcel dest) {

            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel parcel, int i) {
                parcel.writeLong(time);
                parcel.writeString(descirbe);
                parcel.writeString(keyWord);
            }
        }
    }

    public static class NotifyBean implements Parcelable {
        /**
         * type : 通知类型
         * content : [{"time":"2019年1月21日11:15:03","descirbe":"xxxxxxxxxxxx","scope":"作用范围","consulted":"Boolean是否被查阅"},{"time":"2019年1月21日11:15:03","descirbe":"xxxxxxxxxxxx","scope":"作用范围","consulted":"Boolean是否被查阅"}]
         */

        private int type;
        private List<NotifyContentBean> content;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public List<NotifyContentBean> getContent() {
            return content;
        }

        public void setContent(List<NotifyContentBean> content) {
            this.content = content;
        }

        public NotifyBean() {
        }

        public void readFromParcel(Parcel dest) {

        }

        protected NotifyBean(Parcel in) {
            type = in.readInt();
            content = in.createTypedArrayList(NotifyContentBean.CREATOR);
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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(type);
            parcel.writeTypedList(content);
        }


        public static class NotifyContentBean implements Parcelable {
            /**
             * time : 2019年1月21日11:15:03
             * descirbe : xxxxxxxxxxxx
             * scope : 作用范围
             * consulted : Boolean是否被查阅
             */

            private long time;
            private String descirbe;
            private String scope;
            private boolean consulted;

            public long getTime() {
                return time;
            }

            public void setTime(long time) {
                this.time = time;
            }

            public String getDescirbe() {
                return descirbe;
            }

            public void setDescirbe(String descirbe) {
                this.descirbe = descirbe;
            }

            public String getScope() {
                return scope;
            }

            public void setScope(String scope) {
                this.scope = scope;
            }

            public boolean isConsulted() {
                return consulted;
            }

            public void setConsulted(boolean consulted) {
                this.consulted = consulted;
            }

            public NotifyContentBean() {
            }

            protected NotifyContentBean(Parcel in) {
                time = in.readLong();
                descirbe = in.readString();
                scope = in.readString();
                consulted = in.readByte() != 0;
            }

            public static final Creator<NotifyContentBean> CREATOR = new Creator<NotifyContentBean>() {
                @Override
                public NotifyContentBean createFromParcel(Parcel in) {
                    return new NotifyContentBean(in);
                }

                @Override
                public NotifyContentBean[] newArray(int size) {
                    return new NotifyContentBean[size];
                }
            };

            public void readFromParcel(Parcel dest) {

            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel parcel, int i) {
                parcel.writeLong(time);
                parcel.writeString(descirbe);
                parcel.writeString(scope);
                parcel.writeByte((byte) (consulted ? 1 : 0));
            }
        }
    }
}
