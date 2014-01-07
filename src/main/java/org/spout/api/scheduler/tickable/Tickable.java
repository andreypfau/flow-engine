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
package org.spout.api.scheduler.tickable;

public interface Tickable {
	/**
	 * Called each simulation tick.<br/> Override this to perform logic upon ticking.<br/> 1       tick  = 1/20 second<br/> 20      ticks = 1 second<br/> 1200    ticks = 1 minute<br/> 72000   ticks = 1
	 * hour<br/> 1728000 ticks = 1 day
	 *
	 * @param dt time since the last tick in seconds
	 */
	public void onTick(float dt);

	/**
	 * Whether or not this tickable can perform a tick
	 *
	 * @return true if it can tick, false if not
	 */
	public boolean canTick();

	/**
	 * Ticks the Tickable.<br/> Call this to make the Tickable tick.<br/>
	 *
	 * Standard implementation is if(canTick()) { onTick(dt); }<br/>
	 *
	 * 1       tick  = 1/20 second<br/> 20      ticks = 1 second<br/> 1200    ticks = 1 minute<br/> 72000   ticks = 1 hour<br/> 1728000 ticks = 1 day
	 *
	 * @param dt time since the last tick in seconds
	 */
	public void tick(float dt);
}
