package com.kennie.library.switcher;

import android.view.animation.Interpolator;

public class BounceInterpolator implements Interpolator {

    private final double amplitude;
    private final double frequency;

    BounceInterpolator(double amplitude, double frequency) {
        this.amplitude = amplitude;
        this.frequency = frequency;
    }

    @Override
    public float getInterpolation(float time) {
        return (float) (-1 * Math.pow(Math.E, -time / amplitude) * Math.cos(frequency * time) + 1);
    }
}
