package cn.chamatou.biz.fragment;
import android.os.Bundle;
import android.view.ViewGroup;
import cn.chamatou.biz.activity.R;
import apk.lib.fragment.BaseFragment;
import apk.lib.widget.BaseTable;


public class MainPageFragment extends BaseFragment{
	private BaseTable table;
	private BaseTable.TableAdapter adapter;
	private String[] headerStrings;
	private String[][] cellString;
	@Override
	protected void createPageView(ViewGroup container, Bundle savedInstanceState) {
		table=findViewById(R.id.baseTable);
		headerStrings=new String[]{"姓名","年龄","出生日期","备注"};
		cellString=new String[4][4];
		for(int i=0;i<cellString.length;i++){
			for(int n=0;n<cellString.length;n++){
				cellString[i][n]="item:"+i+":"+n;
			}
		}
		adapter =new BaseTable.TableAdapter() {
			
			@Override
			public int getRowCount() {
				return 4;
			}
			
			@Override
			public String getHeaderCellString(int columnNumber) {
				return headerStrings[columnNumber];
			}
			
			@Override
			public float getColumnLength(int totalSize,int columnNumber) {
				return 25;
			}
			
			@Override
			public String getCellString(int row, int column) {
				return cellString[row][column];
			}
		};
		table.setAdapter(adapter);
	}

	@Override
	protected int getFragmentLayoutResource() {
		return R.layout.main_page_fragment;
	}
}
