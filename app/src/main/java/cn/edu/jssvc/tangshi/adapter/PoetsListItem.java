package cn.edu.jssvc.tangshi.adapter;

public class PoetsListItem {

    private String id;
    private String name;
    private String Title;

    public PoetsListItem(String id, String name, String title) {
        this.id = id;
        this.name = name;
        Title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }
}
