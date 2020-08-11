package model;

public class IO_Request {

	private long resourceStart;
	private long resourceLength;
	private boolean hasError;

	/**
	 * 
	 * @param resourceStart Start resource block ID (in 4kB blocks)
	 * @param resourceLength Length of consecutive blocks (4KB) in one operation
	 */
	public IO_Request(long resourceStart, long resourceLength) {
		this.resourceStart = resourceStart;
		this.resourceLength = resourceLength;
		this.hasError = false;
	}
	
	public long getResourceStart() {
		return resourceStart;
	}

	public long getResourceLength() {
		return resourceLength;
	}
	
	public boolean hasError() {
		return hasError;
	}
}