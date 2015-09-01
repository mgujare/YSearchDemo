package com.yahoo.search.demo.keyboard;

import android.content.Intent;
import android.inputmethodservice.InputMethodService;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.yahoo.mobile.client.share.search.ui.activity.SearchLayoutParams;
import com.yahoo.mobile.client.share.search.ui.activity.SearchToLinkActivity;
import com.yahoo.search.demo.R;
import com.yahoo.search.demo.view.CustomSearchFragment;

/**
 * Created by vlaguna on 6/9/15.
 */
public class YInputIME extends InputMethodService {
    private static int REQUEST_CODE_SEARCH_TO_SHARE = 100;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public View onCreateInputView() {
        View parent =  getLayoutInflater().inflate(R.layout.input, null);
        Button b = (Button) parent.findViewById(R.id.buttonSearch);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchToLinkActivity.IntentBuilder builder = new SearchToLinkActivity.IntentBuilder();
                builder.addWebVertical();
                builder.addImageVertical();
                builder.addVertical(CustomSearchFragment.class.getName(), new Bundle());
                builder.setCustomHeader(R.layout.custom_header);

                SearchLayoutParams params = new SearchLayoutParams();
                params.globalTopMargin = 200;
                // Adding margin to the SearchActivity
                builder.setSearchLayoutParams(params);

                Intent intent = builder.buildIntent(getApplicationContext());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                YInputIME.this.startActivity(intent);
            }
        });
        return parent;
    }
    @Override
    public View onCreateCandidatesView() {
        return null;
    }
}
