package pgmacdesign.mvvmarchitecturesamples;

import androidx.annotation.IntRange;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;
import com.pgmacdesign.pgmactips.utilities.L;
import com.pgmacdesign.pgmactips.utilities.MiscUtilities;

import java.util.List;
import java.util.Random;

import pgmacdesign.mvvmarchitecturesamples.datamodels.Recipe;
import pgmacdesign.mvvmarchitecturesamples.misc.Constants;
import pgmacdesign.mvvmarchitecturesamples.networking.RecipeApi;
import pgmacdesign.mvvmarchitecturesamples.networking.ServiceGenerator;
import pgmacdesign.mvvmarchitecturesamples.networking.responses.RecipeResponse;
import pgmacdesign.mvvmarchitecturesamples.networking.responses.RecipeSearchResponse;
import pgmacdesign.mvvmarchitecturesamples.viewmodels.RecipeListViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeListActivity extends BaseActivity {
	
	RecipeApi recipeApi = ServiceGenerator.getRecipeAPI();
	
	private static final String[] recipeIds = {"807602", "26851", "484d98", "484d98", "1f3d85", "46882"};
	
	private RecipeListViewModel viewModel;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_main);
		this.viewModel = ViewModelProviders.of(this).get(RecipeListViewModel.class);
//		this.viewModel = new ViewModelProvider(this, new RecipeListViewModel().getRecipes());
		this.findViewById(R.id.test).setOnClickListener(view -> {
			this.showProgressBar(this.progressBar.getVisibility() != View.VISIBLE);
			
			this.testRetrofitRequest();
//			this.recipeApi.getRecipe(Constants.API_KEY, recipeIds[new Random().nextInt(recipeIds.length - 1)]).enqueue(
//					new Callback<RecipeResponse>() {
//				@Override
//				public void onResponse(Call<RecipeResponse> call, Response<RecipeResponse> response) {
//					RecipeResponse res = response.body();
//					Recipe recipe = res.getRecipe();
//					L.m("Recipe == " + new Gson().toJson(recipe, Recipe.class));
//				}
//
//				@Override
//				public void onFailure(Call<RecipeResponse> call, Throwable t) {
//					t.printStackTrace();
//				}
//			});
		});
		this.subscribeObservers();
	}
	
	private void subscribeObservers(){
		this.viewModel.getRecipes().observe(this, recipes -> {
			showProgressBar(false);
			L.m("Data subscribe change: Recipes size == " + (recipes == null ? 0 : recipes.size()));
			if(!MiscUtilities.isListNullOrEmpty(recipes)){
				MiscUtilities.printOutList(recipes);
			}
		});
	}
	
	private void testRetrofitRequest(){
		L.m("Making API Call in Activity");
		this.searchRecipesApi("chicken", 1);
	}
	
	private void searchRecipesApi(String query, @IntRange(from = 1) int pageNumber){
		this.viewModel.searchRecipesApi(query, pageNumber);
	}
}
