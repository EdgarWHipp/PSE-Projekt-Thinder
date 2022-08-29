package com.hfad.thinder.data.util;

import android.util.ArraySet;

import com.hfad.thinder.data.model.Degree;
import com.hfad.thinder.data.model.Form;
import com.hfad.thinder.data.model.Image;
import com.hfad.thinder.data.model.Thesis;
import com.hfad.thinder.data.model.ThesisDTO;
import com.hfad.thinder.data.source.repository.UserRepository;
import com.hfad.thinder.data.source.result.Pair;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Base64;
import java.util.HashSet;
import java.util.Set;

public class ParseUtils {
    private ParseUtils() {
    }

    public static JSONObject thesisToThesisDtoJson(Thesis thesis) throws JSONException {
        JSONArray possibleDegreeJson = new JSONArray();
        for (Degree degree : thesis.getPossibleDegrees()) {
            JSONObject newDegree = new JSONObject();
            newDegree.put("id", degree.getId());
            newDegree.put("degree", degree.getDegree());
            possibleDegreeJson.put(newDegree);
        }
        JSONArray imagesJson = new JSONArray();
        if (thesis.getImages() != null) {
            for (Image image : thesis.getImages()) {
                imagesJson.put(Base64.getEncoder().encodeToString(image.getImage()));
            }
        }
        JSONObject supervisorJson = new JSONObject();
        supervisorJson.put("id", UserRepository.getInstance().getUser().getId());
        supervisorJson.put("type", "SUPERVISOR");

        JSONObject thesisDTOJson = new JSONObject();
        thesisDTOJson.put("name", thesis.getName());
        thesisDTOJson.put("supervisingProfessor", thesis.getSupervisingProfessor());
        thesisDTOJson.put("motivation", thesis.getMotivation());
        thesisDTOJson.put("task", thesis.getTask());
        thesisDTOJson.put("questions", thesis.getForm().getQuestions());
        thesisDTOJson.put("supervisor", supervisorJson);
        thesisDTOJson.put("possibleDegrees", possibleDegreeJson);
        thesisDTOJson.put("images", imagesJson);
        return thesisDTOJson;
    }

    public static Thesis parseDTOtoThesis(ThesisDTO dtoObject) {
        Thesis thesis = new Thesis();
        Set<Image> images= new ArraySet<>();
        Set<Degree> degrees=new ArraySet<>();

        thesis.setId(dtoObject.getId());
        thesis.setForm(new Form(dtoObject.getQuestions()));
        for(String string : dtoObject.getImages()){
            byte[] image = java.util.Base64.getDecoder().decode(string);
            images.add(new Image(image));
        }
        for(Degree degree : dtoObject.getPossibleDegrees()){
            degrees.add(degree);
        }
        thesis.setImages(images);
        thesis.setMotivation(dtoObject.getMotivation());
        thesis.setName(dtoObject.getName());
        thesis.setPossibleDegrees(degrees);
        thesis.setSupervisingProfessor(dtoObject.getSupervisingProfessor());
        thesis.setSupervisor(dtoObject.getSupervisor());
        thesis.setTask(dtoObject.getTask());
        thesis.setRatings(new Pair(dtoObject.getNumPositiveRated(), dtoObject.getNumNegativeRated()));
        return thesis;
    }
}
