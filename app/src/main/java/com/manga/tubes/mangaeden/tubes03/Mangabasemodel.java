package com.manga.tubes.mangaeden.tubes03;
import com.manga.tubes.mangaeden.tubes03.Mangalist;

import java.util.ArrayList;

public class Mangabasemodel {
    int end; //end page
    ArrayList<Mangalist> manga; //manga list
    int page;   //page no
    int start;  //start of page
    long total; //total
    public  Mangabasemodel(){

    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public ArrayList<Mangalist> getManga() {
        return manga;
    }

    public void setManga(ArrayList<Mangalist> manga) {
        this.manga = manga;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
