package com.hfad.thinder.ui.student;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.hfad.thinder.R;
import com.hfad.thinder.databinding.FragmentLikedThesesBinding;
import com.hfad.thinder.ui.ThesisCardAdapter;
import com.hfad.thinder.viewmodels.ThesisCardItem;
import com.hfad.thinder.viewmodels.student.LikedThesesViewModel;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Responsible for showing the student a list all his/her liked theses in a refreshable {@link RecyclerView}.
 */
public class LikedThesesFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private FragmentLikedThesesBinding binding;
    private LikedThesesViewModel viewModel;


    private SwipeRefreshLayout refreshLayout;
    private ThesisCardAdapter adapter;

    /**
     * Required empty constructor
     */
    public LikedThesesFragment() {
        // Required empty public constructor
    }

    /**
     * Handles layout inflation and binding. Gets the {@link LikedThesesViewModel}
     *
     * @param inflater            used for layout inflation
     * @param container           used for layout inflation
     * @param savedInstanceState  not used
     * @return                    View for fragment's UI
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_liked_theses, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(LikedThesesViewModel.class);
        View view = binding.getRoot();
        return view;
    }

    /**
     * Called after {@link LikedThesesFragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)}.
     * Sets up the {@link RecyclerView} and observes changes in the {@link LikedThesesViewModel} and
     * transfers these to the {@link ThesisCardAdapter}.
     *
     * @param view                  view returned by {@link LikedThesesFragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)}
     * @param savedInstanceState    not used
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        buildRecyclerView(view);
        final Observer<ArrayList<ThesisCardItem>> likedThesesObserver = thesisCardItems -> adapter.setElements(viewModel.getLikedTheses().getValue());
        viewModel.getLikedTheses().observe(getViewLifecycleOwner(), likedThesesObserver);
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
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        adapter = new ThesisCardAdapter(viewModel.getLikedTheses().getValue());
        RecyclerView recyclerView = binding.recyclerView;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        // sends user to the thesis he/she clicked on
        adapter.setOnItemClickListener(position -> {
            ThesisCardItem thesisCardItem = viewModel.getLikedTheses().getValue().get(position);

            UUID UUID = thesisCardItem.getThesisUUID();
            Bundle bundle = new Bundle();
            bundle.putString("thesisUUID", UUID.toString());

            Navigation.findNavController(view).navigate(R.id.action_likedThesesFragment_to_likedThesisDetailedFragment, bundle);
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
        inflater.inflate(R.menu.liked_theses_menu, menu);

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
     * Adds refresh button to the top action bar
     *
     * @param   item menu item
     * @return  returns true if handled successfully
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_refresh) {
            loadRecyclerViewData();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Fetches new theses from the backend.
     */
    private void loadRecyclerViewData(){
        viewModel.loadLikedTheses();
    }
}