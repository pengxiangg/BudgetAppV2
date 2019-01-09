package app.company.bulba.com.budgetappv2.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by Zachary on 26/11/2018.
 */

@Entity(tableName = "monthBudget_table")
public class MonthBudget {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int mhId;

    private String mhDate;

    private String mhCategory;

    private double mhlimit;

    private double mhSpent;

    private double mhRemainder;

    public int getMhId() { return mhId; }
    public void setMhId(int mhId) { this.mhId = mhId; }

    public String getMhDate() { return mhDate; }
    public void setMhDate(String mhDate) { this.mhDate = mhDate; }

    public String getMhCategory() { return mhCategory; }
    public void setMhCategory(String mhCategory) { this.mhCategory = mhCategory; }

    public double getMhlimit() { return mhlimit; }
    public void setMhlimit(double mhlimit) { this.mhlimit = mhlimit; }

    public double getMhSpent() { return mhSpent; }
    public void setMhSpent(double mhSpent) { this.mhSpent = mhSpent; }

    public double getMhRemainder() { return mhRemainder; }
    public void setMhRemainder(double mhRemainder) { this.mhRemainder = mhRemainder; }

}
