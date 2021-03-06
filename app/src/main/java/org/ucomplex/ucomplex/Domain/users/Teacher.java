package org.ucomplex.ucomplex.Domain.users;

import org.ucomplex.ucomplex.Domain.BlackList;
import org.ucomplex.ucomplex.Domain.FriendList;
import org.ucomplex.ucomplex.Domain.role.Role;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 06/12/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public final class Teacher implements UserInterface {

    private final User user;
    private final int post;
    private final String experience;
    private final String dep_experience;
    private final String courses;
    private final int rank;
    private final String academic_awards;
    private final int academic_rank;
    private final int academic_degree;
    private final String upqualification;
    private final int degree;
    private final String bio;
    private final String phone_work;
    private final int plan;
    private final int fact;
    private final int fails;
    private final String activity_update;
    private final int selection;
    private final String department;
    private final int closed;
    private final int agent;
    private final List<Integer> departments;

    public Teacher(User user) {
        this.user = user;
        post = 0;
        experience = "";
        dep_experience = "";
        courses = "";
        rank = 0;
        academic_awards = "";
        academic_rank = 0;
        academic_degree = 0;
        upqualification = "";
        degree = 0;
        bio = "";
        phone_work = "";
        plan = 0;
        fact = 0;
        fails = 0;
        activity_update = "";
        selection = 0;
        department = "";
        closed = 0;
        agent = 0;
        departments = new ArrayList<>();
    }

    public Teacher(Teacher.TeacherBuilder builder) {
        this.user = new User(builder.userBuilder);
        this.post = builder.post;
        this.experience = builder.experience;
        this.dep_experience = builder.dep_experience;
        this.courses = builder.courses;
        this.rank = builder.rank;
        this.academic_awards = builder.academic_awards;
        this.upqualification = builder.upqualification;
        this.degree = builder.degree;
        this.bio = builder.bio;
        this.phone_work = builder.phone_work;
        this.plan = builder.plan;
        this.fact = builder.fact;
        this.fails = builder.fails;
        this.activity_update = builder.activity_update;
        this.selection = builder.selection;
        this.department = builder.department;
        this.closed = builder.closed;
        this.agent = builder.agent;
        this.departments = builder.departments;
        this.academic_degree = builder.academic_degree;
        this.academic_rank = builder.academic_rank;
    }

    public int getPost() {
        return post;
    }

    public String getExperience() {
        return experience;
    }

    public String getDep_experience() {
        return dep_experience;
    }

    public String getCourses() {
        return courses;
    }

    public int getRank() {
        return rank;
    }

    public String getAcademic_awards() {
        return academic_awards;
    }

    public String getUpqualification() {
        return upqualification;
    }

    public int getAcademic_rank() {
        return academic_rank;
    }

    public int getAcademic_degree() {
        return academic_degree;
    }

    public int getDegree() {
        return degree;
    }

    public String getBio() {
        return bio;
    }

    public String getPhone_work() {
        return phone_work;
    }

    public int getPlan() {
        return plan;
    }

    public int getFact() {
        return fact;
    }

    public int getFails() {
        return fails;
    }

    public String getActivity_update() {
        return activity_update;
    }

    public int getSelection() {
        return selection;
    }

    public String getDepartment() {
        return department;
    }

    public int getClosed() {
        return closed;
    }

    public int getAgent() {
        return agent;
    }

    public List<Integer> getDepartments() {
        return departments;
    }

    @Override
    public int getType() {
        return user.getType();
    }

    @Override
    public String getLogin() {
        return user.getLogin();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public List<? extends Role> getRoles() {
        return user.getRoles();
    }

    @Override
    public int getPhoto() {
        return user.getPhoto();
    }

    @Override
    public String getCode() {
        return user.getCode();
    }

    @Override
    public String getName() {
        return user.getName();
    }

    @Override
    public int getId() {
        return user.getId();
    }

    @Override
    public int getPerson() {
        return user.getPerson();
    }

    @Override
    public FriendList getIsFriend() {
        return user.getIsFriend();
    }

    @Override
    public BlackList getIsBlack() {
        return user.getIsBlack();
    }

    public static class TeacherBuilder {
        User.UserBuilder userBuilder;
        int post;
        String experience;
        String dep_experience;
        String courses;
        int rank;
        String academic_awards;
        int academic_degree;
        int academic_rank;
        String upqualification;
        int degree;
        String bio;
        String phone_work;
        int plan;
        int fact;
        int fails;
        String activity_update;
        int selection;
        String department;
        int closed;
        int agent;
        List<Integer> departments;

        public TeacherBuilder() {
            userBuilder = new User.UserBuilder();
        }

        public Teacher build() {
            return new Teacher(this);
        }

        public TeacherBuilder id(int id) {
            userBuilder.id = id;
            return this;
        }

        public TeacherBuilder roles(List<? extends Role> roles) {
            userBuilder.roles(roles);
            return this;
        }

        public TeacherBuilder name(String name) {
            userBuilder.name = name;
            return this;
        }

        public TeacherBuilder statuses(String statuses) {
            userBuilder.statuses = statuses;
            return this;
        }

        public TeacherBuilder photo(int photo) {
            userBuilder.photo = photo;
            return this;
        }

        public TeacherBuilder code(String code) {
            userBuilder.code = code;
            return this;
        }

        public TeacherBuilder email(String email) {
            userBuilder.email = email;
            return this;
        }

        public TeacherBuilder friend(FriendList friend) {
            userBuilder.isFriend = friend;
            return this;
        }

        public TeacherBuilder black(BlackList black) {
            userBuilder.isBlack = black;
            return this;
        }

        public TeacherBuilder post(int post) {
            this.post = post;
            return this;
        }

        public TeacherBuilder experience(String experience) {
            this.experience = experience;
            return this;
        }

        public TeacherBuilder dep_experience(String dep_experience) {
            this.dep_experience = dep_experience;
            return this;
        }

        public TeacherBuilder courses(String courses) {
            this.courses = courses;
            return this;
        }

        public TeacherBuilder rank(int rank) {
            this.rank = rank;
            return this;
        }

        public TeacherBuilder academic_degree(int academic_degree) {
            this.academic_degree = academic_degree;
            return this;
        }

        public TeacherBuilder academic_rank(int academic_rank) {
            this.academic_rank = academic_rank;
            return this;
        }

        public TeacherBuilder academic_awards(String academic_awards) {
            this.academic_awards = academic_awards;
            return this;
        }

        public TeacherBuilder upqualification(String upqualification) {
            this.upqualification = upqualification;
            return this;
        }

        public TeacherBuilder degree(int degree) {
            this.degree = degree;
            return this;
        }

        public TeacherBuilder bio(String bio) {
            this.bio = bio;
            return this;
        }

        public TeacherBuilder phone_work(String phone_work) {
            this.phone_work = phone_work;
            return this;
        }

        public TeacherBuilder plan(int plan) {
            this.plan = plan;
            return this;
        }

        public TeacherBuilder fact(int fact) {
            this.fact = fact;
            return this;
        }

        public TeacherBuilder fails(int fails) {
            this.fails = fails;
            return this;
        }

        public TeacherBuilder activity_update(String activity_update) {
            this.activity_update = activity_update;
            return this;
        }

        public TeacherBuilder selection(int selection) {
            this.selection = selection;
            return this;
        }

        public TeacherBuilder department(String department) {
            this.department = department;
            return this;
        }

        public TeacherBuilder closed(int closed) {
            this.closed = closed;
            return this;
        }

        public TeacherBuilder agent(int agent) {
            this.agent = agent;
            return this;
        }

        public TeacherBuilder departments(List<Integer> departments) {
            this.departments = Collections.unmodifiableList(new ArrayList<>(departments));
            return this;
        }
    }
}
