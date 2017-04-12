package org.ucomplex.ucomplex.Modules.Subject.SubjectMaterials.dagger;

import org.ucomplex.ucomplex.Common.ServiceGenerator;
import org.ucomplex.ucomplex.Common.UCApplication;

import org.ucomplex.ucomplex.Modules.Portfolio.retrofit.DownloadFileService;
import org.ucomplex.ucomplex.Modules.Portfolio.retrofit.FileService;
import org.ucomplex.ucomplex.Modules.Portfolio.retrofit.PortfolioService;
import org.ucomplex.ucomplex.Modules.Subject.SubjectMaterials.SubjectTeachersMaterialsService;

import dagger.Module;
import dagger.Provides;

import static org.ucomplex.ucomplex.Common.UCApplication.BASE_FILES_URL;

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
public class SubjectMaterialsModelModule {

    @Provides
    SubjectTeachersMaterialsService provideTeachersFilesService(){
        return ServiceGenerator.createService(SubjectTeachersMaterialsService.class, UCApplication.getInstance().getAuthString());
    }

    @Provides
    PortfolioService providePortfolioService(){
        return ServiceGenerator.createService(PortfolioService.class, UCApplication.getInstance().getAuthString());
    }

    @Provides
    DownloadFileService provideDownloadService(){
        return ServiceGenerator.createService(DownloadFileService.class, UCApplication.getInstance().getAuthString(), BASE_FILES_URL);
    }

    @Provides
    FileService provideFileService(){
        return ServiceGenerator.createService(FileService.class, UCApplication.getInstance().getAuthString());
    }

}
