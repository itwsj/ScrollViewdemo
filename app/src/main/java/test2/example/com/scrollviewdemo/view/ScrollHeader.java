package test2.example.com.scrollviewdemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import test2.example.com.scrollviewdemo.R;


public class ScrollHeader extends LinearLayout {
    private LinearLayout mContainer;
    private Context mContext;

    public ScrollHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    private void initView() {
        // 初始情况
        LayoutParams lp = new LayoutParams(
                android.view.ViewGroup.LayoutParams.FILL_PARENT, 0);
        mContainer = (LinearLayout) LayoutInflater.from(mContext).inflate(
                R.layout.scroller_header, null);

        addView(mContainer, lp);
        setGravity(Gravity.BOTTOM);
    }


    /**
     * 设置显示的高度
     *
     * @param height
     */
    public void setVisiableHeight(int height) {
        if (height < 0)
            height = 0;
        LayoutParams lp = (LayoutParams) mContainer
                .getLayoutParams();
        lp.height = height;
        mContainer.setLayoutParams(lp);
    }

    /**
     * 获取显示的高度
     *
     * @return
     */
    public int getVisiableHeight() {
        return mContainer.getHeight();
    }
}
