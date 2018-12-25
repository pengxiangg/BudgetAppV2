package app.company.bulba.com.budgetappv2.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.io.StringBufferInputStream;
import java.util.List;

/**
 * Created by Zachary on 17/11/2018.
 */

@Dao
public interface ReceiptDao {

    @Insert
    public void insert(Receipt receipt);

    @Query("DELETE FROM receipt_table")
    void deleteAll();

    @Query("SELECT * FROM receipt_table")
    LiveData<List<Receipt>> getAllReceipts();

    @Query("SELECT SUM(cost) FROM receipt_table")
    LiveData<Integer> getTotalCost();

    @Query("SELECT sum(cost) FROM receipt_table WHERE category = :category AND date LIKE :date")
    int getSumByCatAndDate(String category, String date);

    @Query("SELECT DISTINCT (category || ', ' || SUBSTR(date, INSTR(date, '/') +1, LENGTH(date))) FROM receipt_table")
    List<String> getDistinctCatAndMonthDateReceipt();

    @Delete
    void deleteReceipt(Receipt receipt);
}
