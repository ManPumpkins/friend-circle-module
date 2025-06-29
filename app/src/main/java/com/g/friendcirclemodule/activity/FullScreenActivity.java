package com.g.friendcirclemodule.activity;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.media3.common.MediaItem;
import androidx.media3.common.PlaybackParameters;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;

import com.bumptech.glide.Glide;
import com.g.friendcirclemodule.R;
import com.g.friendcirclemodule.databinding.ActivityFullScreenBinding;
import com.g.friendcirclemodule.model.FullScreenActivityModel;
import com.g.mediaselector.model.ResourceItem;

public class FullScreenActivity extends BaseActivity<ActivityFullScreenBinding, FullScreenActivityModel> {

    private ExoPlayer player;
    private Handler longPressHandler;
    private boolean isLongPress = false;
    private Runnable longPressRunnable;

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

            viewbinding.fullVideo.setUseController(false);
            viewbinding.fullVideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("DDDDD", String.valueOf(player.isPlaying()));
                    if (isLongPress) {
                        isLongPress = false;
                        return;
                    }
                    ObjectAnimator.ofFloat(viewbinding.fullBtnPlayPause, "alpha", 0f, 1f)
                            .setDuration(500)
                            .start();
                    if (player.isPlaying()) {
                        viewbinding.fullBtnPlayPause.setImageResource(R.mipmap.play_circle);
                        player.stop();
                    } else {
                        viewbinding.fullBtnPlayPause.setImageResource(R.mipmap.pause_circle);
                        ObjectAnimator.ofFloat(viewbinding.fullBtnPlayPause, "alpha", 1f, 0f)
                                .setDuration(1000)
                                .start();
                        player.prepare();
                        player.play();
                    }
                }
            });

            longPressHandler = new Handler(Looper.getMainLooper());
            viewbinding.fullVideo.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            // 启动长按
                            longPressRunnable = () -> {
                                isLongPress = true;
                                setPlaybackSpeed(3.5f);
                                Toast.makeText(FullScreenActivity.this, "加速播放", Toast.LENGTH_SHORT).show();
                            };
                            longPressHandler.postDelayed(longPressRunnable, 500); // 500ms 长按时间
                            break;

                        case MotionEvent.ACTION_CANCEL:
                        case MotionEvent.ACTION_UP:
                            // 移除长按检测
                            longPressHandler.removeCallbacks(longPressRunnable);
                            if (isLongPress) {
                                setPlaybackSpeed(1.0f);
                            }
                            break;
                    }
                    return false;
                }
            });


        }
    }
    // 动态设置播放速度
    private void setPlaybackSpeed(float speed) {
        if (player != null) {
            PlaybackParameters parameters = new PlaybackParameters(speed);
            player.setPlaybackParameters(parameters);
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