package com.bjyw.bjckyh.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.bjyw.bjckyh.bean.daobean.DaoMaster;
import com.bjyw.bjckyh.bean.daobean.DaoSession;
import com.bjyw.bjckyh.bean.daobean.Inspect;
import com.bjyw.bjckyh.bean.daobean.InspectDao;

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
     * @param Inspect
     */
    public void insertOrReplace(Inspect Inspect){
        inspectDao.insertOrReplace(Inspect);
    }
    /**插入一条记录，表里面要没有与之相同的记录
     *
     * @param Inspect
     */
    public long insert(Inspect Inspect){
        return  inspectDao.insert(Inspect);
    }

    /**
     * 更新数据
     * @param Inspect
     */
    public void update(Inspect Inspect){
        Inspect mOldInspect = inspectDao.queryBuilder().where(InspectDao.Properties.OrderIndex.eq(Inspect.getId())).build().unique();//拿到之前的记录
        if(mOldInspect !=null){
            inspectDao.update(mOldInspect);
        }
    }
    /**
     * 按条件查询数据
     */
    public List<Inspect> searchByWhere(String wherecluse){
        List<Inspect>Inspects = (List<Inspect>) inspectDao.queryBuilder().where(InspectDao.Properties.OrderIndex.eq(wherecluse)).build().unique();
        return Inspects;
    }
    /**
     * 查询所有数据
     */
    public List<Inspect> searchAll(){
        List<Inspect>Inspects=inspectDao.queryBuilder().list();
        return Inspects;
    }
    /**
     * 删除数据
     */
    public void delete(String wherecluse){
        inspectDao.queryBuilder().where(InspectDao.Properties.OrderIndex.eq(wherecluse)).buildDelete().executeDeleteWithoutDetachingEntities();
    }
}