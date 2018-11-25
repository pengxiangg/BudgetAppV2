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
    private final MutableLiveData<String> selected = new MutableLiveData<String>();

    public BudgetViewModel (@NonNull Application application) {
        super(application);
        mRepository = new ReceiptRepository(application);
        mAllBudgets = mRepository.getAllBudgets();
    }

    LiveData<List<Budget>> getAllBudgets() {return mAllBudgets; }

    public void insert(Budget budget) {mRepository.insert(budget);}

    public void transfer(String string) {
        selected.setValue(string);
    }

    public LiveData<String> getStringo() {
        return selected;
    }
}
