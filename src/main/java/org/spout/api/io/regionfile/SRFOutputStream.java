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
package org.spout.api.io.regionfile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;

public class SRFOutputStream extends ByteArrayOutputStream {
	private final SimpleRegionFile srf;
	private final int index;
	private final Lock lock;
	private final AtomicBoolean lockUnlocked;

	SRFOutputStream(SimpleRegionFile srf, int index, int estimatedSize, Lock lock) {
		super(estimatedSize);
		this.srf = srf;
		this.index = index;
		this.lock = lock;
		this.lockUnlocked = new AtomicBoolean(false);
	}

	@Override
	public void close() throws IOException {
		if (this.lockUnlocked.compareAndSet(false, true)) {
			try {
				srf.write(index, buf, count);
			} finally {
				lock.unlock();
			}
		} else {
			throw new SRFException("Attempt made to close a block output stream twice");
		}
	}
}