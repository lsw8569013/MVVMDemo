package com.ironant.common.netutil;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 * Created by lsw on 2017/2/22.
 */

public  abstract class BaseObserver<T> implements Observer<T> {


    public BaseObserver(){
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onSubscribe(Disposable d) {


    }

    @Override
    public void onNext(T t) {

        onSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
        onFail(e);
    }

    public abstract void onFail(Throwable e);

    public abstract void onSuccess(T t);

}
