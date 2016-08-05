package com.abdulrahman.memenator.viewholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.abdulrahman.memenator.R;
import com.abdulrahman.memenator.models.Meme;
import com.bumptech.glide.Glide;

/**
 * Created by Lenovo Series on 8/5/2016.
 */
public class MemeViewHolder extends RecyclerView.ViewHolder {

    public TextView tv_meme_name;
    public ImageView iv_meme_img;
    public Button btn_customize;
    public TextView tv_number_used;

    public MemeViewHolder(View itemView) {
        super(itemView);

        tv_meme_name = (TextView) itemView.findViewById(R.id.tv_meme_name);
        iv_meme_img = (ImageView) itemView.findViewById(R.id.iv_meme_img);
        btn_customize = (Button) itemView.findViewById(R.id.btn_customize);
        tv_number_used = (TextView) itemView.findViewById(R.id.tv_numberused);
    }

    public void bindToMeme(Context context, Meme model, View.OnClickListener customizeClickListener){
        tv_meme_name.setText(model.memeName);
        Glide.with(context)
                .load(model.memeUrl)
                .into(iv_meme_img);
        tv_number_used.setText(model.numberUsed+"");
        btn_customize.setOnClickListener(customizeClickListener);
    }


}
