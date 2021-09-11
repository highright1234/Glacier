package com.github.highright1234.glacier;

import com.github.highright1234.glacier.protocol.PipelineUtil;
import com.github.highright1234.glacier.protocol.handler.MinecraftDecoder;
import com.github.highright1234.glacier.protocol.handler.MinecraftEncoder;
import com.github.highright1234.glacier.protocol.Protocol;
import com.github.highright1234.glacier.protocol.handler.ReceivingEventHandler;
import com.github.highright1234.glacier.protocol.handler.SendingEventHandler;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.*;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.*;

public class GlacierServer {

    private final EventLoopGroup bossGroup, workerGroup;

    private final List<ClientConnection> clientConnections = new ArrayList<>();

    public Iterable<ClientConnection> getClientConnections() {
        return clientConnections;
    }

    @Getter
    private final Set<Integer> supportProtocols = new TreeSet<>();

    @Getter
    private final ServerConfig serverConfig = new ServerConfig();

    private final Map<Integer, Protocol> protocols = new HashMap<>();

    public Protocol getProtocol(int protocolVersion) {
        protocols.computeIfAbsent(protocolVersion, k -> protocols.put(protocolVersion, new Protocol()));
        return protocols.get(protocolVersion);
    }

    @Getter
    @Setter
    private ChannelInitializer<SocketChannel> channelInitializer = new ChannelInitializer<SocketChannel>() {
        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            Protocol.Type handshake = getProtocol(Protocol.Version.MINECRAFT_1_7_5).HANDSHAKE;
            ClientConnection cliCon = new ClientConnection(ch, handshake);
            ch.attr(PipelineUtil.CONNECTION).set(cliCon);
            ch.pipeline().addAfter(PipelineUtil.MINECRAFT_ENCODER, PipelineUtil.PACKET_ENCODER, new MinecraftEncoder(handshake, true));
            ch.pipeline().addAfter(PipelineUtil.MINECRAFT_DECODER, PipelineUtil.PACKET_DECODER, new MinecraftDecoder(handshake, true));
            ch.pipeline().addLast(PipelineUtil.RECEIVING_EVENT_CALLER, new ReceivingEventHandler(eventManager));
            ch.pipeline().addFirst(PipelineUtil.SENDING_EVENT_CALLER, new SendingEventHandler(eventManager));
        }
    };

    @Getter
    @Setter
    private ChannelFutureListener channelFutureListener = new ChannelFutureListener() {
        @Override
        public void operationComplete(ChannelFuture future) throws Exception {
            clientConnections.add(future.channel().attr(PipelineUtil.CONNECTION).get());
        }
    };

    @Getter
    private final EventManager eventManager = new EventManager();

    public GlacierServer() {
        bossGroup = new NioEventLoopGroup( 0,
                new ThreadFactoryBuilder().setNameFormat("Netty IO Thread #%1$d").build());
        workerGroup = new NioEventLoopGroup( 0,
                new ThreadFactoryBuilder().setNameFormat("Netty IO Thread #%1$d").build());
    }

    public void start() {
        new ServerBootstrap()
                .channel(NioServerSocketChannel.class)
                .option( ChannelOption.SO_REUSEADDR, true ) // TODO: Move this elsewhere!
                .childHandler( channelInitializer )
                .group( bossGroup , workerGroup )
                .localAddress( serverConfig.getAddress() )
                .bind().addListener( channelFutureListener );
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class ServerConfig {

        private String motd = "a Glacier server";
        private int maxPlayerCount = 20;
        private SocketAddress address = new InetSocketAddress("0.0.0.0", 25565);
        private long serverConnectTimeout = 5000;

    }
}
