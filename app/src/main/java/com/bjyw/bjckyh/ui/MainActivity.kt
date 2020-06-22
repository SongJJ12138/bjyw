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
import org.jetbrains.anko.toast


@Suppress("UNREACHABLE_CODE")
class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkLogin()
        initView()
        isCard()
        getOrder()
        getNotify()
    }

    private fun getNotify() {
        HttpManager.getNotify().request(this@MainActivity){ _,data ->
            data.let {
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
            }
        }
    }

    private fun getOrder() {
        HttpManager.getOrder().request(this@MainActivity){ _,data ->
            data.let {
                if (it!=null&&it.size>0){
                    navList[1] = Nav(1,"次日工单", com.bjyw.bjckyh.R.mipmap.main_ciri,true,"待完成")
                    main_transforer.notifyDataChanged(navList)
                }
            }
        }
    }

    private val navList:ArrayList<Nav> by lazy {
        var navList:ArrayList<Nav> = ArrayList()
        navList.add(Nav(0,"巡检工单", com.bjyw.bjckyh.R.mipmap.main_xunjain,false,""))
        navList.add(Nav(1,"次日工单", com.bjyw.bjckyh.R.mipmap.main_ciri,false,""))
        navList.add(Nav(2,"维修工单", com.bjyw.bjckyh.R.mipmap.maia_weixiu,false,""))
        navList.add(Nav(3,"拆建工单", com.bjyw.bjckyh.R.mipmap.main_chaijian,false,""))
        navList.add(Nav(4,"台站采集", com.bjyw.bjckyh.R.mipmap.main_caiji,false,""))
        navList.add(Nav(5,"我的小组", com.bjyw.bjckyh.R.mipmap.main_wode,false,""))
        navList.add(Nav(6,"工作统计", com.bjyw.bjckyh.R.mipmap.main_gongzuo,false,""))
        navList.add(Nav(7,"本地备忘录", com.bjyw.bjckyh.R.mipmap.main_bendi,false,""))
        navList
    }
    private fun isCard() {
        HttpManager.checkCard().request(this@MainActivity){ _,data->
            data?.let {
                if (it!=null&&it.size>0){
                    activity_include_tvrignt.text="已打卡"
                    activity_include_tvrignt.textColor=Color.WHITE
                }
            }
        }
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
                        startActivity(intent)
                    }
                    1 ->{
                        intent= Intent(this@MainActivity,CreateOrderActivity::class.java)
                        startActivity(intent)
                    }
                }
                toast("点击")
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
        NoticeAdapter(notifys)
    }
}
