package com.yahoo.search.demo.view;
import android.content.Context;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.ActionMode.Callback;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import com.yahoo.search.demo.R;

public class SelectableEditText extends EditText {

    private IActionMenuListener listener;
    
    public SelectableEditText(Context context) {
        super(context);
        init();
    }

    public SelectableEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public SelectableEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    
    

    public IActionMenuListener getListener() {
        return listener;
    }

    public void setListener(IActionMenuListener listener) {
        this.listener = listener;
    }

    private void init() {
        this.setTextIsSelectable(true);
        this.setCustomSelectionActionModeCallback(new Callback() {
            
            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }
            
            @Override
            public void onDestroyActionMode(ActionMode mode) {
                // TODO Auto-generated method stub
                
            }
            
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                // Inflate a menu resource providing context menu items
                MenuInflater inflater = mode.getMenuInflater();
                // Assumes that you have "contexual.xml" menu resources
                inflater.inflate(R.menu.contextual, menu);
                return true;
            }
            
            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                if(listener != null) {
                    int startSelection=getSelectionStart();
                    int endSelection=getSelectionEnd();
                    String selectedText = getText().toString().substring(startSelection, endSelection);
                    listener.onSearchMenuItemClicked(SelectableEditText.this,selectedText);
                }
                return false;
            }
        });
    }
}