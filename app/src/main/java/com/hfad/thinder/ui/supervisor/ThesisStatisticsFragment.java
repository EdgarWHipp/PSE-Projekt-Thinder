package com.hfad.thinder.ui.supervisor;

import android.graphics.Color;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.hfad.thinder.R;
import com.hfad.thinder.data.source.result.Pair;
import com.hfad.thinder.databinding.FragmentThesisStatisticsBinding;
import com.hfad.thinder.viewmodels.supervisor.EditThesisViewModel;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ThesisStatisticsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ThesisStatisticsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FragmentThesisStatisticsBinding binding;
    private PieChart pieChart;
    private EditThesisViewModel viewModel;

    public ThesisStatisticsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ThesisStatisticsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ThesisStatisticsFragment newInstance(String param1, String param2) {
        ThesisStatisticsFragment fragment = new ThesisStatisticsFragment();
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_thesis_statistics, container, false);
        pieChart = binding.pcStatistics;
        viewModel = new ViewModelProvider(requireActivity()).get(EditThesisViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        setPieChart(viewModel.getThesisStatistics());
    }

    private void setPieChart(Pair<Integer, Integer> statistics){
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(statistics.getSecond(), getContext().getResources().getString(R.string.likes)));
        entries.add(new PieEntry(statistics.getSecond(), getContext().getResources().getString(R.string.dislikes)));
        PieDataSet dataSet = new PieDataSet(entries, getContext().getResources().getString(R.string.like_dislike_ratio));
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(getContext().getResources().getColor(R.color.green_400));
        colors.add(getContext().getResources().getColor(R.color.red_500));
        dataSet.setColors(colors);
        PieData data = new PieData(dataSet);
        data.setValueTextColor(Color.WHITE);
        data.setValueFormatter(new LargeValueFormatter());
        data.setValueTextSize(13f);
        pieChart.getDescription().setEnabled(false);
        pieChart.animateY(1400, Easing.EaseInOutQuad);
        pieChart.setData(data);
    }
}