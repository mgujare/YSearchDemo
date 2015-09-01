package com.yahoo.search.demo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.yahoo.mobile.client.share.search.interfaces.ISearchController;
import com.yahoo.mobile.client.share.search.interfaces.ISearchViewHolder;
import com.yahoo.search.demo.R;

/**
 * CustomSearchViewHolder Implementation
 * Created by vlaguna on 2/6/15.
 */

public class CustomSearchViewHolder extends LinearLayout implements ISearchViewHolder {
    public CustomSearchViewHolder(Context context) {
        super(context);
    }

    public CustomSearchViewHolder(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomSearchViewHolder(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setSearchController(ISearchController searchController) {

    }

    @Override
    public EditText getSearchEditText() {
        return (EditText) findViewById(R.id.search_bar_edit_text);
    }

    @Override
    public View getVoiceSearchButton() {
        return null;
    }

    @Override
    public View getClearTextButton() {
        return findViewById(R.id.search_bar_clear_icon);
    }

    @Override
    public int getSearchViewHeightOffset() {
        return 0;
    }

    @Override
    public void onVoiceSearchAvailabilityChanged(boolean isVoiceSearchAvailable) {

    }

    @Override
    public void onFocusChanged(EditText editText, boolean b) {

    }

    @Override
    public void onTabChanged(int i) {

    }
}
