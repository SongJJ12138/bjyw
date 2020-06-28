package com.bjyw.bjckyh.ui

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bjyw.bjckyh.R
import com.bjyw.bjckyh.adapter.EquipQcodeApapter
import com.bjyw.bjckyh.bean.Equip
import com.bjyw.bjckyh.bean.SiteDetails
import com.bjyw.bjckyh.network.HttpManager
import com.bjyw.bjckyh.network.request
import kotlinx.android.synthetic.main.activity_site_deatil.*
import org.jetbrains.anko.toast

class SiteDeatilActivity : BaseActivity(), EquipQcodeApapter.onClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_site_deatil)
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
        //???
        safe.text="站点安全等级：2级"
        rating.rating=2F
        rv_equip.layoutManager=GridLayoutManager(applicationContext,3)
        rv_equip.adapter=adapter
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

    override fun onClick(position: Int, equipId: String) {
    }
}
