package pgmacdesign.mvvmarchitecturesamples.misc;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class VerticalSpacingItemDecorator extends RecyclerView.ItemDecoration {
	
	private final int verticalSpacingHeight;
	
	public VerticalSpacingItemDecorator(int verticalSpacingHeight) {
		this.verticalSpacingHeight = verticalSpacingHeight;
	}
	
	// TODO: 5/26/20
	private VerticalSpacingItemDecorator(int verticalSpacingHeight, int type) {
		this.verticalSpacingHeight = verticalSpacingHeight;
		
	}
	
	/**
	 *
	 * @param outRect -- Outside rectangle around the outisde of the view
	 * @param view
	 * @param parent
	 * @param state
	 */
	@Override
	public void getItemOffsets(@NonNull Rect outRect,
	                           @NonNull View view,
	                           @NonNull RecyclerView parent,
	                           @NonNull RecyclerView.State state) {
//		super.getItemOffsets(outRect, view, parent, state);
		outRect.top = verticalSpacingHeight;
	}
}
