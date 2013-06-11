package com.mirantis.bigdatacourse.dao;

public class NasException extends DaoException {
    public NasException(Exception e) {
        super(e);
    }

    public NasException (String msg) {
        super(msg);
    }

    private static final long serialVersionUID = 1L;
}
