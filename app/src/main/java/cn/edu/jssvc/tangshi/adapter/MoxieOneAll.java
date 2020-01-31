package cn.edu.jssvc.tangshi.adapter;

public class MoxieOneAll {

    private String poetrieid;
    private String title;
    private String poetname;
    private String content;
    private String contentmo;
    private String fenshu;
    private String time;

    public MoxieOneAll(String poetrieid, String title, String poetname, String content, String contentmo, String fenshu, String time) {
        this.poetrieid = poetrieid;
        this.title = title;
        this.poetname = poetname;
        this.content = content;
        this.contentmo = contentmo;
        this.fenshu = fenshu;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentmo() {
        return contentmo;
    }

    public void setContentmo(String contentmo) {
        this.contentmo = contentmo;
    }

    public String getFenshu() {
        return fenshu;
    }

    public void setFenshu(String fenshu) {
        this.fenshu = fenshu;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
