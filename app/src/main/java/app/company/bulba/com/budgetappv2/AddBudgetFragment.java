package app.company.bulba.com.budgetappv2;

import android.app.Activity;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import app.company.bulba.com.budgetappv2.data.Budget;
import app.company.bulba.com.budgetappv2.data.MonthBudget;

/**
 * Created by Zachary on 23/11/2018.
 */

public class AddBudgetFragment extends Fragment {

    private EditText categoryET;
    private EditText limitET;
    private BudgetViewModel budgetModel;
    private List<String> mAllCategories;
    private boolean duplicate;
    String category;
    String dateMonth;
    private MonthBudgetViewModel mbModel;
    private List<String> mbAllCats;
    private List<String> mbAllDates;
    private ReceiptViewModel receiptViewModel;
    private List<MonthBudget> mAllMonthBudgets;
    private int mbInt = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_budget_add, parent, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        categoryET = getView().findViewById(R.id.category_input_edit_text);
        limitET = getView().findViewById(R.id.limit_edit_text);
        budgetModel = ViewModelProviders.of(getActivity()).get(BudgetViewModel.class);

        mAllCategories = budgetModel.getAllCategories();

        mbModel = ViewModelProviders.of(getActivity()).get(MonthBudgetViewModel.class);
        mbModel.getAllMonthBudgets().observe(this, new Observer<List<MonthBudget>>() {
            @Override
            public void onChanged(@Nullable List<MonthBudget> monthBudgets) {
                mAllMonthBudgets = monthBudgets;
            }
        });
        receiptViewModel = ViewModelProviders.of(getActivity()).get(ReceiptViewModel.class);

        receiptViewModel = ViewModelProviders.of(getActivity()).get(ReceiptViewModel.class);

        Button button = getView().findViewById(R.id.button_budget_submit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category = categoryET.getText().toString();
                String limitString = limitET.getText().toString();
                int limitInt = Integer.parseInt(limitString);
                duplicate = false;

               if(category.length() == 0 || limitString.length() == 0) {
                   Toast.makeText(getContext(), "Please make sure all details are correct", Toast.LENGTH_LONG).show();
                   return;
               }

               for( int i = 0; i < mAllCategories.size(); ++i) {
                    if(category.equals(mAllCategories.get(i))) {
                        duplicate = true;
                    }
               }
               Log.e("TAG: ", String.valueOf(duplicate));

               if(duplicate == false) {
                   Budget budget = new Budget();
                   budget.setBudgetCategory(category);
                   budget.setLimit(limitInt);
                   budgetModel.insert(budget);

                   Calendar calendar = Calendar.getInstance();
                   SimpleDateFormat mdformat = new SimpleDateFormat("yyyy/MM");
                   dateMonth = mdformat.format(calendar.getTime());
                   Log.e("TAG: ", "date: " + dateMonth);

                   if(!checkMonthBudgetDuplicate(mAllMonthBudgets)) {
                       MonthBudget monthBudget = new MonthBudget();
                       monthBudget.setMhCategory(category);
                       monthBudget.setMhDate(dateMonth);
                       monthBudget.setMhlimit(limitInt);
                       mbModel.insert(monthBudget);
                   } else {
                       if (mbInt > 0) {
                           mbModel.updateMhLimit(limitInt, mbInt);
                           int spent = mbModel.getMhSpent(mbInt);
                           if(spent > 0) {
                               int remainder = limitInt - spent;
                               mbModel.updateMhRemainder(remainder, mbInt);
                           }
                       }
                   }

                   InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                   imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                   BudgetFragment fragment = new BudgetFragment();
                   getFragmentManager().beginTransaction().replace(R.id.frag_container, fragment)
                           .commit();
               } else {
                   Toast.makeText(getContext(), category + " already exists in Budget", Toast.LENGTH_LONG).show();
               }
            }
        });
    }


    private boolean checkMonthBudgetDuplicate(List<MonthBudget> monthBudgetList) {
        boolean duplicate = false;
        if(monthBudgetList.size() == 0) {
            duplicate = false;
        }
        else {
            for(int i = 0; i < monthBudgetList.size(); ++i) {
                MonthBudget monthBudget = monthBudgetList.get(i);
                String mhCat = monthBudget.getMhCategory();
                String mhDate = monthBudget.getMhDate();
                if(mhCat.equals(category) && mhDate.equals(dateMonth)) {
                    if(monthBudget.getMhlimit() == 0) {
                        mbInt = monthBudget.getMhId();
                    }
                    duplicate = true;
                    break;
                } else duplicate = false;
            }
        }
        return duplicate;
    }

}
