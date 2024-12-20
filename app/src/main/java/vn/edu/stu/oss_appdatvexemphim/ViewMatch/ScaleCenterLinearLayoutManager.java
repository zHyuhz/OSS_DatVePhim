package vn.edu.stu.oss_appdatvexemphim.ViewMatch;

import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ScaleCenterLinearLayoutManager extends LinearLayoutManager {
    private float shrinkAmount = 0.15f;  // Tỉ lệ thu nhỏ
    private float shrinkDistance = 0.9f; // Khoảng cách để bắt đầu thu nhỏ

    public ScaleCenterLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    @Override
    public void onLayoutCompleted(RecyclerView.State state) {
        super.onLayoutCompleted(state);
        scaleChildren();
    }

    // Đổi từ private sang public
    public void scaleChildren() {
        float midpoint = getWidth() / 2.0f;
        float d0 = 0.0f;
        float d1 = shrinkDistance * midpoint;
        float s0 = 1.0f;
        float s1 = 1.0f - shrinkAmount;

        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child != null) {
                float childMidpoint = (getDecoratedLeft(child) + getDecoratedRight(child)) / 2.0f;
                float d = Math.min(d1, Math.abs(midpoint - childMidpoint));
                float scale = s0 + (s1 - s0) * (d - d0) / (d1 - d0);
                child.setScaleX(scale);
                child.setScaleY(scale);
            }
        }
    }
}
