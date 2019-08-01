package com.andalus.mohamedawadtasks.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = DatabaseApp.TABLE_NAME)
public class UsersEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "name")
    private String userName;

    @ColumnInfo(name = "gender")
    private String userGender;

    @ColumnInfo(name = "age")
    private String userAge;

    @ColumnInfo(name = "eye_color")
    private String userEyeColor;

    public UsersEntity(String userName, String userGender, String userAge, String userEyeColor) {
        this.userName = userName;
        this.userGender = userGender;
        this.userAge = userAge;
        this.userEyeColor = userEyeColor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    public String getUserAge() {
        return userAge;
    }

    public void setUserAge(String userAge) {
        this.userAge = userAge;
    }

    public String getUserEyeColor() {
        return userEyeColor;
    }

    public void setUserEyeColor(String userEyeColor) {
        this.userEyeColor = userEyeColor;
    }
}
