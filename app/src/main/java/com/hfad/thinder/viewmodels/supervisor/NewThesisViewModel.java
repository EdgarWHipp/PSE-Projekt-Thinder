package com.hfad.thinder.viewmodels.supervisor;

import android.graphics.Bitmap;
import android.media.Image;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hfad.thinder.ui.CourseOfStudyItem;
import com.hfad.thinder.viewmodels.CoursesOfStudyPicker;

import java.util.ArrayList;

public class NewThesisViewModel extends ViewModel implements CoursesOfStudyPicker {
    private MutableLiveData<String> name;
    private MutableLiveData<String> taskDescription;
    private MutableLiveData<String> motivationDescription;
    private MutableLiveData<String> professor;
    private MutableLiveData<ArrayList<String>> fieldsOfStudy;
    private MutableLiveData<ArrayList<Bitmap>> images;

    public void save() {
        //Todo: implementieren
    }

    @Override
    public void makeCourseOfStudySelection(int position, boolean selection) {

    }

    @Override
    public ArrayList<CourseOfStudyItem> getElements() {
        return null;
    }

    @Override
    public MutableLiveData<ArrayList<CourseOfStudyItem>> getCoursesOfStudyList() {
        return null;
    }

    //------------------getter and setter-----------------------------------------------------

    public MutableLiveData<String> getName() {
        if (name == null) {
            name = new MutableLiveData<>();
        }
        return name;
    }


    public void setName(MutableLiveData<String> name) {
        this.name = name;
    }

    public MutableLiveData<String> getTaskDescription() {
        if (taskDescription == null) {
            taskDescription = new MutableLiveData<>();
        }
        return taskDescription;
    }

    public void setTaskDescription(MutableLiveData<String> taskDescription) {
        this.taskDescription = taskDescription;
    }

    public MutableLiveData<String> getMotivationDescription() {
        if (motivationDescription == null) {
            motivationDescription = new MutableLiveData<>();
        }
        return motivationDescription;
    }

    public void setMotivationDescription(
            MutableLiveData<String> motivationDescription) {
        this.motivationDescription = motivationDescription;
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

    public MutableLiveData<ArrayList<String>> getFieldsOfStudy() {
        if (fieldsOfStudy == null) {
            fieldsOfStudy = new MutableLiveData<>();
        }
        return fieldsOfStudy;
    }

    public void setFieldsOfStudy(
            MutableLiveData<ArrayList<String>> fieldsOfStudy) {
        this.fieldsOfStudy = fieldsOfStudy;
    }

    public MutableLiveData<ArrayList<Bitmap>> getImages() {
        if (images == null) {
            images = new MutableLiveData<>();
        }
        return images;
    }

    public void setImages(
            MutableLiveData<ArrayList<Bitmap>> images) {
        this.images = images;
    }
}
