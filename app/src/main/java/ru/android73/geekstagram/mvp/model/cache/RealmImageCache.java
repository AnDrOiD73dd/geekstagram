package ru.android73.geekstagram.mvp.model.cache;

import android.graphics.Bitmap;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import ru.android73.geekstagram.GeekstagramApp;
import ru.android73.geekstagram.mvp.model.entity.ImageListItem;
import ru.android73.geekstagram.mvp.model.entity.CachedImage;
import ru.android73.geekstagram.mvp.model.entity.CachedImageList;
import ru.android73.geekstagram.mvp.model.entity.RealmImageListItem;


public class RealmImageCache implements ImageCache {

    private static final String IMAGE_FOLDER_NAME = "cache";
    private static final String JPEG_SUFFIX = "jpg";
    private static final String PNG_SUFFIX = "png";
    private static final String DOT_JPEG_SUFFIX = "." + JPEG_SUFFIX;
    private static final String DOT_PNG_SUFFIX = "." + PNG_SUFFIX;

    @Override
    public void putImageList(List<ImageListItem> imageListItems) {
        RealmList<RealmImageListItem> realmImageListItems = new RealmList<>();
        for (ImageListItem imageListItem: imageListItems) {
            RealmImageListItem realmImageListItem = RealmImageListItem.map(imageListItem);
            realmImageListItems.add(realmImageListItem);
        }
        Realm.getDefaultInstance().executeTransactionAsync(realm -> {
            CachedImageList cachedImageList = new CachedImageList();
            cachedImageList.setImageListItems(realmImageListItems);
            realm.copyToRealm(cachedImageList);
        });
    }

    @Override
    public List<ImageListItem> getImageList() {
        CachedImageList cachedImageList = Realm.getDefaultInstance()
                .where(CachedImageList.class).findFirst();
        RealmList<RealmImageListItem> realmImageListItems = cachedImageList.getImageListItems();
        List<ImageListItem> imageListItems = new ArrayList<>();
        for (RealmImageListItem realmImageListItem : realmImageListItems) {
            imageListItems.add(realmImageListItem.map());
        }
        return imageListItems;
    }

    @Override
    public void putImage(String imagePath, Bitmap bitmap) {
        if (!getImageDir().exists() && !getImageDir().mkdirs()) {
            throw new RuntimeException("Failed to create directory: " + getImageDir().toString());
        }

        final String fileFormat = imagePath.contains(DOT_JPEG_SUFFIX) ? DOT_JPEG_SUFFIX : DOT_PNG_SUFFIX;
        final File imageFile = new File(getImageDir(), SHA1(imagePath) + fileFormat);
        FileOutputStream fos;

        try {
            fos = new FileOutputStream(imageFile);
            bitmap.compress(fileFormat.equals(JPEG_SUFFIX) ? Bitmap.CompressFormat.JPEG : Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        } catch (Exception e) {
            return;
        }

        Realm.getDefaultInstance().executeTransactionAsync(realm -> {
            CachedImage cachedImage = new CachedImage();
            cachedImage.setUrl(imagePath);
            cachedImage.setPath(imageFile.getAbsolutePath());
            realm.copyToRealm(cachedImage);
        });
    }

    @Override
    public String getImage(String imagePath) {
        CachedImage cachedImage = Realm.getDefaultInstance().where(CachedImage.class).equalTo("url", imagePath).findFirst();
        if (cachedImage == null) {
            return null;
        }
        return cachedImage.getPath();
    }


    public static File getImageDir() {
        return new File(GeekstagramApp.getInstance().getExternalFilesDir(null) + "/" + IMAGE_FOLDER_NAME);
    }

    public static String SHA1(String s) {
        MessageDigest m = null;

        try {
            m = MessageDigest.getInstance("SHA1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        m.update(s.getBytes(), 0, s.length());
        String hash = new BigInteger(1, m.digest()).toString(16);
        return hash;
    }
}
