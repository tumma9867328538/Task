package com.tumma.roomdatabasemvvmcrud.Database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.tumma.roomdatabasemvvmcrud.Model.Task;
import com.tumma.roomdatabasemvvmcrud.Database.Dao.TaskDao;

@Database(entities = {Task.class},version = 1)
public abstract class TaskDatabase extends RoomDatabase {

    private static TaskDatabase instance;

    public abstract TaskDao TaskDao();

    public static synchronized TaskDatabase getInstance(Context context)
    {
        if(instance == null)
        {
            //If instance is null that's mean database is not created and create a new database instance
            instance = Room.databaseBuilder(context.getApplicationContext(),TaskDatabase.class,"task_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .build();
        }

        return instance;
    }

    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback(){

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
//            new PopulateDbAsyncTask(instance).execute();
        }
    };

//    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
//
//        private TaskDao TaskDao;
//
//        public PopulateDbAsyncTask(TaskDatabase db) {
//            this.TaskDao = db.TaskDao();
//        }
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//            TaskDao.insert(new Task("Arther","Midfielder","23"));
//            TaskDao.insert(new Task("Suarez","Forward","32"));
//            TaskDao.insert(new Task("Griezmann","Forward","28"));
//            return null;
//        }
//    }


}
