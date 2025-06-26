package com.g.friendcirclemodule.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.g.friendcirclemodule.databinding.CeRibItemBinding;
import com.g.friendcirclemodule.utlis.UtilityMethod;
import com.g.mediaselector.model.ResourceItem;

import java.util.List;

public class MainImageGridAdapter extends BaseAdapter<ResourceItem> {

    private OnItemClickListener onItemClickListener;

    public MainImageGridAdapter(List<ResourceItem> mData) {
        this.mData = mData;
    }

    // 主ViewHolder
    static class ViewHolder extends RecyclerView.ViewHolder {
        private final CeRibItemBinding binding;
        ViewHolder(CeRibItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CeRibItemBinding ceribb = CeRibItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MainImageGridAdapter.ViewHolder(ceribb);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MainImageGridAdapter.ViewHolder vh = (MainImageGridAdapter.ViewHolder)holder;
        ResourceItem item = mData.get(position);
        Log.i("type_1122", String.valueOf(item.type));
        vh.binding.playerView.setVisibility(View.GONE);
        vh.binding.ivImage.setVisibility(View.VISIBLE);
        vh.binding.videoTime.setVisibility(View.GONE);
        if (item.type == ResourceItem.TYPE_VIDEO) {
            vh.binding.videoTime.setVisibility(View.VISIBLE);
            vh.binding.videoTime.setText(UtilityMethod.formatDuration(item.duration));

        }
        Glide.with(vh.binding.getRoot()).load(item.path).into(vh.binding.ivImage); // Glide加载
        // 点击事件
        vh.binding.getRoot().setOnClickListener(view -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClickListener(view, position);
            }
        });
    }
    public interface OnItemClickListener {
        void onItemClickListener(View view, int position);
    }

    public void setOnItemClickListener(MainImageGridAdapter.OnItemClickListener listener) {
        this.onItemClickListener = listener;  // 接收外部实现的监听器
    }

    @Override
    public int getItemCount() {
        return this.mData.size();
    }
}
