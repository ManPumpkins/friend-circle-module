package com.g.mediaselector.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import com.g.mediaselector.model.ResourceItem;
import java.util.ArrayList;
import java.util.List;

public class MediaStoreUtils {

    public static List<ResourceItem> getMedia(Context ctx, int mode) {
        List<ResourceItem> result = new ArrayList<>();
        ContentResolver cr = ctx.getContentResolver();
        Cursor c = null;

        try {
            if (mode == 1) { // 图片
                c = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        new String[]{MediaStore.Images.Media._ID, MediaStore.Images.Media.DATA},
                        null, null, MediaStore.Images.Media.DATE_ADDED + " DESC");
                if (c != null) {
                    while (c.moveToNext()) {
                        long id = c.getLong(0);
                        String path = c.getString(1);
                        result.add(new ResourceItem(id, path, ResourceItem.TYPE_IMAGE, 0));
                    }
                }
            } else if (mode == 2) { // 视频
                c = cr.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                        new String[]{MediaStore.Video.Media._ID, MediaStore.Video.Media.DATA, MediaStore.Video.Media.DURATION},
                        null, null, MediaStore.Video.Media.DATE_ADDED + " DESC");
                if (c != null) {
                    while (c.moveToNext()) {
                        long id = c.getLong(0);
                        String path = c.getString(1);
                        long duration = c.getLong(2);
                        result.add(new ResourceItem(id, path, ResourceItem.TYPE_VIDEO, duration));
                    }
                }
            } else { // 图片+视频
                c = cr.query(MediaStore.Files.getContentUri("external"),
                        new String[]{
                                MediaStore.Files.FileColumns._ID,
                                MediaStore.Files.FileColumns.DATA,
                                MediaStore.Files.FileColumns.MEDIA_TYPE,
                                MediaStore.Video.Media.DURATION
                        },
                        MediaStore.Files.FileColumns.MEDIA_TYPE + "=" + MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE
                                + " OR " + MediaStore.Files.FileColumns.MEDIA_TYPE + "=" + MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO,
                        null, MediaStore.Files.FileColumns.DATE_ADDED + " DESC");
                if (c != null) {
                    while (c.moveToNext()) {
                        long id = c.getLong(0);
                        String path = c.getString(1);
                        int type = c.getInt(2) == MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE ?
                                ResourceItem.TYPE_IMAGE : ResourceItem.TYPE_VIDEO;
                        long duration = c.isNull(3) ? 0 : c.getLong(3);
                        result.add(new ResourceItem(id, path, type, duration));
                    }
                }
            }
        } finally {
            if (c != null) c.close();
        }
        return result;
    }

    public static String formatDuration(long durationMs) {
        long seconds = durationMs / 1000;
        long min = seconds / 60;
        long sec = seconds % 60;
        return String.format("%02d:%02d", min, sec);
    }
}
