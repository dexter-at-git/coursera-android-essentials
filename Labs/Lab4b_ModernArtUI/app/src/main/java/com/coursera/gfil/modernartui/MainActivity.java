package com.coursera.gfil.modernartui;

import android.app.DialogFragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;

public class MainActivity extends ActionBarActivity {

    private static final String TAG = "Lab-ModernArtUI";
    private static final String MOMA_URL = "http://www.moma.org";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);

        final LinearLayout leftFirstLayout = (LinearLayout) findViewById(R.id.leftFirst);
        final RgbColors leftFirstColors = new RgbColors(leftFirstLayout.getBackground());

        final LinearLayout leftSecondLayout = (LinearLayout) findViewById(R.id.leftSecond);
        final RgbColors leftSecondColors = new RgbColors(leftSecondLayout.getBackground());

        final LinearLayout rightFirstLayout = (LinearLayout) findViewById(R.id.rightFirst);
        final RgbColors rightFirstColors = new RgbColors(rightFirstLayout.getBackground());

        final LinearLayout rightSecondLayout = (LinearLayout) findViewById(R.id.rightSecond);
        final RgbColors rightSecondColors = new RgbColors(rightSecondLayout.getBackground());

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int leftFirstColor = leftFirstColors.getColor(progress);
                int leftSecondColor = leftSecondColors.getColor(progress);
                int rightFirstColor = rightFirstColors.getColor(progress);
                int rightSecondColor = rightSecondColors.getColor(progress);

                leftFirstLayout.setBackgroundColor(leftFirstColor);
                leftSecondLayout.setBackgroundColor(leftSecondColor);
                rightFirstLayout.setBackgroundColor(rightFirstColor);
                rightSecondLayout.setBackgroundColor(rightSecondColor);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_info) {
            DialogFragment dialog = new ConfirmDialogFragment();
            dialog.show(getFragmentManager(), TAG);
        }

        return super.onOptionsItemSelected(item);
    }


    public static class ConfirmDialogFragment extends DialogFragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.dialog, container, false);
            getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

            Button confirm = (Button) view.findViewById(R.id.dialog_button_confirm);
            Button cancel = (Button) view.findViewById(R.id.dialog_button_cancel);

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });

            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent urlIntent = new Intent(Intent.ACTION_VIEW);
                    urlIntent.setData(Uri.parse(MOMA_URL));
                    startActivity(urlIntent);
                }
            });

            return view;
        }
    }
}
