package com.example.finalproject.NewsFeed;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.finalproject.R;

/**
 * A fragment page when client click in one item of saved articles list view
 */
public class NewsSavedFragment extends Fragment {
    private Bundle dataFromActivity;
    TextView title;
    TextView text;
    TextView author;
    TextView url;
    Button deleteBt;
    private boolean isTablet;
    public void setTablet(boolean tablet) { isTablet = tablet; }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View result =  inflater.inflate(R.layout.activity_news_saved_fragment, container, false);
        dataFromActivity = getArguments();
        title = result.findViewById(R.id.articleTitle);
        text = result.findViewById(R.id.articleText);
        author = result.findViewById(R.id.author);
        url = result.findViewById(R.id.url);
        deleteBt = result.findViewById(R.id.deleteBt);

        title.setText(dataFromActivity.getString("title"));
        text.setText(dataFromActivity.getString("text"));
        author.setText(dataFromActivity.getString("author"));
        url.setText(dataFromActivity.getString("url"));
        long id = dataFromActivity.getLong("id");

        text.setMovementMethod(ScrollingMovementMethod.getInstance());

        deleteBt.setOnClickListener(clk->{
            if(isTablet) { //both the list and details are on the screen:
                NewsSavedArticle parent = (NewsSavedArticle) getActivity();
                parent.deleteMessageId((int)id); //this deletes the item and updates the list

                //now remove the fragment since you deleted it from the database:
                // this is the object to be removed, so remove(this):
                parent.getSupportFragmentManager().beginTransaction().remove(this).commit();
            }
            //for Phone:
            else
            {
                NewsEmpty parent = (NewsEmpty) getActivity();
                Intent backToFragmentExample = new Intent();
                backToFragmentExample.putExtra("id", dataFromActivity.getLong("id" ));

                parent.setResult(Activity.RESULT_OK, backToFragmentExample); //send data back to FragmentExample in onActivityResult()
                parent.finish(); //go back
            }
            title.setText("");
            author.setText("");
            text.setText("");
            url.setText("");
        });

        return result;
    }
}
