package com.coursera.gfil.dailyselfie;

import android.app.AlarmManager;
import android.app.ListActivity;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SelfieListMainActivity extends ListActivity {

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private static final String TAG = "DailySelfie";
    private static final String PHOTO_CATALOG = "DailyPhotos";
    private static final Long TWO_MINUTES = 2 * 60 * 1000L;
    private SelfieImageListAdapter mListAdapter;
    private File mPhotoStorageDir;
    private File mPhotoFile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent notificationRecieverIntent = new Intent(SelfieListMainActivity.this, AlarmNotificationReceiver.class);
        PendingIntent notificationRecieverPendingIntent = PendingIntent.getBroadcast(SelfieListMainActivity.this, 0, notificationRecieverIntent, 0);
        AlarmManager mAlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        mAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), TWO_MINUTES, notificationRecieverPendingIntent);

        mPhotoStorageDir = getApplicationContext().getExternalFilesDir(PHOTO_CATALOG);
        mListAdapter = new SelfieImageListAdapter(getApplicationContext());

        getListView().setAdapter(mListAdapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        SelfieImageItem selectedSelfie = (SelfieImageItem) mListAdapter.getItem(position);

        Intent intent = new Intent(SelfieListMainActivity.this, SelfieFullSizeActivity.class);
        intent.putExtra("SelfieImageName", selectedSelfie.getImageName());
        intent.putExtra("SelfieImageUri", selectedSelfie.getImageUri());
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mListAdapter.getCount() != 0) {
            return;
        }

        File[] photos = mPhotoStorageDir.listFiles();
        for (int index = 0; index < photos.length; ++index) {
            addPhotoToList(photos[index]);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_camera) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                try {
                    mPhotoFile = createImageFile();
                } catch (Exception ex) {
                    Log.e(TAG, ex.toString());
                }

                if (mPhotoFile != null) {
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mPhotoFile));
                    startActivityForResult(takePictureIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
                }
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Photo saved", Toast.LENGTH_LONG).show();
                addPhotoToList(mPhotoFile);
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Photo cancelled", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Photo failed", Toast.LENGTH_LONG).show();
            }
        }
    }


    private File createImageFile() throws IOException {
        boolean success = true;
        if (!mPhotoStorageDir.exists()) {
            success = mPhotoStorageDir.mkdir();
        }
        if (success) {
            String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
            String imageFileName = "Selfie_" + timeStamp + ".jpg";

            File mediaFile = new File(mPhotoStorageDir.getPath() + File.separator + imageFileName);

            Log.i(TAG, "Created new file - " + mediaFile);
            return mediaFile;
        } else {
            throw new IOException("Cannot create photos directory");
        }
    }


    private void addPhotoToList(File photo) {
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        Bitmap thumbnailBitmap = BitmapFactory.decodeFile(photo.getAbsolutePath(), bmOptions);
        if (thumbnailBitmap != null) {
            thumbnailBitmap = Bitmap.createScaledBitmap(thumbnailBitmap, 75, 75, false);
        }
        String fileName = photo.getName();

        SelfieImageItem imageItem = new SelfieImageItem(fileName, thumbnailBitmap, Uri.fromFile(photo));
        mListAdapter.add(imageItem);
    }
}


