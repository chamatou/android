package apk.lib.recycleview;

import java.util.List;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
/**
 * 单一文本adapter填充RecycleView 
 *
 */
public class SimpleRecyclerAdapter extends RecyclerView.Adapter<SimpleRecyclerAdapter.SimpleHolder>{
	private List<String> list;
	
	public SimpleRecyclerAdapter(List<String> list){
		this.list=list;
	}
	
	class SimpleHolder extends RecyclerView.ViewHolder{
		//TextView tv;
		public SimpleHolder(View itemView) {
			super(itemView);
		}
		
	}

	@Override
	public SimpleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		TextView view=new TextView(parent.getContext());
		view.setTextSize(24);
		return new SimpleHolder(view);
	}

	@Override
	public void onBindViewHolder(SimpleHolder holder, int position) {
		((TextView)holder.itemView).setText(list.get(position));
	}

	@Override
	public int getItemCount() {
		return list.size();
	}
}
