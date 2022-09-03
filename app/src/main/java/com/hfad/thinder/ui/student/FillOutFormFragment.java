package com.hfad.thinder.ui.student;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.hfad.thinder.R;
import com.hfad.thinder.databinding.FragmentFillOutFormBinding;
import com.hfad.thinder.ui.CoursesOfStudyFragment;
import com.hfad.thinder.viewmodels.CoursesOfStudyPicker;
import com.hfad.thinder.viewmodels.ViewModelResult;
import com.hfad.thinder.viewmodels.student.FillOutFormViewModel;


/**
 * Handles binding and layout inflation of the FillOutForm Screen where the user puts in answers
 * to supervisor questions.
 */
public class FillOutFormFragment extends Fragment {

    FragmentFillOutFormBinding binding;
    FillOutFormViewModel viewModel;

    /**
     *  Required empty public constructor
     */
    public FillOutFormFragment() {
        // Required empty public constructor
    }

    /**
     * Handles layout inflation. Connects binding with Fragment und ViewModel.
     *
     * @param inflater            used for layout inflation
     * @param container           used for layout inflation
     * @param savedInstanceState  not used
     * @return                    View for fragment's UI
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_fill_out_form, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(FillOutFormViewModel.class);
        viewModel.setThesisId(requireArguments().getString("thesisUUID"));
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        binding.setFragment(this);

        View view = binding.getRoot();

        // Observes the result of the send operation and informs the user of its success
        final Observer<ViewModelResult> sendResultObserver = viewModelResult -> {
            if (viewModelResult.isSuccess()) {
                Toast toast = Toast.makeText(getContext(), getContext().getResources().getString(R.string.send_form_success), Toast.LENGTH_LONG);
                toast.show();

            } else if (!viewModelResult.isSuccess()) {
                Toast toast = Toast.makeText(getContext(), viewModelResult.getErrorMessage(), Toast.LENGTH_LONG);
                toast.show();
            }
        };

        viewModel.getSendResult().observe(getViewLifecycleOwner(), sendResultObserver);

        // informs ViewModel about changes in the input
        TextWatcher afterTextChangeWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                viewModel.formDataChanged();
            }
        };

        binding.etInsertAnswers.addTextChangedListener(afterTextChangeWatcher);

        return view;
    }

    /**
     *  Calls {@link FillOutFormViewModel} function to send answers to supervisor
     */
    public void sendForm() {
        viewModel.sendForm();
    }

}