package pgmacdesign.mvvmarchitecturesamples;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class AppExecutors {
	
	private static AppExecutors instance;
	
	public static AppExecutors getInstance(){
		if(instance == null){
			instance = new AppExecutors();
		}
		return instance;
	}
	
	/**
	 * Service that can schedule commands after a delay
	 */
	private final ScheduledExecutorService mNetworkIO = Executors.newScheduledThreadPool(3);
	
	public ScheduledExecutorService networkIO(){
		return this.mNetworkIO;
	}
	
	
	/*
	//Sample for single usage
		this.mBackgroundExecutor.execute(new Runnable() {
			@Override
			public void run() {
				//Sample
			}
		});
		
	//Sample for >1 use (async)
		this.mBackgroundExecutor.execute(new Runnable() {
			@Override
			public void run() {
				//Item 1
			}
		});
		this.mBackgroundExecutor.execute(new Runnable() {
			@Override
			public void run() {
				//Item 2
			}
		});
	 */
}
