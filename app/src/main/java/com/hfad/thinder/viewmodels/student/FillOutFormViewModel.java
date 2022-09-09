package com.hfad.thinder.viewmodels.student;


import android.os.AsyncTask;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.hfad.thinder.data.model.Form;
import com.hfad.thinder.data.source.repository.StudentRepository;
import com.hfad.thinder.data.source.repository.ThesisRepository;
import com.hfad.thinder.data.source.result.Result;
import com.hfad.thinder.viewmodels.ViewModelResult;
import com.hfad.thinder.viewmodels.ViewModelResultTypes;
import java.util.UUID;

/**
 * A class providing a {@link ViewModel} for the {@link com.hfad.thinder.ui.student.FillOutFormFragment FillOutFormFragment}.
 */
public class FillOutFormViewModel extends ViewModel {
  private static final ThesisRepository thesisRepository = ThesisRepository.getInstance();
  private static final StudentRepository studentRepository = StudentRepository.getInstance();
  private MutableLiveData<String> questions;
  private MutableLiveData<String> answers;
  private MutableLiveData<Boolean> isDataValid;
  private MutableLiveData<ViewModelResult> sendResult;
  private UUID thesisId;

  /**
   * Use this method to send the answers given by the Student to the model. In order to use this
   * method the value of {@link #getAnswers()} must not be null.
   */
  public void sendForm() {
    Form form = new Form(getQuestions().getValue());
    form.setAnswers(getAnswers().getValue());
    new SendTask().execute(form);
  }

  /**
   * Use this method to check whether the data provided by the student is valid.
   */
  public void formDataChanged() {
    Boolean value = getQuestions().getValue() != null && !getQuestions().getValue().equals("") &&
        getAnswers().getValue() != null && !getAnswers().getValue().equals("");
    getIsDataValid().postValue(value);
  }

  //------------------getter and setter-------------------------------------------------------------

  /**
   * @return a {@link MutableLiveData} object containing the questions given by the Supervisor as a
   * {@link String}.
   */
  public MutableLiveData<String> getQuestions() {
    if (questions == null) {
      questions = new MutableLiveData<>();
    }
    return questions;
  }

  /**
   * @return a {@link MutableLiveData} object containing the answers given by the student as a
   * {@link String}.
   */
  public MutableLiveData<String> getAnswers() {
    if (answers == null) {
      answers = new MutableLiveData<>();
    }
    return answers;
  }

  /**
   * @param answers a {@link MutableLiveData} object containing the answers given by the student as
   *                a {@link String}.
   */
  public void setAnswers(MutableLiveData<String> answers) {
    formDataChanged();
    this.answers = answers;
  }

  /**
   * @param thesisId the {@link UUID} of the {@link com.hfad.thinder.data.model.Thesis Thesis},
   *                 which contains this {@link Form}.
   */
  public void setThesisId(String thesisId) {
    this.thesisId = UUID.fromString(thesisId);
    loadQuestions();
  }

  /**
   * @return true if the data is valid according to the {@link #formDataChanged()} method, false
   * otherwise.
   */
  public MutableLiveData<Boolean> getIsDataValid() {
    if (isDataValid == null) {
      isDataValid = new MutableLiveData<>();
    }
    return isDataValid;
  }

  /**
   * @return a {@link MutableLiveData} object containing the {@link ViewModelResult} of the
   * {@link #sendForm()} operation.
   */
  public MutableLiveData<ViewModelResult> getSendResult() {
    if (sendResult == null) {
      sendResult = new MutableLiveData<>();
    }
    return sendResult;
  }

  //---------------------private methods------------------------------------------------------------

  private void loadQuestions() {
    String questions = thesisRepository.getThesisMap(false).get(thesisId).getForm().getQuestions();
    getQuestions().setValue(questions);
    formDataChanged();
  }

  private class SendTask extends AsyncTask<Form, Void, Result> {

    @Override
    protected Result doInBackground(Form... forms) {
      return studentRepository.sendForm(forms[0], thesisId);
    }

    @Override
    protected void onPostExecute(Result result) {
      if (result.getSuccess()) {
        getSendResult().setValue(new ViewModelResult(null, ViewModelResultTypes.SUCCESSFUL));
      } else {
        getSendResult().setValue(
            new ViewModelResult(result.getErrorMessage(), ViewModelResultTypes.ERROR));
      }
      getSendResult().setValue(null);
    }
  }
}
