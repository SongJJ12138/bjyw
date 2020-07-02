package com.bjyw.bjckyh.ui

import android.content.ContentUris
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import com.bjyw.bjckyh.R
import com.bjyw.bjckyh.adapter.EquipAdapter
import com.bjyw.bjckyh.bean.EquipBean
import com.bjyw.bjckyh.bean.daobean.InspectEnvironMent
import com.bjyw.bjckyh.bean.daobean.InspectEquipMent
import com.bjyw.bjckyh.dialog.CommitFaileDialog
import com.bjyw.bjckyh.dialog.CommitSuccessDialog
import com.bjyw.bjckyh.network.HttpManager
import com.bjyw.bjckyh.network.HttpModel
import com.bjyw.bjckyh.network.request
import com.bjyw.bjckyh.utils.*
import kotlinx.android.synthetic.main.activity_inspect_main.*
import kotlinx.android.synthetic.main.item_order.*
import kotlinx.android.synthetic.main.toolbar_title.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.textColor
import org.jetbrains.anko.toast
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.FileInputStream

@Suppress("DEPRECATION")
class InspectMainActivity : BaseActivity(), EquipAdapter.onClickListener,
    HttpModel.HttpClientListener {


    private  val REQUEST_CODE=0x01
    private val REQUEST__CODE_IMAGES=0x02
    var httpType=0
    private var youxian=""
    private var wuxian=""
    private var phone=""
    private var huanjing=-1
    private var teshu="0"
    private  var cleanPic=""
    private  var picIndex=0
    var picPath=""
    var picList=ArrayList<Bitmap>()
    override fun onClick(position:Int,equipId: String) {
        if (isok){
            var intent=Intent(this@InspectMainActivity,InspectDetailActivity::class.java)
            intent.putExtra("equipId",equipId)
            intent.putExtra("orderId",orderId)
            intent.putExtra("position",position)
            startActivityForResult(intent,REQUEST_CODE)
        }else{
            toast("异常巡检，无法点击")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inspect_main)
        initView()
        initClick()
        getEquipUsual()
    }

    private val isok by lazy {
       intent.getBooleanExtra("isOk",true)
    }

    var fileName=""
    private fun initClick() {
        activity_include_btback.onClick {
            finish()
        }
        img_clean1.onClick {
            picIndex=0
            fileName = "IMG_" + System.currentTimeMillis() + ".jpg"
            val myuri: Uri = TakePhoto.getOutputMediaFileUri(applicationContext,fileName)
            var intent =Intent(MediaStore.ACTION_IMAGE_CAPTURE)// 启动系统相机
            intent.putExtra(MediaStore.EXTRA_OUTPUT, myuri)
            startActivityForResult(intent, REQUEST__CODE_IMAGES)
        }
        img_clean2.onClick {
            picIndex=1
            fileName = "IMG_" + System.currentTimeMillis() + ".jpg"
            val myuri: Uri = TakePhoto.getOutputMediaFileUri(applicationContext,fileName)
            var intent =Intent(MediaStore.ACTION_IMAGE_CAPTURE)// 启动系统相机
            intent.putExtra(MediaStore.EXTRA_OUTPUT, myuri)
            startActivityForResult(intent, REQUEST__CODE_IMAGES)
        }
        img_clean3.onClick {
            picIndex=2
            fileName = "IMG_" + System.currentTimeMillis() + ".jpg"
            val myuri: Uri = TakePhoto.getOutputMediaFileUri(applicationContext,fileName)
            var intent =Intent(MediaStore.ACTION_IMAGE_CAPTURE)// 启动系统相机
            intent.putExtra(MediaStore.EXTRA_OUTPUT, myuri)
            startActivityForResult(intent, REQUEST__CODE_IMAGES)
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
            huanjing=2
        }
        rb_environment_yiban.onClick {
            huanjing=1
        }
        rb_environment_bad.onClick {
            huanjing=0
        }
        rb_special_banqian.onClick {
            teshu="1"
        }
        rb_special_fengcun.onClick {
            teshu="2"
        }
        rb_special_chaichu.onClick {
            teshu="3"
        }
        rb_special_huifu.onClick {
            teshu="4"
        }
    }

    private fun checkItem() {
        if (youxian == ""){
            toast("请选择有线网络状态")
            return
        }
        if (wuxian == ""){
            toast("请选择无线网络状态")
            return
        }
        if (phone == ""){
            toast("请选择手机信号状态")
            return
        }
        if (huanjing ==-1){
            toast("请选择环境情况")
            return
        }
        var picFiles=ArrayList<File>()
        picList.forEach {
            picFiles.add(convertBitmapToFile(applicationContext,it))
        }
        if (picFiles.size>0){
            uploadPic(picFiles)
        }else{
            showDialog()
            commit()
        }
    }

    private fun uploadPic(files: ArrayList<File>) {
        showDialog()
        var httpModel= HttpModel(this@InspectMainActivity)
        httpModel.postFile3(applicationContext,files)
    }
    override fun onError() {
        dismissDialog()
        errorToast("图片上传失败")
    }

    override fun onSuccess(obj: Any) {
        var jsonObject= JSONObject(obj as String)
        var pic=jsonObject.get("data").toString()
        cleanPic= pic
        commit()
    }

    override fun onSuccess2(response: String, environ: InspectEnvironMent) {
    }

    private val inspect by lazy{
        DbController.getInstance(applicationContext).searchById(orderId)
    }

    private val equimpmentList by lazy{
        DbController.getInstance(applicationContext).searchByWhereEquipment(orderId)
    }

    private val environmentList by lazy{
        DbController.getInstance(applicationContext).searchByWhereEnvironment(orderId)
    }
    private fun commit() {
        val jsonObject=JSONObject()
        if (inspect.today){
            jsonObject.put("today","0")
            jsonObject.put("orderIndex","")
        }else{
            jsonObject.put("today","1")
            jsonObject.put("orderIndex",inspect.orderIndex)
        }
        jsonObject.put("siteId",""+inspect.siteId)
        jsonObject.put("userId",""+SPUtils.instance().getInt("userId"))

        val data=JSONObject()
        data.put("status","5")
        data.put("userId",""+SPUtils.instance().getInt("userId"))
        data.put("netStatus",youxian)
        data.put("noNetStatus",wuxian)
        data.put("pNetStatus",phone)
        data.put("is_unusual",inspect.is_unusual)
        data.put("useStatus",inspect.useStatus)
        var environStatus=inspect.environmentStatus.toInt()
        data.put("environmentStatus",""+environStatus)
        data.put("cleanStatus",""+huanjing)
        data.put("cleanPicture",cleanPic)
        data.put("conId",inspect.conId)
        data.put("comments",ed_content.text.toString())
        data.put("specialProblem",teshu)
        val environmentInspect=JSONArray()
        environmentList.forEach{
            val environment=JSONObject()
            environment.put("environmentIndex",it.environmentIndex)
            environment.put("remark",it.remark)
            environment.put("picture",it.picture)
            environment.put("context",it.context)
            environment.put("is_unusual",it.is_unusual)
            environment.put("is_unusual",it.is_unusual)
            environmentInspect.put(environment)
        }
        data.put("environmentInspect",environmentInspect)
        val equimInspect=JSONArray()
        var badEquip=StringBuffer()
        equimpmentList.forEach {
            val equipment=JSONObject()
            equipment.put("equipmentIndex",it.equipmentIndex)
            equipment.put("remark",it.remark)
            equipment.put("picture",it.picture)
            equipment.put("context",it.context)
            equipment.put("is_unusual",it.is_unusual)
            equipment.put("is_exist",it.is_exist)
            equipment.put("comments",it.comments)
            if(it.is_unusual.equals("1")||it.is_exist.equals("1")){
                badEquip.append(it.equipName)
            }
            var badEquipStr=badEquip.toString()
            var equipStatus=3
            if (badEquipStr.equals("")){
                equipStatus=3
            }else if (badEquipStr.contains("总电源")||badEquipStr.contains("UPS")){
                equipStatus=1
            }else if (badEquipStr.equals("天线1")&&badEquipStr.equals("天线2")){
                equipStatus=1
            }else if (badEquipStr.equals("主馈线")&&badEquipStr.equals("备用馈线")){
                equipStatus=1
            }else if (badEquipStr.equals("主解码器")&&badEquipStr.equals("备用解码器")){
                equipStatus=1
            }else if (badEquipStr.equals("主发射机")&&badEquipStr.equals("备用发射机")){
                equipStatus=1
            }else{
                equipStatus=2
            }
            data.put("equipStatus",""+equipStatus)
            if (equipStatus==1){
                data.put("workStatus","1")
            }else{
                if (environStatus==0){
                    data.put("workStatus","2")
                }else{
                    data.put("workStatus","3")
                }
            }
            val coum= DbController.getInstance(applicationContext).searchByWhereConsum(orderId,it.equipmentIndex)
            val couns=JSONArray()
            if (coum.size>0){
                coum.forEach {counmable ->
                    val coun=JSONObject()
                    coun.put("consumableId",counmable.consumableId)
                    coun.put("count",counmable.count)
                    couns.put(coun)
                }

            }
            equipment.put("consumable",couns)
            equimInspect.put(equipment)
        }
        data.put("equimInspect",equimInspect)
        jsonObject.put("data",data)
        httpType=1
        HttpManager.commit(jsonObject.toString()).request(this) { _, data ->
            data?.let {
                httpType=0
                dismissDialog()
                var dialog= CommitSuccessDialog(this@InspectMainActivity,object:
                    CommitSuccessDialog.onClickListener{
                    override fun onClick() {
                        clearData(true)
                        var intent=Intent(this@InspectMainActivity,MainActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                        this@InspectMainActivity.finish()
                    }
                })
                dialog.show()
            }
        }
    }

    private fun clearData(b: Boolean) {
        inspect.status=""+5
        DbController.getInstance(applicationContext).insertOrReplaceInspect(inspect)
        if (b){
            DbController.getInstance(applicationContext).deleteOrderEquipment(orderId)
            DbController.getInstance(applicationContext).deleteOrderEnvironment(orderId)
            DbController.getInstance(applicationContext).deleteOrderConsum(orderId)
        }
    }

    override fun dismissDialog() {
        super.dismissDialog()
        if (httpType==1){
            httpType=0
            var dialog= CommitFaileDialog(this@InspectMainActivity,object:
                CommitFaileDialog.onClickListener{
                override fun onClick() {
                    clearData(false)
                    var intent=Intent(this@InspectMainActivity,MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    this@InspectMainActivity.finish()
                }
            })
            dialog.show()
        }
    }

    private val siteId by lazy{
        intent.getIntExtra("siteId",0)
    }
    private val orderId by lazy{
        intent.getStringExtra("orderId")
    }
    private val status by lazy{
        intent.getIntExtra("status",0)
    }
    private fun initView() {
        activity_include_tvrignt.text=""
        activity_include_tvrignt.textColor=Color.WHITE
        rv_inspect_equip.layoutManager= GridLayoutManager(applicationContext,3)
        rv_inspect_equip.adapter=adapter
    }

    var list=ArrayList<EquipBean>()
    private val adapter by lazy {
        EquipAdapter(list,this)
    }
    private fun getEquipUsual() {
        showDialog()
        HttpManager.getAllEquip(siteId).request(this@InspectMainActivity){ _, data->
            data.let {
                it!!.forEach{ equip ->
                    var inspectEquipMent= InspectEquipMent()
                    inspectEquipMent.equipName=equip.name
                    inspectEquipMent.equipmentIndex=""+equip.id
                    inspectEquipMent.comments=""
                    inspectEquipMent.context=""
                    inspectEquipMent.is_exist="0"
                    inspectEquipMent.is_unusual="0"
                    inspectEquipMent.orderIndex=orderId
                    inspectEquipMent.picture=""
                    inspectEquipMent.remark=""
                    DbController.getInstance(applicationContext).insertOrReplaceEquipment(inspectEquipMent)
                }
                list.addAll(it!!)
                adapter.notifyDataSetChanged()
                dismissDialog()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST__CODE_IMAGES ){
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
            if (data!=null){
                val pos=data.getIntExtra("position",-1)
                when(data.getIntExtra("type",-1)){
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
    private fun showPic(uri: Uri) {
        val bitmap = TakePhoto.getBitmapFormUri(applicationContext, uri)
        picList.add(bitmap)
        when(picIndex){
            0 ->{
                img_clean1.scaleType=ImageView.ScaleType.CENTER_CROP
                img_clean1.setImageBitmap(bitmap)
            }
            1 ->{
                img_clean2.scaleType=ImageView.ScaleType.CENTER_CROP
                img_clean2.setImageBitmap(bitmap)
            }
            2 ->{
                img_clean3.scaleType=ImageView.ScaleType.CENTER_CROP
                img_clean3.setImageBitmap(bitmap)
            }

        }
    }
}
