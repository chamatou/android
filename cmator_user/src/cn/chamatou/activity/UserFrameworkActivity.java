package cn.chamatou.activity;

import cn.chamatou.R;
import cn.chamatou.fragment.DocPageFragment;
import cn.chamatou.fragment.FoundPageFragment;
import cn.chamatou.fragment.HomePageFragment;
import cn.chamatou.fragment.DynamicPageFragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TabHost.TabSpec;
import apk.lib.BindingUtil;
import apk.lib.activity.BasicActivity;

/**
 * 用户版框架 
 *
 */
public class UserFrameworkActivity extends BasicActivity{
	private FragmentTabHost tabHost;
	private Class<?>[] tabFragments;
	private String[] tabNames;
	private int[] tabIcons;
	private LayoutInflater inflate;
	private View[] tabViews;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		BindingUtil.setContentView(this, R.layout.user_frame_activity,R.class);
		initTabs();
	}
	private void initTabs() {
		tabFragments = new Class<?>[] { DynamicPageFragment.class,
				DocPageFragment.class, FoundPageFragment.class,HomePageFragment.class};
		tabNames = new String[] {getResourceString(R.string.module_dynamic),
				getResourceString(R.string.module_doc),
				getResourceString(R.string.module_found),
				getResourceString(R.string.module_home)};
		tabIcons = new int[] { R.drawable.ic_dynamic_page_btn,
				R.drawable.ic_doc_page_btn,R.drawable.ic_found_page_btn, R.drawable.ic_home_page_btn };
		tabViews = new View[tabFragments.length];
		inflate = LayoutInflater.from(this);
		tabHost.setup(this, getSupportFragmentManager(), R.id.tabContent);
		for (int i = 0; i < tabFragments.length; i++) {
			TabSpec tabSpec = tabHost.newTabSpec(tabNames[i]).setIndicator(
					getTabItemView(i));
			tabHost.addTab(tabSpec, tabFragments[i], null);
			tabHost.getTabWidget().getChildAt(i)
					.setBackgroundResource(R.color.grey_white);
		}
		tabHost.getTabWidget().setDividerDrawable(null);
		tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
			
			@Override
			public void onTabChanged(String tabId) {
				
				//toolbarTitle.setText(titleNames[tabHost.getCurrentTab()]);
			}
		});
	}
	/**
	 *
	 * 给每个Tab按钮设置图标和文字
	 */
	private View getTabItemView(int index) {
		View view = inflate.inflate(apk.lib.R.layout.tab_host_item,null);
		ImageView imageView = (ImageView) view
				.findViewById(apk.lib.R.id.tab_item_img);
		imageView.setImageResource(tabIcons[index]);
		TextView textView = (TextView) view
				.findViewById(apk.lib.R.id.tab_item_text);
		textView.setText(tabNames[index]);
		if (index == 0) {
			imageView.setSelected(true);
			textView.setSelected(true);
		} else {
			imageView.setSelected(false);
			textView.setSelected(false);
		}
		tabViews[index] = view;
		return view;
	}
	@Override
	public String getCurrentActivityName() {
		return "用户版框架页";
	}

}
