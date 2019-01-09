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

import app.company.bulba.com.budgetappv2.data.Receipt;

/**
 * Created by Zachary on 17/11/2018.
 */

public class ReceiptListAdapter extends RecyclerView.Adapter<ReceiptListAdapter.ReceiptViewHolder> {

    class ReceiptViewHolder extends RecyclerView.ViewHolder {
        private final TextView detailsItemView;
        private final TextView costItemView;
        private final TextView dateItemView;
        private final TextView categoryItemView;

        private ReceiptViewHolder(View itemView) {
            super(itemView);
            detailsItemView = itemView.findViewById(R.id.details_textView);
            costItemView = itemView.findViewById(R.id.cost_textView);
            dateItemView = itemView.findViewById(R.id.date_textView);
            categoryItemView = itemView.findViewById(R.id.category_textView);
        }
    }

    private final LayoutInflater mInflater;
    private List<Receipt> mReceipts;

    ReceiptListAdapter(Context context) {mInflater = LayoutInflater.from(context);}

    @Override
    public ReceiptViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new ReceiptViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ReceiptViewHolder holder, int position) {
        if(mReceipts != null) {

            Receipt current = mReceipts.get(position);
            String dayString = "";

            SimpleDateFormat yearSdf = new SimpleDateFormat("yyyy/MM/dd");
            SimpleDateFormat daySdf = new SimpleDateFormat("dd MMM yy");

            try {
                Date date = yearSdf.parse(current.getDate());
                dayString = daySdf.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            holder.detailsItemView.setText(current.getDetails());
            DecimalFormat df = new DecimalFormat("#0.00");
            holder.costItemView.setText("$" + df.format(current.getCost()));
            holder.dateItemView.setText(dayString);
            String category = current.getCategory();
            holder.categoryItemView.setText(category.substring(0,1).toUpperCase()+category.substring(1).toLowerCase());

            //Removes view displaying date if same date with view above
            /*
            if(position > 0 && current.getDate().equals(mReceipts.get(position-1).getDate())) {
                holder.dateItemView.setVisibility(View.GONE);
            } else {
                holder.dateItemView.setVisibility(View.VISIBLE);
            }*/

        } else {
            holder.detailsItemView.setText("No Details");
            holder.costItemView.setText("No Cost");
            holder.dateItemView.setText("No Date");
            holder.categoryItemView.setText("No Category");
        }

    }

    void setReceipts(List<Receipt> receipts) {
        mReceipts = receipts;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(mReceipts!= null)
            return mReceipts.size();
        else return 0;
    }

    public Receipt getReceiptAtPosition (int position) {
        return mReceipts.get(position);
    }

}
