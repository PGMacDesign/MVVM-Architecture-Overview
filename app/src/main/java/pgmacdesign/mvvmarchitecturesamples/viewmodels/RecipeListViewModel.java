package pgmacdesign.mvvmarchitecturesamples.viewmodels;

import androidx.annotation.IntRange;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.pgmacdesign.pgmactips.utilities.L;

import java.util.List;

import pgmacdesign.mvvmarchitecturesamples.datamodels.Recipe;
import pgmacdesign.mvvmarchitecturesamples.repositories.RecipeRepository;

/**
 * Note, the Android View Model passes in an Application within the constructor where the regular
 * view model does not have that passed.
 */
public class RecipeListViewModel extends ViewModel {
	
	private RecipeRepository repository;
	
	public RecipeListViewModel() {
		this.repository = RecipeRepository.getInstance();
	}
	
	public LiveData<List<Recipe>> getRecipes(){
		return this.repository.getRecipes();
	}
	
	public void searchRecipesApi(String query){
		this.repository.searchRecipesApi(query, 1);
	}
	
	public void searchRecipesApi(String query, @IntRange(from = 1) int pageNumber){
		L.m("Making API Call in view model");
		this.repository.searchRecipesApi(query, pageNumber);
	}
	
}
