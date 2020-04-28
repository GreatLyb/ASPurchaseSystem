package com.lysoft.baseproject.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.blankj.utilcode.util.ScreenUtils;
import com.lysoft.baseproject.R;
import com.lysoft.baseproject.glide.GlideImageUtils;
import com.lysoft.baseproject.imp.AdapterViewClickListener;
import com.lysoft.baseproject.imp.CommonGalleryImageImp;
import com.lysoft.baseproject.utils.DensityUtils;

import java.util.List;

public class CommonGalleryImgAdapter extends BaseAdapter {

    private Context context;
    private List<? extends CommonGalleryImageImp> list;
    private AdapterViewClickListener listener;


    public CommonGalleryImgAdapter(Context context, List<? extends CommonGalleryImageImp> list) {
        this.context = context;
        this.list = list;
        this.listener = (AdapterViewClickListener) context;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        Log.i("Lyb", "list========" + list.size());
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(context, R.layout.common_gallery_item_add, null);
            viewHolder.imageView = convertView.findViewById(R.id.iv_avtivity_add_photo);
            viewHolder.delImageView = convertView.findViewById(R.id.iv_avtivity_delete_photo);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //判断点击的位置，添加图片

        int width = (ScreenUtils.getScreenWidth() - DensityUtils.dip2px(context, 20));
        FrameLayout.LayoutParams fp = new FrameLayout.LayoutParams((width - DensityUtils.dip2px(context, 10)) / 3, (width - DensityUtils.dip2px(context, 10)) / 3);
        viewHolder.imageView.setLayoutParams(fp);
        if ("add".equals(list.get(position).getBig_img())) {
//            viewHolder.imageView.setImageResource(R.drawable.base_gallery_add);
            GlideImageUtils.getInstance().loadDrawableImage(context, R.drawable.base_gallery_add, R.drawable.base_gallery_add, viewHolder.imageView);
            viewHolder.delImageView.setVisibility(View.GONE);
        } else {
            viewHolder.delImageView.setVisibility(View.VISIBLE);
            GlideImageUtils.getInstance().loadImage(context, R.drawable.default_img, list.get(position).getBig_img(), viewHolder.imageView);
//            viewHolder.imageView.setImageResource(R.drawable.default_img_round);
            viewHolder.delImageView.setImageResource(R.drawable.base_delete_gray);
        }

        viewHolder.delImageView.setOnClickListener(new MyClickListenr(position));
        return convertView;
    }

    private class ViewHolder {
        ImageView imageView;
        ImageView delImageView;
    }

    /**
     * 接口回调，在Activity中执行删除方法
     */
    private class MyClickListenr implements View.OnClickListener {
        int posi;

        public MyClickListenr(int posi) {
            this.posi = posi;
        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.iv_avtivity_delete_photo) {//删除
                listener.adapterViewClick(posi, view);
            }
        }
    }
}
