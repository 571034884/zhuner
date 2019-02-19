package com.aibabel.speech.properites;

import java.util.Random;

public class Answer {

    private String[] answer={"准儿没有听懂","我还在学习中，你可以说点别的！"};

    public String getAnswer() {
        Random random=new Random();
       int num= random.nextInt(1);
        return answer[num];
    }

}
