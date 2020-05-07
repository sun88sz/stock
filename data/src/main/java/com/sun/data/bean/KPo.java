package com.sun.data.bean;

import com.sun.common.bean.K;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "a")
@Data
public class KPo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "code")
    private String code;

    @Column(name = "open")
    private Double open;

    @Column(name = "close")
    private Double close;

    @Column(name = "high")
    private Double high;

    @Column(name = "low")
    private Double low;

    @Column(name = "time")
    private LocalDateTime time;

    /**
     * 成交量
     */
    @Column
    private Double volume;
    /**
     * 成交额
     */
    @Column
    private Double amount;

    public KPo convertFrom(K k) {
        this.open = k.getOpen();
        this.close = k.getClose();
        this.high = k.getHigh();
        this.low = k.getLow();
        this.time = k.getTimeBegin();
        this.volume = k.getVolume();
        this.amount = k.getAmount();

        return this;
    }

    public K convertTo() {
        K k = new K();
        k.setOpen(this.open);
        k.setClose(this.close);
        k.setHigh(this.high);
        k.setLow(this.low);
        k.setVolume(this.volume);
        k.setAmount(this.amount);
        k.setTimeBegin(this.time);
        return k;
    }
}