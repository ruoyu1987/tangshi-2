package cn.edu.jssvc.tangshi.adapter;

public class MoxieAll {

    private String poetrieid;
    private String title;
    private String poetname;
    private String time;

    public MoxieAll(String poetrieid, String title, String poetname, String time) {
        this.poetrieid = poetrieid;
        this.title = title;
        this.poetname = poetname;
        this.time = time;
    }

    public String getPoetrieid() {
        return poetrieid;
    }

    public void setPoetrieid(String poetrieid) {
        this.poetrieid = poetrieid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoetname() {
        return poetname;
    }

    public void setPoetname(String poetname) {
        this.poetname = poetname;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
