package com.ule.example;

import android.content.Context;

import com.ule.webgum.core.IWebgumPlugin;
import com.ule.webgum.core.PluginEntry;
import com.ule.webgum.core.PluginManager;
import com.ule.webgum.core.SystemWebView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.LinkedHashMap;

/**
 * @Title:
 * @Desc:
 * @CreateTime: 2018-01-29 11:34
 * @Creator: Liy
 * @Version: 1.0
 */

final public class PluginManagerFactory {

	private static boolean insideFeature = false;

	/**
	 * 插件entry
	 */
	private static final LinkedHashMap<String, PluginEntry> entryMap = new LinkedHashMap<>();


	public static final void init(Context context){
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
						PluginEntry entry = new PluginEntry(pluginName, pluginClass, pluginVersion);
						entryMap.put(entry.pluginName, entry);
					}
				}
				eventType = xml.next();
			} catch (XmlPullParserException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}


	public static PluginManager createManager(SystemWebView webView){
		PluginManager pm = new PluginManager();
		for(PluginEntry entry : entryMap.values()){
			try {
				Class<IWebgumPlugin> pluginClass = (Class<IWebgumPlugin>) Class.forName(entry.pluginClass);
				Constructor cst = pluginClass.getConstructor(SystemWebView.class,String.class, String.class);
				IWebgumPlugin plugin = (IWebgumPlugin) cst.newInstance(webView,entry.pluginName, entry.pluginVersion);
				pm.addPlugin(plugin);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return pm;
	}


}
