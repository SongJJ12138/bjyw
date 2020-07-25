package com.bjyw.bjckyh.fragment

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bjyw.bjckyh.R
import com.bjyw.bjckyh.adapter.Order2Adapter
import com.bjyw.bjckyh.adapter.Order3Adapter
import com.bjyw.bjckyh.bean.Order
import com.bjyw.bjckyh.bean.daobean.Inspect
import com.bjyw.bjckyh.bean.daobean.InspectCommmit
import com.bjyw.bjckyh.dialog.CommitSuccessDialog
import com.bjyw.bjckyh.network.HttpManager
import com.bjyw.bjckyh.network.requestByF
import com.bjyw.bjckyh.ui.InspectMainActivity
import com.bjyw.bjckyh.ui.MainActivity
import com.bjyw.bjckyh.utils.DbController
import com.bjyw.bjckyh.utils.SPUtils
import com.yanzhenjie.recyclerview.OnItemMenuClickListener
import com.yanzhenjie.recyclerview.SwipeMenuBridge
import com.yanzhenjie.recyclerview.SwipeMenuCreator
import com.yanzhenjie.recyclerview.SwipeMenuItem
import kotlinx.android.synthetic.main.fragment_chuli.*
import org.jetbrains.anko.toast

class WanchengFragment: BaseFragment(){
    override fun contentViewId(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onFirstVisibleToUser() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    var list=ArrayList<Inspect>()
    var list2=ArrayList<Order>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_chuli, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv2.layoutManager=LinearLayoutManager(context)
        rv2.adapter=adapter2
        initSide()
        getData()
    }

    var position=0
    private fun initSide() {
        ry_orderlist.isNestedScrollingEnabled = false
        ry_orderlist.isItemViewSwipeEnabled = true
        ry_orderlist.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        // 创建菜单：
        val mSwipeMenuCreator =
            SwipeMenuCreator { leftMenu, rightMenu, viewType ->
                val deleteItem = SwipeMenuItem(context)
                deleteItem.setBackgroundColor(Color.parseColor("#FF0000"))
                    .setText("上传")
                    .setTextColor(Color.WHITE)
                    .setHeight(ViewGroup.LayoutParams.MATCH_PARENT).width = 70
                rightMenu.addMenuItem(deleteItem)
            }
        // 设置监听器。
        ry_orderlist.setSwipeMenuCreator(mSwipeMenuCreator)
        val mMenuItemClickListener =
            OnItemMenuClickListener  (){ swipeMenuBridge: SwipeMenuBridge, i: Int ->
                // 任何操作必须先关闭菜单，否则可能出现Item菜单打开状态错乱。
                swipeMenuBridge.closeMenu()
                val adapterPosition = swipeMenuBridge.position // RecyclerView的Item的position。
                position=adapterPosition
                if (list[adapterPosition].status.equals("5")){
                    var list2=DbController.getInstance(context).searchByWhereCommitData(list[adapterPosition].orderIndex,list[adapterPosition].userId)
                    if (list2.size>0)
                    commitOrder(list2[0])
                }else{
                    activity!!.toast("当前巡检不可提交")
                }
            }
        // 菜单点击监听。
        ry_orderlist.setOnItemMenuClickListener(mMenuItemClickListener)
        // 必须 最后执行
        ry_orderlist.adapter = adapter
    }

    private fun commitOrder(inspectCommmit: InspectCommmit) {
        showDialog()
        HttpManager.commit(inspectCommmit.data).requestByF(this) { _, data ->
            data?.let {
                dismissDialog()
                var dialog = CommitSuccessDialog(activity!!.applicationContext, object :
                    CommitSuccessDialog.onClickListener {
                    override fun onClick() {
                        clearData(inspectCommmit)
                        var intent = Intent(activity, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                        activity!!.finish()
                    }
                })
                dialog.show()
            }
        }
    }
    private fun clearData(ins: InspectCommmit) {
        list[position].status=""+6
        DbController.getInstance(context).insertOrReplaceInspect(list[position])
        DbController.getInstance(context).deleteOrderEquipment(ins.orderIndex)
        DbController.getInstance(context).deleteOrderEnvironment(ins.orderIndex)
        DbController.getInstance(context).deleteOrderConsum(ins.orderIndex)
    }
    private val adapter2 by lazy{
        Order3Adapter(list2)
    }
    private val adapter by lazy{
        Order2Adapter(list,object: Order2Adapter.onClickListener{
            override fun onChoose(
                orderId: String,
                siteId: Int,
                is_usual: String,
                status: String
            ) {
                var intent=Intent(activity,InspectMainActivity::class.java)
                intent.putExtra("siteId",siteId.toInt())
                intent.putExtra("orderId",orderId)
                intent.putExtra("status",status)
                if (is_usual.equals("0")){
                    intent.putExtra("isOk",true)
                }else{
                    intent.putExtra("isOk",false)
                }
                intent.putExtra("isOk",1)
                startActivity(intent)
            }

        })
    }

    private fun getData() {
        showDialog()
        var userId=SPUtils.instance().getInt("userId")
        DbController.getInstance(context).searchAllInspect(""+userId).forEach {
            list.add(it)
        }
        HttpManager.getOrder().requestByF(this){ _, data->
            data?.let {
                dismissDialog()
                var listt=ArrayList<Order>()
                list2.clear()
                it.forEach {order ->
                    if (order.status==5){
                        list2.add(order)
                    }
                }
                adapter.notifyDataSetChanged()
                adapter2.notifyDataSetChanged()
            }
        }
    }

    fun refreash() {
        getData()
    }
}