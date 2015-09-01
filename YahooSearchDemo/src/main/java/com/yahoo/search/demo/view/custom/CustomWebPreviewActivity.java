package com.yahoo.search.demo.view.custom;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yahoo.mobile.client.share.search.ui.activity.SearchToLinkActivity;
import com.yahoo.search.demo.R;

public class CustomWebPreviewActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_web_preview);

        String text = "You should load this url=\n" + getIntent().getStringExtra(SearchToLinkActivity.SOURCE_URL);
        TextView tv = (TextView) findViewById(R.id.text_view_custom);
        tv.setText(text);
        Button bt = (Button) findViewById(R.id.button_return);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnLinkData();
            }
        });
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

    private void returnLinkData() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra(SearchToLinkActivity.SOURCE_URL, getIntent().getStringExtra(SearchToLinkActivity.SOURCE_URL));
        returnIntent.putExtra(SearchToLinkActivity.TITLE, "URL title loaded from webview");
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
}
