//package com.example.gamebeat;
/**
 * Not used class
 **/
//
//import android.content.Context;
//import android.database.DataSetObserver;
//import android.graphics.Canvas;
//import android.graphics.Paint.Style;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Adapter;
//
//
//// Manage the draw of circles
//public class ManageGraphicElement implements Adapter{
//	
//	
//	public ManageGraphicElement(){
//		
//	}
//
//	@Override
//	public int getCount() {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public Object getItem(int position) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public long getItemId(int position) {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public int getItemViewType(int position) {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public View getView(int position, View convertView, ViewGroup parent) {
//		 Canvas canvas = new Canvas();
//	        LayoutInflater inflater = (LayoutInflater) context
//	                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//	        View row = convertView;
//	        Holder holder = null;
//
//	        if (row == null) {
//	            row = inflater.inflate(R.layout.gridCircle, parent, false);
//	            holder = new Holder();
//	            row.setTag(holder);
//	        } else {
//	            holder = (Holder) row.getTag();
//	        }
//
//	        Integer item = data.get(position);
//	        int viewWidthHalf = row.getMeasuredWidth()/2;
//	        holder.circelPaint.setStyle(Style.FILL);
//	        holder.circelPaint.setAntiAlias(true);
//	        holder.circelPaint.setColor(item);
//	        canvas.drawCircle(viewWidthHalf, 20, 30, holder.circelPaint);
//	        return row;
//
//	    }
//	}
//
//	@Override
//	public int getViewTypeCount() {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public boolean hasStableIds() {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public boolean isEmpty() {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public void registerDataSetObserver(DataSetObserver observer) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void unregisterDataSetObserver(DataSetObserver observer) {
//		// TODO Auto-generated method stub
//		
//	}
//
//}
