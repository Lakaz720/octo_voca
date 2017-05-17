package com.bombelab.lakaz.octo_voca;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Pomiring on 2016-07-10.
 */
public class V_Check {

    public boolean V_Check(String txt_s){

        boolean checkresult=true;

        for(int i=0;i<txt_s.length();i++){
            switch (txt_s.charAt(i)){
                case '\'':
                    checkresult=false;
                    break;
                case ';':
                    checkresult=false;
                    break;
                case ')':
                    checkresult=false;
                    break;
                case '(':
                    checkresult=false;
                    break;
                case '*':
                    checkresult=false;
                    break;
                case '`':
                    checkresult=false;
                    break;
                case '\\':
                    checkresult=false;
                    break;
                case ':':
                    checkresult=false;
                    break;
                case '=':
                    checkresult=false;
                    break;

            }

        }

        return checkresult;
    }

    public boolean N_Check(String number){

        boolean result = false;

        try{

            Double.parseDouble(number);
            result=true;

        }catch (Exception e){

        }

        return result;
    }
}
