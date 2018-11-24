package app.company.bulba.com.budgetappv2.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by Zachary on 23/11/2018.
 */

@Entity(tableName = "budget_table")
public class Budget {

    @PrimaryKey
    @NonNull
    private String category;

    private int limit;

    public String getCategory() {return category;}
    public void setCategory(String category) {this.category = category; }

    public int getLimit() {return limit; }
    public void setLimit(int limit) {this.limit = limit; }
}
