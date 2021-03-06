package com.example.mediabs;

import java.util.ArrayList;
import logic.Checklist;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class DiagnosticsActivity extends Activity {

	ItemsAdapter adapter;
	
	String patDes;
	Checklist checklist;
	CheckedTextView ctv;
	ArrayList<String> dL = new ArrayList<String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_diagnostics);

		Intent lastScreen = getIntent();
		patDes = lastScreen.getStringExtra("patName");

		Update u = (Update) this.getApplication();
		
		final ArrayList<String> diaList = u.getAllDiagnosis();
		String[] elemArray = diaList.toArray(new String[diaList.size()]);

		for (Checklist checkl : u.getChecklistList()) {
			if (checkl.getOwner().equals(patDes)) {
				checklist = checkl;
			}
		}
		
		ListView lvDia = (ListView) findViewById(R.id.diagnosticListView);
		adapter = new ItemsAdapter(getApplicationContext(), elemArray);

		lvDia.setAdapter(adapter);

		if (checklist.getDiagnosisList() != null){
			dL = checklist.getDiagnosisList();
		}
		
		
		lvDia.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				ctv = (CheckedTextView) arg1;
				int index = (int) adapter.getItemId(arg2);
				if (toggle(ctv)){
					dL.add(diaList.get(index));
				} else {
					for(String elem : dL){
						ArrayList<String> newdL = new ArrayList<String>();
						if (elem != diaList.get(index)){
							newdL.add(elem);
						}
						dL = newdL;
					}				
				}
			}

		});
		
		Button okButtonDia = (Button) findViewById(R.id.okButtonDia);
		okButtonDia.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {
				
				//make the List
				System.out.println(dL);
				checklist.setDiagnosisList(dL);
				
				// Neues Intent anlegen
				Intent toDiagnosisScreen = new Intent(getApplicationContext(),
						ChecklistActivity.class);
				toDiagnosisScreen.putExtra("patient", patDes);
				startActivity(toDiagnosisScreen);
			}
		});

	}

	public boolean toggle(CheckedTextView v) {
		if (v.isChecked()) {
			v.setChecked(false);
			return false;
		} else {
			v.setChecked(true);
			return true;
		}
	}

	private class ItemsAdapter extends BaseAdapter {
		String[] items;

		public ItemsAdapter(Context context, String[] item) {
			this.items = item;
		}

		// @Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			if (v == null) {
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.activity_intervention_items, null);
			}
			CheckedTextView post = (CheckedTextView) v
					.findViewById(R.id.checkList);
			post.setText(items[position]);
			
			for (String elem : dL){
				if(elem.equals(getItem(position))){
					post.setChecked(true);
				}
			}
			
			return v;
		}

		public int getCount() {
			return items.length;
		}

		public Object getItem(int position) {
			return items[position];
		}

		public long getItemId(int position) {
			return position;
		}
	}

}

