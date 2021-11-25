package dev.yhp.study.last_bbs.interfaces;

public interface IResult<T> {
    T getResult();

    String getResultName();

    void setResult(T t);
}