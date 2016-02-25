package cn.chamatou.fragment;
import cn.chamatou.R;
import android.os.Bundle;
import android.view.ViewGroup;
import apk.lib.fragment.BaseFragment;
import apk.lib.log.Logger;

/**
 * 动态也 
 *
 */
public class DynamicPageFragment extends BaseFragment{
	@Override
	protected void createPageView(ViewGroup container, Bundle savedInstanceState) {
		logger.d("createPageView");
	}

	@Override
	protected int getFragmentLayoutResource() {
		return R.layout.dynamic_page_fragment;
	}
}
