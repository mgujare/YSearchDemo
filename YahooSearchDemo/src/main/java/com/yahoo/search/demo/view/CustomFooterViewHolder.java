package com.yahoo.search.demo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yahoo.mobile.client.share.search.interfaces.IFooterViewHolder;
import com.yahoo.mobile.client.share.search.interfaces.ISearchVertical;
import com.yahoo.mobile.client.share.search.interfaces.ITabController;
import com.yahoo.search.demo.R;
import com.yahoo.search.demo.view.Utils.Utils;

import java.util.List;


public class CustomFooterViewHolder extends LinearLayout implements IFooterViewHolder, View.OnClickListener {

    public static final float EPS = 0.0001f;
    private ITabController mTabController;
    private List<ISearchVertical> mTabList;
    private int mTabWidth = 0;
    private int currentTab;
    private View mTabIndicator;

    public CustomFooterViewHolder(Context context) {
        super(context);
    }

    public CustomFooterViewHolder(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomFooterViewHolder(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private void createTabBar(List<ISearchVertical> tabs) {
        mTabList = tabs;
        LayoutInflater inflater =  (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout labelContainer = (LinearLayout) findViewById(R.id.search_tab_label_container);
        labelContainer.removeAllViews();
        int index = 0;
        for (ISearchVertical vertical : mTabList) {
            TextView tv = (TextView) inflater.inflate(R.layout.demo_search_tab, null);
            tv.setText(vertical.getLabel(getContext()));
            LayoutParams lp = new LayoutParams(0, LayoutParams.MATCH_PARENT);
            lp.weight = 1;
            tv.setOnClickListener(this);
            tv.setTag(index++);
            labelContainer.addView(tv, lp);
        }

        mTabWidth = Utils.getScreenWidth(getContext()) / labelContainer.getChildCount();

        mTabIndicator = findViewById(R.id.search_tab_indicator);
        android.view.ViewGroup.LayoutParams lp = mTabIndicator.getLayoutParams();
        lp.width = mTabWidth;
        mTabIndicator.setLayoutParams(lp);

        // If there is only one tab, don't show the tab bar.
        // We're changing the height to 0 instead of setting visibility, because the
        // visibility of the container is often changed in core sdk.
        if (mTabList.size() == 1) {
            setTabContainerHeight(0);
        }
    }

    private void updateTabTextColor(int position, int color, boolean selected) {
        LinearLayout labelContainer = (LinearLayout) findViewById(R.id.search_tab_label_container);
        TextView tv = (TextView) labelContainer.getChildAt(position);
        if (tv != null) {
            tv.setTextColor(color);
            if (selected) {
                //tv.setTypeface(null, Typeface.BOLD);
                tv.setBackgroundColor(getResources().getColor(R.color.demo_search_tab_selected_bg));
            } else {
                //tv.setTypeface(null, Typeface.NORMAL);
                tv.setBackgroundColor(getResources().getColor(R.color.demo_search_tab_unselected_bg));
            }
        }

    }

    private void updateIndicatorPosition(float position) {
        mTabIndicator.setTranslationX(position * mTabWidth);
    }

    private void setSelectedIndex(int position) {
        if(mTabList != null) {
            for (int i = 0; i < mTabList.size(); i++) {
                if (i == position) {
                    updateTabTextColor(i, getResources().getColor(R.color.demo_search_tab_selected), true);
                } else {
                    updateTabTextColor(i, getResources().getColor(R.color.demo_search_tab_standard), false);
                }
            }

            //updateIndicatorPosition(position);
            currentTab = position;
        }
    }

    @Override
    public void onClick(View v) {
        if(v instanceof  TextView) {
            int index = (int)v.getTag();
            if(index > -1) {
                mTabController.changeTab(index);
            }
        }
    }

    private void setTabContainerHeight(int height) {
        ViewGroup.LayoutParams params = getLayoutParams();
        params.height = height;
        setLayoutParams(params);
    }

    /** ITabViewHolder methods */

    @Override
    public void setTabController(ITabController tabController) {
        mTabController = tabController;
    }

    @Override
    public void setTabs(List<ISearchVertical> verticals) {
        createTabBar(verticals);
    }

    @Override
    public void addTab(ISearchVertical vertical) {
        mTabList.add(vertical);
        createTabBar(mTabList);
    }

    @Override
    public void onTabChanged(float position) {
        int intValue = (int) position;
        //if it is almost an integer value
        if(Math.abs(position - intValue) < EPS) {
            setSelectedIndex(intValue);
        }
        else {
            //updateIndicatorPosition(position);
        }
    }

}
