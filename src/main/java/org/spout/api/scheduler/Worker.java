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
 * Represents a worker thread for the scheduler. This gives information about the Thread object for the task, owner of the task and the taskId.
 *
 * Workers are used to execute async tasks.
 */
public interface Worker {
	/**
	 * Returns the taskId for the task being executed by this worker
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
	 * Attempts to cancel the task.  This will trigger an interrupt for async tasks that are in progress.
	 */
	public void cancel();

	/**
	 * Gets the task associated with this worker
	 *
	 * @return the task
	 */
	public Task getTask();
}
