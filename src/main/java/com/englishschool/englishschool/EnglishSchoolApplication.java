package com.englishschool.englishschool;

import com.englishschool.englishschool.domain.GroupRequest;
import com.englishschool.englishschool.domain.UserRequest;
import com.englishschool.englishschool.entity.BlogCommentEntity;
import com.englishschool.englishschool.entity.BlogEntity;
import com.englishschool.englishschool.entity.CourseRatingEntity;
import com.englishschool.englishschool.entity.TimetableEntity;
import com.englishschool.englishschool.enums.UserRole;
import com.englishschool.englishschool.service.BlogService;
import com.englishschool.englishschool.service.GroupService;
import com.englishschool.englishschool.service.TimetableService;
import com.englishschool.englishschool.service.UserService;
import liquibase.pro.packaged.B;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

@SpringBootApplication
@AllArgsConstructor
public class EnglishSchoolApplication {

    public static void main(String[] args) {
        SpringApplication.run(EnglishSchoolApplication.class, args);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner commandLineRunner(UserService userService, GroupService groupService, BlogService blogService, TimetableService timetableService) {
        return args -> {
            userService.saveUser(new UserRequest(null, "student1@mail.ru", "Ivan", "Ivanov", UserRole.STUDENT, false, "owo"));
            userService.saveUser(new UserRequest(null, "student2@mail.ru", "Aleksey", "Maximov", UserRole.STUDENT, false, "owo"));
            userService.saveUser(new UserRequest(null, "student3@mail.ru", "Egor", "Petrov", UserRole.STUDENT, false, "owo"));
            userService.saveUser(new UserRequest(null, "student4@mail.ru", "Maxim", "Skvorcov", UserRole.STUDENT, false, "owo"));
            userService.saveUser(new UserRequest(null, "student5@mail.ru", "Nikita", "Ivanov", UserRole.STUDENT, false, "owo"));
            userService.saveUser(new UserRequest(null, "student6@mail.ru", "Ilya", "Kozel", UserRole.STUDENT, false, "owo"));
            userService.saveUser(new UserRequest(null, "student7@mail.ru", "Pavel", "Petrov", UserRole.STUDENT, false, "owo"));
            userService.saveUser(new UserRequest(null, "student8@mail.ru", "Dmitry", "Maximov", UserRole.STUDENT, false, "owo"));
            userService.saveUser(new UserRequest(null, "student9@mail.ru", "Roman", "Popov", UserRole.STUDENT, false, "owo"));
            userService.saveUser(new UserRequest(null, "student10@mail.ru", "Sergey", "Smirnov", UserRole.STUDENT, false, "owo"));
            userService.saveUser(new UserRequest(null, "student11@mail.ru", "Kirill", "Mihailov", UserRole.STUDENT, false, "owo"));
            userService.saveUser(new UserRequest(null, "student12@mail.ru", "Egor", "Kuznecov", UserRole.STUDENT, false, "owo"));
            userService.saveUser(new UserRequest(null, "student13@mail.ru", "Dmitry", "Lysenok", UserRole.STUDENT, false, "owo"));
            userService.saveUser(new UserRequest(null, "student14@mail.ru", "Maria", "Ivanova", UserRole.STUDENT, false, "owo"));
            userService.saveUser(new UserRequest(null, "student15@mail.ru", "Ann", "Smirnova", UserRole.STUDENT, false, "owo"));
            userService.saveUser(new UserRequest(null, "student16@mail.ru", "Kate", "Petrova", UserRole.STUDENT, false, "owo"));
            userService.saveUser(new UserRequest(null, "student17@mail.ru", "Carol", "Maximova", UserRole.STUDENT, false, "owo"));
            userService.saveUser(new UserRequest(null, "student19@mail.ru", "Daria", "Mihailova", UserRole.STUDENT, false, "owo"));

            userService.saveUser(new UserRequest(null, "teacher1@mail.ru", "Ivan", "Ivanov", UserRole.TEACHER, false, "owo"));
            userService.saveUser(new UserRequest(null, "teacher2@mail.ru", "Aleksey", "Maximov", UserRole.TEACHER, false, "owo"));
            userService.saveUser(new UserRequest(null, "teacher3@mail.ru", "Egor", "Petrov", UserRole.TEACHER, false, "owo"));

            userService.saveUser(new UserRequest(null, "admin@mail.ru", "Admin", "Admin", UserRole.ADMIN, false, "owo"));

            groupService.createGroup("A1 group", 19L);
            groupService.createGroup("B1 group", 20L);
            groupService.createGroup("C2 group", 21L);

            userService.assignToGroup(new GroupRequest(1l, 1l));
            userService.assignToGroup(new GroupRequest(1l, 2l));
            userService.assignToGroup(new GroupRequest(1l, 3l));
            userService.assignToGroup(new GroupRequest(1l, 4l));
            userService.assignToGroup(new GroupRequest(1l, 5l));
            userService.assignToGroup(new GroupRequest(1l, 6l));

            userService.assignToGroup(new GroupRequest(2l, 7l));
            userService.assignToGroup(new GroupRequest(2l, 8l));
            userService.assignToGroup(new GroupRequest(2l, 9l));
            userService.assignToGroup(new GroupRequest(2l, 10l));
            userService.assignToGroup(new GroupRequest(2l, 11l));
            userService.assignToGroup(new GroupRequest(2l, 12l));

            userService.assignToGroup(new GroupRequest(3l, 13l));
            userService.assignToGroup(new GroupRequest(3l, 14));
            userService.assignToGroup(new GroupRequest(3l, 15));

            userService.rateCourses(new CourseRatingEntity(null, "Greate courses", null, 5, new Date()), 1l);
            userService.rateCourses(new CourseRatingEntity(null, "Bad courses", null, 1, new Date()), 2l);
            userService.rateCourses(new CourseRatingEntity(null, "Norm courses", null, 3, new Date()), 3l);
            userService.rateCourses(new CourseRatingEntity(null, "Cool courses", null, 4, new Date()), 4l);

        };
    }
}
