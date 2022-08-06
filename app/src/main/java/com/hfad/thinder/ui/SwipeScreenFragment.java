package com.hfad.thinder.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.utils.widget.ImageFilterButton;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.card.MaterialCardView;
import com.hfad.thinder.R;
import com.hfad.thinder.databinding.FragmentSwipeScreenBinding;
import com.hfad.thinder.viewmodels.student.SwipeScreenViewModel;

import java.util.ArrayList;
import java.util.Observable;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SwipeScreenFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SwipeScreenFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FragmentSwipeScreenBinding binding;
    private SwipeScreenViewModel viewmodel;

    private ImageFilterButton like;
    private ImageFilterButton dislike;
    private ImageFilterButton redraw;

    private MaterialCardView cardTwo;

    private MotionLayout motionLayout;



    // used to prevent user from starting another animation while one is already ongoing
    private View blockTouch;

    public SwipeScreenFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SwipeScreenFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SwipeScreenFragment newInstance(String param1, String param2) {
        SwipeScreenFragment fragment = new SwipeScreenFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        viewmodel.pushRatings();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_swipe_screen, container, false);
        binding.setFragment(this);
        viewmodel = new ViewModelProvider(requireActivity()).get(SwipeScreenViewModel.class);
        Bitmap image2 = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.index);
        ArrayList<Bitmap> images2 = new ArrayList<>();
        images2.add(image2);
        viewmodel.setImages(images2);
        binding.setLifecycleOwner(this);

        View view = binding.getRoot();
        like = binding.btSwipeRight;
        dislike = binding.btSwipeLeft;
        redraw = binding.btRedraw;


        blockTouch = binding.vBlockTouch;
        cardTwo = binding.cardTwo;
        motionLayout = binding.motionLayoutSwipeScreen;
        motionLayout.setTransitionListener(new MotionLayout.TransitionListener(){

            @Override
            public void onTransitionStarted(MotionLayout motionLayout, int startId, int endId) {
                if(startId == R.id.start && endId == R.id.detail){
                    cardTwo.setVisibility(View.GONE);
                }
                if(startId == R.id.detail && endId == R.id.start){
                    cardTwo.setVisibility(View.VISIBLE);
                }
                blockTouch.setClickable(true);
            }

            @Override
            public void onTransitionChange(MotionLayout motionLayout, int startId, int endId, float progress) {

            }

            @Override
            public void onTransitionCompleted(MotionLayout motionLayout, int currentId) {
                blockTouch.setClickable(false);
                switch(currentId){
                    case R.id.offScreenLike:
                        if(motionLayout.getEndState() == R.id.start){
                            viewmodel.like();
                        }

                        break;
                    case R.id.offScreenUnlike:
                        if(motionLayout.getEndState() == R.id.start){
                            viewmodel.dislike();
                        }
                        break;
                }
            }

            @Override
            public void onTransitionTrigger(MotionLayout motionLayout, int triggerId, boolean positive, float progress) {

            }
        });

        final Observer<Integer> currentDeckPositionObserver = new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                populateCards();
            }
        };
        viewmodel.getCurrentDeckPosition().observe(getViewLifecycleOwner(), currentDeckPositionObserver);
        return view;
    }

    public void rightClick(View view){
        if(motionLayout.getCurrentState()!= R.id.detail){
            // implicitly calls viewmodel.like()
            motionLayout.transitionToState(R.id.like);
        }else{
            if(viewmodel.incrementCurrentDetailViewPosition())
                populateDetailView(viewmodel.getCurrentDetailViewState(), R.anim.slide_in_left, R.anim.slide_out_left);
        }
    }



    public void leftClick(View view){
        if(motionLayout.getCurrentState() != R.id.detail){
            motionLayout.transitionToState(R.id.unlike);
        }else{
            if(viewmodel.decrementCurrentDetailViewPosition())
                populateDetailView(viewmodel.getCurrentDetailViewState(), R.anim.slide_in_right, R.anim.slide_out_right);
        }
    }

    public void redrawCard(View view){
        viewmodel.redraw();
    }

    private void populateCards(){
        Bundle cardOneBundle = new Bundle();
        cardOneBundle.putBoolean("isCardOne", true);
        Bundle cardTwoBundle = new Bundle();
        cardTwoBundle.putBoolean("isCardOne", false);
        getChildFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.fragmentCardOne, SwipeScreenTopFragment.class, cardOneBundle)
                .commit();
        getChildFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.fragmentCardTwo, SwipeScreenTopFragment.class, cardTwoBundle)
                .commit();
    }

    private void populateDetailView(DetailViewStates currentDetailViewState, int inAnimationId, int outAnimationId) {
        switch (currentDetailViewState) {

            case TOP:
                getChildFragmentManager().beginTransaction()
                        .setCustomAnimations(
                                inAnimationId,
                                outAnimationId
                        )
                        .setReorderingAllowed(true)
                        .replace(R.id.fragmentCardOne, SwipeScreenTopFragment.class, null)
                        .addToBackStack(null)
                        .commit();
                break;
            case IMAGE:
                getChildFragmentManager().beginTransaction()
                        .setCustomAnimations(
                                inAnimationId,
                                outAnimationId
                        )
                        .setReorderingAllowed(true)
                        .replace(R.id.fragmentCardOne, SwipeScreenImageFragment.class, null)
                        .addToBackStack(null)
                        .commit();
                break;
            case TEXT:
                getChildFragmentManager().beginTransaction()
                        .setCustomAnimations(
                                inAnimationId,
                                outAnimationId
                        )
                        .setReorderingAllowed(true)
                        .replace(R.id.fragmentCardOne, SwipeScreenTextFragment.class, null)
                        .addToBackStack(null)
                        .commit();
                break;
            case INFO:
                getChildFragmentManager().beginTransaction()
                        .setCustomAnimations(
                                inAnimationId,
                                outAnimationId
                        )
                        .setReorderingAllowed(true)
                        .replace(R.id.fragmentCardOne, SwipeScreenInfoFragment.class, null)
                        .addToBackStack(null)
                        .commit();
                break;
        }
    }

    public enum DetailViewStates {
        TOP,
        IMAGE,
        TEXT,
        INFO
    }




}