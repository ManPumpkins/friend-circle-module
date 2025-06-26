package com.g.mediaselector;

import android.view.View;

import com.bumptech.glide.Glide;
import com.g.mediaselector.activity.ResourcePickerActivity;
import com.g.mediaselector.databinding.ItemRibBinding;
import com.g.mediaselector.databinding.ToolbarBinding;
import com.g.mediaselector.interface_method.ResourceUIProvider;
import com.g.mediaselector.model.ResourceItem;

public class MyUIProvider implements ResourceUIProvider {

    @Override
    public void bindItemView(ItemRibBinding binding, ResourceItem item, boolean selected) {

        Glide.with(binding.getRoot()).load(item.path).into(binding.ivThumb); // Glide加载
        binding.checkView.setSelected(selected);
        if (item.type == ResourceItem.TYPE_VIDEO) {
            binding.tvDuration.setText(formatDuration(item.duration));
            binding.tvDuration.setVisibility(View.VISIBLE);
        }
    }

    private String formatDuration(long durationMs) {
        long seconds = durationMs / 1000;
        long min = seconds / 60;
        long sec = seconds % 60;
        return String.format("%02d:%02d", min, sec);
    }

}
