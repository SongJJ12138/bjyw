package com.bjyw.bjckyh.fragment

import android.app.Activity
import android.content.ContentUris
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import com.bjyw.bjckyh.R
import com.bjyw.bjckyh.adapter.ConsumableAdapter
import com.bjyw.bjckyh.bean.Conse
import com.bjyw.bjckyh.bean.Consumable
import com.bjyw.bjckyh.bean.EquipRepairBean
import com.bjyw.bjckyh.bean.daobean.InspectEnvironMent
import com.bjyw.bjckyh.network.HttpManager
import com.bjyw.bjckyh.network.HttpModel
import com.bjyw.bjckyh.network.requestByF
import com.bjyw.bjckyh.utils.TakePhoto
import kotlinx.android.synthetic.main.fragmeny_repair.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.json.JSONObject


class RepairFragment : BaseFragment(), HttpModel.HttpClientListener {
    override fun onSuccess2(response: String, environ: InspectEnvironMent) {}

    var pic=""
    var listPicBit=ArrayList<Bitmap>()
    override fun onSuccess(obj: Any) {
        dismissDialog()
        var jsonObject=JSONObject(obj as String)
        pic=jsonObject.get("data").toString()
    }

    override fun onError() {
        dismissDialog()
        errorToast("上传失败")
    }

    private val REQUEST__CODE_IMAGES = 0x01
    var  picType=1
    var pic1=""
    var pic2=""
    override fun contentViewId()=R.layout.fragmeny_repair

    override fun onFirstVisibleToUser() {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragmeny_repair, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_consumable.isNestedScrollingEnabled = false
        rv_consumable.layoutManager=LinearLayoutManager(context)
        rv_consumable.adapter=adapter
        img_equip_repqir1.onClick {
            picType=1
            takePic()
        }
        img_equip_repqir2.onClick {
            picType=2
            takePic()
        }
        getData()
    }
    var fileName=""
    fun takePic(){
        fileName = "IMG_" + System.currentTimeMillis() + ".jpg"
        val myuri: Uri = TakePhoto.getOutputMediaFileUri(context,fileName)
        var intent =Intent(MediaStore.ACTION_IMAGE_CAPTURE)// 启动系统相机
        intent.putExtra(MediaStore.EXTRA_OUTPUT, myuri)
        startActivityForResult(intent, REQUEST__CODE_IMAGES)
    }

    fun getDataBean():EquipRepairBean{
        var consumList=ArrayList<Conse>()
        for (i in 0 until list.size){
            var id=""+list[i].id
            var count=rv_consumable.getChildAt(i).findViewById<EditText>(R.id.ed_count).text.toString()
            if (count!=null&&!count.equals("")){
                var conse=Conse(id,count,"")
                consumList.add(conse)
            }
        }
        return EquipRepairBean(consumList,pic,ed_equip_repair.text.toString())
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST__CODE_IMAGES && resultCode == Activity.RESULT_OK){
            var bitmap: Bitmap? =null
            try {
                //查询的条件语句
                var selection = MediaStore.Images.Media.DISPLAY_NAME + "=? "
                var cursor = context!!.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,arrayOf(MediaStore.Images.Media._ID),selection, arrayOf(fileName),null)
                if (cursor != null && cursor.moveToFirst()) {
                    do {
                        var uri =  ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, cursor.getLong(0))
                        bitmap = TakePhoto.getBitmapFormUri(context, uri)
                    }while (cursor.moveToNext())
                }
            } catch (e:Exception ) {
                e.printStackTrace()
            }
            if (picType==1){
                img_equip_repqir1.scaleType= ImageView.ScaleType.CENTER_CROP
                img_equip_repqir1.setImageBitmap(bitmap)
            }else{
                img_equip_repqir2.scaleType= ImageView.ScaleType.CENTER_CROP
                img_equip_repqir2.setImageBitmap(bitmap)
            }
            listPicBit.add(bitmap!!)
            uploadPic(listPicBit)
        }
    }

    private fun uploadPic(bitmaps: List<Bitmap>) {
        showDialog()
        var httpModel=HttpModel(this@RepairFragment)
        httpModel.postFile(context!!,bitmaps)
    }

    var list=ArrayList<Consumable>()
    private val adapter by lazy{
        ConsumableAdapter(list)
    }

    private fun getData() {
        HttpManager.getConsumable().requestByF(this){ _, data->
            data?.let {
                list.clear()
                list.addAll(it)
            }
        }
    }


}