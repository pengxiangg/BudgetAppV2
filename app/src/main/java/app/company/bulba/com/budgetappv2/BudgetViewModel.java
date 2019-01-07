package app.company.bulba.com.budgetappv2;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.Bundle;
import android.support.annotation.NonNull;

import java.util.List;

import app.company.bulba.com.budgetappv2.data.Budget;

/**
 * Created by Zachary on 23/11/2018.
 */

public class BudgetViewModel extends AndroidViewModel {

    private ReceiptRepository mRepository;
    private LiveData<List<Budget>> mAllBudgets;

    public BudgetViewModel (@NonNull Application application) {
        super(application);
        mRepository = new ReceiptRepository(application);
        mAllBudgets = mRepository.getAllBudgets();
    }

    LiveData<List<Budget>> getAllBudgets() {return mAllBudgets; }

    public void insert(Budget budget) {mRepository.insert(budget);}

    List<String> getAllCategories() { return mRepository.getAllCategories(); }

    LiveData<List<Integer>> getAllLimitBudget() {return mRepository.getAllLimitBudget();}

    int getLimitCatBudget(String budgetCategory) {return mRepository.getLimitCatBudget(budgetCategory);}

    public void delete(Budget budget) {mRepository.deleteBudget(budget);}

}
