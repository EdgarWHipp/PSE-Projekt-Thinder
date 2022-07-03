package com.hfad.thinder.viewmodels;

import java.util.regex.Pattern;

//Todo: Strings sind erstmal vorläufig
//Todo: Error Zustand eventuell überflüssig
public enum VerifyTokenResultTypes {
    ERROR("error"),
    STUDENT("student"),
    SUPERVISOR("supervisor");

    private Pattern pattern;

    private VerifyTokenResultTypes(String pattern) {
        this.pattern = Pattern.compile(pattern);
    }


    public static VerifyTokenResultTypes setResult(String resultString) {
        for (VerifyTokenResultTypes result : VerifyTokenResultTypes.values()) {
            if (result.pattern.matcher(resultString).matches()) {
                return result;
            }
        }
        return ERROR;
    }


}
