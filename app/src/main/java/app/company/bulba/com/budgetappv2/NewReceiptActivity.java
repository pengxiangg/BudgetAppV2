package app.company.bulba.com.budgetappv2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.w3c.dom.Entity;

public class NewReceiptActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.example.android.wordlistsql.REPLY";

    private EditText mDetailsEditView;
    private EditText mCostEditView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_receipt);
        mDetailsEditView = findViewById(R.id.details_editView);
        mCostEditView = findViewById(R.id.cost_editView);

        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                String details = mDetailsEditView.getText().toString();
                String costString = mCostEditView.getText().toString();
                int costInt = Integer.parseInt(costString);
                Log.i("MyActivity2", "Hello " + details);

                if(details.length() == 0 || costString.length() == 0) {
                    Toast.makeText(NewReceiptActivity.this, "Please make sure all details are correct", Toast.LENGTH_LONG).show();
                    return;
                }

                Bundle bundle = new Bundle();
                bundle.putString("DETAILS_KEY", details);
                bundle.putInt("COST_KEY", costInt);
                Log.i("MyActivity3", "Hello " + bundle.getString("DETAILS_KEY"));

                replyIntent.putExtras(bundle);
                setResult(RESULT_OK, replyIntent);

                finish();
            }
        });
    }
}
