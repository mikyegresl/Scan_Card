package com.example.gamezale.Database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.db.SupportSQLiteOpenHelper.Callback;
import android.arch.persistence.db.SupportSQLiteOpenHelper.Configuration;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.RoomOpenHelper;
import android.arch.persistence.room.RoomOpenHelper.Delegate;
import android.arch.persistence.room.util.TableInfo;
import android.arch.persistence.room.util.TableInfo.Column;
import android.arch.persistence.room.util.TableInfo.ForeignKey;
import android.arch.persistence.room.util.TableInfo.Index;
import java.lang.IllegalStateException;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.HashSet;

@SuppressWarnings("unchecked")
public class Database_Impl extends Database {
  private volatile EmployeeDao _employeeDao;

  private volatile ContactDao _contactDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(8) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `current_user` (`mobile_phone` TEXT NOT NULL, `work_phone` TEXT, `home_phone` TEXT, `fullname` TEXT NOT NULL, `email` TEXT, `company` TEXT, `position` TEXT, `website` TEXT, `street` TEXT, `city` TEXT, PRIMARY KEY(`mobile_phone`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `contacts` (`mobile_phone` TEXT NOT NULL, `full_name` TEXT NOT NULL, `work_phone` TEXT, `home_phone` TEXT, `email` TEXT, `group` TEXT, `company` TEXT, `position` TEXT, `website` TEXT, `street` TEXT, `city` TEXT, PRIMARY KEY(`mobile_phone`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, \"29bf8ce52f3028cff7afccdb2940730e\")");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `current_user`");
        _db.execSQL("DROP TABLE IF EXISTS `contacts`");
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      protected void validateMigration(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsCurrentUser = new HashMap<String, TableInfo.Column>(10);
        _columnsCurrentUser.put("mobile_phone", new TableInfo.Column("mobile_phone", "TEXT", true, 1));
        _columnsCurrentUser.put("work_phone", new TableInfo.Column("work_phone", "TEXT", false, 0));
        _columnsCurrentUser.put("home_phone", new TableInfo.Column("home_phone", "TEXT", false, 0));
        _columnsCurrentUser.put("fullname", new TableInfo.Column("fullname", "TEXT", true, 0));
        _columnsCurrentUser.put("email", new TableInfo.Column("email", "TEXT", false, 0));
        _columnsCurrentUser.put("company", new TableInfo.Column("company", "TEXT", false, 0));
        _columnsCurrentUser.put("position", new TableInfo.Column("position", "TEXT", false, 0));
        _columnsCurrentUser.put("website", new TableInfo.Column("website", "TEXT", false, 0));
        _columnsCurrentUser.put("street", new TableInfo.Column("street", "TEXT", false, 0));
        _columnsCurrentUser.put("city", new TableInfo.Column("city", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCurrentUser = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesCurrentUser = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCurrentUser = new TableInfo("current_user", _columnsCurrentUser, _foreignKeysCurrentUser, _indicesCurrentUser);
        final TableInfo _existingCurrentUser = TableInfo.read(_db, "current_user");
        if (! _infoCurrentUser.equals(_existingCurrentUser)) {
          throw new IllegalStateException("Migration didn't properly handle current_user(com.example.gamezale.Database.Employee).\n"
                  + " Expected:\n" + _infoCurrentUser + "\n"
                  + " Found:\n" + _existingCurrentUser);
        }
        final HashMap<String, TableInfo.Column> _columnsContacts = new HashMap<String, TableInfo.Column>(11);
        _columnsContacts.put("mobile_phone", new TableInfo.Column("mobile_phone", "TEXT", true, 1));
        _columnsContacts.put("full_name", new TableInfo.Column("full_name", "TEXT", true, 0));
        _columnsContacts.put("work_phone", new TableInfo.Column("work_phone", "TEXT", false, 0));
        _columnsContacts.put("home_phone", new TableInfo.Column("home_phone", "TEXT", false, 0));
        _columnsContacts.put("email", new TableInfo.Column("email", "TEXT", false, 0));
        _columnsContacts.put("group", new TableInfo.Column("group", "TEXT", false, 0));
        _columnsContacts.put("company", new TableInfo.Column("company", "TEXT", false, 0));
        _columnsContacts.put("position", new TableInfo.Column("position", "TEXT", false, 0));
        _columnsContacts.put("website", new TableInfo.Column("website", "TEXT", false, 0));
        _columnsContacts.put("street", new TableInfo.Column("street", "TEXT", false, 0));
        _columnsContacts.put("city", new TableInfo.Column("city", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysContacts = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesContacts = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoContacts = new TableInfo("contacts", _columnsContacts, _foreignKeysContacts, _indicesContacts);
        final TableInfo _existingContacts = TableInfo.read(_db, "contacts");
        if (! _infoContacts.equals(_existingContacts)) {
          throw new IllegalStateException("Migration didn't properly handle contacts(com.example.gamezale.Database.Contact).\n"
                  + " Expected:\n" + _infoContacts + "\n"
                  + " Found:\n" + _existingContacts);
        }
      }
    }, "29bf8ce52f3028cff7afccdb2940730e", "891e04e68130993e726c26eb5ada2c7f");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    return new InvalidationTracker(this, "current_user","contacts");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `current_user`");
      _db.execSQL("DELETE FROM `contacts`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  public EmployeeDao employeeDao() {
    if (_employeeDao != null) {
      return _employeeDao;
    } else {
      synchronized(this) {
        if(_employeeDao == null) {
          _employeeDao = new EmployeeDao_Impl(this);
        }
        return _employeeDao;
      }
    }
  }

  @Override
  public ContactDao contactDao() {
    if (_contactDao != null) {
      return _contactDao;
    } else {
      synchronized(this) {
        if(_contactDao == null) {
          _contactDao = new ContactDao_Impl(this);
        }
        return _contactDao;
      }
    }
  }
}
