package zup.omdb.util;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import zup.omdb.view.listener.IBlockMultTouchHolder;


public class BlockMultTouchHolder implements OnTouchListener {

	private IBlockMultTouchHolder listener;
	private View viewBlocker;
	private Handler handler;
	private long delay = 300;
	private static transient BlockMultTouchHolder instance;
	
	public BlockMultTouchHolder() {
		// do nothing
	}
	
	public synchronized static BlockMultTouchHolder getInstance() {
    	if (instance == null) {
    		instance = new BlockMultTouchHolder();
    	}
    	return instance;
	}
	
	/**
	 * Constructor
	 *
	 * @param viewBlocker - View to block multiple touches
	 */
	public BlockMultTouchHolder(final View viewBlocker) {
		this.viewBlocker = viewBlocker;
	}
	
	@SuppressLint("ClickableViewAccessibility") @Override
	public boolean onTouch(final View view, final MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			show();
		} else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
			if(handler != null && delay > 0){
				hideDelay();
			} else {
				hide();
			}
		}
		
		return false;

	}
	
	public void show(){
		if(listener != null){
			listener.showMultTouchBlockView();
		} else {
			CustomLog.error("Listener not found, please use this method initViewBlockHolder or setListener");
		}
		
		if(viewBlocker != null){
			viewBlocker.setVisibility(View.VISIBLE);
		}
	}
	
	public void hideDelay(){
		handler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				hide();
			}
		}, delay);
	}
	
	public void hide(){
		if(listener != null){
			listener.hideMultTouchBlockView();
		} else {
			CustomLog.error("Listener not found, please use this method initViewBlockHolder or setListener");
		}
		
		if(viewBlocker != null){
			viewBlocker.setVisibility(View.GONE);
		}
	}
	
	public void initViewBlockHolder(final View view){
		
		if(view != null){
			handler = new Handler();
			
    		listener = new IBlockMultTouchHolder() {
    			
    			@Override
    			public void showMultTouchBlockView() {
    				view.setVisibility(View.VISIBLE);
    			}
    			
    			@Override
    			public void hideMultTouchBlockView() {
    				view.setVisibility(View.GONE);
    			}
    		};
		} else {
			CustomLog.error("Error view block not found.");
		}
	}
	
	public IBlockMultTouchHolder getListener() {
		return listener;
	}

	public void setListener(final IBlockMultTouchHolder listener) {
		this.listener = listener;
	}
	
	public void setDelay(final long delay) {
		this.delay = delay;
	}
	
	public View getViewBlocker() {
		return viewBlocker;
	}

	public void setViewBlocker(final View viewBlocker) {
		this.viewBlocker = viewBlocker;
	}
	
}