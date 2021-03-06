package com.dao;

import com.sun.corba.se.impl.orbutil.concurrent.Sync;
/**
 * 互斥锁
 */
public class Mutex implements Sync {
	/**
	 *  The lock status
	 */
	protected boolean inuse_ = false;

	public void acquire() throws InterruptedException {
		if (Thread.interrupted()) throw new InterruptedException();//(1)
		synchronized(this) {
			try {
				while (inuse_) wait();
				inuse_ = true;
			}
			catch (InterruptedException ex) {
				//(2)
				notify();
				throw ex;
			}
		}
	}
	public synchronized void release() {
		inuse_ = false;
		notify();
	}

	public boolean attempt(long msecs) throws InterruptedException {
		if (Thread.interrupted()) throw new InterruptedException();
		synchronized(this) {
			if (!inuse_) {
				inuse_ = true;
				return true;
			}
			else if (msecs <= 0)
				return false;
			else {
					long waitTime = msecs;
					long start = System.currentTimeMillis();
					try {
						for (;;) {
							wait(waitTime);
							if (!inuse_) {
								inuse_ = true;
								return true;
							}
							else {
									waitTime = msecs - (System.currentTimeMillis() - start);
									if (waitTime <= 0) // (3)
										 return false;
							}
						}
					} catch (InterruptedException ex) {
						notify();
						throw ex;
					}
				}
		}
	}
}
