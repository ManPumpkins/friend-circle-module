package com.g.friendcirclemodule.activity;

import android.content.ClipData;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import com.g.friendcirclemodule.R;
import com.g.friendcirclemodule.adapter.ImageGridAdapter;
import com.g.friendcirclemodule.databinding.ActivityContentEditingBinding;
import com.g.friendcirclemodule.dp.DMEntryBase;
import com.g.friendcirclemodule.dp.EditDataManager;
import com.g.friendcirclemodule.dp.FeedManager;
import com.g.friendcirclemodule.model.ContentEditingActivityModel;
import com.g.friendcirclemodule.utlis.DragToDeleteCallback;
import com.g.mediaselector.MyUIProvider;
import com.g.mediaselector.PhotoLibrary;
import com.g.mediaselector.model.ResourceItem;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class ContentEditingActivity extends BaseActivity<ActivityContentEditingBinding, ContentEditingActivityModel> {
    ImageGridAdapter adapter;
    List<ResourceItem> list = new ArrayList<>();

    @Override
    protected void initData() {
        list.clear();
        list.addAll(EditDataManager.getList());
    }

    @Override
    protected void initView() {
        viewbinding.ceBtnCancel.setOnClickListener(v -> {finish();});
        viewbinding.ceBtnPublish.setOnClickListener(v -> {
            Date data = new Date();
            long id = data.getTime();
            int name = R.string.user_name;
            int tx = R.mipmap.tx;
            String dec = viewbinding.ceDescribe.getText().toString();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd HH:mm");
            String time = sdf.format(data);
            String imagePath = "";
            String videoPath = "";
            String friendVideoTime = "";
            for (ResourceItem resourceItem : list) {
                if (resourceItem.type == ResourceItem.TYPE_IMAGE) {
                    if(Objects.equals(imagePath, "")) {
                        imagePath = resourceItem.path;
                    } else  {
                        imagePath = imagePath + "," + resourceItem.path;
                    }
                } else {
                    if(Objects.equals(videoPath, "")) {
                        friendVideoTime = String.valueOf(resourceItem.duration);
                        videoPath = resourceItem.path;
                    } else  {
                        friendVideoTime = friendVideoTime + "," + resourceItem.duration;
                        videoPath = videoPath + "," + resourceItem.path;
                    }
                }
            }

            DMEntryBase dmEntryBase = new DMEntryBase(id, name, tx, dec, imagePath, time, videoPath, friendVideoTime);
            FeedManager.insertItemToAccounttb(dmEntryBase);
            finish();
        });

        viewbinding.rvImages.setLayoutManager(new GridLayoutManager(this, 3));
        adapter = new ImageGridAdapter(list);
        viewbinding.rvImages.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        //点击事件
        adapter.setOnItemClickListener((v, pos) -> {
            onItemClickListener(v, pos);
        });
        // 设置 ItemTouchHelper
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new DragToDeleteCallback(adapter, viewbinding.deleteArea));
        itemTouchHelper.attachToRecyclerView(viewbinding.rvImages);

        viewbinding.rvImages.setOnDragListener((v, event) -> {
            if (event.getAction() == DragEvent.ACTION_DROP) {
                moveItemToTarget(event, adapter);
            }
            return true;
        });
    }

    private void moveItemToTarget(DragEvent event, ImageGridAdapter adapter) {

        ClipData.Item clipItem = event.getClipData().getItemAt(0);
        String draggedId = clipItem.getText().toString();
        adapter.removeItem(Integer.parseInt(draggedId));
        Log.i("idddd", draggedId);
    }

    private void onItemClickListener(View view, int position) {

        if (position == (list.size())) {
            new PhotoLibrary.Builder(this)
                    .setMode(PhotoLibrary.MODE_ALL)
                    .setMultiSelect(true)
                    .setUIProvider(new MyUIProvider())
                    .setSelectListener(selectedList -> {
                        Log.i("data_1", selectedList.toString());
                        EditDataManager.setList(selectedList);
                        onResume();
                    })
                    .open();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        list.clear();
        list.addAll(EditDataManager.getList());
        adapter.notifyDataSetChanged();
    }

}