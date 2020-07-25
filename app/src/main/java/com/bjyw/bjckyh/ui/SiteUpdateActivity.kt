package com.bjyw.bjckyh.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentUris
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import com.bjyw.bjckyh.R
import com.bjyw.bjckyh.adapter.EquipQcodeApapter
import com.bjyw.bjckyh.bean.Equip
import com.bjyw.bjckyh.bean.SiteDetails
import com.bjyw.bjckyh.bean.daobean.InspectEnvironMent
import com.bjyw.bjckyh.dialog.ChangePeopleDialog
import com.bjyw.bjckyh.dialog.onePicSelectDialog
import com.bjyw.bjckyh.network.HttpManager
import com.bjyw.bjckyh.network.HttpModel
import com.bjyw.bjckyh.network.request
import com.bjyw.bjckyh.utils.MapLocationUtil
import com.bjyw.bjckyh.utils.TakePhoto
import com.bjyw.bjckyh.utils.convertBitmapToFile
import com.bumptech.glide.Glide
import com.yzq.zxinglibrary.android.CaptureActivity
import com.yzq.zxinglibrary.common.Constant
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
import org.jetbrains.anko.textColor
import org.jetbrains.anko.toast
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

@SuppressLint("SimpleDateFormat")
class SiteUpdateActivity: BaseActivity(), EquipQcodeApapter.onClickListener,
    ChangePeopleDialog.onClickListener, HttpModel.HttpClientListener {

    private val REQUEST__CODE_IMAGES=0x01
    private val REQUEST_CODE_SCAN=0x02
    private val SELECTPICEQUEST_CODE = 0X03
    private val TAKAPHOTO_CODE = 0X04
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
    @SuppressLint("SetTextI18n")
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
        bt_pic.onClick {
            var pic=JSONObject()
            var jsonObject=JSONObject()
            jsonObject.put("siteId",""+siteId)
            jsonObject.put("lat","")
            jsonObject.put("lng","")
            jsonObject.put("contact","")
            jsonObject.put("contactnum","")
            jsonObject.put("updateTime",sim)
            pic.put("dmj",Piclist[0])
            pic.put("dmy",Piclist[1])
            pic.put("wky",Piclist[2])
            pic.put("tj",Piclist[3])
            pic.put("dzy",Piclist[4])
            pic.put("jq",Piclist[5])
            pic.put("ssj",Piclist[6])
            pic.put("gqq",Piclist[7])
            pic.put("jwd",Piclist[8])
            pic.put("dxj",Piclist[9])
            jsonObject.put("picture",pic.toString())
            jsonObject.put("equipList",JSONArray())
            updateSite(jsonObject.toString())
        }
        bt_people.onClick {
            var dialog=ChangePeopleDialog(this@SiteUpdateActivity,this@SiteUpdateActivity)
            dialog.show()
         }
        bt_equip.onClick {
            var jsonObject=JSONObject()
            jsonObject.put("siteId",""+siteId)
            jsonObject.put("lat","")
            jsonObject.put("lng","")
            jsonObject.put("contact","")
            jsonObject.put("contactnum","")
            jsonObject.put("updateTime",sim)
            var picture=JSONObject()
            jsonObject.put("picture",picture)
            var equips=JSONArray()
            Equiplist.forEach {
                var equip=JSONObject()
                if (!it.qrcode.equals("")){
                    equip.put("id",""+it.id)
                    equip.put("qrcode",""+it.qrcode)
                    equips.put(equip)
                }
            }
            jsonObject.put("equipList",equips)
            updateSite(jsonObject.toString())
        }
    }

    private val sim by lazy{
        var date = Date()
        var dateFormat =SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        dateFormat.format(date)
    }

    private fun updateSite(toString: String) {
        showDialog()
        HttpManager.updateSite(toString).request(this){ _, data ->
            data.let {
                dismissDialog()
                toast("更新成功")
            }
        }
    }

    private val siteId by lazy{
        intent.getIntExtra("siteId",0)
    }


    private fun getData() {
        showDialog()
        if (siteId==0){
            toast("当前二维码未绑定站点")
            finish()
        }
        HttpManager.getSiteDetails(""+siteId).request(this){ _, data ->
            data.let {
                Equiplist.clear()
                Equiplist.addAll(it!!.equipList)
                initView(it!!)
            }
        }
    }
    override fun onSuccess2(response: String, environ: InspectEnvironMent) {}
    private val Piclist by lazy{
        var list=ArrayList<String>()
        list.add("")
        list.add("")
        list.add("")
        list.add("")
        list.add("")
        list.add("")
        list.add("")
        list.add("")
        list.add("")
        list.add("")
        list
    }

    var picIndex=0
    var fileName=""
    private fun initView(details: SiteDetails) {
        if (details.picture.dmj!=null){
            Piclist.set(0,details.picture.dmj)
        }
        if (details.picture.dmy!=null){
            Piclist.set(1,details.picture.dmy)
        }
        if (details.picture.wky!=null){
            Piclist.set(2,details.picture.wky)
        }
        if (details.picture.tj!=null){
            Piclist.set(3,details.picture.tj)
        }
        if (details.picture.dzy!=null){
            Piclist.set(4,details.picture.dzy)
        }
        if (details.picture.jq!=null){
            Piclist.set(5,details.picture.jq)
        }
        if (details.picture.ssj!=null){
            Piclist.set(6,details.picture.ssj)
        }
        if (details.picture.gqq!=null){
            Piclist.set(7,details.picture.gqq)
        }
        if (details.picture.jwd!=null){
            Piclist.set(8,details.picture.jwd)
        }
        if (details.picture.dxj!=null){
            Piclist.set(9,details.picture.dxj)
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
        rv_equip.layoutManager= GridLayoutManager(applicationContext,3)
        rv_equip.adapter=adapter
        if (details.picture.dmj!=null&&!details.picture.dmj.equals("")){
            dmj.scaleType= ImageView.ScaleType.CENTER_CROP
            Glide.with(this).load("http://img.bjckyh.com/"+details.picture.dmj).into(dmj)
        }
        dmj.onClick {
            picIndex=0
            fileName = "IMG_" + System.currentTimeMillis() + ".jpg"
            takePhoto(fileName)
        }
        if (details.picture.dmy!=null&&!details.picture.dmy.equals("")){
            dmy.scaleType= ImageView.ScaleType.CENTER_CROP
            Glide.with(this).load("http://img.bjckyh.com/"+details.picture.dmy).into(dmy)
        }
        dmy.onClick {
            picIndex=1
            fileName = "IMG_" + System.currentTimeMillis() + ".jpg"
            takePhoto(fileName)
        }
        if (details.picture.wky!=null&&!details.picture.wky.equals("")){
            wky.scaleType= ImageView.ScaleType.CENTER_CROP
            Glide.with(this).load("http://img.bjckyh.com/"+details.picture.wky).into(wky)
        }
        wky.onClick {
            picIndex=2
            fileName = "IMG_" + System.currentTimeMillis() + ".jpg"
            takePhoto(fileName)
        }
        if (details.picture.tj!=null&&!details.picture.tj.equals("")){
            tj.scaleType= ImageView.ScaleType.CENTER_CROP
            Glide.with(this).load("http://img.bjckyh.com/"+details.picture.tj).into(tj)
        }
        tj.onClick {
            picIndex=3
            fileName = "IMG_" + System.currentTimeMillis() + ".jpg"
            takePhoto(fileName)
        }
        if (details.picture.dzy!=null&&!details.picture.dzy.equals("")){
            dzy.scaleType= ImageView.ScaleType.CENTER_CROP
            Glide.with(this).load("http://img.bjckyh.com/"+details.picture.dzy).into(dzy)
        }
        dzy.onClick {
            picIndex=4
            fileName = "IMG_" + System.currentTimeMillis() + ".jpg"
            takePhoto(fileName)
        }
        if (details.picture.jq!=null&&!details.picture.jq.equals("")){
            jq.scaleType= ImageView.ScaleType.CENTER_CROP
            Glide.with(this).load("http://img.bjckyh.com/"+details.picture.jq).into(jq)
        }
        jq.onClick {
            picIndex=5
            fileName = "IMG_" + System.currentTimeMillis() + ".jpg"
            takePhoto(fileName)
        }
        if (details.picture.ssj!=null&&!details.picture.ssj.equals("")){
            ssj.scaleType= ImageView.ScaleType.CENTER_CROP
            Glide.with(this).load("http://img.bjckyh.com/"+details.picture.ssj).into(ssj)
        }
        ssj.onClick {
            picIndex=6
            fileName = "IMG_" + System.currentTimeMillis() + ".jpg"
            takePhoto(fileName)
        }
        if (details.picture.gqq!=null&&!details.picture.gqq.equals("")){
            gqq.scaleType= ImageView.ScaleType.CENTER_CROP
            Glide.with(this).load("http://img.bjckyh.com/"+details.picture.gqq).into(gqq)
        }
        gqq.onClick {
            picIndex=7
            fileName = "IMG_" + System.currentTimeMillis() + ".jpg"
            takePhoto(fileName)
        }
        if (details.picture.jwd!=null&&!details.picture.jwd.equals("")){
            jwd.scaleType= ImageView.ScaleType.CENTER_CROP
            Glide.with(this).load("http://img.bjckyh.com/"+details.picture.jwd).into(jwd)
        }
        jwd.onClick {
            picIndex=8
            fileName = "IMG_" + System.currentTimeMillis() + ".jpg"
            takePhoto(fileName)
        }
        if (details.picture.dxj!=null&&!details.picture.dxj.equals("")){
            dxj.scaleType= ImageView.ScaleType.CENTER_CROP
            Glide.with(this).load("http://img.bjckyh.com/"+details.picture.dxj).into(dxj)
        }
        dxj.onClick {
            picIndex=9
            fileName = "IMG_" + System.currentTimeMillis() + ".jpg"
            takePhoto(fileName)
        }
        dismissDialog()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_SCAN && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                rv_equip.getChildAt(equipPosition).findViewById<TextView>(R.id.item_tv_equip).background=resources.getDrawable(R.drawable.tv_equip2)
                rv_equip.getChildAt(equipPosition).findViewById<TextView>(R.id.item_tv_equip).textColor=Color.RED
                val content = data.getStringExtra(Constant.CODED_CONTENT)
                Equiplist[equipPosition].id=equipId
                Equiplist[equipPosition].qrcode=content

            }
        }else if (requestCode == TAKAPHOTO_CODE){
            try {
                //查询的条件语句
                var selection = MediaStore.Images.Media.DISPLAY_NAME + "=? "
                var cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,arrayOf(MediaStore.Images.Media._ID),selection, arrayOf(fileName),null)
                if (cursor != null && cursor.moveToFirst()) {
                    do {
                        var uri =  ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, cursor.getLong(0))
                        showPic(uri)
                    }while (cursor.moveToNext())
                }
            } catch (e:Exception ) {
                e.printStackTrace()
            }
        }else{
            var uri =data?.data
            if (uri != null) {
                showPic(uri)
            }
        }
    }

    private fun showPic(uri: Uri) {
        val bitmap = TakePhoto.getBitmapFormUri(applicationContext, uri)
        when(picIndex){
            0 ->{
                dmj.scaleType=ImageView.ScaleType.CENTER_CROP
                dmj.setImageBitmap(bitmap)
            }
            1 ->{
                dmy.scaleType=ImageView.ScaleType.CENTER_CROP
                dmy .setImageBitmap(bitmap)
            }
            2 ->{
                wky.scaleType=ImageView.ScaleType.CENTER_CROP
                wky .setImageBitmap(bitmap)
            }
            3 ->{
                tj.scaleType=ImageView.ScaleType.CENTER_CROP
                tj .setImageBitmap(bitmap)
            }
            4 ->{
                dzy.scaleType=ImageView.ScaleType.CENTER_CROP
                dzy .setImageBitmap(bitmap)
            }
            5 ->{
                jq.scaleType=ImageView.ScaleType.CENTER_CROP
                jq .setImageBitmap(bitmap)
            }
            6 ->{
                ssj.scaleType=ImageView.ScaleType.CENTER_CROP
                ssj .setImageBitmap(bitmap)
            }
            7 ->{
                gqq.scaleType=ImageView.ScaleType.CENTER_CROP
                gqq .setImageBitmap(bitmap)
            }
            8 ->{
                jwd.scaleType=ImageView.ScaleType.CENTER_CROP
                jwd .setImageBitmap(bitmap)
            }
            9 ->{
                dxj.scaleType=ImageView.ScaleType.CENTER_CROP
                dxj.setImageBitmap(bitmap)
            }
        }
        var picFiles=ArrayList<File>()
        picFiles.add(convertBitmapToFile(applicationContext,bitmap,"pic"))
        uploadPic(picFiles)
    }

    private fun uploadPic(files: ArrayList<File>) {
        showDialog()
        var httpModel= HttpModel(this@SiteUpdateActivity)
        httpModel.postFile3(applicationContext,files)
    }
    override fun onError() {
        dismissDialog()
        errorToast("图片上传失败")
    }

    override fun onSuccess(obj: Any) {
        dismissDialog()
        var jsonObject= JSONObject(obj as String)
        var pic=jsonObject.getJSONObject("data").getString("1")
        Piclist.set(picIndex,pic)
    }

    private fun takePhoto(fileName: String) {
        val onePicDialog = onePicSelectDialog(this@SiteUpdateActivity, fileName,object : onePicSelectDialog.SelectPicListener {
            override fun onChooseTake(intent: Intent?) {

                startActivityForResult(intent, TAKAPHOTO_CODE)
            }
            override fun onChooseSelect(intent: Intent?) {
                startActivityForResult(intent, SELECTPICEQUEST_CODE)
            }
        })
        onePicDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        onePicDialog.show()
    }

    var Equiplist=ArrayList<Equip>()
    private val adapter by lazy {
        EquipQcodeApapter(Equiplist,this,true)
    }

    var equipPosition=0
    var equipId=0
    override fun onClick( qcode: String) {}
    override fun onChange(id: Int,pos:Int) {
        equipPosition=pos
        equipId=id
        val intent = Intent(this@SiteUpdateActivity, CaptureActivity::class.java)
        startActivityForResult(intent, REQUEST_CODE_SCAN)
    }
    override fun onAdd(id: Int,pos:Int) {
        equipPosition=pos
        equipId=id
        val intent = Intent(this@SiteUpdateActivity, CaptureActivity::class.java)
        startActivityForResult(intent, REQUEST_CODE_SCAN)
    }
}