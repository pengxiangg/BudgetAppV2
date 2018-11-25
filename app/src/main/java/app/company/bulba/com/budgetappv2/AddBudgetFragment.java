package app.company.bulba.com.budgetappv2;

import android.arch.lifecycle.LiveData;
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

/**
 * Created by Zachary on 23/11/2018.
 */

public class AddBudgetFragment extends Fragment {

    private EditText categoryET;
    private EditText limitET;
    private BudgetViewModel shareModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_budget_add, parent, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        categoryET = getView().findViewById(R.id.category_input_edit_text);
        limitET = getView().findViewById(R.id.limit_edit_text);
        shareModel = ViewModelProviders.of(getActivity()).get(BudgetViewModel.class);

        Button button = getView().findViewById(R.id.button_budget_submit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String category = categoryET.getText().toString();
                String limitString = limitET.getText().toString();
                int limitInt = Integer.parseInt(limitString);

               if(category.length() == 0 || limitString.length() == 0) {
                   Toast.makeText(getContext(), "Please make sure all details are correct", Toast.LENGTH_LONG).show();
                   return;
               }

               /*Bundle bundle = new Bundle();
               bundle.putString("CATEGORY_BUDGET_KEY", category);
               bundle.putInt("LIMIT_KEY", limitInt); */

               shareModel.transfer(category);
               Log.v("TAG: NO", "Cat: " + category);

               BudgetFragment fragment = new BudgetFragment();
               getFragmentManager().beginTransaction().replace(R.id.frag_container, fragment)
                       .commit();
            }
        });
    }
}
