package com.ule.example;

import android.content.Context;

import com.ule.webgum.core.IWebgumPlugin;

import org.xmlpull.v1.XmlPullParser;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

/**
 * @Title:
 * @Desc:
 * @CreateTime: 2018-01-29 11:34
 * @Creator: Liy
 * @Version: 1.0
 */

final public class PluginsConfig {

	public static final List<IWebgumPlugin> loadPlugins(Context context){
		List<IWebgumPlugin> list = new ArrayList<>();
		XmlPullParser xml = context.getResources().getXml(R.xml.webgum);
		int eventType = -1;
		while (eventType != XmlPullParser.END_DOCUMENT) {
			try {
				if (eventType == XmlPullParser.START_TAG) {
					String strNode = xml.getName();
					if("feature".equals(strNode)){
						String pluginName = xml.getAttributeValue(null, "name");
						String pluginVersion = xml.getAttributeValue(null, "version");
						String pluginClass = xml.getAttributeValue(null, "value");
						Class<IWebgumPlugin> clazz = (Class<IWebgumPlugin>) Class.forName(pluginClass);
						Constructor constructor = clazz.getConstructor(new Class<?>[]{String.class, String.class});
						IWebgumPlugin plugin = (IWebgumPlugin) constructor.newInstance(pluginName,pluginVersion);
						list.add(plugin);
					}
				}
				eventType = xml.next();

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return list;
	}

}
