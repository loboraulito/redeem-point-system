package com.integral.common.util;
/**
 * SQL参数类，用于为PrepareStatement的setObject()方法传递参数
 * @author Jason
 *
 */
public class SQLParameter {
    /**
     * PrepareStatement参数值, 如果是基本类型，则需要转换成相应的类型对象，如int型需转换成Integer
     */
    private Object obj;
    /**
     * PrepareStatement参数值对应的Sql类型（参考 java.sql.Types）
     */
    private int targetSqlType;
    
    
    public SQLParameter(Object obj, int targetSqlType) {
        this.obj = obj;
        this.targetSqlType = targetSqlType;
    }
    
    
    public SQLParameter() {
    }

    /**
     * @return PrepareStatement参数值, 如果是基本类型，则需要转换成相应的对象，如int型需转换成Integer
     */
    public Object getObj() {
        return obj;
    }
    /**
     * @param PrepareStatement参数值, 如果是基本类型，则需要转换成相应的对象，如int型需转换成Integer
     */
    public void setObj(Object obj) {
        this.obj = obj;
    }
    /**
     * @return PrepareStatement参数值对应的Sql类型（参考 java.sql.Types）
     */
    public int getTargetSqlType() {
        return targetSqlType;
    }
    /**
     * @param PrepareStatement参数值对应的Sql类型（参考 java.sql.Types）
     */
    public void setTargetSqlType(int targetSqlType) {
        this.targetSqlType = targetSqlType;
    }
    
}
