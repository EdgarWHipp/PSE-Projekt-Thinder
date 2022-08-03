package com.hfad.thinder.ui;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hfad.thinder.R;
import com.hfad.thinder.databinding.FragmentSwipeScreenInfoBinding;
import com.hfad.thinder.viewmodels.student.SwipeScreenViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SwipeScreenInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SwipeScreenInfoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private FragmentSwipeScreenInfoBinding binding;
    private SwipeScreenViewModel viewModel;

    private TextView coursesOfStudy;
    private TextView supervisorName;
    private TextView phoneNumber;
    private TextView building;
    private TextView email;
    private TextView professorName;
    private TextView institute;

    public SwipeScreenInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SwipeScreenInfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SwipeScreenInfoFragment newInstance(String param1, String param2) {
        SwipeScreenInfoFragment fragment = new SwipeScreenInfoFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_swipe_screen_info, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(SwipeScreenViewModel.class);
        coursesOfStudy = binding.tvCoursesOfStudyContent;
        supervisorName = binding.tvSupervisorNameContent;
        phoneNumber = binding.tvSupervisorPhoneNumberContent;
        building = binding.tvBuildingContent;
        email = binding.tvSupervisorEmailContent;
        professorName = binding.tvSupervisingProfContent;
        institute = binding.tvInstituteContent;

        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        SwipeScreenCard currentCard = viewModel.getCurrentCard();
        String coursesOfStudyContent = String.join("\n", currentCard.getCoursesOfStudy());
        coursesOfStudy.setText(coursesOfStudyContent);
        supervisorName.setText(new StringBuilder().append(currentCard.getSupervisorFirstName()).append(" ").append(currentCard.getSupervisorLastName()).toString());
        phoneNumber.setText(currentCard.getPhoneNumber());
        building.setText(new StringBuilder().append(currentCard.getRoomNumber()).append(" ").append(currentCard.getBuilding()).toString());
        email.setText(currentCard.getEmail());
        professorName.setText(currentCard.getProfessor());
        institute.setText(currentCard.getInstitute());

    }
}