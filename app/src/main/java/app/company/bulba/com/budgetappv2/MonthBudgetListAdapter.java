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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

            String MonthString = "";

            SimpleDateFormat yearSdf = new SimpleDateFormat("yyyy/MM");
            SimpleDateFormat MonthSdf = new SimpleDateFormat("MMM yyyy");

            try {
                Date date = yearSdf.parse(current.getMhDate());
                MonthString = MonthSdf.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            holder.mhDateItemView.setText(MonthString);

            String category = current.getMhCategory();
            holder.mhCategoryItemView.setText(category.substring(0,1).toUpperCase()+category.substring(1).toLowerCase());
            DecimalFormat df = new DecimalFormat("#0.00");

            double limit = current.getMhlimit();
            if(limit!=0) {
                holder.mhLimitItemView.setText("$"+df.format(current.getMhlimit()));
            } else {
                holder.mhLimitItemView.setText("-");
            }

            double spent = current.getMhSpent();
            if(spent!=0) {
                holder.mhSpentItemView.setText("$"+df.format(current.getMhSpent()));
            } else {
                holder.mhSpentItemView.setText("-");
            }

            double remainder = current.getMhRemainder();
            if(remainder!=0) {
                holder.mhRemainderItemView.setText("$"+df.format(current.getMhRemainder()));
            } else {
                holder.mhRemainderItemView.setText("-");
            }

            if(position > 0 && current.getMhDate().equals(mMonthBudget.get(position-1).getMhDate())) {
                holder.mhDateItemView.setVisibility(View.GONE);
            } else {
                holder.mhDateItemView.setVisibility(View.VISIBLE);
            }

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

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }


}
