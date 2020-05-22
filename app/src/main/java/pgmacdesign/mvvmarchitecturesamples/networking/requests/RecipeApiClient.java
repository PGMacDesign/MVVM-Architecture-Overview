package pgmacdesign.mvvmarchitecturesamples.networking.requests;

import androidx.lifecycle.MutableLiveData;

import com.pgmacdesign.pgmactips.utilities.L;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import pgmacdesign.mvvmarchitecturesamples.AppExecutors;
import pgmacdesign.mvvmarchitecturesamples.datamodels.Recipe;
import pgmacdesign.mvvmarchitecturesamples.misc.Constants;
import pgmacdesign.mvvmarchitecturesamples.networking.ServiceGenerator;
import pgmacdesign.mvvmarchitecturesamples.networking.responses.RecipeSearchResponse;
import retrofit2.Call;
import retrofit2.Response;

public class RecipeApiClient {

	private static RecipeApiClient instance;
	private MutableLiveData<List<Recipe>> mRecipes;
	private RetrieveRecipesRunnable retrieveRecipesRunnable;
	
	public static RecipeApiClient getInstance(){
		if(instance == null){
			instance = new RecipeApiClient();
		}
		return instance;
	}
	
	private RecipeApiClient(){
		this.mRecipes = new MutableLiveData<>();
	}
	
	public MutableLiveData<List<Recipe>> getRecipes(){
		return this.mRecipes;
	}
	
	/**
	 * Search the recipes Api
	 * @param query
	 * @param pageNumber
	 */
	public void searchRecipesApi(String query, int pageNumber){
		if(this.retrieveRecipesRunnable != null){
			this.retrieveRecipesRunnable = null;
		}
		this.retrieveRecipesRunnable = new RetrieveRecipesRunnable(query, pageNumber);
		L.m("Making API Call in API Client");
		final Future handler = AppExecutors.getInstance().networkIO().submit(this.retrieveRecipesRunnable);
		AppExecutors.getInstance().networkIO().schedule(() -> {
			//Let user know it has timed out
			handler.cancel(true);
		}, Constants.NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);
	}
	
	private class RetrieveRecipesRunnable implements Runnable {
		
		private String query;
		private int pageNumber;
		boolean cancelRequest;
		
		RetrieveRecipesRunnable(String query, int pageNumber){
			this.query = query;
			this.pageNumber = pageNumber;
			this.cancelRequest = false;
		}
		
		@Override
		public void run() {
			try {
				Response response = getRecipes(this.query, this.pageNumber).execute();
				if(this.cancelRequest){
					return;
				}
				if(response == null){
					return;
				}
				if(response.code() == 200){
					List<Recipe> list = new ArrayList<>(((RecipeSearchResponse)response.body()).getRecipes());
					if(this.pageNumber == 1){
						//Note, postValue if for a background thread and setValue is for a foreground thread.
						RecipeApiClient.this.mRecipes.postValue(list);
					} else {
						List<Recipe> currentRecipes = RecipeApiClient.this.mRecipes.getValue();
						currentRecipes.addAll(list);
						RecipeApiClient.this.mRecipes.postValue(currentRecipes);
					}
				} else {
					String error = response.errorBody().string();
					L.m("error!" + error);
					RecipeApiClient.this.mRecipes.postValue(null);
				}
			} catch (IOException e) {
				e.printStackTrace();
				L.m("error!");
				e.printStackTrace();
				RecipeApiClient.this.mRecipes.postValue(null);
			}
		}
		
		private Call<RecipeSearchResponse> getRecipes(String query, int pageNumber){
			return ServiceGenerator.getRecipeAPI().searchRecipe(Constants.API_KEY, query, pageNumber + "");
		}
		
		private void cancelRequest(){
			this.cancelRequest = true;
			L.m("Cancelling request!");
		}
	}
}
