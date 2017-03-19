package com.firefly.cache.memcache.conf;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import org.dom4j.io.SAXReader;
import com.firefly.cache.memcache.exception.MemcacheInitException;

public class MemcacheConfig {

	List<MemcachePoolConfig> poolConfigs;

	@SuppressWarnings("unchecked")
	public static MemcacheConfig parseXmlConfig(InputStream in)
			throws MemcacheInitException {
		MemcacheConfig config = new MemcacheConfig();
		SAXReader reader = new SAXReader();
		Document doc;
		try {
			doc = reader.read(in);

			Element root = doc.getRootElement();
			// init pool conf
			for (Iterator<Element> i = root.elementIterator("pool"); i
					.hasNext();) {
				MemcachePoolConfig poolConfig = parseXmlPoolNode(i.next());
				config.addPoolConfig(poolConfig);
			}
		} catch (DocumentException e) {
			throw new MemcacheInitException(
					"memcache config document parse error:", e);
		} catch (Exception e) {
			throw new MemcacheInitException("memcache config parse error:", e);
		}

		return config;
	}

	private static MemcachePoolConfig parseXmlPoolNode(Element poolEle)
			throws MemcacheInitException {
		MemcachePoolConfig poolConf = new MemcachePoolConfig();
		poolConf.setPoolName(XmlParser.parseAttr(poolEle, "id"));
		poolConf.setInitConn(XmlParser.parseIntAttr(poolEle, "initConn"));
		poolConf.setMinConn(XmlParser.parseIntAttr(poolEle, "minConn"));
		poolConf.setMaxConn(XmlParser.parseIntAttr(poolEle, "maxConn"));
		poolConf.setMaintSleep(XmlParser.parseLongAttr(poolEle, "maintSleep"));
		// poolConf.setFailover(XmlParser.parseBooleanAttr(poolEle,"failover"));
		// 代码设置该参数
		poolConf.setSocketTo(XmlParser.parseIntAttr(poolEle, "socketTO"));
		poolConf.setSocketConnTo(XmlParser.parseIntAttr(poolEle,
				"socketConnectTO"));
		poolConf.setDefaultExpiryMinutes(XmlParser.parseIntAttr(poolEle,
				"defaultExpiryMinutes"));
		poolConf.setCompressEnable(XmlParser.parseBooleanAttr(poolEle,
				"compressEnable"));
		// 默认开启版本号支持
		try {
			poolConf.setVersionEnable(XmlParser.parseBooleanAttr(poolEle,
					"versionEnable"));
		} catch (Exception e) {
			// 应对配置文件中没有配versionEnable这一场景
			poolConf.setVersionEnable(true);
		}
		poolConf.setCompressThreshold(XmlParser.parseIntAttr(poolEle,
				"compressThreshold"));

		List<Element> serverEles = poolEle.selectNodes("servers/server");
		String[] servers = new String[serverEles.size()];
		for (int i = 0; i < serverEles.size(); i++) {
			servers[i] = serverEles.get(i).getText();
		}
		poolConf.setServers(servers);

		return poolConf;

	}

	public MemcacheConfig() {
		poolConfigs = new ArrayList();
	}

	public List<MemcachePoolConfig> getPoolConfigs() {
		return poolConfigs;
	}

	public MemcachePoolConfig getPoolConfig(String poolName) {
		for (MemcachePoolConfig poolConf : this.poolConfigs) {
			if (poolConf.getPoolName().equals(poolName)) {
				return poolConf;
			}
		}

		return null;
	}

	public void addPoolConfig(MemcachePoolConfig poolConfig) {
		this.poolConfigs.add(poolConfig);
	}

}
