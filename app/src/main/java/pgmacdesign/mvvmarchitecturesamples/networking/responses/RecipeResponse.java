package pgmacdesign.mvvmarchitecturesamples.networking.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import pgmacdesign.mvvmarchitecturesamples.datamodels.Recipe;

public class RecipeResponse {
	
	@SerializedName("recipe")
	@Expose
	private Recipe recipe;
	
	public Recipe getRecipe() {
		return recipe;
	}
	
	
}
