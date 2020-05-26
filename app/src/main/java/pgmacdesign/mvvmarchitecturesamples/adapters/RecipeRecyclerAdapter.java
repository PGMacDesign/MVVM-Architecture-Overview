package pgmacdesign.mvvmarchitecturesamples.adapters;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.RequestOptions;
import com.pgmacdesign.pgmactips.utilities.L;
import com.pgmacdesign.pgmactips.utilities.MiscUtilities;

import java.util.ArrayList;
import java.util.List;

import pgmacdesign.mvvmarchitecturesamples.R;
import pgmacdesign.mvvmarchitecturesamples.datamodels.Recipe;
import pgmacdesign.mvvmarchitecturesamples.misc.Constants;

public class RecipeRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
	
	private List<Recipe> mRecipes;
	private OnRecipeListener listener;
	private RequestOptions requestOptions;
	
	private static final int VIEW_TYPE_RECIPE = 1;
	private static final int VIEW_TYPE_LOADING = 2;
	private static final int VIEW_TYPE_CATEGORY = 3;
	
	public RecipeRecyclerAdapter(OnRecipeListener listener) {
		this.mRecipes = new ArrayList<>();
		this.listener = listener;
		this.requestOptions = new RequestOptions()
				.placeholder(R.mipmap.fingerprint_black);
	}
	
	@NonNull
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = null;
		
		switch (viewType) {
			case 3:
				L.m("returning loading holder");
				view = LayoutInflater.from(parent.getContext()).inflate(
						R.layout.layout_category_list_item, parent, false);
				return new CategoryViewHolder(view, this.listener);
			case 2:
				L.m("returning loading holder");
				view = LayoutInflater.from(parent.getContext()).inflate(
						R.layout.layout_loading_list_item, parent, false);
				return new LoadingViewHolder(view);
			case 1:
			default:
				view = LayoutInflater.from(parent.getContext()).inflate(
						R.layout.layout_recipe_list_item, parent, false);
				return new RecipeViewHolder(view, this.listener);
			
		}
	}
	
	@Override
	public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder1, int position) {
		if(getItemViewType(position) == 1){
			L.m("This should be an item");
			RecipeViewHolder holder = (RecipeViewHolder) holder1;
			Recipe recipe;
			if (MiscUtilities.isValidPosition(mRecipes, position)) {
				recipe = mRecipes.get(position);
			} else {
				recipe = null;
			}
			if (recipe == null) {
				// TODO: 5/22/20
				return;
			}
			holder.title.setText(recipe.getTitle());
			holder.publisher.setText(recipe.getPublisher());
			holder.socialScore.setText(String.valueOf(Math.round(recipe.getSocial_rank())));
			Glide.with(holder.itemView.getContext())
					.setDefaultRequestOptions(this.requestOptions)
					.load(recipe.getImage_url())
					.into(holder.image);
		}  else if (getItemViewType(position) == 2){
			L.m("This should be a loading?");
			//No code needed, should auto run on its own
		}  else if (getItemViewType(position) == 3){
			L.m("This should be an item");
			CategoryViewHolder holder = (CategoryViewHolder) holder1;
			Recipe recipe;
			if (MiscUtilities.isValidPosition(mRecipes, position)) {
				recipe = mRecipes.get(position);
			} else {
				recipe = null;
			}
			if (recipe == null) {
				// TODO: 5/22/20
				return;
			}
			Uri path = null;
			try {
				path = Uri.parse("android.resource://pgmacdesign.mvvmarchitecturesamples/drawable/" + recipe.getImage_url());
			} catch (Exception e){
				e.printStackTrace();
				path = Uri.parse("android.resource://pgmacdesign.mvvmarchitecturesamples/drawable/ic_launcher_background.xml");
			}
			Glide.with(holder.itemView.getContext())
					.setDefaultRequestOptions(this.requestOptions)
					.load(path)
					.into(holder.categoryImage);
			holder.categoryTitle.setText(recipe.getTitle());
		} else {
			L.m("This should be a loading?");
		
		}
	}
	
	@Override
	public int getItemViewType(int position) {
		if(!MiscUtilities.isValidPosition(this.mRecipes, position)) {
			return super.getItemViewType(position);
		}
		Recipe r = this.mRecipes.get(position);
		if(r == null){
			return super.getItemViewType(position);
		}
		if(r.getTitle().equalsIgnoreCase("LOADING...")){
			return VIEW_TYPE_LOADING;
		} else if(r.getSocial_rank() == -1) {
			return VIEW_TYPE_CATEGORY;
		} else {
			return VIEW_TYPE_RECIPE;
		}
	}
	
	@Override
	public int getItemCount() {
		return (MiscUtilities.isListNullOrEmpty(this.mRecipes) ? 0 : this.mRecipes.size());
	}
	
	private boolean isLoading(){
		if(!MiscUtilities.isListNullOrEmpty(this.mRecipes)){
			if(this.mRecipes.get(this.mRecipes.size()-1).getTitle().equalsIgnoreCase("LOADING...")){
				return true;
			}
		}
		return false;
	}
	
	public void displayLoading(){
		if(!isLoading()){
			Recipe r = new Recipe();
			r.setTitle("LOADING...");
			List<Recipe> loadingList = new ArrayList<>();
			loadingList.add(r);
			this.setRecipes(loadingList);
		}
	}
	
	public void displaySearchCategories(){
		List<Recipe> categories = new ArrayList<>();
		for(int i = 0; i < Constants.DEFAULT_SEARCH_CATEGORY_IMAGES.length; i++){
			Recipe r = new Recipe();
			r.setTitle(Constants.DEFAULT_SEARCH_CATEGORIES[i]);
			r.setImage_url(Constants.DEFAULT_SEARCH_CATEGORY_IMAGES[i]);
			r.setSocial_rank(-1);
			categories.add(r);
		}
		this.setRecipes(categories);
	}
	
	public void setRecipes(List<Recipe> recipes) {
		this.mRecipes = recipes;
		this.notifyDataSetChanged();
		L.m("setRecipes, notify data set changed");
	}
	
	
}
