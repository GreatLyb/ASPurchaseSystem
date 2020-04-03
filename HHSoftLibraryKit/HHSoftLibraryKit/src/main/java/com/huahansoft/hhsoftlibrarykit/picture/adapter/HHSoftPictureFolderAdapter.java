package com.huahansoft.hhsoftlibrarykit.picture.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.huahansoft.hhsoftlibrarykit.R;
import com.huahansoft.hhsoftlibrarykit.picture.entity.LocalMedia;
import com.huahansoft.hhsoftlibrarykit.picture.entity.LocalMediaFolder;
import com.huahansoft.hhsoftlibrarykit.utils.HHSoftImageUtils;

import java.util.ArrayList;
import java.util.List;

public class HHSoftPictureFolderAdapter extends BaseAdapter {

    private List<LocalMediaFolder> folders;
    private Context mContext;

    public HHSoftPictureFolderAdapter(Context mContext) {
        super();
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return folders.size();
    }

    @Override
    public Object getItem(int position) {
        return folders.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.hhsoft_picture_item_floder_list, null);
            holder.mThumbImageView = convertView.findViewById(R.id.hhsoft_iv_picture_media_thumb);
            holder.mTypeImageView = convertView.findViewById(R.id.hhsoft_iv_picture_media_type);
            holder.mNameTextView = convertView.findViewById(R.id.hhsoft_tv_picture_media_floder_name);
            holder.mCountTextView = convertView.findViewById(R.id.hhsoft_tv_picture_media_type_count);
            holder.mSelectImageView = convertView.findViewById(R.id.hhsoft_iv_picture_meida_type_select);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        LocalMediaFolder folder = folders.get(position);
        HHSoftImageUtils.loadImage(mContext,R.drawable.hhsoft_picture_floder_item_bg,folder.getFirstImagePath(),holder.mThumbImageView);
        holder.mNameTextView.setText(folder.getName());
        if (position == 0) {
            holder.mTypeImageView.setVisibility(View.GONE);
            holder.mCountTextView.setVisibility(View.GONE);
        } else {
//            if (AlbumMediaCreator.AlbumMediaType.VIDEO==model.getMediaType()){
//                holder.mTypeImageView.setVisibility(View.VISIBLE);
//            }else {
//                holder.mTypeImageView.setVisibility(View.GONE);
//            }
            holder.mCountTextView.setVisibility(View.VISIBLE);
            holder.mCountTextView.setText(String.format(mContext.getString(R.string.hhsoft_picture_photo_count), folder.getImageNum()));
        }
        boolean isChecked = folder.isChecked();
        if (isChecked) {
            holder.mSelectImageView.setVisibility(View.VISIBLE);
        } else {
            holder.mSelectImageView.setVisibility(View.GONE);
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener!=null){
                    for (LocalMediaFolder mediaFolder : folders) {
                        mediaFolder.setChecked(false);
                    }
                    folder.setChecked(true);
                    notifyDataSetChanged();
                    onItemClickListener.onItemClick(folder.getName(), folder.getImages());
                }
            }
        });
        return convertView;
    }

    private class ViewHolder {
        ImageView mThumbImageView;
        ImageView mTypeImageView;
        TextView mNameTextView;
        TextView mCountTextView;
        ImageView mSelectImageView;
    }
    public void bindFolder(List<LocalMediaFolder> folders) {
//        adapter.setMimeType(mimeType);
        bindFolderData(folders);
    }
    public void bindFolderData(List<LocalMediaFolder> folders) {
        this.folders = folders;
        if (this.folders==null){
            this.folders=new ArrayList<>();
        }
        this.notifyDataSetChanged();
    }
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(String folderName, List<LocalMedia> images);
    }
}
