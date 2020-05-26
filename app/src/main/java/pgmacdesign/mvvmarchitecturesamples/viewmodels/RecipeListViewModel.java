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
	private boolean mIsViewingRecipes, mIsPerformingQuery;
	
	public RecipeListViewModel() {
		this.repository = RecipeRepository.getInstance();
		this.mIsPerformingQuery = false;
	}
	
	public LiveData<List<Recipe>> getRecipes(){
		return this.repository.getRecipes();
	}
	
	public void searchRecipesApi(String query){
		this.repository.searchRecipesApi(query, 1);
	}
	
	public void searchRecipesApi(String query, @IntRange(from = 1) int pageNumber){
		L.m("Making API Call in view model");
		this.mIsViewingRecipes = true;
		this.mIsPerformingQuery = true;
		this.repository.searchRecipesApi(query, pageNumber);
	}
	
	public boolean onBackPressed(){
		if(mIsViewingRecipes){
			this.mIsViewingRecipes = false;
			return false;
		}
		return true;
	}
	
	//region getters and setters
	public boolean isViewingRecipes() {
		return mIsViewingRecipes;
	}
	
	public void setIsViewingRecipes(boolean mIsViewingRecipes) {
		this.mIsViewingRecipes = mIsViewingRecipes;
	}
	
	public boolean isPerformingQuery() {
		return mIsPerformingQuery;
	}
	
	public void setPerformingQuery(boolean mIsPerformingQuery) {
		this.mIsPerformingQuery = mIsPerformingQuery;
	}
	//endregion
	
}
