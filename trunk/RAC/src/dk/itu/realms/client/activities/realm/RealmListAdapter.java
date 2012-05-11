package dk.itu.realms.client.activities.realm;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import dk.itu.realms.client.R;
import dk.itu.realms.client.model.Realm;

public class RealmListAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private Context context;

	private List<Realm> data;

	public RealmListAdapter(Context context, List<Realm> data) {
		mInflater = LayoutInflater.from(context);
		this.context = context;
		this.data = data;
	}

	/**
	 * Make a view to hold each row.
	 * 
	 * @see android.widget.ListAdapter#getView(int, android.view.View,
	 *      android.view.ViewGroup)
	 */
	public View getView(final int position, View convertView, ViewGroup parent) {
		/* 
		 * a ViewHolder keeps references to children views to avoid unnecessary 
		 * calls to findViewById() on each row 
		 */
		ViewHolder holder;

		/* 
		 * When convertView is not null, we can reuse it directly, there is no need
		 * to reinflate it. We only inflate a new View when the convertView supplied by ListView is null 
		 */
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.realm_adapter_content, null);

			/* 
			 * Creates a ViewHolder and store references to the two children 
			 * views we want to bind data to
			 */
			holder = new ViewHolder();
			holder.titleView = (TextView) convertView.findViewById(R.id.titleView);
			holder.descriptionView = (TextView) convertView.findViewById(R.id.descriptionView);
			holder.locationDescriptionView = (TextView) convertView.findViewById(R.id.locationDescriptionView);
			
			convertView.setOnClickListener(new OnClickListener() {
				private int pos = position;

				@Override
				public void onClick(View v) {
				}
			});

			convertView.setTag(holder);
		} else {
			/* 
			 * Get the ViewHolder back to get fast access to the 
			 * visual items
			 */
			holder = (ViewHolder) convertView.getTag();
		}
		
		final Realm rowData = data.get(position);

		holder.titleView.setText(rowData.getName());
		
		holder.descriptionView.setText(rowData.getGeneralDescription());

		holder.locationDescriptionView.setText(rowData.getLocationDecription());

		return convertView;
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	static class ViewHolder {
		TextView titleView;
		TextView descriptionView;
		TextView locationDescriptionView;
		Button connectButton;
	}

}