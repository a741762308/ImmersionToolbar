package com.jsqix.immersiontoolbar;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

/**
 * Created by dq on 2016/2/1.
 */
public class BaseActivity extends AppCompatActivity {
    private ToolBarHelper mToolBarHelper;
    public Toolbar toolbar;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        mToolBarHelper = new ToolBarHelper(this, layoutResID);
        toolbar = mToolBarHelper.getToolBar();
        setContentView(mToolBarHelper.getContentView());
         /*沉浸式状态栏*/
        StatusBarCompat.compat(this);
        /*把 toolbar 设置到Activity 中*/
        setSupportActionBar(toolbar);
        /*自定义的一些操作*/
        onCreateCustomToolBar(toolbar);

    }

    public void onCreateCustomToolBar(Toolbar toolbar) {
        //默认Toolbar布局 左边返回 中间Title 右边自行设置
        toolbar.setContentInsetsRelative(0, 0);
        toolbar.setNavigationIcon(R.mipmap.icon_back);
        toolbar.showOverflowMenu();
        getLayoutInflater().inflate(R.layout.toobar_custom, toolbar);
    }

    public void setTitle(String str) {
        TextView title = (TextView) toolbar.findViewById(R.id.title_text);
        title.setText(str);
    }

    public void setRightText(String str) {
        TextView right = (TextView) toolbar.findViewById(R.id.right_btn);
        right.setText(str);
    }

    public void setRightDrawble(int sid) {
        TextView right = (TextView) toolbar.findViewById(R.id.right_btn);
        right.setBackgroundResource(sid);
    }

    public void setRightClick(View.OnClickListener ocl) {
        TextView right = (TextView) toolbar.findViewById(R.id.right_btn);
        right.setOnClickListener(ocl);
    }

    /**
     * 返回事件
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
