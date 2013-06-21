package net.chunk64.IPIdentify.utils;

import java.io.IOException;
import java.util.List;

import net.chunk64.IPIdentify.IPIdentify;

public class Utils
{

	public static String formatPlayerList(List<String> nameList)
	{

		StringBuilder sb = new StringBuilder();

		// Don't shorten list
		if (nameList.size() <= 3)
		{
			for (String s : nameList)
				sb.append(s + ", ");

			return sb.toString().replaceAll(", $", "");
		}

		// Shorten
		for (int i = 0; i < 3; i++)
			sb.append(nameList.get(i) + ", ");

		int remaining = nameList.size() - 3;
		String plural = (remaining == 1) ? "." : "s.";
		return sb.toString().replaceAll(", $", "") + " + " + remaining + " other" + plural;

	}

	public static String formatFullList(List<String> nameList)
	{
		StringBuilder sb = new StringBuilder();

		// Only 1 long
		if (nameList.size() == 1) return nameList.get(0);

		// Append all except last one
//		for (String s : nameList)
//			if (!nameList.get(nameList.size() - 2).equals(s)) sb.append(s + ", ");
		for (int i = 0; i < nameList.size() - 1; i++)
			sb.append(nameList.get(i) + ", ");

		// Trim off final comma and add final element
		String str = sb.toString().replaceAll(", $", "");
		return str += " and " + nameList.get(nameList.size() - 1);

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
