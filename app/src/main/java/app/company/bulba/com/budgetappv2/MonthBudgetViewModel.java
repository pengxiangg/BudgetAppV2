package app.company.bulba.com.budgetappv2;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import app.company.bulba.com.budgetappv2.data.MonthBudget;

/**
 * Created by Zachary on 26/11/2018.
 */

public class MonthBudgetViewModel extends AndroidViewModel {

    private ReceiptRepository mRepository;
    private LiveData<List<MonthBudget>> mAllMonthBudgets;

    public MonthBudgetViewModel(@NonNull Application application) {
        super(application);
        mRepository = new ReceiptRepository(application);
        mAllMonthBudgets = mRepository.getAllMonthBudgets();
    }

    LiveData<List<MonthBudget>> getAllMonthBudgets() { return mAllMonthBudgets; }

    public void insert (MonthBudget monthBudget) {mRepository.insert(monthBudget);}
}
