package app.company.bulba.com.budgetappv2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Zachary on 23/11/2018.
 */

public class AddBudgetFragment extends Fragment {

    private EditText categoryET;
    private EditText limitET;

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

        Button button = getView().findViewById(R.id.button_budget_submit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String category = categoryET.getText().toString();
                String limitString = limitET.getText().toString();
                int limitInt = Integer.parseInt(limitString);

                Log.v("TAG: BUDGETFRAGMENT", "Category: " + category);
                Log.v("TAG: BUDGETFRAGMENT", "Limit: " + limitInt);
            }
        });
    }
}
