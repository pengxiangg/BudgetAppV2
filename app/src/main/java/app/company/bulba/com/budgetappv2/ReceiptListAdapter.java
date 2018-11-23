package app.company.bulba.com.budgetappv2;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

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
            holder.detailsItemView.setText(current.getDetails());
            holder.costItemView.setText(Integer.toString(current.getCost()));
            holder.dateItemView.setText(current.getDate());
            holder.categoryItemView.setText(current.getCategory());
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
}
