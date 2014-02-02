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
package com.flowpowered.api.scheduler;

public class IllegalTickSequenceException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public IllegalTickSequenceException(int allowedStages, int restrictedStages, Thread t, TickStage actualStage) {
        super(getMessage(allowedStages, restrictedStages, t, actualStage));
    }

    public IllegalTickSequenceException(int allowedStages, TickStage actualStage) {
        super("Method called during (" + actualStage + ") when only (" + TickStage.getAllStages(allowedStages) + ") were allowed");
    }

    private static String getMessage(int allowedStages, int restrictedStages, Thread t, TickStage actualStage) {
        if (Thread.currentThread() != t) {
            return "Method called by non-owning thread (" + Thread.currentThread() + ") during (" + actualStage + ") when only calls by (" + t + ") during (" + TickStage.getAllStages(allowedStages) + ") were allowed";
        } else {
            return "Method called during (" + actualStage + ") when only (" + TickStage.getAllStages(restrictedStages) + ") were allowed for owning thread " + t;
        }
    }
}
