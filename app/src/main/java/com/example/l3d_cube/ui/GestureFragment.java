package com.example.l3d_cube.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.l3d_cube.MainViewModel;
import com.example.l3d_cube.databinding.FragmentGestureBinding;

public class GestureFragment extends Fragment{

    private FragmentGestureBinding binding;
    private MainViewModel mainViewModel;

//    private GestureDetectorCompat motionDetector;

    @SuppressLint("ClickableViewAccessibility")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentGestureBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

//        motionDetector = new GestureDetectorCompat(context, new GestureListener());
//
//        binding.gesturePad.setOnTouchListener((view, motionEvent) -> {
//            motionDetector.onTouchEvent(motionEvent);
//            return true;
//        });

        setupPresets();

        return root;
    }

    private void setupPresets() {

        binding.left.setOnClickListener(v -> {
            mainViewModel.translate_x(-1);
        });

        binding.right.setOnClickListener(v -> {
            mainViewModel.translate_x(1);
        });

        binding.up.setOnClickListener(v -> {
            mainViewModel.translate_z(1);
        });

        binding.down.setOnClickListener(v -> {
            mainViewModel.translate_z(-1);
        });

        binding.backward.setOnClickListener(v -> {
            mainViewModel.translate_y(-1);
        });

        binding.forward.setOnClickListener(v -> {
            mainViewModel.translate_y(1);
        });

        binding.rotateLeftX.setOnClickListener(v -> {
            mainViewModel.rotate("x", -5);
        });

        binding.rotateLeftY.setOnClickListener(v -> {
            mainViewModel.rotate("y", -5);
        });

        binding.rotateLeftZ.setOnClickListener(v -> {
            mainViewModel.rotate("z", -5);
        });

        binding.rotateRightX.setOnClickListener(v -> {
            mainViewModel.rotate("x", 5);
        });

        binding.rotateRightY.setOnClickListener(v -> {
            mainViewModel.rotate("y", 5);
        });

        binding.rotateRightZ.setOnClickListener(v -> {
            mainViewModel.rotate("z", 5);
        });

        binding.zoomIn.setOnClickListener(v -> {
            mainViewModel.scale(.5);
        });

        binding.zoomOut.setOnClickListener(v -> {
            mainViewModel.scale(2);
        });
    }

//    class GestureListener extends GestureDetector.SimpleOnGestureListener {
//        @Override
//        public boolean onFling(MotionEvent event1, MotionEvent event2,
//                               float velocityX, float velocityY) {
//            if(GestureUtils.isAboveVThreshold(velocityX, velocityY)) {
//                //send
//            }
//            return super.onFling(event1, event2, velocityX, velocityY);
//        }
//
//        @Override
//        public boolean onScroll(MotionEvent event1, MotionEvent event2,
//                                float distanceX, float distanceY) {
//            if(GestureUtils.shouldSendScroll()){
//                //send
//            }
//            return super.onScroll(event1, event2, distanceX, distanceY);
//        }
//
//        @Override
//        public void onLongPress(MotionEvent event){
//            //send
//        }
//
//        @Override
//        public boolean onDoubleTap(MotionEvent event) {
//            //send
//            return super.onDoubleTap(event);
//        }
//    }

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