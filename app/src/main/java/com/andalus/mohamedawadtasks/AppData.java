package com.andalus.mohamedawadtasks;

public class AppData {

    private String appName;
    private int appSize;
    private int appImage;
    private String appDescription;

    public AppData(String appName, int appSize, int appImage , String appDescription) {
        this.appName = appName;
        this.appSize = appSize;
        this.appImage = appImage;
        this.appDescription = appDescription;
    }

    public String getAppName() {
        return appName;
    }

    public int getAppSize() {
        return appSize;
    }

    public int getAppImage() {
        return appImage;
    }

    public String getAppDescription() {
        return appDescription;
    }


}
