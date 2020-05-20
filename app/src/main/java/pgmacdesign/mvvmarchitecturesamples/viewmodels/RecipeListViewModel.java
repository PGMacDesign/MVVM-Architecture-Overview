package pgmacdesign.mvvmarchitecturesamples.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import pgmacdesign.mvvmarchitecturesamples.datamodels.Recipe;

/**
 * Note, the Android View Model passes in an Application within the constructor where the regular
 * view model does not have that passed.
 */
public class RecipeListViewModel extends ViewModel {
	
	private MutableLiveData<List<Recipe>> mRecipes = new MutableLiveData<>();
	
	
	public RecipeListViewModel() {
	
	}
	
	public LiveData<List<Recipe>> getRecipes(){
		return this.mRecipes;
	}
}
