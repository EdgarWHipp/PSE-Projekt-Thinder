package com.hfad.thinder.viewmodels.student;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;

import androidx.annotation.ColorInt;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hfad.thinder.data.model.Image;
import com.hfad.thinder.data.model.Thesis;
import com.hfad.thinder.data.source.repository.StudentRepository;
import com.hfad.thinder.data.source.repository.ThesisRepository;
import com.hfad.thinder.data.source.result.Result;
import com.hfad.thinder.data.source.result.Tuple;
import com.hfad.thinder.ui.SwipeScreenCard;
import com.hfad.thinder.ui.SwipeScreenFragment;
import com.hfad.thinder.viewmodels.ThesisCardItem;
import com.hfad.thinder.viewmodels.ViewModelResult;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;
import java.util.Set;
import java.util.UUID;


public class SwipeScreenViewModel extends ViewModel {

  private static final ThesisRepository thesisRepository = ThesisRepository.getInstance();
  private static final StudentRepository studentRepository = StudentRepository.getInstance();
  private Stack<Tuple<UUID, Boolean>> ratings = new Stack();

  private ArrayList<SwipeScreenCard> cardDeck;
  private MutableLiveData<Integer> currentDeckPosition;

  private ArrayList<SwipeScreenFragment.DetailViewStates> detailViewOrder;
  private MutableLiveData<Integer> currentDetailViewPosition;


  public void like() {
    SwipeScreenCard currentCard = cardDeck.get(getCurrentDeckPosition().getValue());
    ratings.push(new Tuple<>(UUID.fromString(currentCard.getUUID()), true));
    incrementCurrentDeckPosition();
  }

  public void dislike(){
    SwipeScreenCard currentCard = cardDeck.get(getCurrentDeckPosition().getValue());
    ratings.push(new Tuple<>(UUID.fromString(currentCard.getUUID()), false));
    incrementCurrentDeckPosition();
  }

  public void redraw(){
    if (decrementCurrentDeckPosition()) {
      ratings.pop();
    }
  }

  public void pushRatings() {
    studentRepository.rateThesis(ratings);
  }

  public SwipeScreenCard getCurrentCard(){
    return getCardDeck().get(getCurrentDeckPosition().getValue());
  }

  public SwipeScreenCard getNextCard(){
    return getCardDeck().get(getCurrentDeckPosition().getValue() + 1);
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

  //----------------private methods-----------------------------------

  private void loadCardDeck(ArrayList<Bitmap> images) {//Todo Attribut entfernen
    cardDeck = new ArrayList<>();
    ArrayList<Thesis> theses = new ArrayList<>();//thesisRepository.getAll().get();
    for (Thesis thesis : theses) {
      ArrayList<Bitmap> images1 = convertImagesToBitmaps(thesis.getImages());
      //SwipeScreenCard swipeScreenCard = new SwipeScreenCard(images,);
      //Todo fertig machen wenn Implementierung Model fertig ist
    }
    //Todo hardgecodeten Teil entfernen
    //ArrayList<Bitmap> images, String UUID, String title, String task, String motivation, String professor, ArrayList<String> coursesOfStudy, String supervisorFirstName, String supervisorLastName, String building, String roomNumber, String phoneNumber, String institute, String email
    ArrayList<String> coursesOfStudy = new ArrayList<>();
    coursesOfStudy.add("Bachelor Mathematik");
    coursesOfStudy.add("Bachelor Informatik");
    cardDeck.add(new SwipeScreenCard(images, " asdfjsdfkajsf öa", "Mülleimerentsorgung", "Mülleimer sind zu Verschrotten", "Ich mag keine Mülleimer", "Dc. Otto Octavius", coursesOfStudy, "Peter", "Parker", "Occorp Tower", "42", "+49 243243434", "Mega Institut", "dcOc@gmail.com"));
    cardDeck.add(new SwipeScreenCard(images, " ajsdf asjflkasf", "Sachen Machen", "Sachen sind zu Verschrotten", "Ich mag Sachen", "Dc. Otto Octavius", coursesOfStudy, "Peter", "Parker", "Occorp Tower", "42", "+49 243243434", "Mega Institut", "dcOc@gmail.com"));
    cardDeck.add(new SwipeScreenCard(images, " asdöfj askks", "Schlafen bei Tag", "24h durchschlafen", "Ich möchte mehr schlafen", "Dc. Otto Octavius", coursesOfStudy, "Peter", "Parker", "Occorp Tower", "42", "+49 243243434", "Mega Institut", "dcOc@gmail.com"));
    cardDeck.add(new SwipeScreenCard(images, "lasjdfjas", "Regale einräumen", "Regale bei Rewe einräumen", "Regale müssen eingeräumt werden", "Dc. Otto Octavius",coursesOfStudy, "Peter", "Parker", "Occorp Tower", "42", "+49 243243434", "Mega Institut", "dcOc@gmail.com"));
    cardDeck.add(new SwipeScreenCard(images, " ajsdfjaös", "Zimmer aufräumen", "Zimmer aufräumen", "Zimmer ist nicht aufgeräumt", "Dc. Otto Octavius", coursesOfStudy, "Peter", "Parker", "Occorp Tower", "42", "+49 243243434", "Mega Institut", "dcOc@gmail.com"));
    cardDeck.add(new SwipeScreenCard(images, " asjdfjasdf", "keine Karten mehr", "hör auf zu Swipen", "Es gibt kein Karten meht", "Dc. Otto Octavius", coursesOfStudy, "Peter", "Parker", "Occorp Tower", "42", "+49 243243434", "Mega Institut", "dcOc@gmail.com"));
    cardDeck.add(new SwipeScreenCard(images, " lsjdfjasf", "keine Karten Mehr", "Hör auf zu Swipen!!!!!", "Es gibt keine Karten mehr", "Dc. Otto Octavius", coursesOfStudy, "Peter", "Parker", "Occorp Tower", "42", "+49 243243434", "Mega Institut", "dcOc@gmail.com"));
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
