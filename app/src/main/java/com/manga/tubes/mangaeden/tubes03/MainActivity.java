package com.manga.tubes.mangaeden.tubes03;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{
    private RecyclerView recyclerView;
    String url = "https://www.mangaeden.com/api/list/0/";
    ArrayList<Mangalist> mangalist = new ArrayList<>();
    static ArrayList<Mangalist> listmanga = new ArrayList<>();
    ProgressDialog gress;
    RecyclerAdapter adapter;
    MaterialSearchView searchView;
    RelativeLayout previous,next;
    ArrayList<Mangalist> category=new ArrayList<>();
    SharedPreferences categoryPrefrences;
    SharedPreferences.Editor editor;
   static ArrayList<SearchPojo> searchList=new ArrayList<>();
    int page=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        gress = new ProgressDialog(this);
        gress.setIndeterminate(true);
        volleyrequest(0);
        gress.setMessage("Fetching Manga..");
        gress.show();
        categoryPrefrences=getSharedPreferences("CATEGORY",MODE_PRIVATE);
        editor=categoryPrefrences.edit();
        final List<String> g = new ArrayList<String>();
        adapter = new RecyclerAdapter();
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);
        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Do some magic
                for (SearchPojo pojo:searchList){
                    if(pojo.getTitle()!=null){
                        if(pojo.getTitle().equals(query)){
                            Intent i=new Intent(getApplicationContext(), Chapterlist.class);
                            i.putExtra("ID",pojo.getId());
                            startActivity(i);
                        }
                    }
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Do some magic
                //search manga here

                if (newText.length() > 2) {
                    g.clear();
                    for (SearchPojo l : searchList) {
                        String alias=l.getTitle().toLowerCase().trim();
                        alias=alias.replace("*","");
                        alias=alias.replace("+","");
                        alias=alias.replace("/","");
                        alias=alias.replace("!","");
                        alias=alias.replace(":","");
                        alias=alias.replace("?","");
                        alias=alias.replace("-","");
                        alias=alias.replace("[","");
                        alias=alias.replace("]","");
                        alias=alias.replace("@","");
                        alias=alias.replace("(","");
                        alias=alias.replace(")","");
                        alias=alias.replace("_","");
                        String cmpStr=newText.toLowerCase().trim();
                        if(alias!=null&&!alias.isEmpty()){
                            if (alias.contains(cmpStr)){
                                g.add(l.getTitle());
                                Log.d("RAG",l.getTitle());
                            }
                        }
                    }
                    sortlist(g);
                }
                return false;
            }
        });
        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                //Do some magic
            }
            @Override
            public void onSearchViewClosed() {
                //Do some magic
                Log.d("TAG", "search closed");
            }
        });
        previous=(RelativeLayout)findViewById(R.id.previous);
        next=(RelativeLayout)findViewById(R.id.next);
        previous.setVisibility(View.GONE);
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gress.show();
                if(page<=34&&page>0){
                    volleyrequest(--page);
                }
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gress.show();
                if(page<34&&page>=0){
                    volleyrequest(++page);
                }
            }
        });
    }

    private void sortlist(List<String> g) {
        String arrays[] = new String[g.size()];
                    arrays = g.toArray(arrays);
                    searchView.setSuggestions(arrays);
    }

    private void volleyrequest(final int  i) {
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url+"?p="+i, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        if(i==0){

                            if(previous.getVisibility()==View.VISIBLE){
                                previous.setVisibility(View.GONE);
                            }
                        }
                        if(i==34){
                            if(next.getVisibility()==View.VISIBLE){
                                next.setVisibility(View.GONE);
                            }
                        }else{
                            if(next.getVisibility()==View.GONE){
                                next.setVisibility(View.VISIBLE);
                            }

                        }
                        if(i==0){
                            if(previous.getVisibility()==View.VISIBLE){
                                previous.setVisibility(View.GONE);
                            }

                        }else{
                            if(previous.getVisibility()==View.GONE){
                                previous.setVisibility(View.VISIBLE);
                            }

                        }
                        Gson gson = new Gson();
                        Mangabasemodel people;
                        people = gson.fromJson(response.toString(), Mangabasemodel.class);
                        mangalist = people.getManga();
                        listmanga=people.getManga();
                        showcategoryData();
                        gress.dismiss();
                        
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        View view=findViewById(R.id.cordinate);
                        Log.d("TAG",error.toString());
                        Snackbar.make(view,"Check internet connection or refresh",Snackbar.LENGTH_LONG).setAction("RETRY", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                volleyrequest(0);
                            }
                        }).show();
                        gress.dismiss();
                    }
                });
        MySingleton.getInstance(this).addToRequestQueue(jsObjRequest);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MaterialSearchView.REQUEST_VOICE && resultCode == RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (matches != null && matches.size() > 0) {
                String searchWrd = matches.get(0);
                if (!TextUtils.isEmpty(searchWrd)) {
                    searchView.setQuery(searchWrd, false);
                }
            }

            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }



    public void showcategoryData(){
        String cat=categoryPrefrences.getString("CAT","All");
        if(cat.equals("All")){
            adapter.setData(listmanga);
            adapter.notifyDataSetChanged();
        }else{
            ArrayList<Mangalist> news=new ArrayList<>();
            for (Mangalist list:mangalist){
                for(String s:list.getC()){
                    if(s.equals(cat)){
                        news.add(list);
                    }
                }
            }
            adapter.setData(news);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
