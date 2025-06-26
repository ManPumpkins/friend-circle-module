package com.g.friendcirclemodule.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.g.friendcirclemodule.R;
import com.g.friendcirclemodule.databinding.CeRibItemBinding;
import com.g.mediaselector.model.ResourceItem;

import java.util.List;

public class ImageGridAdapter extends BaseAdapter<ResourceItem> {

    private OnItemClickListener onItemClickListener;

    public ImageGridAdapter(List<ResourceItem> mData) {
        this.mData = mData;
        Log.i("22222", String.valueOf(this.mData));
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
        return new ViewHolder(ceribb);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder vh = (ViewHolder)holder;
        if (position == (this.mData.size())) {
            vh.binding.ivImage.setImageResource(R.mipmap.add);
        } else {
            ResourceItem item = mData.get(position);
            Glide.with(vh.binding.getRoot()).load(item.path).into(vh.binding.ivImage); // Glide加载
        }
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

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;  // 接收外部实现的监听器
    }
    public void onItemMove(int fromPosition, int toPosition) {
        if (toPosition == this.mData.size()) {
            return;
        }
        // 拖动排序时交换数据
        ResourceItem fromImage = this.mData.get(fromPosition);
        this.mData.remove(fromPosition);
        this.mData.add(toPosition, fromImage);
        notifyItemMoved(fromPosition, toPosition);
    }

    public ResourceItem getItem(int position) {
        return mData.get(position);
    }

    public void removeItem(int position) {
        mData.remove(position);
        notifyItemRemoved(position);
    }


}

