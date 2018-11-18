package app.company.bulba.com.budgetappv2;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

/**
 * Created by Zachary on 17/11/2018.
 */

@Database(entities = {Receipt.class}, version = 1)
public abstract class ReceiptRoomDatabase extends RoomDatabase{

    public abstract ReceiptDao receiptDao();

    private static volatile ReceiptRoomDatabase INSTANCE;

    static ReceiptRoomDatabase getDatabase(final Context context) {
        if(INSTANCE == null) {
            synchronized (ReceiptRoomDatabase.class) {
                if(INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), ReceiptRoomDatabase.class, "receipt_database").addCallback(sRoomDatabaseCallback).build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db){
            super.onOpen(db);
            //new PopulateDbAsync(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final ReceiptDao mDao;

        PopulateDbAsync(ReceiptRoomDatabase db) {
            mDao = db.receiptDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mDao.deleteAll();
            Receipt receipt = new Receipt();
            receipt.setDetails("Burger");
            receipt.setCost(2);
            mDao.insert(receipt);
            return null;
        }
    }


}
