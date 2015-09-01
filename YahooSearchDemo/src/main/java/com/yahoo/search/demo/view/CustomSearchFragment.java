package com.yahoo.search.demo.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yahoo.mobile.client.share.search.interfaces.ILinkableContent;
import com.yahoo.mobile.client.share.search.interfaces.IQuery;
import com.yahoo.mobile.client.share.search.interfaces.IQueryManager;
import com.yahoo.mobile.client.share.search.interfaces.ISearchToLinkListener;
import com.yahoo.mobile.client.share.search.ui.activity.SearchLayoutParams;
import com.yahoo.mobile.client.share.search.ui.contentfragment.SearchResultFragment;
import com.yahoo.search.demo.R;

/**
 * Created by vlaguna on 4/3/15.
 */
public class CustomSearchFragment  extends SearchResultFragment implements ILinkableContent{
    private TextView queryTextView;
    private IQueryManager mContentManager;
    private View mSpinnerView;
    private ISearchToLinkListener mListener = null;
    public static final String MY_CUSTOM_KEY = "my_custom_key";
    public static final String MY_CUSTOM_KEY2 = "my_custom_key2";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContentManager = new IQueryManager() {
            @Override
            public void loadQuery(IQuery query) {
                CustomSearchFragment.this.onContentReceived(query);
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup mRootView = (ViewGroup) inflater.inflate(R.layout.custom_search_fragment, container, false);
        mSpinnerView= mRootView.findViewById(R.id.spinner_view);
        queryTextView = (TextView) mRootView.findViewById(R.id.text_view_query);
        queryTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener != null) {
                    Bundle b = getBundleToBeReturned();
                    mListener.onContentSelected(b);
                }
            }
        });
        mSpinnerView.setVisibility(View.GONE);
        mRootView.requestFocus();
        return mRootView;
    }

    private Bundle getBundleToBeReturned() {
        Bundle b = new Bundle();
        b.putString(MY_CUSTOM_KEY,queryTextView.getText().toString());
        b.putInt(MY_CUSTOM_KEY2,queryTextView.getText().length());
        return b;
    }

    public void onContentReceived( IQuery query) {
        mSpinnerView.setVisibility(View.GONE);
        queryTextView.setText(query.getQueryString());
        queryTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void moveBackgroundWithParallax(int positionOffset, boolean forward, Context context) {

    }

    @Override
    public void setLayoutParams(SearchLayoutParams searchLayoutParams) {

    }

    @Override
    public void setBackgroundColor(int color) {

    }

    @Override
    public IQueryManager getContentManager() {
        return mContentManager;
    }

    @Override
    public String getLabel(Context context) {
        if(context != null) {
            return "Custom";
        }
        return null;
    }

    @Override
    public OnScrollListener getOnScrollListener() {
        return null;
    }

    @Override
    public void setOnScrollListener(OnScrollListener onScrollListener) {

    }

    @Override
    public boolean isScrollEnabled() {
        return false;
    }

    @Override
    public int getScrollY() {
        return 0;
    }

    @Override
    public void setSearchToLinkListener(ISearchToLinkListener searchToLinkListener) {
        mListener = searchToLinkListener;
    }
}