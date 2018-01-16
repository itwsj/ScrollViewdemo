package test2.example.com.scrollviewdemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Scroller;


public class MyScrollView extends ScrollView {
    private ScrollHeader mHeader;
    private float mLastY = -1;
    private Context mContext;
    private int MAX_VISI_HEIGHT;
    private Scroller mScroller;

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        MAX_VISI_HEIGHT = getScreenHeight() / 3;
        mScroller = new Scroller(context, new DecelerateInterpolator());

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //获取Scroller的第一个孩子,就是包裹所有孩子的View
        LinearLayout firstLL = (LinearLayout) getChildAt(0);
        mHeader = (ScrollHeader) firstLL.getChildAt(0);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mLastY == -1) {
            mLastY = ev.getRawY();
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastY = ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                final float deltaY = ev.getRawY() - mLastY;
                mLastY = ev.getRawY();
                if (deltaY > 0) {
                    int height = mHeader.getVisiableHeight();
                    if (MAX_VISI_HEIGHT > height) {
                        //更新header高度
                        mHeader.setVisiableHeight((int) deltaY + height);
                    }

                }

                break;
            case MotionEvent.ACTION_UP:
                Log.d("MyScrollerView", "MOVE_UP");
                if (mHeader.getVisiableHeight() > 0) {
                    resetHeaderHeight();
                }


                break;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 获取屏幕的高度
     *
     * @return
     */
    private int getScreenHeight() {
        WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    private void resetHeaderHeight() {
        int height = mHeader.getVisiableHeight();
        Log.d("MyScrollerView", "移动的高度:" + (0 - height));
        mScroller.startScroll(0, height, 0, 0 - height,
                400);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            mHeader.setVisiableHeight(mScroller.getCurrY());
            postInvalidate();
        }
        super.computeScroll();
    }
}
