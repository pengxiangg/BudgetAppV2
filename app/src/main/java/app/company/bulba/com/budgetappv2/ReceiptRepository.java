package app.company.bulba.com.budgetappv2;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import app.company.bulba.com.budgetappv2.data.Budget;
import app.company.bulba.com.budgetappv2.data.BudgetDao;
import app.company.bulba.com.budgetappv2.data.MonthBudget;
import app.company.bulba.com.budgetappv2.data.MonthBudgetDao;
import app.company.bulba.com.budgetappv2.data.Receipt;
import app.company.bulba.com.budgetappv2.data.ReceiptDao;

/**
 * Created by Zachary on 17/11/2018.
 */

public class ReceiptRepository {
    private ReceiptDao mReceiptDao;
    private BudgetDao mBudgetDao;
    private MonthBudgetDao mMonthBudgetDao;
    private LiveData<List<Receipt>> mAllReceipts;
    private LiveData<List<Budget>> mAllBudgets;
    private LiveData<List<MonthBudget>> mAllMonthBudgets;

    ReceiptRepository(Application application) {
        ReceiptRoomDatabase db = ReceiptRoomDatabase.getDatabase(application);
        mReceiptDao = db.receiptDao();
        mBudgetDao = db.budgetDao();
        mMonthBudgetDao = db.monthBudgetDao();
        mAllReceipts = mReceiptDao.getAllReceipts();
        mAllBudgets = mBudgetDao.getAllBudgets();
        mAllMonthBudgets = mMonthBudgetDao.getAllMonthBudgets();
    }

    LiveData<List<Receipt>> getAllReceipts() {
        return mAllReceipts;
    }

    LiveData<List<Budget>> getAllBudgets() { return mAllBudgets; }

    LiveData<List<MonthBudget>> getAllMonthBudgets() { return mAllMonthBudgets; }

    LiveData<Integer> getTotalCost() {
        return mReceiptDao.getTotalCost();
    }

    public void insert(Receipt receipt) {
        new insertAsyncTask(mReceiptDao).execute(receipt);
    }

    public void insert(Budget budget) { new insertBudgetAsyncTask(mBudgetDao).execute(budget); }

    public void insert (MonthBudget monthBudget) { new insertMonthBudgetAsyncTask(mMonthBudgetDao).execute(monthBudget); }

    LiveData<List<String>> getAllCategories() { return mBudgetDao.getAllCategories(); }

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
    private static class insertBudgetAsyncTask extends AsyncTask<Budget, Void, Void> {

        private BudgetDao mBudgetAsyncTaskDao;

        insertBudgetAsyncTask(BudgetDao dao) {mBudgetAsyncTaskDao = dao;}

        @Override
        protected Void doInBackground(Budget... budgets) {
            mBudgetAsyncTaskDao.insert(budgets[0]);
            return null;
        }
    }

    private static class insertMonthBudgetAsyncTask extends AsyncTask<MonthBudget, Void, Void> {

        private MonthBudgetDao mMonthBudgetAsyncTaskDao;

        insertMonthBudgetAsyncTask(MonthBudgetDao dao) {mMonthBudgetAsyncTaskDao = dao;}

        @Override
        protected Void doInBackground(MonthBudget... monthBudgets) {
            mMonthBudgetAsyncTaskDao.insert(monthBudgets[0]);
            return null;
        }
    }
}
