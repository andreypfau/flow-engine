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
package org.spout.api.material.block;

import java.io.Serializable;

import com.flowpowered.commons.bit.ByteBitMask;
import gnu.trove.map.hash.TIntObjectHashMap;

import org.spout.math.imaginary.Quaternionf;
import org.spout.math.vector.Vector3f;
import org.spout.math.vector.Vector3i;

/**
 * Indicates the facing of a Block
 */
public enum BlockFace implements ByteBitMask, Serializable {
	TOP(0x1, 0, 1, 0, Quaternionf.fromAngleDegAxis(-90, 1, 0, 0)),
	BOTTOM(0x2, 0, -1, 0, Quaternionf.fromAngleDegAxis(90, 1, 0, 0), TOP),
	NORTH(0x4, -1, 0, 0, Quaternionf.fromAngleDegAxis(-90, 0, 1, 0)),
	SOUTH(0x8, 1, 0, 0, Quaternionf.fromAngleDegAxis(90, 0, 1, 0), NORTH),
	EAST(0x10, 0, 0, -1, Quaternionf.fromAngleDegAxis(180, 0, 1, 0)),
	WEST(0x20, 0, 0, 1, Quaternionf.fromAngleDegAxis(0, 0, 1, 0), EAST),
	THIS(0x40, 0, 0, 0, Quaternionf.IDENTITY);
	private final byte mask;
	private final Vector3f offset;
	private final Vector3i intOffset;
	private final Quaternionf direction;
	private BlockFace opposite = this;
	private static final TIntObjectHashMap<BlockFace> OFFSET_MAP = new TIntObjectHashMap<>(7);
	private static final long serialVersionUID = 1L;

	static {
		for (BlockFace face : values()) {
			OFFSET_MAP.put(getOffsetHash(face.getOffset()), face);
		}
	}

	private BlockFace(int mask, int dx, int dy, int dz, Quaternionf direction, BlockFace opposite) {
		this(mask, dx, dy, dz, direction);
		this.opposite = opposite;
		opposite.opposite = this;
	}

	private BlockFace(int mask, int dx, int dy, int dz, Quaternionf direction) {
		this.offset = new Vector3f(dx, dy, dz);
		this.intOffset = new Vector3i(dx, dy, dz);
		this.direction = direction;
		this.mask = (byte) mask;
	}

	private static int getOffsetHash(Vector3f offset) {
		int x = offset.getFloorX();
		int y = offset.getFloorY();
		int z = offset.getFloorZ();
		x += 1;
		y += 1;
		z += 1;
		return x | y << 2 | z << 4;
	}

	/**
	 * Represents the rotation of the BlockFace in the world as a Quaternion. This is the rotation form the west face to this face.
	 *
	 * @return the direction of the blockface.
	 */
	public Quaternionf getDirection() {
		return this.direction;
	}

	/**
	 * Represents the directional offset of this Blockface as a Vector3.
	 *
	 * @return the offset of this directional.
	 */
	public Vector3f getOffset() {
		return this.offset;
	}

	/**
	 * Represents the directional offset of this Blockface as an IntVector3.
	 *
	 * @return the offset of this directional.
	 */
	public Vector3i getIntOffset() {
		return this.intOffset;
	}

	/**
	 * Gets the opposite BlockFace. If this BlockFace has no opposite the method will return itself.
	 *
	 * @return the opposite BlockFace, or this if it has no opposite.
	 */
	public BlockFace getOpposite() {
		return this.opposite;
	}

	@Override
	public byte getMask() {
		return this.mask;
	}

	/**
	 * Uses a yaw angle to get the north, east, west or south face which points into the same direction.
	 *
	 * @param yaw to use
	 * @return the block face
	 */
	public static BlockFace fromYaw(float yaw) {
		return BlockFaces.WSEN.get(Math.round(yaw / 90f) & 0x3);
	}

	public static BlockFace fromOffset(Vector3f offset) {
		offset = offset.normalize().round();
		return OFFSET_MAP.get(getOffsetHash(offset));
	}
}
