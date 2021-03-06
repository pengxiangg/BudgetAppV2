package app.company.bulba.com.budgetappv2;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import app.company.bulba.com.budgetappv2.data.MonthBudget;
import app.company.bulba.com.budgetappv2.data.Receipt;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Zachary on 23/11/2018.
 */

public class ReceiptFragment extends Fragment {

    private ReceiptViewModel mReceiptViewModel;
    private MonthBudgetViewModel mMonthBudgetViewModel;
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    boolean catAndDateDup;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, parent, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = getView().findViewById(R.id.receiptsRecyclerView);
        final ReceiptListAdapter adapter = new ReceiptListAdapter(getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mReceiptViewModel = ViewModelProviders.of(this).get(ReceiptViewModel.class);

        mMonthBudgetViewModel = ViewModelProviders.of(this).get(MonthBudgetViewModel.class);

        //Loads Receipts into view
        /*mReceiptViewModel.getAllReceipts().observe(this, new Observer<List<Receipt>>() {
            @Override
            public void onChanged(@Nullable final List<Receipt> receipts) {
                adapter.setReceipts(receipts);
            }
        });*/

        mReceiptViewModel.getAllReceiptsDesc().observe(this, new Observer<List<Receipt>>() {
            @Override
            public void onChanged(@Nullable List<Receipt> receipts) {
                adapter.setReceipts(receipts);
            }
        });

        Button button = getView().findViewById(R.id.submitButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewReceiptFragment fragment = new NewReceiptFragment();
                getFragmentManager().beginTransaction().replace(R.id.frag_container, fragment).addToBackStack(null)
                        .commit();
            }
        });

        //final TextView totalCostTextView = getView().findViewById(R.id.total_cost_tv);

        //Gets total spent
        /*mReceiptViewModel.getTotalCost().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                if (integer == null) {
                    integer = 0;
                }
                totalCostTextView.setText(Integer.toString(integer));

            }
        });*/

        //Allows for swipe deleting of Receipt Item
        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                        int position = viewHolder.getAdapterPosition();
                        Receipt myReceipt = adapter.getReceiptAtPosition(position);
                        String category = myReceipt.getCategory();
                        String date = myReceipt.getDate();
                        String monthDate = date.substring(0,7);;
                        double cost = myReceipt.getCost();

                        //Updates the MonthBudget table with updated values after deletion
                        int mbID = mMonthBudgetViewModel.getMhId(category, monthDate);
                        double mbSpent = mMonthBudgetViewModel.getMhSpent(mbID);
                        double newMbSpent = mbSpent - cost;
                        mMonthBudgetViewModel.updateMhSpent(newMbSpent, mbID);

                        //Checks if limit exists to prevent negative remainder value
                        double mbLimit = mMonthBudgetViewModel.getMhLimit(mbID);
                        if(mbLimit!=0) {
                            double mbRemainder = mbLimit - newMbSpent;
                            mMonthBudgetViewModel.updateMhRemainder(mbRemainder, mbID);
                        }


                        mReceiptViewModel.deleteReceipt(myReceipt);
                        if(mbLimit==0.00&&newMbSpent==0.00){
                            MonthBudget monthBudget = mMonthBudgetViewModel.getMonthBudget(mbID);
                            mMonthBudgetViewModel.delete(monthBudget);
                        }

                    }
                });

        helper.attachToRecyclerView(recyclerView);
    }





}
