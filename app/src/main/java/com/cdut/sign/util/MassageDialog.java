package com.cdut.sign.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

public class MassageDialog {
	
	private static AlertDialog.Builder dialog;
	
	public static void showDialog(Context context, String massage)
	{
		dialog = new AlertDialog.Builder(context);
		dialog.setTitle("消息");
		dialog.setMessage(massage);
		dialog.setPositiveButton("确认", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		dialog.create().show();
	}
}
