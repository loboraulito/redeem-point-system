package com.integral.util.security;

import java.util.Properties;

import org.springframework.beans.factory.FactoryBean;

import com.integral.util.Base64Coder;
import com.integral.util.DES;

/** 
 * <p>Description: [描述该类概要功能介绍]</p>
 * @author  <a href="mailto: swpigris81@126.com">代超</a>
 * @version $Revision$ 
 */
public class PropertiesEncryptFactoryBean implements FactoryBean {

    private Properties properties;
    
    /**
     * <p>Discription:[方法功能中文描述]</p>
     * @return Properties properties.
     */
    public Properties getProperties() {
        return properties;
    }

    @Override
    public Object getObject() throws Exception {
        return getProperties();
    }

    @Override
    public Class getObjectType() {
        return Properties.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
    
    /**
     * <p>Discription:[方法功能中文描述]</p>
     * @param properties The properties to set.
     */
    public void setProperties(Properties properties) {
        DES des = new DES();
        this.properties = properties;
        String originalUsername = properties.getProperty("user");
        String originalPassword = properties.getProperty("password");
        if(originalUsername != null){
            String newUser = "";
            try{
                byte[] stringToByte = des.stringToByte(originalUsername);
                byte[] decryptorByte = des.createDecryptor(stringToByte);
                newUser = new String(decryptorByte);
                
            }catch(Exception e){
                //解密失败
                newUser = originalUsername;
            }
            this.properties.put("user", newUser); 
        }
        if(originalPassword != null){
            String newPsw = "";
            try{
                byte[] stringToByte = des.stringToByte(originalUsername);
                byte[] decryptorByte = des.createDecryptor(stringToByte);
                newPsw = new String(decryptorByte);
            }catch(Exception e){
                //解密失败
                newPsw = originalPassword;
            }
            this.properties.put("password", newPsw);
        }
    }


}
