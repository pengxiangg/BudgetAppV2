package app.company.bulba.com.budgetappv2.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by Zachary on 26/11/2018.
 */

@Dao
public interface MonthBudgetDao {

    @Insert
    public void insert(MonthBudget monthBudget);

    @Query("SELECT * FROM monthBudget_table")
    LiveData<List<MonthBudget>> getAllMonthBudgets();
}
