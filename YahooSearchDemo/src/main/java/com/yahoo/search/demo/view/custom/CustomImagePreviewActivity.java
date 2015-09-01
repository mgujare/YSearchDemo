package com.yahoo.search.demo.view.custom;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yahoo.mobile.client.share.search.data.PhotoData;
import com.yahoo.mobile.client.share.search.ui.activity.ImageGalleryActivity;
import com.yahoo.search.demo.R;

import java.util.ArrayList;

public class CustomImagePreviewActivity extends Activity {
    public static final String CURRENT_INDEX = "start_position";
    public static final String LIST = "image_urls";
    private ArrayList<PhotoData> mImageList;
    private int mCurrentIndex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_web_preview);

        restoreFromIntent();
        PhotoData image = mImageList.get(mCurrentIndex);
        String text = "You should load this Image=\n" + image.getTitle() +" \nurl:"+image.getOrigPhotoUrl();
        TextView tv = (TextView) findViewById(R.id.text_view_custom);
        tv.setText(text);
        Button bt = (Button) findViewById(R.id.button_return);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnImageData();
            }
        });
    }

    private void restoreFromIntent() {
        mImageList = getIntent().getParcelableArrayListExtra(LIST);
        mCurrentIndex = getIntent().getIntExtra(CURRENT_INDEX, 0);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_custom_web_preview, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void returnImageData() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra(ImageGalleryActivity.PHOTO_DATA, mImageList.get(mCurrentIndex));
        returnIntent.putExtra(ImageGalleryActivity.CURRENT_POS, mCurrentIndex);
        setResult(RESULT_OK, returnIntent);
        finish();
    }
}
