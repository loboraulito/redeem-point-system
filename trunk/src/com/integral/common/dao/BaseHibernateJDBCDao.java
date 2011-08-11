package com.integral.common.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.integral.util.HibernateUtils;

/**
 * <p>
 * Description: [描述该类概要功能介绍]
 * </p>
 * 
 * @author <a href="mailto: swpigris81@126.com">代超</a>
 * @version $Revision$
 */
public class BaseHibernateJDBCDao extends AbstractHibernateJDBCDao {
    
    public Connection getConnection() {
        try {
            Session curSeesion = null;
            Connection con = null;
            //curSeesion = super.getHibernateTemplate().getSessionFactory().getCurrentSession();
            curSeesion = this.getSessionFactory().openSession();
            con = curSeesion.connection();
            return con;
        }
        catch (Exception es) {
            logger.error(es.getMessage());
            return null;
        }
    }

    public ArrayList<Object> fetchObjects(ResultSet rs) {
        ArrayList<Object> ret = new ArrayList<Object>();
        logger.debug(rs);
        // example:
        // while(rs.next())
        // {
        // Object object = new Object();
        // rs.getString(1);
        // rs.getString(2);
        // ret.add(object);
        return ret;
    }

    public ArrayList<Object> getObjectsBySql(String pureSql, int start, int limit) {
        Connection con = getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            pureSql = HibernateUtils.getHibernateLimitString(HibernateUtils.getHibernateDialect(HibernateUtils.getHibernateProperties()), pureSql, start, limit);
            ps = con.prepareStatement(pureSql);
            if(start > 0){
                ps.setInt(0, start);
                ps.setInt(1, limit);
            }else{
                ps.setInt(0, limit);
            }
            rs = ps.executeQuery();
            return this.fetchObjects(rs);
        }
        catch (Exception es) {
            System.out.println(es.getMessage());
            return null;
        }
        finally {
            try {
                ps.close();
                rs.close();
                con.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
