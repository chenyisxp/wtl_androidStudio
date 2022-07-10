package com.wtl.filter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.wtl.ui.Ble_Activity;

import android.text.InputFilter;
import android.text.Spanned;
import android.widget.Toast;

public class EditInputFilter implements InputFilter {

	public static final int MIN_VALUE = 0;

	public static final int MAX_VALUE = 220;

	public static final int PONTINT_LENGTH = 1;
	Pattern p;
	public EditInputFilter(){   
		p = Pattern.compile("[0-9]*");
	}   

	@Override  
	public CharSequence filter(CharSequence src, int start, int end,   
			Spanned dest, int dstart, int dend) {   
		String oldtext =  dest.toString();
		System.out.println(oldtext);

		if ("".equals(src.toString())) {   
			return null;
		}
		Matcher m = p.matcher(src); 
		if(oldtext.contains(".")){
			if(!m.matches()){
				return null;
			}
		}else{
			if(!m.matches() && !src.equals(".") ){
				return null;
			} 
		}
		if(!src.toString().equals("")){
			double dold = Double.parseDouble(oldtext+src.toString());
			if(dold > MAX_VALUE){
//				Toast.makeText(Ble_Activity.this, "", Toast.LENGTH_SHORT)
//				.show();
				return dest.subSequence(dstart, dend);
			}else if(dold == MAX_VALUE){
				if(src.toString().equals(".")){
//					CustomerToast.showToast(RechargeActivity.this, "");
					return dest.subSequence(dstart, dend);
				}
			}else if(dold<MIN_VALUE){
				return "";
			}
		}
		if(oldtext.contains(".")){
			int index = oldtext.indexOf(".");
			int len = dend - index;	

			if(len > PONTINT_LENGTH){
				CharSequence newText = dest.subSequence(dstart, dend);
				return newText;
			}
		}
		return dest.subSequence(dstart, dend) +src.toString();
	}   

}
