package cn.org.awcp.core.utils.mongodb;

import java.io.IOException;
import java.io.InputStream;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

public class MongoDBUtils {
	private final static Properties config = new Properties();

	static {
		InputStream in = MongoDBUtils.class.getClassLoader().getResourceAsStream("conf/awcp.properties");
		try {
			config.load(in);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public static MongoClient getMongoClient() {
		try {
			List<ServerAddress> seeds = new ArrayList<ServerAddress>();
			String urls = config.getProperty("mongo.url", "");
			if (StringUtils.isNotBlank(urls)) {
				String[] url = urls.split(";");
				for (int i = 0; i < url.length; i++) {
					String tmp = url[i];
					if (tmp.indexOf("@") != -1) {

					} else if (tmp.indexOf(":") != -1) {
						ServerAddress address = new ServerAddress(tmp.split(":")[0],
								Integer.valueOf(tmp.split(":")[1]));
						seeds.add(address);
					} else {
						ServerAddress address = new ServerAddress(tmp.split(":")[0], 27017);
						seeds.add(address);
					}
				}
			}
			if (seeds.size() == 0) {
				seeds.add(new ServerAddress("127.0.0.1", 27017));
			}
			MongoClient mongoClient = new MongoClient(seeds);
			return mongoClient;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return null;
	}

}
