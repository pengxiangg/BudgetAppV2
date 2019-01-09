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
    private String budgetCategory;

    private double limit;

    public String getBudgetCategory() {return budgetCategory;}
    public void setBudgetCategory(String budgetCategory) {this.budgetCategory = budgetCategory; }

    public double getLimit() {return limit; }
    public void setLimit(double limit) {this.limit = limit; }

}
