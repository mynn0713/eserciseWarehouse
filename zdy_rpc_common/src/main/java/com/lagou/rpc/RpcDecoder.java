package com.lagou.rpc;

import com.lagou.serializer.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;

import java.util.List;

public class RpcDecoder extends ByteToMessageDecoder {

    private Class<?> clazz;

    private Serializer serializer;


    public RpcDecoder(Class<?> clazz, Serializer serializer) {

        this.clazz = clazz;

        this.serializer = serializer;

    }
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (byteBuf.readableBytes() < 4) {
            return;
        }
        byteBuf.markReaderIndex();
        int dataLength = byteBuf.readInt();
        if (byteBuf.readableBytes() < dataLength) {
            byteBuf.resetReaderIndex();
            return;
        }
        byte[] data = new byte[dataLength];
        byteBuf.readBytes(data);
        list.add(serializer.deserialize(clazz, data));
    }
}
