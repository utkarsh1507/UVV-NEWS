package com.mrj.quicknews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.mrj.quicknews.models.NewsHeadlines;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {


    private Context context;
    private List<NewsHeadlines> headlines;
    private  SelectListener listener;

    public NewsAdapter(Context context, List<NewsHeadlines> headlines, SelectListener listener) {
        this.context = context;
        this.headlines = headlines;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.headlines_list_items, parent,false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {

        NewsHeadlines item = headlines.get(position);
        holder.text_title.setText(item.getTitle());
        holder.text_source.setText(item.getSource().getName());

        if (item.getUrlToImage()!=null){
            Picasso.get().load(item.getUrlToImage()).into(holder.img_headline);
        }

        holder.main_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onNewsClicked(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return headlines.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {

        TextView text_title,text_source;
        ImageView img_headline;

        CardView main_container;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);

            text_title = itemView.findViewById(R.id.text_title);
            text_source = itemView.findViewById(R.id.text_source);
            img_headline = itemView.findViewById(R.id.img_headline);
            main_container = itemView.findViewById(R.id.main_container);

        }
    }

}
