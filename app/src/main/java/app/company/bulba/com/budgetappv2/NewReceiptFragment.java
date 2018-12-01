package app.company.bulba.com.budgetappv2;

import android.app.DatePickerDialog;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.arch.lifecycle.Observer;
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

import java.io.StringBufferInputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import app.company.bulba.com.budgetappv2.data.MonthBudget;
import app.company.bulba.com.budgetappv2.data.Receipt;

public class NewReceiptFragment extends Fragment {

    public static final String EXTRA_REPLY = "com.example.android.wordlistsql.REPLY";

    private EditText mDetailsEditView;
    private EditText mCostEditView;
    private EditText mDateEditView;
    private EditText mCategoryEditView;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private ReceiptViewModel mReceiptViewModel;
    private MonthBudgetViewModel mMonthBudgetViewModel;
    private ReceiptRepository receiptRepository;

    private List<String> mAllMhCategories;
    private List<String> mAllmhDate;
    private int mMhId;
    private boolean duplicate;
    private int totalSum;


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

        mMonthBudgetViewModel = ViewModelProviders.of(this).get(MonthBudgetViewModel.class);

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

        mMonthBudgetViewModel.getAllMhDate().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable List<String> strings) {
                mAllmhDate = strings;
            }
        });

        mMonthBudgetViewModel.getAllMhCategories().observe(getActivity(), new Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable List<String> strings) {
                mAllMhCategories = strings;
            }
        });

        final Button button = getView().findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String details = mDetailsEditView.getText().toString();
                String costString = mCostEditView.getText().toString();
                int costInt = Integer.parseInt(costString);
                String date = mDateEditView.getText().toString();
                String category = mCategoryEditView.getText().toString();
                duplicate = false;

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

                String[] parts = date.split("/", 2);
                String monthDate = parts[1];

                MonthBudget monthBudget = new MonthBudget();
                monthBudget.setMhDate(monthDate);
                monthBudget.setMhCategory(category);


                for(int i = 0; i < mAllMhCategories.size(); ++i) {
                    if(category.equals(mAllMhCategories.get(i))) {
                        for (int j = 0; j < mAllmhDate.size(); ++j) {
                            if(monthDate.equals(mAllmhDate.get(i))) {
                                duplicate = true;
                            }
                        }
                    }
                }


                if(!duplicate) {
                    totalSum = mReceiptViewModel.getSumByCatAndDate(category, "%" + monthDate);
                    monthBudget.setMhSpent(totalSum);
                    mMonthBudgetViewModel.insert(monthBudget);
                } else {
                    int mhID = mMonthBudgetViewModel.getMhId(category, monthDate);
                    totalSum = mReceiptViewModel.getSumByCatAndDate(category, "%" + monthDate);
                    monthBudget.setMhId(mhID);
                    monthBudget.setMhSpent(totalSum);
                    mMonthBudgetViewModel.update(monthBudget);
                }

                ReceiptFragment fragment = new ReceiptFragment();
                getFragmentManager().beginTransaction().replace(R.id.frag_container, fragment)
                        .commit();

            }
        });

    }


}
