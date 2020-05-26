package pgmacdesign.mvvmarchitecturesamples.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;
import pgmacdesign.mvvmarchitecturesamples.R;

public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
	
	OnRecipeListener listener;
	
	CircleImageView categoryImage;
	TextView categoryTitle;
	
	public CategoryViewHolder(@NonNull View itemView, OnRecipeListener listener) {
		super(itemView);
		this.listener = listener;
		this.categoryImage = itemView.findViewById(R.id.category_image);
		this.categoryTitle = itemView.findViewById(R.id.category_title);
		itemView.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View view) {
		if(this.listener != null){
			this.listener.onCategoryClick(this.categoryTitle.getText().toString());
		}
	}
}
