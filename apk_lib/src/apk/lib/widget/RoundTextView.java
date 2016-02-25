package apk.lib.widget;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.TextView;
import apk.lib.R;
/**
 * 带圆角矩形的TextView,设置padding属性来确定图标与背景的间距
 * 
 */
@SuppressLint("DrawAllocation")
public class RoundTextView extends TextView{
	private int radiusSize;
	private boolean isFill;
	private int fillColor;
	private int roundType;
	/**
	 * 圆角矩形
	 */
	public static final int TYPE_ROUND=0;
	/**
	 * 绘制圆形
	 */
	public static final int TYPE_CIRCLE=1;
	
	public RoundTextView(Context context) {
		this(context,null);
	}
	
	public RoundTextView(Context context,AttributeSet attrs) {
		this(context,attrs,0);
	}
	public RoundTextView(Context context,AttributeSet attrs,int defStyle) {
		super(context, attrs, defStyle);
		final TypedArray a = getContext().obtainStyledAttributes(attrs,
				R.styleable.RoundIconTextView, defStyle, 0);
		roundType=a.getInt(R.styleable.RoundIconTextView_roundType,TYPE_ROUND);
		radiusSize=a.getColor(R.styleable.RoundIconTextView_radiusSize, 0);
		isFill=a.getBoolean(R.styleable.RoundIconTextView_fill, true);
		fillColor=a.getColor(R.styleable.RoundIconTextView_fillColor, Color.TRANSPARENT);
		a.recycle();
	}
	public int getRadiusSize() {
		return radiusSize;
	}
	/**
	 * 设置圆角矩形的圆角大小,只有绘制圆角矩形时有效
	 * @param radiusSize
	 */
	public void setRadiusSize(int radiusSize) {
		this.radiusSize = radiusSize;
	}
	public boolean isFill() {
		return isFill;
	}
	/**
	 * 设置是否填充背景
	 * @param isFill
	 */
	public void setFill(boolean isFill) {
		this.isFill = isFill;
	}
	public int getFillColor() {
		return fillColor;
	}
	/**
	 * 设置填充颜色
	 * @param fillColor
	 */
	public void setFillColor(int fillColor) {
		this.fillColor = fillColor;
	}
	/**
	 * 设置填充颜色
	 * @param fillColor
	 */
	public void changeColor(int fillColor) {
		this.fillColor = fillColor;
		invalidate();
	}
	public int getRoundType() {
		return roundType;
	}
	/**
	 * 设置绘制类型
	 * @param roundType
	 */
	public void setRoundType(int roundType) {
		this.roundType = roundType;
	}
	@Override
	protected void onDraw(Canvas canvas) {
		if(fillColor!=Color.TRANSPARENT){
			Paint bgpaint=new Paint(Paint.ANTI_ALIAS_FLAG);
			bgpaint.setColor(fillColor);
			if(isFill){
				bgpaint.setStyle(Paint.Style.FILL);
			}else{
				bgpaint.setStyle(Paint.Style.STROKE);
			}
			if(roundType==RoundFontIconView.TYPE_ROUND){
				RectF rect=new RectF(0, 0,getMeasuredWidth(),getMeasuredHeight());
				canvas.drawRoundRect(rect, radiusSize, radiusSize, bgpaint);
			}else{
				int cx=getMeasuredWidth()/2;
				int cy=getMeasuredHeight()/2;
				canvas.drawCircle(cx,cy,cx-1, bgpaint);
			}
		}
		super.onDraw(canvas);
	}
}	
