package xu.main.java.Statistics;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import xu.main.java.configure.DataMiningConfigure;
import xu.main.java.util.StringUtil;
import xu.main.java.util.TextUtil;

public class CountStatistics {

	private List<String> stopWordsList = new ArrayList<String>();

	private String inputFileCharset = "UTF-8";
	/* 训练集样本单词总数 */
	private double totalWordsNum = 0.0;

	private Set<String> totalWordsSet = new HashSet<String>();
	/* 每类文本单词总数 */
	private Map<String, Integer> categoryTotalWordsMap = new HashMap<String, Integer>();
	/* 每类文本中出现的单词和出现次数map */
	private Map<String, Map<String, Integer>> categoryWordsAndCountMap = new HashMap<String, Map<String, Integer>>();
	/* 每类文本和此类文本的先验概率 */
	Map<String, Double> categoryPriorProbability = new HashMap<String, Double>();

	public CountStatistics(String charset) {
		this.inputFileCharset = charset;
		try {
			this.loadStopWords();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Map<String, String> matchCategoryProbability(String input) {
		String words = TextUtil.replaceCnStopWords(input, stopWordsList);
		String[] wordArray = words.split("\t");

		Map<String, String> matchResult = new HashMap<String, String>();
		Set<Entry<String, Map<String, Integer>>> set = categoryWordsAndCountMap.entrySet();
		for (Iterator<Entry<String, Map<String, Integer>>> it = set.iterator(); it.hasNext();) {
			Entry<String, Map<String, Integer>> entry = it.next();
			BigDecimal matchedProbability = new BigDecimal(1.0);
			for (String word : wordArray) {
				if (!totalWordsSet.contains(word)) {
					continue;
				}
				matchedProbability = matchedProbability.multiply(computeConditionalprobability(entry.getKey(), word));
				// matchedProbability *=
				// computeConditionalprobability(entry.getKey(), word);
			}
			matchResult.put(entry.getKey(), matchedProbability.multiply(new BigDecimal(categoryPriorProbability.get(entry.getKey()))).toEngineeringString());
		}

		return matchResult;
	}

	// 计算条件概率
	public BigDecimal computeConditionalprobability(String categoryName, String input) {
		// P(tk|C) (类C下单词tk出现次数加1 )除以 (类C下单词总数 + 训练样本中不重复特征词总数)
		return new BigDecimal(StringUtil.nullToInt(categoryWordsAndCountMap.get(categoryName).get(input)) + 1).divide(
				new BigDecimal(StringUtil.nullToInt(categoryTotalWordsMap.get(categoryName)) + totalWordsNum), BigDecimal.ROUND_CEILING);
	}

	public void buildeModel(String categoryRootPath) throws IOException {
		File categoryDirFile = new File(categoryRootPath);
		// 各类别中各个词出现次数
		for (File file : categoryDirFile.listFiles()) {
			computeWordsAndCountByGivenCategory(file, file.getName());
		}
		// 各类别总词数
		computeCategoryTotalWords();
		// 总词数
		computeTotalWordsNum();
		// 先验概率
		computePriorProbability();
	}

	// 计算先验概率
	public void computePriorProbability() {
		for (Iterator<Entry<String, Integer>> it = categoryTotalWordsMap.entrySet().iterator(); it.hasNext();) {
			Entry<String, Integer> entry = it.next();
			entry.getKey();
			entry.getValue();
			categoryPriorProbability.put(entry.getKey(), entry.getValue() / totalWordsNum);
		}
	}

	public void computeTotalWordsNum() {
		totalWordsNum = (double) totalWordsSet.size();
	}

	public void computeCategoryTotalWords() {
		Set<Entry<String, Map<String, Integer>>> set = categoryWordsAndCountMap.entrySet();
		for (Iterator<Entry<String, Map<String, Integer>>> it = set.iterator(); it.hasNext();) {
			Entry<String, Map<String, Integer>> entry = it.next();
			categoryTotalWordsMap.put(entry.getKey(), entry.getValue().size());
			// 数据汇总
			totalWordsSet.addAll(entry.getValue().keySet());
		}
	}

	public void computeWordsAndCountByGivenCategory(File categoryFile, String categoryName) throws IOException {
		if (categoryFile.isFile()) {
			Map<String, Integer> wordsAndCountMap = categoryWordsAndCountMap.get(categoryName);
			if (wordsAndCountMap == null) {
				wordsAndCountMap = new HashMap<String, Integer>();
				categoryWordsAndCountMap.put(categoryName, wordsAndCountMap);
			}
			System.out.println("加载文档 ：" + categoryFile.getAbsolutePath());
			String words = TextUtil.loadFile(categoryFile, inputFileCharset);
			String[] wordArray = words.split("\t");
			// 处理词
			for (String word : wordArray) {
				if (StringUtil.isNullOrEmpty(word)) {
					continue;
				}
				wordsAndCountMap.put(word, StringUtil.nullToInt(wordsAndCountMap.get(word)) + 1);
			}
			return;
		}
		for (File file : categoryFile.listFiles()) {
			computeWordsAndCountByGivenCategory(file, categoryName);
		}
	}

	public void loadStopWords() throws IOException {
		String[] stopWords = TextUtil.loadFile(DataMiningConfigure.CN_STOP_WORDS_FILE_PATH, inputFileCharset).split("\t");
		for (String stopWord : stopWords) {
			this.stopWordsList.add(stopWord);
		}
	}
}
