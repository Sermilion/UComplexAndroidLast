package org.ucomplex.ucomplex.Modules.UserProfile;

import org.ucomplex.ucomplex.Common.FacadeCommon;
import org.ucomplex.ucomplex.Common.base.UCApplication;
import org.ucomplex.ucomplex.Common.interfaces.mvp.MVPModel;
import org.ucomplex.ucomplex.Domain.users.UserInterface;
import org.ucomplex.ucomplex.Domain.role.Role;
import org.ucomplex.ucomplex.Domain.role.RoleStudent;
import org.ucomplex.ucomplex.Domain.role.RoleTeacher;
import org.ucomplex.ucomplex.Modules.UserProfile.model.UserProfileItem;
import org.ucomplex.ucomplex.Modules.UserProfile.model.UserProfileRaw;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 03/05/2017.
 * Project: UComplex
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">www.ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public class UserProfileModel implements MVPModel<UserProfileRaw, List<UserProfileItem>, Integer> {

    private List<UserProfileItem> mData;
    private UserProfileService mService;

    public UserProfileModel() {
        UCApplication.getInstance().getAppDiComponent().inject(this);
    }

    @Inject
    public void setService(UserProfileService service) {
        this.mService = service;
    }

    @Override
    public Observable<UserProfileRaw> loadData(Integer params) {
        return mService.getUserProfile(params).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Void> addAsFriend(Integer user) {
        return mService.addAsFriend(user).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Void> unfriend(Integer user) {
        return mService.unfriend(user).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Void> block(Integer user) {
        return mService.block(user).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Void> unblock(Integer user) {
        return mService.unblock(user).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void setData(List<UserProfileItem> data) {
        this.mData = data;
    }

    @Override
    public void addData(List<UserProfileItem> data) {
        this.mData.addAll(data);
    }

    @Override
    public void clear() {
        mData.clear();
    }

    @Override
    public List<UserProfileItem> getData() {
        return mData;
    }

    @Override
    public List<UserProfileItem> processData(UserProfileRaw data) {
        List<UserProfileItem>  items = new ArrayList<>();
        UserInterface user = data.extractUser();
        String positionName = FacadeCommon.getStringUserType(UCApplication.getInstance(), user.getType());
        UserProfileItem header = new UserProfileItem(user.getName(),
                                                     positionName,
                                                     user.getIsFriend(),
                                                     user.getIsBlack(),
                                                     user.getCode(),
                                                     user.getId());
        items.add(header);
        for (int i = 0; i < user.getRoles().size(); i++) {
            Role role = user.getRoles().get(i);
            String rolePositionName;
            String sectionName;
            if (role instanceof RoleTeacher) {
                rolePositionName = role.getPosition_name();
                sectionName = ((RoleTeacher) role).getSection_name();
            } else if((role instanceof RoleStudent)) {
                rolePositionName = FacadeCommon.getStringUserType(UCApplication.getInstance(), role.getType());
                sectionName = role.getPosition_name();
            } else {
                rolePositionName = FacadeCommon.getStringUserType(UCApplication.getInstance(), role.getType());
                sectionName = role.getPosition_name();
            }
            UserProfileItem item = new UserProfileItem(rolePositionName, sectionName, role.getRole(), role.getType());
            items.add(item);
        }
        mData = items;
        return items;
    }
}
