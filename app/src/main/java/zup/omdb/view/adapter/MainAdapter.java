package zup.omdb.view.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import zup.omdb.R;
import zup.omdb.model.request.Movie;
import zup.omdb.util.DisplayLayout;
import zup.omdb.util.Fonts;
import zup.omdb.util.Utils;
import zup.omdb.view.listener.IMainFragment;

/**
 * Created by Yumi.
 * <p>
 * <p>
 * Adapter da tela Principal para inflar os filmes
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyViewHolder> {
    private final IMainFragment listener;
    private List<Movie> mList;
    private LayoutInflater mLayoutInflater;
    private Context activity;

    public MainAdapter(Context activity, List<Movie> mList, IMainFragment listener) {
        this.mList = mList;
        this.activity = activity;
        this.listener = listener;
        mLayoutInflater = LayoutInflater.from(activity);
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        MyViewHolder mvh = null;
        View v = mLayoutInflater.inflate(R.layout.main_item, viewGroup, false);
        if (activity != null) {
            DisplayLayout.startDisplay(activity, (ViewGroup) v, Fonts.getRobotoMedium(), Typeface.NORMAL);
        }
        if (v != null) {
            mvh = new MyViewHolder(v);
        }

        return mvh;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder myViewHolder, final int position) {
        final int posit = position;
        final Movie movie = mList.get(position);

        if ((posit % 2) == 0) {
            myViewHolder.rl_main_item.setBackgroundResource(R.drawable.button_white);
        } else {
            myViewHolder.rl_main_item.setBackgroundResource(R.drawable.button_ice);
        }

        myViewHolder.txtTitle.setText(movie.getTitle());

        if(Utils.verifyCanShow(movie.getMetascore())){
            myViewHolder.imgMetascore.setVisibility(View.VISIBLE);
            myViewHolder.txtMetascore.setVisibility(View.VISIBLE);
            myViewHolder.txtMetascore.setText(movie.getMetascore());
        } else {
            myViewHolder.imgMetascore.setVisibility(View.GONE);
            myViewHolder.txtMetascore.setVisibility(View.GONE);
        }
        if(Utils.verifyCanShow(movie.getImdbRating())){
            myViewHolder.imgStar.setVisibility(View.VISIBLE);
            myViewHolder.txtStar.setVisibility(View.VISIBLE);
            myViewHolder.txtStar.setText(movie.getImdbRating());
        } else {
            myViewHolder.imgStar.setVisibility(View.GONE);
            myViewHolder.txtStar.setVisibility(View.GONE);
        }

        myViewHolder.rl_main_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.setOnClick(posit);
            }
        });

        Utils.imageLoader(myViewHolder.imgPoster, movie.getPath(), new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                myViewHolder.imgPosterHolder.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

                myViewHolder.imgPosterHolder.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {

                myViewHolder.imgPosterHolder.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

                myViewHolder.imgPosterHolder.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mList == null) {
            return 0;
        }

        return mList.size();
    }

    public void update(List<Movie> mList) {
        this.mList = mList;
        this.notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_star)
        public TextView txtStar;
        @BindView(R.id.txt_megascore)
        public TextView txtMetascore;
        @BindView(R.id.txt_title)
        public TextView txtTitle;
        @BindView(R.id.img_metascore)
        public ImageView imgMetascore;
        @BindView(R.id.img_poster)
        public ImageView imgPoster;
        @BindView(R.id.img_star)
        public ImageView imgStar;
        @BindView(R.id.rl_main_item)
        public RelativeLayout rl_main_item;
        @BindView(R.id.img_poster_holder)
        public ImageView imgPosterHolder;

        public MyViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            txtStar.setTypeface(Fonts.getRobotoRegular());
            txtMetascore.setTypeface(Fonts.getRobotoRegular());
            txtTitle.setTypeface(Fonts.getRobotoRegular());
        }

    }

}