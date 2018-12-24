package app.company.bulba.com.budgetappv2.data;

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

    @Query("DELETE FROM budget_table")
    void deleteAll();

    @Insert
    public void insert(Budget budget);

    @Query("SELECT * FROM budget_table")
    LiveData<List<Budget>> getAllBudgets();

    @Query("SELECT budgetCategory FROM budget_table")
    List<String> getAllCategories();

    @Query("SELECT `limit` FROM budget_table")
    LiveData<List<Integer>> getAllLimitBudget();

    @Query("SELECT `limit` FROM budget_table WHERE budgetCategory = :budgetCategory")
    int getLimitCatBudget(String budgetCategory);
}
