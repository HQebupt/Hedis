package examples;
/**
 * @author Tom White
 * completed by @HuangQiang
 */
import java.math.BigInteger;
import java.util.Collection;
import java.util.SortedMap;
import java.util.TreeMap;

public class ConsistentHash<T> {

	private final HashFunction hashFunction;
	private final int numberOfReplicas;
	private final SortedMap<BigInteger, T> circle = new TreeMap<BigInteger, T>();

	public ConsistentHash(HashFunction hashFunction, int numberOfReplicas,
			Collection<T> nodes) {
		this.hashFunction = hashFunction;
		this.numberOfReplicas = numberOfReplicas;

		for (T node : nodes) {
			add(node);
		}
	}

	public void add(T node) {
		for (int i = 0; i < numberOfReplicas; i++) {
			circle.put(hashFunction.hash(node.toString() + i), node);
		}
	}

	public void remove(T node) {
		for (int i = 0; i < numberOfReplicas; i++) {
			circle.remove(hashFunction.hash(node.toString() + i));
		}
	}

	// To find a node for an object (the get method), the hash value of the
	// object is used to look in the map. Most of the time there will not be a
	// node stored at this hash value (since the hash value space is typically
	// much larger than the number of nodes, even with replicas), so the next
	// node is found by looking for the first key in the tail map. If the tail
	// map is empty then we wrap around the circle by getting the first key in
	// the circle.
	public T get(Object key) {
		if (circle.isEmpty()) {
			return null;
		}
		BigInteger hash = hashFunction.hash(key.toString());
		if (!circle.containsKey(hash)) {
			SortedMap<BigInteger, T> tailMap = circle.tailMap(hash);
			hash = tailMap.isEmpty() ? circle.firstKey() : tailMap.firstKey();
		}
		return circle.get(hash);
	}
	
	public int size() {
		return circle.size();
	}

}