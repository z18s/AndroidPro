package com.example.translatorapp.model.data;

import java.util.List;

public class DataModel {

    DataStatus status;

    public DataStatus getStatus() {
        return status;
    }

    public static class Success extends DataModel {
        private List<SearchResult> data;

        public Success(List<SearchResult> data) {
            super.status = DataStatus.SUCCESS;
            this.data = data;
        }

        public List<SearchResult> getData() {
            return data;
        }

        public void setData(List<SearchResult> data) {
            this.data = data;
        }
    }

    public static class Error extends DataModel {
        private Throwable error;

        public Error(Throwable error) {
            super.status = DataStatus.ERROR;
            this.error = error;
        }

        public Throwable getError() {
            return error;
        }

        public void setError(Throwable error) {
            this.error = error;
        }
    }

    public static class Loading extends DataModel {
        private int progress;

        public Loading(int progress) {
            super.status = DataStatus.LOADING;
            this.progress = progress;
        }

        public int getProgress() {
            return progress;
        }

        public void setProgress(int progress) {
            this.progress = progress;
        }
    }
}