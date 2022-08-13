package com.hfad.thinder.viewmodels.user;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hfad.thinder.R;
import com.hfad.thinder.data.model.USERTYPE;
import com.hfad.thinder.data.source.repository.UserRepository;
import com.hfad.thinder.data.source.result.Result;
import com.hfad.thinder.viewmodels.ViewModelResult;
import com.hfad.thinder.viewmodels.ViewModelResultTypes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForgotPasswordViewModel extends ViewModel {
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,20}$");
    private final UserRepository userRepository = UserRepository.getInstance();
    private MutableLiveData<ForgotPasswordFormState> formState;
    private MutableLiveData<ViewModelResult> loginResult;

    private MutableLiveData<String> code;
    private MutableLiveData<String> newPassword;
    private MutableLiveData<String> newPasswordConfirmation;

    public void login() {
        Result result = userRepository.resetPasswordWithToken(code.getValue(), newPassword.getValue());
        if (!result.getSuccess()) {
            loginResult.setValue(
                    new ViewModelResult(result.getErrorMessage(), ViewModelResultTypes.ERROR));
        } else if (result.getSuccess()) {
            USERTYPE usertype = userRepository.getType();
            if (usertype == USERTYPE.STUDENT) {
                loginResult.setValue(
                        new ViewModelResult(result.getErrorMessage(), ViewModelResultTypes.STUDENT));
            } else if (usertype == USERTYPE.SUPERVISOR) {
                loginResult.setValue(
                        new ViewModelResult(result.getErrorMessage(), ViewModelResultTypes.SUPERVISOR));
            }
        }

    }
    //Todo: es fehlt noch ein Unverified


    public void passwordForgotDataChanged() {
        getFormState().setValue(
                new ForgotPasswordFormState(codeConfirmationIsValid(), passwordFormIsValid(),
                        passwordConfirmationFormIsValid()));
    }

    public MutableLiveData<ForgotPasswordFormState> getFormState() {
        if (formState == null) {
            formState = new MutableLiveData<>();
        }
        return formState;
    }

    public MutableLiveData<ViewModelResult> getLoginResult() {
        if (loginResult == null) {
            loginResult = new MutableLiveData<>();
        }
        return loginResult;
    }

    public MutableLiveData<String> getCode() {
        if (code == null) {
            code = new MutableLiveData<>();
        }
        return code;
    }

    public void setCode(MutableLiveData<String> code) {
        this.code = code;
    }

    public MutableLiveData<String> getNewPassword() {
        if (newPassword == null) {
            newPassword = new MutableLiveData<>();
        }
        return newPassword;
    }

    public void setNewPassword(MutableLiveData<String> newPassword) {
        this.newPassword = newPassword;
    }

    public MutableLiveData<String> getNewPasswordConfirmation() {
        if (newPasswordConfirmation == null) {
            newPasswordConfirmation = new MutableLiveData<>();
        }
        return newPasswordConfirmation;
    }

    public void setNewPasswordConfirmation(
            MutableLiveData<String> newPasswordConfirmation) {
        this.newPasswordConfirmation = newPasswordConfirmation;
    }

    //--------private methods-------------------------------------------

    private Integer passwordFormIsValid() {
        if (newPassword.getValue() == null || newPassword.getValue().equals("")) {
            return R.string.no_password_error;
        }
        if (newPassword.getValue().length() < 8) {
            return R.string.password_to_short_error;
        }
        Matcher m = PASSWORD_PATTERN.matcher(newPassword.getValue());
        if (!m.matches()) {
            return R.string.password_not_safe_error;
        }
        return null;
    }

    private Integer passwordConfirmationFormIsValid() {
        if (newPasswordConfirmation.getValue() == null ||
                newPasswordConfirmation.getValue().equals("")) {
            return R.string.no_password_confirmation_error;
            //Todo: seltsamen Bugg beheben
        }
        if (newPassword.getValue() == null ||
                !newPassword.getValue().equals(newPasswordConfirmation.getValue())) {
            return R.string.passwords_do_not_match_error;
        }
        return null;
    }

    private Integer codeConfirmationIsValid() {
        if (code.getValue() == null || code.getValue().equals("")) {
            return R.string.token_error;
        }
        return null;
    }
}
