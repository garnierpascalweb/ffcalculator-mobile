package com.gpwsofts.ffcalculator.mobile.common;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.gpwsofts.ffcalculator.mobile.common.log.LogUtils;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * https://stackoverflow.com/questions/59840883/when-i-go-back-to-a-fragment-the-observer-is-immediately-called
 * @since 1.0.0
 * @param <T>
 */
public class SingleLiveEvent<T> extends MutableLiveData<T> {

    private static final String TAG_NAME = "SingleLiveEvent";

    private final AtomicBoolean mPending = new AtomicBoolean(false);

    @Override
    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super T> observer) {

        if (hasActiveObservers()) {
            LogUtils.w(TAG_NAME, "Multiple observers registered but only one will be notified of changes.");
        }

        // Observe the internal MutableLiveData
        super.observe(owner, t -> {
            if (mPending.compareAndSet(true, false)) {
                observer.onChanged(t);
            }
        });
    }

    @MainThread
    public void setValue(@Nullable T t) {
        mPending.set(true);
        super.setValue(t);
    }
}