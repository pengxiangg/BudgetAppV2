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

    @Query("UPDATE monthBudget_table SET mhSpent = :mhSpent WHERE mhId = :mhId")
    void updateMhSpent(int mhSpent, int mhId);

    @Query("SELECT mhlimit FROM monthBudget_table WHERE mhId = :mhID")
    int getMhLimit(int mhID);

    @Query("SELECT mhSpent FROM monthBudget_table WHERE mhId = :mhID")
    int getMhSpent(int mhID);

    @Query("UPDATE monthBudget_table SET mhRemainder = :mhRemainder WHERE mhId = :mhId")
    void updateMhRemainder(int mhRemainder, int mhId);

    @Query("UPDATE monthBudget_table SET mhLimit = :mhLimit WHERE mhId = :mhId")
    void updateMhLimit (int mhLimit, int mhId);

    @Query("SELECT * FROM monthBudget_table")
    List<MonthBudget> getAllMonthBudgetsNon();

    @Query("SELECT (mhCategory || ', ' || mhDate) from monthBudget_table")
    List<String> getCatAndMonthDateBudgetM();
}
