package org.ucomplex.ucomplex.Modules.RoleSelect;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.ucomplex.ucomplex.Common.base.UCApplication;
import org.ucomplex.ucomplex.Common.base.BaseMVPActivity;
import org.ucomplex.ucomplex.Common.interfaces.mvp.MVPView;
import org.ucomplex.ucomplex.Modules.Events.EventsActivity;
import org.ucomplex.ucomplex.Modules.Login.model.LoginUser;
import org.ucomplex.ucomplex.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RoleSelectActivity extends BaseMVPActivity<MVPView, RoleSelectPresenter> {

    private static final String EXTRA_USER = "EXTRA_USER";

    @BindView(R.id.recyclerView)
    protected RecyclerView mRecyclerView;
    private RoleSelectAdapter mAdapter;

    public static Intent creteIntent (@NonNull Context context, @NonNull LoginUser userInterface) {
        //noinspection ConstantConditions
        if (userInterface == null || context == null)
            throw new IllegalArgumentException();
        Intent intent = new Intent(context, RoleSelectActivity.class);
        intent.putExtra(EXTRA_USER, userInterface);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        UCApplication.getInstance().getAppDiComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role_select);
        ButterKnife.bind(this);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new RoleSelectAdapter(position -> {
            presenter.onRoleSelected(position);
            startActivity(EventsActivity.creteIntent(RoleSelectActivity.this));
            finish();
        });
        mRecyclerView.setAdapter(mAdapter);
        if (presenter.getData() == null || presenter.getData().size() == 0) {
            presenter.loadData(getIntent().getParcelableExtra(EXTRA_USER));
        } else {
            dataLoaded();
        }
    }

    @Override
    public void dataLoaded() {
        mAdapter.setItems(presenter.getData());
        mAdapter.notifyDataSetChanged();
    }
}
