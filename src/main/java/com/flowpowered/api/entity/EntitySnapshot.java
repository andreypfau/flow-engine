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
package com.flowpowered.api.entity;

import java.util.List;
import java.util.UUID;

import com.flowpowered.commons.datatable.SerializableMap;

import com.flowpowered.api.component.Component;
import com.flowpowered.api.geo.discrete.Transform;
import com.flowpowered.api.scheduler.Snapshot;

/**
 * Represents a snapshot of an entity state at a specific UTC timestamp, with immutable values
 */
public interface EntitySnapshot extends Snapshot<Entity> {

	/**
	 * Gets the id of the entity. <p> Entity ids' may become invalid if the server has stopped and started. They do not persist across server instances. For persistent ids, use {@link #getUID()}. </p>
	 *
	 * @return id
	 */
	public int getId();

	/**
	 * Gets the UID for the entity. <p> This id is persistent across server instances, unique to this entity </p>
	 *
	 * @return uid
	 */
	public UUID getUID();

	/**
	 * Gets the transform for the entity. <p> Note: if the world that the entity was in has been unloaded, the world in the transform will be null. </p>
	 *
	 * @return transform
	 */
	public Transform getTransform();

	/**
	 * Gets the UUID of the world that the entity was in at the time of this snapshot
	 *
	 * @return uid
	 */
	public UUID getWorldUID();

	/**
	 * Gets the name of the world that the entity was in at the time of this snapshot
	 *
	 * @return world name
	 */
	public String getWorldName();

	/**
	 * Gets a copy of the data map for the entity, created at the time of this snapshot
	 *
	 * @return data map
	 */
	public SerializableMap getDataMap();

	/**
	 * Gets the savable flag for the entity at the time of the snapshot
	 *
	 * @return savable
	 */
	public boolean isSavable();

	/**
	 * Gets a list of the classes of components attached to this entity
	 *
	 * @return entity
	 */
	public List<Class<? extends Component>> getComponents();
}
