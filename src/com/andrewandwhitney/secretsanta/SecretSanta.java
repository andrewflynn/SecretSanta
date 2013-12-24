package com.andrewandwhitney.secretsanta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class SecretSanta {
    private static final Random RANDOM = new Random();
    private static final int COUNTER_MAX = 100000;
    
    private enum Family { SMITH, JOHNSON, ADAMS }
    private enum Member {
        JAMES(Family.SMITH), JENNIFER(Family.SMITH),
            STEVE(Family.SMITH), LANA(Family.SMITH), RACHEL(Family.SMITH),
            KATHERINE_S(Family.SMITH), JAMIE(Family.SMITH),
            
        GAVIN(Family.JOHNSON), KATHERINE_J(Family.JOHNSON),
            GREG(Family.JOHNSON), GENE(Family.JOHNSON), ERIN(Family.JOHNSON),
            NIKO(Family.JOHNSON), ROBERT(Family.JOHNSON), TODD(Family.JOHNSON), JEFF(Family.JOHNSON),
            
        MARCUS(Family.ADAMS), ELENA(Family.ADAMS),
            DANNY(Family.ADAMS), STEPHEN(Family.ADAMS);
        
        public final Family branch;
        
        private Member(Family branch) {
            this.branch = branch;
        }
    }
    
    public static void main(String[] args) {
        int counter = 0;
        Map<Member, Member> answer = null;
        while (counter <= COUNTER_MAX && !check(answer)) {
            answer = match();
            counter++;
        }
        if (counter <= COUNTER_MAX) {
            System.out.println("Found answer in (" + counter + ") tries");
            int nameCounter = 0;
            StringBuilder sb1 = new StringBuilder();
            StringBuilder sb2 = new StringBuilder();
            for (Map.Entry<Member, Member> entry : answer.entrySet()) {
                nameCounter++;
                sb1.append("#").append(nameCounter).append(" From: ").append(entry.getKey()).append("\n");
                sb2.append("#").append(nameCounter).append(" To  : ").append(entry.getValue()).append("\n");
            }
            System.out.println(sb1);
            System.out.println(sb2);
        } else {
            System.out.println("No answer");
        }
    }
    
    private static Map<Member, Member> match() {
        List<Member> pool = new ArrayList<Member>(Arrays.asList(Member.values()));
        Map<Member, Member> ret = new HashMap<Member, Member>();
        
        int counter = 0;

        Member first = pool.remove(RANDOM.nextInt(pool.size()));
        Member firstFirst = first;
        while (!pool.isEmpty()) {
            int rand = RANDOM.nextInt(pool.size());
            Member next = pool.get(rand);
            while (counter <= COUNTER_MAX && first.branch == next.branch) {
                rand = RANDOM.nextInt(pool.size());
                next = pool.get(rand);
                counter++;
            }
            if (counter > COUNTER_MAX) return null;
            
            next = pool.remove(rand);
            
            ret.put(first, next);
            first = next;
        }
        if (firstFirst.branch == first.branch) {
            return null;
        } else {
            ret.put(first, firstFirst);
        }
        
        return ret;
    }
    
    private static boolean check(Map<Member, Member> pool) {
        if (pool == null) return false;
        for (Map.Entry<Member, Member> entry : pool.entrySet()) {
            if (entry.getKey().branch == entry.getValue().branch) return false;
        }
        return true;
    }
}
