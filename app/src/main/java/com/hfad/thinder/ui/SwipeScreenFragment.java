package com.hfad.thinder.ui;

import android.os.Bundle;

import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.utils.widget.ImageFilterButton;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.card.MaterialCardView;
import com.hfad.thinder.R;
import com.hfad.thinder.databinding.FragmentSwipeScreenBinding;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SwipeScreenFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SwipeScreenFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FragmentSwipeScreenBinding binding;

    private ImageFilterButton like;
    private ImageFilterButton dislike;
    private ImageFilterButton redraw;

    private MaterialCardView cardTwo;

    private int currentDeckPosition;
    private ArrayList<SwipeScreenCard> cardDeck;

    private MotionLayout motionLayout;

    private ArrayList<DetailViewStates> detailViewOrder;
    private int currentDetailViewPosition;

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
        //todo: let viewmodel send data to backend
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_swipe_screen, container, false);

        ArrayList<Integer> images = new ArrayList<>();
        images.add(R.drawable.index);
        images.add(R.drawable.ic_thinder_logo_v1);
        cardDeck = new ArrayList<>();
        cardDeck.add(new SwipeScreenCard(images,"Deep Reinforcement Learning for Autonomous Driving"));
        cardDeck.add(new SwipeScreenCard(images,"Thinking Fast and Slow with Model-Based Reinforcement Learning"));
        cardDeck.add(new SwipeScreenCard(images,"Deep Reinforcement Learning for the Control of Robotic Manipulation"));
        cardDeck.add(new SwipeScreenCard(images,"Efficient Uncertainty Aware Latent Model-Based Optimization"));
        cardDeck.add(new SwipeScreenCard(images,"Machine Learning & Transfer Learning im Bereich der Arbeitsmaschinen"));
        cardDeck.add(new SwipeScreenCard(images,"There are no more cards"));
        cardDeck.add(new SwipeScreenCard(images,"There are no more cards"));

        View view = binding.getRoot();
        like = binding.btSwipeRight;
        dislike = binding.btSwipeLeft;
        redraw = binding.btRedraw;
        like.setOnClickListener(this);
        dislike.setOnClickListener(this);
        redraw.setOnClickListener(this);


        blockTouch = binding.vBlockTouch;

        cardTwo = binding.cardTwo;

        detailViewOrder = new ArrayList<>();


        populateCards();

        motionLayout = binding.motionLayoutSwipeScreen;
        currentDeckPosition = 0;
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
                            Toast toast = Toast.makeText(view.getContext(), "liked", Toast.LENGTH_SHORT);
                            toast.show();
                            cardLiked();
                            Log.i("tag", motionLayout.toString());
                        }

                        break;
                    case R.id.offScreenUnlike:
                        if(motionLayout.getEndState() == R.id.start){
                            Toast toast1 = Toast.makeText(view.getContext(), "disliked", Toast.LENGTH_SHORT);
                            toast1.show();
                            cardDisliked();
                            Log.i("tag", motionLayout.toString());
                        }
                        break;
                }
            }

            @Override
            public void onTransitionTrigger(MotionLayout motionLayout, int triggerId, boolean positive, float progress) {

            }
        });

        return view;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btSwipeLeft:
                if(motionLayout.getCurrentState() != R.id.detail){
                    motionLayout.transitionToState(R.id.unlike);
                }else{
                    if(currentDetailViewPosition > 0){
                        currentDetailViewPosition--;
                        switch(detailViewOrder.get(currentDetailViewPosition)){
                            case TOP:
                                Bundle bundleCardOne = new Bundle();
                                bundleCardOne.putString("title", cardDeck.get(currentDeckPosition).getTitle());
                                bundleCardOne.putInt("image", cardDeck.get(currentDeckPosition).getImages().get(0));
                                getChildFragmentManager().beginTransaction()
                                        .setCustomAnimations(
                                                R.anim.slide_in_right,
                                                R.anim.slide_out_right
                                        )
                                        .setReorderingAllowed(true)
                                        .replace(R.id.fragmentCardOne, SwipeScreenTopFragment.class, bundleCardOne)
                                        .addToBackStack(null)
                                        .commit();
                                break;
                            case IMAGE:
                                Bundle image = new Bundle();
                                image.putInt("image", cardDeck.get(currentDeckPosition).getImages().get(currentDetailViewPosition-1));
                                getChildFragmentManager().beginTransaction()
                                        .setCustomAnimations(
                                                R.anim.slide_in_right,
                                                R.anim.slide_out_right
                                        )
                                        .setReorderingAllowed(true)
                                        .replace(R.id.fragmentCardOne, SwipeScreenImageFragment.class, image)
                                        .addToBackStack(null)
                                        .commit();
                                break;
                            case TEXT:
                                getChildFragmentManager().beginTransaction()
                                        .setCustomAnimations(
                                                R.anim.slide_in_right,
                                                R.anim.slide_out_right
                                        )
                                        .setReorderingAllowed(true)
                                        .replace(R.id.fragmentCardOne, SwipeScreenTextFragment.class, null)
                                        .addToBackStack(null)
                                        .commit();
                                break;
                            case INFO:
                                getChildFragmentManager().beginTransaction()
                                        .setCustomAnimations(
                                                R.anim.slide_in_right,
                                                R.anim.slide_out_right
                                        )
                                        .setReorderingAllowed(true)
                                        .replace(R.id.fragmentCardOne, SwipeScreenInfoFragment.class, null)
                                        .addToBackStack(null)
                                        .commit();
                                break;
                        }
                    }
                }

                break;
            case R.id.btSwipeRight:
                if(motionLayout.getCurrentState()!= R.id.detail){
                    motionLayout.transitionToState(R.id.like);
                }else{
                    if(currentDetailViewPosition < detailViewOrder.size() - 1){

                        currentDetailViewPosition++;
                        switch(detailViewOrder.get(currentDetailViewPosition)){

                            case TOP:
                                Bundle bundleCardOne = new Bundle();
                                bundleCardOne.putString("title", cardDeck.get(currentDetailViewPosition).getTitle());
                                bundleCardOne.putInt("image", cardDeck.get(currentDeckPosition).getImages().get(0));
                                getChildFragmentManager().beginTransaction()
                                        .setCustomAnimations(
                                                R.anim.slide_in_left,
                                                R.anim.slide_out_left
                                        )
                                        .setReorderingAllowed(true)
                                        .replace(R.id.fragmentCardOne, SwipeScreenTopFragment.class, bundleCardOne)
                                        .addToBackStack(null)
                                        .commit();
                                break;
                            case IMAGE:
                                Bundle image = new Bundle();
                                image.putInt("image", cardDeck.get(currentDeckPosition).getImages().get(currentDetailViewPosition-1));
                                getChildFragmentManager().beginTransaction()
                                        .setCustomAnimations(
                                                R.anim.slide_in_left,
                                                R.anim.slide_out_left
                                        )
                                        .setReorderingAllowed(true)
                                        .replace(R.id.fragmentCardOne, SwipeScreenImageFragment.class, image)
                                        .addToBackStack(null)
                                        .commit();
                                break;
                            case TEXT:
                                getChildFragmentManager().beginTransaction()
                                        .setCustomAnimations(
                                                R.anim.slide_in_left,
                                                R.anim.slide_out_left
                                        )
                                        .setReorderingAllowed(true)
                                        .replace(R.id.fragmentCardOne, SwipeScreenTextFragment.class, null)
                                        .addToBackStack(null)
                                        .commit();
                                break;
                            case INFO:
                                getChildFragmentManager().beginTransaction()
                                        .setCustomAnimations(
                                                R.anim.slide_in_left,
                                                R.anim.slide_out_left
                                        )
                                        .setReorderingAllowed(true)
                                        .replace(R.id.fragmentCardOne, SwipeScreenInfoFragment.class, null)
                                        .addToBackStack(null)
                                        .commit();
                                break;
                        }
                    }
                }

                break;
            case R.id.btRedraw:
                redraw();
                break;
        }
    }

    private void populateCards(){
        Log.i("tag", new StringBuilder().append("image:").append(String.valueOf(cardDeck.get(currentDeckPosition).getImages().get(0))).toString());
        setDetailedViewOrder();
        Bundle bundleCardOne = new Bundle();
        bundleCardOne.putString("title", cardDeck.get(currentDeckPosition).getTitle());
        bundleCardOne.putInt("image", cardDeck.get(currentDeckPosition).getImages().get(0));
        getChildFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.fragmentCardOne, SwipeScreenTopFragment.class, bundleCardOne)
                .commit();
        Bundle bundleCardTwo = new Bundle();
        bundleCardTwo.putString("title", cardDeck.get(currentDeckPosition+1).getTitle());
        bundleCardTwo.putInt("image", cardDeck.get(currentDeckPosition+1).getImages().get(0));
        getChildFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.fragmentCardTwo, SwipeScreenTopFragment.class, bundleCardTwo)
                .commit();
    }

    private void setDetailedViewOrder() {
        currentDetailViewPosition = 0;
        detailViewOrder.clear();
        detailViewOrder.add(DetailViewStates.TOP);
        for(int i = 0; i < cardDeck.get(currentDeckPosition).getImages().size(); ++i){
            detailViewOrder.add(DetailViewStates.IMAGE);
        }
        detailViewOrder.add(DetailViewStates.TEXT);
        detailViewOrder.add(DetailViewStates.INFO);
    }

    private void cardLiked(){
        if(currentDeckPosition < cardDeck.size() - 2){
            currentDeckPosition++;
            populateCards();
        }
    }

    private void cardDisliked(){
        if(currentDeckPosition < cardDeck.size() - 2){
            currentDeckPosition++;
            populateCards();
        }
    }

    private void redraw(){
        if(currentDeckPosition > 0){
            currentDeckPosition--;
            populateCards();
        }
    }

    private enum DetailViewStates {
        TOP,
        IMAGE,
        TEXT,
        INFO
    }




}