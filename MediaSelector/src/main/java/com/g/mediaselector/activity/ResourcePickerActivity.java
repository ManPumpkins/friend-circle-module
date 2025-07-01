package com.g.mediaselector.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import com.g.mediaselector.adapter.ResourceAdapter;
import com.g.mediaselector.interface_method.OnResourceSelectListener;
import com.g.mediaselector.interface_method.ResourceUIProvider;
import com.g.mediaselector.model.ResourceItem;
import com.g.mediaselector.utils.MediaStoreUtils;
import com.g.mediaselector.utils.PermissionUtils;
import com.g.mediaselector.databinding.ActivityResourcePickerBinding;
import com.g.mediaselector.databinding.ToolbarBinding;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ResourcePickerActivity extends Activity {

    public static OnResourceSelectListener staticListener;
    public static ResourceUIProvider staticUIProvider;
    public static int staticMode = 1;
    public static boolean staticMulti = false;
    public static String topBarName = "";
    private final List<ResourceItem> selected = new ArrayList<>();

    ActivityResourcePickerBinding arpb;
    ToolbarBinding tb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        arpb = ActivityResourcePickerBinding.inflate(getLayoutInflater());
        setContentView(arpb.getRoot());

        arpb.recyclerView.setLayoutManager(new GridLayoutManager(this, 4));

        tb = ToolbarBinding.inflate(getLayoutInflater());
        tb.topBarName.setText(Objects.equals(topBarName, "") ? "资源库" : topBarName);
        arpb.toolbarContainer.setVisibility(View.VISIBLE);
        arpb.toolbarContainer.addView(tb.getRoot());
        arpb.bottomBar.setVisibility(staticMulti? View.VISIBLE : View.GONE);
        tb.btnCancel.setOnClickListener(v -> finish());
        if (!PermissionUtils.hasStoragePermission(this)) {
            PermissionUtils.requestStoragePermission(this);
            return;
        }
        loadData();
    }

    private void loadData() {
        List<ResourceItem> dataList = MediaStoreUtils.getMedia(this, staticMode);
        ResourceAdapter adapter = new ResourceAdapter(dataList, staticUIProvider, selected);
        arpb.recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        //点击事件
        adapter.setOnItemClickListener((view, position) -> {
            ResourceItem item = dataList.get(position);
            if (staticMulti) {
                if (selected.contains(item)) selected.remove(item);
                else selected.add(item);
                adapter.notifyItemChanged(position);
            } else {
                selected.clear();
                selected.add(item);
                finishWithResult();
            }
        });

        // 完成按钮
        arpb.btnDone.setOnClickListener(v -> finishWithResult());
    }

    private void finishWithResult() {
        if (staticListener != null) {
            staticListener.onSelected(new ArrayList<>(selected));
        }
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PermissionUtils.REQUEST_CODE) {
            loadData();
        }
    }
}