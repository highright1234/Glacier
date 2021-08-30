package com.github.highright1234.glacier;

import com.github.highright1234.glacier.protocol.PacketDecoder;
import com.github.highright1234.glacier.protocol.PacketEncoder;
import com.github.highright1234.glacier.protocol.Protocol;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.AttributeKey;
import lombok.Getter;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Glacier {

    public static final AttributeKey<ClientConnection> CONNECTION = AttributeKey.valueOf( "ListerInfo" );
    public static final String MINECRAFT_ENCODER = "minecraft-encoder";
    public static final String MINECRAFT_DECODER = "minecraft-decoder";
    public static final String PACKET_ENCODER = "packet-encoder";
    public static final String PACKET_DECODER = "packet-decoder";

    private static Glacier instance;

    public static Glacier getInstance() {
        return instance;
    }

    private final List<ClientConnection> clientConnections = new ArrayList<>();

    public Iterable<ClientConnection> getClientConnections() {
        return clientConnections;
    }

    private final ServerConfig serverConfig = new ServerConfig();
    private ChannelInitializer<SocketChannel> channelInitializer = new ChannelInitializer<SocketChannel>() {
        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            ClientConnection cliCon = new ClientConnection(ch, Protocol.Version.MINECRAFT_1_17_1);
            ch.attr(CONNECTION).set(cliCon);
            ch.pipeline().addAfter(MINECRAFT_ENCODER, PACKET_ENCODER, new PacketEncoder(cliCon));
            ch.pipeline().addAfter(MINECRAFT_DECODER, PACKET_DECODER, new PacketDecoder(cliCon));
        }
    };

    private ChannelFutureListener channelFutureListener = new ChannelFutureListener() {
        @Override
        public void operationComplete(ChannelFuture future) throws Exception {
            clientConnections.add(future.channel().attr(CONNECTION).get());
        }
    };

    @Getter
    private final EventManager eventManager;

    public Glacier() {
        if (instance == null) {
            instance = this;
        }
        if (EventManager.getInstance() == null) {
            eventManager = new EventManager();
        } else {
            eventManager = EventManager.getInstance();
        }
    }

    public void start() {
        new ServerBootstrap()
                .channel(NioServerSocketChannel.class)
                .option( ChannelOption.SO_REUSEADDR, true ) // TODO: Move this elsewhere!
                .childHandler( channelInitializer )
                .group( new NioEventLoopGroup( 0,
                        new ThreadFactoryBuilder().setNameFormat("Netty IO Thread #%1$d").build()) )
                .localAddress( serverConfig.getAddress() )
                .bind().addListener( channelFutureListener );
    }

    public ChannelInitializer<SocketChannel> getChannelInitializer() {
        return channelInitializer;
    }

    // i don't recommend this method.
    public void setChannelInitializer(ChannelInitializer<SocketChannel> channelInitializer) {
        this.channelInitializer = channelInitializer;
    }

    public ChannelFutureListener getChannelFutureListener() {
        return channelFutureListener;
    }

    public void setChannelFutureListener(ChannelFutureListener channelFutureListener) {
        this.channelFutureListener = channelFutureListener;
    }

    public ServerConfig getServerConfig() {
        return serverConfig;
    }

    public static class ServerConfig {

        private String motd = "a Glacier server";
        private int maxPlayerCount = 20;
        private SocketAddress address = new InetSocketAddress("0.0.0.0", 25565);
        private long serverConnectTimeout = 5000;

        public ServerConfig() {
        }

        public ServerConfig(String motd, int maxPlayerCount, SocketAddress address, long serverConnectTimeout) {
            this.motd = motd;
            this.maxPlayerCount = maxPlayerCount;
            this.address = address;
            this.serverConnectTimeout = serverConnectTimeout;
        }

        public String getMotd() {
            return motd;
        }

        public void setMotd(String motd) {
            this.motd = motd;
        }

        public int getMaxPlayerCount() {
            return maxPlayerCount;
        }

        public void setMaxPlayerCount(int maxPlayerCount) {
            this.maxPlayerCount = maxPlayerCount;
        }

        public SocketAddress getAddress() {
            return address;
        }

        public void setAddress(SocketAddress address) {
            this.address = address;
        }

        public long getServerConnectTimeout() {
            return serverConnectTimeout;
        }

        public void setServerConnectTimeout(long serverConnectTimeout) {
            this.serverConnectTimeout = serverConnectTimeout;
        }
    }
}
