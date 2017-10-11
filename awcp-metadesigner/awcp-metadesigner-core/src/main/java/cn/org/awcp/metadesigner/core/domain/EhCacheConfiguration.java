package cn.org.awcp.metadesigner.core.domain;

public class EhCacheConfiguration {

	//缓存属性名称
	private String name;

	//缓存中可以存放元素的最大数量
	private int maxElementsInMemory;

	//内存溢出是否写入磁盘
	private boolean overflowToDisk;

	//是否永驻内存
	private boolean eternal;

	//访问缓存的最大间隔时间
	private long timeToLiveSeconds;

	//缓存的生存时间
	private long timeToIdleSeconds;

	private EhCacheConfiguration() {

	}

	public String name() {
		return name;
	}

	public int maxElementsInMemory() {
		return maxElementsInMemory;
	}

	public boolean overflowToDisk() {
		return overflowToDisk;
	}

	public boolean eternal() {
		return eternal;
	}

	public long timeToLiveSeconds() {
		return timeToLiveSeconds;
	}

	public long timeToIdleSeconds() {
		return timeToIdleSeconds;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private EhCacheConfiguration configuration;
		
		public Builder() {
			configuration = new EhCacheConfiguration();
		}
		
		public Builder name(String name) {
			configuration.name = name;
			return this;
		}
		
		public Builder maxElementsInMemory(int maxElementsInMemory) {
			configuration.maxElementsInMemory = maxElementsInMemory;
			return this;
		}

		public Builder overflowToDisk(boolean overflowToDisk) {
			configuration.overflowToDisk = overflowToDisk;
			return this;
		}

		public Builder eternal(boolean eternal) {
			configuration.eternal = eternal;
			return this;
		}

		public Builder timeToLiveSeconds(long timeToLiveSeconds) {
			configuration.timeToLiveSeconds = timeToLiveSeconds;
			return this;
		}

		public Builder timeToIdleSeconds(long timeToIdleSeconds) {
			configuration.timeToIdleSeconds = timeToIdleSeconds;
			return this;
		}

		public EhCacheConfiguration build() {
			if (configuration.name == null || configuration.name.isEmpty()) {
				throw new IllegalStateException("Cache name is empty!");
			}
			return configuration;
		}
	}

}
