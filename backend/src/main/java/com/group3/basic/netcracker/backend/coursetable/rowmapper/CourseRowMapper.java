package com.group3.basic.netcracker.backend.coursetable.rowmapper;

import com.group3.basic.netcracker.backend.coursetable.entity.Course;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class CourseRowMapper implements RowMapper {
    public Course mapRow(ResultSet row, int rowNum) throws SQLException {
        Course course = new Course();
        course.setId(row.getInt("id"));
        course.setName(row.getString("name"));
        course.setInfo(row.getString("info"));
        course.setStart_date(row.getObject("start_date", LocalDate.class));
        course.setEnd_date(row.getObject("end_date", LocalDate.class));
        course.setSkill_level(row.getString("skill_level"));
        course.setLearn_direction(row.getString("learn_direction"));
        course.setUser_id(row.getInt("user_id"));
        course.setQty_per_week(row.getInt("qty_per_week"));
        return course;
    }
}
