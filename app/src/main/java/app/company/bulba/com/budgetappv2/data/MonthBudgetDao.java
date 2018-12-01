package app.company.bulba.com.budgetappv2.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by Zachary on 26/11/2018.
 */

@Dao
public interface MonthBudgetDao {

    @Insert
    public void insert(MonthBudget monthBudget);

    @Update (onConflict = REPLACE)
    void updateMonthBudget(MonthBudget monthBudget);

    @Query("SELECT * FROM monthBudget_table")
    LiveData<List<MonthBudget>> getAllMonthBudgets();

    @Query("SELECT mhCategory FROM monthBudget_table")
    LiveData<List<String>> getAllMhCategories();

    @Query("SELECT mhDate FROM monthBudget_table")
    LiveData<List<String>> getAllMhDate();

    @Query("SELECT mhId FROM monthBudget_table WHERE mhCategory = :mhCategory AND mhDate = :mhDate")
    int getMhId(String mhCategory, String mhDate);
}
