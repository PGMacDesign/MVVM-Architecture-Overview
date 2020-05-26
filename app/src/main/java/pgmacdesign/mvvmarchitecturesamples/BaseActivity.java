package pgmacdesign.mvvmarchitecturesamples;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.pgmacdesign.pgmactips.utilities.L;

public abstract class BaseActivity extends AppCompatActivity {
	
	@Override
	public void setContentView(int layoutResID) {
		ConstraintLayout constraintLayout = (ConstraintLayout) this.getLayoutInflater().inflate(R.layout.activity_base, null);
		FrameLayout frameLayout = constraintLayout.findViewById(R.id.activity_content);
//		this.progressBar = constraintLayout.findViewById(R.id.progress_bar);
//		this.progressBar.setIndeterminate(true);
		this.getLayoutInflater().inflate(layoutResID, frameLayout, true);
		super.setContentView(constraintLayout);
	}
	
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_base);
	}
	
	public void showProgressBar(boolean visibility){
//		this.progressBar.setVisibility((visibility) ? View.VISIBLE : View.INVISIBLE);
//		this.progressBar.bringToFront();
		L.m("Progress bar set to visible: " + visibility);
	}
}
