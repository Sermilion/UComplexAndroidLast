package org.ucomplex.ucomplex.Modules.Subject.SubjectMaterials.dagger;

import org.ucomplex.ucomplex.Modules.Subject.SubjectMaterials.SubjectMaterialsAdapter;
import org.ucomplex.ucomplex.Modules.Subject.SubjectMaterials.SubjectMaterialsPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 24/03/2017.
 * Project: UComplex
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">www.ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */
@Module
public class SubjectMaterialsModule {

    @Provides
    SubjectMaterialsPresenter providePresenter() {
        return new SubjectMaterialsPresenter();
    }

    @Provides
    SubjectMaterialsAdapter provideAdapter() {
        return new SubjectMaterialsAdapter();
    }

}
