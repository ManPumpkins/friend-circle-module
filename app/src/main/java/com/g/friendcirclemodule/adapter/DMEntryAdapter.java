package com.g.friendcirclemodule.adapter;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.g.friendcirclemodule.R;
import com.g.friendcirclemodule.databinding.MainFriendEntryBinding;
import com.g.friendcirclemodule.databinding.MainTopBinding;
import com.g.friendcirclemodule.dp.DMEntryBase;
import com.g.mediaselector.model.ResourceItem;

import java.util.ArrayList;
import java.util.List;

public class DMEntryAdapter extends BaseAdapter<DMEntryBase> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    public DMEntryAdapter(List<DMEntryBase> mData) {
        this.mData = mData;
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? TYPE_HEADER : TYPE_ITEM;
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        private final MainFriendEntryBinding binding;

        public ItemViewHolder(MainFriendEntryBinding mfeb) {
            super(mfeb.getRoot());
            this.binding = mfeb;
        }
    }

    public static class HeaderViewHolder extends RecyclerView.ViewHolder {
        private final MainTopBinding binding;

        public HeaderViewHolder(MainTopBinding mtb) {
            super(mtb.getRoot());
            this.binding = mtb;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            MainTopBinding mtb = MainTopBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new HeaderViewHolder(mtb);
        } else {
            MainFriendEntryBinding mfeb = MainFriendEntryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new ItemViewHolder(mfeb);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (position == 0) {
            HeaderViewHolder hvh = (HeaderViewHolder)holder;
            hvh.binding.mainTopName.setText(hvh.binding.getRoot().getContext().getString(R.string.user_name));
            hvh.binding.mainTopTx.setImageResource(R.mipmap.tx);
        } else {
            ItemViewHolder mfeb = (ItemViewHolder)holder;
            DMEntryBase dmEntryBase = mData.get(position - 1);
            mfeb.binding.friendEntryName.setText(dmEntryBase.getFriendName());
            mfeb.binding.friendEntryHead.setImageResource(dmEntryBase.getFriendHead());
            mfeb.binding.friendEntryDec.setText(dmEntryBase.getDecStr());
            mfeb.binding.friendEntryTime.setText(mfeb.binding.getRoot().getContext().getString(R.string.entry_time, String.valueOf(dmEntryBase.getTime())));

            mfeb.binding.mainRvImages.setLayoutManager(new GridLayoutManager(holder.itemView.getContext(), 3));

            String str = dmEntryBase.getFriendImageId();
            String[] arr = str.split(",");  // 按逗号分割
            List<ResourceItem> list = new ArrayList<>();

            for (String s : arr) {
                ResourceItem a = new ResourceItem(1, s, 1,0);
                list.add(a);
            }
            MainImageGridAdapter adapter = new MainImageGridAdapter(list);
            mfeb.binding.mainRvImages.setAdapter(adapter);
            adapter.notifyDataSetChanged();

        }
    }
}
