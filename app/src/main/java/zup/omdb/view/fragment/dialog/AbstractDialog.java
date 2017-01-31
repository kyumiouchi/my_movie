package zup.omdb.view.fragment.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.DialogInterface.OnShowListener;
import android.view.View.OnClickListener;
import android.view.WindowManager.BadTokenException;

import zup.omdb.control.app.MovieApp;
import zup.omdb.model.bo.MovieBO;
import zup.omdb.util.BlockMultTouchHolder;
import zup.omdb.util.Utils;


public abstract class AbstractDialog implements OnClickListener {

	private final MyDialog dialog;
	private Context context;
	private static boolean open;
	
	public AbstractDialog(final Context context){
		this.context = context;
		
		if(this.context == null){
			this.context = MovieApp.getContext();
		}

		dialog = new MyDialog(this.context);
	}
	
	public void initDialog() {

		setOpen(true);
		Utils.setAppLocale("pt", MovieApp.getContext());
		
		BlockMultTouchHolder.getInstance().hide();
		
		dialog.setOnShowListener(new OnShowListener() {
			
			@Override
			public void onShow(final DialogInterface dialogInterface) {
				setOpen(true);
				MovieBO.getInstance().setDialog(dialog);
			}
		});
		
		dialog.setOnDismissListener(new OnDismissListener() {
			
			@Override
			public void onDismiss(final DialogInterface dialogInterface) {
				setOpen(false);
				BlockMultTouchHolder.getInstance().hide();
				MovieBO.getInstance().setDialog(null);
			}
		});
		
		dialog.setOnCancelListener(new OnCancelListener() {
			
			@Override
			public void onCancel(DialogInterface dialog) {
				setOpen(false);
				BlockMultTouchHolder.getInstance().hide();
				MovieBO.getInstance().setDialog(null);
			}
		});
	}
	
	public Dialog getDialog() {
		return dialog;
	}
	
	public Context getContext() {
		return context;
	}

	public void hideKeyBoardAtScreen(){

	}
	private static class MyDialog extends Dialog {

		public MyDialog(final Context context) {
			super(context);
		}

		/* (non-Javadoc)
		 * @see android.app.Dialog#show()
		 */
		@Override
		public void show() {
			try {
				super.show();
			} catch (BadTokenException e) {
				AbstractDialog.setOpen(false);
//				WrapperLog.error("Error in Dialog: "+e.getMessage(), MyDialog.class,"show");
			}
		}

		@Override
		public void onWindowFocusChanged(boolean hasFocus) {
			super.onWindowFocusChanged(hasFocus);

			if (!hasFocus){
//				Util.hideKeyBoardAtScreen(getContext(),getCurrentFocus());
			}
		}

		@Override
		protected void onStop() {
//			Util.hideKeyBoardAtScreen(getContext(),getCurrentFocus());
			super.onStop();
		}
	}

	public static void setOpen(final boolean open){
		AbstractDialog.open = open;
	}

	public static boolean isOpen (){
		return  AbstractDialog.open;
	}
}
