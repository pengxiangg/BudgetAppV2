package app.company.bulba.com.budgetappv2;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import app.company.bulba.com.budgetappv2.data.Budget;

/**
 * Created by Zachary on 23/11/2018.
 */

public class AddBudgetFragment extends Fragment {

    private EditText categoryET;
    private EditText limitET;
    private BudgetViewModel shareModel;
    private List<String> mAllCategories;
    private boolean duplicate;
    String category;

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
        shareModel = ViewModelProviders.of(getActivity()).get(BudgetViewModel.class);

        shareModel.getAllCategories().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable List<String> strings) {
                mAllCategories = strings;
            }
        });

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

               Log.e("TAG CHECK", "List: " + mAllCategories);

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
                   shareModel.insert(budget);
                   BudgetFragment fragment = new BudgetFragment();
                   getFragmentManager().beginTransaction().replace(R.id.frag_container, fragment)
                           .commit();
               } else {
                   Toast.makeText(getContext(), category + " already exists in Budget", Toast.LENGTH_LONG).show();
               }
            }
        });
    }

}
