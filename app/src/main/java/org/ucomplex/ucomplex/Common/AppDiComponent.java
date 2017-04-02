package org.ucomplex.ucomplex.Common;

import org.ucomplex.ucomplex.Modules.Events.EventsActivity;
import org.ucomplex.ucomplex.Modules.Events.EventsModel;
import org.ucomplex.ucomplex.Modules.Events.EventsPresenter;
import org.ucomplex.ucomplex.Modules.Events.dagger.EventsActivityModule;
import org.ucomplex.ucomplex.Modules.Events.dagger.EventsModelModule;
import org.ucomplex.ucomplex.Modules.Events.dagger.EventsPresenterModule;
import org.ucomplex.ucomplex.Modules.Login.LoginActivity;
import org.ucomplex.ucomplex.Modules.Login.LoginPresenter;
import org.ucomplex.ucomplex.Modules.Login.dagger.LoginActivityModule;
import org.ucomplex.ucomplex.Modules.Login.dagger.LoginPresenterModule;
import org.ucomplex.ucomplex.Modules.RoleSelect.RoleSelectActivity;
import org.ucomplex.ucomplex.Modules.RoleSelect.RoleSelectPresenter;
import org.ucomplex.ucomplex.Modules.RoleSelect.dagger.RoleSelectActivityModule;
import org.ucomplex.ucomplex.Modules.RoleSelect.dagger.RoleSelectPresenterModule;
import org.ucomplex.ucomplex.Modules.Subject.SubjectMaterials.SubjectMaterialsFragment;
import org.ucomplex.ucomplex.Modules.Subject.SubjectMaterials.SubjectMaterialsModel;
import org.ucomplex.ucomplex.Modules.Subject.SubjectMaterials.SubjectMaterialsPresenter;
import org.ucomplex.ucomplex.Modules.Subject.SubjectMaterials.dagger.SubjectMaterialsModelModule;
import org.ucomplex.ucomplex.Modules.Subject.SubjectMaterials.dagger.SubjectMaterialsModule;
import org.ucomplex.ucomplex.Modules.Subject.SubjectMaterials.dagger.SubjectMaterialsPresenterModule;
import org.ucomplex.ucomplex.Modules.Subject.SubjectProfile.SubjectModel;
import org.ucomplex.ucomplex.Modules.Subject.SubjectProfile.SubjectPresenter;
import org.ucomplex.ucomplex.Modules.Subject.SubjectProfile.SubjectProfileFragment;
import org.ucomplex.ucomplex.Modules.Subject.SubjectProfile.dagger.SubjectProfileModule;
import org.ucomplex.ucomplex.Modules.Subject.SubjectProfile.dagger.SubjectModelModule;
import org.ucomplex.ucomplex.Modules.Subject.SubjectProfile.dagger.SubjectPresenterModule;
import org.ucomplex.ucomplex.Modules.Subject.SubjectTimeline.SubjectTimelineFragment;
import org.ucomplex.ucomplex.Modules.Subject.SubjectTimeline.SubjectTimelineModel;
import org.ucomplex.ucomplex.Modules.Subject.SubjectTimeline.SubjectTimelinePresenter;
import org.ucomplex.ucomplex.Modules.Subject.SubjectTimeline.dagger.SubjectTimelineModelModule;
import org.ucomplex.ucomplex.Modules.Subject.SubjectTimeline.dagger.SubjectTimelineModule;
import org.ucomplex.ucomplex.Modules.Subject.SubjectTimeline.dagger.SubjectTimelinePresenterModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 10/02/2017.
 * Project: Listening
 * ---------------------------------------------------
 * <a href="http://www.skyeng.ru">www.skyeng.ru</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */
@Singleton
@Component(modules =
        {EventsActivityModule.class,
                EventsModelModule.class,
                EventsPresenterModule.class,
                LoginActivityModule.class,
                LoginPresenterModule.class,
                RoleSelectPresenterModule.class,
                RoleSelectActivityModule.class,
                SubjectModelModule.class,
                SubjectPresenterModule.class,
                SubjectProfileModule.class,
                SubjectMaterialsModule.class,
                SubjectMaterialsModelModule.class,
                SubjectMaterialsPresenterModule.class,
                SubjectTimelineModule.class,
                SubjectTimelineModelModule.class,
                SubjectTimelinePresenterModule.class
        })

public interface AppDiComponent {

    void inject(EventsPresenter presenter);

    void inject(EventsModel model);

    void inject(EventsActivity activity);

    void inject(LoginPresenter presenter);

    void inject(LoginActivity activity);

    void inject(RoleSelectActivity activity);

    void inject(RoleSelectPresenter presenter);

    void inject(SubjectProfileFragment fragment);

    void inject(SubjectModel model);

    void inject(SubjectPresenter presenter);

    void inject(SubjectMaterialsFragment fragment);

    void inject(SubjectMaterialsModel model);

    void inject(SubjectMaterialsPresenter presenter);

    void inject(SubjectTimelineFragment fragment);

    void inject(SubjectTimelineModel model);

    void inject(SubjectTimelinePresenter presenter);

}

