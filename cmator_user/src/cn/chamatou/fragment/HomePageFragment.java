package cn.chamatou.fragment;
import cn.chamatou.R;
import android.os.Bundle;
import android.view.ViewGroup;
import apk.lib.fragment.BaseFragment;


public class HomePageFragment extends BaseFragment{
	@Override
	protected void createPageView(ViewGroup container, Bundle savedInstanceState) {
		logger.d("createPageView");
	}

	@Override
	protected int getFragmentLayoutResource() {
		return R.layout.home_page_fragment;
	}
}
