package com.qyh.coderepository.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * @author 邱永恒
 * @time 2017/11/9  13:42
 * @desc ${TODD}
 */
@Entity
public class Student {
    @Id(autoincrement = true) // id自增长
    private Long stuId; // 学员id

    //@Index(unique = true) // 唯一性
    private String stuNo; // 学员编号

    private String stuName; // 学员姓名

    private String stuSex; // 学员性别

    private String stuScore; // 学员成绩

    @Generated(hash = 315497705)
    public Student(Long stuId, String stuNo, String stuName, String stuSex,
            String stuScore) {
        this.stuId = stuId;
        this.stuNo = stuNo;
        this.stuName = stuName;
        this.stuSex = stuSex;
        this.stuScore = stuScore;
    }

    @Generated(hash = 1556870573)
    public Student() {
    }

    public Long getStuId() {
        return this.stuId;
    }

    public void setStuId(Long stuId) {
        this.stuId = stuId;
    }

    public String getStuNo() {
        return this.stuNo;
    }

    public void setStuNo(String stuNo) {
        this.stuNo = stuNo;
    }

    public String getStuName() {
        return this.stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public String getStuSex() {
        return this.stuSex;
    }

    public void setStuSex(String stuSex) {
        this.stuSex = stuSex;
    }

    public String getStuScore() {
        return this.stuScore;
    }

    public void setStuScore(String stuScore) {
        this.stuScore = stuScore;
    }
}
