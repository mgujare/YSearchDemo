package com.yahoo.search.demo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import com.custom.mylibrary.SearchProxyActivity;
import com.yahoo.mobile.client.share.search.data.filters.ImageFilter;
import com.yahoo.mobile.client.share.search.ui.activity.SearchActivity;
import com.yahoo.mobile.client.share.search.ui.activity.SearchLayoutParams;
import com.yahoo.mobile.client.share.search.ui.activity.SearchToLinkActivity;
import com.yahoo.mobile.client.share.search.ui.activity.SearchToLinkActivity.ShareActivityError;
import com.yahoo.search.demo.view.CustomSearchFragment;
import com.yahoo.search.demo.view.IActionMenuListener;
import com.yahoo.search.demo.view.SelectableEditText;

import java.io.InputStream;


public class MainActivity extends Activity implements IActionMenuListener {
    private ImageView imageViewSearchShare;
    private CheckBox launchToSuggest;
    private String text = "This is an activity from App";
    private static int REQUEST_CODE_SEARCH_TO_SHARE = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        SelectableEditText content = (SelectableEditText) findViewById(R.id.selectableEditText);
        content.setListener(this);
        content.setText(text);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_search) {

            /*SearchActivity.IntentBuilder builder = new SearchActivity.IntentBuilder();
            builder.setCustomHeader(R.layout.custom_header);
            builder.setCustomFooter(R.layout.custom_footer);
            builder.setCustomSearchAssist(R.layout.custom_assist);
            builder.showAppSuggestions(true);

            // Adding margin to the SearchActivity
            SearchLayoutParams params = new SearchLayoutParams();
            params.globalTopMargin = 200;
            builder.setSearchLayoutParams(params);

            Intent intent = builder.buildIntent(MainActivity.this);*/
            Intent intent = new Intent(this,SearchProxyActivity.class);

            startActivity(intent);
            return true;

        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onSearchMenuItemClicked(SelectableEditText editText, String text) {
        SearchActivity.IntentBuilder builder = new SearchActivity.IntentBuilder();
        builder.setCustomHeader(R.layout.custom_header);
        builder.setCustomFooter(R.layout.custom_footer);
        builder.setQueryString(text);
        builder.showAppSuggestions(true);

        // Adding margin to the SearchActivity
        SearchLayoutParams params = new SearchLayoutParams();
        params.globalTopMargin = 200;
        builder.setSearchLayoutParams(params);

        if (launchToSuggest.isChecked()) {
            builder.launchSuggestions(true);
        }

        Intent intent = builder.buildIntent(MainActivity.this);
        startActivity(intent);
    }
    
    @Override
   protected void onActivityResult(int requestCode, int resultCode, Intent data) {
         if(resultCode == RESULT_CANCELED) {
             if(data != null && data.hasExtra(SearchToLinkActivity.SHARE_ERROR_CODE)) {
                 ShareActivityError errorCode = (ShareActivityError)data.getExtras().get(SearchToLinkActivity.SHARE_ERROR_CODE);
                 String message = data.getStringExtra(SearchToLinkActivity.SHARE_ERROR_MESSAGE);
                 Toast.makeText(getApplicationContext(), "error = " + message+ " "+errorCode, Toast.LENGTH_SHORT).show();
             }
         } else if (requestCode == REQUEST_CODE_SEARCH_TO_SHARE) {
             if (resultCode == RESULT_OK) {
                 Bundle bundle = data.getBundleExtra(SearchToLinkActivity.SHARE_BUNDLE);
                 int type = bundle.getInt(SearchToLinkActivity.SHARED_OBJECT_TYPE);
                 switch (type) {
                     case SearchToLinkActivity.IMAGES:
                         String photoUrl = bundle.getString(SearchToLinkActivity.FULL_URL);
                         new DownloadImageTask(imageViewSearchShare).execute(photoUrl);
                         String shortUrl = bundle.getString(SearchToLinkActivity.SHORT_URL);
                         Toast.makeText(getApplicationContext(), "photoUrl = " + photoUrl +"\nshortUrl="+shortUrl, Toast.LENGTH_SHORT).show();
                         break;
                     case SearchToLinkActivity.CUSTOM:
                         Bundle custom = bundle.getBundle(SearchToLinkActivity.CONTENT);
                         Toast.makeText(getApplicationContext(),"custom content returned:"+custom, Toast.LENGTH_SHORT).show();
                     case SearchToLinkActivity.WEB:
                         String url = bundle.getString(SearchToLinkActivity.SHORT_URL);
                         if (url == null) {
                             url = bundle.getString(SearchToLinkActivity.SOURCE_URL);
                         }
                         String title = bundle.getString(SearchToLinkActivity.TITLE);
                         Toast.makeText(getApplicationContext(),"link content returned:\n url:"+url+"\ntitle:"+title,
                                 Toast.LENGTH_SHORT).show();
                 }

             }
         }
       super.onActivityResult(requestCode, resultCode, data);
   }
    
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
