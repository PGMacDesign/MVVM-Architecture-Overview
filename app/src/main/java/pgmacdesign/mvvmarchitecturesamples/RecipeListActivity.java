package pgmacdesign.mvvmarchitecturesamples;

import androidx.annotation.IntRange;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import com.google.gson.Gson;
import com.pgmacdesign.pgmactips.utilities.L;
import com.pgmacdesign.pgmactips.utilities.MiscUtilities;
import com.pgmacdesign.pgmactips.utilities.StringUtilities;

import java.util.List;
import java.util.Random;

import pgmacdesign.mvvmarchitecturesamples.adapters.OnRecipeListener;
import pgmacdesign.mvvmarchitecturesamples.adapters.RecipeRecyclerAdapter;
import pgmacdesign.mvvmarchitecturesamples.datamodels.Recipe;
import pgmacdesign.mvvmarchitecturesamples.misc.Constants;
import pgmacdesign.mvvmarchitecturesamples.misc.VerticalSpacingItemDecorator;
import pgmacdesign.mvvmarchitecturesamples.networking.RecipeApi;
import pgmacdesign.mvvmarchitecturesamples.networking.ServiceGenerator;
import pgmacdesign.mvvmarchitecturesamples.networking.responses.RecipeResponse;
import pgmacdesign.mvvmarchitecturesamples.networking.responses.RecipeSearchResponse;
import pgmacdesign.mvvmarchitecturesamples.viewmodels.RecipeListViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeListActivity extends BaseActivity implements OnRecipeListener {
	
	RecipeApi recipeApi = ServiceGenerator.getRecipeAPI();
	
	private static final String[] recipeIds = {"807602", "26851", "484d98", "484d98", "1f3d85", "46882"};
	
	private RecipeListViewModel viewModel;
	private RecyclerView recyclerView;
	private RecipeRecyclerAdapter adapter;
	private SearchView searchView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_recipe_list);
		this.viewModel = ViewModelProviders.of(this).get(RecipeListViewModel.class);
//		this.viewModel = new ViewModelProvider(this, new RecipeListViewModel().getRecipes());
		this.initRecyclerview();
		this.subscribeObservers();
		this.initSearchView();
		if(!this.viewModel.isViewingRecipes()){
			//Display Search Categories
			this.displaySearchCategories();
		}
		this.setSupportActionBar((Toolbar)this.findViewById(R.id.toolbar));
	}
	
	private void initRecyclerview(){
		this.adapter = new RecipeRecyclerAdapter(this);
		this.recyclerView = this.findViewById(R.id.recipe_list);
		this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
		this.recyclerView.setAdapter(this.adapter);
		this.recyclerView.addItemDecoration(new VerticalSpacingItemDecorator(30));
	}
	
	private void initSearchView(){
		this.searchView = this.findViewById(R.id.search_view);
		this.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
			@Override
			public boolean onQueryTextSubmit(String query) {
				if(!StringUtilities.isNullOrEmpty(query)){
					searchRecipesApi(query);
					searchView.setIconified(false);
					searchView.clearFocus();
				}
				return false;
			}
			
			@Override
			public boolean onQueryTextChange(String newText) {
				return false;
			}
		});
		this.searchView.setIconified(false);
		this.searchView.clearFocus();
	}
	
	private void subscribeObservers(){
		this.viewModel.getRecipes().observe(this, recipes -> {
			L.m("Data subscribe change: Recipes size == " + (recipes == null ? 0 : recipes.size()));
			if(!MiscUtilities.isListNullOrEmpty(recipes)){
				if(this.viewModel.isViewingRecipes()){
					this.adapter.setRecipes(recipes);
				}
			}
		});
	}
	
	private void searchRecipesApi(String query){
		// TODO: 5/26/20 add in pagination
		this.searchRecipesApi(query, 1);
	}
	
	private void searchRecipesApi(String query, @IntRange(from = 1) int pageNumber){
		this.adapter.displayLoading();
		this.viewModel.searchRecipesApi(query, pageNumber);
	}
	
	private void displaySearchCategories(){
		this.viewModel.setIsViewingRecipes(false);
		this.adapter.displaySearchCategories();
	}
	
	@Override
	public void onRecipeClick(int position) {
		L.m("POS CLICKED == " + position);
		try {
			Recipe r = this.viewModel.getRecipes().getValue().get(position);
			if(r != null){
				L.m("Recipe clicked: " + new Gson().toJson(r, Recipe.class));
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	public void onCategoryClick(String category) {
		if(!StringUtilities.isNullOrEmpty(category)){
			this.searchRecipesApi(category);
		}
	}
	
	@Override
	public void onBackPressed() {
		if(this.viewModel.onBackPressed()){
			super.onBackPressed();
		} else {
			this.displaySearchCategories();
		}
		
	}
}
