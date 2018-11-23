package app.company.bulba.com.budgetappv2;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;


/**
 * Created by Zachary on 17/11/2018.
 */

@Entity(tableName = "receipt_table")
public class Receipt {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;

    private String details;
    private int cost;
    private String date;
    private String category;

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getDetails() { return details; }

    public void setDetails(String details) { this.details = details; }

    public int getCost() { return cost; }

    public void setCost(int cost) { this.cost = cost; }

    public String getDate() { return date; }

    public void setDate(String date) { this.date = date; }

    public String getCategory() { return category; }

    public void setCategory(String category) {this.category = category; }
}
