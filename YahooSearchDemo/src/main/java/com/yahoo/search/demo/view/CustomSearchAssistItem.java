package com.yahoo.search.demo.view;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yahoo.mobile.client.share.search.data.SearchAssistData;
import com.yahoo.mobile.client.share.search.interfaces.ISearchAssistController;
import com.yahoo.mobile.client.share.search.interfaces.ISearchAssistData;
import com.yahoo.mobile.client.share.search.interfaces.ISearchAssistItemHolder;
import com.yahoo.search.demo.R;

/**
 * Created by vlaguna on 8/19/15.
 */
public class CustomSearchAssistItem extends RelativeLayout implements ISearchAssistItemHolder {

    private ISearchAssistData mData;
    private ISearchAssistController mSearchAssistController;

    public CustomSearchAssistItem(Context context) {
        super(context);
    }

    public CustomSearchAssistItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomSearchAssistItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setData(ISearchAssistData itemData) {
        mData = itemData;
        createView();
    }

    private void createView() {
        TextView text = (TextView) findViewById(R.id.text);
        String label = mData.getText();
        text.setText(label);
        text.setTag(mData);
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSearchAssistController != null) {
                    mSearchAssistController.onSearchAssistItemClick(mData);
                }
            }
        });

        TextView subText = (TextView) findViewById(R.id.subtext);


        ImageView iconView = (ImageView) findViewById(R.id.icon);
        if (mData != null & mData.getIcon() != null) {
            iconView.setImageDrawable(mData.getIcon());
            switch (mData.getType()) {
                case ISearchAssistData.SEARCH_APPS:
                    subText.setText("this is an app");
                    break;
                case ISearchAssistData.SEARCH_CONTACTS:
                    subText.setText("This is a contact");
                    break;
            }
        } else {
            Resources res = getContext().getResources();
            switch (mData.getType()) {
                case ISearchAssistData.SEARCH_TRENDING:
                    iconView.setImageDrawable(res.getDrawable(android.R.drawable.ic_menu_mapmode));
                    subText.setText("trending");
                    break;
                case ISearchAssistData.SEARCH_SUGGEST:
                    iconView.setImageDrawable(res.getDrawable(android.R.drawable.ic_menu_view));
                    subText.setText("suggestion");
                    break;
                case ISearchAssistData.LOCAL_HISTORY:
                case SearchAssistData.SEARCH_HISTORY:
                    iconView.setImageDrawable(res.getDrawable(android.R.drawable.ic_menu_recent_history));
                    subText.setText("history");
                    break;
                case ISearchAssistData.CLEAR_LOCAL_HISTORY:
                case SearchAssistData.CLEAR_HISTORY:
                    iconView.setImageDrawable(res.getDrawable(android.R.drawable.ic_menu_delete));
                    text.setText("clear all!");
                    break;
                case ISearchAssistData.SEARCH_DEFAULT:
                    iconView.setImageDrawable(res.getDrawable(android.R.drawable.ic_media_next));
                    subText.setText("search it!");
                    break;
                case ISearchAssistData.SHOW_ALL_HISTORY:
                    iconView.setImageDrawable(res.getDrawable(android.R.drawable.ic_menu_more));
                    text.setText("show all");
                    break;
            }
        }
    }

    @Override
    public TextView getTextView() {
        return (TextView) findViewById(R.id.text);
    }

    @Override
    public View getIconView() {
        return findViewById(R.id.icon);
    }

    @Override
    public void setSearchController(ISearchAssistController searchAssistController) {
        this.mSearchAssistController = searchAssistController;
    }
}
