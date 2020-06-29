package com.bjyw.bjckyh.ui

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import com.bjyw.bjckyh.R
import com.bjyw.bjckyh.adapter.EquipQcodeApapter
import com.bjyw.bjckyh.bean.Equip
import com.bjyw.bjckyh.bean.SiteDetails
import com.bjyw.bjckyh.dialog.ChangePeopleDialog
import com.bjyw.bjckyh.dialog.EquipQcodeDialog
import com.bjyw.bjckyh.network.HttpManager
import com.bjyw.bjckyh.network.request
import com.bjyw.bjckyh.utils.MapLocationUtil
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_site_deatil.*
import kotlinx.android.synthetic.main.activity_site_deatil.district
import kotlinx.android.synthetic.main.activity_site_deatil.dmj
import kotlinx.android.synthetic.main.activity_site_deatil.dmy
import kotlinx.android.synthetic.main.activity_site_deatil.dxj
import kotlinx.android.synthetic.main.activity_site_deatil.dzy
import kotlinx.android.synthetic.main.activity_site_deatil.gqq
import kotlinx.android.synthetic.main.activity_site_deatil.jq
import kotlinx.android.synthetic.main.activity_site_deatil.jwd
import kotlinx.android.synthetic.main.activity_site_deatil.lat
import kotlinx.android.synthetic.main.activity_site_deatil.lng
import kotlinx.android.synthetic.main.activity_site_deatil.people
import kotlinx.android.synthetic.main.activity_site_deatil.phone
import kotlinx.android.synthetic.main.activity_site_deatil.pinlv
import kotlinx.android.synthetic.main.activity_site_deatil.rating
import kotlinx.android.synthetic.main.activity_site_deatil.rv_equip
import kotlinx.android.synthetic.main.activity_site_deatil.safe
import kotlinx.android.synthetic.main.activity_site_deatil.ssj
import kotlinx.android.synthetic.main.activity_site_deatil.time
import kotlinx.android.synthetic.main.activity_site_deatil.tj
import kotlinx.android.synthetic.main.activity_site_deatil.town
import kotlinx.android.synthetic.main.activity_site_deatil.village
import kotlinx.android.synthetic.main.activity_site_deatil.wky
import kotlinx.android.synthetic.main.activity_site_update.*
import kotlinx.android.synthetic.main.toolbar_title.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.toast
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class SiteUpdateActivity: BaseActivity(), EquipQcodeApapter.onClickListener,
    ChangePeopleDialog.onClickListener {
    override fun onClick(nameStr: String, phoneStr: String) {
        var jsonObject=JSONObject()
        jsonObject.put("siteId",""+siteId)
        jsonObject.put("lat","")
        jsonObject.put("lng","")
        jsonObject.put("contact",nameStr)
        jsonObject.put("contactnum",phoneStr)
        jsonObject.put("updateTime",sim)
        jsonObject.put("picture","")
        jsonObject.put("equipList",JSONArray())
        updateSite(jsonObject.toString())
        people.text="负责人："+nameStr
        phone.text="联系方式："+phoneStr
    }

    override fun onNull() {
        toast("负责人或手机号码为空")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_site_update)
        activity_include_btback.onClick {
            finish()
        }
        activity_include_tvtitle.text="台站修改"
        activity_include_tvrignt.text=""
        initClick()
        getData()
    }
//    {
//        "siteId":"1",
//        "lat":"",
//        "lng":"",
//        "contact":"王振",
//        "contactnum":"13718050370",
//        "updateTime":"2020-6-17 16:00:00",
//        "picture":"",
//        "equipList":[
//        {
//            "id":"1",
//            "qrcode":"321312"
//        }
//        ]
//    }
    private fun initClick() {
        var isShow=true
        shouqi.onClick {
            if (isShow){
                layout11.visibility=View.GONE
                layout22.visibility=View.GONE
                layout_33.visibility=View.GONE
            }else{
                layout11.visibility=View.VISIBLE
                layout22.visibility=View.VISIBLE
                layout_33.visibility=View.VISIBLE
            }
            isShow=!isShow
        }
        bt_latlng.onClick {
            var jsonObject=JSONObject()
            jsonObject.put("siteId",""+siteId)
            jsonObject.put("lat",MapLocationUtil.instance.lat)
            jsonObject.put("lng",MapLocationUtil.instance.lng)
            jsonObject.put("contact","")
            jsonObject.put("contactnum","")
            jsonObject.put("updateTime",sim)
            jsonObject.put("picture","")
            jsonObject.put("equipList",JSONArray())
            updateSite(jsonObject.toString())
            lat.text="经度："+MapLocationUtil.instance.lat
            lng.text="纬度："+MapLocationUtil.instance.lng
        }
        bt_people.onClick {
            var dialog=ChangePeopleDialog(this@SiteUpdateActivity,this@SiteUpdateActivity)
            dialog.show()
         }
    }

    private val sim by lazy{
        var date = Date()
        var dateFormat =SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        dateFormat.format(date)
    }

    private fun updateSite(toString: String) {
        HttpManager.updateSite(toString).request(this){ _, data ->
            data.let {
                toast("更新成功")
            }
        }
    }

    private val siteId by lazy{
        intent.getIntExtra("siteId",1)
    }

    private fun getData() {
        showDialog()
        HttpManager.getSiteDetails(""+siteId).request(this){ _, data ->
            data.let {
                Equiplist.clear()
                Equiplist.addAll(it!!.equipList)
                initView(it!!)
            }
        }

    }
    private fun initView(details: SiteDetails) {
        district.text=details.district
        town.text=details.town
        village.text=details.village
        lat.text="经度："+details.lat
        lng.text="纬度："+details.lng
        time.text="时间："+details.time
        pinlv.text=details.frequency
        people.text="负责人："+details.contact
        phone.text="联系电话：："+details.contactnum
        var safeStatus=details.grade1+details.grade2+details.grade3+details.grade4
        safe.text="站点安全等级："+safeStatus+"级"
        rating.rating= safeStatus.toFloat()
        rv_equip.layoutManager= GridLayoutManager(applicationContext,3)
        rv_equip.adapter=adapter
        if (!details.picture.dmj.equals("")){
            dmj.scaleType= ImageView.ScaleType.CENTER_CROP
            Glide.with(this).load("http://img.bjckyh.com/"+details.picture.dmj).into(dmy)
        }
        if (!details.picture.dmy.equals("")){
            dmy.scaleType= ImageView.ScaleType.CENTER_CROP
            Glide.with(this).load("http://img.bjckyh.com/"+details.picture.dmy).into(dmy)
        }
        if (!details.picture.wky.equals("")){
            wky.scaleType= ImageView.ScaleType.CENTER_CROP
            Glide.with(this).load("http://img.bjckyh.com/"+details.picture.wky).into(wky)
        }
        if (!details.picture.tj.equals("")){
            tj.scaleType= ImageView.ScaleType.CENTER_CROP
            Glide.with(this).load("http://img.bjckyh.com/"+details.picture.tj).into(tj)
        }
        if (!details.picture.dzy.equals("")){
            dzy.scaleType= ImageView.ScaleType.CENTER_CROP
            Glide.with(this).load("http://img.bjckyh.com/"+details.picture.dzy).into(dzy)
        }
        if (!details.picture.jq.equals("")){
            jq.scaleType= ImageView.ScaleType.CENTER_CROP
            Glide.with(this).load("http://img.bjckyh.com/"+details.picture.jq).into(jq)
        }
        if (!details.picture.ssj.equals("")){
            ssj.scaleType= ImageView.ScaleType.CENTER_CROP
            Glide.with(this).load("http://img.bjckyh.com/"+details.picture.ssj).into(ssj)
        }
        if (!details.picture.gqq.equals("")){
            gqq.scaleType= ImageView.ScaleType.CENTER_CROP
            Glide.with(this).load("http://img.bjckyh.com/"+details.picture.gqq).into(gqq)
        }
        if (!details.picture.ghq.equals("")){
            jwd.scaleType= ImageView.ScaleType.CENTER_CROP
            Glide.with(this).load("http://img.bjckyh.com/"+details.picture.ghq).into(jwd)
        }
        if (!details.picture.dxj.equals("")){
            dxj.scaleType= ImageView.ScaleType.CENTER_CROP
            Glide.with(this).load("http://img.bjckyh.com/"+details.picture.dxj).into(dxj)
        }
        dismissDialog()
    }

    var Equiplist=ArrayList<Equip>()
    private val adapter by lazy {
        EquipQcodeApapter(Equiplist,this)
    }


    override fun onClick( qcode: String) {
        var dialog= EquipQcodeDialog(this@SiteUpdateActivity,qcode)
        dialog.show()
    }
}