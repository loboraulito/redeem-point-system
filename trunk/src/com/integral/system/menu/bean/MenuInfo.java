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
    private String isLeave;

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

}
