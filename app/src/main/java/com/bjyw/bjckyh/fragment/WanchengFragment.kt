package com.bjyw.bjckyh.fragment

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bjyw.bjckyh.R
import com.bjyw.bjckyh.adapter.Order2Adapter
import com.bjyw.bjckyh.adapter.OrderAdapter
import com.bjyw.bjckyh.bean.daobean.Inspect
import com.bjyw.bjckyh.ui.InspectMainActivity
import com.bjyw.bjckyh.ui.InspectSelectActivity
import com.bjyw.bjckyh.utils.DbController
import com.yanzhenjie.recyclerview.OnItemMenuClickListener
import com.yanzhenjie.recyclerview.SwipeMenuBridge
import com.yanzhenjie.recyclerview.SwipeMenuCreator
import com.yanzhenjie.recyclerview.SwipeMenuItem
import kotlinx.android.synthetic.main.fragment_chuli.*
import org.jetbrains.anko.toast

class WanchengFragment: BaseFragment(), OrderAdapter.onClickListener {
    override fun contentViewId(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onFirstVisibleToUser() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onClick(orderId: String) {
        var intent= Intent(activity, InspectSelectActivity::class.java)
        intent.putExtra("orderId",orderId)
        activity?.setResult(Activity.RESULT_OK,intent)
        activity?.finish()
    }

    var list=ArrayList<Inspect>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_chuli, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSide()
    }

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
                val menuPosition = swipeMenuBridge.position // 菜单在RecyclerView的Item中的Position。
                if (list[adapterPosition].status.equals("5")){
                    commitOrder()
                }else{
                    activity!!.toast("当前巡检未完成，请先去完成巡检")
                }
            }
        // 菜单点击监听。
        ry_orderlist.setOnItemMenuClickListener(mMenuItemClickListener)
        // 必须 最后执行
        ry_orderlist.adapter = adapter
    }

    private fun commitOrder() {

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
                startActivity(intent)
            }

        })
    }

    private fun getData() {
        showDialog()
        DbController.getInstance(context).searchAllInspect().forEach {
            list.add(it)
        }
        adapter.notifyDataSetChanged()
        dismissDialog()
    }

    fun refreash() {
        getData()
    }
}