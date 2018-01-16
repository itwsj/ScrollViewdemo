**自定义ScrollView显示和隐藏header**

# 效果 #

  ![](https://i.imgur.com/mhenIiP.gif)

## 实现MyScrollView ##

	  1 package test2.example.com.scrollviewdemo.view;
	  2 
	  3 import android.content.Context;
	  4 import android.graphics.Canvas;
	  5 import android.util.AttributeSet;
	  6 import android.util.DisplayMetrics;
	  7 import android.util.Log;
	  8 import android.view.MotionEvent;
	  9 import android.view.WindowManager;
	 10 import android.view.animation.DecelerateInterpolator;
	 11 import android.widget.LinearLayout;
	 12 import android.widget.ScrollView;
	 13 import android.widget.Scroller;
	 14 
	 15 
	 16 public class MyScrollView extends ScrollView {
	 17     private ScrollHeader mHeader;
	 18     private float mLastY = -1;
	 19     private Context mContext;
	 20     private int MAX_VISI_HEIGHT;
	 21     private Scroller mScroller;
	 22 
	 23     public MyScrollView(Context context, AttributeSet attrs) {
	 24         super(context, attrs);
	 25         mContext = context;
	 26         MAX_VISI_HEIGHT = getScreenHeight() / 3;
	 27         mScroller = new Scroller(context, new DecelerateInterpolator());
	 28 
	 29     }
	 30 
	 31     @Override
	 32     protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
	 33         super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	 34         //获取Scroller的第一个孩子,就是包裹所有孩子的View
	 35         LinearLayout firstLL = (LinearLayout) getChildAt(0);
	 36         mHeader = (ScrollHeader) firstLL.getChildAt(0);
	 37     }
	 38 
	 39     @Override
	 40     protected void onDraw(Canvas canvas) {
	 41         super.onDraw(canvas);
	 42 
	 43     }
	 44 
	 45     @Override
	 46     public boolean onTouchEvent(MotionEvent ev) {
	 47         if (mLastY == -1) {
	 48             mLastY = ev.getRawY();
	 49         }
	 50         switch (ev.getAction()) {
	 51             case MotionEvent.ACTION_DOWN:
	 52                 mLastY = ev.getRawY();
	 53                 break;
	 54             case MotionEvent.ACTION_MOVE:
	 55                 final float deltaY = ev.getRawY() - mLastY;
	 56                 mLastY = ev.getRawY();
	 57                 if (deltaY > 0) {
	 58                     int height = mHeader.getVisiableHeight();
	 59                     if (MAX_VISI_HEIGHT > height) {
	 60                         //更新header高度
	 61                         mHeader.setVisiableHeight((int) deltaY + height);
	 62                     }
	 63 
	 64                 }
	 65 
	 66                 break;
	 67             case MotionEvent.ACTION_UP:
	 68                 Log.d("MyScrollerView", "MOVE_UP");
	 69                 if (mHeader.getVisiableHeight() > 0) {
	 70                     resetHeaderHeight();
	 71                 }
	 72 
	 73 
	 74                 break;
	 75         }
	 76         return super.onTouchEvent(ev);
	 77     }
	 78 
	 79     /**
	 80      * 获取屏幕的高度
	 81      *
	 82      * @return
	 83      */
	 84     private int getScreenHeight() {
	 85         WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
	 86         DisplayMetrics displayMetrics = new DisplayMetrics();
	 87         windowManager.getDefaultDisplay().getMetrics(displayMetrics);
	 88         return displayMetrics.heightPixels;
	 89     }
	 90 
	 91     private void resetHeaderHeight() {
	 92         int height = mHeader.getVisiableHeight();
	 93         Log.d("MyScrollerView", "移动的高度:" + (0 - height));
	 94         mScroller.startScroll(0, height, 0, 0 - height,
	 95                 400);
	 96         invalidate();
	 97     }
	 98 
	 99     @Override
	100     public void computeScroll() {
	101         if (mScroller.computeScrollOffset()) {
	102             mHeader.setVisiableHeight(mScroller.getCurrY());
	103             postInvalidate();
	104         }
	105         super.computeScroll();
	106     }
	107 }

## ScrollHeader ##

	  1 package test2.example.com.scrollviewdemo.view;
	  2 
	  3 import android.content.Context;
	  4 import android.util.AttributeSet;
	  5 import android.view.Gravity;
	  6 import android.view.LayoutInflater;
	  7 import android.widget.LinearLayout;
	  8 
	  9 import test2.example.com.scrollviewdemo.R;
	 10 
	 11 
	 12 public class ScrollHeader extends LinearLayout {
	 13     private LinearLayout mContainer;
	 14     private Context mContext;
	 15 
	 16     public ScrollHeader(Context context, AttributeSet attrs) {
	 17         super(context, attrs);
	 18         mContext = context;
	 19         initView();
	 20     }
	 21 
	 22     private void initView() {
	 23         // 初始情况
	 24         LayoutParams lp = new LayoutParams(
	 25                 android.view.ViewGroup.LayoutParams.FILL_PARENT, 0);
	 26         mContainer = (LinearLayout) LayoutInflater.from(mContext).inflate(
	 27                 R.layout.scroller_header, null);
	 28 
	 29         addView(mContainer, lp);
	 30         setGravity(Gravity.BOTTOM);
	 31     }
	 32 
	 33 
	 34     /**
	 35      * 设置显示的高度
	 36      *
	 37      * @param height
	 38      */
	 39     public void setVisiableHeight(int height) {
	 40         if (height < 0)
	 41             height = 0;
	 42         LayoutParams lp = (LayoutParams) mContainer
	 43                 .getLayoutParams();
	 44         lp.height = height;
	 45         mContainer.setLayoutParams(lp);
	 46     }
	 47 
	 48     /**
	 49      * 获取显示的高度
	 50      *
	 51      * @return
	 52      */
	 53     public int getVisiableHeight() {
	 54         return mContainer.getHeight();
	 55     }
	 56 }


