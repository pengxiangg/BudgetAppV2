package app.company.bulba.com.budgetappv2;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by Zachary on 23/11/2018.
 */

@Dao
public interface BudgetDao {

    @Insert
    public void insert(Budget budget);

    @Query("SELECT * FROM budget_table")
    LiveData<List<Budget>> getAllBudgets();
}
