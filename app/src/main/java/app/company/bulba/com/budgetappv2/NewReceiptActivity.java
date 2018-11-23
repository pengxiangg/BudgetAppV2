package app.company.bulba.com.budgetappv2;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import org.w3c.dom.Entity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class NewReceiptActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.example.android.wordlistsql.REPLY";

    private EditText mDetailsEditView;
    private EditText mCostEditView;
    private EditText mDateEditView;
    private EditText mCategoryEditView;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_receipt);
        mDetailsEditView = findViewById(R.id.details_editView);
        mCostEditView = findViewById(R.id.cost_editView);
        mDateEditView = findViewById(R.id.date_editView);
        mCategoryEditView = findViewById(R.id.category_editView);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        mDateEditView.setText(sdf.format(new Date()));

        mDateEditView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int day = cal.get(Calendar.DAY_OF_MONTH);
                int month = cal.get(Calendar.MONTH);
                int year = cal.get(Calendar.YEAR);

                DatePickerDialog dialog = new DatePickerDialog(NewReceiptActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = day + "/" + month + "/" + year;
                mDateEditView.setText(date);
            }
        };


        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                String details = mDetailsEditView.getText().toString();
                String costString = mCostEditView.getText().toString();
                int costInt = Integer.parseInt(costString);
                String date = mDateEditView.getText().toString();
                String category = mCategoryEditView.getText().toString();

                if(details.length() == 0 || costString.length() == 0 || date.length() == 0) {
                    Toast.makeText(NewReceiptActivity.this, "Please make sure all details are correct", Toast.LENGTH_LONG).show();
                    return;
                }

                Bundle bundle = new Bundle();
                bundle.putString("DETAILS_KEY", details);
                bundle.putInt("COST_KEY", costInt);
                bundle.putString("DATE_KEY", date);
                bundle.putString("CATEGORY_KEY", category);

                replyIntent.putExtras(bundle);
                setResult(RESULT_OK, replyIntent);

                finish();
            }
        });
    }
}
