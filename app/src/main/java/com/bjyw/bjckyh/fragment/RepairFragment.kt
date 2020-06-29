package com.bjyw.bjckyh.fragment

import android.app.Activity
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
import com.bjyw.bjckyh.network.HttpManager
import com.bjyw.bjckyh.network.requestByF
import com.bjyw.bjckyh.utils.TakePhoto
import com.bjyw.bjckyh.utils.convertBitmapToFile
import kotlinx.android.synthetic.main.fragmeny_repair.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.io.File


class RepairFragment : BaseFragment(){
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

     var picPath=""
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

    fun takePic(){
        val myuri: Uri = TakePhoto.getOutputMediaFileUri(context)
        val openCameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, myuri)
        openCameraIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        startActivityForResult(openCameraIntent, REQUEST__CODE_IMAGES)
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
        return EquipRepairBean("",consumList,pic1+pic2,ed_equip_repair.text.toString())
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST__CODE_IMAGES && resultCode == Activity.RESULT_OK){
            val uri = TakePhoto.getOutputMediaFileUri(context)
            val bitmap = TakePhoto.getBitmapFormUri(context, uri)
            if (picType==1){
                img_equip_repqir1.scaleType= ImageView.ScaleType.CENTER_CROP
                img_equip_repqir1.setImageBitmap(bitmap)
            }else{
                img_equip_repqir2.scaleType= ImageView.ScaleType.CENTER_CROP
                img_equip_repqir2.setImageBitmap(bitmap)
            }
            uploadPic(bitmap)
        }
    }

    private fun uploadPic(bitmap: Bitmap) {
        showDialog()
        var file=convertBitmapToFile(activity!!.applicationContext ,bitmap)
        HttpManager.updataPic(file).requestByF(this){ _,data->
            dismissDialog()
            data.let {
                if (picType==1){
                    pic1= it.toString()
                }else{
                    pic2= it.toString()
                }
            }
        }
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