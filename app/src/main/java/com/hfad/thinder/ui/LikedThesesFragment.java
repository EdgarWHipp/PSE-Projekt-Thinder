package com.hfad.thinder.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;

import com.hfad.thinder.R;
import com.hfad.thinder.databinding.FragmentLikedThesesBinding;
import com.hfad.thinder.viewmodels.ThesisCardItem;
import com.hfad.thinder.viewmodels.student.LikedThesesViewModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LikedThesesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LikedThesesFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FragmentLikedThesesBinding binding;
    private LikedThesesViewModel viewModel;
    private View view;


    private RecyclerView recyclerView;
    private ThesisCardAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    public LikedThesesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LikedThesesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LikedThesesFragment newInstance(String param1, String param2) {
        LikedThesesFragment fragment = new LikedThesesFragment();
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
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        final Observer<ArrayList<ThesisCardItem>> likedThesesObserver = new Observer<ArrayList<ThesisCardItem>>() {
            @Override
            public void onChanged(ArrayList<ThesisCardItem> thesisCardItems) {
                buildRecyclerView(viewModel.getLikedTheses().getValue(), view);
            }
        };
        viewModel.getLikedTheses().observe(getViewLifecycleOwner(), likedThesesObserver);

    }

    private void buildRecyclerView(ArrayList<ThesisCardItem> elements, View view) {
        recyclerView = binding.recyclerView;
        layoutManager = new LinearLayoutManager(view.getContext());
        adapter = new ThesisCardAdapter(elements);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new ThesisCardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Handle click events
                String UUID = elements.get(position).getThesisUUID();
                String thesisTitle = elements.get(position).getTitle();
                Bundle bundle = new Bundle();
                bundle.putString("thesisUUID", UUID);
                bundle.putString("thesisTitle", thesisTitle);
                Navigation.findNavController(view).navigate(R.id.action_likedThesesFragment_to_likedThesisDetailedFragment, bundle);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_liked_theses, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(LikedThesesViewModel.class);
        view = binding.getRoot();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.search_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });
    }
}