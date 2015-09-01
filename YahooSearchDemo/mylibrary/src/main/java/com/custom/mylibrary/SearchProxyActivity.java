package com.custom.mylibrary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.yahoo.mobile.client.share.search.ui.activity.SearchActivity;
import com.yahoo.mobile.client.share.search.ui.activity.SearchLayoutParams;

public class SearchProxyActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_proxy);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search_proxy, menu);
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
            SearchActivity.IntentBuilder builder = new SearchActivity.IntentBuilder();
            builder.showAppSuggestions(true);

            // Adding margin to the SearchActivity
            SearchLayoutParams params = new SearchLayoutParams();
            params.globalTopMargin = 200;
            builder.setSearchLayoutParams(params);

            Intent intent = builder.buildIntent(SearchProxyActivity.this);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
