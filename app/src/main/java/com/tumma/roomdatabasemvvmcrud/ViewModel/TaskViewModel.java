package com.tumma.roomdatabasemvvmcrud.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.tumma.roomdatabasemvvmcrud.Model.Task;
import com.tumma.roomdatabasemvvmcrud.Repository.TaskRepository;

import java.util.List;

public class TaskViewModel extends AndroidViewModel {

    private TaskRepository repository;
    private LiveData<List<Task>> alltasks;
    private LiveData<List<Task>> alltasksbydate;

    public TaskViewModel(@NonNull Application application) {
        super(application);
        repository = new TaskRepository(application);
        alltasks = repository.getAlltasks();
//        alltasksbydate = repository.getAlltasksbyDate();
    }

    public LiveData<List<Task>> getAlltasksbydate(String date) {
        return repository.getAlltasksbyDate(date);
    }
    public void insert(Task Task) {
        repository.insert(Task);
    }

    public void update(Task Task) {
        repository.update(Task);
    }

    public  void delete(Task Task) {
        repository.delete(Task);
    }

    public void deleteAlltasks() {
        repository.deleteAlltasks();
    }

    public LiveData<List<Task>> getAlltasks() {
        return alltasks;
    }

//    public LiveData<List<Task>> getAlltasksbyDate() {
//        return alltasksbydate;
//    }
}
