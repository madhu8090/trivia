package com.mad.triviaapp.repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.mad.triviaapp.dao.HistoryDao;
import com.mad.triviaapp.database.HistoryDataBase;
import com.mad.triviaapp.model.History;

import java.util.List;

public class HistoryRepository {

    private HistoryDao historyDao;
    private LiveData<List<History>> historyList;

    public HistoryRepository(Application application) {
        HistoryDataBase database = HistoryDataBase.getInstance(application);
        historyDao = database.historyDao();
        historyList = historyDao.getAllHistory();
    }

    public void insert(History history) {
        new InsertHistoryAsyncTask(historyDao).execute(history);
    }

    public void update(History history) {
        new UpdateHistoryAsyncTask(historyDao).execute(history);
    }

    public void delete(History history) {
        new DeleteHistoryAsyncTask(historyDao).execute(history);
    }

    public void deleteAllHistory() {
        new DeleteAllHistoryAsyncTask(historyDao).execute();
    }

    public LiveData<List<History>> getHistoryList() {
        return historyList;
    }

    private static class InsertHistoryAsyncTask extends AsyncTask<History, Void, Void> {
        private HistoryDao historyDao;

        private InsertHistoryAsyncTask(HistoryDao historyDao) {
            this.historyDao = historyDao;
        }

        @Override
        protected Void doInBackground(History... histories) {
            historyDao.insertHistory(histories[0]);
            return null;
        }
    }

    private static class UpdateHistoryAsyncTask extends AsyncTask<History, Void, Void> {
        private HistoryDao historyDao;

        private UpdateHistoryAsyncTask(HistoryDao historyDao) {
            this.historyDao = historyDao;
        }

        @Override
        protected Void doInBackground(History... histories) {
            historyDao.updateHistory(histories[0]);
            return null;
        }
    }

    private static class DeleteHistoryAsyncTask extends AsyncTask<History, Void, Void> {
        private HistoryDao historyDao;

        private DeleteHistoryAsyncTask(HistoryDao historyDao) {
            this.historyDao = historyDao;
        }

        @Override
        protected Void doInBackground(History... histories) {
            historyDao.deleteHistory(histories[0]);
            return null;
        }
    }

    private static class DeleteAllHistoryAsyncTask extends AsyncTask<Void, Void, Void> {
        private HistoryDao historyDao;

        private DeleteAllHistoryAsyncTask(HistoryDao historyDao) {
            this.historyDao = historyDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            historyDao.deleteAllHistory();
            return null;
        }
    }
}
