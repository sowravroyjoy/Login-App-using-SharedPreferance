package com.example.loginapp;

import java.util.HashMap;
import java.util.Map;

public class InputData {
    HashMap<String, String> inputMapper = new HashMap<String, String>();

    public void addinput(String username, String password){
        inputMapper.put(username,password);
    }

    public boolean checkUsername(String username){
        return inputMapper.containsKey(username);
    }

    public boolean checkInput(String username, String password){
        /* checks if username exists*/
        if(inputMapper.containsKey(username)){
            /* checks if password exists*/
            if(password.equals(inputMapper.get(username))){
                return true;
            }
        }
        return false;
    }

    public void loadInputs(Map<String , ?> preferencesMap){
        for(Map.Entry<String, ?> entries : preferencesMap.entrySet()){
            if(!entries.getKey().equals("cbRememberMe")){
                inputMapper.put(entries.getKey(),entries.getValue().toString());
            }
        }
    }
}
