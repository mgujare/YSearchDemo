package com.yahoo.search.demo;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import com.yahoo.mobile.client.share.search.data.PhotoData;
import com.yahoo.mobile.client.share.search.interfaces.IBrowser;
import com.yahoo.mobile.client.share.search.interfaces.IImageViewer;
import com.yahoo.mobile.client.share.search.interfaces.ISearchStatusListener;
import com.yahoo.mobile.client.share.search.settings.SearchSDKSettings;
import com.yahoo.mobile.client.share.search.ui.activity.SearchToLinkActivity;
import com.yahoo.mobile.client.share.search.util.SafeSearchEnum;
import com.yahoo.mobile.client.share.yahoosearchlibraryexternalplugin.implementations.Factory;
import com.yahoo.search.demo.view.custom.CustomImagePreviewActivity;
import com.yahoo.search.demo.view.custom.CustomWebPreviewActivity;

import java.util.ArrayList;

public class MyApplication extends Application {

    
    
    @Override
    public void onCreate() {
        super.onCreate();
        initializeSearchSDK();
    }

    private void initializeSearchSDK() {
        ISearchStatusListener callback = new ISearchStatusListener() {
            @Override
            public void onSearchStatusReceived(SearchAppIdStatus status) {
                Log.v("SearchAppIdStatus", status.name());
            }
            @Override
            public Context getContext() {
                return MyApplication.this;
            }
        };
        SearchSDKSettings.initializeSearchSDKSettings(
                new SearchSDKSettings.Builder("mSdkDemo") //Don't forget to provide your appId here
                .setVoiceSearchEnabled(true)
                .setSafeSearch(SafeSearchEnum.OFF)
                .setConsumptionModeEnabled(false)
        );

        Factory factory = (Factory)SearchSDKSettings.getFactory();
        factory.setPreviewBrowser(new IBrowser() {
            @Override
            public IntentFilter getIntentFilter() {
                return null;
            }

            @Override
            public Intent getIntent(Context context, String url, String referral) {
                Intent previewIntent = new Intent(context, CustomWebPreviewActivity.class);
                previewIntent.putExtra(SearchToLinkActivity.SOURCE_URL, url);
                return previewIntent;
            }
        });

        factory.setImagePreviewer(new IImageViewer() {
            @Override
            public Intent getIntent(Context context, String query, ArrayList<PhotoData> images, int position, boolean showCopyrghtMessage) {
                Intent previewIntent = new Intent(context, CustomImagePreviewActivity.class);
                previewIntent.putParcelableArrayListExtra(CustomImagePreviewActivity.LIST, images);
                previewIntent.putExtra(CustomImagePreviewActivity.CURRENT_INDEX, position);
                return previewIntent;
            }
        });
    }
}
