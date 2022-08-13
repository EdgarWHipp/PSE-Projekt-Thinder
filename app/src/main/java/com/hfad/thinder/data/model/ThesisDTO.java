package com.hfad.thinder.data.model;

import java.util.List;
import java.util.UUID;

public class ThesisDTO {

    private UUID id;

    private String name;

    private String supervisingProfessor;

    private String motivation;

    private String task;

    private String questions;
    private Supervisor supervisor;

    private List<Byte[]> images;

    private List<Degree> possibleDegrees;

    public ThesisDTO() {
    }

    public ThesisDTO(String name, String supervisingProfessor, String motivation, String task, String questions
            , List<Byte[]> images, List<Degree> possibleDegrees) {
        this.name = name;
        this.supervisingProfessor = supervisingProfessor;
        this.motivation = motivation;
        this.task = task;
        this.questions = questions;
        this.images = images;
        this.possibleDegrees = possibleDegrees;
    }
}
