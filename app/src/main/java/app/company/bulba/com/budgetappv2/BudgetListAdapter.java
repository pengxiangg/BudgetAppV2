package app.company.bulba.com.budgetappv2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;

import app.company.bulba.com.budgetappv2.data.Budget;


/**
 * Created by Zachary on 24/11/2018.
 */

public class BudgetListAdapter extends RecyclerView.Adapter<BudgetListAdapter.BudgetViewHolder> {


    class BudgetViewHolder extends RecyclerView.ViewHolder {
        private final TextView categoryItemView;
        private final TextView limitItemView;

        private BudgetViewHolder (View itemView) {
            super(itemView);
            categoryItemView = itemView.findViewById(R.id.budget_category_textView);
            limitItemView = itemView.findViewById(R.id.budget_limit_textView);
        }

    }

    private final LayoutInflater mBudgetInflater;
    private List<Budget> mBudget;

    BudgetListAdapter(Context context) {mBudgetInflater = LayoutInflater.from(context);}


    @Override
    public BudgetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mBudgetInflater.inflate(R.layout.recyclerview_budget, parent, false);
        return new BudgetViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BudgetViewHolder holder, int position) {

        if(mBudget != null) {
            Budget current = mBudget.get(position);
            String category = current.getBudgetCategory();
            holder.categoryItemView.setText(category.substring(0,1).toUpperCase()+category.substring(1).toLowerCase());
            DecimalFormat df = new DecimalFormat("#0.00");
            holder.limitItemView.setText("$"+df.format(current.getLimit()));
        } else {
            holder.categoryItemView.setText("No Category");
            holder.limitItemView.setText("No Limit");
        }

    }

    void setBudgets(List<Budget> budgets) {
        mBudget = budgets;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(mBudget != null)
            return mBudget.size();
        else return 0;
    }

    public Budget getBudgetAtPosition (int position) {
        return mBudget.get(position);
    }

}
