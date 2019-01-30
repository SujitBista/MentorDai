package com.example.androidregisterandlogin;

public class FormValidate implements ValidationInterface {
    @Override
    public boolean validateName(String name) {
        boolean validate = true;
        if(name.isEmpty() || name.length() > 32){
            validate = false;
        }
        return validate;
    }

    @Override
    public boolean validateEmail(String vemail) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return vemail.matches(emailPattern);
    }
}
