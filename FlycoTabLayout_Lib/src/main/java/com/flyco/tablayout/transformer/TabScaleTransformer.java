package com.flyco.tablayout.transformer;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.flyco.tablayout.SlidingScaleTabLayout;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * tab切换
 */
public class TabScaleTransformer implements ViewPager.PageTransformer {

    private SlidingScaleTabLayout slidingScaleTabLayout;

    private PagerAdapter pagerAdapter;

    private float textSelectSize;

    private float textUnSelectSize;

    private List<IViewPagerTransformer> transformers = null;

    public TabScaleTransformer(SlidingScaleTabLayout slidingScaleTabLayout, PagerAdapter pagerAdapter,
                               float textSelectSize, float textUnSelectSize) {
        this.slidingScaleTabLayout = slidingScaleTabLayout;
        this.pagerAdapter = pagerAdapter;
        this.textSelectSize = textSelectSize;
        this.textUnSelectSize = textUnSelectSize;
    }

    private Map<String, View> map = new LinkedHashMap<>();


    public int getPositionFromString(String text) {
        int count = pagerAdapter.getCount();
        for (int index = 0; index < count; index++) {
            if (TextUtils.equals(text, pagerAdapter.getPageTitle(index))) {
                return index;
            }
        }
        return 0;
    }

    public int getPositionFromView(View view) {
        for (Map.Entry<String, View> entry : map.entrySet()) {
            String key = entry.getKey();
            View value = entry.getValue();
            if (value == view) {
                return getPositionFromString(key);
            }
        }
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            if (viewGroup.getChildCount() > 0) {
                View childView = viewGroup.getChildAt(0);
                if (childView instanceof TextView) {
                    TextView textView = (TextView) childView;
                    String text = textView.getText().toString();
                    map.put(text, view);
                    return getPositionFromString(text);
                }
            }
        }
        return 0;
    }



    @Override
    public void transformPage(@NonNull View view, final float position) {
        int viewOfPosition = getPositionFromView(view);
        Log.e("TEST", "position == " + position + ", viewOfPosition == " + viewOfPosition);
        final TextView currentTab = slidingScaleTabLayout.getTitleView(viewOfPosition);
        if (currentTab == null) {
            return;
        }
        // 必须要在View调用post更新样式，否则可能无效
        currentTab.post(new Runnable() {
            @Override
            public void run() {
                if (position >= -1 && position <= 1) { // [-1,1]
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
}
