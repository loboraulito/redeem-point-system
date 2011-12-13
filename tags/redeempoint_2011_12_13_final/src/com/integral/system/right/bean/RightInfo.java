package com.integral.system.right.bean;

/** 
 * <p>Description: [用户权限信息（用于管理用户能访问的按钮信息）]</p>
 * @author  <a href="mailto: swpigris81@126.com">Chao Dai</a>
 * @createDate 2011-6-5
 */
public class RightInfo {
    /** 权限ID **/
    private String rightId;
    /** 权限名称 **/
    private String rightName;
    /** 角色ID **/
    private String roleId;
    /** 菜单ID **/
    private String menuId;
    /** 按钮ID **/
    private String buttonId;
    /**
     * <p>Discription:[方法功能描述]</p>
     * @return String rightId.
     */
    public String getRightId() {
        return rightId;
    }
    /**
     * <p>Discription:[方法功能描述]</p>
     * @param rightId The rightId to set.
     */
    public void setRightId(String rightId) {
        this.rightId = rightId;
    }
    /**
     * <p>Discription:[方法功能描述]</p>
     * @return String rightName.
     */
    public String getRightName() {
        return rightName;
    }
    /**
     * <p>Discription:[方法功能描述]</p>
     * @param rightName The rightName to set.
     */
    public void setRightName(String rightName) {
        this.rightName = rightName;
    }
    /**
     * <p>Discription:[方法功能描述]</p>
     * @return String roleId.
     */
    public String getRoleId() {
        return roleId;
    }
    /**
     * <p>Discription:[方法功能描述]</p>
     * @param roleId The roleId to set.
     */
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
    /**
     * <p>Discription:[方法功能描述]</p>
     * @return String menuId.
     */
    public String getMenuId() {
        return menuId;
    }
    /**
     * <p>Discription:[方法功能描述]</p>
     * @param menuId The menuId to set.
     */
    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }
    /**
     * <p>Discription:[方法功能描述]</p>
     * @return String buttonId.
     */
    public String getButtonId() {
        return buttonId;
    }
    /**
     * <p>Discription:[方法功能描述]</p>
     * @param buttonId The buttonId to set.
     */
    public void setButtonId(String buttonId) {
        this.buttonId = buttonId;
    }
}