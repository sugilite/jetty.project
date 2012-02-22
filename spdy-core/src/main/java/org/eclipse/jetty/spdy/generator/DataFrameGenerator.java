/*
 * Copyright (c) 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.eclipse.jetty.spdy.generator;

import java.nio.ByteBuffer;

import org.eclipse.jetty.spdy.api.DataInfo;
import org.eclipse.jetty.spdy.frames.DataFrame;

public class DataFrameGenerator
{
    public ByteBuffer generate(int streamId, int windowSize, DataInfo dataInfo)
    {
        ByteBuffer buffer = ByteBuffer.allocateDirect(DataFrame.HEADER_LENGTH + windowSize);
        buffer.position(DataFrame.HEADER_LENGTH);
        // Guaranteed to always be >= 0
        int read = dataInfo.getContent(buffer);

        buffer.putInt(0, streamId & 0x7F_FF_FF_FF);
        buffer.putInt(4, read & 0x00_FF_FF_FF);

        // TODO: compression can be done here, as long as we have one DataFrameGenerator per stream
        // since the compression context for data is per-stream, without dictionary
        byte flags = dataInfo.isConsumed() && dataInfo.isClose() ? DataInfo.FLAG_CLOSE : 0;
        buffer.put(4, flags);

        buffer.flip();
        return buffer;
    }
}
