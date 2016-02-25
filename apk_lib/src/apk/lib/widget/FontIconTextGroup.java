package apk.lib.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;
/**
 * 带字符图标和文字的控件 
 *
 */
public class FontIconTextGroup extends LinearLayout{
	private TextView textView;
	
	public FontIconTextGroup(Context context) {
		this(context, null);
	}
	public FontIconTextGroup(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
}
