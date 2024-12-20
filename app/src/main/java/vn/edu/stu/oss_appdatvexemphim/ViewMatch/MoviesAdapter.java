package vn.edu.stu.oss_appdatvexemphim.ViewMatch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.edu.stu.oss_appdatvexemphim.Models.Movies;
import vn.edu.stu.oss_appdatvexemphim.R;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {
    private List<Movies> movieList;
    private Context context;

    int resoure;
    public MoviesAdapter(Context context, int resoure,List<Movies> movieList) {
        this.context = context;
        this.movieList = movieList;
        this.resoure = resoure;
    }

    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(resoure, parent, false);
        return new MoviesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MoviesViewHolder holder, int position) {

        holder.tv_movieName.setText(this.movieList.get(position).getMovie_name());
        holder.tv_movieLength.setText( this.movieList.get(position).getMovie_length());
        holder.tv_movieGenres.setText(this.movieList.get(position).getMovie_genres());
        holder.movieImageView.setImageResource(this.movieList.get(position).getMovie_poster());

    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public static class MoviesViewHolder extends RecyclerView.ViewHolder {
        TextView tv_movieName,tv_movieLength,tv_movieGenres;
        ImageView movieImageView;


        public MoviesViewHolder(View itemView) {
            super(itemView);
            tv_movieName = itemView.findViewById(R.id.tv_Item_movieName);
            tv_movieLength = itemView.findViewById(R.id.tv_Item_length);
            tv_movieGenres = itemView.findViewById(R.id.tv_Item_genres);
            movieImageView = itemView.findViewById(R.id.img_Item_movieImage);
        }
    }
}
