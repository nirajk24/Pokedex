package com.example.pokedex.utility;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

import androidx.annotation.Nullable;

import com.example.pokedex.R;

public class LoadingSpinnerWhite extends androidx.appcompat.widget.AppCompatImageView {

    private static final int ROTATE_ANIMATION_DURATION = 6000;
    private static final int IMAGE_RESOURCE_ID = R.drawable.pokeball;

    public LoadingSpinnerWhite(Context context) {
        super(context, null);
    }

    public LoadingSpinnerWhite(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LoadingSpinnerWhite(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        // Set and scale the image
        setImageResource(IMAGE_RESOURCE_ID);

        // Start the animation
        startAnimation();


    }

    /**
     * Starts the rotate animation.
     */
    private void startAnimation() {
        clearAnimation();

        RotateAnimation rotate = new RotateAnimation(
                0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );
        rotate.setDuration(ROTATE_ANIMATION_DURATION);
        rotate.setRepeatCount(Animation.INFINITE);
        startAnimation(rotate);
    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);

        if (visibility == View.VISIBLE) {
            startAnimation();
        } else {
            clearAnimation();
        }
    }
}
