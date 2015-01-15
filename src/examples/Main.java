package examples;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

public class Main {
	// 目标：测试节点命中的情况
	// 测试采用了10个节点，50个replicase,数据量为1000*1000(count)个随机整数
	public static void main(String[] args) {
		// replicase: 虚拟节点的个数
		// nodes: 真正的节点
		// nodeMap: key,节点; value,节点被命中的次数
		int replicase = 10 * 5;
		List<String> nodes = new ArrayList<String>();
		Map<String, Integer> nodeMap = new HashMap<String, Integer>();

		// 初始化10个节点,构建节点环
		for (char i = 'A'; i < 'A' + 10; i++) {
			String key = "" + i;
			nodes.add(key);
			nodeMap.put(key, 0);
		}

		HashFunction hash = new MD5HashFuction();
		ConsistentHash<String> conHash = new ConsistentHash<String>(hash,
				replicase, nodes);

		// count个随机数
		final Random r = new Random(System.currentTimeMillis());
		final int count = 1000 * 1000;
		for (int i = 0; i < count; i++) {
			String node = conHash.get(r.nextInt());
			countNodeUsed(nodeMap, node);
		}

		// 获取每个节点的覆盖情况
		for (Entry<String, Integer> entry : nodeMap.entrySet()) {
			double ratio = (double) entry.getValue() / count;
			System.out.println(entry.getKey() + " Cache bingo :" + ratio * 100
					+ "%");
		}
	}

	private static void countNodeUsed(Map<String, Integer> nodeMap, String node) {
		nodeMap.put(node, nodeMap.get(node) + 1);
	}
}
