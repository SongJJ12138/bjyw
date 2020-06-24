package com.bjyw.bjckyh.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import com.bjyw.bjckyh.R
import com.bjyw.bjckyh.adapter.EquipAdapter
import com.bjyw.bjckyh.bean.Equip
import com.bjyw.bjckyh.network.HttpManager
import com.bjyw.bjckyh.network.request
import com.bjyw.bjckyh.utils.DbController
import kotlinx.android.synthetic.main.activity_inspect_main.*
import kotlinx.android.synthetic.main.toolbar_title.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.textColor
import org.jetbrains.anko.toast
import org.json.JSONArray
import org.json.JSONObject

class InspectMainActivity : BaseActivity(), EquipAdapter.onClickListener {
    val REQUEST_CODE=0x01
    var youxian=""
    var wuxian=""
    var phone=""
    var huanjing=""
    var teshu=""
    var cleanPic=""
    var beizhu=""
    override fun onClick(position:Int,equipId: String) {
        var intent=Intent(this@InspectMainActivity,InspectDetailActivity::class.java)
        intent.putExtra("equipId",equipId)
        intent.putExtra("orderId",orderId)
        intent.putExtra("position",position)
        startActivityForResult(intent,REQUEST_CODE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inspect_main)
        initView()
        initClick()
        getEquipUsual()
    }

    private fun initClick() {
        activity_include_btback.onClick {
            finish()
        }
        tv_commit.onClick {
            checkItem()
        }
        rb_dianxin.onClick {
            youxian="电信"
        }
        rb_yidong.onClick {
            youxian="移动"
        }
        rb_liantong.onClick {
            youxian="联通"
        }
        rb_gehua.onClick {
            youxian="歌华"
        }
        rb_Ndianxin.onClick {
            wuxian="电信"
        }
        rb_Nliantong.onClick {
            wuxian="联通"
        }
        rb_Nyidong.onClick {
            wuxian="移动"
        }
        rb_phone4G.onClick {
            phone="有4G"
        }
        rb_Nphone4G.onClick {
            phone="无4G"
        }
        rb_environment_good.onClick {
            huanjing="好"
        }
        rb_environment_yiban.onClick {
            huanjing="一般"
        }
        rb_environment_bad.onClick {
            huanjing="差"
        }
        rb_special_banqian.onClick {
            teshu="需搬迁"
        }
        rb_special_fengcun.onClick {
            teshu="需封存"
        }
        rb_special_chaichu.onClick {
            teshu="待拆除"
        }
        rb_special_huifu.onClick {
            teshu="待恢复"
        }
    }

    private fun checkItem() {
        if (youxian.equals("")){
            toast("请选择有线网络状态")
            return
        }
        if (wuxian.equals("")){
            toast("请选择无线网络状态")
            return
        }
        if (phone.equals("")){
            toast("请选择手机信号状态")
            return
        }
        if (huanjing.equals("")){
            toast("请选择环境情况")
            return
        }
        if (cleanPic.equals("")){
            toast("请添加清扫照片")
            return
        }
        commit()
    }

    private fun commit() {
        var inspect=DbController.getInstance(applicationContext).searchByWhereInspect(orderId)
        var equimpment= DbController.getInstance(applicationContext).searchByWhereEquipment(orderId)
        var environment= DbController.getInstance(applicationContext).searchByWhereEnvironment(orderId)
        var jsonObject=JSONObject()
        jsonObject.put("orderIndex",inspect[0].orderIndex)
        jsonObject.put("userId",inspect[0].userId)
        var data=JSONObject()
//        data.put("workStatus",inspect[0].)
        data.put("status",inspect[0].status)
        data.put("netStatus",youxian)
        data.put("noNetStatus",wuxian)
        data.put("pNetStatus",phone)
        data.put("is_unusual",inspect[0].is_unusual)
        data.put("useStatus",inspect[0].useStatus)
        data.put("environmentStatus",inspect[0].environmentStatus)
//        data.put("equipStatus",inspect[0].environmentStatus)
        data.put("cleanStatus",huanjing)
        data.put("cleanPicture",cleanPic)
        data.put("userId",inspect[0].userId)
        data.put("conId",inspect[0].conId)
        data.put("comments",ed_content.text.toString())
        data.put("specialProblem",teshu)
        var environmentInspect=JSONArray()
        environment.forEach{
            var environment=JSONObject()
            environment.put("environmentIndex",it.environmentIndex)
            environment.put("remark",it.remark)
            environment.put("picture",it.picture)
            environment.put("context",it.context)
            environment.put("is_unusual",it.is_unusual)
            environment.put("is_unusual",it.is_unusual)
            environmentInspect.put(environment)
        }
        data.put("environmentInspect",environmentInspect)
        var equimInspect=JSONArray()
        equimpment.forEach {
            var equipment=JSONObject()
            equipment.put("equipmentIndex",it.equipmentIndex)
            equipment.put("remark",it.remark)
            equipment.put("picture",it.picture)
            equipment.put("context",it.context)
            equipment.put("is_unusual",it.is_unusual)
            equipment.put("is_exist",it.is_exist)
            equipment.put("comments",it.comments)
            var coum= DbController.getInstance(applicationContext).searchByWhereConsum(orderId,it.equipmentIndex)
            if (coum.size>0){
                var couns=JSONArray()
                coum.forEach {counmable ->
                    var coun=JSONObject()
                    coun.put("consumableId",counmable.consumableId)
                    coun.put("handId",counmable.handId)
                    coun.put("count",counmable.count)
                }
                equipment.put("comments",couns)
            }else{
                equipment.put("consumable","")
            }
        }
        data.put("equimInspect",equimInspect)
        jsonObject.put("data",data)
        HttpManager.commit(jsonObject.toString()).request(this) { _, data ->
            data?.let {
            }
        }

    }

    private val siteId by lazy{
        intent.getIntExtra("siteId",0)
    }
    private val orderId by lazy{
        intent.getStringExtra("orderId")
    }
    private fun initView() {
        activity_include_tvrignt.text=""
        activity_include_tvrignt.textColor=Color.WHITE
        rv_inspect_equip.layoutManager= GridLayoutManager(applicationContext,3)
        rv_inspect_equip.adapter=adapter
    }

    var list=ArrayList<Equip>()
    private val adapter by lazy {
        EquipAdapter(list,this)
    }
    private fun getEquipUsual() {
        showDialog()
        HttpManager.getAllEquip(siteId).request(this@InspectMainActivity){ _, data->
            data.let {
                list.addAll(it!!)
                adapter.notifyDataSetChanged()
                dismissDialog()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data!=null){
            var pos=data.getIntExtra("position",-1)
            var type=data.getIntExtra("type",-1)
            when(type){
                0->{
                    rv_inspect_equip.getChildAt(pos).findViewById<TextView>(R.id.item_tv_equip).textColor=Color.GREEN
                    rv_inspect_equip.getChildAt(pos).findViewById<TextView>(R.id.item_tv_equip).background=resources.getDrawable(R.drawable.tv_equip_green)
                }
                1->{
                    rv_inspect_equip.getChildAt(pos).findViewById<TextView>(R.id.item_tv_equip).textColor=Color.BLACK
                    rv_inspect_equip.getChildAt(pos).findViewById<TextView>(R.id.item_tv_equip).background=resources.getDrawable(R.drawable.tv_equip_yello)
                }
                2->{
                    rv_inspect_equip.getChildAt(pos).findViewById<TextView>(R.id.item_tv_equip).textColor=Color.RED
                    rv_inspect_equip.getChildAt(pos).findViewById<TextView>(R.id.item_tv_equip).background=resources.getDrawable(R.drawable.tv_equip_red)
                }
            }

        }
    }
}
