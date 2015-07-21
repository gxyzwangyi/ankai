package cn.edu.shu.ankai.dialog;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import cn.edu.shu.ankai.R;

public class CityDialog extends Dialog {

	private DBManager dbm;
	private SQLiteDatabase db;
	private Spinner spinner1 = null;
	private Spinner spinner2=null;
	private Spinner spinner3=null;
	private Spinner spinner4=null;
	private Button bt_ok;
	private Button bt_cancel;
	private String province=null;
	private String city=null;
	private String district=null;
	private String location=null; 
	private Context context;
	private InputListener IListener;
	public CityDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
	}
	
	public CityDialog(Context context,
			InputListener inputListener) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
		IListener = inputListener;
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.spinnerdialog);
		spinner1=(Spinner)findViewById(R.id.spinner1);
        spinner2=(Spinner)findViewById(R.id.spinner2);
        spinner3=(Spinner)findViewById(R.id.spinner3);
        spinner4=(Spinner)findViewById(R.id.spinner4);

        bt_ok = (Button) findViewById(R.id.bt_ok);
        bt_cancel = (Button) findViewById(R.id.bt_cancel);
        bt_ok.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String address = province+city+district+location;
				IListener.getText(address);
				dismiss();
			}
		});
        bt_cancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dismiss();
			}
		});

		spinner1.setPrompt("校区");
		spinner2.setPrompt("楼幢");
		spinner3.setPrompt("楼层");
		spinner4.setPrompt("教室");
        initSpinner1();
	}
	public interface InputListener{
		void getText(String str);
	}

	public void initSpinner1(){
		dbm = new DBManager(context);
	 	dbm.openDatabase();
	 	db = dbm.getDatabase();
	 	List<MyListItem> list = new ArrayList<MyListItem>();
		
	 	try {    
	        String sql = "select * from location_1";  
	        Cursor cursor = db.rawQuery(sql,null);  
	        cursor.moveToFirst();
	        while (!cursor.isLast()){ 
		        String code=cursor.getString(cursor.getColumnIndex("id")); 
		        byte bytes[]=cursor.getBlob(2); 
		        String name=new String(bytes,"GBK");
		        MyListItem myListItem=new MyListItem();
		        myListItem.setName(name);
		        myListItem.setPcode(code);
		        list.add(myListItem);
		        cursor.moveToNext();
	        }
	        String code=cursor.getString(cursor.getColumnIndex("id")); 
	        byte bytes[]=cursor.getBlob(2); 
	        String name=new String(bytes,"GBK");
	        MyListItem myListItem=new MyListItem();
	        myListItem.setName(name);
	        myListItem.setPcode(code);
	        list.add(myListItem);
	        
	    } catch (Exception e) {  
	    } 
	 	dbm.closeDatabase();
	 	db.close();	
	 	
	 	MyAdapter myAdapter = new MyAdapter(context,list);
	 	spinner1.setAdapter(myAdapter);
		spinner1.setOnItemSelectedListener(new SpinnerOnSelectedListener1());
	}
    public void initSpinner2(String pcode){
		dbm = new DBManager(context);
	 	dbm.openDatabase();
	 	db = dbm.getDatabase();
	 	List<MyListItem> list = new ArrayList<MyListItem>();
		
	 	try {    
	        String sql = "select * from location_2 where parent_id='"+pcode+"'";  
	        Cursor cursor = db.rawQuery(sql,null);  
	        cursor.moveToFirst();
	        while (!cursor.isLast()){ 
		        String code=cursor.getString(cursor.getColumnIndex("id")); 
		        byte bytes[]=cursor.getBlob(2); 
		        String name=new String(bytes,"GBK");
		        MyListItem myListItem=new MyListItem();
		        myListItem.setName(name);
		        myListItem.setPcode(code);
		        list.add(myListItem);
		        cursor.moveToNext();
	        }
	        String code=cursor.getString(cursor.getColumnIndex("id")); 
	        byte bytes[]=cursor.getBlob(2); 
	        String name=new String(bytes,"GBK");
	        MyListItem myListItem=new MyListItem();
	        myListItem.setName(name);
	        myListItem.setPcode(code);
	        list.add(myListItem);
	        
	    } catch (Exception e) {  
	    } 
	 	dbm.closeDatabase();
	 	db.close();	
	 	
	 	MyAdapter myAdapter = new MyAdapter(context,list);
	 	spinner2.setAdapter(myAdapter);
		spinner2.setOnItemSelectedListener(new SpinnerOnSelectedListener2());
	}
    public void initSpinner3(String pcode){
		dbm = new DBManager(context);
	 	dbm.openDatabase();
	 	db = dbm.getDatabase();
	 	List<MyListItem> list = new ArrayList<MyListItem>();
		
	 	try {    
	        String sql = "select * from location_3 where parent_id='"+pcode+"'";  
	        Cursor cursor = db.rawQuery(sql,null);  
	        cursor.moveToFirst();
	        while (!cursor.isLast()){ 
		        String code=cursor.getString(cursor.getColumnIndex("id")); 
		        byte bytes[]=cursor.getBlob(2); 
		        String name=new String(bytes,"GBK");
		        MyListItem myListItem=new MyListItem();
		        myListItem.setName(name);
		        myListItem.setPcode(code);
		        list.add(myListItem);
		        cursor.moveToNext();
	        }
	        String code=cursor.getString(cursor.getColumnIndex("id")); 
	        byte bytes[]=cursor.getBlob(2); 
	        String name=new String(bytes,"GBK");
	        MyListItem myListItem=new MyListItem();
	        myListItem.setName(name);
	        myListItem.setPcode(code);
	        list.add(myListItem);
	        
	    } catch (Exception e) {  
	    } 
	 	dbm.closeDatabase();
	 	db.close();	
	 	
	 	MyAdapter myAdapter = new MyAdapter(context,list);
	 	spinner3.setAdapter(myAdapter);
		spinner3.setOnItemSelectedListener(new SpinnerOnSelectedListener3());
	}
    public void initSpinner4(String pcode){
		dbm = new DBManager(context);
	 	dbm.openDatabase();
	 	db = dbm.getDatabase();
	 	List<MyListItem> list = new ArrayList<MyListItem>();
		
	 	try {    
	        String sql = "select * from location_4 where parent_id='"+pcode+"'";  
	        Cursor cursor = db.rawQuery(sql,null);  
	        cursor.moveToFirst();
	        while (!cursor.isLast()){ 
		        String code=cursor.getString(cursor.getColumnIndex("id")); 
		        byte bytes[]=cursor.getBlob(2); 
		        String name=new String(bytes,"GBK");
		        MyListItem myListItem=new MyListItem();
		        myListItem.setName(name);
		        myListItem.setPcode(code);
		        list.add(myListItem);
		        cursor.moveToNext();
	        }
	        String code=cursor.getString(cursor.getColumnIndex("id")); 
	        byte bytes[]=cursor.getBlob(2); 
	        String name=new String(bytes,"GBK");
	        MyListItem myListItem=new MyListItem();
	        myListItem.setName(name);
	        myListItem.setPcode(code);
	        list.add(myListItem);
	        
	    } catch (Exception e) {  
	    } 
	 	dbm.closeDatabase();
	 	db.close();	
	 	
	 	MyAdapter myAdapter = new MyAdapter(context,list);
	 	spinner4.setAdapter(myAdapter);
		spinner4.setOnItemSelectedListener(new SpinnerOnSelectedListener4());
	}
    
    
   
   
 
    
	class SpinnerOnSelectedListener1 implements OnItemSelectedListener{
		
		public void onItemSelected(AdapterView<?> adapterView, View view, int position,
				long id) {
			province=((MyListItem) adapterView.getItemAtPosition(position)).getName();
			province=province.substring(0, province.length()-1);
			Log.e("埃斯皮0",province);
			String pcode =((MyListItem) adapterView.getItemAtPosition(position)).getPcode();			
			initSpinner2(pcode);
			initSpinner3(pcode);
			initSpinner4(pcode);
		}

		public void onNothingSelected(AdapterView<?> adapterView) {
			// TODO Auto-generated method stub
		}		
	}
	class SpinnerOnSelectedListener2 implements OnItemSelectedListener{
		
		public void onItemSelected(AdapterView<?> adapterView, View view, int position,
				long id) {
			city=((MyListItem) adapterView.getItemAtPosition(position)).getName();
			city=city.substring(0,city.length()-1);
			Log.e("埃斯皮01",city);
				String pcode =((MyListItem) adapterView.getItemAtPosition(position)).getPcode();
			initSpinner3(pcode);
			initSpinner4(pcode);

		}

		public void onNothingSelected(AdapterView<?> adapterView) {
			// TODO Auto-generated method stub
		}		
	}
	
	class SpinnerOnSelectedListener3 implements OnItemSelectedListener{
		
		public void onItemSelected(AdapterView<?> adapterView, View view, int position,
				long id) {
			district=((MyListItem) adapterView.getItemAtPosition(position)).getName();
			district=district.substring(0, district.length()-1);
			String pcode =((MyListItem) adapterView.getItemAtPosition(position)).getPcode();
			initSpinner4(pcode);
		}

		public void onNothingSelected(AdapterView<?> adapterView) {
			// TODO Auto-generated method stub
		}		
	}

	class SpinnerOnSelectedListener4 implements OnItemSelectedListener{
		
		public void onItemSelected(AdapterView<?> adapterView, View view, int position,
				long id) {
			location=((MyListItem) adapterView.getItemAtPosition(position)).getName();
			location=location.substring(0, location.length() - 1);

		}

		public void onNothingSelected(AdapterView<?> adapterView) {
			// TODO Auto-generated method stub
		}		
	}




}
