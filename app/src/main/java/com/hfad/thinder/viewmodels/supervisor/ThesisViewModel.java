package com.hfad.thinder.viewmodels.supervisor;


import android.graphics.Bitmap;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hfad.thinder.R;
import com.hfad.thinder.ui.CourseOfStudyItem;
import com.hfad.thinder.viewmodels.CoursesOfStudyPicker;
import com.hfad.thinder.viewmodels.ImageGalleryPicker;
import com.hfad.thinder.viewmodels.ViewModelResult;

import java.util.ArrayList;
import java.util.ListIterator;


public abstract class ThesisViewModel extends ViewModel implements CoursesOfStudyPicker, ImageGalleryPicker {
    private MutableLiveData<String> title;
    private MutableLiveData<String> task;
    private MutableLiveData<String> motivation;
    private MutableLiveData<String> professor;
    private MutableLiveData<ArrayList<Bitmap>> images;
    private MutableLiveData<String> questions;

    private MutableLiveData<Bitmap> currentImage;
    private ListIterator<Bitmap> iterator;
    private MutableLiveData<String> selectedCoursesOfStudy;
    private MutableLiveData<ArrayList<CourseOfStudyItem>> coursesOfStudyList;
    private MutableLiveData<EditThesisFormState> formState;
    private MutableLiveData<ViewModelResult> saveResult;

    public abstract void save();

    public void thesisDataChanged() {
        getFormState().setValue(new EditThesisFormState(checkTitle(), checkTask(), checkMotivation(), checkQuestions(), checkProfessor(), checkCoursesOfStudy(), chekImages()));
    }

    @Override
    public void makeCourseOfStudySelection(int position, boolean selection) {
        ArrayList<CourseOfStudyItem> copy = getCoursesOfStudyList().getValue();
        copy.get(position).setPicked(selection);
        getCoursesOfStudyList().setValue(copy);
    }

    @Override
    public ArrayList<CourseOfStudyItem> getElements() {
        return getCoursesOfStudyList().getValue();
    }

    @Override
    public MutableLiveData<ArrayList<CourseOfStudyItem>> getCoursesOfStudyList() {
        if (coursesOfStudyList == null) {
            coursesOfStudyList = new MutableLiveData<>();
            loadCoursesOfStudy();
        }
        return coursesOfStudyList;
    }

    public MutableLiveData<Bitmap> getCurrentImage() {
        if(currentImage == null){
            currentImage = new MutableLiveData<>();
        }
        return currentImage;
    }

    public void nextImage(){
        if(iterator.hasNext())
            getCurrentImage().setValue(iterator.next());
    }

    public void previousImage() {
        if (iterator.hasPrevious())
            getCurrentImage().setValue(iterator.previous());
    }


    //----------------getter and setter -----------------------------------------------------------

    public MutableLiveData<EditThesisFormState> getFormState() {
        if (formState == null) {
            formState = new MutableLiveData<>();
        }
        return formState;
    }


    public MutableLiveData<String> getTitle() {
        if (title == null) {
            title = new MutableLiveData<>();

        }
        return title;
    }


    public void setTitle(MutableLiveData<String> title) {
        this.title = title;
    }

    public MutableLiveData<String> getTask() {
        if (task == null) {
            task = new MutableLiveData<>();

        }
        return task;
    }

    public void setTask(MutableLiveData<String> task) {
        this.task = task;
    }

    public MutableLiveData<String> getMotivation() {
        if (motivation == null) {
            motivation = new MutableLiveData<>();

        }
        return motivation;
    }

    public void setMotivation(
            MutableLiveData<String> motivation) {
        this.motivation = motivation;
    }

    public MutableLiveData<String> getProfessor() {
        if (professor == null) {
            professor = new MutableLiveData<>();

        }
        return professor;
    }

    public void setProfessor(MutableLiveData<String> professor) {
        this.professor = professor;
    }

    public MutableLiveData<String> getSelectedCoursesOfStudy() {
        if (selectedCoursesOfStudy == null) {
            selectedCoursesOfStudy = new MutableLiveData<>();

        }
        return selectedCoursesOfStudy;
    }

    public void setSelectedCoursesOfStudy(
            MutableLiveData<String> fieldsOfStudy) {
        this.selectedCoursesOfStudy = fieldsOfStudy;
    }



    public MutableLiveData<ArrayList<Bitmap>> getImages() {
        if (images == null) {
            images = new MutableLiveData<>();
        }
        return images;
    }

    public void setImages(
            MutableLiveData<ArrayList<Bitmap>> images) {
        iterator = images.getValue().listIterator();
        getCurrentImage().setValue(iterator.next());
        this.images = images;
    }

    public MutableLiveData<String> getQuestions() {
        if (questions == null) {
            questions = new MutableLiveData<>();
        }
        return questions;
    }

    public void setQuestions(MutableLiveData<String> questions) {
        this.questions = questions;
    }



    public MutableLiveData<ViewModelResult> getSaveResult() {
        if (saveResult == null) {
            saveResult = new MutableLiveData<>();
        }
        return saveResult;
    }

    //-----------------------------private methods---------------------------------------------------------------

    private void loadCoursesOfStudy() {
        ArrayList<String> allDegrees = ThesisUtility.DEGREE_REPOSITORY.getAllDegrees();
        ArrayList<String> selectedDegrees = ThesisUtility.THESIS_REPOSITORY.getSelectedDegrees();
        ArrayList<CourseOfStudyItem> courseOfStudyItems = new ArrayList<>();
        for (String degree : allDegrees) {
            if (selectedDegrees.contains(degree)) {
                courseOfStudyItems.add(new CourseOfStudyItem(degree, true));
                selectedDegrees.add(degree);
            }else{
                courseOfStudyItems.add(new CourseOfStudyItem(degree, false));
            }
        }
        coursesOfStudyList = new MutableLiveData<>();
        coursesOfStudyList.setValue(courseOfStudyItems);

        String degreesAsString = String.join(", ", selectedDegrees);

        selectedCoursesOfStudy = new MutableLiveData<>();
        selectedCoursesOfStudy.setValue(degreesAsString);
    }

    private Integer checkTitle() {
        if (getTitle().getValue() == null || getTitle().getValue().equals("")) {
            return R.string.no_title_error;
        }
        return null;
    }

    private Integer checkTask() {
        if(getTask().getValue() == null || getTask().getValue().equals("")) {
            return R.string.no_task_description_error;
        }
        return null;
    }

    private Integer checkMotivation() {
        if (getMotivation().getValue() == null || getMotivation().getValue().equals("")) {
            return R.string.no_motivation_error;
        }
        return null;
    }

    private Integer checkQuestions() {
        if (getQuestions().getValue() == null || getQuestions().getValue().equals("")) {
            return R.string.no_questions_error;
        }
        return null;
    }

    private Integer checkProfessor() {
        if (getProfessor().getValue() == null || getProfessor().getValue().equals("")) {
            return R.string.no_professor_error;
        }
        return null;
    }

    private Integer checkCoursesOfStudy() {
        if (getSelectedCoursesOfStudy().getValue() == null || getSelectedCoursesOfStudy().getValue().equals("")) {
            return R.string.no_courses_of_study_error;
        }
        return null;
    }

    private boolean chekImages() {
        return getImages().getValue() != null;
    }
}



