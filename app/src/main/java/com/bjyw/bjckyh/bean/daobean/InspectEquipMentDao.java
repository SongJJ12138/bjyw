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
 * DAO for table "INSPECT_EQUIP_MENT".
*/
public class InspectEquipMentDao extends AbstractDao<InspectEquipMent, Long> {

    public static final String TABLENAME = "INSPECT_EQUIP_MENT";

    /**
     * Properties of entity InspectEquipMent.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property EquipName = new Property(1, String.class, "equipName", false, "EQUIP_NAME");
        public final static Property OrderIndex = new Property(2, String.class, "orderIndex", false, "ORDER_INDEX");
        public final static Property EquipmentIndex = new Property(3, String.class, "equipmentIndex", false, "EQUIPMENT_INDEX");
        public final static Property Remark = new Property(4, String.class, "remark", false, "REMARK");
        public final static Property Picture = new Property(5, String.class, "picture", false, "PICTURE");
        public final static Property Context = new Property(6, String.class, "context", false, "CONTEXT");
        public final static Property Is_unusual = new Property(7, String.class, "is_unusual", false, "IS_UNUSUAL");
        public final static Property Is_exist = new Property(8, String.class, "is_exist", false, "IS_EXIST");
        public final static Property Comments = new Property(9, String.class, "comments", false, "COMMENTS");
    }


    public InspectEquipMentDao(DaoConfig config) {
        super(config);
    }
    
    public InspectEquipMentDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"INSPECT_EQUIP_MENT\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"EQUIP_NAME\" TEXT," + // 1: equipName
                "\"ORDER_INDEX\" TEXT," + // 2: orderIndex
                "\"EQUIPMENT_INDEX\" TEXT," + // 3: equipmentIndex
                "\"REMARK\" TEXT," + // 4: remark
                "\"PICTURE\" TEXT," + // 5: picture
                "\"CONTEXT\" TEXT," + // 6: context
                "\"IS_UNUSUAL\" TEXT," + // 7: is_unusual
                "\"IS_EXIST\" TEXT," + // 8: is_exist
                "\"COMMENTS\" TEXT);"); // 9: comments
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"INSPECT_EQUIP_MENT\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, InspectEquipMent entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String equipName = entity.getEquipName();
        if (equipName != null) {
            stmt.bindString(2, equipName);
        }
 
        String orderIndex = entity.getOrderIndex();
        if (orderIndex != null) {
            stmt.bindString(3, orderIndex);
        }
 
        String equipmentIndex = entity.getEquipmentIndex();
        if (equipmentIndex != null) {
            stmt.bindString(4, equipmentIndex);
        }
 
        String remark = entity.getRemark();
        if (remark != null) {
            stmt.bindString(5, remark);
        }
 
        String picture = entity.getPicture();
        if (picture != null) {
            stmt.bindString(6, picture);
        }
 
        String context = entity.getContext();
        if (context != null) {
            stmt.bindString(7, context);
        }
 
        String is_unusual = entity.getIs_unusual();
        if (is_unusual != null) {
            stmt.bindString(8, is_unusual);
        }
 
        String is_exist = entity.getIs_exist();
        if (is_exist != null) {
            stmt.bindString(9, is_exist);
        }
 
        String comments = entity.getComments();
        if (comments != null) {
            stmt.bindString(10, comments);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, InspectEquipMent entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String equipName = entity.getEquipName();
        if (equipName != null) {
            stmt.bindString(2, equipName);
        }
 
        String orderIndex = entity.getOrderIndex();
        if (orderIndex != null) {
            stmt.bindString(3, orderIndex);
        }
 
        String equipmentIndex = entity.getEquipmentIndex();
        if (equipmentIndex != null) {
            stmt.bindString(4, equipmentIndex);
        }
 
        String remark = entity.getRemark();
        if (remark != null) {
            stmt.bindString(5, remark);
        }
 
        String picture = entity.getPicture();
        if (picture != null) {
            stmt.bindString(6, picture);
        }
 
        String context = entity.getContext();
        if (context != null) {
            stmt.bindString(7, context);
        }
 
        String is_unusual = entity.getIs_unusual();
        if (is_unusual != null) {
            stmt.bindString(8, is_unusual);
        }
 
        String is_exist = entity.getIs_exist();
        if (is_exist != null) {
            stmt.bindString(9, is_exist);
        }
 
        String comments = entity.getComments();
        if (comments != null) {
            stmt.bindString(10, comments);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public InspectEquipMent readEntity(Cursor cursor, int offset) {
        InspectEquipMent entity = new InspectEquipMent( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // equipName
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // orderIndex
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // equipmentIndex
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // remark
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // picture
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // context
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // is_unusual
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // is_exist
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9) // comments
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, InspectEquipMent entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setEquipName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setOrderIndex(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setEquipmentIndex(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setRemark(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setPicture(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setContext(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setIs_unusual(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setIs_exist(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setComments(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(InspectEquipMent entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(InspectEquipMent entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(InspectEquipMent entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
