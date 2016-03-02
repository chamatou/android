package apk.lib.widget;

import com.joanzapata.iconify.widget.IconTextView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;
import app.lib.R;
/**
 * 货币textView,货币符号为字符图标
 *
 */
public class CurrencyTextView extends FrameLayout{
	private String currencyIcon;
	private float currencySize;
	private float amount;
	private float amountSize;
	private IconTextView currencyTextView;
	private TextView amountTextView;
	private int currencyColor;
	private int amountColor;
	public CurrencyTextView(Context context){
		this(context,null);
	}
	public CurrencyTextView(Context context, AttributeSet attrs){
		super(context, attrs);
		//this.setOrientation(LinearLayout.HORIZONTAL);
		initAttr(attrs,0);
		initLayout();
	}
	private void initAttr( AttributeSet attrs, int defStyle){
		final TypedArray a = getContext().obtainStyledAttributes(attrs,
				R.styleable.CurrencyTextView, defStyle, 0);
		currencyIcon=a.getString(R.styleable.CurrencyTextView_currency);
		if(currencyIcon==null||currencyIcon.equals("")){
			currencyIcon="{fa-cny}";
		}
		currencySize=a.getDimension(R.styleable.CurrencyTextView_currencySize, 16);
		currencyColor=a.getColor(R.styleable.CurrencyTextView_currencyColor, Color.BLACK);
		amount=a.getFloat(R.styleable.CurrencyTextView_amount, 0.0f);
		amountSize=a.getDimension(R.styleable.CurrencyTextView_amountSize, 24);
		amountColor=a.getColor(R.styleable.CurrencyTextView_amountColor, Color.BLACK);
		a.recycle();
	}
	private void initLayout() {
		FrameLayout.LayoutParams iconLayout=
				new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,FrameLayout.LayoutParams.WRAP_CONTENT);
		currencyTextView=new IconTextView(getContext());
		currencyTextView.setText(currencyIcon);
		currencyTextView.setTextSize(currencySize);
		currencyTextView.setTextColor(currencyColor);
		//currencyTextView.setBackgroundColor(Color.WHITE);
		FrameLayout.LayoutParams amountLayout=
				new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,FrameLayout.LayoutParams.WRAP_CONTENT);
		amountTextView=new TextView(getContext());
		amountTextView.setText(String.valueOf(amount));
		amountTextView.setTextSize(amountSize);
		amountTextView.setTextColor(amountColor);
		//amountTextView.setBackgroundColor(Color.WHITE);
		currencyTextView.measure(-2, -2);
		amountLayout.leftMargin=currencyTextView.getMeasuredWidth();
		amountTextView.measure(-2, -2);
		Rect bounds=new Rect();
		amountTextView.getPaint().getTextBounds(currencyTextView.getText().toString(), 0, 1, bounds);
		int bottomMargin=(amountTextView.getMeasuredHeight()-bounds.height())/2;
		iconLayout.topMargin=amountTextView.getMeasuredHeight()-currencyTextView.getMeasuredHeight()-bottomMargin;
		currencyTextView.setLayoutParams(iconLayout);
		amountTextView.setLayoutParams(amountLayout);
		this.addView(currencyTextView);
		this.addView(amountTextView);
	}
	public String getCurrencyIcon() {
		return currencyIcon;
	}
	public void setCurrencyIcon(String currencyIcon) {
		this.currencyIcon = currencyIcon;
		currencyTextView.setText(currencyIcon);
	}
	public float getCurrencySize() {
		return currencySize;
	}
	public void setCurrencySize(int currencySize) {
		this.currencySize = currencySize;
		currencyTextView.setTextSize(currencySize);
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
		amountTextView.setText(String.valueOf(amount));
	}
	public float getAmountSize() {
		return amountSize;
	}
	public void setAmountSize(int amountSize) {
		this.amountSize = amountSize;
		amountTextView.setTextSize(amountSize);
	}
	public int getCurrencyColor() {
		return currencyColor;
	}
	public void setCurrencyColor(int currencyColor) {
		this.currencyColor = currencyColor;
		currencyTextView.setTextColor(currencyColor);
	}
	public int getAmountColor() {
		return amountColor;
	}
	public void setAmountColor(int amountColor) {
		this.amountColor = amountColor;
		amountTextView.setTextColor(amountColor);
	}
}
