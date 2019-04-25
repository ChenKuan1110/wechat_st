package com.dao;

public class BufferManager {
	Mutex mutex = new Mutex();

	public BufferManager(int bufSize) {
		m_Buffer = new byte[bufSize];
		m_MaxBufferSize = bufSize;
		m_rPtr = 0;
		m_wPtr = 0;
		m_NowSize = 0;
	}
	private byte[] m_Buffer;
	private int m_MaxBufferSize;
	private int m_rPtr;
	private int m_wPtr;
	private int m_NowSize;
	/**
	 * 将读取到的数据放入buffer
	 * @param buf 存放数据的buffer
	 * @param len 存放数据的长度
	 * @return
	 */
	public boolean PushBuffer(byte[] buf, int len) {
		boolean retState = false;
		try {
			mutex.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if ((m_MaxBufferSize - m_NowSize) >= len) {
			for (int i = 0; i < len; i++) {
				m_Buffer[m_wPtr] = buf[i];
				m_NowSize++;
				m_wPtr++;
				m_wPtr %= m_MaxBufferSize;
			}
			retState = true;
		}
		mutex.release();
		return retState;
	}
	/**
	 * 获取指定长度的buffer数据
	 * @param bufSize 需要取出的数据长度
	 * @return
	 */
	public byte[] GetBuffer(int bufSize) {
		byte[] buf = null;
//		boolean retState = false;
		try {
			mutex.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if(m_NowSize >= bufSize) {
			buf = new byte[bufSize];

			for (int i = 0; i < bufSize; i++) {
				buf[i] = m_Buffer[m_rPtr];
				m_NowSize--;
				m_rPtr++;
				m_rPtr %= m_MaxBufferSize;
			}
		}
		mutex.release();
		return buf;
	}
	/**
	 * 将buffer中存放的数据一次全部取出
	 * @return
	 */
	public byte[] GetBuffer() {
		byte[] buf = null;
//		boolean retState = false;
		try {
			mutex.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (m_NowSize > 0) {
			buf = new byte[m_NowSize];

			int TmpNowSize = m_NowSize;
			for (int i = 0; i < TmpNowSize; i++) {
				buf[i] = m_Buffer[m_rPtr];
				m_NowSize--;
				m_rPtr++;
				m_rPtr %= m_MaxBufferSize;
			}
		}
		mutex.release();
		return buf;
	}
	/**
	 * 找到数据头
	 * 发送的每一段完整数据都是以0xa5 0xa5打头
	 */
	private boolean m_FindOne = false;
	public boolean FindHead() {
		boolean retState = false;
		try {
			mutex.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (m_NowSize >= 2) {
			int TmpPtr = m_rPtr;
			int Tmp;
			for (int i = 0; i < m_NowSize; i++) {
				Tmp = m_Buffer[TmpPtr] & 0xff;
				if (Tmp == 0xa5) {
					if (m_FindOne) {
						retState = true;
						m_FindOne = false;

						m_NowSize -= (int)i - 1;
						m_rPtr += (int)i - 1;
						m_rPtr %= m_MaxBufferSize;

						break;
					}
					else {
						m_FindOne = true;
					}
				}
				else {
					m_FindOne = false;
				}
				TmpPtr++;
				TmpPtr %= m_MaxBufferSize;
			}
		}
		mutex.release();
		return retState;
	}

	public int GetSize() {
		try {
			mutex.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		int ret;
		ret = m_MaxBufferSize - m_NowSize;

		mutex.release();

		return ret;
	}
}
