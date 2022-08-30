package com.hfad.thinder.data.source.remote.okhttp.Utils;

import com.hfad.thinder.data.model.Degree;
import com.hfad.thinder.data.model.Form;
import com.hfad.thinder.data.model.Image;
import com.hfad.thinder.data.model.Thesis;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class SampleThesis {
    public static byte[] bytes = {64, 64, 64};
    public static Image image = new Image(bytes);
    public static Set<Image> images = new HashSet<>(Collections.singletonList(image));

    public static Form form = new Form("Why do you want it?");

    public static String supervisingProfessor = "Dr. Prof. Tamim";
    public static String name = "Lasercoating";
    public static String motivation = "Current topic.";
    public static String task = "Very complex.";

    public static Thesis thesisObject() {
        return new Thesis(supervisingProfessor, name, motivation, task, form, images,
                SampleSupervisor.supervisorObject(), new HashSet<>(SampleStudent.degrees));
    }
}
