package com.example.mymultipleabilityapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.SSA> {

    Context context;
    List<Object> list;

    public Adapter(Context context, List<Object> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public SSA onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SSA(LayoutInflater.from(context).inflate(R.layout.recyview,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull SSA holder, int position) {

        float morew,moreh;

        if(list.size()!=0) {
            if (list.get(position) instanceof EditText) {
                holder.editText.setVisibility(View.VISIBLE);
                holder.imageView.setVisibility(View.INVISIBLE);
                holder.linearLayout.setBackground(context.getDrawable(R.drawable.layout2));
            }
            if (list.get(position) instanceof Bitmap) {
                morew=500/2f;moreh=morew;
                Bitmap bitmap=(Bitmap)list.get(position);
                Matrix matrix=new Matrix();
                matrix.postScale(morew/bitmap.getWidth(),moreh/bitmap.getHeight());
                bitmap=Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth()
                        ,bitmap.getHeight(),matrix,false);
                holder.imageView.setVisibility(View.VISIBLE);
                holder.editText.setVisibility(View.INVISIBLE);
                holder.imageView.setImageBitmap(bitmap);
                holder.linearLayout.setBackground(context.getDrawable(R.drawable.layout));
            }
            if(list.get(position) instanceof Uri){
                holder.imageView.setVisibility(View.VISIBLE);
                holder.editText.setVisibility(View.INVISIBLE);
                holder.imageView.setImageURI((Uri) list.get(position));
                holder.linearLayout.setBackground(context.getDrawable(R.drawable.layout));
            }
        }
    }
    public  void getlist(List<Object> list1){
        this.list=list1;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class SSA extends RecyclerView.ViewHolder{
        LinearLayout linearLayout;
        EditText editText;
        ImageView imageView;
        public SSA(@NonNull View itemView) {
            super(itemView);
            linearLayout=itemView.findViewById(R.id.line);
            editText=itemView.findViewById(R.id.ed);
            imageView=itemView.findViewById(R.id.im);
        }
    }
}
