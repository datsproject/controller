package com.datsddos.controller.app.model.emqx;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@lombok.Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode
public class Data {
    private int recv_cnt;
    private int recv_msg;
    private String zone;
    private String username;
    private String ip_address;
    private String created_at;
    private int max_inflight;
    private int send_pkt;
    private int recv_oct;
    private int mailbox_len;
    private int max_awaiting_rel;
    private int mqueue_len;
    private boolean connected;
    private int heap_size;
    private boolean clean_start;
    private boolean is_bridge;
    private int awaiting_rel;
    private int inflight;
    private String node;
    private String clientid;
    private int mqueue_dropped;
    private String proto_name;
    private int max_mqueue;
    private int subscriptions_cnt;
    private int keepalive;
    private int reductions;
    private int max_subscriptions;
    private int expiry_interval;
    private int recv_pkt;
    private int proto_ver;
    private int send_cnt;
    private int send_msg;
    private String mountpoint;
    private int send_oct;
    private int port;
    private String connected_at;
}
