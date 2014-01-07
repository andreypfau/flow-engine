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

import org.spout.api.geo.cuboid.Region;

/**
 * Represents a task being executed by the scheduler
 */
public interface Task {
	/**
	 * Returns the taskId for the task
	 *
	 * @return Task id number
	 */
	public int getTaskId();

	/**
	 * Returns the Object that owns this task
	 *
	 * @return The Object that owns the task
	 */
	public Object getOwner();

	/**
	 * Returns true if the Task is a sync task
	 *
	 * @return true if the task is run by main thread
	 */
	public boolean isSync();

	/**
	 * Returns true if the Task is alive.  Dead tasks are no longer being scheduled
	 *
	 * @return true if the task is alive
	 */
	public boolean isAlive();

	/**
	 * Returns true if the Task is executing.
	 *
	 * @return true if the task is executing
	 */
	public boolean isExecuting();

	/**
	 * Returns true if the task is a long lived async task
	 *
	 * @return true if the task is a long lived task
	 */
	public boolean isLongLived();

	/**
	 * Cancels current Task
	 */
	public void cancel();
}
