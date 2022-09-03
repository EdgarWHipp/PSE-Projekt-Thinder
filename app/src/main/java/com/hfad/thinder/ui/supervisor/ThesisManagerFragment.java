package com.hfad.thinder.ui.supervisor;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.hfad.thinder.R;
import com.hfad.thinder.databinding.FragmentThesisManagerBinding;
import com.hfad.thinder.ui.ThesisCardAdapter;
import com.hfad.thinder.ui.student.LikedThesesFragment;
import com.hfad.thinder.viewmodels.ThesisCardItem;
import com.hfad.thinder.viewmodels.supervisor.ThesisManagerViewModel;

import java.util.ArrayList;
import java.util.UUID;

/**
 * UI responsible for showing the supervisor a list all his/her created theses in a refreshable {@link RecyclerView}.
 */
public class ThesisManagerFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private FragmentThesisManagerBinding binding;

    private RecyclerView recyclerView;
    private ThesisCardAdapter adapter;
    private SwipeRefreshLayout refreshLayout;

    private View view;

    private ThesisManagerViewModel viewModel;

    /**
     * Required empty constructor
     */
    public ThesisManagerFragment() {
        // Required empty public constructor
    }

    /**
     * Handles layout inflation and binding. Gets the {@link ThesisManagerViewModel}
     *
     * @param inflater            used for layout inflation
     * @param container           used for layout inflation
     * @param savedInstanceState  not used
     * @return                    View for fragment's UI
     */
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_thesis_manager, container, false);
        view = binding.getRoot();
        return view;
    }

    /**
     * Called after {@link ThesisManagerFragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)}.
     * Sets up the {@link RecyclerView}, creates and observes changes in the {@link ThesisManagerViewModel} and
     * transfers these to the {@link ThesisCardAdapter}.
     *
     * @param view                  view returned by {@link ThesisManagerFragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)}
     * @param savedInstanceState    not used
     */
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        viewModel = new ViewModelProvider(this).get(ThesisManagerViewModel.class);
        buildRecyclerView(view);
        final androidx.lifecycle.Observer<ArrayList<ThesisCardItem>> thesisCardItemObserver = thesisCardItems -> {
            if (!recyclerView.isComputingLayout()) {
                ArrayList<ThesisCardItem> elements = viewModel.getThesisCardItems().getValue();
                adapter.setElements(elements);
            }
        };

        viewModel.getThesisCardItems().observe(getViewLifecycleOwner(), thesisCardItemObserver);

        final Observer<Boolean> isLoadingObserver = aBoolean -> refreshLayout.setRefreshing(aBoolean);
        viewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoadingObserver);
    }

    /**
     * Sets up the {@link RecyclerView} and {@link ThesisCardAdapter}.
     *
     * @param view needed to get the context
     */
    private void buildRecyclerView(View view) {
        refreshLayout = binding.refreshLayout;
        refreshLayout.setOnRefreshListener(this);
        recyclerView = binding.recyclerView;
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        adapter = new ThesisCardAdapter(viewModel.getThesisCardItems().getValue());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(position -> {
            ThesisCardItem thesisCardItem = viewModel.getThesisCardItems().getValue().get(position);
            UUID UUID = thesisCardItem.getThesisUUID();
            String thesisTitle = thesisCardItem.getTitle();
            Bundle bundle = new Bundle();
            bundle.putString("thesisUUID", UUID.toString());
            bundle.putString("thesisTitle", thesisTitle);
            Navigation.findNavController(view).navigate(R.id.action_thesisManagerFragment_to_editThesisFragment, bundle);
        });
    }

    /**
     * Called when user pulls down to refresh.
     */
    @Override
    public void onRefresh() {
        loadRecyclerViewData();
    }

    /**
     * adds filtering of the {@link RecyclerView} in the top action bar
     *
     * @param menu      menu item
     * @param inflater  menu inflater
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.theses_manager_menu, menu);

        buildSearchMenu(menu);
    }

    /**
     * Sets up the search menu for filtering
     *
     * @param menu menu component to attach search function to
     */
    private void buildSearchMenu(Menu menu) {
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

    /**
     * Adds refresh button and create new thesis button to the top action bar
     *
     * @param   item menu item
     * @return  returns true if handled successfully
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_newFragment:
                Navigation.findNavController(view).navigate(R.id.action_thesisManagerFragment_to_newThesisFragment);
                break;
            case R.id.menu_refresh:
                loadRecyclerViewData();
                break;

        }
        return true;
    }

    /**
     * Fetches new theses from the backend.
     */
    private void loadRecyclerViewData(){
        viewModel.loadThesisManagerItems();
    }

}
