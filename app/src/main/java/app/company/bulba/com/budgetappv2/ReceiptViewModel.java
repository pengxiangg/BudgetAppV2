package app.company.bulba.com.budgetappv2;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

/**
 * Created by Zachary on 17/11/2018.
 */

public class ReceiptViewModel extends AndroidViewModel {

    private ReceiptRepository mRepository;
    private LiveData<List<Receipt>> mAllReceipts;

    public ReceiptViewModel(@NonNull Application application) {
        super(application);
        mRepository = new ReceiptRepository(application);
        mAllReceipts = mRepository.getAllReceipts();
    }

    LiveData<List<Receipt>> getAllReceipts() {return mAllReceipts; }

    public void insert(Receipt receipt) { mRepository.insert(receipt);}

    LiveData<Integer> getTotalCost() { return mRepository.getTotalCost(); }
}
