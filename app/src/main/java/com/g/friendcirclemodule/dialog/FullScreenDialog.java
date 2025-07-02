package com.g.friendcirclemodule.dialog;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.media3.common.MediaItem;
import androidx.media3.common.PlaybackParameters;
import androidx.media3.exoplayer.ExoPlayer;

import com.bumptech.glide.Glide;
import com.g.friendcirclemodule.R;
import com.g.friendcirclemodule.activity.BaseActivity;
import com.g.friendcirclemodule.databinding.ActivityFullScreenBinding;
import com.g.friendcirclemodule.model.FullScreenActivityModel;
import com.g.mediaselector.model.ResourceItem;

public class FullScreenDialog extends BaseDialog<ActivityFullScreenBinding, FullScreenActivityModel> {

    private ExoPlayer player;
    private Handler longPressHandler;
    private boolean isLongPress = false;
    private Runnable longPressRunnable;
    private Bundle receivedBundle;
    private int initialX = 0;
    private int initialY = 0;

    public FullScreenDialog(@NonNull Context context, Bundle receivedBundle) {
        super(context);
        this.receivedBundle = receivedBundle;

    }

    @Override
    protected void initView() {
        viewbinding.fullBtnClose.setOnClickListener(v -> {cancel();});
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
                            initialX = (int) event.getX();
                            initialY = (int) event.getY();
                            // 启动长按
                            longPressRunnable = () -> {
                                isLongPress = true;
                                setPlaybackSpeed(3.5f);
                                Toast.makeText(getContext(), "加速播放", Toast.LENGTH_SHORT).show();
                            };
                            longPressHandler.postDelayed(longPressRunnable, 500); // 500ms 长按时间
                            break;
                        case MotionEvent.ACTION_MOVE:
                            if ((initialX -(int)event.getX()) > 2 || (initialY -(int)event.getY()) > 2) {
                                longPressHandler.removeCallbacks(longPressRunnable);
                                isLongPress = true;
                            }
                            Log.d("Touch0000", "手指移动: X=" + event.getX() + ", Y=" + event.getY());
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
    public void setDialogSize() {
        super.setDialogSize();
    }

    protected void onDismiss() {
        if (player != null) {
            player.release();
        }
    }
}