package com.mad.triviaapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.mad.triviaapp.model.History;
import com.mad.triviaapp.repository.HistoryRepository;

import java.util.List;

public class HistoryViewModel extends AndroidViewModel {

    private HistoryRepository repository;
    private LiveData<List<History>> historyList;

    public HistoryViewModel(@NonNull Application application) {
        super(application);
        repository = new HistoryRepository(application);
        historyList = repository.getHistoryList();
    }

    public void insert(History history) {
        repository.insert(history);
    }

    public void update(History history) {
        repository.update(history);
    }

    public void delete(History history) {
        repository.delete(history);
    }

    public void deleteAllHistory() {
        repository.deleteAllHistory();
    }

    public LiveData<List<History>> getHistoryList() {
        return historyList;
    }
}
