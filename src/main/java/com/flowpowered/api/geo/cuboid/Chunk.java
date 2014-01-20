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
package com.flowpowered.api.geo.cuboid;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.Future;

import com.flowpowered.commons.BitSize;

import com.flowpowered.api.entity.Entity;
import com.flowpowered.api.entity.Player;
import com.flowpowered.api.geo.AreaBlockAccess;
import com.flowpowered.api.geo.LoadOption;
import com.flowpowered.api.geo.World;
import com.flowpowered.api.geo.cuboid.ChunkSnapshot.EntityType;
import com.flowpowered.api.geo.cuboid.ChunkSnapshot.ExtraData;
import com.flowpowered.api.geo.cuboid.ChunkSnapshot.SnapshotType;
import com.flowpowered.api.geo.discrete.Point;
import com.flowpowered.api.material.block.BlockFace;
import com.flowpowered.math.vector.Vector3f;

/**
 * Represents a cube containing 16x16x16 Blocks
 */
public abstract class Chunk extends Cube implements AreaBlockAccess {
	/**
	 * Stores the size of the amount of blocks in this Chunk
	 */
	public static final BitSize BLOCKS = new BitSize(4);
	/**
	 * Mask to convert a block integer coordinate into the point base
	 */
	public final static int POINT_BASE_MASK = -BLOCKS.SIZE;
	private final int blockX;
	private final int blockY;
	private final int blockZ;

	public Chunk(World world, int x, int y, int z) {
		super(new Point(world, x, y, z), BLOCKS.SIZE);
		blockX = super.getX() << BLOCKS.BITS;
		blockY = super.getY() << BLOCKS.BITS;
		blockZ = super.getZ() << BLOCKS.BITS;
	}

	/**
	 * Performs the necessary tasks to unload this chunk from the world.
	 *
	 * @param save whether the chunk data should be saved.
	 */
	public abstract void unload(boolean save);

	/**
	 * Performs the necessary tasks to save this chunk.
	 */
	public abstract void save();

	/**
	 * Gets a snapshot of the data for the chunk. <br/><br/> This process may result in tearing if called during potential updates <br/><br/> This is the same as calling getSnapshot(BOTH, WEAK_ENTITIES,
	 * NO_EXTRA_DATA)
	 *
	 * @return the snapshot
	 */
	public abstract ChunkSnapshot getSnapshot();

	/**
	 * Gets a snapshot of the data for the chunk. <br/><br/> This process may result in tearing if called during potential updates <br/><br/>
	 *
	 * @param type the type of basic snapshot information to be stored
	 * @param entities whether to include entity data in the snapshot
	 * @param data the extra data, if any, to be stored
	 * @return the snapshot
	 */
	public abstract ChunkSnapshot getSnapshot(SnapshotType type, EntityType entities, ExtraData data);

	/**
	 * Fills the given block container with the block data for this chunk
	 */
	public abstract void fillBlockContainer(BlockContainer container);

	/**
	 * Fills the given block component container with the block components for this chunk
	 */
	public abstract void fillBlockComponentContainer(BlockComponentContainer container);

	/**
	 * Gets a snapshot of the data for the chunk.  The snapshot will be taken at a stable moment in the tick. <br/><br/> This is the same as calling getFutureSnapshot(BOTH, WEAK_ENTITIES, NO_EXTRA_DATA)
	 *
	 * @return the snapshot
	 */
	public abstract Future<ChunkSnapshot> getFutureSnapshot();

	/**
	 * Gets a snapshot of the data for the chunk.  The snapshot will be taken at a stable moment in the tick.
	 *
	 * @param type the type of basic snapshot information to be stored
	 * @param entities whether to include entity data in the snapshot
	 * @param data the extra data, if any, to be stored
	 * @return the snapshot
	 */
	public abstract Future<ChunkSnapshot> getFutureSnapshot(SnapshotType type, EntityType entities, ExtraData data);

	/**
	 * Refresh the distance between a player and the chunk, and adds the player as an observer if not previously observing.
	 *
	 * @param player the player
	 * @return false if the player was already observing the chunk
	 */
	public abstract boolean refreshObserver(Entity player);

	/**
	 * De-register a player as observing the chunk.
	 *
	 * @param player the player
	 * @return true if the player was observing the chunk
	 */
	public abstract boolean removeObserver(Entity player);

	/**
	 * Gets the region that this chunk is located in
	 *
	 * @return region
	 */
	public abstract Region getRegion();

	/**
	 * Tests if the chunk is currently loaded
	 *
	 * Chunks may be unloaded at the end of each tick
	 */
	public abstract boolean isLoaded();

	/**
	 * Populates the chunk with all the Populators attached to the WorldGenerator of its world.
	 */
	public abstract boolean populate();

	/**
	 * Populates the chunk with all the Populators attached to the WorldGenerator of its world.
	 *
	 * @param force forces to populate the chunk even if it already has been populated.
	 */
	public abstract boolean populate(boolean force);

	/**
	 * Populates the chunk with all the Populators attached to the WorldGenerator of its world.<br> <br> Warning: populating with force observer should not be called from within populators as it could
	 * lead to a population cascade
	 *
	 * @param sync queues the population to occur at a later time
	 * @param observe forces the chunk to be observed for population
	 */
	public abstract void populate(boolean sync, boolean observe);

	/**
	 * Populates the chunk with all the Populators attached to the WorldGenerator of its world.<br> <br> Warning: populating with force observer should not be called from within populators as it could
	 * lead to a population cascade
	 *
	 * @param sync queues the population to occur at a later time
	 * @param observe forces the chunk to be observed for population
	 * @param priority adds the chunk to the high priority queue
	 */
	public abstract void populate(boolean sync, boolean observe, boolean priority);

	/**
	 * Gets if this chunk already has been populated.
	 *
	 * @return if the chunk is populated.
	 */
	public abstract boolean isPopulated();

	/**
	 * Gets the entities in the chunk at the last snapshot
	 *
	 * @return the entities
	 */
	public abstract List<Entity> getEntities();

	/**
	 * Gets the entities currently in the chunk
	 *
	 * @return the entities
	 */
	public abstract List<Entity> getLiveEntities();

	/**
	 * Gets the number of observers viewing this chunk. If the number of observing entities falls to zero, this chunk may be reaped at any time.
	 *
	 * @return number of observers
	 */
	public abstract int getNumObservers();

	/**
	 * Gets the observing players of this chunk (done based on the player's view distance).
	 *
	 * @return List containing the observing players
	 */
	public abstract Set<? extends Player> getObservingPlayers();

	/**
	 * Gets the observers of this chunk
	 *
	 * @return Set containing the observing players
	 */
	public abstract Set<? extends Entity> getObservers();

	@Override
	public boolean containsBlock(int x, int y, int z) {
		return x >> BLOCKS.BITS == this.getX() && y >> BLOCKS.BITS == this.getY() && z >> BLOCKS.BITS == this.getZ();
	}

	/**
	 * Gets the x-coordinate of this chunk as a Block coordinate
	 *
	 * @return the x-coordinate of the first block in this chunk
	 */
	public int getBlockX() {
		return blockX;
	}

	/**
	 * Gets the y-coordinate of this chunk as a Block coordinate
	 *
	 * @return the y-coordinate of the first block in this chunk
	 */
	public int getBlockY() {
		return blockY;
	}

	/**
	 * Gets the z-coordinate of this chunk as a Block coordinate
	 *
	 * @return the z-coordinate of the first block in this chunk
	 */
	public int getBlockZ() {
		return blockZ;
	}

	/**
	 * Gets the Block x-coordinate in the world
	 *
	 * @param x-coordinate within this Chunk
	 * @return x-coordinate within the World
	 */
	public int getBlockX(int x) {
		return this.blockX + (x & BLOCKS.MASK);
	}

	/**
	 * Gets the Block x-coordinate in the world
	 *
	 * @param y y-coordinate within this Chunk
	 * @return y-coordinate within the World
	 */
	public int getBlockY(int y) {
		return this.blockY + (y & BLOCKS.MASK);
	}

	/**
	 * Gets the Block x-coordinate in the world
	 *
	 * @param z z-coordinate within this Chunk
	 * @return z-coordinate within the World
	 */
	public int getBlockZ(int z) {
		return this.blockZ + (z & BLOCKS.MASK);
	}

	/**
	 * Gets a random Block x-coordinate using a Random
	 *
	 * @param random to use
	 * @return x-coordinate within the World in this Chunk
	 */
	public int getBlockX(Random random) {
		return this.blockX + random.nextInt(BLOCKS.SIZE);
	}

	/**
	 * Gets a random Block y-coordinate using a Random
	 *
	 * @param random to use
	 * @return y-coordinate within the World in this Chunk
	 */
	public int getBlockY(Random random) {
		return this.blockY + random.nextInt(BLOCKS.SIZE);
	}

	/**
	 * Gets a random Block z-coordinate using a Random
	 *
	 * @param random to use
	 * @return z-coordinate within the World in this Chunk
	 */
	public int getBlockZ(Random random) {
		return this.blockZ + random.nextInt(BLOCKS.SIZE);
	}

    /**
     * Gets a chunk relative to this chunk
     *
     * @param x
     * @param y
     * @param z
     * @param opt
     * @return The Chunk, or null if not loaded and load is False
     */
    public Chunk getRelative(int x, int y, int z, LoadOption opt) {
        // We check to see if the chunk is in this chunk's region first, to avoid a map lookup for the other region
        final int otherChunkX = this.getX() + x;
        final int otherChunkY = this.getY() + y;
        final int otherChunkZ = this.getZ() + z;
        final int regionX = getRegion().getX();
        final int regionY = getRegion().getY();
        final int regionZ = getRegion().getZ();
        final int otherRegionX = otherChunkX >> Region.CHUNKS.BITS;
        final int otherRegionY = otherChunkY >> Region.CHUNKS.BITS;
        final int otherRegionZ = otherChunkZ >> Region.CHUNKS.BITS;
        if (regionX == otherRegionX && regionZ == otherRegionZ && regionY == otherRegionY) {
            // Get the chunk from the current region
            return getRegion().getChunk(otherChunkX - otherRegionX, otherChunkY - otherRegionY, otherChunkZ - otherRegionZ, opt);
        }
        return this.getWorld().getChunk(otherChunkX, otherChunkY, otherChunkZ, opt);
    }

	/**
	 * Gets a chunk relative to this chunk
	 *
	 * @param offset of the chunk relative to this chunk
	 * @param opt True to load the chunk if it is not yet loaded
	 * @return The Chunk, or null if not loaded and load is False
	 */
	public Chunk getRelative(Vector3f offset, LoadOption opt) {
		return this.getWorld().getChunk(this.getX() + (int) offset.getX(), this.getY() + (int) offset.getY(), this.getZ() + (int) offset.getZ(), opt);
	}

	/**
	 * Gets a chunk relative to this chunk
	 *
	 * @param offset of the chunk relative to this chunk
	 * @param opt True to load the chunk if it is not yet loaded
	 * @return The Chunk, or null if not loaded and load is False
	 */
	public Chunk getRelative(BlockFace offset, LoadOption opt) {
		return this.getRelative(offset.getOffset(), opt);
	}

	/**
	 * Gets the generation index for this chunk.  Only chunks generated as part of the same bulk initialize have the same index.
	 *
	 * @return a unique generation id, or -1 if the chunk was loaded from disk
	 */
	public abstract int getGenerationIndex();

	/**
	 * Converts a point in such a way that it points to the first block (the base block) of the chunk<br> This is similar to performing the following operation on the x, y and z coordinate:<br> - Convert
	 * to the chunk coordinate<br> - Multiply by chunk size
	 */
	public static Point pointToBase(Point p) {
		return new Point(p.getWorld(), (int) p.getX() & POINT_BASE_MASK, (int) p.getY() & POINT_BASE_MASK, (int) p.getZ() & POINT_BASE_MASK);
	}
}