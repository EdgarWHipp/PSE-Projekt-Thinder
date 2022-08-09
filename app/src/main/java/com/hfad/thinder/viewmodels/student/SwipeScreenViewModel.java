package com.hfad.thinder.viewmodels.student;

import static android.content.ContentValues.TAG;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hfad.thinder.data.model.Image;
import com.hfad.thinder.data.model.Thesis;
import com.hfad.thinder.data.source.repository.StudentRepository;
import com.hfad.thinder.data.source.repository.ThesisRepository;
import com.hfad.thinder.data.source.result.Pair;
import com.hfad.thinder.ui.student.SwipeScreenCard;
import com.hfad.thinder.ui.student.SwipeScreenFragment;

import java.util.ArrayList;
import java.util.Stack;
import java.util.Set;
import java.util.UUID;


public class SwipeScreenViewModel extends ViewModel {

  private static final ThesisRepository thesisRepository = ThesisRepository.getInstance();
  private static final StudentRepository studentRepository = StudentRepository.getInstance();
  private Stack<Pair<UUID, Boolean>> ratings = new Stack();

  private ArrayList<SwipeScreenCard> cardDeck;
  private MutableLiveData<Integer> currentDeckPosition;

  private ArrayList<SwipeScreenFragment.DetailViewStates> detailViewOrder;
  private MutableLiveData<Integer> currentDetailViewPosition;
  private MutableLiveData<Bitmap> currentImage;
  private MutableLiveData<Bitmap> nextImage;

  private MutableLiveData<String> coursesOfStudy;
  private MutableLiveData<String> superVisorName;
  private MutableLiveData<String> phoneNumber;
  private MutableLiveData<String> building;
  private MutableLiveData<String> mail;
  private MutableLiveData<String> professorName;
  private MutableLiveData<String> institute;
  private MutableLiveData<String> currentTask;
  private MutableLiveData<String> currentMotivation;


  public void like() {
    if(getCurrentDeckPosition().getValue() >= cardDeck.size() - 2){
      return;
    }
    SwipeScreenCard currentCard = cardDeck.get(getCurrentDeckPosition().getValue());
    ratings.push(new Pair<>(currentCard.getUUID(), true));
    incrementCurrentDeckPosition();
  }

  public void dislike(){
    if(getCurrentDeckPosition().getValue() >= cardDeck.size() - 2){
      return;
    }
    SwipeScreenCard currentCard = cardDeck.get(getCurrentDeckPosition().getValue());
    ratings.push(new Pair<>(currentCard.getUUID(), false));
    incrementCurrentDeckPosition();
  }

  public void redraw(){
    if (decrementCurrentDeckPosition()) {
      ratings.pop();
    }
  }

  public void pushRatings() {
    if(ratings.size() != 0){
      studentRepository.rateThesis(ratings);
    }
  }

  public SwipeScreenCard getCurrentCard(){
    SwipeScreenCard currentCard = getCardDeck().get(getCurrentDeckPosition().getValue());
    return currentCard;
  }

  public SwipeScreenCard getNextCard(){
    SwipeScreenCard nextCard = getCardDeck().get(getCurrentDeckPosition().getValue()+1);
    return nextCard;
  }

  public SwipeScreenFragment.DetailViewStates getCurrentDetailViewState(){
    return getDetailViewOrder().get(getCurrentDetailViewPosition().getValue());
  }

  public Bitmap getCurrentDetailViewImage(){
    if(getCurrentDetailViewPosition().getValue() > 0 && getCurrentDetailViewPosition().getValue() <= getCurrentCard().getImages().size())
      return getCurrentCard().getImages().get(getCurrentDetailViewPosition().getValue() - 1);
    return null;
  }


  public boolean incrementCurrentDetailViewPosition() {
    Integer oldPosition = getCurrentDetailViewPosition().getValue();
    if (getCurrentDetailViewPosition().getValue() < getDetailViewOrder().size() - 1) {
      getCurrentDetailViewPosition().setValue(oldPosition + 1);
      return true;
    }
    return false;
  }

  public boolean decrementCurrentDetailViewPosition() {
    Integer oldPosition = getCurrentDetailViewPosition().getValue();
    if (getCurrentDetailViewPosition().getValue() > 0) {
      getCurrentDetailViewPosition().setValue(oldPosition -1);
      return true;
    }
    return false;
  }


  //--------------getter and setter---------------------------------


  private MutableLiveData<Integer> getCurrentDetailViewPosition() {
    if(currentDetailViewPosition == null){
      currentDetailViewPosition = new MutableLiveData<>();
      currentDetailViewPosition.setValue(0);
    }
    return currentDetailViewPosition;
  }

  private ArrayList<SwipeScreenFragment.DetailViewStates> getDetailViewOrder() {
    if (detailViewOrder == null) {
      detailViewOrder = new ArrayList<>();
      setDetailedViewOrder();
    }
    return detailViewOrder;
  }

  private ArrayList<SwipeScreenCard> getCardDeck() {
    if (cardDeck == null) {
      loadCardDeck(images);
    }
    return cardDeck;
  }

  public MutableLiveData<Integer> getCurrentDeckPosition() {
    if(currentDeckPosition == null){
      currentDeckPosition = new MutableLiveData<>();
      currentDeckPosition.setValue(0);
    }
    return currentDeckPosition;
  }

  public MutableLiveData<Bitmap> getCurrentImage() {
    if(currentImage == null)
      currentImage = new MutableLiveData<>();
    if(getCurrentCard().getImages().size() == 0)
      return null;
    currentImage.setValue(getCurrentCard().getImages().get(0));
    return currentImage;
  }

  public MutableLiveData<Bitmap> getNextImage() {
    if(nextImage == null)
      nextImage = new MutableLiveData<>();
    if(getNextCard().getImages().size() == 0)
      return null;
    nextImage.setValue(getNextCard().getImages().get(0));
    return nextImage;
  }

  public MutableLiveData<String> getCoursesOfStudy() {
    if(coursesOfStudy == null)
      coursesOfStudy = new MutableLiveData<>();
    if(getCurrentCard().getCoursesOfStudy() == null)
      return null;
    coursesOfStudy.setValue(String.join("\n", getCurrentCard().getCoursesOfStudy()));
    return coursesOfStudy;
  }

  public MutableLiveData<String> getSuperVisorName() {
    if(superVisorName == null)
      superVisorName = new MutableLiveData<>();
    if(getCurrentCard().getSupervisorLastName() == null || getCurrentCard().getSupervisorFirstName() == null)
      return null;
    superVisorName.setValue(String.join(" ",getCurrentCard().getAcademicDegree(), getCurrentCard().getSupervisorFirstName(), getCurrentCard().getSupervisorLastName()));
    return superVisorName;
  }

  public MutableLiveData<String> getPhoneNumber() {
    if(phoneNumber==null)
      phoneNumber = new MutableLiveData<>();
    if(getCurrentCard().getPhoneNumber() == null)
      return null;
    phoneNumber.setValue(getCurrentCard().getPhoneNumber());
    return phoneNumber;
  }

  public MutableLiveData<String> getBuilding() {
    if(building==null)
      building=new MutableLiveData<>();
    if (getCurrentCard().getRoomNumber() == null || getCurrentCard().getBuilding() == null)
      return null;
    building.setValue(String.join(" ", getCurrentCard().getRoomNumber(), getCurrentCard().getBuilding()));
    return building;
  }

  public MutableLiveData<String> getMail() {
    if (mail == null)
      mail = new MutableLiveData<>();
    if(getCurrentCard().getEmail() == null)
      return null;
    mail.setValue(getCurrentCard().getEmail());
    return mail;
  }

  public MutableLiveData<String> getProfessorName() {
    if(professorName == null)
      professorName = new MutableLiveData<>();
    if(getCurrentCard().getProfessor() == null)
      return null;
    professorName.setValue(getCurrentCard().getProfessor());
    return professorName;
  }

  public MutableLiveData<String> getInstitute() {
    if(institute == null)
      institute = new MutableLiveData<>();
    if(getCurrentCard().getInstitute() == null)
      return null;
    institute.setValue(getCurrentCard().getInstitute());
    return institute;
  }

  public MutableLiveData<String> getCurrentTask() {
    if(currentTask == null)
      currentTask = new MutableLiveData<>();
    if(getCurrentCard().getTask() == null)
      return null;
    currentTask.setValue(getCurrentCard().getTask());
    return currentTask;
  }

  public MutableLiveData<String> getCurrentMotivation() {
    if(currentMotivation == null)
      currentMotivation = new MutableLiveData<>();
    if(getCurrentCard().getMotivation() == null)
      return null;
    currentMotivation.setValue(getCurrentCard().getMotivation());
    return currentMotivation;
  }


  //----------------private methods-----------------------------------

  private void loadCardDeck(ArrayList<Bitmap> images) {//Todo Attribut entfernen
    cardDeck = new ArrayList<>();
    ArrayList<Thesis> theses = new ArrayList<>();//thesisRepository.getAll().get();
    for (Thesis thesis : theses) {
      ArrayList<Bitmap> bitmaps = convertImagesToBitmaps(thesis.getImages());
      //SwipeScreenCard swipeScreenCard = new SwipeScreenCard(images,);
      //Todo fertig machen wenn Implementierung Model fertig ist
    }
    //Todo hardgecodeten Teil entfernen
    //ArrayList<Bitmap> images, String UUID, String title, String task, String motivation, String professor, ArrayList<String> coursesOfStudy, String supervisorFirstName, String supervisorLastName, String building, String roomNumber, String phoneNumber, String institute, String email
    ArrayList<String> coursesOfStudy = new ArrayList<>();
    coursesOfStudy.add("Bachelor Mathematik");
    coursesOfStudy.add("Bachelor Informatik");
    cardDeck.add(new SwipeScreenCard(new ArrayList<>(), UUID.randomUUID(), "Mülleimerentsorgung", "Mülleimer sind zu Verschrotten", "Ich mag keine Mülleimer", "Dc. Otto Octavius", coursesOfStudy, "Peter", "Parker", "Occorp Tower", "42", "+49 243243434", "Mega Institut", "dcOc@gmail.com", "B. Sc."));
    cardDeck.add(new SwipeScreenCard(new ArrayList<>(), UUID.randomUUID(), "Sachen Machen", "Sachen sind zu Verschrotten", "Ich mag Sachen", "Dc. Otto Octavius", coursesOfStudy, "Peter", "Parker", "Occorp Tower", "42", "+49 243243434", "Mega Institut", "dcOc@gmail.com", "B. Sc."));
    cardDeck.add(new SwipeScreenCard(new ArrayList<>(), UUID.randomUUID(), "Schlafen bei Tag", "24h durchschlafen", "Ich möchte mehr schlafen", "Dc. Otto Octavius", coursesOfStudy, "Peter", "Parker", "Occorp Tower", "42", "+49 243243434", "Mega Institut", "dcOc@gmail.com", "B. Sc."));
    cardDeck.add(new SwipeScreenCard(new ArrayList<>(), UUID.randomUUID(), "Regale einräumen", "Regale bei Rewe einräumen", "Regale müssen eingeräumt werden", "Dc. Otto Octavius",coursesOfStudy, "Peter", "Parker", "Occorp Tower", "42", "+49 243243434", "Mega Institut", "dcOc@gmail.com", "B. Sc."));
    cardDeck.add(new SwipeScreenCard(new ArrayList<>(), UUID.randomUUID(), "Zimmer aufräumen", "Zimmer aufräumen", "Zimmer ist nicht aufgeräumt", "Dc. Otto Octavius", coursesOfStudy, "Peter", "Parker", "Occorp Tower", "42", "+49 243243434", "Mega Institut", "dcOc@gmail.com", "B. Sc."));
    cardDeck.add(new SwipeScreenCard(new ArrayList<>(), UUID.randomUUID(), null, null, null, null, null, null, null, null, null, null, null, null, null));
    cardDeck.add(new SwipeScreenCard(new ArrayList<>(), UUID.randomUUID(), null, null, null, null, null, null, null, null, null, null, null, null, null));
  }

  private ArrayList<Bitmap> convertImagesToBitmaps(Set<Image> imageSet) {
    ArrayList<Bitmap> images = new ArrayList<>();
    for (Image image : imageSet) {
      Bitmap bitmap = BitmapFactory.decodeByteArray(image.getImage(), 0, image.getImage().length);
      images.add(bitmap);
    }
    return images;
  }

  private boolean incrementCurrentDeckPosition() {
    Integer oldPosition = getCurrentDeckPosition().getValue();
    if (oldPosition < getCardDeck().size() - 2) {
      setDetailedViewOrder();
      getCurrentDeckPosition().setValue(oldPosition + 1);
      return true;
    }
    return false;
  }

  private boolean decrementCurrentDeckPosition(){
    Integer oldPosition = getCurrentDeckPosition().getValue();
    if (oldPosition > 0) {
      setDetailedViewOrder();
      getCurrentDeckPosition().setValue(oldPosition - 1);
      return true;
    }
    return false;
  }

  private void setDetailedViewOrder() {
    getCurrentDetailViewPosition().setValue(0);
    getDetailViewOrder().clear();
    detailViewOrder.add(SwipeScreenFragment.DetailViewStates.TOP);
    for(int i = 0; i < cardDeck.get(getCurrentDeckPosition().getValue()).getImages().size(); ++i){
      detailViewOrder.add(SwipeScreenFragment.DetailViewStates.IMAGE);
    }
    detailViewOrder.add(SwipeScreenFragment.DetailViewStates.TEXT);
    detailViewOrder.add(SwipeScreenFragment.DetailViewStates.INFO);
  }

  //Todo löschen
  private ArrayList<Bitmap> images;

  public void setImages(ArrayList<Bitmap> images) {
    this.images = images;
  }
}
