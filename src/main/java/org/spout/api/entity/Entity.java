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
package org.spout.api.entity;

import java.util.UUID;

import com.flowpowered.commons.datatable.ManagedMap;

import org.spout.api.Engine;
import org.spout.api.component.ComponentOwner;
import org.spout.api.geo.WorldSource;
import org.spout.api.geo.cuboid.Chunk;
import org.spout.api.geo.cuboid.Region;
import org.spout.api.scheduler.tickable.Tickable;

/**
 * Represents an entity, which may or may not be spawned into the world.
 */
public interface Entity extends Tickable, WorldSource, ComponentOwner {
	/**
	 * Gets the current ID of this entity within the current game session
	 *
	 * @return The entities' id.
	 */
	public int getId();

	/**
	 * Gets the entity's persistent unique id. <p> Can be used to look up the entity and persists between starts.
	 *
	 * @return persistent {@link UUID}
	 */
	public UUID getUID();

	/**
	 * Gets the {@link Engine} that spawned and is managing this entity
	 *
	 * @return {@link Engine}
	 */
	public Engine getEngine();

	/**
	 * Removes the entity. This takes effect at the next snapshot.
	 */
	public void remove();

	/**
	 * True if the entity is removed.
	 *
	 * @return removed
	 */
	public boolean isRemoved();

	/**
	 * Sets whether or not the entity should be saved.<br/>
	 *
	 * @param savable True if the entity should be saved, false if not
	 */
	public void setSavable(boolean savable);

	/**
	 * Returns true if this entity should be saved.
	 *
	 * @return savable
	 */
	public boolean isSavable();

	/**
	 * Gets the {@link Chunk} this entity resides in, or null if removed.
	 *
	 * @return {@link Chunk} the entity is in, or null if removed.
	 */
	public Chunk getChunk();

	/**
	 * Gets the region the entity is associated and managed with, or null if removed.
	 *
	 * @return {@link Region} the entity is in.
	 */
	public Region getRegion();

	/**
	 * Creates an immutable snapshot of the entity state at the time the method is called
	 *
	 * @return immutable snapshot
	 */
	public EntitySnapshot snapshot();

    public Physics getPhysics();

	/**
	 * Gets the {@link ManagedMap} which an Entity always has.
	 *
	 * @return {@link ManagedMap}
	 */
	public ManagedMap getData();
}
