package com.bjyw.bjckyh.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import com.bjyw.bjckyh.R
import com.bjyw.bjckyh.adapter.EquipQcodeApapter
import com.bjyw.bjckyh.bean.Equip
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
    override fun onChange(id: Int, position: Int) {}

    override fun onAdd(id: Int, position: Int) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_site_deatil)
        activity_include_btback.onClick {
            finish()
        }
        activity_include_tvtitle.text="台站详情"
        activity_include_tvrignt.text=""
        var isShow=true
        shouqii.onClick {
            if (isShow){
                layout1.visibility=View.GONE
                layout2.visibility=View.GONE
                layout_3.visibility=View.GONE
            }else{
                layout1.visibility=View.VISIBLE
                layout2.visibility=View.VISIBLE
                layout_3.visibility=View.VISIBLE
            }
            isShow=!isShow
        }
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
        var piclist=ArrayList<String>()
        if (details.picture.dmy != ""){
            piclist.add(details.picture.dmy)
        }
        if (details.picture.dmj != ""){
            piclist.add(details.picture.dmj)
        }
        if (details.picture.wky != ""){
            piclist.add(details.picture.wky)
        }
        if (details.picture.tj != ""){
            piclist.add(details.picture.tj)
        }
        if (details.picture.dzy != ""){
            piclist.add(details.picture.dzy)
        }
        if (details.picture.jq != ""){
            piclist.add(details.picture.jq)
        }
        if (details.picture.ssj != ""){
            piclist.add(details.picture.ssj)
        }
        if (details.picture.gqq != ""){
            piclist.add(details.picture.gqq)
        }
        if (details.picture.jwd != ""){
            piclist.add(details.picture.jwd)
        }
        if (details.picture.dxj != ""){
            piclist.add(details.picture.dxj)
        }
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
        if (details.picture.dmj!=null&&details.picture.dmj != ""){
            dmj.scaleType=ImageView.ScaleType.CENTER_CROP
            Glide.with(this).load("http://img.bjckyh.com/"+details.picture.dmj).into(dmj)
        }
        if (details.picture.dmy!=null&&details.picture.dmy != ""){
            dmy.scaleType=ImageView.ScaleType.CENTER_CROP
            Glide.with(this).load("http://img.bjckyh.com/"+details.picture.dmy).into(dmy)
        }
        if (details.picture.wky!=null&&details.picture.wky != ""){
            wky.scaleType=ImageView.ScaleType.CENTER_CROP
            Glide.with(this).load("http://img.bjckyh.com/"+details.picture.wky).into(wky)
        }
        if (details.picture.tj!=null&&details.picture.tj != ""){
            tj.scaleType=ImageView.ScaleType.CENTER_CROP
            Glide.with(this).load("http://img.bjckyh.com/"+details.picture.tj).into(tj)
        }
        if (details.picture.dzy!=null&&details.picture.dzy != ""){
            dzy.scaleType=ImageView.ScaleType.CENTER_CROP
            Glide.with(this).load("http://img.bjckyh.com/"+details.picture.dzy).into(dzy)
        }
        if (details.picture.jq!=null&&details.picture.jq != ""){
            jq.scaleType=ImageView.ScaleType.CENTER_CROP
            Glide.with(this).load("http://img.bjckyh.com/"+details.picture.jq).into(jq)
        }
        if (details.picture.ssj!=null&&details.picture.ssj != ""){
            ssj.scaleType=ImageView.ScaleType.CENTER_CROP
            Glide.with(this).load("http://img.bjckyh.com/"+details.picture.ssj).into(ssj)
        }
        if (details.picture.gqq!=null&&details.picture.gqq != ""){
            gqq.scaleType=ImageView.ScaleType.CENTER_CROP
            Glide.with(this).load("http://img.bjckyh.com/"+details.picture.gqq).into(gqq)
        }
        if (details.picture.jwd!=null&&details.picture.jwd != ""){
            jwd.scaleType=ImageView.ScaleType.CENTER_CROP
            Glide.with(this).load("http://img.bjckyh.com/"+details.picture.jwd).into(jwd)
        }
        if (details.picture.dxj!=null&&details.picture.dxj != ""){
            dxj.scaleType=ImageView.ScaleType.CENTER_CROP
            Glide.with(this).load("http://img.bjckyh.com/"+details.picture.dxj).into(dxj)
        }
        dmy.onClick {
            val intent = Intent(this@SiteDeatilActivity, PhotoActivity::class.java)
            val bundle = Bundle()
            bundle.putStringArrayList("photo", piclist)
            intent.putExtra("photo", bundle)
            intent.putExtra("firstCode", 0)
            startActivity(intent)
        }
        dmj.onClick {
            val intent = Intent(this@SiteDeatilActivity, PhotoActivity::class.java)
            val bundle = Bundle()
            bundle.putStringArrayList("photo", piclist)
            intent.putExtra("photo", bundle)
            intent.putExtra("firstCode", 1)
            startActivity(intent)
        }
        wky.onClick {
            val intent = Intent(this@SiteDeatilActivity, PhotoActivity::class.java)
            val bundle = Bundle()
            bundle.putStringArrayList("photo", piclist)
            intent.putExtra("photo", bundle)
            intent.putExtra("firstCode", 2)
            startActivity(intent)
        }
        tj.onClick {
            val intent = Intent(this@SiteDeatilActivity, PhotoActivity::class.java)
            val bundle = Bundle()
            bundle.putStringArrayList("photo", piclist)
            intent.putExtra("photo", bundle)
            intent.putExtra("firstCode", 3)
            startActivity(intent)
        }
        dzy.onClick {
            val intent = Intent(this@SiteDeatilActivity, PhotoActivity::class.java)
            val bundle = Bundle()
            bundle.putStringArrayList("photo", piclist)
            intent.putExtra("photo", bundle)
            intent.putExtra("firstCode", 4)
            startActivity(intent)
        }
        jq.onClick {
            val intent = Intent(this@SiteDeatilActivity, PhotoActivity::class.java)
            val bundle = Bundle()
            bundle.putStringArrayList("photo", piclist)
            intent.putExtra("photo", bundle)
            intent.putExtra("firstCode", 5)
            startActivity(intent)
        }
        ssj.onClick {
            val intent = Intent(this@SiteDeatilActivity, PhotoActivity::class.java)
            val bundle = Bundle()
            bundle.putStringArrayList("photo", piclist)
            intent.putExtra("photo", bundle)
            intent.putExtra("firstCode", 6)
            startActivity(intent)
        }
        gqq.onClick {
            val intent = Intent(this@SiteDeatilActivity, PhotoActivity::class.java)
            val bundle = Bundle()
            bundle.putStringArrayList("photo", piclist)
            intent.putExtra("photo", bundle)
            intent.putExtra("firstCode", 7)
            startActivity(intent)
        }
        jwd.onClick {
            val intent = Intent(this@SiteDeatilActivity, PhotoActivity::class.java)
            val bundle = Bundle()
            bundle.putStringArrayList("photo", piclist)
            intent.putExtra("photo", bundle)
            intent.putExtra("firstCode", 8)
            startActivity(intent)
        }
        dxj.onClick {
            val intent = Intent(this@SiteDeatilActivity, PhotoActivity::class.java)
            val bundle = Bundle()
            bundle.putStringArrayList("photo", piclist)
            intent.putExtra("photo", bundle)
            intent.putExtra("firstCode", 0)
            startActivity(intent)
        }
    }

    private val id by lazy {
        intent.getStringExtra("siteId")
    }
    var Equiplist=ArrayList<Equip>()
    private val adapter by lazy {
        EquipQcodeApapter(Equiplist,this,false)
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
