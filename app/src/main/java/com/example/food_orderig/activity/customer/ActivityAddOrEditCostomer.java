package com.example.food_orderig.activity.customer;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;

import com.example.food_orderig.R;

public class ActivityAddOrEditCostomer extends AppCompatActivity {

    private View background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_or_edit_costomer);

//        background = findViewById(R.id.background);
//
//        if (savedInstanceState == null) {
//            background.setVisibility(View.INVISIBLE);
//
//            final ViewTreeObserver viewTreeObserver = background.getViewTreeObserver();
//
//            if (viewTreeObserver.isAlive()) {
//                viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//
//                    @Override
//                    public void onGlobalLayout() {
//                        circularRevealActivity();
//                        background.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//                    }
//
//                });
//            }
//
//        }
    }
//    private void circularRevealActivity() {
//        int cx = background.getLeft() - getDips(44);
//        int cy = background.getBottom() - getDips(44);
//
//        float finalRadius = Math.max(background.getWidth(), background.getHeight());
//
//        Animator circularReveal = ViewAnimationUtils.createCircularReveal(
//                background,
//                cx,
//                cy,
//                0,
//                finalRadius);
//
//        circularReveal.setDuration(2000);
//        background.setVisibility(View.VISIBLE);
//        circularReveal.start();
//
//    }
//
//    private int getDips(int dps) {
//        Resources resources = getResources();
//        return (int) TypedValue.applyDimension(
//                TypedValue.COMPLEX_UNIT_DIP,
//                dps,
//                resources.getDisplayMetrics());
//    }
//
//    @Override
    public void onBackPressed() {


//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            int cx = background.getLeft() - getDips(44);
//            int cy = background.getBottom() - getDips(44);
//
//            float finalRadius = Math.max(background.getWidth(), background.getHeight());
//            Animator circularReveal = ViewAnimationUtils.createCircularReveal(background, cx, cy, finalRadius, 0);
//
//            circularReveal.addListener(new Animator.AnimatorListener() {
//                @Override
//                public void onAnimationStart(Animator animator) {
//
//                }
//
//                @Override
//                public void onAnimationEnd(Animator animator) {
//                    background.setVisibility(View.INVISIBLE);
//                    finish();
//                }
//
//                @Override
//                public void onAnimationCancel(Animator animator) {
//
//                }
//
//                @Override
//                public void onAnimationRepeat(Animator animator) {
//
//                }
//            });
//            circularReveal.setDuration(2000);
//            circularReveal.start();
//        }
//        else {
//            super.onBackPressed();
//        }
    }
}