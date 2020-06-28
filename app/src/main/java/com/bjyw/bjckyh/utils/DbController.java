package com.bjyw.bjckyh.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.bjyw.bjckyh.bean.daobean.DaoMaster;
import com.bjyw.bjckyh.bean.daobean.DaoSession;
import com.bjyw.bjckyh.bean.daobean.Inspect;
import com.bjyw.bjckyh.bean.daobean.InspectConsumable;
import com.bjyw.bjckyh.bean.daobean.InspectConsumableDao;
import com.bjyw.bjckyh.bean.daobean.InspectDao;
import com.bjyw.bjckyh.bean.daobean.InspectEnvironMent;
import com.bjyw.bjckyh.bean.daobean.InspectEnvironMentDao;
import com.bjyw.bjckyh.bean.daobean.InspectEquipMent;
import com.bjyw.bjckyh.bean.daobean.InspectEquipMentDao;

import org.greenrobot.greendao.AbstractDao;

import java.util.List;

public class DbController {
    /**
     * Helper
     */
    private DaoMaster.DevOpenHelper mHelper;//获取Helper对象
    /**
     * 数据库
     */
    private SQLiteDatabase db;
    /**
     * DaoMaster
     */
    private DaoMaster mDaoMaster;
    /**
     * DaoSession
     */
    private DaoSession mDaoSession;
    /**
     * 上下文
     */
    private Context context;
    /**
     * dao
     */
    private InspectDao inspectDao;
    private InspectEnvironMentDao inspectEnvironMentDao;
    private InspectEquipMentDao inspectEquipMentDao;
    private InspectConsumableDao inspectConsumableDao;

    private static DbController mDbController;

    /**
     * 获取单例
     */
    public static DbController getInstance(Context context){
        if(mDbController == null){
            synchronized (DbController.class){
                if(mDbController == null){
                    mDbController = new DbController(context);
                }
            }
        }
        return mDbController;
    }
    /**
     * 初始化
     * @param context
     */
    public DbController(Context context) {
        this.context = context;
        mHelper = new DaoMaster.DevOpenHelper(context,"person.db", null);
        mDaoMaster =new DaoMaster(getWritableDatabase());
        mDaoSession = mDaoMaster.newSession();
        inspectDao = mDaoSession.getInspectDao();
        inspectEnvironMentDao=mDaoSession.getInspectEnvironMentDao();
        inspectEquipMentDao=mDaoSession.getInspectEquipMentDao();
        inspectConsumableDao=mDaoSession.getInspectConsumableDao();
    }
    /**
     * 获取可读数据库
     */
    private SQLiteDatabase getReadableDatabase(){
        if(mHelper == null){
            mHelper = new DaoMaster.DevOpenHelper(context,"person.db",null);
        }
        SQLiteDatabase db =mHelper.getReadableDatabase();
        return db;
    }

    /**
     * 获取可写数据库
     * @return
     */
    private SQLiteDatabase getWritableDatabase(){
        if(mHelper == null){
            mHelper =new DaoMaster.DevOpenHelper(context,"person.db",null);
        }
        SQLiteDatabase db = mHelper.getWritableDatabase();
        return db;
    }

    /**
     * 会自动判定是插入还是替换
     * @param inspect
     */
    public void insertOrReplaceInspect(Inspect inspect){
        deleteInspect(inspect.getOrderIndex());
        inspectDao.insertOrReplace(inspect);
    }

    /**
     * 按条件查询数据
     */
    public Inspect searchById(String wherecluse){
        Inspect Inspects = inspectDao.queryBuilder().where(InspectDao.Properties.OrderIndex.eq(wherecluse)).build().unique();
        return Inspects;
    }

    /**
     * 查询所有数据
     */
    public List<Inspect> searchAllInspect(){
        List<Inspect>Inspects=inspectDao.queryBuilder().list();
        return Inspects;
    }
    /**
     * 删除数据
     */
    public void deleteInspect(String wherecluse){
        inspectDao.queryBuilder().where(InspectDao.Properties.OrderIndex.eq(wherecluse)).buildDelete().executeDeleteWithoutDetachingEntities();
    }

    public void insertOrReplaceEnvironment(InspectEnvironMent environMent){
        deleteEnvironment(environMent.getEnvironmentIndex());
        inspectEnvironMentDao.insertOrReplace(environMent);
    }
    public List<InspectEnvironMent> searchByWhereEnvironment(String wherecluse){
        List<InspectEnvironMent>environMents = (List<InspectEnvironMent>) inspectEnvironMentDao.queryBuilder().where(InspectEnvironMentDao.Properties.OnrderIndex.eq(wherecluse)).list();
        return environMents;
    }

    public void deleteEnvironment(String wherecluse){
        inspectEnvironMentDao.queryBuilder().where(InspectEnvironMentDao.Properties.EnvironmentIndex.eq(wherecluse)).buildDelete().executeDeleteWithoutDetachingEntities();
    }


    public void insertOrReplaceEquipment(InspectEquipMent equipMent){
        deleteEquipment(""+equipMent.getEquipmentIndex());
        inspectEquipMentDao.insertOrReplace(equipMent);
    }
    public void deleteEquipment(String wherecluse){
        inspectEquipMentDao.queryBuilder().where(InspectEquipMentDao.Properties.EquipmentIndex.eq(wherecluse)).buildDelete().executeDeleteWithoutDetachingEntities();
    }
    public List<InspectEquipMent> searchByWhereEquipment(String wherecluse){
        List<InspectEquipMent>equipMents = (List<InspectEquipMent>) inspectEquipMentDao.queryBuilder().where(InspectEquipMentDao.Properties.OrderIndex.eq(wherecluse),InspectEquipMentDao.Properties.OrderIndex.eq(wherecluse)).list();
        return equipMents;
    }


    public void insertOrReplaceConsum(InspectConsumable inspectConsumable){
        deleteConsum(inspectConsumable.getConsumableId(),inspectConsumable.getEquipId());
        inspectConsumableDao.insertOrReplace(inspectConsumable);
    }
    public void deleteConsum(String wherecluse,String wercouse2){
        inspectConsumableDao.queryBuilder().where(InspectConsumableDao.Properties.ConsumableId.eq(wherecluse),InspectConsumableDao.Properties.EquipId.eq(wercouse2)).buildDelete().executeDeleteWithoutDetachingEntities();
    }
    public List<InspectConsumable> searchByWhereConsum(String wherecluse,String equipId){
        List<InspectConsumable>consumables = (List<InspectConsumable>) inspectConsumableDao.queryBuilder().where(InspectConsumableDao.Properties.OrderIndex.eq(wherecluse),InspectConsumableDao.Properties.EquipId.eq(equipId)).list();
        return consumables;
    }
}