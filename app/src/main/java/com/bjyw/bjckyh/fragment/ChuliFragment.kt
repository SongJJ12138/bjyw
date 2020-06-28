package com.bjyw.bjckyh.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bjyw.bjckyh.R
import com.bjyw.bjckyh.adapter.OrderAdapter
import com.bjyw.bjckyh.bean.Order
import com.bjyw.bjckyh.network.HttpManager
import com.bjyw.bjckyh.network.requestByF
import kotlinx.android.synthetic.main.fragment_chuli.*
import android.graphics.Color
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bjyw.bjckyh.network.ResultDataSubscriber
import com.bjyw.bjckyh.network.request
import com.bjyw.bjckyh.ui.InspectSelectActivity
import com.yanzhenjie.recyclerview.OnItemMenuClickListener
import com.yanzhenjie.recyclerview.SwipeMenuBridge
import com.yanzhenjie.recyclerview.SwipeMenuCreator
import com.yanzhenjie.recyclerview.SwipeMenuItem
import kotlinx.android.synthetic.main.activity_inspect_select.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.util.*
import kotlin.collections.ArrayList


class ChuliFragment: BaseFragment(), OrderAdapter.onClickListener {
    override fun contentViewId(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onFirstVisibleToUser() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onClick(orderId: String) {
        var intent=Intent(activity,InspectSelectActivity::class.java)
        intent.putExtra("orderId",orderId)
        //测试
//        intent.putExtra("orderIndex","2390108011083969470")
        activity?.setResult(Activity.RESULT_OK,intent)
        activity?.finish()
    }

    var list=ArrayList<Order>()

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
        ry_orderlist.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        // 创建菜单：
        val mSwipeMenuCreator =
            SwipeMenuCreator { leftMenu, rightMenu, viewType ->
                val deleteItem = SwipeMenuItem(activity)
                deleteItem.setBackgroundColor(Color.parseColor("#DDDDDD"))
                    .setImage(R.mipmap.delete)
                    .setTextColor(Color.WHITE)
                    .setHeight(ViewGroup.LayoutParams.MATCH_PARENT)
                    .setWidth(170)
                rightMenu.addMenuItem(deleteItem)
            }
        // 设置监听器。
        ry_orderlist.setSwipeMenuCreator(mSwipeMenuCreator)
        val mMenuItemClickListener =
            OnItemMenuClickListener { swipeMenuBridge: SwipeMenuBridge, i: Int ->
                // 任何操作必须先关闭菜单，否则可能出现Item菜单打开状态错乱。
                swipeMenuBridge.closeMenu()
                val adapterPosition = swipeMenuBridge.position // RecyclerView的Item的position。
                val menuPosition = swipeMenuBridge.position // 菜单在RecyclerView的Item中的Position。
                if (menuPosition == 0) {
                    var id=list[adapterPosition].id
                    showDialog()
                    HttpManager.deleteOrder(id).requestByF(this) { _, data ->
                        data.let {
                            dismissDialog()
                            list.removeAt(adapterPosition)
                            adapter.notifyDataSetChanged() // 刷新
                            Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        // 菜单点击监听。
        ry_orderlist.setOnItemMenuClickListener(mMenuItemClickListener)
        // 必须 最后执行
        ry_orderlist.adapter = adapter
    }

    private val adapter by lazy{
        OrderAdapter(list,this@ChuliFragment)
    }

    private fun getData() {
        showDialog()
        HttpManager.getOrder().requestByF(this){ _, data->
            data?.let {
                dismissDialog()
                list.clear()
                //测试
                list.addAll(it)
                adapter.notifyDataSetChanged()
            }
        }
    }

    fun refreash() {
        getData()
    }
}