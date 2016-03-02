package cn.chamatou.fragment;
import com.joanzapata.iconify.widget.IconTextView;
import cn.chamatou.R;
import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;
import apk.lib.BindingUtil;
import apk.lib.fragment.BaseFragment;

/**
 * 动态也 
 *
 */
public class DynamicPageFragment extends BaseFragment{
	private Toolbar toolbar;
	private IconTextView searchButton;
	private IconTextView messageButton;
	@SuppressLint("NewApi")
	@Override
	protected void createPageView(ViewGroup container, Bundle savedInstanceState) {
		BindingUtil.viewBinding(container, R.class);
		toolbar=findViewById(R.id.toolbar);
		if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
			toolbar.setElevation(20f);
		}else{
			ViewCompat.setElevation(toolbar, 20);
		}
		searchButton=findViewById(R.id.searchBtn);
		messageButton=findViewById(R.id.msgBtn);
		logger.d("createPageView");
	}

	@Override
	protected int getFragmentLayoutResource() {
		return R.layout.dynamic_page_fragment;
	}
}
