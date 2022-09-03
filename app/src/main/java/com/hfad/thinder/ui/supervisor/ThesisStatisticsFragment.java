package com.hfad.thinder.ui.supervisor;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.hfad.thinder.R;
import com.hfad.thinder.data.source.result.Pair;
import com.hfad.thinder.databinding.FragmentThesisStatisticsBinding;
import com.hfad.thinder.ui.ThesisCardAdapter;
import com.hfad.thinder.ui.student.LikedThesesFragment;
import com.hfad.thinder.viewmodels.student.LikedThesesViewModel;
import com.hfad.thinder.viewmodels.supervisor.EditThesisViewModel;

import java.util.ArrayList;


/**
 *  UI to show the like/dislike ratio of rated theses
 */
public class ThesisStatisticsFragment extends Fragment {

    public static final int DURATION_MILLIS = 1400;
    public static final float TEXT_SIZE = 13f;

    private PieChart pieChart;
    private EditThesisViewModel viewModel;

    /**
     * Required empty constructor
     */
    public ThesisStatisticsFragment() {
        // Required empty public constructor
    }

    /**
     * Handles layout inflation and binding. Gets the {@link EditThesisViewModel}
     *
     * @param inflater            used for layout inflation
     * @param container           used for layout inflation
     * @param savedInstanceState  not used
     * @return                    View for fragment's UI
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        com.hfad.thinder.databinding.FragmentThesisStatisticsBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_thesis_statistics, container, false);
        pieChart = binding.pcStatistics;
        viewModel = new ViewModelProvider(requireActivity()).get(EditThesisViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        return binding.getRoot();
    }

    /**
     * Called after {@link ThesisStatisticsFragment#onCreateView(android.view.LayoutInflater,
     * android.view.ViewGroup, android.os.Bundle)}. Sets up pie chart.
     *
     * @param view                  view returned by {@link ThesisStatisticsFragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)}
     * @param savedInstanceState    not used
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        setPieChart(viewModel.getThesisStatistics());
    }

    /**
     * Creates a pie chart with the given like/dislike-ratio and sets up all necessary parameters.
     *
     * @param statistics like/dislike-ratio
     */
    private void setPieChart(Pair<Integer, Integer> statistics) {
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(statistics.getFirst(), getContext().getResources().getString(R.string.likes)));
        entries.add(new PieEntry(statistics.getSecond(), getContext().getResources().getString(R.string.dislikes)));
        PieDataSet dataSet = new PieDataSet(entries, getContext().getResources().getString(R.string.like_dislike_ratio));
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(getContext().getResources().getColor(R.color.green_400));
        colors.add(getContext().getResources().getColor(R.color.red_500));
        dataSet.setColors(colors);
        PieData data = new PieData(dataSet);
        data.setValueTextColor(Color.WHITE);
        data.setValueFormatter(new LargeValueFormatter());
        data.setValueTextSize(TEXT_SIZE);
        pieChart.getDescription().setEnabled(false);
        pieChart.animateY(DURATION_MILLIS, Easing.EaseInOutQuad);
        pieChart.setData(data);
    }
}