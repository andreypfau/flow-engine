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
package org.spout.api;

import org.spout.api.entity.Player;
import org.spout.api.geo.World;
import org.spout.api.render.Renderer;

/**
 * Represents the client-specific component of the Spout platform.
 */
public interface Client extends Engine {
	/**
	 * Gets the player on the local machine (the one who is using the client).
	 *
	 * @return player
	 */
	public Player getPlayer();

	/**
	 * Gets the current world in-which the player on the local machine is within.
	 *
	 * This is always the world the client is currently rendering.
	 *
	 * @return world
	 */
	public World getWorld();

    /**
     * Gets the renderer that the client is using.
     *
     * @return the renderer in use
     */
    public Renderer getRenderer();
}
