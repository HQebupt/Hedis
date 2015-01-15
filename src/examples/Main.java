package examples;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

public class Main {
	// Ŀ�꣺���Խڵ����е����
	// ���Բ�����10���ڵ㣬50��replicase,������Ϊ1000*1000(count)���������
	public static void main(String[] args) {
		// replicase: ����ڵ�ĸ���
		// nodes: �����Ľڵ�
		// nodeMap: key,�ڵ�; value,�ڵ㱻���еĴ���
		int replicase = 10 * 5;
		List<String> nodes = new ArrayList<String>();
		Map<String, Integer> nodeMap = new HashMap<String, Integer>();

		// ��ʼ��10���ڵ�,�����ڵ㻷
		for (char i = 'A'; i < 'A' + 10; i++) {
			String key = "" + i;
			nodes.add(key);
			nodeMap.put(key, 0);
		}

		HashFunction hash = new MD5HashFuction();
		ConsistentHash<String> conHash = new ConsistentHash<String>(hash,
				replicase, nodes);

		// count�������
		final Random r = new Random(System.currentTimeMillis());
		final int count = 1000 * 1000;
		for (int i = 0; i < count; i++) {
			String node = conHash.get(r.nextInt());
			countNodeUsed(nodeMap, node);
		}

		// ��ȡÿ���ڵ�ĸ������
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