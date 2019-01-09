package app.company.bulba.com.budgetappv2;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import app.company.bulba.com.budgetappv2.data.Budget;
import app.company.bulba.com.budgetappv2.data.MonthBudget;

/**
 * Created by Zachary on 25/11/2018.
 */

public class BudgetFragment extends Fragment {

    private BudgetViewModel mBudgetViewModel;
    private MonthBudgetViewModel mMonthBudgetViewModel;
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_budget, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = getView().findViewById(R.id.budgetRecyclerView);
        final BudgetListAdapter adapter = new BudgetListAdapter(getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mBudgetViewModel = ViewModelProviders.of(getActivity()).get(BudgetViewModel.class);

        mMonthBudgetViewModel = ViewModelProviders.of(this).get(MonthBudgetViewModel.class);

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
                getFragmentManager().beginTransaction().replace(R.id.frag_container, fragment).addToBackStack(null)
                        .commit();
            }
        });

        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
                        int position = viewHolder.getAdapterPosition();
                        final Budget myBudget = adapter.getBudgetAtPosition(position);

                        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        LayoutInflater inflater = getActivity().getLayoutInflater();
                        View dialogView = inflater.inflate(R.layout.checkbox, null);
                        builder.setView(dialogView);
                        final CheckBox checkBox = (CheckBox)dialogView.findViewById(R.id.budget_checkbox);
                        builder.setTitle("Are you sure you want to delete?")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        String category = myBudget.getBudgetCategory();

                                        Calendar calendar = Calendar.getInstance();
                                        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy/MM");
                                        String dateMonth = mdformat.format(calendar.getTime());

                                        //If user wants to delete budget limit for current month from monthBudget
                                        if(checkBox.isChecked()) {
                                            int mbId = mMonthBudgetViewModel.getMhId(category, dateMonth);
                                            int spent = mMonthBudgetViewModel.getMhSpent(mbId);
                                            if(spent==0){
                                                //Delete everything if spending is 0
                                                MonthBudget monthBudget = mMonthBudgetViewModel.getMonthBudget(mbId);
                                                mMonthBudgetViewModel.delete(monthBudget);
                                            } else {
                                                //Change limit and remainder if there is spending
                                                mMonthBudgetViewModel.updateMhLimit(0, mbId);
                                                mMonthBudgetViewModel.updateMhRemainder(0, mbId);
                                            }
                                        }

                                        mBudgetViewModel.delete(myBudget);
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        adapter.notifyItemChanged(viewHolder.getAdapterPosition());

                                    }
                                });
                        AlertDialog dialog = builder.create();
                        dialog.show();

                    }
                }
        );

        helper.attachToRecyclerView(recyclerView);


    }
}
