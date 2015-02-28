package lettersample.softdream.com;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import lettersample.softdream.com.utils.ChineseNameGener;


public class MainActivity extends ActionBarActivity {

    private ListView mListView;
    private FriendAdapter mFriendAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = (ListView) findViewById(R.id.list_item);
        mFriendAdapter=new FriendAdapter(this);
        for (int i=0;i<20;i++){
            mFriendAdapter.addItem(new Friend(ChineseNameGener.generName()));
        }
        mListView.setAdapter(mFriendAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

       switch (id){
           case R.id.action_show:
               mFriendAdapter.setShowKey(true);
               mFriendAdapter.notifyDataSetChanged();
               return true;
           case R.id.action_unshow:
               mFriendAdapter.setShowKey(false);
               mFriendAdapter.notifyDataSetChanged();
               return true;
       }

        return super.onOptionsItemSelected(item);
    }
}
