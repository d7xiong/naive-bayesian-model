package xu.main.java.data_pre_processor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.List;

import xu.main.java.configure.DataMiningConfigure;
import xu.main.java.util.TextUtil;

public class ChineseDataPreProcessor {

	private List<String> stopWordsList = null;

	public ChineseDataPreProcessor() {
		try {
			this.loadStopWords();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String dataPreProcessor(String content){
		
		return "";
	}
	
	public void dataPreProcessor(File inputFile, String outputFilePath) throws IOException {

		if (inputFile.isFile()) {
			System.out.println("process file : " + inputFile.getAbsolutePath());
			// 处理文档
			String fileContent = getFileContent(inputFile);
			String words = TextUtil.replaceCnStopWords(fileContent, stopWordsList);
			System.out.println("write to disk: " + outputFilePath);
			this.writeToDisk(words, outputFilePath);
			// 写入磁盘
			return;
		}
		File[] fileList = inputFile.listFiles();
		for (File file : fileList) {
			dataPreProcessor(file, outputFilePath + File.separator + file.getName());
		}
	}

	public void writeToDisk(String content, File outputFile) throws IOException {
		if (!outputFile.getParentFile().exists()) {
			outputFile.getParentFile().mkdirs();
		}
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile), "gb2312"));
		bw.write(content);
		bw.flush();
		bw.close();
	}

	public void writeToDisk(String content, String outputFilePath) throws IOException {
		writeToDisk(content, new File(outputFilePath));
	}

	public String getFileContent(String inputFilePath) throws IOException {
		return getFileContent(new File(inputFilePath));
	}

	public String getFileContent(File inputFile) throws IOException {
		StringBuilder resultBuilder = new StringBuilder();
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile), "gb2312"));
		String temp = "";
		while ((temp = br.readLine()) != null) {
			resultBuilder.append(temp).append("\n");
		}

		br.close();

		return resultBuilder.toString();
	}

	public void loadStopWords() throws IOException {
		this.stopWordsList = Arrays.asList(getFileContent(DataMiningConfigure.CN_STOP_WORDS_FILE_PATH).split("\n"));
	}

	public static void main(String[] args) throws IOException {
		ChineseDataPreProcessor cnDataPreProcessor = new ChineseDataPreProcessor();
		File rootFile = new File(DataMiningConfigure.CN_INPUT_FILE_ROOT_PATH);
		cnDataPreProcessor.dataPreProcessor(rootFile, DataMiningConfigure.CN_PROCESSED_OUTPUT_FILE_ROOT_PATH);
		System.out.println("preProcessor done ... ");

	}

}
