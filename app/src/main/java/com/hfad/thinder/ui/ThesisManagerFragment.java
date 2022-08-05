package com.hfad.thinder.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hfad.thinder.R;
import com.hfad.thinder.databinding.FragmentThesisManagerBinding;
import com.hfad.thinder.viewmodels.ThesisCardItem;
import com.hfad.thinder.viewmodels.supervisor.ThesisManagerViewModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ThesisManagerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ThesisManagerFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FragmentThesisManagerBinding binding;

    private ArrayList<ThesisCardItem> elements;

    private RecyclerView recyclerView;
    private ThesisCardAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private View view;

    private ThesisManagerViewModel viewModel;

    public ThesisManagerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ThesisManagerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ThesisManagerFragment newInstance(String param1, String param2) {
        ThesisManagerFragment fragment = new ThesisManagerFragment();
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
        viewModel = new ViewModelProvider(this).get(ThesisManagerViewModel.class);
        final androidx.lifecycle.Observer<ArrayList<ThesisCardItem>> thesisCardItemObserver = new Observer<ArrayList<ThesisCardItem>>() {
            @Override
            public void onChanged(ArrayList<ThesisCardItem> thesisCardItems) {
                elements = viewModel.getThesisCardItems().getValue();
                adapter.setElements(elements);
            }
        };

        viewModel.getThesisCardItems().observe(getViewLifecycleOwner(), thesisCardItemObserver);
    }

    private void buildRecyclerView(View view) {
        recyclerView = binding.recyclerView;
        layoutManager = new LinearLayoutManager(view.getContext());
        adapter = new ThesisCardAdapter(elements);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new ThesisCardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String UUID = elements.get(position).getThesisUUID();
                String thesisTitle = elements.get(position).getTitle();
                Bundle bundle = new Bundle();
                bundle.putString("thesisUUID", UUID);
                bundle.putString("thesisTitle", thesisTitle);
                Navigation.findNavController(view).navigate(R.id.action_thesisManagerFragment_to_editThesisFragment, bundle);

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_thesis_manager, container, false);


        // Inflate the layout for this fragment
        view = binding.getRoot();
        return view;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.theses_manager_menu, menu);

        buildSearchView(menu);
    }

    private void buildSearchView(Menu menu) {
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        NavController navController = Navigation.findNavController(view);
        return NavigationUI.onNavDestinationSelected(item, navController) || super.onOptionsItemSelected(item);
    }

}
