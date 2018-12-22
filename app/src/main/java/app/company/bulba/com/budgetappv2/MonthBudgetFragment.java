package app.company.bulba.com.budgetappv2;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import app.company.bulba.com.budgetappv2.data.Budget;
import app.company.bulba.com.budgetappv2.data.MonthBudget;
import app.company.bulba.com.budgetappv2.data.Receipt;

/**
 * Created by Zachary on 26/11/2018.
 */

public class MonthBudgetFragment extends Fragment {

    private MonthBudgetViewModel mMonthBudgetViewModel;
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    private ReceiptViewModel mReceiptViewModel;
    private BudgetViewModel mBudgetViewModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_month_budget, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = getView().findViewById(R.id.month_budget_recyclerview);
        final MonthBudgetListAdapter adapter = new MonthBudgetListAdapter(getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mMonthBudgetViewModel = ViewModelProviders.of(getActivity()).get(MonthBudgetViewModel.class);

        mMonthBudgetViewModel.getAllMonthBudgets().observe(this, new Observer<List<MonthBudget>>() {
            @Override
            public void onChanged(@Nullable List<MonthBudget> monthBudgets) {
                adapter.setMonthBudgets(monthBudgets);
            }
        });
    }

        /* every time new item created in receipt, add into this table
            if category and date already exists, update total spent
            else create new category and date with total spent

            search budget table for limit
         */



}
