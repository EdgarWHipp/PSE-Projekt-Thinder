package com.hfad.thinder.data.source.remote.okhttp;

import com.hfad.thinder.data.model.Degree;
import com.hfad.thinder.data.model.Form;
import com.hfad.thinder.data.model.Image;
import com.hfad.thinder.data.model.Supervisor;
import com.hfad.thinder.data.model.Thesis;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class SampleThesis {
    public static byte[] bytes = {64, 64, 64};
    public static Image image = new Image(bytes);
    public static Set<Image> images = new HashSet<>(Collections.singletonList(image));

    public static Form form = new Form("Why do you want it?");

    public static String supervisingProfessor = "Dr. Prof. Tamim";
    public static String name = "Lasercoating";
    public static String motivation = "Current topic.";
    public static String task = "Very complex.";
    public static int positivelyRatedNum = 3;
    public static int negativelyRatedNum = 2;
    public static HashSet<Degree> degrees = new HashSet<>();
    public static Supervisor supervisor = SampleSupervisor.supervisorObject();
    public static UUID id = new UUID(0x8a3a5503cd414a9aL, 0xa86eaa2d64c4d314L);

    public static Thesis thesisObject() {
        return new Thesis(supervisingProfessor, name, motivation, task, form, images,
                supervisor, degrees);
    }

    public static JSONObject getThesisJson() throws JSONException {
        return new JSONObject()
                .put("id", id)
                .put("name", name)
                .put("supervisingProfessor", supervisingProfessor)
                .put("motivation", motivation)
                .put("task", task)
                .put("questions", form.getQuestions())
                .put("positivelyRatedNum", positivelyRatedNum)
                .put("negativelyRatedNum", negativelyRatedNum)
                .put("supervisor", SampleSupervisor.getSupervisorJson())
                .put("images", new JSONArray())
                .put("possibleDegrees", new JSONArray());
    }
}
