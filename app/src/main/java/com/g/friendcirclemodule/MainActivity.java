package com.g.friendcirclemodule;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.g.friendcirclemodule.adapter.DMEntryAdapter;
import com.g.friendcirclemodule.databinding.ActivityMainBinding;
import com.g.friendcirclemodule.databinding.MainTopBinding;
import com.g.friendcirclemodule.dp.DMEntryBase;
import com.g.friendcirclemodule.model.MainActivityModel;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity<ActivityMainBinding, MainActivityModel> implements View.OnClickListener {

    List<DMEntryBase> mData = new ArrayList<>();
    DMEntryBase dmEntryBase = new DMEntryBase(R.string.user_name,R.mipmap.tx,"太惨了，但是没关系，否极泰来，肯定是我有好事要发生了！", null,1, null);
    DMEntryAdapter adapter;
    MainTopBinding mtb;

    @Override
    protected void initView() {

        viewbinding.mainBtnBack.setOnClickListener(this);
        viewbinding.mainBtnCamera.setOnClickListener(this);

        List<DMEntryBase> list = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
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
                int scrollY = recyclerView.computeVerticalScrollOffset();
                int maxScroll = 600;
                int alpha = (int)(Math.min(scrollY, maxScroll) / (float)maxScroll * 255);
                viewbinding.mainToolbar.setBackgroundColor(Color.argb(alpha, 125, 125, 125));
                Log.i("TTTTTTT", String.valueOf(alpha));
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() ==  R.id.main_btn_back) {
            finish();
        }
        if (v.getId() ==  R.id.main_btn_camera) {
            Toast.makeText(this, "点击了记录", Toast.LENGTH_SHORT).show();
        }
    }
}