
package recipesearch;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.transform.Shear;
import javafx.util.Callback;
import se.chalmers.ait.dat215.lab2.Ingredient;
import se.chalmers.ait.dat215.lab2.Recipe;
import se.chalmers.ait.dat215.lab2.RecipeDatabase;


public class RecipeSearchController implements Initializable {

    @FXML
    protected ComboBox mainIngredientBox;
    @FXML
    protected ComboBox cuisineBox;
    @FXML
    protected RadioButton allDifficultiesButton;
    @FXML
    protected RadioButton easyButton;
    @FXML
    protected RadioButton mediumButton;
    @FXML
    protected RadioButton hardButton;
    @FXML
    protected Spinner maxPriceSpinner;
    @FXML
    protected Slider maxTimeSlider;
    @FXML
    protected FlowPane recipeFlowPane;
    @FXML
    protected Label timeLabel;
    @FXML
    protected Label recipeNameLabel;
    @FXML
    protected ImageView recipeImageView;
    @FXML
    protected Button closeRecipeButton;
    @FXML
    protected ImageView closeImageView;
    @FXML
    protected AnchorPane recipeDetailPane;
    @FXML
    protected SplitPane searchPane;

    @FXML
    protected ImageView recipeFlag;
    @FXML
    protected TextArea recipeIngredients;
    @FXML
    protected TextArea recipeSmallDescription;
    @FXML
    protected TextArea recipeDescription;
    @FXML
    protected ImageView recipeMainIngredient;
    @FXML
    protected ImageView recipeDifficulty;
    @FXML
    protected Label recipeTime;
    @FXML
    protected Label recipePrice;
    @FXML
    protected Label recipePortions;



    ToggleGroup difficultyToggleGroup;

    RecipeDatabase db = RecipeDatabase.getSharedInstance();
    RecipeBackendController rbc = new RecipeBackendController();
    private Map<String, RecipeListItem> recipeListItemMap = new HashMap<String, RecipeListItem>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        for (Recipe recipe : rbc.getRecipes()) {
            RecipeListItem recipeListItem = new RecipeListItem(recipe, this);
            recipeListItemMap.put(recipe.getName(), recipeListItem);
        }

        updateRecipeList();
        mainIngredientBox.getItems().addAll("Visa alla", "Kött", "Fisk", "Kyckling", "Vegetarisk");
        mainIngredientBox.getSelectionModel().select("Visa alla");

        cuisineBox.getItems().addAll("Visa alla", "Sverige", "Grekland", "Indien", "Asien", "Afrika", "Frankrike");
        cuisineBox.getSelectionModel().select("Visa alla");

        mainIngredientBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                rbc.setMainIngredient(newValue);
                updateRecipeList();
            }
        });

        cuisineBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                rbc.setCuisine(newValue);
                updateRecipeList();
            }
        });

        difficultyToggleGroup = new ToggleGroup();
        allDifficultiesButton.setToggleGroup(difficultyToggleGroup);
        easyButton.setToggleGroup(difficultyToggleGroup);
        mediumButton.setToggleGroup(difficultyToggleGroup);
        hardButton.setToggleGroup(difficultyToggleGroup);
        allDifficultiesButton.setSelected(true);

        difficultyToggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {

                if (difficultyToggleGroup.getSelectedToggle() != null) {
                    RadioButton selected = (RadioButton) difficultyToggleGroup.getSelectedToggle();
                    rbc.setDifficulty(selected.getText());
                    updateRecipeList();
                }
            }
        });

        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(10, 300, 0, 10);
        maxPriceSpinner.setValueFactory(valueFactory);

        maxPriceSpinner.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                rbc.setMaxPrice(newValue);
                updateRecipeList();
            }
        });

        maxPriceSpinner.focusedProperty().addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

                if (newValue) {
                    //focusgained - do nothing
                } else {
                    Integer value = Integer.valueOf(maxPriceSpinner.getEditor().getText());
                    rbc.setMaxPrice(value);
                    updateRecipeList();
                }

            }
        });

        maxTimeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (newValue != null && !newValue.equals(oldValue) && !maxTimeSlider.isValueChanging()) {
                    rbc.setMaxTime(newValue.intValue());
                    timeLabel.setText(newValue.intValue() + " minuter");
                    updateRecipeList();
                }
            }
        });

        populateMainIngredientComboBox();
        populateCuisineComboBox();
        Platform.runLater(() -> mainIngredientBox.requestFocus());

    }

    @FXML
    public void closeRecipeView() {
        searchPane.toFront();
    }

    public void openRecipeView(Recipe recipe) {
        populateRecipeDetailView(recipe);
        recipeDetailPane.toFront();
    }


    private void populateRecipeDetailView(Recipe recipe) {
        recipeNameLabel.setText(recipe.getName());
        recipeImageView.setImage(getSquareImage(recipe.getFXImage()));
        recipeFlag.setImage(getCuisineImage(recipe.getCuisine()));
        recipePortions.setText(recipe.getServings() + " portioner");
        recipeSmallDescription.setText(recipe.getDescription());
        recipeDescription.setText(recipe.getInstruction());
        recipeIngredients.setText(getIngredientsAsString(recipe));

        recipeDifficulty.setImage(getDifficultyImage(recipe.getDifficulty()));
        recipePrice.setText(recipe.getPrice() + " kr");
        recipeTime.setText(recipe.getTime() + " minuter");
        recipeMainIngredient.setImage(getMainIngredientImage(recipe.getMainIngredient()));

    }

    private String getIngredientsAsString(Recipe recipe){
        StringBuilder str = new StringBuilder();
        for(Ingredient ing : recipe.getIngredients()){
            str.append(ing.getAmount()).append(" ").append(ing.getUnit()).append(" ").append(ing.getName()).append("\n");
        }
        return str.toString();
    }

    private void updateRecipeList() {
        recipeFlowPane.getChildren().clear();
        List<Recipe> rl = rbc.getRecipes();
        for (Recipe r : rl) {
            recipeFlowPane.getChildren().add(recipeListItemMap.get(r.getName()));

        }
    }

    @FXML
    public void closeButtonMouseEntered(){
        closeImageView.setImage(new Image(getClass().getClassLoader().getResourceAsStream(
                "RecipeSearch/resources/icon_close_hover.png")));
    }

    @FXML
    public void closeButtonMousePressed(){
        closeImageView.setImage(new Image(getClass().getClassLoader().getResourceAsStream(
                "RecipeSearch/resources/icon_close_pressed.png")));
    }

    @FXML
    public void closeButtonMouseExited(){
        closeImageView.setImage(new Image(getClass().getClassLoader().getResourceAsStream(
                "RecipeSearch/resources/icon_close.png")));
    }

    @FXML
    public void mouseTrap(Event event){
        event.consume();
    }

    private void populateMainIngredientComboBox() {
        Callback<ListView<String>, ListCell<String>> cellFactory = new Callback<ListView<String>, ListCell<String>>() {

            @Override
            public ListCell<String> call(ListView<String> p) {

                return new ListCell<String>() {

                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);

                        setText(item);

                        if (item == null || empty) {
                            setGraphic(null);
                        } else {
                            Image icon = null;
                            String iconPath;
                            try {
                                switch (item) {

                                    case "Kött":
                                        iconPath = "RecipeSearch/resources/icon_main_meat.png";
                                        icon = new Image(getClass().getClassLoader().getResourceAsStream(iconPath));
                                        break;
                                    case "Fisk":
                                        iconPath = "RecipeSearch/resources/icon_main_fish.png";
                                        icon = new Image(getClass().getClassLoader().getResourceAsStream(iconPath));
                                        break;
                                    case "Kyckling":
                                        iconPath = "RecipeSearch/resources/icon_main_chicken.png";
                                        icon = new Image(getClass().getClassLoader().getResourceAsStream(iconPath));
                                        break;
                                    case "Vegetarisk":
                                        iconPath = "RecipeSearch/resources/icon_main_veg.png";
                                        icon = new Image(getClass().getClassLoader().getResourceAsStream(iconPath));
                                        break;
                                }
                            } catch (NullPointerException ex) {
                                //This should never happen in this lab but could load a default image in case of a NullPointer
                            }
                            ImageView iconImageView = new ImageView(icon);
                            iconImageView.setFitHeight(12);
                            iconImageView.setPreserveRatio(true);
                            setGraphic(iconImageView);
                        }
                    }
                };
            }
        };
        mainIngredientBox.setButtonCell(cellFactory.call(null));
        mainIngredientBox.setCellFactory(cellFactory);
    }

    private void populateCuisineComboBox() {
        Callback<ListView<String>, ListCell<String>> cellFactory = new Callback<ListView<String>, ListCell<String>>() {

            @Override
            public ListCell<String> call(ListView<String> p) {

                return new ListCell<String>() {

                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);

                        setText(item);

                        if (item == null || empty) {
                            setGraphic(null);
                        } else {
                            Image icon = null;
                            String iconPath;
                            try {
                                switch (item) {
                                    case "Sverige":
                                        iconPath = "RecipeSearch/resources/icon_flag_sweden.png";
                                        icon = new Image(getClass().getClassLoader().getResourceAsStream(iconPath));
                                        break;
                                    case "Grekland":
                                        iconPath = "RecipeSearch/resources/icon_flag_greece.png";
                                        icon = new Image(getClass().getClassLoader().getResourceAsStream(iconPath));
                                        break;
                                    case "Indien":
                                        iconPath = "RecipeSearch/resources/icon_flag_india.png";
                                        icon = new Image(getClass().getClassLoader().getResourceAsStream(iconPath));
                                        break;
                                    case "Asien":
                                        iconPath = "RecipeSearch/resources/icon_flag_asia.png";
                                        icon = new Image(getClass().getClassLoader().getResourceAsStream(iconPath));
                                        break;
                                    case "Afrika":
                                        iconPath = "RecipeSearch/resources/icon_flag_africa.png";
                                        icon = new Image(getClass().getClassLoader().getResourceAsStream(iconPath));
                                        break;
                                    case "Frankrike":
                                        iconPath = "RecipeSearch/resources/icon_flag_france.png";
                                        icon = new Image(getClass().getClassLoader().getResourceAsStream(iconPath));
                                        break;
                                }
                            } catch (NullPointerException ex) {
                                //This should never happen in this lab but could load a default image in case of a NullPointer
                            }
                            ImageView iconImageView = new ImageView(icon);
                            iconImageView.setFitHeight(12);
                            iconImageView.setPreserveRatio(true);
                            setGraphic(iconImageView);
                        }
                    }
                };
            }
        };
        cuisineBox.setButtonCell(cellFactory.call(null));
        cuisineBox.setCellFactory(cellFactory);
    }

    public Image getCuisineImage(String cuisine) {
        String iconPath;
        switch (cuisine) {
            case "Sverige":
                iconPath = "RecipeSearch/resources/icon_flag_sweden.png";
                return new Image(getClass().getClassLoader().getResourceAsStream(iconPath));
            case "Grekland":
                iconPath = "RecipeSearch/resources/icon_flag_greece.png";
                return new Image(getClass().getClassLoader().getResourceAsStream(iconPath));
            case "Asien":
                iconPath = "RecipeSearch/resources/icon_flag_asia.png";
                return new Image(getClass().getClassLoader().getResourceAsStream(iconPath));
            case "Afrika":
                iconPath = "RecipeSearch/resources/icon_flag_africa.png";
                return new Image(getClass().getClassLoader().getResourceAsStream(iconPath));
            case "Frankrike":
                iconPath = "RecipeSearch/resources/icon_flag_france.png";
                return new Image(getClass().getClassLoader().getResourceAsStream(iconPath));
            case "Indien":
                iconPath = "RecipeSearch/resources/icon_flag_india.png";
                return new Image(getClass().getClassLoader().getResourceAsStream(iconPath));
        }
        return null;
    }

    public Image getMainIngredientImage(String ingredient) {
        String iconPath;
        switch (ingredient) {
            case "Kött":
                iconPath = "RecipeSearch/resources/icon_main_meat.png";
                return new Image(getClass().getClassLoader().getResourceAsStream(iconPath));
            case "Fisk":
                iconPath = "RecipeSearch/resources/icon_main_fish.png";;
                return new Image(getClass().getClassLoader().getResourceAsStream(iconPath));
            case "Vegetarisk":
                iconPath = "RecipeSearch/resources/icon_main_veg.png";;
                return new Image(getClass().getClassLoader().getResourceAsStream(iconPath));
            case "Kyckling":
                iconPath = "RecipeSearch/resources/icon_main_chicken.png";;
                return new Image(getClass().getClassLoader().getResourceAsStream(iconPath));
        }
        return null;
    }

    public Image getDifficultyImage(String difficulty) {
        String iconPath;
        switch (difficulty) {
            case "Lätt":
                iconPath = "RecipeSearch/resources/icon_difficulty_easy.png";
                return new Image(getClass().getClassLoader().getResourceAsStream(iconPath));
            case "Mellan":
                iconPath = "RecipeSearch/resources/icon_difficulty_medium.png";
                return new Image(getClass().getClassLoader().getResourceAsStream(iconPath));
            case "Svår":
                iconPath = "RecipeSearch/resources/icon_difficulty_hard.png";
                return new Image(getClass().getClassLoader().getResourceAsStream(iconPath));
        }
        return null;
    }

    public Image getSquareImage(Image image){

        int x = 0;
        int y = 0;
        int width = 0;
        int height = 0;

        if(image.getWidth() > image.getHeight()){
            width = (int) image.getHeight();
            height = (int) image.getHeight();
            x = (int)(image.getWidth() - width)/2;
            y = 0;
        }

        else if(image.getHeight() > image.getWidth()){
            width = (int) image.getWidth();
            height = (int) image.getWidth();
            x = 0;
            y = (int) (image.getHeight() - height)/2;
        }

        else{
            //Width equals Height, return original image
            return image;
        }
        return new WritableImage(image.getPixelReader(), x, y, width, height);
    }

}
