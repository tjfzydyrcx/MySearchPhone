package com.example.mymusic.FIleinfo;

/**
 * Created by Administrator on 2017-08-15 0015.
 */
public class Threadinfo {

    int id;
    String url;
    int start;
    int end;
    int finished;//下载的进度

    @Override
    public String toString() {
        return "Threadinfo{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", start=" + start +
                ", end=" + end +
                ", finished=" + finished +
                '}';
    }

    public Threadinfo() {
        super();
    }

    public Threadinfo(int id, String url, int start, int end, int finished) {
        super();
        this.id = id;
        this.url = url;
        this.start = start;
        this.end = end;
        this.finished = finished;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getFinished() {
        return finished;
    }

    public void setFinished(int finished) {
        this.finished = finished;
    }
}
