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
package com.flowpowered.engine.entity;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.flowpowered.commons.datatable.ManagedHashMap;
import com.flowpowered.commons.datatable.SerializableMap;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.flowpowered.api.component.Component;
import com.flowpowered.api.entity.Entity;
import com.flowpowered.api.entity.EntitySnapshot;
import com.flowpowered.api.geo.discrete.Transform;

public class SpoutEntitySnapshot implements EntitySnapshot {
	private final WeakReference<Entity> entity;
	private final int entityId;
	private final UUID uniqueId;
	private final Transform location;
	private final String worldName;
	private final UUID worldId;
	private final SerializableMap dataMap;
	private final boolean savable;
	private final List<Class<? extends Component>> components;
	private final long time = System.currentTimeMillis();

	public SpoutEntitySnapshot(Entity e) {
		if (e.isRemoved()) {
			throw new IllegalArgumentException("Can not take a snapshot of a removed entity");
		}

		this.entity = new WeakReference<>(e);
		this.entityId = e.getId();
		this.uniqueId = e.getUID();
		//this.location = e.getPhysics().getTransform();
        this.location = null;
		this.worldName = e.getWorld().getName();
		this.worldId = e.getWorld().getUID();
		this.savable = e.isSavable();
		if (e.getData().size() > 0) {
			this.dataMap = e.getData().deepCopy();
		} else {
			this.dataMap = new ManagedHashMap();
		}
		components = new ArrayList<>();
		for (Component c : e.values()) {
			if (c.isDetachable()) {
				this.components.add(c.getClass());
			}
		}
	}

	public SpoutEntitySnapshot(UUID id, Transform t, UUID worldId, byte[] dataMap, List<Class<? extends Component>> types) {
		this.entity = new WeakReference<>(null);
		this.entityId = -1;
		this.uniqueId = id;
		this.location = t;
		this.worldName = null;
		this.worldId = worldId;
		this.savable = true;
		this.dataMap = new ManagedHashMap();
		if (dataMap != null) {
			try {
				this.dataMap.deserialize(dataMap);
			} catch (IOException e) {
				throw new RuntimeException("Unable to deserialize data", e);
			}
		}
		this.components = new ArrayList<>(types);
	}

	@Override
	public Entity getReference() {
		return entity.get();
	}

	@Override
	public final int getId() {
		return entityId;
	}

	@Override
	public final UUID getUID() {
		return uniqueId;
	}

	@Override
	public final Transform getTransform() {
		return location;
	}

	@Override
	public final UUID getWorldUID() {
		return worldId;
	}

	@Override
	public String getWorldName() {
		return worldName;
	}

	@Override
	public final SerializableMap getDataMap() {
		return dataMap;
	}

	@Override
	public boolean isSavable() {
		return savable;
	}

	@Override
	public List<Class<? extends Component>> getComponents() {
		return components;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	@Override
	public long getSnapshotTime() {
		return time;
	}
}
