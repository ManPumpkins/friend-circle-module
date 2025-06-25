package com.g.friendcirclemodule;
import android.graphics.Color;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.g.friendcirclemodule.adapter.DMEntryAdapter;
import com.g.friendcirclemodule.databinding.ActivityMainBinding;
import com.g.friendcirclemodule.dp.DMEntryBase;
import com.g.friendcirclemodule.model.MainActivityModel;
import com.g.mediaselector.MyUIProvider;
import com.g.mediaselector.PhotoLibrary;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity<ActivityMainBinding, MainActivityModel> {

    List<DMEntryBase> mData = new ArrayList<>();
    DMEntryBase dmEntryBase = new DMEntryBase(R.string.user_name,R.mipmap.tx,"太惨了，但是没关系，否极泰来，肯定是我有好事要发生了！", null,1, null);
    DMEntryAdapter adapter;

    int toolbarType = 1;

    int offsetY = 0;

    @Override
    protected void initView() {

        viewbinding.mainBtnBack.setOnClickListener(v -> { finish();});
        viewbinding.mainBtnCamera.setOnClickListener(v -> {
            new PhotoLibrary.Builder(this)
                    .setMode(PhotoLibrary.MODE_ALL)
                    .setMultiSelect(true)
                    .setUIProvider(new MyUIProvider())
                    .setSelectListener(selectedList -> {
                        Log.i("data_1", selectedList.toString());
                    })
                    .open();
        });

        List<DMEntryBase> list = new ArrayList<>();
        for (int i = 0; i < 18; i++) {
            list.add(dmEntryBase);
        }
        mData.clear();
        mData.addAll(list);
        adapter = new DMEntryAdapter(mData);
        viewbinding.mainRecycler.setLayoutManager(new LinearLayoutManager(this));
        viewbinding.mainRecycler.setAdapter(adapter);
        viewbinding.mainRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() { // 监听方法
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                offsetY = offsetY + dy;
                int maxScroll = 600;
                int alpha = 0;
                if (offsetY >= maxScroll) {
                    if (toolbarType == 1) {
                        viewbinding.mainBtnBack.setImageResource(R.mipmap.arrow_back_black);
                        viewbinding.mainBtnCamera.setImageResource(R.mipmap.photo_camera_black);
                        viewbinding.mainTitle.setText(getString(R.string.app_name));
                        toolbarType = 2;
                    }
                    alpha = (int)((offsetY - maxScroll) / (float)maxScroll * 255);
                } else {
                    if (toolbarType == 2) {
                        viewbinding.mainBtnBack.setImageResource(R.mipmap.arrow_back_white);
                        viewbinding.mainBtnCamera.setImageResource(R.mipmap.photo_camera_white);
                        viewbinding.mainTitle.setText("");
                        toolbarType = 1;
                    }
                }
                alpha = Math.min(alpha, 255);
                viewbinding.mainTitle.setTextColor(Color.argb(alpha, 0, 0, 0));
                viewbinding.mainToolbar.setBackgroundColor(Color.argb(alpha, 125, 125, 125));
            }
        });
    }
}