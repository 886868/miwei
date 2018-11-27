/**
 * PropUtil.java
 */
package com.comm.web.common.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.comm.model.SystemEnv;
import com.comm.service.SystemEnvService;

/**
 * [name]<br>
 * 基本设定相关共通函数<br><br>
 * [function]<br>
 * 从属性文件或者数据库取得相应的设定信息<br><br>
 * [history]<br>
 * 2012/02/11 ver1.00 first edition JiangJusheng<br>
 */
public class PropUtil {

	/**
	 * instances of the log class
	 */
	private static Logger logger = Logger.getLogger(PropUtil.class);

	/**
	 * 設定情報保持用
	 */
	private static HashMap<String, String> innConfig = new HashMap<String, String>();

	public static void setProperty(String key, String value) {
        innConfig.put(key, value);
    }
	
	public static void initSystemEnvStatic(SystemEnvService systemEnvService) {
        List<SystemEnv> lst = systemEnvService.getAllSystemEnv();

        if (lst != null && lst.size() > 0) {
            for (SystemEnv obj : lst) {
                setProperty(obj.getItem(), obj.getValue());
            }
        } else {
            logger.info("环境表无数据");
        }
    }
	/**
	 * 初期化
	 * 注* 在Jboss AS 7.1环境下只能使用Spring提供的ClassPathXmlApplicationContext获取property属性文件的路径
	 *     直接用xxx.class.getResource()或ResourceBundle都不能取得属性文件的路径
	 */
//	public static void init() {
//		InputStream is = null;
//		try {
//			Resource rs = new ClassPathXmlApplicationContext().getResource("classpath:app.properties");
//			is = new FileInputStream(rs.getFile());
//
//			Properties p = new Properties();
//			p.load(is);
//			for (Iterator iter = p.entrySet().iterator(); iter.hasNext(); ) { 
//			    Map.Entry entry = (Map.Entry) iter.next();
//			    innConfig.put((String) entry.getKey(), (String) entry.getValue());
//			}
//			logger.debug("app prop file init success");
//
//		} catch (Exception exp) {
//			logger.error(exp);
//		} finally {
//			if (is != null) {
//				try {
//					is.close();
//				} catch (IOException ioex) {
//					logger.error(ioex);
//				}
//			}
//		}
//	}

	/**
	 * 初期化,加载属性文件
	 *
	 * @param fileName 属性文件名(全路径)
	 */
	public static void init(String fileName) {
		InputStream is = null;
		try {
			is = new FileInputStream(fileName);
			Properties p = new Properties();
			p.load(is);

			for (Iterator iter = p.entrySet().iterator(); iter.hasNext(); ) { 
			    Map.Entry entry = (Map.Entry) iter.next();
			    innConfig.put((String) entry.getKey(), (String) entry.getValue());
			}
			logger.debug("app prop file init success");
		} catch (Exception exp) {
			logger.error(exp);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException ioex) {
					logger.error(ioex);
				}
			}
		}
	}

	public static void initProp(String filePath) {
        Properties prop = new Properties();
        InputStream in = null;
        try {
            // 读取属性文件a.properties
            in = new BufferedInputStream(new FileInputStream(filePath));
            prop.load(in);

            // 加载属性列表
            for (Iterator iter = prop.entrySet().iterator(); iter.hasNext();) {
                Map.Entry entry = (Map.Entry) iter.next();
                innConfig.put((String) entry.getKey(), (String) entry.getValue());
            }

            // // 加载私钥
            // innConfig.put(Const.RSAPRIVATEKEY,
            // FileUtil.getKey(Const.PRIVATEKEY_PROP_PATH));

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
	
	/**
	 * 加载设定信息
	 *
	 * @param info 设定信息
	 */
//	public static void init(HashMap<String, String> info) {
//		innConfig.putAll(info);
//	}

	/**
	 * 获取设定信息<br>
	 * 若该KEY对应的键值不存在，则返回空白字符串
	 *
	 * @param key KEY
	 *
	 * @return String 设定信息
	 */
	public static String getProperty(String key) {
		String strs = innConfig.get(key);
		if (strs == null) {
			return "";
		} else {
			return strs.trim();
		}
	}

	/**
	 * 从数据库加载设定信息<br>
	 * 必须在属性文件加载完之后再调用
	 */
//	public static void initAppEnvInfo() {
//		Connection dbConn = null;
//		Statement stmt = null;
//		ResultSet rs = null;
//		try {
//			Class.forName(getProperty("jdbc.driverClassName"));
//			dbConn = DriverManager.getConnection(getProperty("jdbc.url"), 
//					getProperty("jdbc.username"), getProperty("jdbc.password"));
//			stmt = dbConn.createStatement();
//			rs = stmt.executeQuery("select * from SystemEnv");
//
//			while (rs.next()) {
//				innConfig.put(rs.getString(1), rs.getString(2));
//			}
//		} catch (Exception exp) {
//			logger.error(exp);
//		} finally {
//			try {
//				if (rs != null) {
//					rs.close();
//				}
//				if (stmt != null) {
//					stmt.close();
//				}
//				if (dbConn != null) {
//					dbConn.close();
//				}
//			} catch (SQLException sqlexp) {
//				logger.error(sqlexp);
//			}
//		}
//	}

}
