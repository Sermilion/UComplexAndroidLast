package org.ucomplex.ucomplex.Modules.Events;

import org.ucomplex.ucomplex.Common.base.AbstractPresenter;
import org.ucomplex.ucomplex.Common.base.RecyclerFragment;
import org.ucomplex.ucomplex.Common.base.UCApplication;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 24/03/2017.
 * Project: UComplex
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">www.ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public class EventsPresenter extends AbstractPresenter<
        List<EventItem>, List<EventItem>,
        EventsParams, EventsModel> {

    @Inject
    public void setModel(EventsModel model) {
        mModel = model;
    }

    public EventsPresenter() {
        mRequestParams = new EventsParams();
        UCApplication.getInstance().getAppDiComponent().inject(this);
    }

    @Override
    public void loadData() {
        Observable<List<EventItem>> dataObservable = mModel.loadData(mRequestParams);
        dataObservable.subscribe(new Observer<List<EventItem>>() {
            @Override
            public void onSubscribe(Disposable d) {
                showProgress();
            }

            @Override
            public void onNext(List<EventItem> value) {
                mModel.addData(value);
                if(getView()!=null){
                    ((RecyclerFragment)getView()).receiveNewData(value);
                }
            }

            @Override
            public void onError(Throwable e) {
                hideProgress();
            }

            @Override
            public void onComplete() {
                hideProgress();
            }
        });
    }
}