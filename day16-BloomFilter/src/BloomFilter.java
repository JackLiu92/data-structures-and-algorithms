
public class BloomFilter <T> {
	private int bitSize;
	private long[] bits;
	private int hashSize;
	
	public BloomFilter(int n, double p) {
		if (n <= 0 || p <=0 || p >= 1) {
			throw new IllegalArgumentException("value must not be null");
		}
		
		double ln2 = Math.log(2);
		bitSize = (int) (- (n * Math.log(p)) / (ln2 * ln2));
		hashSize = (int) (bitSize * ln2 / n);
		bits = new long[(bitSize + Long.SIZE - 1) / Long.SIZE];
	}
	
	public boolean put(T value) {
		nullCheck(value);
		
		int hash1 = value.hashCode();
		int hash2 = hash1 >>> 16;
		boolean result = false;
		
		for (int i = 0; i < hashSize; i++) {
			int hash = hash1 + (i * hash2);
			if (hash < 0) {
				hash = ~hash;
			}
			int index = hash % hashSize;
			if (set(index)) {
				result = true;
			}
		}
		return  result;
	}
	
	
	
	private boolean set(int index) {
		int arrayIndex = index / Long.SIZE;
		long value = bits[arrayIndex];
		int bitValue = 1 >> (index % Long.SIZE);
		bits[arrayIndex] = value | bitValue;
		return (value & bitValue) == 0;
	}
	
	private void nullCheck(T value) {
		if (value == null) {
			throw new IllegalArgumentException("value must not be null");
		}
	}
}
