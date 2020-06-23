package com.bjyw.bjckyh.bean.daobean;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "INSPECT".
*/
public class InspectDao extends AbstractDao<Inspect, Long> {

    public static final String TABLENAME = "INSPECT";

    /**
     * Properties of entity Inspect.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property OrderIndex = new Property(1, String.class, "orderIndex", false, "ORDER_INDEX");
        public final static Property UserId = new Property(2, String.class, "userId", false, "USER_ID");
        public final static Property Status = new Property(3, String.class, "status", false, "STATUS");
        public final static Property Is_unusual = new Property(4, int.class, "is_unusual", false, "IS_UNUSUAL");
        public final static Property UseStatus = new Property(5, String.class, "useStatus", false, "USE_STATUS");
        public final static Property EnvironmentStatus = new Property(6, String.class, "environmentStatus", false, "ENVIRONMENT_STATUS");
        public final static Property ConId = new Property(7, String.class, "conId", false, "CON_ID");
    }


    public InspectDao(DaoConfig config) {
        super(config);
    }
    
    public InspectDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"INSPECT\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"ORDER_INDEX\" TEXT," + // 1: orderIndex
                "\"USER_ID\" TEXT," + // 2: userId
                "\"STATUS\" TEXT," + // 3: status
                "\"IS_UNUSUAL\" INTEGER NOT NULL ," + // 4: is_unusual
                "\"USE_STATUS\" TEXT," + // 5: useStatus
                "\"ENVIRONMENT_STATUS\" TEXT," + // 6: environmentStatus
                "\"CON_ID\" TEXT);"); // 7: conId
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"INSPECT\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Inspect entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String orderIndex = entity.getOrderIndex();
        if (orderIndex != null) {
            stmt.bindString(2, orderIndex);
        }
 
        String userId = entity.getUserId();
        if (userId != null) {
            stmt.bindString(3, userId);
        }
 
        String status = entity.getStatus();
        if (status != null) {
            stmt.bindString(4, status);
        }
        stmt.bindLong(5, entity.getIs_unusual());
 
        String useStatus = entity.getUseStatus();
        if (useStatus != null) {
            stmt.bindString(6, useStatus);
        }
 
        String environmentStatus = entity.getEnvironmentStatus();
        if (environmentStatus != null) {
            stmt.bindString(7, environmentStatus);
        }
 
        String conId = entity.getConId();
        if (conId != null) {
            stmt.bindString(8, conId);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Inspect entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String orderIndex = entity.getOrderIndex();
        if (orderIndex != null) {
            stmt.bindString(2, orderIndex);
        }
 
        String userId = entity.getUserId();
        if (userId != null) {
            stmt.bindString(3, userId);
        }
 
        String status = entity.getStatus();
        if (status != null) {
            stmt.bindString(4, status);
        }
        stmt.bindLong(5, entity.getIs_unusual());
 
        String useStatus = entity.getUseStatus();
        if (useStatus != null) {
            stmt.bindString(6, useStatus);
        }
 
        String environmentStatus = entity.getEnvironmentStatus();
        if (environmentStatus != null) {
            stmt.bindString(7, environmentStatus);
        }
 
        String conId = entity.getConId();
        if (conId != null) {
            stmt.bindString(8, conId);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public Inspect readEntity(Cursor cursor, int offset) {
        Inspect entity = new Inspect( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // orderIndex
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // userId
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // status
            cursor.getInt(offset + 4), // is_unusual
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // useStatus
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // environmentStatus
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7) // conId
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Inspect entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setOrderIndex(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setUserId(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setStatus(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setIs_unusual(cursor.getInt(offset + 4));
        entity.setUseStatus(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setEnvironmentStatus(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setConId(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(Inspect entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(Inspect entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Inspect entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}