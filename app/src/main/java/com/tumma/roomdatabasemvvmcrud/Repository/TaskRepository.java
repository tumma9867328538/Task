package com.tumma.roomdatabasemvvmcrud.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.tumma.roomdatabasemvvmcrud.Database.Dao.TaskDao;
import com.tumma.roomdatabasemvvmcrud.Database.TaskDatabase;
import com.tumma.roomdatabasemvvmcrud.Model.Task;

import java.util.List;

public class TaskRepository {

    private TaskDao TaskDao;
    private LiveData<List<Task>> alltasks;
    private LiveData<List<Task>> alltasksbydate;

    public TaskRepository(Application application) {
        TaskDatabase TaskDatabase = com.tumma.roomdatabasemvvmcrud.Database.TaskDatabase.getInstance(application);
        TaskDao = TaskDatabase.TaskDao();
        alltasks = TaskDao.getAlltasks();
        //alltasksbydate = TaskDao.getAlltasksbydate();
    }

    public void insert(Task Task) {
        new InsertTaskAsyncTask(TaskDao).execute(Task);
    }

    public void update(Task Task) {
        new UpdateTaskAsyncTask(TaskDao).execute(Task);
    }

    public void delete(Task Task) {
        new DeleteTaskAsyncTask(TaskDao).execute(Task);
    }

    public void deleteAlltasks() {
        new DeleteAlltasksAsyncTask(TaskDao).execute();
    }

    public LiveData<List<Task>> getAlltasks() {
        return alltasks;
    }

    public LiveData<List<Task>> getAlltasksbyDate(String date) {
        return TaskDao.getAlltasksbydate(date);
    }

    //AsyncTask for create new Task
    private static class InsertTaskAsyncTask extends AsyncTask<Task, Void, Void> {

        private TaskDao TaskDao;

        public InsertTaskAsyncTask(TaskDao TaskDao) {
            this.TaskDao = TaskDao;
        }


        @Override
        protected Void doInBackground(Task... tasks) {
            TaskDao.insert(tasks[0]);
            return null;
        }
    }

    //AsyncTask for update existing Task
    private static class UpdateTaskAsyncTask extends AsyncTask<Task, Void, Void> {

        private TaskDao TaskDao;

        public UpdateTaskAsyncTask(TaskDao TaskDao) {
            this.TaskDao = TaskDao;
        }

        @Override
        protected Void doInBackground(Task... tasks) {
            TaskDao.update(tasks[0]);
            return null;
        }
    }

    //AsyncTask for delete existing Task
    private static class DeleteTaskAsyncTask extends AsyncTask<Task, Void, Void> {

        private TaskDao TaskDao;

        public DeleteTaskAsyncTask(TaskDao TaskDao) {
            this.TaskDao = TaskDao;
        }

        @Override
        protected Void doInBackground(Task... tasks) {
            TaskDao.delete(tasks[0]);
            return null;
        }
    }

    //AsyncTask for delete all tasks
    private static class DeleteAlltasksAsyncTask extends AsyncTask<Void, Void, Void> {

        private TaskDao TaskDao;

        public DeleteAlltasksAsyncTask(TaskDao TaskDao) {
            this.TaskDao = TaskDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            TaskDao.deleteAllTask();
            return null;
        }
    }


}
