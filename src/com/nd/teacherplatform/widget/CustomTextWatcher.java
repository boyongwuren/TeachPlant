package com.nd.teacherplatform.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.EditText;

public class CustomTextWatcher {

	private EditText et_text;
	private Drawable mIconClear; // �����ı�������ı�����ͼ��
	private Context mcontext;

	public CustomTextWatcher(Context context, EditText text, int resID) {
		this.mcontext = context;
		this.et_text = text;
		this.mIconClear = mcontext.getResources().getDrawable(resID);
	}

	/**
	 * ��̬����
	 */
	public TextWatcher tbxSearch_TextChanged = new TextWatcher() {

		// ������һ���ı������Ƿ�Ϊ��
		private boolean isnull = true;

		@Override
		public void afterTextChanged(Editable s) {
			if (TextUtils.isEmpty(s)) {
				if (!isnull) {
					et_text.setCompoundDrawablesWithIntrinsicBounds(null, null,
							null, null);
					isnull = true;
				}
			} else {
				if (isnull) {
					et_text.setCompoundDrawablesWithIntrinsicBounds(null, null,
							mIconClear, null);
					isnull = false;
				}
			}
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}

		/**
		 * �����ı������ݸı䶯̬�ı��б�����
		 */
		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {

		}
	};

	public OnTouchListener txtSearch_OnTouch = new OnTouchListener() {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_UP:
				int curX = (int) event.getX();
				if (curX > v.getWidth() - 38
						&& !TextUtils.isEmpty(et_text.getText())) {
					et_text.setText("");
					// backupthe input type
					int cacheInputType = et_text.getInputType();
					// disable soft input
					et_text.setInputType(InputType.TYPE_NULL);
					// call native handler
					et_text.onTouchEvent(event);
					// restore input type
					et_text.setInputType(cacheInputType);
					// consume touch even
					return true;
				}
				break;
			}
			return false;
		}
	};
}
