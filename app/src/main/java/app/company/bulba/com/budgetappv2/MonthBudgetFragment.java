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

import java.text.SimpleDateFormat;
import java.util.Calendar;
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
    private BudgetViewModel mBudgetViewModel;
    private List<String> mAllCatBudget;
    private List<String> mAllCatMb;
    private List<String> mAllDateMb;
    private List<Integer> mAllLimitBudget;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_month_budget, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = getView().findViewById(R.id.month_budget_recyclerview);
        final MonthBudgetListAdapter adapter = new MonthBudgetListAdapter(getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mMonthBudgetViewModel = ViewModelProviders.of(getActivity()).get(MonthBudgetViewModel.class);

        mBudgetViewModel = ViewModelProviders.of(getActivity()).get(BudgetViewModel.class);

        mMonthBudgetViewModel.getAllMonthBudgetDesc().observe(this, new Observer<List<MonthBudget>>() {
            @Override
            public void onChanged(@Nullable List<MonthBudget> monthBudgets) {
                adapter.setMonthBudgets(monthBudgets);
            }
        });

        mAllCatMb = mMonthBudgetViewModel.getAllMhCategories();

        mAllDateMb = mMonthBudgetViewModel.getAllMhDate();



        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy/MM");
        String dateMonth = mdformat.format(calendar.getTime());
        Log.e("Tag: ", dateMonth + "DATE");

       mAllCatBudget = mBudgetViewModel.getAllCategories();

        Log.e("Tag: ", mAllCatBudget + "LOL");
        if(mAllCatBudget!=null) {
            for (int i = 0; i < mAllCatBudget.size(); ++i) {
                boolean duplicate = false;
                String category = mAllCatBudget.get(i);
                duplicate = checkDuplicate(category, dateMonth);
                if(duplicate = false) {
                    MonthBudget monthBudget = new MonthBudget();
                    monthBudget.setMhCategory(mAllCatBudget.get(i));
                    monthBudget.setMhDate(dateMonth);
                    double limit = mBudgetViewModel.getLimitCatBudget(mAllCatBudget.get(i));
                    monthBudget.setMhlimit(limit);
                    monthBudget.setMhRemainder(limit);
                    mMonthBudgetViewModel.insert(monthBudget);
                }
            }
        }
    }



    private boolean checkDuplicate(String category, String dateMonth) {
        boolean dup = false;
        if(mAllCatMb!=null) {
            for (int i = 0; i < mAllCatMb.size(); ++i) {
                for (int j = 0; j < mAllDateMb.size(); ++j) {
                    if (mAllCatMb.get(i).equals(category)) {
                        if (mAllDateMb.get(j).equals(dateMonth)) {
                            return dup = true;
                            //Need to add break?????? No
                        }
                    }
                }
            }
        }
        return dup;
    }




}
