package com.g.friendcirclemodule.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.g.friendcirclemodule.databinding.CeRibItemBinding;
import com.g.mediaselector.model.ResourceItem;

import java.util.List;

public class MainImageGridAdapter extends BaseAdapter<ResourceItem> {

    private MainImageGridAdapter.OnItemClickListener onItemClickListener;

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
    };

    @Override
    public int getItemCount() {
        return this.mData.size();
    }
}
