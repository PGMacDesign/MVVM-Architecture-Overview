package pgmacdesign.mvvmarchitecturesamples.networking;

import pgmacdesign.mvvmarchitecturesamples.networking.responses.RecipeResponse;
import pgmacdesign.mvvmarchitecturesamples.networking.responses.RecipeSearchResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RecipeService {
	
	/**
	 * Search for recipes with a keyword
	 * @param key
	 * @param q
	 * @param page
	 * @return
	 */
	@GET("api/search")
	Call<RecipeSearchResponse> searchRecipe(
			@Query("key") String key,
			@Query("q") String q,
			@Query("page") String page
	);
	
	/**
	 * Get a recipe with the recipe ID
	 * @param key
	 * @param recipeId
	 * @return
	 */
	@GET("api/get")
	Call<RecipeResponse> getRecipe(
			@Query("key") String key,
			@Query("rId") String recipeId
	);
	
	
}
