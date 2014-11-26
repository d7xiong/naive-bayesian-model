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

public class EnglishDataPreProcessor {

	private List<String> stopWordsList = null;

	public EnglishDataPreProcessor() {
		try {
			this.loadStopWords();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void dataPreProcessor(File inputFile, String outputFilePath) throws IOException {

		if (inputFile.isFile()) {
			System.out.println("process file : " + inputFile.getAbsolutePath());
			// 处理文档
			String fileContent = getFileContent(inputFile);
			String words = TextUtil.replaceEnStopWords(fileContent, stopWordsList);
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
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile)));
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
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile)));
		String temp = "";
		while ((temp = br.readLine()) != null) {
			resultBuilder.append(temp).append("\n");
		}

		br.close();

		return resultBuilder.toString();
	}

	public void loadStopWords() throws IOException {
		this.stopWordsList = Arrays.asList(getFileContent(DataMiningConfigure.EN_STOP_WORDS_FILE_PATH).split("\n"));
	}

	public static void main(String[] args) throws IOException {
		EnglishDataPreProcessor enDataPreProcessor = new EnglishDataPreProcessor();
		File rootFile = new File(DataMiningConfigure.EN_INPUT_FILE_ROOT_PATH);
		enDataPreProcessor.dataPreProcessor(rootFile, DataMiningConfigure.EN_PROCESSED_OUTPUT_FILE_ROOT_PATH);
		System.out.println("preProcessor done ... ");

	}

}
