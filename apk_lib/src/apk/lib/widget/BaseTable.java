package apk.lib.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import apk.lib.R;

public class BaseTable extends View{
	public static final int ALIGN_LEFT=0;
	public static final int ALIGN_RIGHT=1;
	public static final int ALIGN_CENTER=2;
	/**
	 * 计量方式,dip
	 */
	public static final int METER_DIP=0;
	/**
	 * 计量方式,百分比
	 */
	public static final int METER_PERCENT=1;
	/**
	 * 是否绘制分割线 
	 */
	private boolean splitLine;
	/**
	 * 分割线颜色
	 */
	private int lineColor;
	/**
	 * 列数
	 */
	private int columnNumber;
	/**
	 * 列高度
	 */
	private float columnHeight;
	/**
	 * 头部背景颜色
	 */
	private int headerBackground;
	/**
	 * 头部文本颜色
	 */
	private int headerTextColor;
	/**
	 * 头部文本大小
	 */
	private float headerTextSize;
	/**
	 * 头部高度
	 */
	private float headerHeight;
	
	private float cellLeftPadding;
	private float cellRightPadding;
	
	private float headerLeftPadding;
	private float headerRightPadding;
	
	private int headerTextAlign;
	/**
	 * 单元格文本颜色
	 */
	private int cellTextColor;
	/**
	 * 单元格文本大小
	 */
	private float cellTextSize;
	/**
	 * 单元格文本对齐方式
	 */
	private int cellTextAlign;
	/**
	 * 列宽计算方式
	 */
	private int columnMeter;
	
	private boolean hasHeader;
	
	private int tableBackgroundColor;
	
	
	private TableAdapter adapter;
	//private Paint rectPaint;
	private Paint paint;
	public interface TableAdapter{
		/**
		 * 获取头部文本
		 * @param column 当前列,从0开始
		 * @return
		 */
		public abstract String getHeaderCellString(int columnNumber);
		/**
		 * 获取列宽度
		 * @param totalSize 总体宽度
		 * @param column
		 * @return
		 */
		public abstract float getColumnLength(int totalSize,int columnNumber);
		/**
		 * 获取行数,不包含表格头的行数
		 * @return
		 */
		public abstract int getRowCount();
		/**
		 * 获取单元格文本
		 * @param row 行
		 * @param column 列
		 * @return
		 */
		public abstract String getCellString(int row,int column);
	}
	
	public BaseTable(Context context) {
		this(context, null);
	}
	public BaseTable(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	public BaseTable(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		final TypedArray a = getContext().obtainStyledAttributes(attrs,
				R.styleable.BaseTable, defStyle, 0);
		tableBackgroundColor=a.getColor(R.styleable.BaseTable_tableBackgroundColor,Color.TRANSPARENT);
		splitLine=a.getBoolean(R.styleable.BaseTable_splitLine, false);
		lineColor=a.getColor(R.styleable.BaseTable_lineColor,Color.BLACK);
		hasHeader=a.getBoolean(R.styleable.BaseTable_hasHeader, false);
		columnNumber=a.getInt(R.styleable.BaseTable_columnNumber, 0);
		columnHeight=a.getDimension(R.styleable.BaseTable_columnHeight, 40);
		headerBackground=a.getColor(R.styleable.BaseTable_headerBackground, Color.TRANSPARENT);
		headerTextColor=a.getColor(R.styleable.BaseTable_headerTextColor, Color.GRAY);
		headerTextSize=a.getDimension(R.styleable.BaseTable_headerTextSize, 12f);
		headerHeight=a.getDimension(R.styleable.BaseTable_headerHeight, 40);
		float headerPadding=a.getDimension(R.styleable.BaseTable_headerPadding,0);
		if(headerPadding>0){
			headerLeftPadding=headerRightPadding=0;
		}
		headerLeftPadding=a.getDimension(R.styleable.BaseTable_headerLeftPadding, headerLeftPadding);
		headerRightPadding=a.getDimension(R.styleable.BaseTable_headerRightPadding, headerRightPadding);
		headerTextAlign=a.getInt(R.styleable.BaseTable_headerTextAlign, ALIGN_CENTER);
		
		float cellPadding=a.getDimension(R.styleable.BaseTable_cellPadding,0);
		if(cellPadding>0){
			cellLeftPadding=cellRightPadding=0;
		}
		cellLeftPadding=a.getDimension(R.styleable.BaseTable_cellLeftPadding, cellLeftPadding);
		cellRightPadding=a.getDimension(R.styleable.BaseTable_cellRightPadding, cellRightPadding);
		cellTextAlign=a.getInt(R.styleable.BaseTable_cellTextAlign, ALIGN_CENTER);
		cellTextColor=a.getColor(R.styleable.BaseTable_cellTextColor, Color.GRAY);
		cellTextSize=a.getFloat(R.styleable.BaseTable_cellTextSize, 12f);
		columnMeter=a.getInt(R.styleable.BaseTable_columnMeter, METER_PERCENT);
		a.recycle();
		//rectPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
		paint=new Paint(Paint.ANTI_ALIAS_FLAG);
	}
	private int mHeight;
	private int mWidth;
	private void computerViewHeight(){
		mHeight=0;
		if(hasHeader){
			mHeight+=headerHeight;
			mHeight++;
		}
		int rowCount=adapter.getRowCount();//adapter.getRowCount();
		for(int i=0;i<rowCount;i++){
			mHeight+=columnHeight;
		}
		if(splitLine){
			mHeight=mHeight+rowCount+1;
		}
	}
	
	private void computerViewWidth(int widthSpecSize){
		if(columnMeter==METER_PERCENT){
			mWidth=widthSpecSize;
		}else{
			mWidth=0;
			for(int i=0;i<columnNumber;i++){
				mWidth+=adapter.getColumnLength(widthSpecSize,i);
				if(splitLine){
					mWidth=mWidth+columnNumber+1;
				}
			}
		}
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		computerViewHeight();
		
		int widthSpecMode=MeasureSpec.getMode(widthMeasureSpec);
		int widthSpecSize=MeasureSpec.getSize(widthMeasureSpec);
		int heightSpecMode=MeasureSpec.getMode(heightMeasureSpec);
		int heightSpecSize=MeasureSpec.getSize(heightMeasureSpec);
		computerViewWidth(widthSpecSize);
		if(widthSpecMode==MeasureSpec.AT_MOST&&heightSpecMode==MeasureSpec.AT_MOST){
			setMeasuredDimension(mWidth, mHeight);
		}else if(widthSpecMode==MeasureSpec.AT_MOST){
			setMeasuredDimension(mWidth, heightSpecSize);
		}else if(heightSpecMode==MeasureSpec.AT_MOST){
			setMeasuredDimension(widthSpecSize, mHeight);
		}
	}
	
	
	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		//int rowCount=adapter.getRowCount();
		int lx=0;
		int ly=0;
		int oldColor;
		Paint.Style oldStyle;
		float oldTextSize;
		float fontX=0f;
		float fontY=0f;
		float startX=0f;
		if(hasHeader){
			//绘制背景
			if(headerBackground!=Color.TRANSPARENT){
				oldColor=paint.getColor();
				oldStyle=paint.getStyle();
				paint.setColor(headerBackground);
				paint.setStyle(Paint.Style.FILL);
				canvas.drawRect(lx,ly, mWidth, headerHeight, paint);
				paint.setColor(oldColor);
				paint.setStyle(oldStyle);
			}
			//绘制内容
			Rect textRect=new Rect();
			oldColor=paint.getColor();
			oldTextSize=paint.getTextSize();
			paint.setTextSize(headerTextSize);
			paint.setColor(headerTextColor);
			
			for(int i=0;i<columnNumber;i++){
				String headerString=adapter.getHeaderCellString(i);
				paint.getTextBounds(headerString, 0, headerString.length(), textRect);
				float columnSize=adapter.getColumnLength(mWidth, i);
				if(columnMeter==METER_PERCENT){
					columnSize=mWidth*(columnSize/100);
				}
				fontY=headerHeight/2.0f+textRect.height()/2.0f;
				if(headerTextAlign==ALIGN_LEFT){
					fontX=startX+headerLeftPadding;
				}else if(headerTextAlign==ALIGN_RIGHT){
					fontX=startX+columnSize-headerRightPadding-paint.measureText(headerString);
				}else{
					//居中方式的fontX
					fontX=startX+(columnSize/2-paint.measureText(headerString)/2);
				}
				canvas.drawText(headerString,fontX,fontY, paint);
				startX=startX+columnSize;
				
			}
			paint.setTextSize(oldTextSize);
			paint.setColor(oldColor);
			ly+=headerHeight;
		}
		//绘制文字
		if(tableBackgroundColor!=Color.TRANSPARENT){
			float tableHeight=(adapter.getRowCount())*columnHeight;
			oldColor=paint.getColor();
			oldStyle=paint.getStyle();
			paint.setColor(tableBackgroundColor);
			paint.setStyle(Paint.Style.FILL);
			if(hasHeader){
				tableHeight+=headerHeight;
			}
			canvas.drawRect(lx,ly, mWidth, tableHeight, paint);
			paint.setColor(oldColor);
			paint.setStyle(oldStyle);
		}
		fontX=0f;
		fontY=0f;
		oldColor=paint.getColor();
		oldTextSize=paint.getTextSize();
		paint.setTextSize(headerTextSize);
		paint.setColor(headerTextColor);
		for(int row=0;row<adapter.getRowCount();row++){
			startX=0f;
			for(int column=0;column<columnNumber;column++){
				String text=adapter.getCellString(row, column);
				Rect textRect=new Rect();
				paint.getTextBounds(text, 0, text.length(), textRect);
				fontY=columnHeight/2.0f+textRect.height()/2.0f+(columnHeight*row);
				if(hasHeader){
					fontY=fontY+headerHeight;
				}
				float columnSize=adapter.getColumnLength(mWidth,column);
				if(columnMeter==METER_PERCENT){
					columnSize=mWidth*(columnSize/100);
				}
				if(cellTextAlign==ALIGN_RIGHT){
					fontX=startX+columnSize-cellRightPadding-paint.measureText(text);
				}else if(cellTextAlign==ALIGN_LEFT){
					fontX=startX+cellLeftPadding;
				}else{
					//居中方式的fontX
					fontX=startX+(columnSize/2-paint.measureText(text)/2);
				}
				canvas.drawText(text,fontX,fontY, paint);
				startX=startX+columnSize;
			}
		}
		paint.setTextSize(oldTextSize);
		paint.setColor(oldColor);
		//画线
		if(splitLine){
			oldColor=paint.getColor();
			paint.setColor(lineColor);
			float strokeWidth=paint.getStrokeMiter();
			paint.setStrokeWidth(1.0f);
			//画横线
			lx=0;
			ly=0;
			if(hasHeader){
				canvas.drawLine(lx, ly,mWidth,ly, paint);
				ly+=headerHeight;
			}
			for(int i=0;i<=adapter.getRowCount();i++){
				canvas.drawLine(0,ly,mWidth,ly, paint);
				ly+=columnHeight;
			}
			ly-=columnHeight;
			canvas.drawLine(0, 0, 0, ly, paint);
			canvas.drawLine(mWidth, 0, mWidth, ly, paint);
			startX=0;
			for(int i=0;i<columnNumber;i++){
				float columnSize=adapter.getColumnLength(mWidth, i);
				if(columnMeter==METER_PERCENT){
					columnSize=mWidth*(columnSize/100);
				}
				canvas.drawLine(columnSize+startX, 0, columnSize+startX, ly, paint);
				startX+=columnSize;
			}
			paint.setColor(oldColor);
			paint.setStrokeWidth(strokeWidth);
		}
	}
	public boolean isSplitLine() {
		return splitLine;
	}
	public void setSplitLine(boolean splitLine) {
		this.splitLine = splitLine;
	}
	public int getLineColor() {
		return lineColor;
	}
	public void setLineColor(int lineColor) {
		this.lineColor = lineColor;
	}
	public int getColumnNumber() {
		return columnNumber;
	}
	public void setColumnNumber(int columnNumber) {
		this.columnNumber = columnNumber;
	}
	public float getColumnHeight() {
		return columnHeight;
	}
	public void setColumnHeight(float columnHeight) {
		this.columnHeight = columnHeight;
	}
	public int getHeaderBackground() {
		return headerBackground;
	}
	public void setHeaderBackground(int headerBackground) {
		this.headerBackground = headerBackground;
	}
	public int getHeaderTextColor() {
		return headerTextColor;
	}
	public void setHeaderTextColor(int headerTextColor) {
		this.headerTextColor = headerTextColor;
	}
	public float getHeaderTextSize() {
		return headerTextSize;
	}
	public void setHeaderTextSize(float headerTextSize) {
		this.headerTextSize = headerTextSize;
	}
	public float getHeaderHeight() {
		return headerHeight;
	}
	public void setHeaderHeight(float headerHeight) {
		this.headerHeight = headerHeight;
	}
	public float getCellLeftPadding() {
		return cellLeftPadding;
	}
	public void setCellLeftPadding(float cellLeftPadding) {
		this.cellLeftPadding = cellLeftPadding;
	}
	public float getCellRightPadding() {
		return cellRightPadding;
	}
	public void setCellRightPadding(float cellRightPadding) {
		this.cellRightPadding = cellRightPadding;
	}
	public int getCellTextColor() {
		return cellTextColor;
	}
	public void setCellTextColor(int cellTextColor) {
		this.cellTextColor = cellTextColor;
	}
	public float getCellTextSize() {
		return cellTextSize;
	}
	public void setCellTextSize(float cellTextSize) {
		this.cellTextSize = cellTextSize;
	}
	public int getCellTextAlign() {
		return cellTextAlign;
	}
	public void setCellTextAlign(int cellTextAlign) {
		this.cellTextAlign = cellTextAlign;
	}
	public int getColumnMeter() {
		return columnMeter;
	}
	public void setColumnMeter(int columnMeter) {
		this.columnMeter = columnMeter;
	}
	public TableAdapter getAdapter() {
		return adapter;
	}
	public void setAdapter(TableAdapter adapter) {
		this.adapter = adapter;
	}
	public float getHeaderLeftPadding() {
		return headerLeftPadding;
	}
	public void setHeaderLeftPadding(float headerLeftPadding) {
		this.headerLeftPadding = headerLeftPadding;
	}
	public float getHeaderRightPadding() {
		return headerRightPadding;
	}
	public void setHeaderRightPadding(float headerRightPadding) {
		this.headerRightPadding = headerRightPadding;
	}
	public int getHeaderTextAlign() {
		return headerTextAlign;
	}
	public void setHeaderTextAlign(int headerTextAlign) {
		this.headerTextAlign = headerTextAlign;
	}
	public boolean isHasHeader() {
		return hasHeader;
	}
	public void setHasHeader(boolean hasHeader) {
		this.hasHeader = hasHeader;
	}
	public int getTableBackgroundColor() {
		return tableBackgroundColor;
	}
	public void setTableBackgroundColor(int tableBackgroundColor) {
		this.tableBackgroundColor = tableBackgroundColor;
	}
	
}
