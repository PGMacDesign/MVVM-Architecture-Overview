package pgmacdesign.mvvmarchitecturesamples.networking;

import com.pgmacdesign.pgmactips.networkclasses.retrofitutilities.CustomConverterFactory;
import com.pgmacdesign.pgmactips.networkclasses.retrofitutilities.RetryCallAdapterFactory;

import pgmacdesign.mvvmarchitecturesamples.misc.Constants;
import retrofit2.Retrofit;

public class ServiceGenerator {
	
	private static Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
			.baseUrl(Constants.BASE_URL)
			.addCallAdapterFactory(RetryCallAdapterFactory.create(1))
			.addConverterFactory(new CustomConverterFactory());
	
	private static Retrofit retrofit = retrofitBuilder.build();
	
	private static RecipeService recipeService = retrofit.create(RecipeService.class);
	
	public static RecipeService getRecipeAPI(){
		return recipeService;
	}
}
