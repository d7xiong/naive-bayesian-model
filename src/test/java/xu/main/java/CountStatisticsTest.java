package xu.main.java;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import xu.main.java.Statistics.CountStatistics;
import xu.main.java.configure.DataMiningConfigure;

public class CountStatisticsTest {
	public static void main(String[] args) throws IOException {

		CountStatistics countStatistics = new CountStatistics("gb2312");
		countStatistics.buildeModel(DataMiningConfigure.CN_PROCESSED_OUTPUT_FILE_ROOT_PATH);

		String categoryName = "";
		BigDecimal bigDecimal = new BigDecimal(1);

		String input = "居民：两楼接吻皆因“楼歪歪”最先发现楼“歪”了的，是17号楼1503室的住户秦丽君。29日一早，秦女士在自家阳台上晾晒衣物，无意中发现大楼楼顶一角似乎已经碰到了隔壁紧挨着的18号楼。秦女士的丈夫获悉后，随即来到楼顶查看，而眼前的情况让他大为震惊：17号楼楼顶的一角已经结结实实地贴上了18号楼，两栋楼屋顶的墙皮和混凝土块均有多处剥落。秦女士告诉东方网记者，看到楼顶的情况，她的第一反应就是楼“歪”了：“我们这幢楼就好比一个病人，站都站不住，要靠在隔壁18号身上才能站稳。”而另一个细节，则更让秦女士确定了17号楼确实有问题：“去年年初，我们家刚装修完没过多久，墙壁就开始出现大大小小的裂缝。当时我认为是装潢队的施工质量问题，但对方却一口咬死，说是房屋质量导致了墙面开裂。现在想想，他们说的可能真的不是没有道理。”认为可能有更大的危险发生，秦女士随即挨家挨户地通知了楼里的邻居。一时间，不安的气氛笼罩了整栋大楼。1504室的住户周阿姨说：“知道这件事后我觉都睡不好，再下去都不敢住了！”物业：超出职能范围爱莫能助心圆西苑小区位于川沙镇华夏二路。据东方网记者了解，该小区于2010年5月开工建设，2012年11月交付使用，属上海迪士尼项目配套动拆迁安置小区。“亲吻”在一起的17号和18号楼均为15层，目前住户已经基本全部迁入。眼看两幢楼“亲”在一起，居民们希望能够“讨个说法”，确认大楼是否安全，于是他们首先想到了小区物业。然而周六、周日两天，纵使居民们几次三番地打去电话，负责小区管理的新川物业公司却均以休息为由，未对此事进行处理。据居民称，直至21日上午，物业方才会同开发商和当地镇政府的有关人员实地查看情况，但并未对居民进行任何反馈或说明任何情况便匆匆离去。21日下午，东方网记者随小区居民来到了新川物业的办公地点。面对大批情绪激动的居民，小区物业经理唐青表示，自己是20日深夜才获悉此事的，然而目前事态已经超出了自己的职能范围，自己作为经理也“没有办法”。开发商：地面沉降招来“激情一吻”虽然部分居民已经因为害怕而不敢回家，但小区开发商心圆房地产开发有限公司方面则认为，两幢居民楼的“激情一吻”却属正常。心圆公司相关负责人陈彤在接受东方网记者采访时表示，17号和18号楼的建设为规避道路红线，在设计上采用了折形布局，因此两楼南面的间距原本就仅有11厘米。同时，两幢大楼的楼顶都设计了装饰用的“女儿墙”，外表有一定突起，因此楼顶几乎是紧贴在一起的。房屋建成后，由于自然沉降，才出现了“接吻”的情况。陈彤强调，经初步判断，17号楼并不存在所谓“歪”了的情况；同时，出现开裂剥落的楼顶“女儿墙”属大楼外装饰面，并不会对房屋结构产生影响，大楼安全性“绝对没问题”。但陈彤也承认，两楼“接吻”确系设计时未预料到的问题，但未来仅需简单施工即可解决。最新进展：权威机构将进行实地检测“接吻楼”是否存在安全隐患，断然不能只听信开发商一家之言。21日下午，川沙镇镇政府紧急委派了浦东新区内的一家房屋鉴定机构对心圆西苑小区17号、18号楼的沉降水平、垂直度等数据进行了实测。截至发稿时，详细的鉴定报告尚未出具。但据心圆公司方面透露，实地检测的数据“均在规范内”。";
		Map<String, String> matchResult = countStatistics.matchCategoryProbability(input);
		for (Iterator<Entry<String, String>> it = matchResult.entrySet().iterator(); it.hasNext();) {
			Entry<String, String> entry = it.next();
			System.out.print(entry.getKey());
			System.out.print("\t");
			System.out.println(entry.getValue());
			BigDecimal tempDec = new BigDecimal(entry.getValue());
			if (bigDecimal.compareTo(tempDec) == -1) {
				bigDecimal = tempDec;
				categoryName = entry.getKey();
			}
		}
		System.out.println("类型：" + categoryName);
		System.out.println(" done ...");
	}
}
