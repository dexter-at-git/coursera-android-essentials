package com.coursera.gfil.dailyselfie;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

public class SelfieFullSizeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selfie_full_size);

        Intent intent = getIntent();
        String title = intent.getStringExtra("SelfieImageName");
        Uri imageUri = intent.getParcelableExtra("SelfieImageUri");

        setTitle(title);

        ImageView imageView = (ImageView) findViewById(R.id.selfieImageView);
        imageView.setImageURI(imageUri);
    }
}
