package zup.omdb.view.fragment.dialog;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import zup.omdb.R;
import zup.omdb.control.app.MovieApp;
import zup.omdb.util.BlockMultTouchHolder;
import zup.omdb.util.Constants;
import zup.omdb.util.DisplayLayout;
import zup.omdb.util.Fonts;
import zup.omdb.view.listener.IDialogListener;

public class DialogWarning extends AbstractDialog {

	private final String textUp;
	private final String textMiddle;
	private final String textBottom;
	private final int idDialog;
	private final IDialogListener listener;
	private final int type;
	private final Object[] params;


	public DialogWarning(final Context context, final String textUp, final String textMiddle, final String textBottom, final int idDialog, final int type, IDialogListener listener, final Object... params) {
		super(context);
		this.textUp = textUp;
		this.textMiddle = textMiddle;
		this.textBottom = textBottom;
		this.idDialog = idDialog;
		this.listener = listener;
		this.type = type;
		this.params = params;
		initDialog();
	}

	@Override
	public void initDialog(){
		super.initDialog();
		
		final LayoutInflater inflater = (LayoutInflater) MovieApp.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final ViewGroup resolutionDialogDateView = (ViewGroup) inflater.inflate(R.layout.dialog_warning, ((ViewGroup) null), false);

		DisplayLayout.startDisplayByWidth(MovieApp.getContext(), resolutionDialogDateView, Fonts.getRobotoRegular(), Typeface.NORMAL);

		getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
		getDialog().setContentView(resolutionDialogDateView);
		getDialog().setCanceledOnTouchOutside(false);

		final TextView textTitleTop = (TextView) getDialog().findViewById(R.id.text_title_general_top);
		textTitleTop.setTypeface(Fonts.getRobotoBold(), Typeface.NORMAL);
		textTitleTop.setText(textUp);

		final TextView textTitleMiddle = (TextView) getDialog().findViewById(R.id.text_title_general_middle);
		textTitleMiddle.setTypeface(Fonts.getRobotoRegular(), Typeface.NORMAL);

		final TextView textTitleBottom = (TextView) getDialog().findViewById(R.id.text_title_general_bottom);
		textTitleBottom.setTypeface(Fonts.getRobotoRegular(), Typeface.NORMAL);

		if(textMiddle == null) {
			textTitleMiddle.setVisibility(View.GONE);
		} else {
			textTitleMiddle.setText(textMiddle);
			textTitleMiddle.setVisibility(View.VISIBLE);
		}

		if(textBottom == null) {
			textTitleBottom.setVisibility(View.GONE);
		} else {
			textTitleBottom.setText(textBottom);
			textTitleBottom.setVisibility(View.VISIBLE);
		}

		final Button btnCancel = (Button) getDialog().findViewById(R.id.text_cancel_general);
		btnCancel.setOnTouchListener(BlockMultTouchHolder.getInstance());
		btnCancel.setOnClickListener(this);
		btnCancel.setTypeface(Fonts.getRobotoMedium(), Typeface.NORMAL);

		final Button btnConfirm = (Button) getDialog().findViewById(R.id.text_confirm_dialog_general);
		btnConfirm.setOnTouchListener(BlockMultTouchHolder.getInstance());
		btnConfirm.setOnClickListener(this);
		btnConfirm.setTypeface(Fonts.getRobotoMedium(), Typeface.NORMAL);

		if(type == Constants.NO_AND_YES){
			btnCancel.setText("NÃO");
			btnConfirm.setText("SIM");
		} else if(type == Constants.OK){
			btnCancel.setVisibility(View.INVISIBLE);
			btnConfirm.setText("OK");
		}

		if(type == Constants.OK){
			textTitleBottom.setTextIsSelectable(true);
		}

		BlockMultTouchHolder.getInstance().setViewBlocker(getDialog().findViewById(R.id.viewblock));
		
		getDialog().show();
		
	}

	@Override
	public void onClick(final View view) {

		if (listener != null) {
			if(view.getId() == R.id.text_confirm_dialog_general) {
				listener.onClickDialog(idDialog, true, params);
			} else if(view.getId() == R.id.text_cancel_general) {
				listener.onClickDialog(idDialog, false, params);
			}
		}

		switch (view.getId()) {
		case R.id.text_cancel_general:
			getDialog().dismiss();
			break;
		case R.id.text_confirm_dialog_general:
			getDialog().dismiss();
			break;

		default:
			break;
		}
	}
	
}
