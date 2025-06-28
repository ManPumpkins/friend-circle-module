package com.g.friendcirclemodule.activity;

import android.os.Bundle;
import android.view.View;
import androidx.media3.common.MediaItem;
import androidx.media3.exoplayer.ExoPlayer;
import com.bumptech.glide.Glide;
import com.g.friendcirclemodule.databinding.ActivityFullScreenBinding;
import com.g.friendcirclemodule.model.FullScreenActivityModel;
import com.g.mediaselector.model.ResourceItem;

public class FullScreenActivity extends BaseActivity<ActivityFullScreenBinding, FullScreenActivityModel> {

    private ExoPlayer player;
    @Override
    protected void initView() {
        viewbinding.fullBtnClose.setOnClickListener(v -> {finish();});
        Bundle receivedBundle = getIntent().getExtras();
        String path = null;
        if (receivedBundle != null) {
            path = receivedBundle.getString("PATH");
        }
        int type = 0;
        if (receivedBundle != null) {
            type = receivedBundle.getInt("TYPE");
        }
        viewbinding.fullVideo.setVisibility(View.GONE);
        viewbinding.fullImage.setVisibility(View.VISIBLE);
        if (type == ResourceItem.TYPE_IMAGE) {
            Glide.with(viewbinding.getRoot()).load(path).into(viewbinding.fullImage);
        } else {
            viewbinding.fullVideo.setVisibility(View.VISIBLE);
            viewbinding.fullImage.setVisibility(View.GONE);
            player = new ExoPlayer.Builder(viewbinding.getRoot().getContext()).build();
            viewbinding.fullVideo.setPlayer(player);
            MediaItem mediaItem = MediaItem.fromUri(path);
            player.setMediaItem(mediaItem);
            player.prepare();
            player.play();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.release();
        }
    }
}