package app.company.bulba.com.budgetappv2;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

/**
 * Created by Zachary on 17/11/2018.
 */

public class ReceiptRepository {
    private ReceiptDao mReceiptDao;
    private LiveData<List<Receipt>> mAllReceipts;

    ReceiptRepository(Application application) {
        ReceiptRoomDatabase db = ReceiptRoomDatabase.getDatabase(application);
        mReceiptDao = db.receiptDao();
        mAllReceipts = mReceiptDao.getAllReceipts();
        //mTotalCost = mReceiptDao.getTotalCost();
    }

    LiveData<List<Receipt>> getAllReceipts() {
        return mAllReceipts;
    }

    LiveData<Integer> getTotalCost() {
        return mReceiptDao.getTotalCost();
    }

    public void insert(Receipt receipt) {
        new insertAsyncTask(mReceiptDao).execute(receipt);
    }

    private static class insertAsyncTask extends AsyncTask<Receipt, Void, Void> {

        private ReceiptDao mAsyncTaskDao;

        insertAsyncTask(ReceiptDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Receipt... receipts) {
            mAsyncTaskDao.insert(receipts[0]);
            return null;
        }
    }
}
