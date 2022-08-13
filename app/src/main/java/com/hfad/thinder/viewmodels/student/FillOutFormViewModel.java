package com.hfad.thinder.viewmodels.student;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hfad.thinder.data.model.Form;
import com.hfad.thinder.data.source.repository.StudentRepository;
import com.hfad.thinder.data.source.repository.ThesisRepository;
import com.hfad.thinder.viewmodels.ViewModelResult;

import java.util.UUID;

public class FillOutFormViewModel extends ViewModel {
    private static final ThesisRepository thesisRepository = ThesisRepository.getInstance();
    private static final StudentRepository studentRepository = StudentRepository.getInstance();
    private MutableLiveData<String> questions;
    private MutableLiveData<String> answers;
    private MutableLiveData<Boolean> isDataValid;
    private MutableLiveData<ViewModelResult> sendResult;
    private UUID thesisId;

    public void sendForm() {
        Form form = new Form(getQuestions().getValue());
        form.setAnswers(getAnswers().getValue());
        studentRepository.sendForm(form, thesisId);
    }

    public void formDataChanged() {
        Boolean value = getQuestions().getValue() != null && !getQuestions().getValue().equals("") &&
                getAnswers().getValue() != null && !getAnswers().getValue().equals("");
        getIsDataValid().postValue(value);
    }

    public MutableLiveData<String> getQuestions() {
        if (questions == null) {
            questions = new MutableLiveData<>();
        }
        return questions;
    }

    public MutableLiveData<String> getAnswers() {
        if (answers == null) {
            answers = new MutableLiveData<>();
        }
        return answers;
    }

    public void setAnswers(MutableLiveData<String> answers) {
        formDataChanged();
        this.answers = answers;
    }

    public void setThesisId(String thesisId) {
        this.thesisId = UUID.fromString(thesisId);
        loadQuestions();
    }

    public MutableLiveData<Boolean> getIsDataValid() {
        if (isDataValid == null) {
            isDataValid = new MutableLiveData<>();
        }
        return isDataValid;
    }

    public MutableLiveData<ViewModelResult> getSendResult() {
        if (sendResult == null) {
            sendResult = new MutableLiveData<>();
        }
        return sendResult;
    }

    private void loadQuestions() {
        String questions = thesisRepository.getThesis(thesisId).getFirst().getForm().getQuestions();
        getQuestions().setValue(questions);
        formDataChanged();
    }
}
