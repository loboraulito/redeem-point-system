package com.integral.util.properties;

import java.io.InputStream;
import java.util.Properties;

public class PropertiesReader extends Properties {
    private static PropertiesReader reader;
    
    private InputStream is = getClass().getResourceAsStream("/jdbc.properties");
    
    private PropertiesReader() throws Exception{
        try{
            this.load(is);
            is.close();
        }catch(Exception e){
            throw e;
        }
    }
    
    public static PropertiesReader getInstance() throws Exception{
        if(reader == null){
            return new PropertiesReader();
        }else{
            return reader;
        }
    }
}
