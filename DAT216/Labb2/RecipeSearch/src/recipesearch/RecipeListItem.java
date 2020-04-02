package recipesearch;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.ait.dat215.lab2.Recipe;

import java.io.IOException;

public class RecipeListItem extends AnchorPane {
    @FXML
    protected Label recipeCardLabel;
    @FXML
    protected ImageView recipeCardImage;
    @FXML
    protected AnchorPane recipeCard;

    private RecipeSearchController parentController;
    private Recipe recipe;


    public RecipeListItem(Recipe recipe, RecipeSearchController recipeSearchController){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("recipe_listitem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.recipe = recipe;
        this.parentController = recipeSearchController;
        recipeCardLabel.setText(recipe.getName());
        recipeCardImage.setImage(recipe.getFXImage());

    }

    @FXML
    protected void onClick(Event event){
        parentController.openRecipeView(recipe);
    }

}
