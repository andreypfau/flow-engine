/*
 * This file is part of Spout.
 *
 * Copyright (c) 2011 Spout LLC <http://www.spout.org/>
 * Spout is licensed under the Spout License Version 1.
 *
 * Spout is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option)
 * any later version.
 *
 * In addition, 180 days after any changes are published, you can use the
 * software, incorporating those changes, under the terms of the MIT license,
 * as described in the Spout License Version 1.
 *
 * Spout is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for
 * more details.
 *
 * You should have received a copy of the GNU Lesser General Public License,
 * the MIT license and the Spout License Version 1 along with this program.
 * If not, see <http://www.gnu.org/licenses/> for the GNU Lesser General Public
 * License and see <http://spout.in/licensev1> for the full license, including
 * the MIT license.
 */
package com.flowpowered.engine.util.thread;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

import com.flowpowered.api.Spout;

public class AsyncExecutorUtils {
	private static final String LINE = "------------------------------";

	/**
	 * Logs all threads, the thread details, and active stack traces
	 */
	public static void dumpAllStacks() {
		Logger log = Spout.getLogger();
		Map<Thread, StackTraceElement[]> traces = Thread.getAllStackTraces();
		Iterator<Entry<Thread, StackTraceElement[]>> i = traces.entrySet().iterator();
		while (i.hasNext()) {
			Entry<Thread, StackTraceElement[]> entry = i.next();
			Thread thread = entry.getKey();
			log.info(LINE);

			log.info("Current Thread: " + thread.getName());
			log.info("    PID: " + thread.getId() + " | Alive: " + thread.isAlive() + " | State: " + thread.getState());
			log.info("    Stack:");
			StackTraceElement[] stack = entry.getValue();
			for (int line = 0; line < stack.length; line++) {
				log.info("        " + stack[line].toString());
			}
		}
		log.info(LINE);
	}

	/**
	 * Scans for deadlocked threads
	 */
	public static void checkForDeadlocks() {
		Logger log = Spout.getLogger();
		ThreadMXBean tmx = ManagementFactory.getThreadMXBean();
		long[] ids = tmx.findDeadlockedThreads();
		if (ids != null) {
			log.info("Checking for deadlocks");
			ThreadInfo[] infos = tmx.getThreadInfo(ids, true, true);
			log.info("The following threads are deadlocked:");
			for (ThreadInfo ti : infos) {
				log.info(ti.toString());
			}
		}
	}

	/**
	 * Dumps the stack for the given Thread
	 *
	 * @param t the thread
	 */
	public static void dumpStackTrace(Thread t) {
		StackTraceElement[] stackTrace = t.getStackTrace();
		Spout.getLogger().info("Stack trace for Thread " + t.getName());
		for (StackTraceElement e : stackTrace) {
			Spout.getLogger().info("\tat " + e);
		}
	}
}
