package com.osrsmapautotype;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class OsrsMapAutotypePluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(OsrsMapAutotypePlugin.class);
		RuneLite.main(args);
	}
}