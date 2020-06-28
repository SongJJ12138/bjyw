package com.bjyw.bjckyh.ui

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bjyw.bjckyh.R
import com.bjyw.bjckyh.adapter.EquipQcodeApapter
import com.bjyw.bjckyh.bean.Equip
import com.bjyw.bjckyh.bean.Picture
import com.bjyw.bjckyh.bean.SiteDetails
import com.bjyw.bjckyh.dialog.EquipQcodeDialog
import com.bjyw.bjckyh.network.HttpManager
import com.bjyw.bjckyh.network.request
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_site_deatil.*
import kotlinx.android.synthetic.main.toolbar_title.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.toast

class SiteDeatilActivity : BaseActivity(), EquipQcodeApapter.onClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_site_deatil)
        activity_include_btback.onClick {
            finish()
        }
        activity_include_tvtitle.text="台站详情"
        activity_include_tvrignt.text=""
        getData()
    }

    private fun getData() {
        HttpManager.getSiteDetails(id).request(this){ _,data ->
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
        rv_equip.layoutManager=GridLayoutManager(applicationContext,3)
        rv_equip.adapter=adapter
        if (!details.picture.dmj.equals("")){
            dmj.scaleType=ImageView.ScaleType.CENTER_CROP
            Glide.with(this).load("http://img.bjckyh.com/"+details.picture.dmj).into(dmy)
        }
        if (!details.picture.dmy.equals("")){
            dmy.scaleType=ImageView.ScaleType.CENTER_CROP
            Glide.with(this).load("http://img.bjckyh.com/"+details.picture.dmy).into(dmy)
        }
        if (!details.picture.wky.equals("")){
            wky.scaleType=ImageView.ScaleType.CENTER_CROP
            Glide.with(this).load("http://img.bjckyh.com/"+details.picture.wky).into(wky)
        }
        if (!details.picture.tj.equals("")){
            tj.scaleType=ImageView.ScaleType.CENTER_CROP
            Glide.with(this).load("http://img.bjckyh.com/"+details.picture.tj).into(tj)
        }
        if (!details.picture.dzy.equals("")){
            dzy.scaleType=ImageView.ScaleType.CENTER_CROP
            Glide.with(this).load("http://img.bjckyh.com/"+details.picture.dzy).into(dzy)
        }
        if (!details.picture.jq.equals("")){
            jq.scaleType=ImageView.ScaleType.CENTER_CROP
            Glide.with(this).load("http://img.bjckyh.com/"+details.picture.jq).into(jq)
        }
        if (!details.picture.ssj.equals("")){
            ssj.scaleType=ImageView.ScaleType.CENTER_CROP
            Glide.with(this).load("http://img.bjckyh.com/"+details.picture.ssj).into(ssj)
        }
        if (!details.picture.gqq.equals("")){
            gqq.scaleType=ImageView.ScaleType.CENTER_CROP
            Glide.with(this).load("http://img.bjckyh.com/"+details.picture.gqq).into(gqq)
        }
        if (!details.picture.ghq.equals("")){
            jwd.scaleType=ImageView.ScaleType.CENTER_CROP
            Glide.with(this).load("http://img.bjckyh.com/"+details.picture.ghq).into(jwd)
        }
        if (!details.picture.dxj.equals("")){
            dxj.scaleType=ImageView.ScaleType.CENTER_CROP
            Glide.with(this).load("http://img.bjckyh.com/"+details.picture.dxj).into(dxj)
        }
    }

    private val id by lazy {
        intent.getStringExtra("siteId")
    }
    var Equiplist=ArrayList<Equip>()
    private val adapter by lazy {
        EquipQcodeApapter(Equiplist,this)
    }

    override fun dismissDialog() {
        super.dismissDialog()
        toast("数据获取失败")
        this.finish()
    }

    override fun onClick( qcode: String) {
        var dialog= EquipQcodeDialog(this@SiteDeatilActivity,qcode)
        dialog.show()
    }
}
