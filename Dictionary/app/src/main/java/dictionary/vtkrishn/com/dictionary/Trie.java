package dictionary.vtkrishn.com.dictionary;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by vtkrishn on 5/14/2017.
 */

public class Trie {
    TrieNode root;

    public Trie(){
        root = new TrieNode();
    }

    class TrieNode{
        TrieNode[] children;
        boolean isLeaf;

        public TrieNode(){
            children = new TrieNode[26];
        }
    }

    public void insert(String str, Activity activity){
        TrieNode node = root;
        LinearLayout linearLayout = (LinearLayout) activity.findViewById(R.id.linear);
        for(int i=0;i<str.length();i++){
            int ch = str.charAt(i);
            int index = ch - 'a';
            if(node.children[index] == null) {
                node.children[index] = new TrieNode();
                TextView t = new TextView(activity.getApplicationContext());
                t.setId(ch);
                t.setText(""+(char)ch);

                if(linearLayout.findViewById(ch) == null)
                    linearLayout.addView(t);
                else
                    ((TextView)linearLayout.findViewById(ch)).setTextColor(ContextCompat.getColor(activity,R.color.colorAccent));
            }
            node = node.children[index];
        }
        node.isLeaf = true;
    }

}
