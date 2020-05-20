package pgmacdesign.mvvmarchitecturesamples;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pgmacdesign.pgmactips.datamodels.SamplePojo;
import com.pgmacdesign.pgmactips.utilities.L;
import com.pgmacdesign.pgmactips.utilities.MiscUtilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import pgmacdesign.mvvmarchitecturesamples.datamodels.Recipe;
import pgmacdesign.mvvmarchitecturesamples.misc.Constants;
import pgmacdesign.mvvmarchitecturesamples.networking.RecipeService;
import pgmacdesign.mvvmarchitecturesamples.networking.ServiceGenerator;
import pgmacdesign.mvvmarchitecturesamples.networking.responses.RecipeResponse;
import pgmacdesign.mvvmarchitecturesamples.networking.responses.RecipeSearchResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeListActivity extends BaseActivity {
	
	RecipeService recipeService = ServiceGenerator.getRecipeAPI();
	private static final String[] recipeIds = {"807602", "26851", "484d98", "484d98", "1f3d85", "46882"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_main);
		this.findViewById(R.id.test).setOnClickListener(view -> {
			this.showProgressBar(this.progressBar.getVisibility() != View.VISIBLE);
			this.recipeService.getRecipe(Constants.API_KEY, recipeIds[new Random().nextInt(recipeIds.length - 1)]).enqueue(
					new Callback<RecipeResponse>() {
				@Override
				public void onResponse(Call<RecipeResponse> call, Response<RecipeResponse> response) {
					RecipeResponse res = response.body();
					Recipe recipe = res.getRecipe();
					L.m("Recipe == " + new Gson().toJson(recipe, Recipe.class));
				}
				
				@Override
				public void onFailure(Call<RecipeResponse> call, Throwable t) {
					t.printStackTrace();
				}
			});
		});
		this.testRetrofitRequest();
	}
	
	private void testRetrofitRequest(){
		
		recipeService.searchRecipe(Constants.API_KEY, "Chicken", "1").enqueue(new Callback<RecipeSearchResponse>() {
			@Override
			public void onResponse(Call<RecipeSearchResponse> call, Response<RecipeSearchResponse> response) {
				if(response.isSuccessful()){
					L.m("Response successful. Data == ");
					RecipeSearchResponse res = response.body();
					List<Recipe> recipes = res.getRecipes();
					MiscUtilities.printOutList(recipes);
					L.m("recipes size == " + recipes.size());
				} else {
					L.m("response not successful!");
				}
			}
			
			@Override
			public void onFailure(Call<RecipeSearchResponse> call, Throwable t) {
				t.printStackTrace();
			}
		});
	}
	
}
