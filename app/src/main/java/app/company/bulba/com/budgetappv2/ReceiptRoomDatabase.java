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
import app.company.bulba.com.budgetappv2.data.Receipt;
import app.company.bulba.com.budgetappv2.data.ReceiptDao;

/**
 * Created by Zachary on 17/11/2018.
 */

@Database(entities = {Receipt.class, Budget.class}, version = 1)
public abstract class ReceiptRoomDatabase extends RoomDatabase{

    public abstract ReceiptDao receiptDao();
    public abstract BudgetDao budgetDao();

    private static volatile ReceiptRoomDatabase INSTANCE;

    static ReceiptRoomDatabase getDatabase(final Context context) {
        if(INSTANCE == null) {
            synchronized (ReceiptRoomDatabase.class) {
                if(INSTANCE == null) {
                    INSTANCE = Room.inMemoryDatabaseBuilder(context.getApplicationContext(), ReceiptRoomDatabase.class).addCallback(sRoomDatabaseCallback).build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db){
            super.onOpen(db);
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final BudgetDao mDao;

        PopulateDbAsync(ReceiptRoomDatabase db) {
            mDao = db.budgetDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mDao.deleteAll();
            Budget word = new Budget();
            word.setCategory("Testing3");
            word.setLimit(2);
            word.setSpent(3);
            word.setRemainder(4);
            mDao.insert(word);
            return null;
        }
    }



}
