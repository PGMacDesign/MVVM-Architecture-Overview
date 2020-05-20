package pgmacdesign.mvvmarchitecturesamples.networking.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import pgmacdesign.mvvmarchitecturesamples.datamodels.Recipe;

public class RecipeSearchResponse {
	
	@SerializedName("count")
	@Expose
	private int count;
	@SerializedName("recipes")
	@Expose
	private List<Recipe> recipes;
	
	public int getCount() {
		return count;
	}
	
	
	public List<Recipe> getRecipes() {
		return recipes;
	}
	
}
