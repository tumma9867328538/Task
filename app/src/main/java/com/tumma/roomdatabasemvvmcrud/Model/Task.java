package com.tumma.roomdatabasemvvmcrud.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "task_table")
public class Task implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "task")
    private String task;

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "starttime")
    private String starttime;

    @ColumnInfo(name = "endtime")
    private String endtime;

    @ColumnInfo(name = "descr")
    private String desc;

    public Task(int id, String task, String date, String starttime, String endtime, String desc, String category) {
        this.id = id;
        this.task = task;
        this.date = date;
        this.starttime = starttime;
        this.endtime = endtime;
        this.desc = desc;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @ColumnInfo(name = "category")
    private String category;



    /*
     * Getters and Setters
     * */

}
