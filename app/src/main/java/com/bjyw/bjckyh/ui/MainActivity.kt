package com.bjyw.bjckyh.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.bjyw.bjckyh.R
import com.bjyw.bjckyh.adapter.NoticeAdapter
import com.bjyw.bjckyh.utils.SPUtils
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar_title.*
import com.zaaach.transformerslayout.holder.TransformersHolderCreator
import com.zaaach.transformerslayout.listener.OnTransformersItemClickListener
import com.zaaach.transformerslayout.TransformersOptions
import com.bjyw.bjckyh.adapter.TransFormersViewHolder
import com.bjyw.bjckyh.bean.Nav
import com.bjyw.bjckyh.bean.Notify
import com.bjyw.bjckyh.network.HttpManager
import com.bjyw.bjckyh.network.request
import com.bjyw.bjckyh.utils.dp2px
import com.zaaach.transformerslayout.holder.Holder
import org.jetbrains.anko.textColor


@Suppress("UNREACHABLE_CODE")
class MainActivity : BaseActivity() {
    var orderSize="0"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkLogin()
        initView()
    }

    override fun onResume() {
        super.onResume()
        getData()
    }


    private fun getData() {
        showDialog()
        var threadCount=3
        HttpManager.getOrder().request(this@MainActivity){ _,data ->
            data.let {
                threadCount--
                if (it!=null&&it.size>0){
                    orderSize=""+it.size
                    navList[0] = Nav(0,"巡检工单", com.bjyw.bjckyh.R.mipmap.main_xunjain,true,"待完成")
                    main_transforer.notifyDataChanged(navList)
                }
            }
            if (threadCount==0){
                dismissDialog()
            }
        }
        HttpManager.getNotify().request(this@MainActivity){ _,data ->
            data.let {
                threadCount--
                if (it!=null&&it.size>0){
                    notifys.clear()
                    activity_mian_tvwordsize.text=""+it.size
                    if (it.size>4){
                        for (i in 0..3){
                            notifys.add(it[i])
                        }
                        adapter.notifyDataSetChanged()
                    }else{
                        notifys.addAll(it)
                        adapter.notifyDataSetChanged()
                    }
                }
                if (threadCount==0){
                    dismissDialog()
                }
            }
        }
        HttpManager.checkCard().request(this@MainActivity){ _,data->
            data?.let {
                threadCount--
                if (it!=null&&it.size>0){
                    activity_include_tvrignt.text="已打卡"
                    activity_include_tvrignt.textColor=Color.WHITE
                }
                if (threadCount==0){
                    dismissDialog()
                }
            }
        }
    }

    private val navList:ArrayList<Nav> by lazy {
        var navList:ArrayList<Nav> = ArrayList()
        navList.add(Nav(0,"巡检工单", R.mipmap.main_xunjain,false,""))
        navList.add(Nav(1,"台站采集", R.mipmap.mian_caiji,false,""))
        navList.add(Nav(2,"次日工单",R.mipmap.main_ciri,false,""))
        navList.add(Nav(3,"我的小组", R.mipmap.mian_wode,false,""))
        navList.add(Nav(4,"维修工单", R.mipmap.main_weixiu,false,""))
        navList.add(Nav(5,"工作统计", R.mipmap.main_gongzuo,false,""))
        navList.add(Nav(6,"拆建工单", R.mipmap.main_chaijian,false,""))
        navList.add(Nav(7,"本地备忘录", R.mipmap.main_bendi,false,""))
        navList
    }

    private fun initView() {
        activity_mian_recyclerword.layoutManager= LinearLayoutManager(applicationContext)
        activity_mian_recyclerword.isNestedScrollingEnabled = false
        activity_mian_recyclerword.adapter=adapter
        activity_include_btback.visibility=View.GONE
        var options = TransformersOptions.Builder()
            .lines(2)
            .spanCount(4)
            .scrollBarWidth(dp2px(this, 40f))
            .scrollBarHeight(dp2px(this, 3f))
            .scrollBarRadius(dp2px(this, 3f) / 2f)
            .scrollBarTopMargin(dp2px(this, 6f))
            .build()
        main_transforer.apply(options)
            .addOnTransformersItemClickListener(OnTransformersItemClickListener {
                var intent:Intent
                when(it){
                    0 ->{
                        intent= Intent(this@MainActivity,InspectSelectActivity::class.java)
                        intent.putExtra("orderId","")
                        intent.putExtra("orderSize",orderSize)
                        startActivity(intent)
                    }
                    1 ->{
                        intent= Intent(this@MainActivity,SiteCollectActivity::class.java)
                        startActivity(intent)
                    }
                    2 ->{
                        intent= Intent(this@MainActivity,CreateOrderActivity::class.java)
                        startActivity(intent)
                    }
                    2 ->{
                        intent= Intent(this@MainActivity,MineActivity::class.java)
                        startActivity(intent)
                    }
                }
            })
            .load(navList, object : TransformersHolderCreator<Nav> {
                override fun createHolder(itemView: View): Holder<Nav> {
                    return TransFormersViewHolder(itemView)
                }
                override fun getLayoutId(): Int {
                    return R.layout.item_nav_list
                }
            })
    }

    /**
     * 登录检查
     */
    private fun checkLogin() {
        var id:Int=SPUtils.instance().getInt("userId")
        if (id!=-1){
            return
        }else{
            var intent=Intent(this@MainActivity,LogonActivity::class.java)
            startActivity(intent)
            this.finish()
        }
    }
    var notifys= ArrayList<Notify>()
    private val adapter by lazy {
       NoticeAdapter(this@MainActivity,notifys)
    }
}
