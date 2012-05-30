package com.integral.system.menu.bean;

/**
 * <p>
 * Description: [描述该类概要功能介绍]
 * </p>
 * 
 * @author <a href="mailto: swpigris81@126.com">Chao Dai</a>
 * @createDate May 18, 2011
 */
public class MenuInfo {
    private String menuId;
    private String menuName;
    private String pagePath;
    private String menuLevel;
    private String parentMenuId;
    private String parentMenuName;
    private String isLeave;
    private String isShow;
    private String comment;

    /**
     * <p>Discription:[方法功能描述]</p>
     * @return String parentMenuName.
     */
    public String getParentMenuName() {
        return parentMenuName;
    }

    /**
     * <p>Discription:[方法功能描述]</p>
     * @param parentMenuName The parentMenuName to set.
     */
    public void setParentMenuName(String parentMenuName) {
        this.parentMenuName = parentMenuName;
    }

    /**
     * <p>
     * Discription:[方法功能描述]
     * </p>
     * 
     * @return String menuId.
     */
    public String getMenuId() {
        return menuId;
    }

    /**
     * <p>
     * Discription:[方法功能描述]
     * </p>
     * 
     * @param menuId
     *            The menuId to set.
     */
    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    /**
     * <p>
     * Discription:[方法功能描述]
     * </p>
     * 
     * @return String menuName.
     */
    public String getMenuName() {
        return menuName;
    }

    /**
     * <p>
     * Discription:[方法功能描述]
     * </p>
     * 
     * @param menuName
     *            The menuName to set.
     */
    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    /**
     * <p>
     * Discription:[方法功能描述]
     * </p>
     * 
     * @return String pagePath.
     */
    public String getPagePath() {
        return pagePath;
    }

    /**
     * <p>
     * Discription:[方法功能描述]
     * </p>
     * 
     * @param pagePath
     *            The pagePath to set.
     */
    public void setPagePath(String pagePath) {
        this.pagePath = pagePath;
    }

    /**
     * <p>
     * Discription:[方法功能描述]
     * </p>
     * 
     * @return String menuLevel.
     */
    public String getMenuLevel() {
        return menuLevel;
    }

    /**
     * <p>
     * Discription:[方法功能描述]
     * </p>
     * 
     * @param menuLevel
     *            The menuLevel to set.
     */
    public void setMenuLevel(String menuLevel) {
        this.menuLevel = menuLevel;
    }

    /**
     * <p>
     * Discription:[方法功能描述]
     * </p>
     * 
     * @return String parentMenuId.
     */
    public String getParentMenuId() {
        return parentMenuId;
    }

    /**
     * <p>
     * Discription:[方法功能描述]
     * </p>
     * 
     * @param parentMenuId
     *            The parentMenuId to set.
     */
    public void setParentMenuId(String parentMenuId) {
        this.parentMenuId = parentMenuId;
    }

    /**
     * <p>
     * Discription:[方法功能描述]
     * </p>
     * 
     * @return String isLeave.
     */
    public String getIsLeave() {
        return isLeave;
    }

    /**
     * <p>
     * Discription:[方法功能描述]
     * </p>
     * 
     * @param isLeave
     *            The isLeave to set.
     */
    public void setIsLeave(String isLeave) {
        this.isLeave = isLeave;
    }

    /**
     * <p>Discription:[方法功能中文描述]</p>
     * @return String isShow.
     */
    public String getIsShow() {
        return isShow;
    }

    /**
     * <p>Discription:[方法功能中文描述]</p>
     * @param isShow The isShow to set.
     */
    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }

    /**
     * <p>Discription:[方法功能中文描述]</p>
     * @return String comment.
     */
    public String getComment() {
        return comment;
    }

    /**
     * <p>Discription:[方法功能中文描述]</p>
     * @param comment The comment to set.
     */
    public void setComment(String comment) {
        this.comment = comment;
    }
}
