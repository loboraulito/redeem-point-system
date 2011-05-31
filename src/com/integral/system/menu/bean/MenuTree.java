package com.integral.system.menu.bean;

/** 
 * <p>Description: [菜单树]</p>
 * @author  <a href="mailto: swpigris81@126.com">Chao Dai</a>
 * @createDate 2011-5-29
 */
public class MenuTree {
    private String id; // ID
    private String text; // 节点显示
    private String cls; // 图标
    private boolean leaf; // 是否叶子
    private String href; // 链接
    private String hrefTarget; // 链接指向
    private boolean expandable; // 是否展开
    private String description; // 描述信息
    private String qtip;// 提示信息
    private boolean isTarget; // 是否可以链接到目标地址
    private boolean singleClickExpand; //是否单击展开节点
    private Object listeners; //监听器
    
    /**
     * <p>Discription:[方法功能描述]</p>
     * @return boolean isTarget.
     */
    public boolean isTarget() {
        return isTarget;
    }
    /**
     * <p>Discription:[方法功能描述]</p>
     * @param isTarget The isTarget to set.
     */
    public void setTarget(boolean isTarget) {
        this.isTarget = isTarget;
    }
    /**
     * <p>Discription:[方法功能描述]</p>
     * @return boolean singleClickExpand.
     */
    public boolean isSingleClickExpand() {
        return singleClickExpand;
    }
    /**
     * <p>Discription:[方法功能描述]</p>
     * @param singleClickExpand The singleClickExpand to set.
     */
    public void setSingleClickExpand(boolean singleClickExpand) {
        this.singleClickExpand = singleClickExpand;
    }
    /**
     * <p>Discription:[方法功能描述]</p>
     * @return Object listeners.
     */
    public Object getListeners() {
        return listeners;
    }
    /**
     * <p>Discription:[方法功能描述]</p>
     * @param listeners The listeners to set.
     */
    public void setListeners(Object listeners) {
        this.listeners = listeners;
    }
    /**
     * <p>Discription:[方法功能描述]</p>
     * @return String id.
     */
    public String getId() {
        return id;
    }
    /**
     * <p>Discription:[方法功能描述]</p>
     * @param id The id to set.
     */
    public void setId(String id) {
        this.id = id;
    }
    /**
     * <p>Discription:[方法功能描述]</p>
     * @return String text.
     */
    public String getText() {
        return text;
    }
    /**
     * <p>Discription:[方法功能描述]</p>
     * @param text The text to set.
     */
    public void setText(String text) {
        this.text = text;
    }
    /**
     * <p>Discription:[方法功能描述]</p>
     * @return String cls.
     */
    public String getCls() {
        return cls;
    }
    /**
     * <p>Discription:[方法功能描述]</p>
     * @param cls The cls to set.
     */
    public void setCls(String cls) {
        this.cls = cls;
    }
    /**
     * <p>Discription:[方法功能描述]</p>
     * @return boolean leaf.
     */
    public boolean isLeaf() {
        return leaf;
    }
    /**
     * <p>Discription:[方法功能描述]</p>
     * @param leaf The leaf to set.
     */
    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }
    /**
     * <p>Discription:[方法功能描述]</p>
     * @return String href.
     */
    public String getHref() {
        return href;
    }
    /**
     * <p>Discription:[方法功能描述]</p>
     * @param href The href to set.
     */
    public void setHref(String href) {
        this.href = href;
    }
    /**
     * <p>Discription:[方法功能描述]</p>
     * @return String hrefTarget.
     */
    public String getHrefTarget() {
        return hrefTarget;
    }
    /**
     * <p>Discription:[方法功能描述]</p>
     * @param hrefTarget The hrefTarget to set.
     */
    public void setHrefTarget(String hrefTarget) {
        this.hrefTarget = hrefTarget;
    }
    /**
     * <p>Discription:[方法功能描述]</p>
     * @return boolean expandable.
     */
    public boolean isExpandable() {
        return expandable;
    }
    /**
     * <p>Discription:[方法功能描述]</p>
     * @param expandable The expandable to set.
     */
    public void setExpandable(boolean expandable) {
        this.expandable = expandable;
    }
    /**
     * <p>Discription:[方法功能描述]</p>
     * @return String description.
     */
    public String getDescription() {
        return description;
    }
    /**
     * <p>Discription:[方法功能描述]</p>
     * @param description The description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }
    /**
     * <p>Discription:[方法功能描述]</p>
     * @return String qtip.
     */
    public String getQtip() {
        return qtip;
    }
    /**
     * <p>Discription:[方法功能描述]</p>
     * @param qtip The qtip to set.
     */
    public void setQtip(String qtip) {
        this.qtip = qtip;
    }
}
