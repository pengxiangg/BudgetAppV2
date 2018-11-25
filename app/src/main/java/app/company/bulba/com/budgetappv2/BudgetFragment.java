package app.company.bulba.com.budgetappv2;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

import app.company.bulba.com.budgetappv2.data.Budget;

/**
 * Created by Zachary on 25/11/2018.
 */

public class BudgetFragment extends Fragment {

    private BudgetViewModel mBudgetViewModel;
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_budget, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = getView().findViewById(R.id.budgetRecyclerView);
        final BudgetListAdapter adapter = new BudgetListAdapter(getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mBudgetViewModel = ViewModelProviders.of(getActivity()).get(BudgetViewModel.class);

        mBudgetViewModel.getAllBudgets().observe(this, new Observer<List<Budget>>() {
            @Override
            public void onChanged(@Nullable List<Budget> budgets) {
                adapter.setBudgets(budgets);
            }
        });

        Button button = getView().findViewById(R.id.budgetAddButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddBudgetFragment fragment = new AddBudgetFragment();
                getFragmentManager().beginTransaction().replace(R.id.frag_container, fragment)
                        .commit();
            }
        });

        mBudgetViewModel.getStringo().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                Log.v("TAG: HI", "Cat: " + s);
                Budget budget = new Budget();
                budget.setCategory(s);
                budget.setLimit(99);
                budget.setSpent(99);
                mBudgetViewModel.insert(budget);
            }
        });

    }
}
