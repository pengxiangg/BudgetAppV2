package app.company.bulba.com.budgetappv2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import app.company.bulba.com.budgetappv2.data.MonthBudget;

/**
 * Created by Zachary on 26/11/2018.
 */

public class MonthBudgetListAdapter extends RecyclerView.Adapter<MonthBudgetListAdapter.MonthBudgetViewHolder> {


    class MonthBudgetViewHolder extends RecyclerView.ViewHolder {
        private final TextView mhDateItemView;
        private final TextView mhCategoryItemView;
        private final TextView mhLimitItemView;
        private final TextView mhSpentItemView;
        private final TextView mhRemainderItemView;

        public MonthBudgetViewHolder(View itemView) {
            super(itemView);
            mhDateItemView = itemView.findViewById(R.id.mh_date_textview);
            mhCategoryItemView = itemView.findViewById(R.id.mh_category_textview);
            mhLimitItemView = itemView.findViewById(R.id.mh_limit_textview);
            mhSpentItemView = itemView.findViewById(R.id.mh_spent_textview);
            mhRemainderItemView = itemView.findViewById(R.id.mh_remainder_textview);
        }
    }

    private final LayoutInflater mMonthBudgetInflater;
    private List<MonthBudget> mMonthBudget;

    MonthBudgetListAdapter(Context context) { mMonthBudgetInflater = LayoutInflater.from(context); }


    @Override
    public MonthBudgetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mMonthBudgetInflater.inflate(R.layout.recyclerview_month_budget, parent, false);
        return new MonthBudgetViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MonthBudgetViewHolder holder, int position) {
        if(mMonthBudget != null) {
            MonthBudget current = mMonthBudget.get(position);
            holder.mhDateItemView.setText(current.getMhDate());
            holder.mhCategoryItemView.setText(current.getMhCategory());
            holder.mhLimitItemView.setText(Integer.toString(current.getMhlimit()));
            holder.mhSpentItemView.setText(Integer.toString(current.getMhSpent()));
            holder.mhRemainderItemView.setText(Integer.toString(current.getMhRemainder()));
        } else {
            holder.mhDateItemView.setText("No Date");
            holder.mhCategoryItemView.setText("No Category");
            holder.mhLimitItemView.setText("No Limit");
            holder.mhSpentItemView.setText("No Spendings");
            holder.mhRemainderItemView.setText("No Remainder");
        }

    }

    void setMonthBudgets(List<MonthBudget> monthBudgets) {
        mMonthBudget = monthBudgets;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(mMonthBudget != null)
            return mMonthBudget.size();
        else return 0;
    }


}
