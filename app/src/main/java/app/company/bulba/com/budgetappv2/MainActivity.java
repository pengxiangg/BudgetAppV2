package app.company.bulba.com.budgetappv2;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ReceiptViewModel mReceiptViewModel;
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.receiptsRecyclerView);
        final ReceiptListAdapter adapter = new ReceiptListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mReceiptViewModel = ViewModelProviders.of(this).get(ReceiptViewModel.class);

        mReceiptViewModel.getAllReceipts().observe(this, new Observer<List<Receipt>>() {
            @Override
            public void onChanged(@Nullable final List<Receipt> receipts) {
                // Update the cached copy of the words in the adapter.
                adapter.setReceipts(receipts);
            }
        });

        Button button = findViewById(R.id.submitButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewReceiptActivity.class);
                startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
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
            receipt.setDetails(details);
            receipt.setCost(cost);
            receipt.setDate(date);
            mReceiptViewModel.insert(receipt);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    "Error",
                    Toast.LENGTH_LONG).show();
        }
    }
}
