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
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@SuppressWarnings("unchecked")
public class ContactDao_Impl implements ContactDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfContact;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfContact;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfContact;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public ContactDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfContact = new EntityInsertionAdapter<Contact>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `contacts`(`mobile_phone`,`full_name`,`work_phone`,`home_phone`,`email`,`group`,`company`,`position`,`website`,`street`,`city`) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Contact value) {
        if (value.getMobile_phone() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getMobile_phone());
        }
        if (value.getFull_name() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getFull_name());
        }
        if (value.getWork_phone() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getWork_phone());
        }
        if (value.getHome_phone() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getHome_phone());
        }
        if (value.getEmail() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getEmail());
        }
        if (value.getGroup() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getGroup());
        }
        if (value.getCompany() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getCompany());
        }
        if (value.getPosition() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getPosition());
        }
        if (value.getWebsite() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getWebsite());
        }
        final Address _tmpAddress = value.getAddress();
        if(_tmpAddress != null) {
          if (_tmpAddress.getStreet() == null) {
            stmt.bindNull(10);
          } else {
            stmt.bindString(10, _tmpAddress.getStreet());
          }
          if (_tmpAddress.getCity() == null) {
            stmt.bindNull(11);
          } else {
            stmt.bindString(11, _tmpAddress.getCity());
          }
        } else {
          stmt.bindNull(10);
          stmt.bindNull(11);
        }
      }
    };
    this.__deletionAdapterOfContact = new EntityDeletionOrUpdateAdapter<Contact>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `contacts` WHERE `mobile_phone` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Contact value) {
        if (value.getMobile_phone() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getMobile_phone());
        }
      }
    };
    this.__updateAdapterOfContact = new EntityDeletionOrUpdateAdapter<Contact>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `contacts` SET `mobile_phone` = ?,`full_name` = ?,`work_phone` = ?,`home_phone` = ?,`email` = ?,`group` = ?,`company` = ?,`position` = ?,`website` = ?,`street` = ?,`city` = ? WHERE `mobile_phone` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Contact value) {
        if (value.getMobile_phone() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getMobile_phone());
        }
        if (value.getFull_name() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getFull_name());
        }
        if (value.getWork_phone() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getWork_phone());
        }
        if (value.getHome_phone() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getHome_phone());
        }
        if (value.getEmail() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getEmail());
        }
        if (value.getGroup() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getGroup());
        }
        if (value.getCompany() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getCompany());
        }
        if (value.getPosition() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getPosition());
        }
        if (value.getWebsite() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getWebsite());
        }
        final Address _tmpAddress = value.getAddress();
        if(_tmpAddress != null) {
          if (_tmpAddress.getStreet() == null) {
            stmt.bindNull(10);
          } else {
            stmt.bindString(10, _tmpAddress.getStreet());
          }
          if (_tmpAddress.getCity() == null) {
            stmt.bindNull(11);
          } else {
            stmt.bindString(11, _tmpAddress.getCity());
          }
        } else {
          stmt.bindNull(10);
          stmt.bindNull(11);
        }
        if (value.getMobile_phone() == null) {
          stmt.bindNull(12);
        } else {
          stmt.bindString(12, value.getMobile_phone());
        }
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM contacts";
        return _query;
      }
    };
  }

  @Override
  public void insertContact(Contact contact) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfContact.insert(contact);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteContact(Contact contact) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfContact.handle(contact);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void updateContact(Contact contact) {
    __db.beginTransaction();
    try {
      __updateAdapterOfContact.handle(contact);
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
  public LiveData<List<Contact>> getAllContacts() {
    final String _sql = "SELECT * FROM contacts ORDER BY full_name";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return new ComputableLiveData<List<Contact>>() {
      private Observer _observer;

      @Override
      protected List<Contact> compute() {
        if (_observer == null) {
          _observer = new Observer("contacts") {
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
          final int _cursorIndexOfFullName = _cursor.getColumnIndexOrThrow("full_name");
          final int _cursorIndexOfWorkPhone = _cursor.getColumnIndexOrThrow("work_phone");
          final int _cursorIndexOfHomePhone = _cursor.getColumnIndexOrThrow("home_phone");
          final int _cursorIndexOfEmail = _cursor.getColumnIndexOrThrow("email");
          final int _cursorIndexOfGroup = _cursor.getColumnIndexOrThrow("group");
          final int _cursorIndexOfCompany = _cursor.getColumnIndexOrThrow("company");
          final int _cursorIndexOfPosition = _cursor.getColumnIndexOrThrow("position");
          final int _cursorIndexOfWebsite = _cursor.getColumnIndexOrThrow("website");
          final int _cursorIndexOfStreet = _cursor.getColumnIndexOrThrow("street");
          final int _cursorIndexOfCity = _cursor.getColumnIndexOrThrow("city");
          final List<Contact> _result = new ArrayList<Contact>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Contact _item;
            final String _tmpMobile_phone;
            _tmpMobile_phone = _cursor.getString(_cursorIndexOfMobilePhone);
            final String _tmpFull_name;
            _tmpFull_name = _cursor.getString(_cursorIndexOfFullName);
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
            _item = new Contact(_tmpMobile_phone,_tmpFull_name);
            final String _tmpWork_phone;
            _tmpWork_phone = _cursor.getString(_cursorIndexOfWorkPhone);
            _item.setWork_phone(_tmpWork_phone);
            final String _tmpHome_phone;
            _tmpHome_phone = _cursor.getString(_cursorIndexOfHomePhone);
            _item.setHome_phone(_tmpHome_phone);
            final String _tmpEmail;
            _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
            _item.setEmail(_tmpEmail);
            final String _tmpGroup;
            _tmpGroup = _cursor.getString(_cursorIndexOfGroup);
            _item.setGroup(_tmpGroup);
            final String _tmpCompany;
            _tmpCompany = _cursor.getString(_cursorIndexOfCompany);
            _item.setCompany(_tmpCompany);
            final String _tmpPosition;
            _tmpPosition = _cursor.getString(_cursorIndexOfPosition);
            _item.setPosition(_tmpPosition);
            final String _tmpWebsite;
            _tmpWebsite = _cursor.getString(_cursorIndexOfWebsite);
            _item.setWebsite(_tmpWebsite);
            _item.setAddress(_tmpAddress);
            _result.add(_item);
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
}
