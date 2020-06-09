package com.example.frodog;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TextAdapter extends RecyclerView.Adapter<TextAdapter.ViewHolder>
{
    private ArrayList<String> mData = null;
    private SharedPref sharedPref;
    private Context mContext;
    private Cursor mCursor;
    private Typeface face;
    // 커스텀 리스너 인터페이스
    public interface OnItemClickListener
    {
        void onItemClick(View v, int pos);
    }

    public interface OnItemLongClickListener
    {
        void onItemLongClick(View v, int pos);
    }

    // 리스너 객체 참조를 저장하는 변수
    private OnItemClickListener mListener = null;
    private OnItemLongClickListener mLongListener = null;


    public void setOnItemClickListener(OnItemClickListener listener)
    {
        this.mListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener)
    {
        this.mLongListener = listener;
    }

    //
    public TextAdapter(ArrayList<String> list)
    {
        mData = list;
    }

    // ViewHolder (화면에 표시될 아이템뷰 저장)
    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView textView1;

        ViewHolder(View itemView)
        {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {

                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION)
                    {
                        mListener.onItemClick(v, pos);
                    }

                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener()
            {
                @Override
                public boolean onLongClick(View v)
                {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION)
                    {
                        mLongListener.onItemLongClick(v, pos);
                    }
                    return true;
                }
            });


            textView1 = itemView.findViewById(R.id.text1);
        }
    }


    // 아이템 뷰를 위한 뷰홀더 객체를 생성하고 리턴
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        sharedPref =new SharedPref(context);

        View view = inflater.inflate(R.layout.recyclerview_item, parent, false);
        TextAdapter.ViewHolder vh = new TextAdapter.ViewHolder(view);

        return vh;
    }


    // position에 해당되는 데이터를 뷰홀더의 아이템뷰에 표시
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        if(sharedPref.loadFont())
        {
            face= Typeface.createFromAsset(holder.itemView.getContext().getAssets(), "fonts/scdream5.otf");
        }
        else
        {
            face= Typeface.createFromAsset(holder.itemView.getContext().getAssets(), "fonts/scdream2.otf");
        }

        String text = mData.get(position);
        holder.textView1.setTypeface(face);
        holder.textView1.setText(text);
    }


    @Override
    public int getItemCount()
    {
        return mData.size();
    }

    // 어댑터에 보관되어있는 커서를 새로운 것을 바꿔 UI 갱신
    public void swapCursor(Cursor newCursor)
    {

        // 이전 커서를 닫고
        if (mCursor != null)
            mCursor.close();

        // 새 커서로 업데이트
        mCursor = newCursor;

        // 리사이클러뷰 업데이트
        if (newCursor != null)
        {
            this.notifyDataSetChanged();
        }
    }
}
