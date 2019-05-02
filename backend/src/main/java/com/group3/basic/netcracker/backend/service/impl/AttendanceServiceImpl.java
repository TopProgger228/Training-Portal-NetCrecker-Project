package com.group3.basic.netcracker.backend.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.group3.basic.netcracker.backend.dao.*;
import com.group3.basic.netcracker.backend.dto.CourseAttendanceDto;
import com.group3.basic.netcracker.backend.dto.LessonAttendanceDto;
import com.group3.basic.netcracker.backend.dto.UserAttendanceDto;
import com.group3.basic.netcracker.backend.entity.*;
import com.group3.basic.netcracker.backend.util.dtomapper.CourseAttendanceDtoMapper;
import com.group3.basic.netcracker.backend.util.dtomapper.LessonAttendanceDtoMapper;
import com.group3.basic.netcracker.backend.util.dtomapper.TrainerAttendanceDtoMapper;
import com.group3.basic.netcracker.backend.util.dtomapper.UserAttendanceDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.group3.basic.netcracker.backend.service.AttendanceService;
import org.springframework.stereotype.Service;

@Service
public class AttendanceServiceImpl implements AttendanceService {
	private final AttendanceDao attendanceDao;
	private final CourseDAO courseDao;
	private final LessonDao lessonDao;
	private final UserDao userDao;
	private final LessonMissingDao lessonMissingDao;
	private final CourseAttendanceDtoMapper courseAttendanceDtoMapper;
	private final LessonAttendanceDtoMapper lessonAttendanceDtoMapper;
	private final UserAttendanceDtoMapper userAttendanceDtoMapper;
	private final TrainerAttendanceDtoMapper trainerAttendanceDtoMapper;

	@Autowired
	public AttendanceServiceImpl(AttendanceDao attendanceDao, CourseDAO courseDao, LessonDao lessonDao, UserDao userDao, LessonMissingDao lessonMissingDao, CourseAttendanceDtoMapper courseAttendanceDtoMapper, LessonAttendanceDtoMapper lessonAttendanceDtoMapper, UserAttendanceDtoMapper userAttendanceDtoMapper, TrainerAttendanceDtoMapper trainerAttendanceDtoMapper) {
		this.attendanceDao = attendanceDao;
		this.courseDao = courseDao;
		this.lessonDao = lessonDao;
		this.userDao = userDao;
        this.lessonMissingDao = lessonMissingDao;
        this.courseAttendanceDtoMapper = courseAttendanceDtoMapper;
		this.lessonAttendanceDtoMapper = lessonAttendanceDtoMapper;
		this.userAttendanceDtoMapper = userAttendanceDtoMapper;
		this.trainerAttendanceDtoMapper = trainerAttendanceDtoMapper;
	}



	@Override
	public List<Attendance> listAttendance() {
         return attendanceDao.listAttendance();
	}



	@Override
	public List<CourseAttendanceDto> getAllCourseAttendance() {

		List<CourseAttendanceDto> courseAttendanceDtoList = new ArrayList<>();
		List<Course> courseList = courseDao.listCourses();

		for (Course c : courseList) {
		    CourseAttendanceDto cad = courseAttendanceDtoMapper.toCourseAttendanceDto(c);
			cad.setTrainer(trainerAttendanceDtoMapper.toTrainerAttendanceDto(userDao.getTrainerByCourse(c.getId())));

			courseAttendanceDtoList.add(cad);

		}

		return courseAttendanceDtoList;
	}



	@Override
	public List<LessonAttendanceDto> getLessonsOfCourseAttendance(int courseId) {

		List<LessonAttendanceDto> lessonAttendanceDtoList = new ArrayList<>();
		List<Lesson> lessonsList = lessonDao.getLessonByCourse(courseId);

		for (Lesson l : lessonsList) {
		   lessonAttendanceDtoList.add(lessonAttendanceDtoMapper.toLessonAttendanceDto(l));
		}

		return lessonAttendanceDtoList;

	}



	@Override
	public List<UserAttendanceDto> getUsersOfCourseAttendance(int lessonId) {

		List<UserAttendanceDto> userAttendanceDtoList = new ArrayList<>();
		List<User> userList = userDao.getUsersByLesson(lessonId);
        List<LessonMissing> lessonMissingList = lessonMissingDao.getLessonMissingByLesson(lessonId);

		for (User u : userList) {
		    UserAttendanceDto uad = userAttendanceDtoMapper.toUserAttendanceDto(u);
		    uad.setLessonId(lessonId);

		    for (LessonMissing lm : lessonMissingList) {
                if (uad.getId() == lm.getUserId()) {
                    uad.setAttendanceStatus(lm.getReason());
                } else {
                    uad.setAttendanceStatus("Present");
                }
            }

			userAttendanceDtoList.add(uad);

		}

		return userAttendanceDtoList;
	}

}
