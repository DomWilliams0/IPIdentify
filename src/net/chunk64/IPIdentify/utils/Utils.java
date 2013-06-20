package net.chunk64.IPIdentify.utils;

import java.io.IOException;
import java.util.List;

import net.chunk64.IPIdentify.IPIdentify;

public class Utils
{

	public static String formatPlayerList(List<String> ipList)
	{

		StringBuilder sb = new StringBuilder();

		// Don't shorten list
		if (ipList.size() <= 3)
		{
			for (String s : ipList)
				sb.append(s + ", ");

			return sb.toString().replaceAll(", $", "");
		}

		// Shorten
		for (int i = 0; i < 3; i++)
			sb.append(ipList.get(i) + ", ");

		int remaining = ipList.size() - 3;
		return sb.toString().replaceAll(", $", "") + " + " + remaining + " others.";

	}

	public static void setToStore(String ip, List<String> list)
	{
		try
		{
			IPIdentify.ipStore.set(ip, list);
			IPIdentify.ipStore.save(IPIdentify.ipStoreFile);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

}
