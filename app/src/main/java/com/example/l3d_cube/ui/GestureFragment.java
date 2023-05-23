package com.example.l3d_cube.ui;

import static com.example.l3d_cube.Model.PresetShapes.Cube.cube;
import static com.example.l3d_cube.Model.PresetShapes.Cylinder.cylinder;
import static com.example.l3d_cube.Model.PresetShapes.Rhomboid.rhomboid;
import static com.example.l3d_cube.Model.PresetShapes.Sphere.sphere;

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
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.core.view.GestureDetectorCompat;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;

import com.example.l3d_cube.MainViewModel;
import com.example.l3d_cube.databinding.FragmentGestureBinding;
import com.example.l3d_cube.gesture.GestureUtils;

public class GestureFragment extends Fragment{

    private FragmentGestureBinding binding;
    private Context context;
    private MainViewModel mainViewModel;

//    private GestureDetectorCompat motionDetector;

    @SuppressLint("ClickableViewAccessibility")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentGestureBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        context = getActivity();

//        motionDetector = new GestureDetectorCompat(context, new GestureListener());
//
//        binding.gesturePad.setOnTouchListener((view, motionEvent) -> {
//            motionDetector.onTouchEvent(motionEvent);
//            return true;
//        });

        return root;
    }

    private void setupPresets() {
        ImageButton preset1 = binding.preset1;
        preset1.setOnClickListener(v -> {
            mainViewModel.translate_x(1);
        });

        ImageButton preset2 = binding.preset2;
        preset2.setOnClickListener(v -> {
            mainViewModel.translate_x(-1);
        });

        ImageButton preset3 = binding.preset3;
        preset3.setOnClickListener(v -> {
            mainViewModel.translate_y(1);
        });

        ImageButton preset4 = binding.preset4;
        preset4.setOnClickListener(v -> {
            mainViewModel.translate_y(-1);
        });

        ImageButton preset5 = binding.preset5;
        preset5.setOnClickListener(v -> {
            mainViewModel.translate_z(1);
        });

        ImageButton preset6 = binding.preset6;
        preset6.setOnClickListener(v -> {
            mainViewModel.translate_z(-1);
        });

        ImageButton preset7 = binding.preset7;
        preset7.setOnClickListener(v -> {
            mainViewModel.scale(.5);
        });

        ImageButton preset8 = binding.preset8;
        preset8.setOnClickListener(v -> {
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