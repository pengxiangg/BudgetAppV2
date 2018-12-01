package app.company.bulba.com.budgetappv2;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import app.company.bulba.com.budgetappv2.data.Budget;
import app.company.bulba.com.budgetappv2.data.BudgetDao;
import app.company.bulba.com.budgetappv2.data.MonthBudget;
import app.company.bulba.com.budgetappv2.data.MonthBudgetDao;
import app.company.bulba.com.budgetappv2.data.Receipt;
import app.company.bulba.com.budgetappv2.data.ReceiptDao;

/**
 * Created by Zachary on 17/11/2018.
 */

@Database(entities = {Receipt.class, Budget.class, MonthBudget.class}, version = 1)
public abstract class ReceiptRoomDatabase extends RoomDatabase{

    public abstract ReceiptDao receiptDao();
    public abstract BudgetDao budgetDao();
    public abstract MonthBudgetDao monthBudgetDao();

    private static volatile ReceiptRoomDatabase INSTANCE;

    static ReceiptRoomDatabase getDatabase(final Context context) {
        if(INSTANCE == null) {
            synchronized (ReceiptRoomDatabase.class) {
                if(INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), ReceiptRoomDatabase.class, "receipt_database").addCallback(sRoomDatabaseCallback).allowMainThreadQueries().build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db){
            super.onOpen(db);
        }
    };

}
