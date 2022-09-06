package com.hfad.thinder.viewmodels;


import android.graphics.Bitmap;
import java.util.UUID;

/**
 * This class is used to hold the Data of theses, when multiple theses are displayed on Screen. This
 * is the case in {@link com.hfad.thinder.ui.student.LikedThesesFragment LikedThesesFragment} and the
 * {@link com.hfad.thinder.ui.supervisor.ThesisManagerFragment ThesisManagerFragment}.
 */
public class ThesisCardItem {

  private final UUID thesisUUID;
  private final String title;
  private final String task;
  private final Bitmap image;

  /**
   * Creates a new {@link ThesisCardItem} object.
   *
   * @param id    the {@link UUID} of the thesis
   * @param title the title of the thesis
   * @param task  the description of the task
   * @param image an image selected from the images of the thesis.
   */
  public ThesisCardItem(UUID id, String title, String task, Bitmap image) {
    this.thesisUUID = id;
    this.title = title;
    this.task = task;
    this.image = image;
  }

  /**
   * @return the {@link UUID} of the thesis
   */
  public UUID getThesisUUID() {
    return thesisUUID;
  }

  /**
   * @return the title of the thesis
   */
  public String getTitle() {
    return title;
  }

  /**
   * @return the description of the task
   */
  public String getTask() {
    return task;
  }

  /**
   * @return an image selected from the images of the thesis.
   */
  public Bitmap getImage() {
    return image;
  }


}
