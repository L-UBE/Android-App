package com.example.l3d_cube.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.view.GestureDetectorCompat;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;

import com.example.l3d_cube.databinding.FragmentGestureBinding;
import com.example.l3d_cube.gesture.GestureUtils;

public class GestureFragment extends Fragment{

    private FragmentGestureBinding binding;
    private Context context;

    private GestureDetectorCompat motionDetector;

    @SuppressLint("ClickableViewAccessibility")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentGestureBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        context = getActivity();

        motionDetector = new GestureDetectorCompat(context, new GestureListener());

        binding.gesturePad.setOnTouchListener((view, motionEvent) -> {
            motionDetector.onTouchEvent(motionEvent);
            return true;
        });

        return root;
    }

    class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY) {
            if(GestureUtils.isAboveVThreshold(velocityX, velocityY)) {
                //send
            }
            return super.onFling(event1, event2, velocityX, velocityY);
        }

        @Override
        public boolean onScroll(MotionEvent event1, MotionEvent event2,
                                float distanceX, float distanceY) {
            if(GestureUtils.shouldSendScroll()){
                //send
            }
            return super.onScroll(event1, event2, distanceX, distanceY);
        }

        @Override
        public void onLongPress(MotionEvent event){
            //send
        }

        @Override
        public boolean onDoubleTap(MotionEvent event) {
            //send
            return super.onDoubleTap(event);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
}