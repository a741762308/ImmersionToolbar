# ImmersionToolbar
====
沉浸式状态栏，现在一般来讲是透明状态栏，从android4.4开始android支持设置状态栏的颜色，从android5.0开始有了AppCompat主题，支持toolbar，colorPrimary，colorPrimaryDark等的设置，本例是在基于android版本在4.4以上的沉浸式。
#效果图

#主要代码
##沉浸式
```java
public class StatusBarCompat {
    private static final int INVALID_VAL = -1;
    private static final int COLOR_DEFAULT = Color.parseColor("#FF303F9F");

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void compat(Activity activity, int statusColor) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0+
                if (statusColor != INVALID_VAL) {
                    activity.getWindow().setStatusBarColor(statusColor);
                }
            } else {//4.4
                设置状态栏透明
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                int color = COLOR_DEFAULT;
                ViewGroup contentView = (ViewGroup) activity.findViewById(android.R.id.content);
                if (statusColor != INVALID_VAL) {
                    color = statusColor;
                }
                View statusBarView = contentView.getChildAt(0);
                //改变颜色时避免重复添加statusBarView
                if (statusBarView != null && statusBarView.getMeasuredHeight() == getStatusBarHeight(activity)) {
                    statusBarView.setBackgroundColor(color);
                    return;
                }
                statusBarView = new View(activity);
                ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        getStatusBarHeight(activity));
                statusBarView.setBackgroundColor(color);
                contentView.addView(statusBarView, lp);
            }
        }

    }

    public static void compat(Activity activity) {
        compat(activity, INVALID_VAL);
    }

    //获取状态栏高度
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
```
###toolbar
```java
public class ToolBarHelper {

    /*上下文，创建view的时候需要用到*/
    private Context mContext;

    /*base view*/
    private FrameLayout mContentView;

    /*用户定义的view*/
    private View mUserView;

    /*toolbar*/
    private Toolbar mToolBar;

    /*视图构造器*/
    private LayoutInflater mInflater;

    /*
    * 两个属性
    * 1、toolbar是否悬浮在窗口之上
    * 2、toolbar的高度获取
    * */
    private static int[] ATTRS = {
            R.attr.windowActionBarOverlay,
            R.attr.actionBarSize
    };

    public ToolBarHelper(Context context, int layoutId) {
        this.mContext = context;
        mInflater = LayoutInflater.from(mContext);
        /*初始化整个内容*/
        initContentView();
        /*初始化用户定义的布局*/
        initUserView(layoutId);
        /*初始化toolbar*/
        initToolBar();
    }

    private void initContentView() {
        /*直接创建一个帧布局，作为视图容器的父容器*/
        mContentView = new FrameLayout(mContext);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        mContentView.setLayoutParams(params);
        mContentView.setFitsSystemWindows(true);//必须添加，不然沉浸式没效果
    }

    private void initToolBar() {
        /*通过inflater获取toolbar的布局文件*/
        View toolbar = mInflater.inflate(R.layout.tool_bar, mContentView);
        mToolBar = (Toolbar) toolbar.findViewById(R.id.toolbar);
    }

    private void initUserView(int id) {
        mUserView = mInflater.inflate(id, null);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        TypedArray typedArray = mContext.getTheme().obtainStyledAttributes(ATTRS);
        /*获取主题中定义的悬浮标志*/
        boolean overly = typedArray.getBoolean(0, true);
        /*获取主题中定义的toolbar的高度*/
        int toolBarSize = (int) typedArray.getDimension(1, (int) mContext.getResources().getDimension(R.dimen.abc_action_bar_default_height_material));
        typedArray.recycle();
        /*如果是悬浮状态，则不需要设置间距*/
        params.topMargin = overly ? 0 : toolBarSize;
        mContentView.addView(mUserView, params);

    }

    public FrameLayout getContentView() {
        return mContentView;
    }

    public Toolbar getToolBar() {
        return mToolBar;
    }
}
```
#使用
```java
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
        getLayoutInflater().inflate(R.layout.toobar_custom, toolbar);//自定义的布局
 }
```
#License
    Copyright 2015 a741762308

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.


