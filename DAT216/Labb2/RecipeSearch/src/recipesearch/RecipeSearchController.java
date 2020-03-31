
package recipesearch;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.layout.FlowPane;
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


    RecipeDatabase db = RecipeDatabase.getSharedInstance();
    RecipeBackendController rbc = new RecipeBackendController();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        updateRecipeList();
    }

    private void updateRecipeList(){
        recipeFlowPane.getChildren().clear();
        List<Recipe> rl = rbc.getRecipes();
        for (Recipe r : rl){
            recipeFlowPane.getChildren().add(new RecipeListItem(r, this));
        }

    }


}