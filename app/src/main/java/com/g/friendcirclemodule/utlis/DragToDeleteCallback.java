package com.g.friendcirclemodule.utlis;

import android.content.ClipData;
import android.os.Build;
import android.util.Log;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.g.friendcirclemodule.adapter.ImageGridAdapter;
import com.g.mediaselector.model.ResourceItem;

public class DragToDeleteCallback extends ItemTouchHelper.Callback {

    private final ImageGridAdapter adapter;
    private View deleteArea;

    public DragToDeleteCallback(ImageGridAdapter adapter, View deleteArea) {
        this.adapter = adapter;
        this.deleteArea = deleteArea;
    }


    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        int dragFlags = 0;
        if (viewHolder.getBindingAdapterPosition() != (adapter.getItemCount() - 1)) {
            dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT;
        }
        return makeMovementFlags(dragFlags, 0);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        // 通知适配器交换数据
        adapter.onItemMove(viewHolder.getBindingAdapterPosition(), target.getBindingAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) { // 拖拽删除
//        if (actionState == ItemTouchHelper.ACTION_STATE_DRAG && viewHolder != null) {
//            startDrag(viewHolder);
//        }
    }
    private void startDrag(RecyclerView.ViewHolder holder) {
        if (holder.getBindingAdapterPosition() == (adapter.getItemCount() - 1)) return;
        Log.i("1111111", String.valueOf(holder.getBindingAdapterPosition()));
        ResourceItem item = adapter.getItem(holder.getBindingAdapterPosition());
        ClipData dragData = ClipData.newPlainText("id", String.valueOf(holder.getBindingAdapterPosition()));

        View.DragShadowBuilder shadow = new View.DragShadowBuilder(holder.itemView);

        holder.itemView.startDragAndDrop(dragData, shadow, null,
                View.DRAG_FLAG_GLOBAL);
    }
}
