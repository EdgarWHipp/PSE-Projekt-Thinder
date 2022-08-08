package com.hfad.thinder.viewmodels.student;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

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
  private MutableLiveData<Boolean> currentHasImages;
  private MutableLiveData<Boolean> nextHasImages;


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
    Boolean currentCardHasImages = currentCard.getImages().size() > 0;
    getCurrentHasImages().setValue(currentCardHasImages);
    return currentCard;
  }

  public SwipeScreenCard getNextCard(){
    SwipeScreenCard nextCard = getCardDeck().get(getCurrentDeckPosition().getValue()+1);
    Boolean currentCardHasImages = nextCard.getImages().size() > 0;
    getNextHasImages().setValue(currentCardHasImages);
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

  public MutableLiveData<Boolean> getCurrentHasImages() {
    if(currentHasImages == null){
      currentHasImages = new MutableLiveData<>();
    }
    return currentHasImages;
  }

  public MutableLiveData<Boolean> getNextHasImages() {
    if(nextHasImages == null){
      nextHasImages = new MutableLiveData<>();
    }
    return nextHasImages;
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
    cardDeck.add(new SwipeScreenCard(images, UUID.randomUUID(), "Mülleimerentsorgung", "Mülleimer sind zu Verschrotten", "Ich mag keine Mülleimer", "Dc. Otto Octavius", coursesOfStudy, "Peter", "Parker", "Occorp Tower", "42", "+49 243243434", "Mega Institut", "dcOc@gmail.com"));
    cardDeck.add(new SwipeScreenCard(images, UUID.randomUUID(), "Sachen Machen", "Sachen sind zu Verschrotten", "Ich mag Sachen", "Dc. Otto Octavius", coursesOfStudy, "Peter", "Parker", "Occorp Tower", "42", "+49 243243434", "Mega Institut", "dcOc@gmail.com"));
    cardDeck.add(new SwipeScreenCard(images, UUID.randomUUID(), "Schlafen bei Tag", "24h durchschlafen", "Ich möchte mehr schlafen", "Dc. Otto Octavius", coursesOfStudy, "Peter", "Parker", "Occorp Tower", "42", "+49 243243434", "Mega Institut", "dcOc@gmail.com"));
    cardDeck.add(new SwipeScreenCard(images, UUID.randomUUID(), "Regale einräumen", "Regale bei Rewe einräumen", "Regale müssen eingeräumt werden", "Dc. Otto Octavius",coursesOfStudy, "Peter", "Parker", "Occorp Tower", "42", "+49 243243434", "Mega Institut", "dcOc@gmail.com"));
    cardDeck.add(new SwipeScreenCard(images, UUID.randomUUID(), "Zimmer aufräumen", "Zimmer aufräumen", "Zimmer ist nicht aufgeräumt", "Dc. Otto Octavius", coursesOfStudy, "Peter", "Parker", "Occorp Tower", "42", "+49 243243434", "Mega Institut", "dcOc@gmail.com"));
    cardDeck.add(new SwipeScreenCard(images, UUID.randomUUID(), "keine Karten mehr", "hör auf zu Swipen", "Es gibt kein Karten meht", "Dc. Otto Octavius", coursesOfStudy, "Peter", "Parker", "Occorp Tower", "42", "+49 243243434", "Mega Institut", "dcOc@gmail.com"));
    cardDeck.add(new SwipeScreenCard(images, UUID.randomUUID(), "keine Karten Mehr", "Hör auf zu Swipen!!!!!", "Es gibt keine Karten mehr", "Dc. Otto Octavius", coursesOfStudy, "Peter", "Parker", "Occorp Tower", "42", "+49 243243434", "Mega Institut", "dcOc@gmail.com"));
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
