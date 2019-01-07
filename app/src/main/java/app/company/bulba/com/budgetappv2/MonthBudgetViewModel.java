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
    LiveData<List<MonthBudget>> mAllMonthBudgetDesc;

    public MonthBudgetViewModel(@NonNull Application application) {
        super(application);
        mRepository = new ReceiptRepository(application);
        mAllMonthBudgets = mRepository.getAllMonthBudgets();
        mAllMonthBudgetDesc = mRepository.getAllMonthBudgetDesc();
    }

    LiveData<List<MonthBudget>> getAllMonthBudgets() { return mAllMonthBudgets; }

    public void insert (MonthBudget monthBudget) {mRepository.insert(monthBudget);}

    List<String> getAllMhCategories() {return mRepository.getAllMhCategories(); }

    List<String> getAllMhDate() { return mRepository.getAllMhDate(); }

    int getMhId(String category, String date) { return mRepository.getMhId(category, date); }

    public void update (MonthBudget monthBudget) {mRepository.update(monthBudget);}

    void updateMhSpent (int spent, int id) {mRepository.updateMhSpent(spent, id);}

    int getMhLimit (int mhId) {return mRepository.getMhLimit(mhId);}

    void updateMhRemainder (int remainder, int id) {mRepository.updateMhRemainder(remainder, id);}

    void updateMhLimit (int limit, int id) {mRepository.updateMhLimit(limit, id);}

    int getMhSpent (int mhId) {return mRepository.getMhSpent(mhId);}

    List<String> getCatAndMonthDateBudgetM() {return mRepository.getCatAndMonthDateBudgetM();}

    LiveData<List<MonthBudget>> getAllMonthBudgetDesc() {return mAllMonthBudgetDesc;}

    public void delete(MonthBudget monthBudget) {mRepository.deleteMonthBudget(monthBudget);}

    MonthBudget getMonthBudget (int mhId) {return mRepository.getMonthBudget(mhId);}
}
