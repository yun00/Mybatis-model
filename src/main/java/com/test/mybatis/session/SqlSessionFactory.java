package com.test.mybatis.session;

import com.test.mybatis.config.Configuration;
import com.test.mybatis.config.MappedStatement;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Properties;

/**
 *
 */
public class SqlSessionFactory {

    private Configuration conf = new Configuration();

    public SqlSessionFactory(){
        loadDbInfo();

        loadMappersInfo();
    }

    public SqlSession openSession(){
        return new DefaultSqlSession(conf);
    }

    public static final String DB_CONFIG_FILE = "db.properties";
    public static final String MAPPER_CONFIG_LOCATION = "mappers";

    private void loadDbInfo(){
        InputStream dbIn = SqlSessionFactory.class.getClassLoader().getResourceAsStream(DB_CONFIG_FILE);
        Properties p = new Properties();

        try{
            p.load(dbIn);
        }catch(IOException e) {
            e.printStackTrace();
        }

        conf.setJdbcDriver(p.get("jdbc.driver").toString());
        conf.setJdbcPassword(p.get("jdbc.password").toString());
        conf.setJdbcUrl(p.get("jdbc.url").toString());
        conf.setJdbcUsername(p.get("jdbc.username").toString());
    }

    private void loadMappersInfo(){
        URL resources =  SqlSessionFactory.class.getClassLoader().getResource(MAPPER_CONFIG_LOCATION);
        File mappers = new File(resources.getFile());
        if(mappers.isDirectory()){
            File[] listFiles = mappers.listFiles();
            for(File file : listFiles){
                loadMapperInfo(file);
            }
        }

    }

    private void loadMapperInfo(File file){
        SAXReader reader  = new SAXReader();

        Document document = null;

        try{
            document = reader.read(file);
        } catch (DocumentException e){
            e.printStackTrace();
        }

        Element root = document.getRootElement();

        String namespace = root.attribute("namespace").getData().toString();

        List<Element> selects = root.elements("select");

        for(Element element : selects){
            MappedStatement mappedStatement = new MappedStatement();

            String id = element.attribute("id").getData().toString();
            String resultType = element.attribute("resultType").getData().toString();
            String sql = element.getData().toString();
            String sourceId = namespace + "." + id;

            mappedStatement.setSourceId(sourceId);
            mappedStatement.setResultType(resultType);
            mappedStatement.setSql(sql);
            mappedStatement.setNamespace(namespace);

            conf.getMappedStatementMap().put(sourceId,mappedStatement);
        }

    }
}
