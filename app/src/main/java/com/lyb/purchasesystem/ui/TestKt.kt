package com.lyb.purchasesystem.ui

import android.content.Intent
import android.graphics.BitmapFactory
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.MediaStore.Images.Media.insertImage
import android.util.Log
import com.lysoft.baseproject.activity.BaseUIActivity
import com.lzy.okgo.OkGo
import com.lzy.okgo.callback.FileCallback
import com.lzy.okgo.model.Progress
import com.lzy.okgo.model.Response
import com.lzy.okgo.request.base.Request
import java.io.File


/**
 * ASPurchaseSystem
 * 类描述：
 * 类传参：
 * @Author： create by Lyb on 2020-04-11 10:55
 */
class TestKt : BaseUIActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        popShotSrceenDialog()
    }
     fun popShotSrceenDialog() {
//         val lj="https://flv2.bn.netease.com/videolib1/1811/26/OqJAZ893T/HD/OqJAZ893T-mobile.mp4";
        val lj = "http://video.aizhiyi.com/video/3/100018/video/pi2ds6dklfggwvbphbihstkekjkw2okuo5tt2pi_water.mp4?spm=5176.8466032.0.dopenurl.33701450aVqFaI&file=pi2ds6dklfggwvbphbihstkekjkw2okuo5tt2pi_water.mp4"
        Log.i("zll", "url==$lj")
        val savePath: String = Environment.getExternalStorageDirectory().getAbsolutePath().toString() + "/000AA"
        val file = File(savePath)
        if (!file.exists()) {
            Log.i("Lyb", "创建文件夹==" + file.mkdir())
        }
        OkGo.get<File>(lj).execute(object : FileCallback(savePath, System.currentTimeMillis().toString() + ".mp4") {
            override fun onStart(request: Request<File, out Request<Any, Request<*, *>>>?) {
                super.onStart(request)
            }
            override fun onError(response: Response<File?>?) {
                super.onError(response)
                //下载失败
                if (response != null) {
                    Log.i("Lyb", "下载失败=="+response.exception.cause)
                }
            }
            override fun downloadProgress(progress: Progress) {
                super.downloadProgress(progress)
                Log.i("Lyb", "下载进度=="+progress.fraction)
            }

            override fun onSuccess(response: Response<File?>?) {
                Log.i("Lyb", "下载完成==")
                val intent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                val uri = Uri.fromFile(response?.body()?.absoluteFile);
                intent.setData(uri);
                sendBroadcast(intent);

            }
        })
    }
}
