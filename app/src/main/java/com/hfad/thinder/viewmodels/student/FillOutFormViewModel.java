package com.hfad.thinder.viewmodels.student;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hfad.thinder.data.source.repository.ThesisRepository;
import com.hfad.thinder.data.model.Form;

import java.util.UUID;

public class FillOutFormViewModel extends ViewModel {
    private static final ThesisRepository thesisRepository = ThesisRepository.getInstance();
    private MutableLiveData<String> questions;
    private MutableLiveData<String> answers;
    private String thesisId;

    public void sendForm() {
        //Todo Implementieren wenn im model vorhanden
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
        this.answers = answers;
    }

    public void setThesisId(String thesisId) {
        this.thesisId = thesisId;
        loadQuestions();
    }

    private void loadQuestions() {
        //UUID uuid = UUID.fromString(thesisId); //Todo kommentierung aufheben
        getQuestions().setValue("Questions");//Todo hardgecodeten Teil löschen
        //questions.setValue(thesisRepository.getThesis(uuid).x.getForm());//Todo hier ändern sich noch Dinge im Model
    }
}
