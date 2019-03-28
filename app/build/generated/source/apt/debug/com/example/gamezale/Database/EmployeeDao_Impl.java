package com.example.gamezale.Database;

import android.arch.lifecycle.ComputableLiveData;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.db.SupportSQLiteStatement;
import android.arch.persistence.room.EntityDeletionOrUpdateAdapter;
import android.arch.persistence.room.EntityInsertionAdapter;
import android.arch.persistence.room.InvalidationTracker.Observer;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomSQLiteQuery;
import android.arch.persistence.room.SharedSQLiteStatement;
import android.database.Cursor;
import android.support.annotation.NonNull;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Set;

@SuppressWarnings("unchecked")
public class EmployeeDao_Impl implements EmployeeDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfEmployee;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfEmployee;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfEmployee;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public EmployeeDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfEmployee = new EntityInsertionAdapter<Employee>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `current_user`(`mobile_phone`,`work_phone`,`home_phone`,`fullname`,`email`,`company`,`position`,`website`,`street`,`city`) VALUES (?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Employee value) {
        if (value.getMobile_phone() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getMobile_phone());
        }
        if (value.getWork_phone() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getWork_phone());
        }
        if (value.getHome_phone() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getHome_phone());
        }
        if (value.getFullname() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getFullname());
        }
        if (value.getEmail() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getEmail());
        }
        if (value.getCompany() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getCompany());
        }
        if (value.getPosition() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getPosition());
        }
        if (value.getWebsite() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getWebsite());
        }
        final Address _tmpAddress = value.getAddress();
        if(_tmpAddress != null) {
          if (_tmpAddress.getStreet() == null) {
            stmt.bindNull(9);
          } else {
            stmt.bindString(9, _tmpAddress.getStreet());
          }
          if (_tmpAddress.getCity() == null) {
            stmt.bindNull(10);
          } else {
            stmt.bindString(10, _tmpAddress.getCity());
          }
        } else {
          stmt.bindNull(9);
          stmt.bindNull(10);
        }
      }
    };
    this.__deletionAdapterOfEmployee = new EntityDeletionOrUpdateAdapter<Employee>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `current_user` WHERE `mobile_phone` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Employee value) {
        if (value.getMobile_phone() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getMobile_phone());
        }
      }
    };
    this.__updateAdapterOfEmployee = new EntityDeletionOrUpdateAdapter<Employee>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR REPLACE `current_user` SET `mobile_phone` = ?,`work_phone` = ?,`home_phone` = ?,`fullname` = ?,`email` = ?,`company` = ?,`position` = ?,`website` = ?,`street` = ?,`city` = ? WHERE `mobile_phone` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Employee value) {
        if (value.getMobile_phone() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getMobile_phone());
        }
        if (value.getWork_phone() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getWork_phone());
        }
        if (value.getHome_phone() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getHome_phone());
        }
        if (value.getFullname() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getFullname());
        }
        if (value.getEmail() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getEmail());
        }
        if (value.getCompany() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getCompany());
        }
        if (value.getPosition() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getPosition());
        }
        if (value.getWebsite() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getWebsite());
        }
        final Address _tmpAddress = value.getAddress();
        if(_tmpAddress != null) {
          if (_tmpAddress.getStreet() == null) {
            stmt.bindNull(9);
          } else {
            stmt.bindString(9, _tmpAddress.getStreet());
          }
          if (_tmpAddress.getCity() == null) {
            stmt.bindNull(10);
          } else {
            stmt.bindString(10, _tmpAddress.getCity());
          }
        } else {
          stmt.bindNull(9);
          stmt.bindNull(10);
        }
        if (value.getMobile_phone() == null) {
          stmt.bindNull(11);
        } else {
          stmt.bindString(11, value.getMobile_phone());
        }
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM current_user";
        return _query;
      }
    };
  }

  @Override
  public void insertEmployee(Employee employee) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfEmployee.insert(employee);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteEmployee(Employee employee) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfEmployee.handle(employee);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void updateEmployee(Employee employee) {
    __db.beginTransaction();
    try {
      __updateAdapterOfEmployee.handle(employee);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteAll() {
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAll.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteAll.release(_stmt);
    }
  }

  @Override
  public LiveData<Employee> getCurrentEmployeeLive() {
    final String _sql = "SELECT * FROM current_user LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return new ComputableLiveData<Employee>() {
      private Observer _observer;

      @Override
      protected Employee compute() {
        if (_observer == null) {
          _observer = new Observer("current_user") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfMobilePhone = _cursor.getColumnIndexOrThrow("mobile_phone");
          final int _cursorIndexOfWorkPhone = _cursor.getColumnIndexOrThrow("work_phone");
          final int _cursorIndexOfHomePhone = _cursor.getColumnIndexOrThrow("home_phone");
          final int _cursorIndexOfFullname = _cursor.getColumnIndexOrThrow("fullname");
          final int _cursorIndexOfEmail = _cursor.getColumnIndexOrThrow("email");
          final int _cursorIndexOfCompany = _cursor.getColumnIndexOrThrow("company");
          final int _cursorIndexOfPosition = _cursor.getColumnIndexOrThrow("position");
          final int _cursorIndexOfWebsite = _cursor.getColumnIndexOrThrow("website");
          final int _cursorIndexOfStreet = _cursor.getColumnIndexOrThrow("street");
          final int _cursorIndexOfCity = _cursor.getColumnIndexOrThrow("city");
          final Employee _result;
          if(_cursor.moveToFirst()) {
            final String _tmpMobile_phone;
            _tmpMobile_phone = _cursor.getString(_cursorIndexOfMobilePhone);
            final String _tmpFullname;
            _tmpFullname = _cursor.getString(_cursorIndexOfFullname);
            final Address _tmpAddress;
            if (! (_cursor.isNull(_cursorIndexOfStreet) && _cursor.isNull(_cursorIndexOfCity))) {
              final String _tmpStreet;
              _tmpStreet = _cursor.getString(_cursorIndexOfStreet);
              final String _tmpCity;
              _tmpCity = _cursor.getString(_cursorIndexOfCity);
              _tmpAddress = new Address(_tmpStreet,_tmpCity);
            }  else  {
              _tmpAddress = null;
            }
            _result = new Employee(_tmpMobile_phone,_tmpFullname);
            final String _tmpWork_phone;
            _tmpWork_phone = _cursor.getString(_cursorIndexOfWorkPhone);
            _result.setWork_phone(_tmpWork_phone);
            final String _tmpHome_phone;
            _tmpHome_phone = _cursor.getString(_cursorIndexOfHomePhone);
            _result.setHome_phone(_tmpHome_phone);
            final String _tmpEmail;
            _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
            _result.setEmail(_tmpEmail);
            final String _tmpCompany;
            _tmpCompany = _cursor.getString(_cursorIndexOfCompany);
            _result.setCompany(_tmpCompany);
            final String _tmpPosition;
            _tmpPosition = _cursor.getString(_cursorIndexOfPosition);
            _result.setPosition(_tmpPosition);
            final String _tmpWebsite;
            _tmpWebsite = _cursor.getString(_cursorIndexOfWebsite);
            _result.setWebsite(_tmpWebsite);
            _result.setAddress(_tmpAddress);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    }.getLiveData();
  }

  @Override
  public Employee getCurrentEmployee() {
    final String _sql = "SELECT * FROM current_user LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfMobilePhone = _cursor.getColumnIndexOrThrow("mobile_phone");
      final int _cursorIndexOfWorkPhone = _cursor.getColumnIndexOrThrow("work_phone");
      final int _cursorIndexOfHomePhone = _cursor.getColumnIndexOrThrow("home_phone");
      final int _cursorIndexOfFullname = _cursor.getColumnIndexOrThrow("fullname");
      final int _cursorIndexOfEmail = _cursor.getColumnIndexOrThrow("email");
      final int _cursorIndexOfCompany = _cursor.getColumnIndexOrThrow("company");
      final int _cursorIndexOfPosition = _cursor.getColumnIndexOrThrow("position");
      final int _cursorIndexOfWebsite = _cursor.getColumnIndexOrThrow("website");
      final int _cursorIndexOfStreet = _cursor.getColumnIndexOrThrow("street");
      final int _cursorIndexOfCity = _cursor.getColumnIndexOrThrow("city");
      final Employee _result;
      if(_cursor.moveToFirst()) {
        final String _tmpMobile_phone;
        _tmpMobile_phone = _cursor.getString(_cursorIndexOfMobilePhone);
        final String _tmpFullname;
        _tmpFullname = _cursor.getString(_cursorIndexOfFullname);
        final Address _tmpAddress;
        if (! (_cursor.isNull(_cursorIndexOfStreet) && _cursor.isNull(_cursorIndexOfCity))) {
          final String _tmpStreet;
          _tmpStreet = _cursor.getString(_cursorIndexOfStreet);
          final String _tmpCity;
          _tmpCity = _cursor.getString(_cursorIndexOfCity);
          _tmpAddress = new Address(_tmpStreet,_tmpCity);
        }  else  {
          _tmpAddress = null;
        }
        _result = new Employee(_tmpMobile_phone,_tmpFullname);
        final String _tmpWork_phone;
        _tmpWork_phone = _cursor.getString(_cursorIndexOfWorkPhone);
        _result.setWork_phone(_tmpWork_phone);
        final String _tmpHome_phone;
        _tmpHome_phone = _cursor.getString(_cursorIndexOfHomePhone);
        _result.setHome_phone(_tmpHome_phone);
        final String _tmpEmail;
        _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
        _result.setEmail(_tmpEmail);
        final String _tmpCompany;
        _tmpCompany = _cursor.getString(_cursorIndexOfCompany);
        _result.setCompany(_tmpCompany);
        final String _tmpPosition;
        _tmpPosition = _cursor.getString(_cursorIndexOfPosition);
        _result.setPosition(_tmpPosition);
        final String _tmpWebsite;
        _tmpWebsite = _cursor.getString(_cursorIndexOfWebsite);
        _result.setWebsite(_tmpWebsite);
        _result.setAddress(_tmpAddress);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
