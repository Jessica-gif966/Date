package com.example.nicedate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListAdaptar extends RecyclerView.Adapter<ListAdaptar.ViewHolder> implements View.OnClickListener {

    public Context context;
    View.OnClickListener listener;
    public List<ListElemetns> mData2;
    public LayoutInflater mInflater;

    public ListAdaptar(List<ListElemetns> itemList, Context context2) {
        this.mInflater = LayoutInflater.from(context2);
        this.context = context2;
        this.mData2 = itemList;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.mData2.size();
    }

    public void setOnClickListener(View.OnClickListener listener2) {
        this.listener = listener2;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = this.mInflater.inflate(R.layout.adapter_message, (ViewGroup) null);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(this.mData2.get(position));
    }
    public void setItem(List<ListElemetns> items) {
        this.mData2 = items;
    }


    @Override
    public void onClick(View v) {
        View.OnClickListener onClickListener = this.listener;
        if (onClickListener != null) {
            onClickListener.onClick(v);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mensage_enviado,mensaje_recibido;


        ViewHolder(View itemView) {
            super(itemView);
            mensage_enviado = itemView.findViewById(R.id.message_send);
            mensaje_recibido = itemView.findViewById(R.id.message_receive);

        }

        /* access modifiers changed from: package-private */
        public void bindData(ListElemetns item) {
            Chat m = new Chat();
            mensage_enviado.setText(item.getMensaje_eviado());


            if (item.getMensaje_recivido() == "") {
                m.getResponse(item.getMensaje_eviado(),mensaje_recibido,context,mData2,item.getCount());
            }
            else {
                mensaje_recibido.setText(item.getMensaje_recivido());
                mensaje_recibido.setVisibility(View.VISIBLE);
            }

        }
    }
}
