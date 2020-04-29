package com.lyb.purchasesystem.ui.clapatwill

import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.AdapterView
import com.blankj.utilcode.util.ToastUtils
import com.kongzue.dialog.v3.TipDialog
import com.lyb.purchasesystem.R
import com.lyb.purchasesystem.bean.ImageBean
import com.lysoft.baseproject.activity.BaseUIActivity
import com.lysoft.baseproject.adapter.CommonGalleryImgAdapter
import com.lysoft.baseproject.imp.AdapterViewClickListener
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.engine.impl.GlideEngine
import com.zhihu.matisse.internal.entity.CaptureStrategy
import kotlinx.android.synthetic.main.activity_submit_complete_info.*
import kotlinx.android.synthetic.main.activity_submit_complete_info.view.*
import java.util.*
import kotlin.collections.HashMap


/**
 * ASPurchaseSystem
 * 类描述：发布随手拍
 * 类传参：
 * @Author： create by Lyb on 2020-04-24 14:58
 */

class SubmitCompleteInfoActivity : AdapterView.OnItemClickListener, AdapterViewClickListener, View.OnClickListener, BaseUIActivity() {
    lateinit var commonGalleryImgAdapter: CommonGalleryImgAdapter
    var galleryImageList = mutableListOf<ImageBean>()
    val maxNumPhoto = 3
    val REQUEST_SELECT_IMAGES_CODE = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        topViewManager().titleTextView().text = "提交处理意见"
        val view = View.inflate(pageContext, R.layout.activity_submit_complete_info, null)
        containerView().addView(view)
        containerView().tv_submit_deal_info.setOnClickListener(this)
        initImageSelect()
    }

    private fun initImageSelect() {
        galleryImageList.add(ImageBean("add", "add", "add"))
        commonGalleryImgAdapter = CommonGalleryImgAdapter(pageContext, galleryImageList)
        containerView().gv_add_deal_img.adapter = commonGalleryImgAdapter
        containerView().gv_add_deal_img.setOnItemClickListener(this)
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            tv_submit_deal_info.id -> {
                //提交
                submitInfo()
            }
        }
    }


    private fun submitInfo() {
        val dealContent = containerView().et_deal_content.text.toString()
        if (TextUtils.isEmpty(dealContent)) {
            TipDialog.show(this, "请输入处理意见", TipDialog.TYPE.WARNING)
            return
        }
        val param = HashMap<String, String>()
//        val userInfo = UserInfoUtils.getUserInfo(pageContext)
//        param.put("deaprtment", userInfo.departments);
//        param.put("suggestContent", "suggestContent");
//        param.put("suggestName", "suggestContent");
//        param.put("userId", userInfo.token);
        if (galleryImageList.size < 2) {
            ToastUtils.showShort("请选择图片")
            return
        }
        TipDialog.show(this, "提交成功", TipDialog.TYPE.SUCCESS);
//        val jsonCallBack: JsonCallBack<String> = object : JsonCallBack<String>() {
//            override fun onSuccess(code: Int, msg: String, response: String) {
//                if (code == 200) {
//                    //添加成功
//
//                } else {
//                    //添加失败
//                    TipDialog.show(this@AddClapAtWillActivity, msg, TipDialog.TYPE.ERROR)
//                }
//            }
//
//            override fun onFailure(tag: Any, e: Exception) {
//                TipDialog.show(this@AddClapAtWillActivity, e.message, TipDialog.TYPE.ERROR)
//            }
//        }
//        OkGo.post<String>(Api.LOGIN).tag(this).upRequestBody(RequestBodyUtils.getRequestBody(param)).execute(jsonCallBack)
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        //当点击添加图标时，去获取图片，position为0，添加图标始终在最后一个位置
        if (position == galleryImageList.size - 1 && "add".equals(galleryImageList[galleryImageList.size - 1].ThumImage)) {
//            var mImageList = mutableListOf<String>()
//            for (imageBean in galleryImageList) {
//                if (!imageBean.ThumImage.equals("add")) {
//                    Log.i("Lyb", "添加图片===" + imageBean.ThumImage);
//                    mImageList.add(imageBean.ThumImage)
//                }
//            }
            Matisse.from(this)
                    .choose(MimeType.ofImage())
                    .capture(true)
                    .captureStrategy(CaptureStrategy(true, "ZHIHUphotoPicker"))
                    .countable(true)
                    .maxSelectable(maxNumPhoto + 1 - galleryImageList.size)

                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                    .thumbnailScale(0.85f)
                    .imageEngine(GlideEngine())
                    .theme(R.style.zhihu_select)
                    .showSingleMediaType(true)
                    .originalEnable(false)
                    .forResult(REQUEST_SELECT_IMAGES_CODE)

//            com.lcw.library.imagepicker.ImagePicker.getInstance()
//                    .setTitle("选择随手拍照片")//设置标题
//                    .showCamera(true)//设置是否显示拍照按钮
//                    .showImage(true)//设置是否展示图片
//                    .showVideo(false)//设置是否展示视频
//                    .setSingleType(true)//设置图片视频不能同时选择
//                    .setMaxCount(3)//设置最大选择图片数目(默认为1，单选)
//                    .setImagePaths(mImageList as ArrayList<String>?)//保存上一次选择图片的状态，如果不需要可以忽略
//                    .setImageLoader(GlideLoader(pageContext))//设置自定义图片加载器
//                    .start(this, REQUEST_SELECT_IMAGES_CODE);//REQEST_SELECT_IMAGES_CODE为Intent调用的
        } else {

        }
    }

    override fun adapterViewClick(position: Int, view: View?) {
        delete(position)
    }

    /**
     * 删除图片
     *
     * @param position
     */
    private fun delete(position: Int) {
        //根据位置删除
        galleryImageList.removeAt(position)
        //图片小于张时，显示出添加的图标
        if (!"add".equals(galleryImageList[galleryImageList.size - 1].ThumImage)) {
            val model = ImageBean("add", "add", "add")
            galleryImageList.add(model)
        }
        commonGalleryImgAdapter.notifyDataSetChanged()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_SELECT_IMAGES_CODE) {
            val mImageList = Matisse.obtainPathResult(data) as ArrayList<String>


//            galleryImageList.clear()
//            galleryImageList.add(ImageBean("add", "add", "add"))
//            var mImageList = data!!.getStringArrayListExtra(EXTRA_SELECT_IMAGES)
            for (path in mImageList) {
                val model = ImageBean(path, path, path)
                galleryImageList.add(galleryImageList.size - 1, model)
            }
            Log.i("Lyb", "mImageList===" + mImageList.size);
//            Log.i("Lyb","galleryImageList==="+galleryImageList.size);

            //mList达到最大数量时，移除add图标
            if (galleryImageList.size - 1 == maxNumPhoto) {
                galleryImageList.removeAt(galleryImageList.size - 1)
            }
            commonGalleryImgAdapter.notifyDataSetChanged()
        }
    }
}