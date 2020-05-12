package com.sun.czsc;

import com.google.common.collect.Lists;
import com.sun.common.bean.K;
import com.sun.common.bean.Typing;
import com.sun.common.bean.TypingEnum;
import org.apache.commons.collections4.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;

public class Bi {

    /**
     * filter contains
     *
     * @param ks
     */
    public List<K> filter(List<K> ks) {
        List<K> combines = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(ks) && ks.size() > 2) {
            combines.add(ks.get(0));

            for (int i = 1; i < ks.size(); i++) {
                int combinesSize = combines.size();
                K prev = combines.get(combinesSize - 1);
                K prevOfPrev;
                if (combinesSize > 1) {
                    prevOfPrev = combines.get(combinesSize - 2);
                } else {
                    prevOfPrev = prev;
                }

                K curr = ks.get(i);

                // 当前包含下一根
                if (curr.contains(prev)) {
                    curr = combine(prevOfPrev, curr, prev);
                    combines.remove(combinesSize - 1);
                }
                // 下一根包含当前
                else if (prev.contains(curr)) {
                    curr = combine(prevOfPrev, prev, curr);
                    combines.remove(combinesSize - 1);
                }
                combines.add(curr);
            }
        }
        return combines;
    }

    private K combine(K prev, K longer, K shorter) {
        K.KBuilder kBuilder = K.builder();
        LocalDateTime timeBegin = null;
        LocalDateTime timeEnd = null;

        if (longer.getTimeBegin().compareTo(shorter.getTimeBegin()) < 0) {
            timeBegin = longer.getTimeBegin();
            timeEnd = shorter.getTimeEnd();
        } else {
            timeBegin = shorter.getTimeBegin();
            timeEnd = longer.getTimeEnd();
        }
        // 向上
        if (longer.getHigh() >= prev.getHigh() && longer.getLow() >= prev.getLow()) {
            return kBuilder.high(longer.getHigh()).low(shorter.getLow()).timeBegin(timeBegin).timeEnd(timeEnd).build();
        }
        // 向下
        else {
            return kBuilder.high(shorter.getHigh()).low(longer.getLow()).timeBegin(timeBegin).timeEnd(timeEnd).build();
        }
    }

    /**
     * typing
     *
     * @param ks
     */
    public List<Typing> typing(List<K> ks) {
        List<Typing> types = Lists.newArrayList();

        if (CollectionUtils.isNotEmpty(ks) && ks.size() > 2) {
            for (int i = 0; i < ks.size(); i++) {
                K curr = ks.get(i);
                curr.setIndex(i);
            }

            for (int i = 1; i < ks.size() - 1; i++) {
                K prev = ks.get(i - 1);
                K curr = ks.get(i);
                K next = ks.get(i + 1);

                if (curr.highest(prev, next)) {
                    Typing typing = new Typing(curr, prev, next, TypingEnum.TOP);
                    types.add(typing);
                } else if (curr.lowest(prev, next)) {
                    Typing typing = new Typing(curr, prev, next, TypingEnum.BOTTOM);
                    types.add(typing);
                }

            }
        }
        return types;
    }

    /**
     * bi
     *
     * @param typings
     * @return
     */
    public List<Typing> line(List<Typing> typings) {
        List<Typing> rtn = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(typings) && typings.size() > 1) {

            Typing last = typings.get(0);
            rtn.add(last);
            for (int i = 1; i < typings.size(); i++) {
                Typing curr = typings.get(i);
                // 相反类型
                if (!last.getType().equals(curr.getType())) {
                    if (last.getEnd().getIndex() + 1 < curr.getBegin().getIndex()) {
                        if (TypingEnum.TOP.equals(last.getType())) {
                            if (last.getExtremum() > curr.getExtremum()) {
                                rtn.add(curr);
                                last = curr;
                            }
                        } else {
                            if (curr.getExtremum() > last.getExtremum()) {
                                rtn.add(curr);
                                last = curr;
                            }
                        }
                    }
                }
                // 相同类型
                else {
                    if (TypingEnum.TOP.equals(last.getType())) {
                        if (last.getExtremum() < curr.getExtremum()) {
                            rtn.remove(rtn.size() - 1);
                            rtn.add(curr);
                            last = curr;
                        }
                    } else {
                        if (last.getExtremum() > curr.getExtremum()) {
                            rtn.remove(rtn.size() - 1);
                            rtn.add(curr);
                            last = curr;
                        }
                    }
                }
            }
        }
        return rtn;
    }


//    public static void main(String[] args) {
//        Bi bi = new Bi();
//        List<K> ks2 = bi.filter(ks);
//        List<Typing> typing = bi.typing(ks2);
//        List<Typing> line = bi.line(typing);
//
//        line.stream().forEach(
//                l -> {
//                    System.out.println(l.getType().getDesc() + " : " + l.getMiddle().getTimeBegin() + "-" + l.getMiddle().getTimeEnd());
//                }
//        );
//
//    }
}
