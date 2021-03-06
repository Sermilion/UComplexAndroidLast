package org.ucomplex.ucomplex.Modules.SubjectsList;

import org.ucomplex.ucomplex.Common.base.UCApplication;
import org.ucomplex.ucomplex.Common.interfaces.mvp.MVPModel;
import org.ucomplex.ucomplex.Modules.SubjectsList.model.StudentSubjectList;
import org.ucomplex.ucomplex.Modules.SubjectsList.model.SubjectsListItem;
import org.ucomplex.ucomplex.Modules.SubjectsList.model.SubjectsListRaw;
import org.ucomplex.ucomplex.R;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 04/04/2017.
 * Project: UComplex
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">www.ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public class SubjectsListModel implements MVPModel<SubjectsListRaw, List<SubjectsListItem>, Void> {

    private final int[] assessmentType = {R.string.zachet, R.string.exam, R.string.samostoyatelnaya, R.string.empty};
    private List<SubjectsListItem> mData;
    private SubjectsListService mService;

    private SubjectsListModel() {

    }

    private void inject() {
        UCApplication.getInstance().getAppDiComponent().inject(this);
    }

    public static SubjectsListModel getInstance() {
        SubjectsListModel model = new SubjectsListModel();
        model.inject();
        return model;
    }

    public static SubjectsListModel getTestInstance() {
        return new SubjectsListModel();
    }

    @Inject
    public void setSubjectsListService(SubjectsListService service) {
        this.mService = service;
    }

    @Override
    public Observable<SubjectsListRaw> loadData(Void params) {
        return mService.getSubjectList().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void setData(List<SubjectsListItem> data) {
        mData = data;
    }

    @Override
    public void addData(List<SubjectsListItem> data) {
        mData.addAll(data);
    }

    @Override
    public void clear() {
        mData.clear();
    }

    @Override
    public List<SubjectsListItem> getData() {
        return mData;
    }

    @Override
    public List<SubjectsListItem> processData(SubjectsListRaw data) {
        if (mData == null) {
            mData = new ArrayList<>();
        }
        List<SubjectsListItem> items = new ArrayList<>();
        for (int i = 0; i < data.getStudentSubjectsList().size(); i++) {
            SubjectsListItem item = new SubjectsListItem();
            StudentSubjectList subject = data.getStudentSubjectsList().get(i);
            item.setAssessmentType(assessmentType[data.getCourses_forms().get(subject.getCourse())]);
            item.setCourseId(subject.getId());
            item.setCourseName(data.getCourses().get(subject.getCourse()));
            items.add(item);
        }
        mData.addAll(items);
        return items;
    }
}
