package com.hfad.thinder.viewmodels;

import java.util.regex.Pattern;

//Todo: Strings sind erstmal vorläufig
//Todo: Error Zustand eventuell überflüssig
public enum ConfirmPasswordResultTypes {
  ERROR("error"),
  STUDENT("student"),
  SUPERVISOR("supervisor");

  private Pattern pattern;

  private ConfirmPasswordResultTypes(String pattern) {
    this.pattern = Pattern.compile(pattern);
  }


  public static ConfirmPasswordResultTypes setResult(String resultString) {
    for (ConfirmPasswordResultTypes result : ConfirmPasswordResultTypes.values()) {
      if (result.pattern.matcher(resultString).matches()) {
        return result;
      }
    }
    return ERROR;
  }


}
