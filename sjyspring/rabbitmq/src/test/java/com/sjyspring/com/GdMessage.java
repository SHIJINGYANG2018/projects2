package com.sjyspring.com;

/**
 * 将底层传输协议 最终封装为国都扩展CMPP2.0扩展协议 存储到Redis相应对列 存储格式：byte[]=header+body
 * 
 * 
 */
public abstract class GdMessage implements Cloneable {

	public abstract void setBuffer(byte[] buf);

	public abstract byte[] getBuffer();

	public abstract String toString();
}
