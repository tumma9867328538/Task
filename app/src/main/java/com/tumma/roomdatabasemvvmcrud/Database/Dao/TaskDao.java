package com.tumma.roomdatabasemvvmcrud.Database.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.tumma.roomdatabasemvvmcrud.Model.Task;

import java.util.List;

@Dao
public interface TaskDao {

    //Insert new Task in table
    @Insert
    void insert(Task Task);

    //Update single Task in table
    @Update
    void update(Task Task);

    //Delete single Task from table
    @Delete
    void delete(Task Task);

    //Delete all Task from table
    @Query("DELETE FROM task_table")
    void deleteAllTask();

    //Get all the list of Task from table by descending order
    @Query("SELECT * FROM task_table ")
    LiveData<List<Task>> getAlltasks();

    @Query("SELECT * FROM task_table where date=:date")
    LiveData<List<Task>> getAlltasksbydate(String date);

}
