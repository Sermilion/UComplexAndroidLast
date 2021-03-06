package org.ucomplex.ucomplex.Modules.RoleInfoTeacher.RoleInfoTeacherInfo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import org.ucomplex.ucomplex.Common.base.BaseMvpFragment;
import org.ucomplex.ucomplex.Common.base.UCApplication;
import org.ucomplex.ucomplex.Modules.RoleInfoTeacher.RoleInfoTeacherProfile.RoleInfoTeacherProfileAdapter;
import org.ucomplex.ucomplex.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 28/05/2017.
 * Project: UComplex
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">www.ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public class RoleInfoTeacherInfoFragment extends BaseMvpFragment<RoleInfoTeacherInfoPresenter> {

    public static RoleInfoTeacherInfoFragment getInstance() {
        RoleInfoTeacherInfoFragment fragment = new RoleInfoTeacherInfoFragment();
        return fragment;
    }

    @BindView(R.id.progressBar)
    protected ProgressBar mProgress;
    @BindView(R.id.recyclerView)
    protected RecyclerView mRecyclerView;

    private RoleInfoTeacherInfoAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        UCApplication.getInstance().getAppDiComponent().inject(this);
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setUserVisibleHint(false);
        View view = inflater.inflate(R.layout.fragment_recycler, container, false);
        ButterKnife.bind(this, view);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivityContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new RoleInfoTeacherInfoAdapter();
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter.attachView(this);

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (presenter.getData() == null) {
                presenter.loadData(null);
            } else {
                mAdapter.setItems(presenter.getData());
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void dataLoaded() {
        mAdapter.setItems(presenter.getData());
        mAdapter.notifyDataSetChanged();
    }
}
