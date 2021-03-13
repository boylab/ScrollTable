package com.boylab.example.bean;

import com.boylab.protocol.ItemRow;

public class Student implements ItemRow {

    private Long id;
    private String name;
    private int age;
    private String sex;
    private float height;
    private float weight;

    private float chinese;
    private float math;
    private float english;

    private String chineseTeacher;
    private String mathTeacher;
    private String englishTeacher;

    private String remark;

    public Student(int i) {
        /**
         * 模拟初始化数据
         */
        this.id = Long.valueOf(100 + i * i);
        this.name = "名字" + i;
        this.age = i * i;
        this.sex = (i % 3 == 0 ? "boy" : "girl");
        this.height = 1.00f * i;
        this.weight = 5.00f * i;
        this.chinese = 600.00f;
        this.math = 10.00f * i;
        this.english = 5.00f;
        this.chineseTeacher = "语文老师" + i % 5;
        this.mathTeacher = "数学老师" + i % 5;
        this.englishTeacher = "英语老师" + i % 5;
        this.remark = "";
    }

    public Student() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getChinese() {
        return chinese;
    }

    public void setChinese(float chinese) {
        this.chinese = chinese;
    }

    public float getMath() {
        return math;
    }

    public void setMath(float math) {
        this.math = math;
    }

    public float getEnglish() {
        return english;
    }

    public void setEnglish(float english) {
        this.english = english;
    }

    public String getChineseTeacher() {
        return chineseTeacher;
    }

    public void setChineseTeacher(String chineseTeacher) {
        this.chineseTeacher = chineseTeacher;
    }

    public String getMathTeacher() {
        return mathTeacher;
    }

    public void setMathTeacher(String mathTeacher) {
        this.mathTeacher = mathTeacher;
    }

    public String getEnglishTeacher() {
        return englishTeacher;
    }

    public void setEnglishTeacher(String englishTeacher) {
        this.englishTeacher = englishTeacher;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public int size() {
        return StudentLabel.SIZE;
    }

    @Override
    public String get(int position) {
        /**
         * position 对应的值 与 Label的标签对应上即可
         */
        position = position % 12;

        if (position == 0) {
            return String.valueOf(id);
        } else if (position == 1) {
            return name;
        } else if (position == 2) {
            return String.valueOf(age);
        } else if (position == 3) {
            return sex;
        } else if (position == 4) {
            return String.format("%6.3f", this.height);
        } else if (position == 5) {
            return String.format("%6.3f", this.weight);
        } else if (position == 6) {
            return String.format("%6.3f", this.chinese);
        } else if (position == 7) {
            return String.format("%6.3f", this.math);
        } else if (position == 8) {
            return String.format("%6.3f", this.english);
        } else if (position == 9) {
            return chineseTeacher;
        } else if (position == 10) {
            return mathTeacher;
        } else if (position == 11) {
            return englishTeacher;
        } else if (position == 12) {
            return remark;
        }
        return "N/A";
    }


}
