package com.osrsmapautotype;

import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.events.WidgetLoaded;
import net.runelite.api.gameval.InterfaceID;
import net.runelite.api.widgets.Widget;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

@Slf4j
@PluginDescriptor(
		name = "Map Autotype",
		description = "Automatically activates world map search when the map is opened",
		tags = {"map", "search", "keyboard", "worldmap"}
)
public class OsrsMapAutotypePlugin extends Plugin
{
	private static final int WORLDMAP_SEARCH_ACTIVATE_SCRIPT = 1736;

	@Inject
	private Client client;

	@Inject
	private ClientThread clientThread;

	@Override
	protected void startUp()
	{
		log.debug("Map Autotype started!");
	}

	@Override
	protected void shutDown()
	{
		log.debug("Map Autotype stopped!");
	}

	@Subscribe
	public void onWidgetLoaded(WidgetLoaded event)
	{
		if (event.getGroupId() != InterfaceID.WORLDMAP)
		{
			return;
		}

		clientThread.invokeLater(() ->
		{
			Widget search = client.getWidget(InterfaceID.Worldmap.SEARCH);

			if (search == null)
			{
				return false;
			}

			log.debug("World map opened; activating search");

			/*
			 * This reproduces the client script fired when the user
			 * manually activates the World Map search widget.
			 *
			 * Captured vanilla event:
			 * script 1736
			 * args: SEARCH_WIDGET_ID, 0, 1
			 */
			client.runScript(
					WORLDMAP_SEARCH_ACTIVATE_SCRIPT,
					InterfaceID.Worldmap.SEARCH,
					0,
					1
			);

			return true;
		});
	}
}