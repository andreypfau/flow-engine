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
package com.flowpowered.engine;

import java.util.concurrent.atomic.AtomicReference;

import org.apache.logging.log4j.LogManager;


import com.flowpowered.api.Platform;
import com.flowpowered.api.geo.discrete.Transform;
import com.flowpowered.engine.player.FlowPlayer;
import com.flowpowered.engine.network.FlowSession;
import com.flowpowered.engine.network.FlowSingleplayerSession;
import com.flowpowered.engine.render.FlowRenderer;
import org.spout.renderer.lwjgl.LWJGLUtil;

public class FlowSingleplayerImpl extends FlowServerImpl implements FlowSingleplayer {
    private final AtomicReference<FlowPlayer> player = new AtomicReference<>();
    private FlowSingleplayerSession session = null;

    public FlowSingleplayerImpl(FlowApplication args) {
        super(args);
    }

    @Override
    public void init() {
        try {
            LWJGLUtil.deployNatives(null);
        } catch (Exception ex) {
            LogManager.getLogger(FlowSingleplayer.class.getName()).fatal("", ex);
            return;
        }
        super.init();
    }

    @Override
    public void start() {
        getScheduler().startClientThreads(this);
        try {
            // Wait until RenderThread has initialized
            // TODO: confirm necessary
            getScheduler().getRenderThread().getIntializedLatch().await();
        } catch (InterruptedException ex) {}

        super.start();

        FlowPlayer player = addPlayer("Flowy", new FlowSingleplayerSession(this, true));
        this.player.set(player);
        session = new FlowSingleplayerSession(this, false);
        session.setPlayer(player);
    }

    @Override
    public boolean stop() {
        return super.stop();

    }

    @Override
    public Platform getPlatform() {
        return Platform.SINGLEPLAYER;
    }

    @Override
    public FlowPlayer getPlayer() {
        return player.get();
    }

    @Override
    public FlowRenderer getRenderer() {
        return getScheduler().getRenderThread().getRenderer();
    }

    @Override
    public Transform getTransform() {
        FlowPlayer player = getPlayer();
        if (player == null) {
            return Transform.INVALID;
        } else {
            return player.getTransformProvider().getTransform();
        }
    }

    @Override
    public void setTransform(Transform transform) {
        throw new UnsupportedOperationException("Singleplayer does not gets it's Transform set.");
    }

    @Override
    public FlowSession getSession() {
        return session;
    }
}
