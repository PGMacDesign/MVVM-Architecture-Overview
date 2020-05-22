package pgmacdesign.mvvmarchitecturesamples.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import pgmacdesign.mvvmarchitecturesamples.R;

public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
	
	TextView title, publisher, socialScore;
	AppCompatImageView image;
	OnRecipeListener listener;
	
	public RecipeViewHolder(@NonNull View itemView, OnRecipeListener listener) {
		super(itemView);
		this.listener = listener;
		this.title = itemView.findViewById(R.id.recipe_title);
		this.publisher = itemView.findViewById(R.id.recipe_publisher);
		this.socialScore = itemView.findViewById(R.id.recipe_social_score);
		this.image = itemView.findViewById(R.id.recipe_image);
		this.itemView.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View view) {
		if(this.listener != null){
			this.listener.onRecipeClick(getAdapterPosition());
		}
	}
}
