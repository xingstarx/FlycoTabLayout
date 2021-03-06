package com.flyco.tablayout.transformer;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.flyco.tablayout.SlidingScaleTabLayout;

import java.util.List;

/**
 * tab切换
 */
public class TabScaleTransformer implements ViewPager.PageTransformer {

    private SlidingScaleTabLayout slidingScaleTabLayout;

    private PagerAdapter pagerAdapter;

    private float textSelectSize;

    private float textUnSelectSize;

    private int mTextSelectColor;

    private int mTextUnselectColor;


    private List<IViewPagerTransformer> transformers = null;

    public TabScaleTransformer(SlidingScaleTabLayout slidingScaleTabLayout, PagerAdapter pagerAdapter,
                               float textSelectSize, float textUnSelectSize) {
        this.slidingScaleTabLayout = slidingScaleTabLayout;
        this.pagerAdapter = pagerAdapter;
        this.textSelectSize = textSelectSize;
        this.textUnSelectSize = textUnSelectSize;
    }

    @Override
    public void transformPage(@NonNull View view, final float position) {
        int viewOfPosition = pagerAdapter.getItemPosition(view);
//        Log.e("TEST", ", position == " + position + ", viewOfPosition == " + viewOfPosition);
        final TextView currentTab = slidingScaleTabLayout.getTitleView(viewOfPosition);

        if (currentTab == null) {
            return;
        }
        // 必须要在View调用post更新样式，否则可能无效
        currentTab.post(new Runnable() {
            @Override
            public void run() {
                if (position >= -1 && position <= 1) { // [-1,1]
                    if (position >= -0.5 && position <= 0.5) {
                        currentTab.setTextColor(mTextSelectColor);
                        currentTab.getPaint().setFakeBoldText(true);
                    } else {
                        currentTab.setTextColor(mTextUnselectColor);
                        currentTab.getPaint().setFakeBoldText(false);
                    }
                    currentTab.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSelectSize - Math.abs((textSelectSize - textUnSelectSize) * position));
                } else {
                    currentTab.setTextSize(TypedValue.COMPLEX_UNIT_PX, textUnSelectSize);
                }
            }
        });
        // 回调设置的页面切换效果设置
        if (transformers != null && transformers.size() > 0) {
            for (IViewPagerTransformer transformer : transformers) {
                transformer.transformPage(view, position);
            }
        }
    }

    public List<IViewPagerTransformer> getTransformers() {
        return transformers;
    }

    public void setTransformers(List<IViewPagerTransformer> transformers) {
        this.transformers = transformers;
    }

    public void setColor(int textSelectColor, int textUnselectColor) {
        this.mTextSelectColor = textSelectColor;
        this.mTextUnselectColor = textUnselectColor;
    }
}
