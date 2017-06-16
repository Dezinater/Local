package gorillasquad.local;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Jason on 6/14/2017.
 */

public class CommentAdapter extends ArrayAdapter<Post> {

    private PostHandler ph;

    public CommentAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public CommentAdapter(Context context, ArrayList<Post> items,PostHandler ph) {
        super(context, R.layout.post, items);
        this.ph = ph;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        LayoutInflater vi;
        vi = LayoutInflater.from(getContext());
        v = vi.inflate(R.layout.comment,null);
        final Post p = getItem(position);


        int colour = 0;
        if(p.getColour() != null){
            colour = Color.parseColor(p.getColour());
        }
        Drawable authorIcon = getContext().getResources().getDrawable(R.drawable.author_icon);
        authorIcon.mutate().setColorFilter(colour, PorterDuff.Mode.MULTIPLY );

        TextView postText = (TextView) v.findViewById(R.id.postText);
        TextView rating = (TextView) v.findViewById(R.id.rating);
        TextView commentIcon = (TextView) v.findViewById(R.id.commentIcon);

        postText.setText(p.getText());
        rating.setText(p.getRating()+"");
        commentIcon.setText(p.getIcon());
        commentIcon.setBackgroundDrawable(authorIcon);

        ImageButton upVoteButton = (ImageButton) v.findViewById(R.id.upVoteButton);
        ImageButton downVoteButton = (ImageButton) v.findViewById(R.id.downVoteButton);

        upVoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ph.voteComment(p.getKey(),true);
            }
        });
        downVoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ph.voteComment(p.getKey(),false);
            }
        });

        if(p.getUpVotes().contains(ph.getMyId())){
            upVoteButton.setImageResource(R.drawable.up_arrow_highlight);
        }else if(p.getDownVotes().contains(ph.getMyId())){
            downVoteButton.setImageResource(R.drawable.down_arrow_highlight);
        }

        return v;
    }
}
