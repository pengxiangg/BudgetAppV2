package app.company.bulba.com.budgetappv2;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import java.time.Month;
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
    private List<MonthBudget> mAllMonthBudgetsNon;

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

    LiveData<Double> getTotalCost() {
        return mReceiptDao.getTotalCost();
    }

    double getSumByCatAndDate(String category, String date) {return mReceiptDao.getSumByCatAndDate(category, date); }

    public void insert(Receipt receipt) {
        new insertAsyncTask(mReceiptDao).execute(receipt);
    }

    public void insert(Budget budget) { new insertBudgetAsyncTask(mBudgetDao).execute(budget); }

    public void insert (MonthBudget monthBudget) { new insertMonthBudgetAsyncTask(mMonthBudgetDao).execute(monthBudget); }

    void update (MonthBudget monthBudget) { new updateMonthBudgetAsyncTask(mMonthBudgetDao).execute(monthBudget); }

    public void deleteReceipt(Receipt receipt) { new deleteReceiptAsyncTask(mReceiptDao).execute(receipt);}

    public void deleteBudget(Budget budget) {new deleteBudgetAsyncTask(mBudgetDao).execute(budget);}

    public void deleteMonthBudget(MonthBudget monthBudget) {new deleteMonthBudgetAsyncTask(mMonthBudgetDao).execute(monthBudget);}

    void updateMhSpent (double spent, int id) { mMonthBudgetDao.updateMhSpent(spent, id);}

    List<String> getAllCategories() { return mBudgetDao.getAllCategories(); }

    List<String> getAllMhCategories() { return mMonthBudgetDao.getAllMhCategories(); }

    List<String> getAllMhDate() {return mMonthBudgetDao.getAllMhDate();}

    int getMhId(String mhCategory, String mhDate) { return mMonthBudgetDao.getMhId(mhCategory, mhDate);}

    double getMhLimit(int mhID) {return mMonthBudgetDao.getMhLimit(mhID);}

    void updateMhRemainder (double remainder, int id) {mMonthBudgetDao.updateMhRemainder(remainder, id);}

    void updateMhLimit (double limit, int id) {mMonthBudgetDao.updateMhLimit(limit, id);}

    double getMhSpent(int mhID) {return mMonthBudgetDao.getMhSpent(mhID);}

    List<String> getDistinctCatAndMonthDateReceipt() {return mReceiptDao.getDistinctCatAndMonthDateReceipt(); }

    List<String> getCatAndMonthDateBudgetM() {return mMonthBudgetDao.getCatAndMonthDateBudgetM();}

    LiveData<List<Double>> getAllLimitBudget() {return mBudgetDao.getAllLimitBudget();}

    double getLimitCatBudget(String budgetCategory) {return mBudgetDao.getLimitCatBudget(budgetCategory);}

    LiveData<List<Receipt>> getAllReceiptsDesc() {return mReceiptDao.getAllReceiptsDesc();}

    LiveData<List<MonthBudget>> getAllMonthBudgetDesc() {return mMonthBudgetDao.getAllMonthBudgetDesc();}

    MonthBudget getMonthBudget(int id) {return  mMonthBudgetDao.getMonthBudget(id);}

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

    private static class updateMonthBudgetAsyncTask extends AsyncTask<MonthBudget, Void, Void> {
        private MonthBudgetDao mMonthBudgetAsyncTaskDao;

        updateMonthBudgetAsyncTask(MonthBudgetDao dao) {mMonthBudgetAsyncTaskDao = dao;}

        @Override
        protected Void doInBackground (MonthBudget... monthBudgets) {
            mMonthBudgetAsyncTaskDao.updateMonthBudget(monthBudgets[0]);
            return null;
        }
    }

    private static class deleteReceiptAsyncTask extends AsyncTask<Receipt, Void, Void> {
        private ReceiptDao mAsyncTaskDao;

        deleteReceiptAsyncTask(ReceiptDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Receipt... params) {
            mAsyncTaskDao.deleteReceipt(params[0]);
            return null;
        }
    }

    private static class deleteBudgetAsyncTask extends AsyncTask<Budget, Void, Void> {
        private BudgetDao mAsyncTaskDao;

        deleteBudgetAsyncTask(BudgetDao dao) {mAsyncTaskDao = dao;}

        @Override
        protected Void doInBackground(final Budget... params) {
            mAsyncTaskDao.deleteBudget(params[0]);
            return null;
        }
    }

    private static class deleteMonthBudgetAsyncTask extends AsyncTask<MonthBudget, Void, Void> {
        private MonthBudgetDao mAsyncTaskDao;

        deleteMonthBudgetAsyncTask(MonthBudgetDao dao) {mAsyncTaskDao = dao;}

        @Override
        protected Void doInBackground(final MonthBudget... params) {
            mAsyncTaskDao.deleteMonthBudget(params[0]);
            return null;
        }
    }

}
