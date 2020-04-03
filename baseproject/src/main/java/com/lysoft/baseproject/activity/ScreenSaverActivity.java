package com.lysoft.baseproject.activity;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnPageChangeListener;
import com.lysoft.baseproject.R;
import com.lysoft.baseproject.constant.SpConstants;
import com.lysoft.baseproject.glide.GlideImageUtils;
import com.lysoft.baseproject.manager.ActivityStackManager;
import com.lysoft.baseproject.utils.SPUtils;
import com.lysoft.baseproject.view.MyJzvdStd;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

/**
 * LargeScreenShop
 * 类描述：
 * 类传参：
 * Creat by Lyb on 2019/10/25 10:23
 */
public class ScreenSaverActivity extends BaseActivity {

    private ConvenientBanner convenientBanner;
    private MyJzvdStd myJzvdStd;

    @Override
    public View initView() {
        SPUtils.getInstance().put(SpConstants.CONSUMER_ID, "");
        View view = View.inflate(this, R.layout.activity_screen_save, null);
        return view;
    }

    private ArrayList<String> screenSaverResource = new ArrayList<>();

    @Override
    public void initValues() {
        convenientBanner = findViewById(R.id.convenientBanner);
        ActivityStackManager.getActivityStackManager().popAllActivityWithOutCurrent();
        screenSaverResource.add("http://attach.bbs.miui.com/forum/201603/28/211036f0kktflfoaaftfdk.jpg");
        screenSaverResource.add("http://img.mp.itc.cn/upload/20170311/beb8b7c88b1d4ff9aeb2a483261903ef_th.jpeg");
        screenSaverResource.add("http://a.hiphotos.baidu.com/zhidao/pic/item/b21bb051f8198618de1df9144bed2e738ad4e6cc.jpg");
        screenSaverResource.add("http://img0.imgtn.bdimg.com/it/u=1472307295,1378141733&fm=26&gp=0.jpg");
        screenSaverResource.add("http://5b0988e595225.cdn.sohucs.com/images/20170930/acb1d638ae424bba9ad0c4d2140c6f38.jpeg");
        screenSaverResource.add("http://img0.imgtn.bdimg.com/it/u=1047582934,3039079448&fm=26&gp=0.jpg");
        initConvenientBanner();
        //        initVideoPlayer();
    }

    private void initVideoPlayer() {
        myJzvdStd = findViewById(R.id.videoplayer);
        myJzvdStd.setUp("http://vd3.bdstatic.com/mda-jinpn2ghm53htxvb/sc/mda-jinpn2ghm53htxvb.mp4"
                , "", JzvdStd.SYSTEM_UI_FLAG_FULLSCREEN);
        GlideImageUtils.getInstance().loadImage(getPageContext(), R.drawable.screen_default, "http://jzvd-pic.nathen.cn/jzvd-pic/1bb2ebbe-140d-4e2e-abd2-9e7e564f71ac.png", myJzvdStd.thumbImageView);

        myJzvdStd.dissmissControlView();
        myJzvdStd.setVideoPlayCompleteListener(new MyJzvdStd.OnVideoPlayCompleteListener() {
            @Override
            public void onVideoPlayComplete() {
                //播放完成
                Log.i("Lyb", "播放完成");
            }
        });
        myJzvdStd.setScreenFullscreen();
        myJzvdStd.progressBar.setVisibility(View.GONE);
        myJzvdStd.bottomProgressBar.setVisibility(View.GONE);
        myJzvdStd.startVideo();
    }

    private void initConvenientBanner() {
        convenientBanner.setPages(
                new CBViewHolderCreator() {
                    @Override
                    public NetworkImageHolderView createHolder(View itemView) {
                        return new NetworkImageHolderView(itemView);
                    }

                    @Override
                    public int getLayoutId() {
                        return R.layout.item_banner;
                    }
                }, screenSaverResource)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                //                .setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused})
                //设置指示器的方向
                //                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT)
                .setOnPageChangeListener(new OnPageChangeListener() {
                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    }

                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                    }

                    @Override
                    public void onPageSelected(int index) {
                        //                        Log.i("Lyb", "index=" + index);

                    }
                });
    }

    //A、网络图片
    public class NetworkImageHolderView extends Holder<String> {
        private ImageView mImageView;

        private NetworkImageHolderView(View itemView) {
            super(itemView);
        }

        @Override
        protected void initView(View itemView) {
            //找到对应展示图片的imageview
            mImageView = itemView.findViewById(R.id.iv_banner1);
            //设置图片加载模式为铺满，具体请搜索 ImageView.ScaleType.FIT_XY
//            mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        }

        @Override
        public void updateUI(String data) {
            GlideImageUtils.getInstance().loadImage(getPageContext(), R.drawable.screen_default, data, mImageView);
        }
    }

    //如果你需要考虑更好的体验，可以这么操作
    @Override
    protected void onStart() {
        super.onStart();
        convenientBanner.startTurning();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Jzvd.releaseAllVideos();
        convenientBanner.stopTurning();
    }

    @Override
    protected void onDestroy() {
        convenientBanner.stopTurning();
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
        convenientBanner.stopTurning();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            ARouter.getInstance().build("/app/HomeActivity").navigation();
        }
        return true;
    }
}
