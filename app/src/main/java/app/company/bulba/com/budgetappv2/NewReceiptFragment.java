package app.company.bulba.com.budgetappv2;

import android.app.DatePickerDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import org.w3c.dom.Entity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import app.company.bulba.com.budgetappv2.data.Receipt;

public class NewReceiptFragment extends Fragment {

    public static final String EXTRA_REPLY = "com.example.android.wordlistsql.REPLY";

    private EditText mDetailsEditView;
    private EditText mCostEditView;
    private EditText mDateEditView;
    private EditText mCategoryEditView;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private ReceiptViewModel mReceiptViewModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_new_receipt, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mDetailsEditView = getView().findViewById(R.id.details_editView);
        mCostEditView = getView().findViewById(R.id.cost_editView);
        mDateEditView = getView().findViewById(R.id.date_editView);
        mCategoryEditView = getView().findViewById(R.id.category_editView);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        mDateEditView.setText(sdf.format(new Date()));

        mReceiptViewModel = ViewModelProviders.of(this).get(ReceiptViewModel.class);

        mDateEditView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int day = cal.get(Calendar.DAY_OF_MONTH);
                int month = cal.get(Calendar.MONTH);
                int year = cal.get(Calendar.YEAR);

                DatePickerDialog dialog = new DatePickerDialog(getContext(), android.R.style.Theme_Holo_Light_Dialog_MinWidth,
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

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = day + "/" + month + "/" + year;
                mDateEditView.setText(date);
            }
        };

        final Button button = getView().findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String details = mDetailsEditView.getText().toString();
                String costString = mCostEditView.getText().toString();
                int costInt = Integer.parseInt(costString);
                String date = mDateEditView.getText().toString();
                String category = mCategoryEditView.getText().toString();

                if(details.length() == 0 || costString.length() == 0 || date.length() == 0) {
                    Toast.makeText(getContext(), "Please make sure all details are correct", Toast.LENGTH_LONG).show();
                    return;
                }

                Receipt receipt = new Receipt();
                receipt.setDetails(details);
                receipt.setCost(costInt);
                receipt.setDate(date);
                receipt.setCategory(category);
                mReceiptViewModel.insert(receipt);
                ReceiptFragment fragment = new ReceiptFragment();
                getFragmentManager().beginTransaction().replace(R.id.frag_container, fragment)
                        .commit();

            }
        });

    }

}
