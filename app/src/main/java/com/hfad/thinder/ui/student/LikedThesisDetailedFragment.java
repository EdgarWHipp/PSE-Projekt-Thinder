package com.hfad.thinder.ui.student;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.hfad.thinder.R;
import com.hfad.thinder.databinding.FragmentLikedThesisDetailedBinding;
import com.hfad.thinder.viewmodels.ViewModelResult;
import com.hfad.thinder.viewmodels.student.LikedThesesViewModel;
import com.hfad.thinder.viewmodels.student.LikedThesisDetailedViewModel;

import java.util.UUID;

/**
 * Shows user detailed view of one of her/his liked theses.
 */
public class LikedThesisDetailedFragment extends Fragment {


    private UUID thesisUUID;

    private FragmentLikedThesisDetailedBinding binding;
    private LikedThesisDetailedViewModel viewModel;


    /**
     * required public constructor
     */
    public LikedThesisDetailedFragment() {
        // Required empty public constructor
    }

    /**
     * Handles layout inflation and binding. Gets the {@link LikedThesisDetailedViewModel}
     *
     * @param inflater            used for layout inflation
     * @param container           used for layout inflation
     * @param savedInstanceState  not used
     * @return                    View for fragment's UI
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_liked_thesis_detailed, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(LikedThesisDetailedViewModel.class);
        thesisUUID = UUID.fromString(requireArguments().getString("thesisUUID"));

        viewModel.setThesisId(thesisUUID);
        // Inflate the layout for this fragment
        binding.setFragment(this);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(getViewLifecycleOwner());

        // observes the result of a thesis deletion and informs user if it was successful or not
        final Observer<ViewModelResult> deleteResultObserver = viewModelResult -> {
            if(viewModelResult == null)
                return;
            if (viewModelResult.isSuccess()) {
                Toast toast = Toast.makeText(getContext(), getText(R.string.delete_successful), Toast.LENGTH_LONG);
                toast.show();
                Navigation.findNavController(binding.getRoot()).popBackStack();
            } else if (!viewModelResult.isSuccess()) {
                Toast toast = Toast.makeText(getContext(), viewModelResult.getErrorMessage(), Toast.LENGTH_LONG);
                toast.show();
            }
        };

        viewModel.getDeleteResult().observe(getViewLifecycleOwner(), deleteResultObserver);

        return binding.getRoot();
    }

    /**
     * starts a new mail client activity with the mail of the supervisor as the receiver
     */
    public void startEmailClient() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        String receiver = viewModel.getMail().getValue();
        Uri data = Uri.parse("mailto:" + receiver);
        intent.setData(data);
        startActivity(intent);
    }

    /**
     * Moves the user to the {@link FillOutFormFragment}
     *
     * @param view  used to find fragment in the NavController
     */
    public void goToFillOutFormFragment(View view) {
        Bundle bundle = new Bundle();
        bundle.putString("thesisUUID", thesisUUID.toString());
        Navigation.findNavController(view).navigate(R.id.action_likedThesisDetailedFragment_to_fillOutFormFragment, bundle);
    }

    /**
     * Moves the user to the {@link ImageGalleryStudentFragment}
     *
     * @param view  used to find fragment in the NavController
     */
    public void goToImageGalleryFragment(View view) {
        Navigation.findNavController(view).navigate(R.id.action_likedThesisDetailedFragment_to_imageGalleryFragmentStudent);
    }

    /**
     * Opens confirmation prompt and calls delete method in the {@link LikedThesisDetailedViewModel}
     */
    public void deleteThesis() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(true);
        builder.setTitle(getContext().getResources().getString(R.string.remove_thesis));
        builder.setMessage(getContext().getResources().getString(R.string.remove_thesis_text));
        builder.setPositiveButton(getContext().getResources().getString(R.string.remove),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        viewModel.delete();
                    }
                });
        builder.setNegativeButton(android.R.string.cancel, (dialog, which) -> {
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}