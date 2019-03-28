package com.example.gamezale.Database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

@android.arch.persistence.room.Database(entities = {Employee.class, Contact.class}, version = 8, exportSchema = false)
public abstract class Database extends RoomDatabase
{
    public abstract EmployeeDao employeeDao();
    public abstract ContactDao contactDao();
    private static Database INSTANCE;
    private static final String DB_NAME = "ucard.db";

    public static Database getDatabase(final Context context)
    {
        if (INSTANCE == null) {
            synchronized (Database.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), Database.class, DB_NAME)
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .addCallback(new Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);
                                    Log.d("ucard database", "populating with data...");
                                    new PopulateDbAsync(INSTANCE).execute();
                                }
                            }).build();
                }
            }
        }
        return INSTANCE;
    }

    public void clearDb()
    {
        if (INSTANCE != null) {
            new PopulateDbAsync(INSTANCE).execute();
        }
        employeeDao().deleteAll();
        contactDao().deleteAll();
    }

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void>
    {
        private final EmployeeDao employeeDao;
        private final ContactDao contactDao;

        public PopulateDbAsync(Database instance)
        {
            employeeDao = instance.employeeDao();
            contactDao = instance.contactDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}

