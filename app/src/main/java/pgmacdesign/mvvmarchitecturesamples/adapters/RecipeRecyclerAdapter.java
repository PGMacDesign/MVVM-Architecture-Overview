package pgmacdesign.mvvmarchitecturesamples.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.RequestOptions;
import com.pgmacdesign.pgmactips.utilities.MiscUtilities;

import java.util.List;

import pgmacdesign.mvvmarchitecturesamples.R;
import pgmacdesign.mvvmarchitecturesamples.datamodels.Recipe;

public class RecipeRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
	
	private List<Recipe> mRecipes;
	private OnRecipeListener listener;
	private RequestOptions requestOptions;
	
	public RecipeRecyclerAdapter(List<Recipe> mRecipes, OnRecipeListener listener) {
		this.mRecipes = mRecipes;
		this.listener = listener;
		this.requestOptions = new RequestOptions()
				.placeholder(R.drawable.android_toolbelt_large);
	}
	
	@NonNull
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recipe_list_item, parent, false);
		
		// TODO: 5/22/20
		return new RecipeViewHolder(view, this.listener);
	}
	
	@Override
	public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder1, int position) {
		// TODO: 5/22/20
		
		RecipeViewHolder holder = (RecipeViewHolder)holder1;
		Recipe recipe;
		if(MiscUtilities.isValidPosition(mRecipes, position)){
			recipe = mRecipes.get(position);
		} else {
			recipe = null;
		}
		if(recipe == null){
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
	}
	
	@Override
	public int getItemCount() {
		return (MiscUtilities.isListNullOrEmpty(this.mRecipes) ? 0 : this.mRecipes.size());
	}
	
	public void setRecipes(List<Recipe> recipes){
		this.mRecipes = recipes;
		this.notifyDataSetChanged();
	}
	
	
	
}
