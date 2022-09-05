package com.hfad.thinder.ui.student.swipescreen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.utils.widget.ImageFilterButton;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.card.MaterialCardView;
import com.hfad.thinder.R;
import com.hfad.thinder.databinding.FragmentSwipeScreenBinding;
import com.hfad.thinder.viewmodels.student.SwipeScreenViewModel;

/**
 * Presents swipeable cards to the student. Handles swipe animation and the transition into the
 * detailed view of the swipe card. There are always two cards present. The top card is the one that
 * the user can interact with and the bottom card is used to make the illusion of an infinitely
 * large swipe deck.
 */
public class SwipeScreenFragment extends Fragment {

    private FragmentSwipeScreenBinding binding;
    private SwipeScreenViewModel viewModel;

    private ImageFilterButton like;
    private ImageFilterButton dislike;
    private ImageFilterButton redraw;

    private MaterialCardView cardTwo;

    private MotionLayout motionLayout;


    // used to prevent user from starting another animation while one is already ongoing
    private View blockTouch;

    /**
     * Required empty constructor
     */
    public SwipeScreenFragment() {
        // Required empty public constructor
    }

    /**
     * Handles layout inflation and binding. Gets the {@link SwipeScreenViewModel}. Observes the
     * animation transitions caused by user input.
     *
     * @param inflater            used for layout inflation
     * @param container           used for layout inflation
     * @param savedInstanceState  not used
     * @return                    View for fragment's UI
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_swipe_screen, container, false);
        binding.setFragment(this);
        viewModel = new ViewModelProvider(requireActivity()).get(SwipeScreenViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(getViewLifecycleOwner());

        View view = binding.getRoot();
        like = binding.btSwipeRight;
        dislike = binding.btSwipeLeft;
        redraw = binding.btRedraw;

        blockTouch = binding.vBlockTouch;
        cardTwo = binding.cardTwo;
        motionLayout = binding.motionLayoutSwipeScreen;
        // observes changes in the animation states
        motionLayout.setTransitionListener(new MotionLayout.TransitionListener() {

            @Override
            public void onTransitionStarted(MotionLayout motionLayout, int startId, int endId) {
                if (startId == R.id.start && endId == R.id.detail) {
                    cardTwo.setVisibility(View.GONE);
                }
                if (startId == R.id.detail && endId == R.id.start) {
                    cardTwo.setVisibility(View.VISIBLE);
                }
                blockTouch.setClickable(true);
            }

            @Override
            public void onTransitionChange(MotionLayout motionLayout, int startId, int endId,
                                           float progress) {

            }

            @Override
            public void onTransitionCompleted(MotionLayout motionLayout, int currentId) {
                blockTouch.setClickable(false);
                switch (currentId) {
                    case R.id.offScreenLike:
                        if (motionLayout.getEndState() == R.id.start) {
                            viewModel.like();
                        }
                        break;
                    case R.id.offScreenUnlike:
                        if (motionLayout.getEndState() == R.id.start) {
                            viewModel.dislike();
                        }
                        break;
                    case R.id.detail:
                        dislike.setBackground(getContext().getResources().getDrawable(R.drawable.round_button));
                        break;
                    case R.id.start:
                        dislike.setBackground(getContext().getResources().getDrawable(R.drawable.round_button_red));
                        break;
                }
            }

            @Override
            public void onTransitionTrigger(MotionLayout motionLayout, int triggerId, boolean positive,
                                            float progress) {

            }
        });

        final Observer<Integer> currentDeckPositionObserver = integer -> populateCards();
        viewModel.getCurrentDeckPosition()
                .observe(getViewLifecycleOwner(), currentDeckPositionObserver);
        return view;
    }

    /**
     * Called on deletion of the fragment. Pushes the user ratings to the backend when user leaves
     * the swipescreen.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        viewModel.pushRatings();
    }

    /**
     * Called when user presses button on the right. Either likes a thesis or when in detailed view
     * shows next screen.
     */
    public void rightClick() {
        if (motionLayout.getCurrentState() != R.id.detail) {
            // implicitly calls viewmodel.like()
            motionLayout.transitionToState(R.id.like);
        } else {
            if (viewModel.incrementCurrentDetailViewPosition()) {
                populateDetailView(viewModel.getCurrentDetailViewState(), R.anim.slide_in_left,
                        R.anim.slide_out_left);
            }
        }
    }

    /**
     * Called when user presses button on the left. Either dislikes a thesis or when in detailed view
     * shows previous screen.
     */
    public void leftClick() {
        if (motionLayout.getCurrentState() != R.id.detail) {
            motionLayout.transitionToState(R.id.unlike);
        } else {
            if (viewModel.decrementCurrentDetailViewPosition()) {
                populateDetailView(viewModel.getCurrentDetailViewState(), R.anim.slide_in_right,
                        R.anim.slide_out_right);
            }
        }
    }

    /**
     * Reverts the last rating.
     */
    public void redrawCard() {
        viewModel.redraw();
    }

    /**
     * Sets the correct data to both the card in the front and in the back.
     */
    private void populateCards() {
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

    /**
     * Populates cards in the detail view with the correct data.
     *
     * @param currentDetailViewState    what kind of detail view card
     * @param inAnimationId             transition going into the screen
     * @param outAnimationId            transition leaving the screen
     */
    private void populateDetailView(DetailViewStates currentDetailViewState, int inAnimationId,
                                    int outAnimationId) {
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

    /**
     * all the different detail view states
     */
    public enum DetailViewStates {
        TOP,       // the card on the swipe deck
        IMAGE,     // card showing an image
        TEXT,      // card showing the task and motivation of the thesis
        INFO       // information about the supervisor and thesis in general
    }


}