package app.company.bulba.com.budgetappv2;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import app.company.bulba.com.budgetappv2.data.Receipt;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Zachary on 23/11/2018.
 */

public class ReceiptFragment extends Fragment {

    private ReceiptViewModel mReceiptViewModel;
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;

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

        mReceiptViewModel.getAllReceipts().observe(this, new Observer<List<Receipt>>() {
            @Override
            public void onChanged(@Nullable final List<Receipt> receipts) {
                // Update the cached copy of the words in the adapter.
                adapter.setReceipts(receipts);
            }
        });

        Button button = getView().findViewById(R.id.submitButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), NewReceiptActivity.class);
                startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
            }
        });

        final TextView totalCostTextView = getView().findViewById(R.id.total_cost_tv);

        mReceiptViewModel.getTotalCost().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                if (integer == null) {
                    integer = 0;
                }
                totalCostTextView.setText(Integer.toString(integer));

            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Receipt receipt = new Receipt();
            Bundle extras = data.getExtras();
            String details = extras.getString("DETAILS_KEY");
            int cost = extras.getInt("COST_KEY");
            String date = extras.getString("DATE_KEY");
            String category = extras.getString("CATEGORY_KEY");
            receipt.setDetails(details);
            receipt.setCost(cost);
            receipt.setDate(date);
            receipt.setCategory(category);
            mReceiptViewModel.insert(receipt);
        } else {
            Toast.makeText(
                    getActivity(),
                    "Error",
                    Toast.LENGTH_LONG).show();
        }
    }
}
