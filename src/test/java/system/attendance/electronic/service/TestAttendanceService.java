package system.attendance.electronic.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import system.attendance.electronic.model.Attendance;

import java.util.List;

/**
 * @author Soloist
 * @version 1.0
 * @createtime 2018/6/13 2:48
 * @email ly@soloist.top
 * @description 一个测试类中如果有多个测试方法，所有测试方法是同时进行的，无法确定执行顺序的先后。
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-context.xml")
public class TestAttendanceService extends AbstractJUnit4SpringContextTests {
    
    @Autowired
    private AttendanceService attendanceService;
    
    @Test
    public void textCheckAttendance() {
        Integer integer = attendanceService.checkAttendance("203238190990102528");
        System.out.println(integer);
    }
    
    @Test
    public void testUpdateAttendance() {
        Attendance attendance = attendanceService.updateAttendance("203238190990102528");
        System.out.println(attendance);
    }
    
    @Test
    public void testDelAttendance() {
        Attendance attendance = attendanceService.delAttendance("203238190990102528");
        System.out.println(attendance);

    }
    
    @Test
    public void testGetUserAttendance() {
        List<Attendance> userAttendance = attendanceService.getUserAttendance("203238190990102528", 2018, 6);
        userAttendance.forEach(System.out::println);
    }
    
}
