package org.ucomplex.ucomplex.Modules.Subject.SubjectProfile.model;

import org.ucomplex.ucomplex.Domain.MaterialsFile;
import org.ucomplex.ucomplex.Domain.role.Role;
import org.ucomplex.ucomplex.Domain.role.RoleExtractorFactory.RoleExtractorFactory;
import org.ucomplex.ucomplex.Domain.role.RoleExtractorFactory.TeacherExtractorFactory;
import org.ucomplex.ucomplex.Domain.role.RoleTeacher;

import java.util.ArrayList;
import java.util.List;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 30/03/2017.
 * Project: UComplex
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">www.ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public final class FilesRaw {

    private TeacherRaw teacher;
    private Role roleTeacher;
    private final List<MaterialsFile> files;

    public FilesRaw() {
        this.teacher = new TeacherRaw();
        this.roleTeacher = null;
        this.files = new ArrayList<>();
    }

    public Role getTeacher() {
        if (roleTeacher == null) {
            this.roleTeacher =  RoleExtractorFactory.extractRole(new TeacherExtractorFactory(teacher));
            this.teacher = null;
        }
        return roleTeacher;
    }

    public List<MaterialsFile> getFiles() {
        return files;
    }

}
