package xu.main.java.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;

public class TextUtil {

	public static String replaceCnStopWords(String line, List<String> stopWordsList) {
		char[] res = line.replaceAll("\r", "").replaceAll("\n", "").toCharArray();
		StringBuilder resultBuilder = new StringBuilder();
		for (int index = 0, len = res.length; index < len; index++) {
			if (!String.valueOf(res[index]).isEmpty() && !stopWordsList.contains(String.valueOf(res[index]).toLowerCase())) {
				resultBuilder.append(res[index]).append("\t");
			}
		}
		return resultBuilder.toString();
	}

	public static String replaceEnStopWords(String line, List<String> stopWordsList) {
		String[] res = line.split("[^a-zA-Z]");
		StringBuilder resultBuilder = new StringBuilder();
		for (int index = 0, len = res.length; index < len; index++) {
			if (!res[index].isEmpty() && !stopWordsList.contains(res[index].toLowerCase())) {
				resultBuilder.append(res[index]).append("\t");
			}
		}
		return resultBuilder.toString();
	}

	public static String loadFile(String filePath, String charset) throws IOException {
		return loadFile(new File(filePath), charset);
	}

	public static String loadFile(File inputFile, String charset) throws IOException {

		StringBuilder resultBuilder = new StringBuilder();
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile), charset));
		String temp = "";
		while ((temp = br.readLine()) != null) {
			resultBuilder.append(temp).append("\t");
		}
		br.close();
		return resultBuilder.toString();
	}

	public static void writeToDisk(String content, File outputFile, String charset) throws IOException {
		if (!outputFile.getParentFile().exists()) {
			outputFile.getParentFile().mkdirs();
		}
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile), charset));
		bw.write(content);
		bw.flush();
		bw.close();
	}
}
