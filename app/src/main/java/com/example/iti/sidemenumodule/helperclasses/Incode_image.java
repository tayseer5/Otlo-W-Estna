package com.example.iti.sidemenumodule.helperclasses;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;

/**
 * Created by ITI on 6/11/2016.
 */
public class Incode_image extends AsyncTask<Uri,Void,String> {
    Activity context;
    IncodeImageInterface incodeImageInterface;
    public Incode_image(Activity context, IncodeImageInterface incodeImageInterface) {
        this.incodeImageInterface = incodeImageInterface;
        this.context=context;
    }

    @Override
    protected String doInBackground(Uri... uris) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.managedQuery(uris[0], projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String mys=cursor.getString(column_index);

        Bitmap bitmap = BitmapFactory.decodeFile(mys);
        Log.e("tag3", bitmap + "");
        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
        byte[] array=stream.toByteArray();
        String incoded_image = Base64.encodeToString(array, 0);
        return incoded_image;
    }

    @Override
    protected void onPostExecute(String incodedImage) {
        Log.e("decode image","after get image incode "+incodedImage);
        incodeImageInterface.afterDecode(incodedImage);
    }
}
