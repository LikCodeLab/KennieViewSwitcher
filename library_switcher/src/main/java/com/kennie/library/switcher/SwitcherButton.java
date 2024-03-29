package com.kennie.library.switcher;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.kennie.library.switcher.common.Constant;
import com.kennie.library.switcher.common.Extensions;

import java.util.Set;

public class SwitcherButton extends Switcher{
    private final RectF switcherRect = new RectF(0f, 0f, 0f, 0f);

    public SwitcherButton(Context context) {
        super(context);
    }

    public SwitcherButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SwitcherButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h) {
        if (!Extensions.isLollipopAndAbove()) {
            iconTranslateX = -shadowOffset;
        }

        switcherRect.left = shadowOffset;
        switcherRect.top = shadowOffset / 2;
        switcherRect.right = (float) getWidth() - shadowOffset;
        switcherRect.bottom = (float) getHeight() - shadowOffset - shadowOffset / 2;

        switcherRadius = (getHeight() - shadowOffset * 2) / 2f;

        iconRadius = switcherRadius * 0.6f;
        iconClipRadius = iconRadius / 2.25f;
        iconCollapsedWidth = iconRadius - iconClipRadius;

        iconHeight = iconRadius * 2f;

        iconRect.set(
                getWidth() - switcherRadius - iconCollapsedWidth / 2,
                ((getHeight() - iconHeight) / 2f) - shadowOffset / 2,
                getWidth() - switcherRadius + iconCollapsedWidth / 2,
                (getHeight() - (getHeight() - iconHeight) / 2f) - shadowOffset / 2
        );

        if (!isChecked) {
            iconRect.left = getWidth() - switcherRadius - iconCollapsedWidth / 2;
            iconRect.right = getWidth() - switcherRadius + iconCollapsedWidth / 2;

            iconClipRect.set(
                    getWidth() - iconRect.centerX() - iconClipRadius,
                    iconRect.centerY() - iconClipRadius,
                    getWidth() - iconRect.centerX() + iconClipRadius,
                    iconRect.centerY() + iconClipRadius
            );
            iconTranslateX = -(getWidth() - shadowOffset - switcherRadius * 2);
        }
    }

    @Override
    public void setIconProgress(float iconProgress) {
        if (this.iconProgress != iconProgress) {
            this.iconProgress = iconProgress;
            float iconOffset = Extensions.lerp(0f, iconRadius - iconCollapsedWidth / 2, 1 - iconProgress);
            iconRect.left = getWidth() - switcherRadius - iconCollapsedWidth / 2 - iconOffset;
            iconRect.right = getWidth() - switcherRadius + iconCollapsedWidth / 2 + iconOffset;

            float clipOffset = Extensions.lerp(0f, iconClipRadius, 1 - iconProgress);
            iconClipRect.set(
                    iconRect.centerX() - clipOffset,
                    iconRect.centerY() - clipOffset,
                    iconRect.centerX() + clipOffset,
                    iconRect.centerY() + clipOffset
            );
            postInvalidateOnAnimation();
        }
    }

    @Override
    protected void drawRect(Canvas canvas, @NonNull Paint paint) {
        canvas.drawRoundRect(switcherRect, switcherRadius, switcherRadius, paint);
    }

    @Override
    protected void generateAnimates(Set<Animator> animatorSet) {
        super.generateAnimates(animatorSet);

        setOnClickOffset(Constant.ON_CLICK_RADIUS_OFFSET);
        float iconTranslateA = 0f;
        float iconTranslateB = -((float) getWidth() - shadowOffset - switcherRadius * 2f);
        if (!isChecked) {
            iconTranslateA = iconTranslateB;
            iconTranslateB = -shadowOffset;
        }

        ValueAnimator translateAnimator = ValueAnimator.ofFloat(0f, 1f);
        final float ta = iconTranslateA;
        final float tb = iconTranslateB;
        translateAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                iconTranslateX = Extensions.lerp(ta, tb, value);
            }
        });
        translateAnimator.addListener(new SimpleAnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animation) {
                setOnClickOffset(0f);
            }
        });
        translateAnimator.setDuration(Constant.TRANSLATE_ANIMATION_DURATION);

        animatorSet.add(translateAnimator);
    }

    private void setOnClickOffset(float onClickOffset) {
        switcherRect.left = onClickOffset + shadowOffset;
        switcherRect.top = onClickOffset + shadowOffset / 2;
        switcherRect.right = (float) getWidth() - onClickOffset - shadowOffset;
        switcherRect.bottom = (float) getHeight() - onClickOffset - shadowOffset - shadowOffset / 2;
        invalidate();
    }
}
