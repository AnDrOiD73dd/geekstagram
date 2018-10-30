package ru.android73.geekstagram.mvp.model.repo;

public enum DataType {
    LOCAL(0),
    REMOTE(1);

    private int code;

    DataType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
