package com.aibabel.download.offline.bean;

public class Offline_database {
    private String id;

    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;

    }

    public String getName() {
        return name;
    }



    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    private String name;
    private String size;
    private String status;
    private String copyPath;

    public String getCopyPath() {
        return copyPath;
    }

    public void setCopyPath(String copyPath) {
        this.copyPath = copyPath;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Offline_database(String id, String name, String size, String status, String copyPath) {
        this.id = id;
        this.name = name;
        this.size = size;
        this.status = status;
        this.copyPath = copyPath;
    }

    public Offline_database() {
    }

    @Override
    public String toString() {
        return "Offline_database{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", size='" + size + '\'' +
                ", status='" + status + '\'' +
                ", copyPath='" + copyPath + '\'' +
                '}';
    }
}
