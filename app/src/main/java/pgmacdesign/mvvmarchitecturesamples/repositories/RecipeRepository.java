package pgmacdesign.mvvmarchitecturesamples.repositories;

import androidx.annotation.IntRange;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.pgmacdesign.pgmactips.utilities.L;

import java.util.List;

import pgmacdesign.mvvmarchitecturesamples.datamodels.Recipe;
import pgmacdesign.mvvmarchitecturesamples.networking.requests.RecipeApiClient;

public class RecipeRepository {
	
	
	private static RecipeRepository instance;
	private RecipeApiClient mRecipeApiClient;
	
	public static RecipeRepository getInstance(){
		if(instance == null){
			instance  = new RecipeRepository();
		}
		return instance;
	}
	
	private RecipeRepository(){
		this.mRecipeApiClient = RecipeApiClient.getInstance();
	}
	
	public MutableLiveData<List<Recipe>> getRecipes(){
		return this.mRecipeApiClient.getRecipes();
	}
	
	public void searchRecipesApi(String query){
		this.searchRecipesApi(query, 1);
	}
	
	public void searchRecipesApi(String query, @IntRange(from = 1) int pageNumber){
		if(pageNumber < 1){
			pageNumber = 1;
		}
		L.m("Making API Call in repository");
		this.mRecipeApiClient.searchRecipesApi(query, pageNumber);
	}
}
