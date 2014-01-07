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
package org.spout.api.scheduler;

/**
 * A class to allow non-pulsed threads to synchronize with the pulsed thread system
 */
public interface SnapshotLock {
	/**
	 * Readlocks the stable snapshot.
	 *
	 * This method will prevent server ticks from completing, so any locks should be short
	 *
	 * @param plugin the plugin
	 */
	public void readLock(Object plugin);

	/**
	 * Attempts to readlock the stable snapshot and returns immediately
	 *
	 * This method will prevent server ticks from completing, so any locks should be short
	 *
	 * @param plugin the plugin
	 */
	public boolean readTryLock(Object plugin);

	/**
	 * Releases a previous readlock
	 *
	 * @param plugin the plugin
	 */
	public void readUnlock(Object plugin);

	/**
	 * Gets if the lock is read locked
	 *
	 * @return true if locked
	 */
	public boolean isWriteLocked();
}
