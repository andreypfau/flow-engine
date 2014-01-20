/*
 * This file is part of Flow Engine, licensed under the MIT License (MIT).
 *
 * Copyright (c) 2013 Spout LLC <http://www.spout.org/>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.flowpowered.engine.geo.chunk;

import com.flowpowered.api.geo.cuboid.ChunkSnapshot;
import com.flowpowered.api.geo.cuboid.ChunkSnapshot.EntityType;
import com.flowpowered.api.geo.cuboid.ChunkSnapshot.ExtraData;
import com.flowpowered.api.geo.cuboid.ChunkSnapshot.SnapshotType;
import com.flowpowered.commons.future.SimpleFuture;

public class FlowChunkSnapshotFuture extends SimpleFuture<ChunkSnapshot> implements Runnable {
	private final FlowChunk chunk;
	private final SnapshotType type;
	private final EntityType entities;
	private final ExtraData data;

	public FlowChunkSnapshotFuture(FlowChunk chunk, SnapshotType type, EntityType entities, ExtraData data) {
		this.chunk = chunk;
		this.type = type;
		this.entities = entities;
		this.data = data;
	}

	@Override
	public void run() {
		try {
			ChunkSnapshot snapshot = chunk.getSnapshot(type, entities, data);
			super.setResult(snapshot);
		} catch (Throwable t) {
			super.setThrowable(t);
		}
	}
}